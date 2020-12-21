/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import java.sql.Connection;

public interface SpiTransaction
extends Transaction {
    public boolean isLogSql();

    public boolean isLogSummary();

    public void logInternal(String var1);

    public void addIndexUpdateFuture(LIndexUpdateFuture var1);

    public TransactionLogBuffer getLogBuffer();

    public void registerBean(Integer var1);

    public void unregisterBean(Integer var1);

    public boolean isRegisteredBean(Integer var1);

    public String getId();

    public int getBatchSize();

    public int depth(int var1);

    public boolean isExplicit();

    public TransactionEvent getEvent();

    public boolean isPersistCascade();

    public boolean isBatchThisRequest();

    public BatchControl getBatchControl();

    public void setBatchControl(BatchControl var1);

    public PersistenceContext getPersistenceContext();

    public void setPersistenceContext(PersistenceContext var1);

    public Connection getInternalConnection();
}

