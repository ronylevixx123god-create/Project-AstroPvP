/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.LogLevel;
import java.sql.Connection;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

public interface Transaction {
    public static final int READ_COMMITTED = 2;
    public static final int READ_UNCOMMITTED = 1;
    public static final int REPEATABLE_READ = 4;
    public static final int SERIALIZABLE = 8;

    public void waitForIndexUpdates();

    public boolean isReadOnly();

    public void setReadOnly(boolean var1);

    public void log(String var1);

    public void setLogLevel(LogLevel var1);

    public LogLevel getLogLevel();

    public void setLoggingOn(boolean var1);

    public void commit() throws RollbackException;

    public void rollback() throws PersistenceException;

    public void rollback(Throwable var1) throws PersistenceException;

    public void end() throws PersistenceException;

    public boolean isActive();

    public void setPersistCascade(boolean var1);

    public void setBatchMode(boolean var1);

    public void setBatchSize(int var1);

    public void setBatchGetGeneratedKeys(boolean var1);

    public void setBatchFlushOnMixed(boolean var1);

    public void setBatchFlushOnQuery(boolean var1);

    public boolean isBatchFlushOnQuery();

    public void flushBatch() throws PersistenceException, OptimisticLockException;

    public void batchFlush() throws PersistenceException, OptimisticLockException;

    public Connection getConnection();

    public void addModification(String var1, boolean var2, boolean var3, boolean var4);
}

