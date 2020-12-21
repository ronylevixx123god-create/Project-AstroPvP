/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiOrmQueryRequest<T> {
    public SpiQuery<T> getQuery();

    public BeanDescriptor<?> getBeanDescriptor();

    public void initTransIfRequired();

    public void endTransIfRequired();

    public void rollbackTransIfRequired();

    public Object findId();

    public int findRowCount();

    public List<Object> findIds();

    public void findVisit(QueryResultVisitor<T> var1);

    public QueryIterator<T> findIterate();

    public List<T> findList();

    public Set<?> findSet();

    public Map<?, ?> findMap();

    public T getFromPersistenceContextOrCache();

    public BeanCollection<T> getFromQueryCache();
}

