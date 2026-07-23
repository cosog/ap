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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.task.MemoryDataManagerTask;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import oracle.sql.CLOB;

public class OracleJdbcUtis {

    private static final Logger logger = LoggerFactory.getLogger(OracleJdbcUtis.class);

    // HikariCP 数据源单例（使用 volatile + 双重检查锁）
    private static volatile HikariDataSource dataSource = null;
    private static volatile HikariDataSource outerDataSource = null;
    private static volatile HikariDataSource outerDataWriteBackDataSource = null;

    // 超时配置（单位：毫秒）
    private static final int CONNECT_TIMEOUT_MS = 10 * 1000;      // 连接建立超时 10秒
    private static final int SOCKET_TIMEOUT_MS = 30 * 1000;       // socket读写超时 30秒
    private static final int VALIDATION_TIMEOUT_MS = 5000;        // 验证查询超时 5秒（单位毫秒，HikariCP要求>=250ms）

    // 高并发重试配置（最多重试1次，无延迟，快速失败）
    private static final int MAX_RETRY_COUNT = 2;
    private static final long RETRY_DELAY_MS = 100;

    // 连接池默认参数（HikariCP 3.x 兼容配置）
    private static final int DEFAULT_MAX_POOL_SIZE = 50;          // 最大连接数
    private static final int DEFAULT_MIN_IDLE = 20;               // 最小空闲连接数
    private static final long DEFAULT_CONNECTION_TIMEOUT_MS = 10000; // 获取连接超时10秒
    private static final long DEFAULT_IDLE_TIMEOUT_MS = 300000;   // 空闲超时5分钟（300秒）
    private static final long DEFAULT_MAX_LIFETIME_MS = 1800000;  // 最大生命周期30分钟（需小于数据库超时）
    private static final long DEFAULT_LEAK_DETECTION_THRESHOLD_MS = 0; // 连接泄露检测10秒

    // 健康检查调度器（可选）
    private static volatile ScheduledExecutorService healthCheckScheduler = null;
    private static final AtomicBoolean resetting = new AtomicBoolean(false);

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            logger.error("Oracle JDBC Driver not found", e);
        }
    }
    
    // ---------- 初始化内部数据源（HikariCP 3.4.5）----------
    private static void initDataSource() {
        try {
            String driver = Config.getInstance().configFile.getAp().getDatasource().getDriver();
            String url = Config.getInstance().configFile.getAp().getDatasource().getDriverUrl();
            String username = Config.getInstance().configFile.getAp().getDatasource().getUser();
            String password = Config.getInstance().configFile.getAp().getDatasource().getPassword();

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(driver);
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);

            // 连接池核心配置（3.x 支持的参数）
            config.setMaximumPoolSize(DEFAULT_MAX_POOL_SIZE);
            config.setMinimumIdle(DEFAULT_MIN_IDLE);
            config.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT_MS);
            config.setIdleTimeout(DEFAULT_IDLE_TIMEOUT_MS);
            config.setMaxLifetime(DEFAULT_MAX_LIFETIME_MS);
            config.setLeakDetectionThreshold(DEFAULT_LEAK_DETECTION_THRESHOLD_MS);

            // 连接有效性检查
            String validationQuery = getValidationQuery(url, driver);
            if (validationQuery != null) {
                config.setConnectionTestQuery(validationQuery);
                config.setValidationTimeout(VALIDATION_TIMEOUT_MS); // 单位毫秒，已修正
            }

            // 设置 JDBC 驱动级属性（连接超时、socket超时、tcp keepalive）
            config.addDataSourceProperty("oracle.net.CONNECT_TIMEOUT", String.valueOf(CONNECT_TIMEOUT_MS));
            config.addDataSourceProperty("oracle.net.SOCKET_TIMEOUT", String.valueOf(SOCKET_TIMEOUT_MS));
            config.addDataSourceProperty("oracle.jdbc.defaultConnectionValidation", "SOCKET");
            config.addDataSourceProperty("oracle.net.keepAlive", "true");

            // 其他性能优化配置
            config.setPoolName("HikariPool-Main");
            config.setRegisterMbeans(true);
            config.setAllowPoolSuspension(false);
            config.setAutoCommit(true);

            dataSource = new HikariDataSource(config);
            logger.info("HikariCP 3.x data source initialized successfully. Url: {}, MaxPoolSize: {}",
                    url, DEFAULT_MAX_POOL_SIZE);
        } catch (Exception e) {
            logger.error("Failed to initialize HikariCP data source", e);
            dataSource = null;
        }
    }

    // ---------- 初始化外部数据源（HikariCP 3.4.5）----------
    private static void initOuterDataSource() {
        DataSourceConfig dataSourceConfig = MemoryDataManagerTask.getDataSourceConfig();
        if (dataSourceConfig != null && dataSourceConfig.isEnable()) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + dataSourceConfig.getIP() + ":" + dataSourceConfig.getPort()
                    + (dataSourceConfig.getVersion() >= 12 ? "/" : ":") + dataSourceConfig.getInstanceName();
            config.setJdbcUrl(url);
            config.setUsername(dataSourceConfig.getUser());
            config.setPassword(dataSourceConfig.getPassword());

            config.setMaximumPoolSize(100);
            config.setMinimumIdle(DEFAULT_MIN_IDLE);
            config.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT_MS);
            config.setIdleTimeout(DEFAULT_IDLE_TIMEOUT_MS);
            config.setMaxLifetime(DEFAULT_MAX_LIFETIME_MS);
            config.setLeakDetectionThreshold(DEFAULT_LEAK_DETECTION_THRESHOLD_MS);

            config.setConnectionTestQuery("SELECT 1 FROM DUAL");
            config.setValidationTimeout(VALIDATION_TIMEOUT_MS); // 修正单位

            config.addDataSourceProperty("oracle.net.CONNECT_TIMEOUT", String.valueOf(CONNECT_TIMEOUT_MS));
            config.addDataSourceProperty("oracle.net.SOCKET_TIMEOUT", String.valueOf(SOCKET_TIMEOUT_MS));
            config.addDataSourceProperty("oracle.net.keepAlive", "true");

            config.setPoolName("HikariPool-Outer");
            config.setRegisterMbeans(true);

            outerDataSource = new HikariDataSource(config);
            logger.info("Outer HikariCP data source initialized. Url: {}", url);
        }
    }

    // ---------- 初始化写回数据源（HikariCP 3.4.5）----------
    private static void initDataWriteBackDataSource() {
        DataWriteBackConfig dataWriteBackConfig = MemoryDataManagerTask.getDataWriteBackConfig();
        if (dataWriteBackConfig != null && dataWriteBackConfig.isEnable()) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + dataWriteBackConfig.getIP() + ":" + dataWriteBackConfig.getPort()
                    + (dataWriteBackConfig.getVersion() >= 12 ? "/" : ":") + dataWriteBackConfig.getInstanceName();
            config.setJdbcUrl(url);
            config.setUsername(dataWriteBackConfig.getUser());
            config.setPassword(dataWriteBackConfig.getPassword());

            config.setMaximumPoolSize(100);
            config.setMinimumIdle(DEFAULT_MIN_IDLE);
            config.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT_MS);
            config.setIdleTimeout(DEFAULT_IDLE_TIMEOUT_MS);
            config.setMaxLifetime(DEFAULT_MAX_LIFETIME_MS);
            config.setLeakDetectionThreshold(DEFAULT_LEAK_DETECTION_THRESHOLD_MS);

            config.setConnectionTestQuery("SELECT 1 FROM DUAL");
            config.setValidationTimeout(VALIDATION_TIMEOUT_MS); // 修正单位

            config.addDataSourceProperty("oracle.net.CONNECT_TIMEOUT", String.valueOf(CONNECT_TIMEOUT_MS));
            config.addDataSourceProperty("oracle.net.SOCKET_TIMEOUT", String.valueOf(SOCKET_TIMEOUT_MS));
            config.addDataSourceProperty("oracle.net.keepAlive", "true");

            config.setPoolName("HikariPool-WriteBack");
            config.setRegisterMbeans(true);

            outerDataWriteBackDataSource = new HikariDataSource(config);
            logger.info("Data write-back HikariCP data source initialized. Url: {}", url);
        }
    }

    // ---------- 获取数据源实例（线程安全）----------
    private static HikariDataSource getDataSource() {
        if (dataSource == null) {
            synchronized (OracleJdbcUtis.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }
        return dataSource;
    }

    private static HikariDataSource getOuterDataSource() {
        if (outerDataSource == null) {
            synchronized (OracleJdbcUtis.class) {
                if (outerDataSource == null) {
                    initOuterDataSource();
                }
            }
        }
        return outerDataSource;
    }

    private static HikariDataSource getDataWriteBackDataSource() {
        if (outerDataWriteBackDataSource == null) {
            synchronized (OracleJdbcUtis.class) {
                if (outerDataWriteBackDataSource == null) {
                    initDataWriteBackDataSource();
                }
            }
        }
        return outerDataWriteBackDataSource;
    }

    // ---------- 获取连接（高并发优化：快速重试，直接抛异常）----------
    public static Connection getConnection() throws SQLException {
        SQLException lastException = null;
        for (int retry = 0; retry <= MAX_RETRY_COUNT; retry++) {
            try {
                HikariDataSource ds = getDataSource();
                if (ds == null) {
                    throw new SQLException("DataSource is not available");
                }
                return ds.getConnection();
            } catch (SQLException e) {
                lastException = e;
                if (retry < MAX_RETRY_COUNT && isRetryableException(e)) {
                    logger.warn("Retryable SQLException, immediate retry (attempt {})", retry + 1);
                    try {
						Thread.sleep(RETRY_DELAY_MS);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    continue;
                }
                break;
            }
        }
        logger.error("Failed to get connection after {} attempts", MAX_RETRY_COUNT + 1, lastException);
        String dataSourceStats= getDataSourceStats();
		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"-"+"连接池状态:"+dataSourceStats);
        throw lastException;
    }

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

    // 重置数据源（使用原子标志避免并发重置）
    public static void resetDataSource() {
        if (resetting.compareAndSet(false, true)) {
            try {
                if (dataSource != null) {
                    try {
                        dataSource.close();
                        logger.info("HikariCP DataSource closed and will be reinitialized on next request");
                    } catch (Exception e) {
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

    // 可选：启动后台健康检查（不自动重置，仅记录日志）
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
                            logger.debug("Health check succeeded");
                        } catch (Exception e) {
                            logger.warn("Health check failed", e);
                            // 不自动重置，避免惊群效应
                        }
                    }, 30, 30, TimeUnit.SECONDS);
                    logger.info("Database health check started");
                }
            }
        }
    }

    // ---------- 外部数据源连接 ----------
    public static Connection getOuterConnection() throws SQLException {
        HikariDataSource ds = getOuterDataSource();
        if (ds == null) {
            throw new SQLException("Outer data source is not enabled or not available");
        }
        return ds.getConnection();
    }

    public static Connection getDataWriteBackConnection() throws SQLException {
        HikariDataSource ds = getDataWriteBackDataSource();
        if (ds == null) {
            throw new SQLException("Data write-back data source is not enabled or not available");
        }
        return ds.getConnection();
    }

    // ---------- 原始的直接连接方式（保留兼容性）----------
    public static Connection getConnection(String driver, String url, String username, String password) {
        try {
            Class.forName(driver).newInstance();
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("oracle.net.CONNECT_TIMEOUT", String.valueOf(CONNECT_TIMEOUT_MS));
            props.setProperty("oracle.net.SOCKET_TIMEOUT", String.valueOf(SOCKET_TIMEOUT_MS));
            props.setProperty("oracle.net.keepAlive", "true");
            return DriverManager.getConnection(url, props);
        } catch (Exception e) {
            logger.error("Failed to create direct connection", e);
            return null;
        }
    }

    // ---------- 统一的资源关闭方法（支持变参）----------
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
            return conn != null && conn.isValid(VALIDATION_TIMEOUT_MS / 1000); // isValid 参数单位秒，转换一下
        } catch (Exception e) {
            logger.debug("Database status check failed", e);
            return false;
        }
    }

    // ---------- 执行更新（CLOB 支持）----------
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

    // ---------- 存储过程调用（移除了 synchronized）----------
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

    private static String buildProcedureCall(String procedure, List<Object> params) {
        StringBuilder call = new StringBuilder("{call ").append(procedure).append("(");
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                call.append("?,");
            }
            call.setLength(call.length() - 1);
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
    
    /**
     * 获取内部主数据源的连接池状态
     * @return 包含各项指标的字符串，格式：Total=X, Active=Y, Idle=Z, Waiting=W
     */
    public static String getDataSourceStats() {
        return getPoolStats(getDataSource());
    }

    /**
     * 获取外部数据源的连接池状态
     */
    public static String getOuterDataSourceStats() {
        return getPoolStats(getOuterDataSource());
    }

    /**
     * 获取写回数据源的连接池状态
     */
    public static String getDataWriteBackDataSourceStats() {
        return getPoolStats(getDataWriteBackDataSource());
    }

    /**
     * 获取所有数据源的简要状态（用于整体监控）
     */
    public static String getAllDataSourcesStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("Main: ").append(getDataSourceStats()).append("; ");
        sb.append("Outer: ").append(getOuterDataSourceStats()).append("; ");
        sb.append("WriteBack: ").append(getDataWriteBackDataSourceStats());
        return sb.toString();
    }

    // 私有通用方法：从 HikariDataSource 获取 HikariPoolMXBean 并提取指标
    private static String getPoolStats(HikariDataSource ds) {
        if (ds == null) {
            return "DataSource not initialized";
        }
        // HikariPoolMXBean 在 HikariCP 3.x 中位于 com.zaxxer.hikari 包下
        com.zaxxer.hikari.HikariPoolMXBean poolBean = ds.getHikariPoolMXBean();
        if (poolBean == null) {
            // 可能由于未启用 JMX 或连接池尚未初始化完成
            return "MXBean not available (enable registerMbeans or wait for initialization)";
        }
        try {
            int total = poolBean.getTotalConnections();
            int active = poolBean.getActiveConnections();
            int idle = poolBean.getIdleConnections();
            int waiting = poolBean.getThreadsAwaitingConnection();
            return String.format("Total=%d, Active=%d, Idle=%d, Waiting=%d", total, active, idle, waiting);
        } catch (Exception e) {
            logger.warn("Failed to get pool stats", e);
            return "Error retrieving stats: " + e.getMessage();
        }
    }
}