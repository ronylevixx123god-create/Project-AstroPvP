/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

public class FetchConfig {
    private int lazyBatchSize = -1;
    private int queryBatchSize = -1;
    private boolean queryAll;

    public FetchConfig lazy() {
        this.lazyBatchSize = 0;
        return this;
    }

    public FetchConfig lazy(int lazyBatchSize) {
        this.lazyBatchSize = lazyBatchSize;
        return this;
    }

    public FetchConfig query() {
        this.queryBatchSize = 0;
        this.queryAll = true;
        return this;
    }

    public FetchConfig query(int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
        this.queryAll = true;
        return this;
    }

    public FetchConfig queryFirst(int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
        this.queryAll = false;
        return this;
    }

    public int getLazyBatchSize() {
        return this.lazyBatchSize;
    }

    public int getQueryBatchSize() {
        return this.queryBatchSize;
    }

    public boolean isQueryAll() {
        return this.queryAll;
    }
}

