/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.Expression;
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
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.expression.JunctionExpressionLucene;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
abstract class JunctionExpression<T>
implements Junction<T>,
SpiExpression,
ExpressionList<T> {
    private static final long serialVersionUID = -7422204102750462676L;
    private static final String OR = " or ";
    private static final String AND = " and ";
    private final DefaultExpressionList<T> exprList;
    private final String joinType;

    JunctionExpression(String joinType, Query<T> query, ExpressionList<T> parent) {
        this.joinType = joinType;
        this.exprList = new DefaultExpressionList<T>(query, parent);
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isLuceneResolvable(req)) continue;
            return false;
        }
        return true;
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        boolean disjunction = OR.equals(this.joinType);
        return new JunctionExpressionLucene().createLuceneExpr(request, this.exprList.internalList(), disjunction);
    }

    @Override
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
        List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            list.get(i).containsMany(desc, manyWhereJoin);
        }
    }

    @Override
    public Junction<T> add(Expression item) {
        SpiExpression i = (SpiExpression)item;
        this.exprList.add(i);
        return this;
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            SpiExpression item = list.get(i);
            item.addBindValues(request);
        }
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        List<SpiExpression> list = this.exprList.internalList();
        if (!list.isEmpty()) {
            request.append("(");
            for (int i = 0; i < list.size(); ++i) {
                SpiExpression item = list.get(i);
                if (i > 0) {
                    request.append(this.joinType);
                }
                item.addSql(request);
            }
            request.append(") ");
        }
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = JunctionExpression.class.getName().hashCode();
        hc = hc * 31 + this.joinType.hashCode();
        List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            hc = hc * 31 + list.get(i).queryAutoFetchHash();
        }
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        int hc = JunctionExpression.class.getName().hashCode();
        hc = hc * 31 + this.joinType.hashCode();
        List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            hc = hc * 31 + list.get(i).queryPlanHash(request);
        }
        return hc;
    }

    @Override
    public int queryBindHash() {
        int hc = JunctionExpression.class.getName().hashCode();
        List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            hc = hc * 31 + list.get(i).queryBindHash();
        }
        return hc;
    }

    @Override
    public ExpressionList<T> endJunction() {
        return this.exprList.endJunction();
    }

    @Override
    public ExpressionList<T> allEq(Map<String, Object> propertyMap) {
        return this.exprList.allEq(propertyMap);
    }

    @Override
    public ExpressionList<T> and(Expression expOne, Expression expTwo) {
        return this.exprList.and(expOne, expTwo);
    }

    @Override
    public ExpressionList<T> between(String propertyName, Object value1, Object value2) {
        return this.exprList.between(propertyName, value1, value2);
    }

    @Override
    public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) {
        return this.exprList.betweenProperties(lowProperty, highProperty, value);
    }

    @Override
    public Junction<T> conjunction() {
        return this.exprList.conjunction();
    }

    @Override
    public ExpressionList<T> contains(String propertyName, String value) {
        return this.exprList.contains(propertyName, value);
    }

    @Override
    public Junction<T> disjunction() {
        return this.exprList.disjunction();
    }

    @Override
    public ExpressionList<T> endsWith(String propertyName, String value) {
        return this.exprList.endsWith(propertyName, value);
    }

    @Override
    public ExpressionList<T> eq(String propertyName, Object value) {
        return this.exprList.eq(propertyName, value);
    }

    @Override
    public ExpressionList<T> exampleLike(Object example) {
        return this.exprList.exampleLike(example);
    }

    @Override
    public ExpressionList<T> filterMany(String prop) {
        throw new RuntimeException("filterMany not allowed on Junction expression list");
    }

    @Override
    public FutureIds<T> findFutureIds() {
        return this.exprList.findFutureIds();
    }

    @Override
    public FutureList<T> findFutureList() {
        return this.exprList.findFutureList();
    }

    @Override
    public FutureRowCount<T> findFutureRowCount() {
        return this.exprList.findFutureRowCount();
    }

    @Override
    public List<Object> findIds() {
        return this.exprList.findIds();
    }

    @Override
    public void findVisit(QueryResultVisitor<T> visitor) {
        this.exprList.findVisit(visitor);
    }

    @Override
    public QueryIterator<T> findIterate() {
        return this.exprList.findIterate();
    }

    @Override
    public List<T> findList() {
        return this.exprList.findList();
    }

    @Override
    public Map<?, T> findMap() {
        return this.exprList.findMap();
    }

    @Override
    public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) {
        return this.exprList.findMap(keyProperty, keyType);
    }

    @Override
    public PagingList<T> findPagingList(int pageSize) {
        return this.exprList.findPagingList(pageSize);
    }

    @Override
    public int findRowCount() {
        return this.exprList.findRowCount();
    }

    @Override
    public Set<T> findSet() {
        return this.exprList.findSet();
    }

    @Override
    public T findUnique() {
        return this.exprList.findUnique();
    }

    @Override
    public ExpressionList<T> ge(String propertyName, Object value) {
        return this.exprList.ge(propertyName, value);
    }

    @Override
    public ExpressionList<T> gt(String propertyName, Object value) {
        return this.exprList.gt(propertyName, value);
    }

    @Override
    public ExpressionList<T> having() {
        throw new RuntimeException("having() not allowed on Junction expression list");
    }

    @Override
    public ExpressionList<T> icontains(String propertyName, String value) {
        return this.exprList.icontains(propertyName, value);
    }

    @Override
    public ExpressionList<T> idEq(Object value) {
        return this.exprList.idEq(value);
    }

    @Override
    public ExpressionList<T> idIn(List<?> idValues) {
        return this.exprList.idIn(idValues);
    }

    @Override
    public ExpressionList<T> iendsWith(String propertyName, String value) {
        return this.exprList.iendsWith(propertyName, value);
    }

    @Override
    public ExpressionList<T> ieq(String propertyName, String value) {
        return this.exprList.ieq(propertyName, value);
    }

    @Override
    public ExpressionList<T> iexampleLike(Object example) {
        return this.exprList.iexampleLike(example);
    }

    @Override
    public ExpressionList<T> ilike(String propertyName, String value) {
        return this.exprList.ilike(propertyName, value);
    }

    @Override
    public ExpressionList<T> in(String propertyName, Collection<?> values) {
        return this.exprList.in(propertyName, values);
    }

    @Override
    public ExpressionList<T> in(String propertyName, Object ... values) {
        return this.exprList.in(propertyName, values);
    }

    @Override
    public ExpressionList<T> in(String propertyName, Query<?> subQuery) {
        return this.exprList.in(propertyName, subQuery);
    }

    @Override
    public ExpressionList<T> isNotNull(String propertyName) {
        return this.exprList.isNotNull(propertyName);
    }

    @Override
    public ExpressionList<T> isNull(String propertyName) {
        return this.exprList.isNull(propertyName);
    }

    @Override
    public ExpressionList<T> istartsWith(String propertyName, String value) {
        return this.exprList.istartsWith(propertyName, value);
    }

    @Override
    public Query<T> join(String assocProperty, String assocProperties) {
        return this.exprList.join(assocProperty, assocProperties);
    }

    @Override
    public Query<T> join(String assocProperties) {
        return this.exprList.join(assocProperties);
    }

    @Override
    public ExpressionList<T> le(String propertyName, Object value) {
        return this.exprList.le(propertyName, value);
    }

    @Override
    public ExpressionList<T> like(String propertyName, String value) {
        return this.exprList.like(propertyName, value);
    }

    @Override
    public ExpressionList<T> lt(String propertyName, Object value) {
        return this.exprList.lt(propertyName, value);
    }

    @Override
    public ExpressionList<T> lucene(String propertyName, String value) {
        return this.exprList.lucene(propertyName, value);
    }

    @Override
    public ExpressionList<T> ne(String propertyName, Object value) {
        return this.exprList.ne(propertyName, value);
    }

    @Override
    public ExpressionList<T> not(Expression exp) {
        return this.exprList.not(exp);
    }

    @Override
    public ExpressionList<T> or(Expression expOne, Expression expTwo) {
        return this.exprList.or(expOne, expTwo);
    }

    @Override
    public OrderBy<T> order() {
        return this.exprList.order();
    }

    @Override
    public Query<T> order(String orderByClause) {
        return this.exprList.order(orderByClause);
    }

    @Override
    public OrderBy<T> orderBy() {
        return this.exprList.orderBy();
    }

    @Override
    public Query<T> orderBy(String orderBy) {
        return this.exprList.orderBy(orderBy);
    }

    @Override
    public Query<T> query() {
        return this.exprList.query();
    }

    @Override
    public ExpressionList<T> raw(String raw, Object value) {
        return this.exprList.raw(raw, value);
    }

    @Override
    public ExpressionList<T> raw(String raw, Object[] values) {
        return this.exprList.raw(raw, values);
    }

    @Override
    public ExpressionList<T> raw(String raw) {
        return this.exprList.raw(raw);
    }

    @Override
    public Query<T> select(String properties) {
        return this.exprList.select(properties);
    }

    @Override
    public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
        return this.exprList.setBackgroundFetchAfter(backgroundFetchAfter);
    }

    @Override
    public Query<T> setFirstRow(int firstRow) {
        return this.exprList.setFirstRow(firstRow);
    }

    @Override
    public Query<T> setListener(QueryListener<T> queryListener) {
        return this.exprList.setListener(queryListener);
    }

    @Override
    public Query<T> setMapKey(String mapKey) {
        return this.exprList.setMapKey(mapKey);
    }

    @Override
    public Query<T> setMaxRows(int maxRows) {
        return this.exprList.setMaxRows(maxRows);
    }

    @Override
    public Query<T> setOrderBy(String orderBy) {
        return this.exprList.setOrderBy(orderBy);
    }

    @Override
    public Query<T> setUseCache(boolean useCache) {
        return this.exprList.setUseCache(useCache);
    }

    @Override
    public ExpressionList<T> startsWith(String propertyName, String value) {
        return this.exprList.startsWith(propertyName, value);
    }

    @Override
    public ExpressionList<T> where() {
        return this.exprList.where();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class Disjunction<T>
    extends JunctionExpression<T> {
        private static final long serialVersionUID = -8464470066692221413L;

        Disjunction(Query<T> query, ExpressionList<T> parent) {
            super(JunctionExpression.OR, query, parent);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class Conjunction<T>
    extends JunctionExpression<T> {
        private static final long serialVersionUID = -645619859900030678L;

        Conjunction(Query<T> query, ExpressionList<T> parent) {
            super(JunctionExpression.AND, query, parent);
        }
    }
}

