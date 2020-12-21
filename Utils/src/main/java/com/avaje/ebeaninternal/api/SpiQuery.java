/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryListener;
import com.avaje.ebean.bean.BeanCollectionTouched;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.query.CancelableQuery;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiQuery<T>
extends Query<T> {
    public boolean selectAllForLazyLoadProperty();

    public void setMode(Mode var1);

    public Mode getMode();

    public void deriveSharedInstance();

    public void setSharedInstance();

    public boolean isSharedInstance();

    public void setParentState(int var1);

    public BeanCollectionTouched getBeanCollectionTouched();

    public void setBeanCollectionTouched(BeanCollectionTouched var1);

    public void setIdList(List<Object> var1);

    public List<Object> getIdList();

    @Override
    public SpiQuery<T> copy();

    public void setType(Query.Type var1);

    public String getLoadDescription();

    public String getLoadMode();

    public void setLoadDescription(String var1, String var2);

    public void setBeanDescriptor(BeanDescriptor<?> var1);

    public boolean initManyWhereJoins();

    public ManyWhereJoins getManyWhereJoins();

    public void setSelectId();

    public void setFilterMany(String var1, ExpressionList<?> var2);

    public List<OrmQueryProperties> removeQueryJoins();

    public List<OrmQueryProperties> removeLazyJoins();

    public void setLazyLoadManyPath(String var1);

    public void convertManyFetchJoinsToQueryJoins(boolean var1, int var2);

    public PersistenceContext getPersistenceContext();

    public void setPersistenceContext(PersistenceContext var1);

    public boolean isDetailEmpty();

    public Boolean isAutofetch();

    public AutoFetchManager getAutoFetchManager();

    public void setAutoFetchManager(AutoFetchManager var1);

    public ObjectGraphNode setOrigin(CallStack var1);

    public void setParentNode(ObjectGraphNode var1);

    public void setLazyLoadProperty(String var1);

    public String getLazyLoadProperty();

    public String getLazyLoadManyPath();

    public ObjectGraphNode getParentNode();

    public boolean isUsageProfiling();

    public void setUsageProfiling(boolean var1);

    public String getName();

    public int queryAutofetchHash();

    public int queryPlanHash(BeanQueryRequest<?> var1);

    public int queryBindHash();

    public int queryHash();

    public boolean isSqlSelect();

    public boolean isRawSql();

    public OrderBy<T> getOrderBy();

    public String getAdditionalWhere();

    public SpiExpressionList<T> getWhereExpressions();

    public SpiExpressionList<T> getHavingExpressions();

    public String getAdditionalHaving();

    public boolean hasMaxRowsOrFirstRow();

    public Boolean isUseBeanCache();

    public boolean isUseQueryCache();

    public boolean isLoadBeanCache();

    public Boolean isReadOnly();

    public void contextAdd(EntityBean var1);

    public Class<T> getBeanType();

    public int getTimeout();

    public ArrayList<EntityBean> getContextAdditions();

    public BindParams getBindParams();

    public String getQuery();

    public void setDetail(OrmQueryDetail var1);

    public boolean tuneFetchProperties(OrmQueryDetail var1);

    public void setAutoFetchTuned(boolean var1);

    public OrmQueryDetail getDetail();

    public TableJoin getIncludeTableJoin();

    public void setIncludeTableJoin(TableJoin var1);

    public String getMapKey();

    public int getBackgroundFetchAfter();

    @Override
    public int getMaxRows();

    @Override
    public int getFirstRow();

    public boolean isDistinct();

    public boolean isVanillaMode(boolean var1);

    public void setDefaultSelectClause();

    public String getRawWhereClause();

    public Object getId();

    public QueryListener<T> getListener();

    public boolean createOwnTransaction();

    public void setGeneratedSql(String var1);

    public int getBufferFetchSizeHint();

    public boolean isFutureFetch();

    public void setFutureFetch(boolean var1);

    public void setCancelableQuery(CancelableQuery var1);

    public boolean isCancelled();

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Mode {
        NORMAL(false),
        LAZYLOAD_MANY(false),
        LAZYLOAD_BEAN(true),
        REFRESH_BEAN(true);

        private final boolean loadContextBean;

        private Mode(boolean loadContextBean) {
            this.loadContextBean = loadContextBean;
        }

        public boolean isLoadContextBean() {
            return this.loadContextBean;
        }
    }
}

