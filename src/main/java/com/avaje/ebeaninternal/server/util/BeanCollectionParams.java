/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.util;

import com.avaje.ebean.Query;

public class BeanCollectionParams {
    private final Query.Type manyType;

    public BeanCollectionParams(Query.Type manyType) {
        this.manyType = manyType;
    }

    public Query.Type getManyType() {
        return this.manyType;
    }
}

