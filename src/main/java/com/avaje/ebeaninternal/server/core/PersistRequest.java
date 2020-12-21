/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.BeanRequest;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.persist.BatchPostExecute;
import com.avaje.ebeaninternal.server.persist.PersistExecute;

public abstract class PersistRequest
extends BeanRequest
implements BatchPostExecute {
    boolean persistCascade;
    Type type;
    final PersistExecute persistExecute;

    public PersistRequest(SpiEbeanServer server, SpiTransaction t, PersistExecute persistExecute) {
        super(server, t);
        this.persistExecute = persistExecute;
    }

    public abstract int executeOrQueue();

    public abstract int executeNow();

    public PstmtBatch getPstmtBatch() {
        return this.ebeanServer.getPstmtBatch();
    }

    public boolean isLogSql() {
        return this.transaction.isLogSql();
    }

    public boolean isLogSummary() {
        return this.transaction.isLogSummary();
    }

    public int executeStatement() {
        int rows;
        boolean batch = this.transaction.isBatchThisRequest();
        BatchControl control = this.transaction.getBatchControl();
        if (control != null) {
            rows = control.executeStatementOrBatch(this, batch);
        } else if (batch) {
            control = this.persistExecute.createBatchControl(this.transaction);
            rows = control.executeStatementOrBatch(this, batch);
        } else {
            rows = this.executeNow();
        }
        return rows;
    }

    public void initTransIfRequired() {
        this.createImplicitTransIfRequired(false);
        this.persistCascade = this.transaction.isPersistCascade();
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isPersistCascade() {
        return this.persistCascade;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Type {
        INSERT,
        UPDATE,
        DELETE,
        ORMUPDATE,
        UPDATESQL,
        CALLABLESQL;

    }
}

