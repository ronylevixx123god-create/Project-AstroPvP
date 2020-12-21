/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.lib.cron.CronManager;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceException;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceNotify;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePoolListener;
import com.avaje.ebeaninternal.server.lib.sql.PooledConnection;
import com.avaje.ebeaninternal.server.lib.sql.PooledConnectionQueue;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DataSourcePool
implements DataSource {
    private static final Logger logger = Logger.getLogger(DataSourcePool.class.getName());
    private final String name;
    private final DataSourceNotify notify;
    private final DataSourcePoolListener poolListener;
    private final Properties connectionProps;
    private final String databaseUrl;
    private final String databaseDriver;
    private final String heartbeatsql;
    private final int transactionIsolation;
    private final boolean autoCommit;
    private boolean captureStackTrace;
    private int maxStackTraceSize;
    private boolean dataSourceDownAlertSent;
    private long lastTrimTime;
    private boolean dataSourceUp = true;
    private boolean inWarningMode;
    private int minConnections;
    private int maxConnections;
    private int warningSize;
    private int waitTimeoutMillis;
    private int pstmtCacheSize;
    private int maxInactiveTimeSecs;
    private final PooledConnectionQueue queue;
    private long leakTimeMinutes;

    public DataSourcePool(DataSourceNotify notify, String name, DataSourceConfig params) {
        this.notify = notify;
        this.name = name;
        this.poolListener = this.createPoolListener(params.getPoolListener());
        this.autoCommit = false;
        this.transactionIsolation = params.getIsolationLevel();
        this.maxInactiveTimeSecs = params.getMaxInactiveTimeSecs();
        this.leakTimeMinutes = params.getLeakTimeMinutes();
        this.captureStackTrace = params.isCaptureStackTrace();
        this.maxStackTraceSize = params.getMaxStackTraceSize();
        this.databaseDriver = params.getDriver();
        this.databaseUrl = params.getUrl();
        this.pstmtCacheSize = params.getPstmtCacheSize();
        this.minConnections = params.getMinConnections();
        this.maxConnections = params.getMaxConnections();
        this.waitTimeoutMillis = params.getWaitTimeoutMillis();
        this.heartbeatsql = params.getHeartbeatSql();
        this.queue = new PooledConnectionQueue(this);
        String un = params.getUsername();
        String pw = params.getPassword();
        if (un == null) {
            throw new RuntimeException("DataSource user is null?");
        }
        if (pw == null) {
            throw new RuntimeException("DataSource password is null?");
        }
        this.connectionProps = new Properties();
        this.connectionProps.setProperty("user", un);
        this.connectionProps.setProperty("password", pw);
        try {
            this.initialise();
        }
        catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    private DataSourcePoolListener createPoolListener(String cn) {
        if (cn == null) {
            return null;
        }
        try {
            return (DataSourcePoolListener)ClassUtil.newInstance(cn, this.getClass());
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private void initialise() throws SQLException {
        try {
            ClassUtil.forName(this.databaseDriver, this.getClass());
        }
        catch (Throwable e) {
            throw new PersistenceException("Problem loading Database Driver [" + this.databaseDriver + "]: " + e.getMessage(), e);
        }
        String transIsolation = TransactionIsolation.getLevelDescription(this.transactionIsolation);
        StringBuffer sb = new StringBuffer();
        sb.append("DataSourcePool [").append(this.name);
        sb.append("] autoCommit[").append(this.autoCommit);
        sb.append("] transIsolation[").append(transIsolation);
        sb.append("] min[").append(this.minConnections);
        sb.append("] max[").append(this.maxConnections).append("]");
        logger.info(sb.toString());
        this.queue.ensureMinimumConnections();
    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        throw new SQLException("Not Implemented");
    }

    public String getName() {
        return this.name;
    }

    public int getMaxStackTraceSize() {
        return this.maxStackTraceSize;
    }

    public boolean isDataSourceUp() {
        return this.dataSourceUp;
    }

    protected void notifyWarning(String msg) {
        if (!this.inWarningMode) {
            this.inWarningMode = true;
            logger.warning(msg);
            if (this.notify != null) {
                String subject = "DataSourcePool [" + this.name + "] warning";
                this.notify.notifyWarning(subject, msg);
            }
        }
    }

    private void notifyDataSourceIsDown(SQLException ex) {
        if (this.isExpectedToBeDownNow()) {
            if (this.dataSourceUp) {
                String msg = "DataSourcePool [" + this.name + "] is down but in downtime!";
                logger.log(Level.WARNING, msg, ex);
            }
        } else if (!this.dataSourceDownAlertSent) {
            String msg = "FATAL: DataSourcePool [" + this.name + "] is down!!!";
            logger.log(Level.SEVERE, msg, ex);
            if (this.notify != null) {
                this.notify.notifyDataSourceDown(this.name);
            }
            this.dataSourceDownAlertSent = true;
        }
        if (this.dataSourceUp) {
            this.reset();
        }
        this.dataSourceUp = false;
    }

    private void notifyDataSourceIsUp() {
        if (this.dataSourceDownAlertSent) {
            String msg = "RESOLVED FATAL: DataSourcePool [" + this.name + "] is back up!";
            logger.log(Level.SEVERE, msg);
            if (this.notify != null) {
                this.notify.notifyDataSourceUp(this.name);
            }
            this.dataSourceDownAlertSent = false;
        } else if (!this.dataSourceUp) {
            logger.log(Level.WARNING, "DataSourcePool [" + this.name + "] is back up!");
        }
        if (!this.dataSourceUp) {
            this.dataSourceUp = true;
            this.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void checkDataSource() {
        Connection conn = null;
        try {
            conn = this.getConnection();
            this.testConnection(conn);
            this.notifyDataSourceIsUp();
            if (System.currentTimeMillis() > this.lastTrimTime + (long)(this.maxInactiveTimeSecs * 1000)) {
                this.queue.trim(this.maxInactiveTimeSecs);
                this.lastTrimTime = System.currentTimeMillis();
            }
        }
        catch (SQLException ex) {
            this.notifyDataSourceIsDown(ex);
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                logger.log(Level.WARNING, "Can't close connection in checkDataSource!");
            }
        }
    }

    private boolean isExpectedToBeDownNow() {
        return CronManager.isDowntime();
    }

    public Connection createUnpooledConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(this.databaseUrl, this.connectionProps);
            conn.setAutoCommit(this.autoCommit);
            conn.setTransactionIsolation(this.transactionIsolation);
            return conn;
        }
        catch (SQLException ex) {
            this.notifyDataSourceIsDown(null);
            throw ex;
        }
    }

    public void setMaxSize(int max) {
        this.queue.setMaxSize(max);
        this.maxConnections = max;
    }

    public int getMaxSize() {
        return this.maxConnections;
    }

    public void setMinSize(int min) {
        this.queue.setMinSize(min);
        this.minConnections = min;
    }

    public int getMinSize() {
        return this.minConnections;
    }

    public void setWarningSize(int warningSize) {
        this.queue.setWarningSize(warningSize);
        this.warningSize = warningSize;
    }

    public int getWarningSize() {
        return this.warningSize;
    }

    public int getWaitTimeoutMillis() {
        return this.waitTimeoutMillis;
    }

    public void setMaxInactiveTimeSecs(int maxInactiveTimeSecs) {
        this.maxInactiveTimeSecs = maxInactiveTimeSecs;
    }

    public int getMaxInactiveTimeSecs() {
        return this.maxInactiveTimeSecs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void testConnection(Connection conn) throws SQLException {
        if (this.heartbeatsql == null) {
            return;
        }
        Statement stmt = null;
        ResultSet rset = null;
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(this.heartbeatsql);
            conn.commit();
        }
        finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            }
            catch (SQLException e) {
                logger.log(Level.SEVERE, null, e);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }
    }

    protected boolean validateConnection(PooledConnection conn) {
        try {
            if (this.heartbeatsql == null) {
                logger.info("Can not test connection as heartbeatsql is not set");
                return false;
            }
            this.testConnection(conn);
            return true;
        }
        catch (Exception e) {
            String desc = "heartbeatsql test failed on connection[" + conn.getName() + "]";
            logger.warning(desc);
            return false;
        }
    }

    protected void returnConnection(PooledConnection pooledConnection) {
        if (this.poolListener != null) {
            this.poolListener.onBeforeReturnConnection(pooledConnection);
        }
        this.queue.returnPooledConnection(pooledConnection);
    }

    public String getBusyConnectionInformation() {
        return this.queue.getBusyConnectionInformation();
    }

    public void dumpBusyConnectionInformation() {
        this.queue.dumpBusyConnectionInformation();
    }

    public void closeBusyConnections(long leakTimeMinutes) {
        this.queue.closeBusyConnections(leakTimeMinutes);
    }

    protected PooledConnection createConnectionForQueue(int connId) throws SQLException {
        try {
            Connection c = this.createUnpooledConnection();
            PooledConnection pc = new PooledConnection(this, connId, c);
            pc.resetForUse();
            if (!this.dataSourceUp) {
                this.notifyDataSourceIsUp();
            }
            return pc;
        }
        catch (SQLException ex) {
            this.notifyDataSourceIsDown(ex);
            throw ex;
        }
    }

    public void reset() {
        this.queue.reset(this.leakTimeMinutes);
        this.inWarningMode = false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getPooledConnection();
    }

    public PooledConnection getPooledConnection() throws SQLException {
        PooledConnection c = this.queue.getPooledConnection();
        if (this.captureStackTrace) {
            c.setStackTrace(Thread.currentThread().getStackTrace());
        }
        if (this.poolListener != null) {
            this.poolListener.onAfterBorrowConnection(c);
        }
        return c;
    }

    public void testAlert() {
        String subject = "Test DataSourcePool [" + this.name + "]";
        String msg = "Just testing if alert message is sent successfully.";
        if (this.notify != null) {
            this.notify.notifyWarning(subject, msg);
        }
    }

    public void shutdown() {
        this.queue.shutdown();
    }

    public boolean getAutoCommit() {
        return this.autoCommit;
    }

    public int getTransactionIsolation() {
        return this.transactionIsolation;
    }

    public boolean isCaptureStackTrace() {
        return this.captureStackTrace;
    }

    public void setCaptureStackTrace(boolean captureStackTrace) {
        this.captureStackTrace = captureStackTrace;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter writer) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setLeakTimeMinutes(long leakTimeMinutes) {
        this.leakTimeMinutes = leakTimeMinutes;
    }

    public long getLeakTimeMinutes() {
        return this.leakTimeMinutes;
    }

    public int getPstmtCacheSize() {
        return this.pstmtCacheSize;
    }

    public void setPstmtCacheSize(int pstmtCacheSize) {
        this.pstmtCacheSize = pstmtCacheSize;
    }

    public Status getStatus(boolean reset) {
        return this.queue.getStatus(reset);
    }

    public static class Status {
        private final String name;
        private final int minSize;
        private final int maxSize;
        private final int free;
        private final int busy;
        private final int waiting;
        private final int highWaterMark;
        private final int waitCount;
        private final int hitCount;

        protected Status(String name, int minSize, int maxSize, int free, int busy, int waiting, int highWaterMark, int waitCount, int hitCount) {
            this.name = name;
            this.minSize = minSize;
            this.maxSize = maxSize;
            this.free = free;
            this.busy = busy;
            this.waiting = waiting;
            this.highWaterMark = highWaterMark;
            this.waitCount = waitCount;
            this.hitCount = hitCount;
        }

        public String toString() {
            return "min:" + this.minSize + " max:" + this.maxSize + " free:" + this.free + " busy:" + this.busy + " waiting:" + this.waiting + " highWaterMark:" + this.highWaterMark + " waitCount:" + this.waitCount + " hitCount:" + this.hitCount;
        }

        public String getName() {
            return this.name;
        }

        public int getMinSize() {
            return this.minSize;
        }

        public int getMaxSize() {
            return this.maxSize;
        }

        public int getFree() {
            return this.free;
        }

        public int getBusy() {
            return this.busy;
        }

        public int getWaiting() {
            return this.waiting;
        }

        public int getHighWaterMark() {
            return this.highWaterMark;
        }

        public int getWaitCount() {
            return this.waitCount;
        }

        public int getHitCount() {
            return this.hitCount;
        }
    }
}

