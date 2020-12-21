/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.Expression;
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
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ExpressionList<T>
extends Serializable {
    public Query<T> query();

    public Query<T> order(String var1);

    public OrderBy<T> order();

    public OrderBy<T> orderBy();

    public Query<T> orderBy(String var1);

    public Query<T> setOrderBy(String var1);

    public QueryIterator<T> findIterate();

    public void findVisit(QueryResultVisitor<T> var1);

    public List<T> findList();

    public List<Object> findIds();

    public int findRowCount();

    public Set<T> findSet();

    public Map<?, T> findMap();

    public <K> Map<K, T> findMap(String var1, Class<K> var2);

    public T findUnique();

    public FutureRowCount<T> findFutureRowCount();

    public FutureIds<T> findFutureIds();

    public FutureList<T> findFutureList();

    public PagingList<T> findPagingList(int var1);

    public ExpressionList<T> filterMany(String var1);

    public Query<T> select(String var1);

    public Query<T> join(String var1);

    public Query<T> join(String var1, String var2);

    public Query<T> setFirstRow(int var1);

    public Query<T> setMaxRows(int var1);

    public Query<T> setBackgroundFetchAfter(int var1);

    public Query<T> setMapKey(String var1);

    public Query<T> setListener(QueryListener<T> var1);

    public Query<T> setUseCache(boolean var1);

    public ExpressionList<T> having();

    public ExpressionList<T> where();

    public ExpressionList<T> add(Expression var1);

    public ExpressionList<T> eq(String var1, Object var2);

    public ExpressionList<T> ne(String var1, Object var2);

    public ExpressionList<T> ieq(String var1, String var2);

    public ExpressionList<T> between(String var1, Object var2, Object var3);

    public ExpressionList<T> betweenProperties(String var1, String var2, Object var3);

    public ExpressionList<T> gt(String var1, Object var2);

    public ExpressionList<T> ge(String var1, Object var2);

    public ExpressionList<T> lt(String var1, Object var2);

    public ExpressionList<T> le(String var1, Object var2);

    public ExpressionList<T> isNull(String var1);

    public ExpressionList<T> isNotNull(String var1);

    public ExpressionList<T> exampleLike(Object var1);

    public ExpressionList<T> iexampleLike(Object var1);

    public ExpressionList<T> like(String var1, String var2);

    public ExpressionList<T> ilike(String var1, String var2);

    public ExpressionList<T> startsWith(String var1, String var2);

    public ExpressionList<T> istartsWith(String var1, String var2);

    public ExpressionList<T> endsWith(String var1, String var2);

    public ExpressionList<T> iendsWith(String var1, String var2);

    public ExpressionList<T> contains(String var1, String var2);

    public ExpressionList<T> lucene(String var1, String var2);

    public ExpressionList<T> icontains(String var1, String var2);

    public ExpressionList<T> in(String var1, Query<?> var2);

    public ExpressionList<T> in(String var1, Object ... var2);

    public ExpressionList<T> in(String var1, Collection<?> var2);

    public ExpressionList<T> idIn(List<?> var1);

    public ExpressionList<T> idEq(Object var1);

    public ExpressionList<T> allEq(Map<String, Object> var1);

    public ExpressionList<T> raw(String var1, Object var2);

    public ExpressionList<T> raw(String var1, Object[] var2);

    public ExpressionList<T> raw(String var1);

    public ExpressionList<T> and(Expression var1, Expression var2);

    public ExpressionList<T> or(Expression var1, Expression var2);

    public ExpressionList<T> not(Expression var1);

    public Junction<T> conjunction();

    public Junction<T> disjunction();

    public ExpressionList<T> endJunction();
}

