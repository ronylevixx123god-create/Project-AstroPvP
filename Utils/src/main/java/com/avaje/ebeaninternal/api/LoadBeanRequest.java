/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.api.LoadBeanContext;
import com.avaje.ebeaninternal.api.LoadRequest;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LoadBeanRequest
extends LoadRequest {
    private final List<EntityBeanIntercept> batch;
    private final LoadBeanContext loadContext;
    private final String lazyLoadProperty;

    public LoadBeanRequest(LoadBeanContext loadContext, List<EntityBeanIntercept> batch, Transaction transaction, int batchSize, boolean lazy, String lazyLoadProperty) {
        super(transaction, batchSize, lazy);
        this.loadContext = loadContext;
        this.batch = batch;
        this.lazyLoadProperty = lazyLoadProperty;
    }

    public String getDescription() {
        String fullPath = this.loadContext.getFullPath();
        String s = "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
        return s;
    }

    public List<EntityBeanIntercept> getBatch() {
        return this.batch;
    }

    public LoadBeanContext getLoadContext() {
        return this.loadContext;
    }

    public String getLazyLoadProperty() {
        return this.lazyLoadProperty;
    }
}

