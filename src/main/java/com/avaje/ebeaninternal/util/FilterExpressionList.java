/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.util;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.FutureIds;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryListener;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.util.DefaultExpressionList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FilterExpressionList<T>
extends DefaultExpressionList<T> {
    private static final long serialVersionUID = 2226895827150099020L;
    private final Query<T> rootQuery;
    private final FilterExprPath pathPrefix;
    private String notAllowedMessage = "This method is not allowed on a filter";

    public FilterExpressionList(FilterExprPath pathPrefix, ExpressionFactory expr, Query<T> rootQuery) {
        super(null, expr, null);
        this.pathPrefix = pathPrefix;
        this.rootQuery = rootQuery;
    }

    @Override
    public void trimPath(int prefixTrim) {
        this.pathPrefix.trimPath(prefixTrim);
    }

    public FilterExprPath getPathPrefix() {
        return this.pathPrefix;
    }

    @Override
    public ExpressionList<T> filterMany(String prop) {
        return this.rootQuery.filterMany(prop);
    }

    @Override
    public FutureIds<T> findFutureIds() {
        return this.rootQuery.findFutureIds();
    }

    @Override
    public FutureList<T> findFutureList() {
        return this.rootQuery.findFutureList();
    }

    @Override
    public FutureRowCount<T> findFutureRowCount() {
        return this.rootQuery.findFutureRowCount();
    }

    @Override
    public List<T> findList() {
        return this.rootQuery.findList();
    }

    @Override
    public Map<?, T> findMap() {
        return this.rootQuery.findMap();
    }

    @Override
    public PagingList<T> findPagingList(int pageSize) {
        return this.rootQuery.findPagingList(pageSize);
    }

    @Override
    public int findRowCount() {
        return this.rootQuery.findRowCount();
    }

    @Override
    public Set<T> findSet() {
        return this.rootQuery.findSet();
    }

    @Override
    public T findUnique() {
        return this.rootQuery.findUnique();
    }

    @Override
    public ExpressionList<T> having() {
        throw new PersistenceException(this.notAllowedMessage);
    }

    @Override
    public ExpressionList<T> idEq(Object value) {
        throw new PersistenceException(this.notAllowedMessage);
    }

    @Override
    public ExpressionList<T> idIn(List<?> idValues) {
        throw new PersistenceException(this.notAllowedMessage);
    }

    @Override
    public Query<T> join(String assocProperty, String assocProperties) {
        throw new PersistenceException(this.notAllowedMessage);
    }

    @Override
    public Query<T> join(String assocProperties) {
        throw new PersistenceException(this.notAllowedMessage);
    }

    @Override
    public OrderBy<T> order() {
        return this.rootQuery.order();
    }

    @Override
    public Query<T> order(String orderByClause) {
        return this.rootQuery.order(orderByClause);
    }

    @Override
    public Query<T> orderBy(String orderBy) {
        return this.rootQuery.orderBy(orderBy);
    }

    @Override
    public Query<T> query() {
        return this.rootQuery;
    }

    @Override
    public Query<T> select(String properties) {
        throw new PersistenceException(this.notAllowedMessage);
    }

    @Override
    public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
        return this.rootQuery.setBackgroundFetchAfter(backgroundFetchAfter);
    }

    @Override
    public Query<T> setFirstRow(int firstRow) {
        return this.rootQuery.setFirstRow(firstRow);
    }

    @Override
    public Query<T> setListener(QueryListener<T> queryListener) {
        return this.rootQuery.setListener(queryListener);
    }

    @Override
    public Query<T> setMapKey(String mapKey) {
        return this.rootQuery.setMapKey(mapKey);
    }

    @Override
    public Query<T> setMaxRows(int maxRows) {
        return this.rootQuery.setMaxRows(maxRows);
    }

    @Override
    public Query<T> setUseCache(boolean useCache) {
        return this.rootQuery.setUseCache(useCache);
    }

    @Override
    public ExpressionList<T> where() {
        return this.rootQuery.where();
    }
}

