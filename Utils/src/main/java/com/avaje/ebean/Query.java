/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.FetchConfig;
import com.avaje.ebean.FutureIds;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.JoinConfig;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryListener;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.RawSql;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Query<T>
extends Serializable {
    public Query<T> setUseIndex(UseIndex var1);

    public UseIndex getUseIndex();

    public Type getType();

    public RawSql getRawSql();

    public Query<T> setRawSql(RawSql var1);

    public void cancel();

    public Query<T> copy();

    public ExpressionFactory getExpressionFactory();

    public boolean isAutofetchTuned();

    public Query<T> setAutofetch(boolean var1);

    public Query<T> setQuery(String var1);

    public Query<T> select(String var1);

    public Query<T> fetch(String var1, String var2);

    public Query<T> join(String var1, String var2);

    public Query<T> fetch(String var1, String var2, FetchConfig var3);

    public Query<T> join(String var1, String var2, JoinConfig var3);

    public Query<T> fetch(String var1);

    public Query<T> join(String var1);

    public Query<T> fetch(String var1, FetchConfig var2);

    public Query<T> join(String var1, JoinConfig var2);

    public List<Object> findIds();

    public QueryIterator<T> findIterate();

    public void findVisit(QueryResultVisitor<T> var1);

    public List<T> findList();

    public Set<T> findSet();

    public Map<?, T> findMap();

    public <K> Map<K, T> findMap(String var1, Class<K> var2);

    public T findUnique();

    public int findRowCount();

    public FutureRowCount<T> findFutureRowCount();

    public FutureIds<T> findFutureIds();

    public FutureList<T> findFutureList();

    public PagingList<T> findPagingList(int var1);

    public Query<T> setParameter(String var1, Object var2);

    public Query<T> setParameter(int var1, Object var2);

    public Query<T> setListener(QueryListener<T> var1);

    public Query<T> setId(Object var1);

    public Query<T> where(String var1);

    public Query<T> where(Expression var1);

    public ExpressionList<T> where();

    public ExpressionList<T> filterMany(String var1);

    public ExpressionList<T> having();

    public Query<T> having(String var1);

    public Query<T> having(Expression var1);

    public Query<T> orderBy(String var1);

    public Query<T> order(String var1);

    public OrderBy<T> order();

    public OrderBy<T> orderBy();

    public Query<T> setOrder(OrderBy<T> var1);

    public Query<T> setOrderBy(OrderBy<T> var1);

    public Query<T> setDistinct(boolean var1);

    public Query<T> setVanillaMode(boolean var1);

    public int getFirstRow();

    public Query<T> setFirstRow(int var1);

    public int getMaxRows();

    public Query<T> setMaxRows(int var1);

    public Query<T> setBackgroundFetchAfter(int var1);

    public Query<T> setMapKey(String var1);

    public Query<T> setUseCache(boolean var1);

    public Query<T> setUseQueryCache(boolean var1);

    public Query<T> setReadOnly(boolean var1);

    public Query<T> setLoadBeanCache(boolean var1);

    public Query<T> setTimeout(int var1);

    public Query<T> setBufferFetchSizeHint(int var1);

    public String getGeneratedSql();

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Type {
        BEAN,
        LIST,
        SET,
        MAP,
        ID_LIST,
        ROWCOUNT,
        SUBQUERY;

    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum UseIndex {
        NO,
        DEFAULT,
        YES_IDS,
        YES_OBJECTS;

    }
}

