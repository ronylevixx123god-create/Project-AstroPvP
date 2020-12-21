/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.lucene.IndexUpdates;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;

public final class LIndexWork {
    private final WorkType workType;
    private final LIndexUpdateFuture future;
    private final IndexUpdates indexUpdates;

    public static LIndexWork newRebuild(LIndexUpdateFuture future) {
        return new LIndexWork(WorkType.REBUILD, future, null);
    }

    public static LIndexWork newQueryUpdate(LIndexUpdateFuture future) {
        return new LIndexWork(WorkType.QUERY_UPDATE, future, null);
    }

    public static LIndexWork newTxnUpdate(LIndexUpdateFuture future, IndexUpdates indexUpdates) {
        return new LIndexWork(WorkType.TXN_UPDATE, future, indexUpdates);
    }

    private LIndexWork(WorkType workType, LIndexUpdateFuture future, IndexUpdates indexUpdates) {
        this.workType = workType;
        this.future = future;
        this.indexUpdates = indexUpdates;
    }

    public WorkType getWorkType() {
        return this.workType;
    }

    public IndexUpdates getIndexUpdates() {
        return this.indexUpdates;
    }

    public LIndexUpdateFuture getFuture() {
        return this.future;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum WorkType {
        TXN_UPDATE,
        QUERY_UPDATE,
        REBUILD;

    }
}

