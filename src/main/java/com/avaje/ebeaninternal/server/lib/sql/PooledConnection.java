/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebeaninternal.jdbc.ConnectionDelegator;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePool;
import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
import com.avaje.ebeaninternal.server.lib.sql.PstmtCache;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PooledConnection
extends ConnectionDelegator {
    private static final Logger logger = Logger.getLogger(PooledConnection.class.getName());
    private static String IDLE_CONNECTION_ACCESSED_ERROR = "Pooled Connection has been accessed whilst idle in the pool, via method: ";
    static final int STATUS_IDLE = 88;
    static final int STATUS_ACTIVE = 89;
    static final int STATUS_ENDED = 87;
    final String name;
    final DataSourcePool pool;
    final Connection connection;
    final long creationTime;
    final PstmtCache pstmtCache;
    final Object pstmtMonitor = new Object();
    int status = 88;
    boolean longRunning;
    boolean hadErrors;
    long startUseTime;
    long lastUseTime;
    String lastStatement;
    int pstmtHitCounter;
    int pstmtMissCounter;
    String createdByMethod;
    StackTraceElement[] stackTrace;
    int maxStackTrace;
    int slotId;
    boolean resetIsolationReadOnlyRequired = false;

    public PooledConnection(DataSourcePool pool, int uniqueId, Connection connection) throws SQLException {
        super(connection);
        this.pool = pool;
        this.connection = connection;
        this.name = pool.getName() + "." + uniqueId;
        this.pstmtCache = new PstmtCache(this.name, pool.getPstmtCacheSize());
        this.maxStackTrace = pool.getMaxStackTraceSize();
        this.lastUseTime = this.creationTime = System.currentTimeMillis();
    }

    protected PooledConnection(String name) {
        super(null);
        this.name = name;
        this.pool = null;
        this.connection = null;
        this.pstmtCache = null;
        this.maxStackTrace = 0;
        this.lastUseTime = this.creationTime = System.currentTimeMillis();
    }

    public int getSlotId() {
        return this.slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public DataSourcePool getDataSourcePool() {
        return this.pool;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public String getDescription() {
        return "name[" + this.name + "] startTime[" + this.getStartUseTime() + "] stmt[" + this.getLastStatement() + "] createdBy[" + this.getCreatedByMethod() + "]";
    }

    public String getStatistics() {
        return "name[" + this.name + "] startTime[" + this.getStartUseTime() + "] pstmtHits[" + this.pstmtHitCounter + "] pstmtMiss[" + this.pstmtMissCounter + "] " + this.pstmtCache.getDescription();
    }

    public boolean isLongRunning() {
        return this.longRunning;
    }

    public void setLongRunning(boolean longRunning) {
        this.longRunning = longRunning;
    }

    public void closeConnectionFully(boolean logErrors) {
        block10: {
            String msg;
            block9: {
                block8: {
                    msg = "Closing Connection[" + this.getName() + "]" + " psReuse[" + this.pstmtHitCounter + "] psCreate[" + this.pstmtMissCounter + "] psSize[" + this.pstmtCache.size() + "]";
                    logger.info(msg);
                    try {
                        if (this.connection.isClosed()) {
                            msg = "Closing Connection[" + this.getName() + "] that is already closed?";
                            logger.log(Level.SEVERE, msg);
                            return;
                        }
                    }
                    catch (SQLException ex) {
                        if (!logErrors) break block8;
                        msg = "Error when fully closing connection [" + this.getName() + "]";
                        logger.log(Level.SEVERE, msg, ex);
                    }
                }
                try {
                    for (ExtendedPreparedStatement ps : this.pstmtCache.values()) {
                        ps.closeDestroy();
                    }
                }
                catch (SQLException ex) {
                    if (!logErrors) break block9;
                    logger.log(Level.WARNING, "Error when closing connection Statements", ex);
                }
            }
            try {
                this.connection.close();
            }
            catch (SQLException ex) {
                if (!logErrors) break block10;
                msg = "Error when fully closing connection [" + this.getName() + "]";
                logger.log(Level.SEVERE, msg, ex);
            }
        }
    }

    public PstmtCache getPstmtCache() {
        return this.pstmtCache;
    }

    @Override
    public Statement createStatement() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");
        }
        try {
            return this.connection.createStatement();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurreny) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");
        }
        try {
            return this.connection.createStatement(resultSetType, resultSetConcurreny);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void returnPreparedStatement(ExtendedPreparedStatement pstmt) {
        Object object = this.pstmtMonitor;
        synchronized (object) {
            ExtendedPreparedStatement alreadyInCache = this.pstmtCache.get(pstmt.getCacheKey());
            if (alreadyInCache == null) {
                this.pstmtCache.put(pstmt.getCacheKey(), pstmt);
            } else {
                try {
                    pstmt.closeDestroy();
                }
                catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error closing Pstmt", e);
                }
            }
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int returnKeysFlag) throws SQLException {
        String cacheKey = sql + returnKeysFlag;
        return this.prepareStatement(sql, true, returnKeysFlag, cacheKey);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.prepareStatement(sql, false, 0, sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PreparedStatement prepareStatement(String sql, boolean useFlag, int flag, String cacheKey) throws SQLException {
        if (this.status == 88) {
            String m = IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()";
            throw new SQLException(m);
        }
        try {
            Object m = this.pstmtMonitor;
            synchronized (m) {
                this.lastStatement = sql;
                ExtendedPreparedStatement pstmt = this.pstmtCache.remove(cacheKey);
                if (pstmt != null) {
                    ++this.pstmtHitCounter;
                    return pstmt;
                }
                ++this.pstmtMissCounter;
                PreparedStatement actualPstmt = useFlag ? this.connection.prepareStatement(sql, flag) : this.connection.prepareStatement(sql);
                return new ExtendedPreparedStatement(this, actualPstmt, sql, cacheKey);
            }
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurreny) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()");
        }
        try {
            ++this.pstmtMissCounter;
            this.lastStatement = sql;
            return this.connection.prepareStatement(sql, resultSetType, resultSetConcurreny);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    protected void resetForUse() {
        this.status = 89;
        this.startUseTime = System.currentTimeMillis();
        this.createdByMethod = null;
        this.lastStatement = null;
        this.hadErrors = false;
        this.longRunning = false;
    }

    public void addError(Throwable e) {
        this.hadErrors = true;
    }

    public boolean hadErrors() {
        return this.hadErrors;
    }

    @Override
    public void close() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "close()");
        }
        if (this.hadErrors && !this.pool.validateConnection(this)) {
            this.closeConnectionFully(false);
            this.pool.checkDataSource();
            return;
        }
        try {
            if (this.connection.getAutoCommit() != this.pool.getAutoCommit()) {
                this.connection.setAutoCommit(this.pool.getAutoCommit());
            }
            if (this.resetIsolationReadOnlyRequired) {
                this.resetIsolationReadOnly();
                this.resetIsolationReadOnlyRequired = false;
            }
            this.lastUseTime = System.currentTimeMillis();
            this.status = 88;
            this.pool.returnConnection(this);
        }
        catch (Exception ex) {
            this.closeConnectionFully(false);
            this.pool.checkDataSource();
        }
    }

    private void resetIsolationReadOnly() throws SQLException {
        if (this.connection.getTransactionIsolation() != this.pool.getTransactionIsolation()) {
            this.connection.setTransactionIsolation(this.pool.getTransactionIsolation());
        }
        if (this.connection.isReadOnly()) {
            this.connection.setReadOnly(false);
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                String msg = "Closing Connection[" + this.getName() + "] on finalize().";
                logger.warning(msg);
                this.closeConnectionFully(false);
            }
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        super.finalize();
    }

    public long getStartUseTime() {
        return this.startUseTime;
    }

    public long getLastUsedTime() {
        return this.lastUseTime;
    }

    public String getLastStatement() {
        return this.lastStatement;
    }

    protected void setLastStatement(String lastStatement) {
        this.lastStatement = lastStatement;
        if (logger.isLoggable(Level.FINER)) {
            logger.finer(".setLastStatement[" + lastStatement + "]");
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        this.resetIsolationReadOnlyRequired = true;
        this.connection.setReadOnly(readOnly);
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setTransactionIsolation()");
        }
        try {
            this.resetIsolationReadOnlyRequired = true;
            this.connection.setTransactionIsolation(level);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public void clearWarnings() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "clearWarnings()");
        }
        this.connection.clearWarnings();
    }

    @Override
    public void commit() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "commit()");
        }
        try {
            this.status = 87;
            this.connection.commit();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getAutoCommit()");
        }
        return this.connection.getAutoCommit();
    }

    @Override
    public String getCatalog() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getCatalog()");
        }
        return this.connection.getCatalog();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getMetaData()");
        }
        return this.connection.getMetaData();
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getTransactionIsolation()");
        }
        return this.connection.getTransactionIsolation();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getTypeMap()");
        }
        return this.connection.getTypeMap();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getWarnings()");
        }
        return this.connection.getWarnings();
    }

    @Override
    public boolean isClosed() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "isClosed()");
        }
        return this.connection.isClosed();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "isReadOnly()");
        }
        return this.connection.isReadOnly();
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "nativeSQL()");
        }
        this.lastStatement = sql;
        return this.connection.nativeSQL(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
        }
        this.lastStatement = sql;
        return this.connection.prepareCall(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurreny) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
        }
        this.lastStatement = sql;
        return this.connection.prepareCall(sql, resultSetType, resultSetConcurreny);
    }

    @Override
    public void rollback() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "rollback()");
        }
        try {
            this.status = 87;
            this.connection.rollback();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setAutoCommit()");
        }
        try {
            this.connection.setAutoCommit(autoCommit);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setCatalog()");
        }
        this.connection.setCatalog(catalog);
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setTypeMap()");
        }
        this.connection.setTypeMap(map);
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        try {
            return this.connection.setSavepoint();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public Savepoint setSavepoint(String savepointName) throws SQLException {
        try {
            return this.connection.setSavepoint(savepointName);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public void rollback(Savepoint sp) throws SQLException {
        try {
            this.connection.rollback(sp);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public void releaseSavepoint(Savepoint sp) throws SQLException {
        try {
            this.connection.releaseSavepoint(sp);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public void setHoldability(int i) throws SQLException {
        try {
            this.connection.setHoldability(i);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public int getHoldability() throws SQLException {
        try {
            return this.connection.getHoldability();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public Statement createStatement(int i, int x, int y) throws SQLException {
        try {
            return this.connection.createStatement(i, x, y);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int x, int y) throws SQLException {
        try {
            return this.connection.prepareStatement(s, i, x, y);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public PreparedStatement prepareStatement(String s, int[] i) throws SQLException {
        try {
            return this.connection.prepareStatement(s, i);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public PreparedStatement prepareStatement(String s, String[] s2) throws SQLException {
        try {
            return this.connection.prepareStatement(s, s2);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int x, int y) throws SQLException {
        try {
            return this.connection.prepareCall(s, i, x, y);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }

    public String getCreatedByMethod() {
        if (this.createdByMethod != null) {
            return this.createdByMethod;
        }
        if (this.stackTrace == null) {
            return null;
        }
        for (int j = 0; j < this.stackTrace.length; ++j) {
            String methodLine = this.stackTrace[j].toString();
            if (this.skipElement(methodLine)) continue;
            this.createdByMethod = methodLine;
            return this.createdByMethod;
        }
        return null;
    }

    private boolean skipElement(String methodLine) {
        if (methodLine.startsWith("java.lang.")) {
            return true;
        }
        if (methodLine.startsWith("java.util.")) {
            return true;
        }
        if (methodLine.startsWith("com.avaje.ebeaninternal.server.query.CallableQuery.<init>")) {
            return true;
        }
        if (methodLine.startsWith("com.avaje.ebeaninternal.server.query.Callable")) {
            return false;
        }
        return methodLine.startsWith("com.avaje.ebeaninternal");
    }

    protected void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public StackTraceElement[] getStackTrace() {
        if (this.stackTrace == null) {
            return null;
        }
        ArrayList<StackTraceElement> filteredList = new ArrayList<StackTraceElement>();
        boolean include = false;
        for (int i = 0; i < this.stackTrace.length; ++i) {
            if (!include && !this.skipElement(this.stackTrace[i].toString())) {
                include = true;
            }
            if (!include || filteredList.size() >= this.maxStackTrace) continue;
            filteredList.add(this.stackTrace[i]);
        }
        return filteredList.toArray(new StackTraceElement[filteredList.size()]);
    }
}

