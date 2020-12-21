/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.util;

import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.FutureIds;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.Junction;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryListener;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.util.LuceneQueryList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultExpressionList<T>
implements SpiExpressionList<T> {
    private static final long serialVersionUID = -6992345500247035947L;
    private final ArrayList<SpiExpression> list = new ArrayList();
    private final Query<T> query;
    private final ExpressionList<T> parentExprList;
    private transient ExpressionFactory expr;
    private final String exprLang;
    private final String listAndStart;
    private final String listAndEnd;
    private final String listAndJoin;

    public DefaultExpressionList(Query<T> query, ExpressionList<T> parentExprList) {
        this(query, query.getExpressionFactory(), parentExprList);
    }

    public DefaultExpressionList(Query<T> query, ExpressionFactory expr, ExpressionList<T> parentExprList) {
        this.query = query;
        this.expr = expr;
        this.exprLang = expr.getLang();
        this.parentExprList = parentExprList;
        if ("ldap".equals(this.exprLang)) {
            this.listAndStart = "(&";
            this.listAndEnd = ")";
            this.listAndJoin = "";
        } else {
            this.listAndStart = "";
            this.listAndEnd = "";
            this.listAndJoin = " and ";
        }
    }

    @Override
    public void trimPath(int prefixTrim) {
        throw new RuntimeException("Only allowed on FilterExpressionList");
    }

    public List<SpiExpression> internalList() {
        return this.list;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (this.list.get(i).isLuceneResolvable(req)) continue;
            return false;
        }
        return true;
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, SpiLuceneExpr.ExprOccur occur) {
        LuceneQueryList queryList = new LuceneQueryList(occur);
        for (int i = 0; i < this.list.size(); ++i) {
            SpiLuceneExpr query = this.list.get(i).createLuceneExpr(request);
            queryList.add(query);
        }
        return queryList;
    }

    @Override
    public void setExpressionFactory(ExpressionFactory expr) {
        this.expr = expr;
    }

    public DefaultExpressionList<T> copy(Query<T> query) {
        DefaultExpressionList<T> copy = new DefaultExpressionList<T>(query, this.expr, null);
        copy.list.addAll(this.list);
        return copy;
    }

    @Override
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins whereManyJoins) {
        for (int i = 0; i < this.list.size(); ++i) {
            this.list.get(i).containsMany(desc, whereManyJoins);
        }
    }

    @Override
    public ExpressionList<T> endJunction() {
        return this.parentExprList == null ? this : this.parentExprList;
    }

    @Override
    public Query<T> query() {
        return this.query;
    }

    @Override
    public ExpressionList<T> where() {
        return this.query.where();
    }

    @Override
    public OrderBy<T> order() {
        return this.query.order();
    }

    @Override
    public OrderBy<T> orderBy() {
        return this.query.order();
    }

    @Override
    public Query<T> order(String orderByClause) {
        return this.query.order(orderByClause);
    }

    @Override
    public Query<T> orderBy(String orderBy) {
        return this.query.order(orderBy);
    }

    @Override
    public Query<T> setOrderBy(String orderBy) {
        return this.query.order(orderBy);
    }

    @Override
    public FutureIds<T> findFutureIds() {
        return this.query.findFutureIds();
    }

    @Override
    public FutureRowCount<T> findFutureRowCount() {
        return this.query.findFutureRowCount();
    }

    @Override
    public FutureList<T> findFutureList() {
        return this.query.findFutureList();
    }

    @Override
    public PagingList<T> findPagingList(int pageSize) {
        return this.query.findPagingList(pageSize);
    }

    @Override
    public int findRowCount() {
        return this.query.findRowCount();
    }

    @Override
    public List<Object> findIds() {
        return this.query.findIds();
    }

    @Override
    public void findVisit(QueryResultVisitor<T> visitor) {
        this.query.findVisit(visitor);
    }

    @Override
    public QueryIterator<T> findIterate() {
        return this.query.findIterate();
    }

    @Override
    public List<T> findList() {
        return this.query.findList();
    }

    @Override
    public Set<T> findSet() {
        return this.query.findSet();
    }

    @Override
    public Map<?, T> findMap() {
        return this.query.findMap();
    }

    @Override
    public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) {
        return this.query.findMap(keyProperty, keyType);
    }

    @Override
    public T findUnique() {
        return this.query.findUnique();
    }

    @Override
    public ExpressionList<T> filterMany(String prop) {
        return this.query.filterMany(prop);
    }

    @Override
    public Query<T> select(String fetchProperties) {
        return this.query.select(fetchProperties);
    }

    @Override
    public Query<T> join(String assocProperties) {
        return this.query.fetch(assocProperties);
    }

    @Override
    public Query<T> join(String assocProperty, String assocProperties) {
        return this.query.fetch(assocProperty, assocProperties);
    }

    @Override
    public Query<T> setFirstRow(int firstRow) {
        return this.query.setFirstRow(firstRow);
    }

    @Override
    public Query<T> setMaxRows(int maxRows) {
        return this.query.setMaxRows(maxRows);
    }

    @Override
    public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
        return this.query.setBackgroundFetchAfter(backgroundFetchAfter);
    }

    @Override
    public Query<T> setMapKey(String mapKey) {
        return this.query.setMapKey(mapKey);
    }

    @Override
    public Query<T> setListener(QueryListener<T> queryListener) {
        return this.query.setListener(queryListener);
    }

    @Override
    public Query<T> setUseCache(boolean useCache) {
        return this.query.setUseCache(useCache);
    }

    @Override
    public ExpressionList<T> having() {
        return this.query.having();
    }

    @Override
    public ExpressionList<T> add(Expression expr) {
        this.list.add((SpiExpression)expr);
        return this;
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public String buildSql(SpiExpressionRequest request) {
        request.append(this.listAndStart);
        int size = this.list.size();
        for (int i = 0; i < size; ++i) {
            SpiExpression expression = this.list.get(i);
            if (i > 0) {
                request.append(this.listAndJoin);
            }
            expression.addSql(request);
        }
        request.append(this.listAndEnd);
        return request.getSql();
    }

    @Override
    public ArrayList<Object> buildBindValues(SpiExpressionRequest request) {
        int size = this.list.size();
        for (int i = 0; i < size; ++i) {
            SpiExpression expression = this.list.get(i);
            expression.addBindValues(request);
        }
        return request.getBindValues();
    }

    public int queryAutoFetchHash() {
        int hash = DefaultExpressionList.class.getName().hashCode();
        int size = this.list.size();
        for (int i = 0; i < size; ++i) {
            SpiExpression expression = this.list.get(i);
            hash = hash * 31 + expression.queryAutoFetchHash();
        }
        return hash;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        int hash = DefaultExpressionList.class.getName().hashCode();
        int size = this.list.size();
        for (int i = 0; i < size; ++i) {
            SpiExpression expression = this.list.get(i);
            hash = hash * 31 + expression.queryPlanHash(request);
        }
        return hash;
    }

    public int queryBindHash() {
        int hash = DefaultExpressionList.class.getName().hashCode();
        int size = this.list.size();
        for (int i = 0; i < size; ++i) {
            SpiExpression expression = this.list.get(i);
            hash = hash * 31 + expression.queryBindHash();
        }
        return hash;
    }

    @Override
    public ExpressionList<T> eq(String propertyName, Object value) {
        this.add(this.expr.eq(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> ieq(String propertyName, String value) {
        this.add(this.expr.ieq(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> ne(String propertyName, Object value) {
        this.add(this.expr.ne(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> allEq(Map<String, Object> propertyMap) {
        this.add(this.expr.allEq(propertyMap));
        return this;
    }

    @Override
    public ExpressionList<T> and(Expression expOne, Expression expTwo) {
        this.add(this.expr.and(expOne, expTwo));
        return this;
    }

    @Override
    public ExpressionList<T> between(String propertyName, Object value1, Object value2) {
        this.add(this.expr.between(propertyName, value1, value2));
        return this;
    }

    @Override
    public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) {
        this.add(this.expr.betweenProperties(lowProperty, highProperty, value));
        return this;
    }

    @Override
    public Junction<T> conjunction() {
        Junction<T> conjunction = this.expr.conjunction(this.query, this);
        this.add(conjunction);
        return conjunction;
    }

    @Override
    public ExpressionList<T> contains(String propertyName, String value) {
        this.add(this.expr.contains(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> lucene(String propertyName, String value) {
        this.add(this.expr.lucene(propertyName, value));
        return this;
    }

    @Override
    public Junction<T> disjunction() {
        Junction<T> disjunction = this.expr.disjunction(this.query, this);
        this.add(disjunction);
        return disjunction;
    }

    @Override
    public ExpressionList<T> endsWith(String propertyName, String value) {
        this.add(this.expr.endsWith(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> ge(String propertyName, Object value) {
        this.add(this.expr.ge(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> gt(String propertyName, Object value) {
        this.add(this.expr.gt(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> icontains(String propertyName, String value) {
        this.add(this.expr.icontains(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> idIn(List<?> idList) {
        this.add(this.expr.idIn(idList));
        return this;
    }

    @Override
    public ExpressionList<T> idEq(Object value) {
        this.add(this.expr.idEq(value));
        return this;
    }

    @Override
    public ExpressionList<T> iendsWith(String propertyName, String value) {
        this.add(this.expr.iendsWith(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> ilike(String propertyName, String value) {
        this.add(this.expr.ilike(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> in(String propertyName, Query<?> subQuery) {
        this.add(this.expr.in(propertyName, subQuery));
        return this;
    }

    @Override
    public ExpressionList<T> in(String propertyName, Collection<?> values) {
        this.add(this.expr.in(propertyName, values));
        return this;
    }

    @Override
    public ExpressionList<T> in(String propertyName, Object ... values) {
        this.add(this.expr.in(propertyName, values));
        return this;
    }

    @Override
    public ExpressionList<T> isNotNull(String propertyName) {
        this.add(this.expr.isNotNull(propertyName));
        return this;
    }

    @Override
    public ExpressionList<T> isNull(String propertyName) {
        this.add(this.expr.isNull(propertyName));
        return this;
    }

    @Override
    public ExpressionList<T> istartsWith(String propertyName, String value) {
        this.add(this.expr.istartsWith(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> le(String propertyName, Object value) {
        this.add(this.expr.le(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> exampleLike(Object example) {
        this.add(this.expr.exampleLike(example));
        return this;
    }

    @Override
    public ExpressionList<T> iexampleLike(Object example) {
        this.add(this.expr.iexampleLike(example));
        return this;
    }

    @Override
    public ExpressionList<T> like(String propertyName, String value) {
        this.add(this.expr.like(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> lt(String propertyName, Object value) {
        this.add(this.expr.lt(propertyName, value));
        return this;
    }

    @Override
    public ExpressionList<T> not(Expression exp) {
        this.add(this.expr.not(exp));
        return this;
    }

    @Override
    public ExpressionList<T> or(Expression expOne, Expression expTwo) {
        this.add(this.expr.or(expOne, expTwo));
        return this;
    }

    @Override
    public ExpressionList<T> raw(String raw, Object value) {
        this.add(this.expr.raw(raw, value));
        return this;
    }

    @Override
    public ExpressionList<T> raw(String raw, Object[] values) {
        this.add(this.expr.raw(raw, values));
        return this;
    }

    @Override
    public ExpressionList<T> raw(String raw) {
        this.add(this.expr.raw(raw));
        return this;
    }

    @Override
    public ExpressionList<T> startsWith(String propertyName, String value) {
        this.add(this.expr.startsWith(propertyName, value));
        return this;
    }
}

