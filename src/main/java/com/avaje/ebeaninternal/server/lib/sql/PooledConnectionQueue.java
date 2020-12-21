/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebeaninternal.server.lib.sql.BusyConnectionBuffer;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePool;
import com.avaje.ebeaninternal.server.lib.sql.FreeConnectionBuffer;
import com.avaje.ebeaninternal.server.lib.sql.PooledConnection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PooledConnectionQueue {
    private static final Logger logger = Logger.getLogger(PooledConnectionQueue.class.getName());
    private static final TimeUnit MILLIS_TIME_UNIT = TimeUnit.MILLISECONDS;
    private final String name;
    private final DataSourcePool pool;
    private final FreeConnectionBuffer freeList;
    private final BusyConnectionBuffer busyList;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private int connectionId;
    private long waitTimeoutMillis;
    private long leakTimeMinutes;
    private int warningSize;
    private int maxSize;
    private int minSize;
    private int waitingThreads;
    private int waitCount;
    private int hitCount;
    private int highWaterMark;
    private long lastResetTime;
    private boolean doingShutdown;

    public PooledConnectionQueue(DataSourcePool pool) {
        this.pool = pool;
        this.name = pool.getName();
        this.minSize = pool.getMinSize();
        this.maxSize = pool.getMaxSize();
        this.warningSize = pool.getWarningSize();
        this.waitTimeoutMillis = pool.getWaitTimeoutMillis();
        this.leakTimeMinutes = pool.getLeakTimeMinutes();
        this.busyList = new BusyConnectionBuffer(50, 20);
        this.freeList = new FreeConnectionBuffer(this.maxSize);
        this.lock = new ReentrantLock(true);
        this.notEmpty = this.lock.newCondition();
    }

    private DataSourcePool.Status createStatus() {
        return new DataSourcePool.Status(this.name, this.minSize, this.maxSize, this.freeList.size(), this.busyList.size(), this.waitingThreads, this.highWaterMark, this.waitCount, this.hitCount);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            String string = this.createStatus().toString();
            return string;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataSourcePool.Status getStatus(boolean reset) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            DataSourcePool.Status s = this.createStatus();
            if (reset) {
                this.highWaterMark = this.busyList.size();
                this.hitCount = 0;
                this.waitCount = 0;
            }
            DataSourcePool.Status status = s;
            return status;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMinSize(int minSize) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (minSize > this.maxSize) {
                throw new IllegalArgumentException("minSize " + minSize + " > maxSize " + this.maxSize);
            }
            this.minSize = minSize;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMaxSize(int maxSize) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (maxSize < this.minSize) {
                throw new IllegalArgumentException("maxSize " + maxSize + " < minSize " + this.minSize);
            }
            this.freeList.setCapacity(maxSize);
            this.maxSize = maxSize;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setWarningSize(int warningSize) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (warningSize > this.maxSize) {
                throw new IllegalArgumentException("warningSize " + warningSize + " > maxSize " + this.maxSize);
            }
            this.warningSize = warningSize;
        }
        finally {
            lock.unlock();
        }
    }

    private int totalConnections() {
        return this.freeList.size() + this.busyList.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void ensureMinimumConnections() throws SQLException {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int add = this.minSize - this.totalConnections();
            if (add > 0) {
                for (int i = 0; i < add; ++i) {
                    PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
                    this.freeList.add(c);
                }
                this.notEmpty.signal();
            }
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void returnPooledConnection(PooledConnection c) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (!this.busyList.remove(c)) {
                logger.log(Level.SEVERE, "Connection [" + c + "] not found in BusyList? ");
            }
            if (c.getCreationTime() <= this.lastResetTime) {
                c.closeConnectionFully(false);
            } else {
                this.freeList.add(c);
                this.notEmpty.signal();
            }
        }
        finally {
            lock.unlock();
        }
    }

    private PooledConnection extractFromFreeList() {
        PooledConnection c = this.freeList.remove();
        this.registerBusyConnection(c);
        return c;
    }

    public PooledConnection getPooledConnection() throws SQLException {
        try {
            PooledConnection pc = this._getPooledConnection();
            pc.resetForUse();
            return pc;
        }
        catch (InterruptedException e) {
            String msg = "Interrupted getting connection from pool " + e;
            throw new SQLException(msg);
        }
    }

    private int registerBusyConnection(PooledConnection c) {
        int busySize = this.busyList.add(c);
        if (busySize > this.highWaterMark) {
            this.highWaterMark = busySize;
        }
        return busySize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PooledConnection _getPooledConnection() throws InterruptedException, SQLException {
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            if (this.doingShutdown) {
                throw new SQLException("Trying to access the Connection Pool when it is shutting down");
            }
            ++this.hitCount;
            if (this.waitingThreads == 0) {
                int freeSize = this.freeList.size();
                if (freeSize > 0) {
                    PooledConnection pooledConnection = this.extractFromFreeList();
                    return pooledConnection;
                }
                if (this.busyList.size() < this.maxSize) {
                    PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
                    int busySize = this.registerBusyConnection(c);
                    String msg = "DataSourcePool [" + this.name + "] grow; id[" + c.getName() + "] busy[" + busySize + "] max[" + this.maxSize + "]";
                    logger.info(msg);
                    this.checkForWarningSize();
                    PooledConnection pooledConnection = c;
                    return pooledConnection;
                }
            }
            try {
                ++this.waitCount;
                ++this.waitingThreads;
                PooledConnection pooledConnection = this._getPooledConnectionWaitLoop();
                --this.waitingThreads;
                return pooledConnection;
            }
            catch (Throwable throwable) {
                --this.waitingThreads;
                throw throwable;
            }
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * Exception decompiling
     */
    private PooledConnection _getPooledConnectionWaitLoop() throws SQLException, InterruptedException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[DOLOOP]], but top level block is 0[TRYBLOCK]
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:429)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:478)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:728)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:806)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException(Decompilation failed);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.doingShutdown = true;
            DataSourcePool.Status status = this.createStatus();
            logger.info("DataSourcePool [" + this.name + "] shutdown: " + status);
            this.closeFreeConnections(true);
            if (!this.busyList.isEmpty()) {
                String msg = "A potential connection leak was detected.  Busy connections: " + this.busyList.size();
                logger.warning(msg);
                this.dumpBusyConnectionInformation();
                this.closeBusyConnections(0L);
            }
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset(long leakTimeMinutes) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            DataSourcePool.Status status = this.createStatus();
            logger.info("Reseting DataSourcePool [" + this.name + "] " + status);
            this.lastResetTime = System.currentTimeMillis();
            this.closeFreeConnections(false);
            this.closeBusyConnections(leakTimeMinutes);
            String busyMsg = "Busy Connections:\r\n" + this.getBusyConnectionInformation();
            logger.info(busyMsg);
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void trim(int maxInactiveTimeSecs) throws SQLException {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.trimInactiveConnections(maxInactiveTimeSecs);
            this.ensureMinimumConnections();
        }
        finally {
            lock.unlock();
        }
    }

    private int trimInactiveConnections(int maxInactiveTimeSecs) {
        int maxTrim = this.freeList.size() - this.minSize;
        if (maxTrim <= 0) {
            return 0;
        }
        int trimedCount = 0;
        long usedSince = System.currentTimeMillis() - (long)(maxInactiveTimeSecs * 1000);
        List<PooledConnection> freeListCopy = this.freeList.getShallowCopy();
        Iterator<PooledConnection> it = freeListCopy.iterator();
        while (it.hasNext()) {
            PooledConnection pc = it.next();
            if (pc.getLastUsedTime() >= usedSince) continue;
            it.remove();
            pc.closeConnectionFully(true);
            if (++trimedCount < maxTrim) continue;
            break;
        }
        if (trimedCount > 0) {
            this.freeList.setShallowCopy(freeListCopy);
            String msg = "DataSourcePool [" + this.name + "] trimmed [" + trimedCount + "] inactive connections. New size[" + this.totalConnections() + "]";
            logger.info(msg);
        }
        return trimedCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closeFreeConnections(boolean logErrors) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            while (!this.freeList.isEmpty()) {
                PooledConnection c = this.freeList.remove();
                logger.info("PSTMT Statistics: " + c.getStatistics());
                c.closeConnectionFully(logErrors);
            }
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closeBusyConnections(long leakTimeMinutes) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            long olderThanTime = System.currentTimeMillis() - leakTimeMinutes * 60000L;
            List<PooledConnection> copy = this.busyList.getShallowCopy();
            for (int i = 0; i < copy.size(); ++i) {
                PooledConnection pc = copy.get(i);
                if (pc.isLongRunning() || pc.getLastUsedTime() > olderThanTime) continue;
                this.busyList.remove(pc);
                this.closeBusyConnection(pc);
            }
        }
        finally {
            lock.unlock();
        }
    }

    private void closeBusyConnection(PooledConnection pc) {
        try {
            String methodLine = pc.getCreatedByMethod();
            Date luDate = new Date();
            luDate.setTime(pc.getLastUsedTime());
            String msg = "DataSourcePool closing leaked connection?  name[" + pc.getName() + "] lastUsed[" + luDate + "] createdBy[" + methodLine + "] lastStmt[" + pc.getLastStatement() + "]";
            logger.warning(msg);
            this.logStackElement(pc, "Possible Leaked Connection: ");
            System.out.println("CLOSING BUSY CONNECTION ??? " + pc);
            pc.close();
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void logStackElement(PooledConnection pc, String prefix) {
        Object[] stackTrace = pc.getStackTrace();
        if (stackTrace != null) {
            String s = Arrays.toString(stackTrace);
            String msg = prefix + " name[" + pc.getName() + "] stackTrace: " + s;
            logger.warning(msg);
            System.err.println(msg);
        }
    }

    private void checkForWarningSize() {
        int availableGrowth = this.maxSize - this.totalConnections();
        if (availableGrowth < this.warningSize) {
            this.closeBusyConnections(this.leakTimeMinutes);
            String msg = "DataSourcePool [" + this.name + "] is [" + availableGrowth + "] connections from its maximum size.";
            this.pool.notifyWarning(msg);
        }
    }

    public String getBusyConnectionInformation() {
        return this.getBusyConnectionInformation(false);
    }

    public void dumpBusyConnectionInformation() {
        this.getBusyConnectionInformation(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getBusyConnectionInformation(boolean toLogger) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (toLogger) {
                logger.info("Dumping busy connections: (Use datasource.xxx.capturestacktrace=true  ... to get stackTraces)");
            }
            StringBuilder sb = new StringBuilder();
            List<PooledConnection> copy = this.busyList.getShallowCopy();
            for (int i = 0; i < copy.size(); ++i) {
                PooledConnection pc = copy.get(i);
                if (toLogger) {
                    logger.info(pc.getDescription());
                    this.logStackElement(pc, "Busy Connection: ");
                    continue;
                }
                sb.append(pc.getDescription()).append("\r\n");
            }
            String string = sb.toString();
            return string;
        }
        finally {
            lock.unlock();
        }
    }
}

