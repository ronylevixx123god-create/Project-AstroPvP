/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.LoadManyContext;
import com.avaje.ebeaninternal.api.LoadRequest;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LoadManyRequest
extends LoadRequest {
    private final List<BeanCollection<?>> batch;
    private final LoadManyContext loadContext;
    private final boolean onlyIds;

    public LoadManyRequest(LoadManyContext loadContext, List<BeanCollection<?>> batch, Transaction transaction, int batchSize, boolean lazy, boolean onlyIds) {
        super(transaction, batchSize, lazy);
        this.loadContext = loadContext;
        this.batch = batch;
        this.onlyIds = onlyIds;
    }

    public String getDescription() {
        String fullPath = this.loadContext.getFullPath();
        String s = "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
        return s;
    }

    public List<BeanCollection<?>> getBatch() {
        return this.batch;
    }

    public LoadManyContext getLoadContext() {
        return this.loadContext;
    }

    public boolean isOnlyIds() {
        return this.onlyIds;
    }
}

