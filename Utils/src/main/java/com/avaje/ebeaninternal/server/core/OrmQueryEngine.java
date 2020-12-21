/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface OrmQueryEngine {
    public <T> T findId(OrmQueryRequest<T> var1);

    public <T> BeanCollection<T> findMany(OrmQueryRequest<T> var1);

    public <T> QueryIterator<T> findIterate(OrmQueryRequest<T> var1);

    public <T> int findRowCount(OrmQueryRequest<T> var1);

    public <T> BeanIdList findIds(OrmQueryRequest<T> var1);
}

