package com.cosog.utils;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.task.MemoryDataManagerTask;

import oracle.sql.CLOB;

public class OracleJdbcUtis3 {

    private static final Logger logger = LoggerFactory.getLogger(OracleJdbcUtis.class);

    // 数据源单例（使用 volatile + 双重检查锁）
    private static volatile BasicDataSource dataSource = null;
    private static volatile BasicDataSource outerDataSource = null;
    private static volatile BasicDataSource outerDataWriteBackDataSource = null;

    // 超时配置（单位：毫秒）
    private static final int CONNECT_TIMEOUT_MS = 10 * 1000;      // 连接建立超时 10秒
    private static final int SOCKET_TIMEOUT_MS = 30 * 1000;       // socket读写超时 30秒
    private static final int VALIDATION_QUERY_TIMEOUT = 5;        // 验证查询超时 5秒（适度增加）

    // 高并发重试配置：最多重试1次，无延迟（避免线程阻塞）
    private static final int MAX_RETRY_COUNT = 1;                 // 只重试1次
    private static final long INITIAL_RETRY_DELAY_MS = 0;         // 无延迟，立即重试

    // 连接池默认参数（针对高并发优化）
    private static final int DEFAULT_MAX_TOTAL = 50;              // 最大连接数（根据实际负载调整）
    private static final int DEFAULT_MAX_IDLE = 20;               // 最大空闲连接数
    private static final int DEFAULT_MIN_IDLE = 5;                // 最小空闲连接数
    private static final int DEFAULT_INITIAL_SIZE = 5;            // 初始连接数
    private static final long DEFAULT_MAX_WAIT_MILLIS = 10000;    // 获取连接最大等待时间10秒
    private static final int DEFAULT_REMOVE_ABANDONED_TIMEOUT = 180;   // 3分钟
    private static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MS = 20000; // 20秒

    // 健康检查调度器
    private static volatile ScheduledExecutorService healthCheckScheduler = null;
    private static final AtomicBoolean resetting = new AtomicBoolean(false); // 防止并发重置

    // ---------- 初始化数据源（内部使用） ----------
    private static void initDataSource() {
        try {
            String driver = Config.getInstance().configFile.getAp().getDatasource().getDriver();
            String url = Config.getInstance().configFile.getAp().getDatasource().getDriverUrl();
            String username = Config.getInstance().configFile.getAp().getDatasource().getUser();
            String password = Config.getInstance().configFile.getAp().getDatasource().getPassword();

            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(driver);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);

            // 连接池数量配置
            ds.setInitialSize(DEFAULT_INITIAL_SIZE);
            ds.setMaxIdle(DEFAULT_MAX_IDLE);
            ds.setMinIdle(DEFAULT_MIN_IDLE);
            ds.setMaxTotal(DEFAULT_MAX_TOTAL);
            ds.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);

            // [MODIFIED] 高并发优化：关闭借出验证（testOnBorrow），改用空闲验证（testWhileIdle）
            // 避免每次借连接都执行验证查询，降低数据库压力；空闲连接定期清理，极小概率拿到失效连接
            String validationQuery = getValidationQuery(url, driver);
            if (validationQuery != null) {
                ds.setTestOnBorrow(false);           // 关闭借出验证
                ds.setTestOnReturn(false);            // 关闭归还验证
                ds.setTestWhileIdle(true);            // 开启空闲验证
                ds.setValidationQuery(validationQuery);
                ds.setValidationQueryTimeout(VALIDATION_QUERY_TIMEOUT);
            }

            // 连接回收配置：空闲30秒以上且空闲检查时被驱逐
            ds.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MS);
            ds.setMinEvictableIdleTimeMillis(30000);  // 空闲30秒后驱逐（原60秒）
            ds.setRemoveAbandonedOnBorrow(true);
            ds.setRemoveAbandonedTimeout(DEFAULT_REMOVE_ABANDONED_TIMEOUT);
            ds.setLogAbandoned(true);

            // [FIXED] 修正连接属性设置格式
            String connProps = "connectTimeout=" + CONNECT_TIMEOUT_MS +
                               ";socketTimeout=" + SOCKET_TIMEOUT_MS +
                               ";tcpKeepAlive=true";
            ds.setConnectionProperties(connProps);

            dataSource = ds;
            logger.info("Database data source initialized successfully. Url: {}", url);
        } catch (Exception e) {
            logger.error("Failed to initialize data source", e);
            dataSource = null;
        }
    }

    private static void initOuterDataSource() {
        DataSourceConfig dataSourceConfig = MemoryDataManagerTask.getDataSourceConfig();
        if (dataSourceConfig != null && dataSourceConfig.isEnable()) {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + dataSourceConfig.getIP() + ":" + dataSourceConfig.getPort()
                    + (dataSourceConfig.getVersion() >= 12 ? "/" : ":") + dataSourceConfig.getInstanceName();
            ds.setUrl(url);
            ds.setUsername(dataSourceConfig.getUser());
            ds.setPassword(dataSourceConfig.getPassword());

            ds.setInitialSize(DEFAULT_INITIAL_SIZE);
            ds.setMaxIdle(DEFAULT_MAX_IDLE);
            ds.setMinIdle(DEFAULT_MIN_IDLE);
            ds.setMaxTotal(100);
            ds.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
            ds.setTestOnBorrow(false);
            ds.setTestWhileIdle(true);
            ds.setValidationQuery("SELECT 1 FROM DUAL");
            ds.setValidationQueryTimeout(VALIDATION_QUERY_TIMEOUT);
            ds.setRemoveAbandonedOnBorrow(true);
            ds.setRemoveAbandonedTimeout(DEFAULT_REMOVE_ABANDONED_TIMEOUT);
            ds.setLogAbandoned(true);

            String connProps = "connectTimeout=" + CONNECT_TIMEOUT_MS +
                               ";socketTimeout=" + SOCKET_TIMEOUT_MS +
                               ";tcpKeepAlive=true";
            ds.setConnectionProperties(connProps);

            outerDataSource = ds;
            logger.info("Outer data source initialized. Url: {}", url);
        }
    }

    private static void initDataWriteBackDataSource() {
        DataWriteBackConfig dataWriteBackConfig = MemoryDataManagerTask.getDataWriteBackConfig();
        if (dataWriteBackConfig != null && dataWriteBackConfig.isEnable()) {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + dataWriteBackConfig.getIP() + ":" + dataWriteBackConfig.getPort()
                    + (dataWriteBackConfig.getVersion() >= 12 ? "/" : ":") + dataWriteBackConfig.getInstanceName();
            ds.setUrl(url);
            ds.setUsername(dataWriteBackConfig.getUser());
            ds.setPassword(dataWriteBackConfig.getPassword());

            ds.setInitialSize(DEFAULT_INITIAL_SIZE);
            ds.setMaxIdle(DEFAULT_MAX_IDLE);
            ds.setMinIdle(DEFAULT_MIN_IDLE);
            ds.setMaxTotal(100);
            ds.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
            ds.setTestOnBorrow(false);
            ds.setTestWhileIdle(true);
            ds.setValidationQuery("SELECT 1 FROM DUAL");
            ds.setValidationQueryTimeout(VALIDATION_QUERY_TIMEOUT);
            ds.setRemoveAbandonedOnBorrow(true);
            ds.setRemoveAbandonedTimeout(DEFAULT_REMOVE_ABANDONED_TIMEOUT);
            ds.setLogAbandoned(true);

            String connProps = "connectTimeout=" + CONNECT_TIMEOUT_MS +
                               ";socketTimeout=" + SOCKET_TIMEOUT_MS +
                               ";tcpKeepAlive=true";
            ds.setConnectionProperties(connProps);

            outerDataWriteBackDataSource = ds;
            logger.info("Data write-back data source initialized. Url: {}", url);
        }
    }

    // ---------- 获取数据源实例（线程安全） ----------
    private static BasicDataSource getDataSource() {
        if (dataSource == null) {
            synchronized (OracleJdbcUtis.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }
        return dataSource;
    }

    private static BasicDataSource getOuterDataSource() {
        if (outerDataSource == null) {
            synchronized (OracleJdbcUtis.class) {
                if (outerDataSource == null) {
                    initOuterDataSource();
                }
            }
        }
        return outerDataSource;
    }

    private static BasicDataSource getDataWriteBackDataSource() {
        if (outerDataWriteBackDataSource == null) {
            synchronized (OracleJdbcUtis.class) {
                if (outerDataWriteBackDataSource == null) {
                    initDataWriteBackDataSource();
                }
            }
        }
        return outerDataWriteBackDataSource;
    }

    // ---------- 获取连接（高并发优化：直接抛出SQLException，快速失败，无阻塞重试） ----------
    // [MODIFIED] 移除返回 null 和 sleep 重试，改为最多一次快速重试（无延迟）
    public static Connection getConnection() throws SQLException {
        SQLException lastException = null;
        for (int retry = 0; retry <= MAX_RETRY_COUNT; retry++) {
            try {
                BasicDataSource ds = getDataSource();
                if (ds == null) {
                    throw new SQLException("DataSource is not available");
                }
                return ds.getConnection();
            } catch (SQLException e) {
                lastException = e;
                if (retry < MAX_RETRY_COUNT && isRetryableException(e)) {
                    // 快速重试，不 sleep，避免线程阻塞
                    logger.warn("Retryable SQLException, immediate retry (attempt {})", retry + 1);
                    continue;
                }
                break;
            }
        }
        logger.error("Failed to get connection after {} attempts", MAX_RETRY_COUNT + 1, lastException);
        throw lastException;  // 抛出异常，不再返回 null
    }

    // 判断异常是否可重试（连接超时、网络中断、无效连接等）
    private static boolean isRetryableException(SQLException e) {
        if (e instanceof SQLRecoverableException || e instanceof SQLTimeoutException) {
            return true;
        }
        String msg = e.getMessage();
        if (msg != null) {
            String lowerMsg = msg.toLowerCase();
            if (lowerMsg.contains("invalid connection") ||
                lowerMsg.contains("io error") ||
                lowerMsg.contains("connection refused") ||
                lowerMsg.contains("network adapter") ||
                lowerMsg.contains("timeout") ||
                lowerMsg.contains("closed connection")) {
                return true;
            }
        }
        return false;
    }

    // [MODIFIED] 重置数据源时使用原子标志避免并发重置
    public static void resetDataSource() {
        if (resetting.compareAndSet(false, true)) {
            try {
                if (dataSource != null) {
                    try {
                        dataSource.close();
                        logger.info("DataSource closed and will be reinitialized on next request");
                    } catch (SQLException e) {
                        logger.error("Error closing data source", e);
                    } finally {
                        dataSource = null;
                    }
                }
            } finally {
                resetting.set(false);
            }
        } else {
            logger.debug("DataSource reset already in progress, skipping");
        }
    }

    // [MODIFIED] 启动健康检查（每30秒一次，只记录日志，不自动重置，避免“惊群”）
    public static void startHealthCheck() {
        if (healthCheckScheduler == null) {
            synchronized (OracleJdbcUtis.class) {
                if (healthCheckScheduler == null) {
                    healthCheckScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                        Thread t = new Thread(r, "DB-HealthCheck");
                        t.setDaemon(true);
                        return t;
                    });
                    healthCheckScheduler.scheduleAtFixedRate(() -> {
                        try (Connection conn = getConnection();
                             Statement st = conn.createStatement();
                             ResultSet rs = st.executeQuery("SELECT 1 FROM DUAL")) {
                            // 健康
                            logger.debug("Health check succeeded");
                        } catch (Exception e) {
                            logger.warn("Health check failed, but won't auto-reset to avoid cascading issues", e);
                            // 可选：如果连续失败多次再重置，这里简化处理只记录日志
                        }
                    }, 30, 30, TimeUnit.SECONDS);
                    logger.info("Database health check started");
                }
            }
        }
    }

    // ---------- 外部数据源连接 ----------
    public static Connection getOuterConnection() throws SQLException {
        BasicDataSource ds = getOuterDataSource();
        if (ds == null) {
            throw new SQLException("Outer data source is not enabled or not available");
        }
        return ds.getConnection();
    }

    public static Connection getDataWriteBackConnection() throws SQLException {
        BasicDataSource ds = getDataWriteBackDataSource();
        if (ds == null) {
            throw new SQLException("Data write-back data source is not enabled or not available");
        }
        return ds.getConnection();
    }

    // ---------- 原始的直接连接方式（保留兼容性） ----------
    public static Connection getConnection(String driver, String url, String username, String password) {
        try {
            Class.forName(driver).newInstance();
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("connectTimeout", String.valueOf(CONNECT_TIMEOUT_MS));
            props.setProperty("socketTimeout", String.valueOf(SOCKET_TIMEOUT_MS));
            return DriverManager.getConnection(url, props);
        } catch (Exception e) {
            logger.error("Failed to create direct connection", e);
            return null;
        }
    }

    // ---------- 统一的资源关闭方法（支持变参） ----------
    public static void closeQuietly(AutoCloseable... resources) {
        if (resources == null) return;
        for (AutoCloseable ac : resources) {
            if (ac != null) {
                try {
                    ac.close();
                } catch (Exception e) {
                    logger.warn("Error closing resource", e);
                }
            }
        }
    }

    // 保留旧的重载方法以兼容现有调用（内部调用统一关闭）
    @Deprecated
    public static void closeDBConnection(Connection conn, PreparedStatement pstmt) {
        closeQuietly(pstmt, conn);
    }

    @Deprecated
    public static void closeDBConnection(Connection conn, CallableStatement cs) {
        closeQuietly(cs, conn);
    }

    @Deprecated
    public static void closeDBConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        closeQuietly(rs, pstmt, conn);
    }

    @Deprecated
    public static void closeDBConnection(PreparedStatement pstmt, ResultSet rs) {
        closeQuietly(rs, pstmt);
    }

    @Deprecated
    public static void closeDBConnection(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
        closeQuietly(rs, pstmt, stmt, conn);
    }

    // ---------- 数据库状态检查 ----------
    public static boolean databaseStatus() {
        try (Connection conn = getConnection()) {
            return conn != null && conn.isValid(VALIDATION_QUERY_TIMEOUT);
        } catch (Exception e) {
            logger.debug("Database status check failed", e);
            return false;
        }
    }

    // ---------- 执行更新（CLOB 支持） ----------
    public static int executeSqlUpdateClob(String sql, List<String> values) throws SQLException {
        return executeSqlUpdateClob(sql, values, 0);
    }

    public static int executeSqlUpdateClob(String sql, List<String> values, int timeOut) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (timeOut > 0) {
                ps.setQueryTimeout(timeOut);
            }
            for (int i = 0; i < values.size(); i++) {
                ps.setCharacterStream(i + 1, new java.io.StringReader(values.get(i)), values.get(i).length());
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("executeSqlUpdateClob failed, sql: {}", sql, e);
            throw e;
        }
    }

    public static int executeSqlUpdate(String sql) {
        return executeSqlUpdate(sql, 0);
    }

    public static int executeSqlUpdate(String sql, int timeOut) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (timeOut > 0) {
                pstmt.setQueryTimeout(timeOut);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("executeSqlUpdate failed, sql: {}", sql, e);
            return -1;
        }
    }

    // ---------- 查询方法 ----------
    public static List<Object[]> query(String sql) {
        return query(sql, 0);
    }

    public static List<Object[]> query(String sql, int timeout) {
        List<Object[]> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (timeout > 0) {
                ps.setQueryTimeout(timeout);
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Object[] objs = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        Object obj = rs.getObject(i + 1);
                        if (obj instanceof oracle.sql.CLOB || obj instanceof java.sql.Clob) {
                            try {
                                objs[i] = StringManagerUtils.CLOBtoString2(rs.getClob(i + 1));
                            } catch (IOException e) {
                                logger.error("CLOB conversion error", e);
                                objs[i] = "";
                            }
                        } else {
                            objs[i] = obj;
                        }
                    }
                    list.add(objs);
                }
            }
        } catch (SQLException e) {
            logger.error("query failed, sql: {}", sql, e);
        }
        return list;
    }

    // 带自定义连接信息的查询（保留原有接口）
    public static List<Object[]> query(String sql, String driver, String url, String username, String password) {
        return query(sql, driver, url, username, password, 0);
    }

    public static List<Object[]> query(String sql, String driver, String url, String username, String password, int timeout) {
        List<Object[]> list = new ArrayList<>();
        try (Connection conn = getConnection(driver, url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (timeout > 0) {
                ps.setQueryTimeout(timeout);
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Object[] objs = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        Object obj = rs.getObject(i + 1);
                        if (obj instanceof oracle.sql.CLOB || obj instanceof java.sql.Clob) {
                            try {
                                objs[i] = (rs.getClob(i + 1) != null) ? StringManagerUtils.CLOBtoString2(rs.getClob(i + 1)) : "";
                            } catch (IOException e) {
                                logger.error("CLOB conversion error", e);
                                objs[i] = "";
                            }
                        } else {
                            objs[i] = obj;
                        }
                    }
                    list.add(objs);
                }
            }
        } catch (SQLException e) {
            logger.error("query with custom connection failed, sql: {}", sql, e);
        }
        return list;
    }

    // ---------- 存储过程调用 ----------
    // [MODIFIED] 移除 synchronized，避免串行化（存储过程调用本身是线程安全的）
    public static int callProcedure(String procedure, List<Object> params) throws SQLException {
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(buildProcedureCall(procedure, params))) {
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    cs.setObject(i + 1, params.get(i));
                }
            }
            return cs.executeUpdate();
        } catch (SQLException e) {
            logger.error("callProcedure failed, procedure: {}", procedure, e);
            throw e;
        }
    }

    // Java 8 兼容的存储过程调用字符串构建
    private static String buildProcedureCall(String procedure, List<Object> params) {
        StringBuilder call = new StringBuilder("{call ").append(procedure).append("(");
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                call.append("?,");
            }
            call.setLength(call.length() - 1); // 删除最后一个逗号
        }
        call.append(")}");
        return call.toString();
    }

    // ---------- 辅助方法 ----------
    private static String getValidationQuery(String url, String driver) {
        if (url == null) return null;
        String urlLower = url.toLowerCase();
        if (urlLower.contains("oracle")) {
            return "SELECT 1 FROM DUAL";
        } else if (urlLower.contains("mysql") || urlLower.contains("postgresql") ||
                   urlLower.contains("sqlserver") || urlLower.contains("microsoft") ||
                   urlLower.contains("h2")) {
            return "SELECT 1";
        } else if (urlLower.contains("db2") || urlLower.contains("derby")) {
            return "SELECT 1 FROM SYSIBM.SYSDUMMY1";
        }
        return null;
    }
}