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

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.task.MemoryDataManagerTask;

import oracle.sql.CLOB;

public class OracleJdbcUtis {

    private static final Logger logger = LoggerFactory.getLogger(OracleJdbcUtis.class);

    // 数据源单例（使用 volatile + 双重检查锁）
    private static volatile BasicDataSource dataSource = null;
    private static volatile BasicDataSource outerDataSource = null;
    private static volatile BasicDataSource outerDataWriteBackDataSource = null;

    // 超时配置（单位：毫秒）
    private static final int CONNECT_TIMEOUT_MS = 10 * 1000;      // 连接建立超时 10秒
    private static final int SOCKET_TIMEOUT_MS = 30 * 1000;       // socket读写超时 30秒
    private static final int VALIDATION_QUERY_TIMEOUT = 3;        // 验证查询超时 3秒

    // 重试配置
    private static final int MAX_RETRY_COUNT = 3;
    private static final long INITIAL_RETRY_DELAY_MS = 1000;       // 初始重试延迟 1秒

    // 连接池默认参数
    private static final int DEFAULT_MAX_TOTAL = 50;
    private static final int DEFAULT_MAX_IDLE = 20;
    private static final int DEFAULT_MIN_IDLE = 5;
    private static final int DEFAULT_INITIAL_SIZE = 5;
    private static final long DEFAULT_MAX_WAIT_MILLIS = CONNECT_TIMEOUT_MS;
    private static final int DEFAULT_REMOVE_ABANDONED_TIMEOUT = 180;   // 3分钟
    private static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MS = 20000; // 20秒

    // 健康检查调度器（可选，按需启用）
    private static volatile ScheduledExecutorService healthCheckScheduler = null;

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

            // 连接测试与验证
            String validationQuery = getValidationQuery(url, driver);
            if (validationQuery != null) {
                ds.setTestOnBorrow(true);
                ds.setTestOnReturn(true);
                ds.setTestWhileIdle(true);
                ds.setValidationQuery(validationQuery);
                ds.setValidationQueryTimeout(VALIDATION_QUERY_TIMEOUT);
            }

            // 连接回收与泄漏检测
            ds.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MS);
            ds.setMinEvictableIdleTimeMillis(60000);        // 空闲连接最少存活时间 60秒
            ds.setRemoveAbandonedOnBorrow(true);
            ds.setRemoveAbandonedTimeout(DEFAULT_REMOVE_ABANDONED_TIMEOUT);
            ds.setLogAbandoned(true);                       // 输出连接泄漏日志

            // 额外连接属性（TCP keepalive、超时等）
            Properties props = new Properties();
            props.setProperty("connectTimeout", String.valueOf(CONNECT_TIMEOUT_MS));
            props.setProperty("socketTimeout", String.valueOf(SOCKET_TIMEOUT_MS));
            props.setProperty("tcpKeepAlive", "true");
            ds.setConnectionProperties(props.toString());

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
            ds.setMaxTotal(100);   // 外部数据源可适当加大
            ds.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
            ds.setTestOnBorrow(true);
            ds.setValidationQuery("SELECT 1 FROM DUAL");
            ds.setValidationQueryTimeout(VALIDATION_QUERY_TIMEOUT);
            ds.setRemoveAbandonedOnBorrow(true);
            ds.setRemoveAbandonedTimeout(DEFAULT_REMOVE_ABANDONED_TIMEOUT);
            ds.setLogAbandoned(true);

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
            ds.setTestOnBorrow(true);
            ds.setValidationQuery("SELECT 1 FROM DUAL");
            ds.setValidationQueryTimeout(VALIDATION_QUERY_TIMEOUT);
            ds.setRemoveAbandonedOnBorrow(true);
            ds.setRemoveAbandonedTimeout(DEFAULT_REMOVE_ABANDONED_TIMEOUT);
            ds.setLogAbandoned(true);

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

    // ---------- 获取连接（带重试和异常分类） ----------
    public static Connection getConnection() {
        SQLException lastException = null;
        for (int retry = 0; retry < MAX_RETRY_COUNT; retry++) {
            try {
                BasicDataSource ds = getDataSource();
                if (ds == null) {
                    throw new SQLException("DataSource is not available");
                }
                Connection conn = ds.getConnection();
                // 快速验证连接有效性
                if (conn.isValid(VALIDATION_QUERY_TIMEOUT)) {
                    return conn;
                }
                // 无效连接则关闭并抛出异常
                closeQuietly(conn);
                throw new SQLException("Connection obtained but is invalid");
            } catch (SQLException e) {
                lastException = e;
                if (!isRetryableException(e)) {
                    logger.error("Non-retryable SQLException occurred, returning null", e);
                    return null;
                }
                if (retry == MAX_RETRY_COUNT - 1) {
                    break;
                }
                long delay = INITIAL_RETRY_DELAY_MS * (1L << retry); // 1,2,4秒
                logger.warn("Retryable SQLException, retry {} after {} ms: {}", retry + 1, delay, e.getMessage());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Interrupted during retry, returning null", ie);
                    return null;
                }
                // 重试前重置数据源（强制重建连接池）
                resetDataSource();
            }
        }
        logger.error("Failed to get connection after {} retries", MAX_RETRY_COUNT, lastException);
        return null;
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

    // 重置内部数据源（用于重试或健康检查）
    public static synchronized void resetDataSource() {
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
    }

    // 可选：启动后台健康检查线程（每30秒检查一次）
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
                        } catch (Exception e) {
                            logger.warn("Health check failed, resetting data source", e);
                            resetDataSource();
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
    public static synchronized int callProcedure(String procedure, List<Object> params) throws SQLException {
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