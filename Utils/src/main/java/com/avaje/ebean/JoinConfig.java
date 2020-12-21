/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

public class JoinConfig {
    private int lazyBatchSize = -1;
    private int queryBatchSize = -1;
    private boolean queryAll;

    public JoinConfig lazy() {
        this.lazyBatchSize = 0;
        return this;
    }

    public JoinConfig lazy(int lazyBatchSize) {
        this.lazyBatchSize = lazyBatchSize;
        return this;
    }

    public JoinConfig query() {
        this.queryBatchSize = 0;
        this.queryAll = true;
        return this;
    }

    public JoinConfig query(int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
        this.queryAll = true;
        return this;
    }

    public JoinConfig queryFirst(int queryBatchSize) {
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

