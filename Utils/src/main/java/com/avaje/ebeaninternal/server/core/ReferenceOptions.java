/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

public class ReferenceOptions {
    private final boolean readOnly;
    private final boolean useCache;
    private final String warmingQuery;

    public ReferenceOptions(boolean useCache, boolean readOnly, String warmingQuery) {
        this.useCache = useCache;
        this.readOnly = readOnly;
        this.warmingQuery = warmingQuery;
    }

    public boolean isUseCache() {
        return this.useCache;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public String getWarmingQuery() {
        return this.warmingQuery;
    }
}

