/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.query.LoadedPropertiesCache;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import com.avaje.ebeaninternal.server.query.SqlTreeNode;
import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SqlTreeNodeBean
implements SqlTreeNode {
    private static final SqlTreeNode[] NO_CHILDREN = new SqlTreeNode[0];
    final BeanDescriptor<?> desc;
    final IdBinder idBinder;
    final SqlTreeNode[] children;
    final boolean readOnlyLeaf;
    final boolean partialObject;
    final Set<String> partialProps;
    final int partialHash;
    final BeanProperty[] properties;
    final String extraWhere;
    final BeanPropertyAssoc<?> nodeBeanProp;
    final TableJoin[] tableJoins;
    final boolean readId;
    final boolean disableLazyLoad;
    final InheritInfo inheritInfo;
    final String prefix;
    final Set<String> includedProps;
    final Map<String, String> pathMap;

    public SqlTreeNodeBean(String prefix, BeanPropertyAssoc<?> beanProp, SqlTreeProperties props, List<SqlTreeNode> myChildren, boolean withId) {
        this(prefix, beanProp, beanProp.getTargetDescriptor(), props, myChildren, withId);
    }

    public SqlTreeNodeBean(String prefix, BeanPropertyAssoc<?> beanProp, BeanDescriptor<?> desc, SqlTreeProperties props, List<SqlTreeNode> myChildren, boolean withId) {
        this.prefix = prefix;
        this.nodeBeanProp = beanProp;
        this.desc = desc;
        this.inheritInfo = desc.getInheritInfo();
        this.extraWhere = beanProp == null ? null : beanProp.getExtraWhere();
        this.idBinder = desc.getIdBinder();
        this.readId = withId && desc.propertiesId().length > 0;
        this.disableLazyLoad = !this.readId || desc.isSqlSelectBased();
        this.tableJoins = props.getTableJoins();
        this.partialObject = props.isPartialObject();
        this.partialProps = props.getIncludedProperties();
        this.partialHash = this.partialObject ? ((Object)this.partialProps).hashCode() : 0;
        this.readOnlyLeaf = props.isReadOnly();
        this.properties = props.getProps();
        this.includedProps = this.partialObject ? LoadedPropertiesCache.get(this.partialHash, this.partialProps, desc) : null;
        this.children = myChildren == null ? NO_CHILDREN : myChildren.toArray(new SqlTreeNode[myChildren.size()]);
        this.pathMap = this.createPathMap(prefix, desc);
    }

    private Map<String, String> createPathMap(String prefix, BeanDescriptor<?> desc) {
        BeanPropertyAssocMany<?>[] manys = desc.propertiesMany();
        HashMap<String, String> m = new HashMap<String, String>();
        for (int i = 0; i < manys.length; ++i) {
            String name = manys[i].getName();
            m.put(name, this.getPath(prefix, name));
        }
        return m;
    }

    private String getPath(String prefix, String propertyName) {
        if (prefix == null) {
            return propertyName;
        }
        return prefix + "." + propertyName;
    }

    protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) {
    }

    @Override
    public void buildSelectExpressionChain(List<String> selectChain) {
        int i;
        if (this.readId) {
            this.idBinder.buildSelectExpressionChain(this.prefix, selectChain);
        }
        int x = this.properties.length;
        for (i = 0; i < x; ++i) {
            this.properties[i].buildSelectExpressionChain(this.prefix, selectChain);
        }
        for (i = 0; i < this.children.length; ++i) {
            this.children[i].buildSelectExpressionChain(selectChain);
        }
    }

    @Override
    public void load(DbReadContext ctx, Object parentBean, int parentState) throws SQLException {
        int i;
        int x;
        BeanDescriptor<?> localDesc;
        Class<?> localType;
        Object localBean;
        IdBinder localIdBinder;
        Object contextBean = null;
        if (this.inheritInfo != null) {
            InheritInfo localInfo = this.inheritInfo.readType(ctx);
            if (localInfo == null) {
                localIdBinder = this.idBinder;
                localBean = null;
                localType = null;
                localDesc = this.desc;
            } else {
                localBean = localInfo.createBean(ctx.isVanillaMode());
                localType = localInfo.getType();
                localIdBinder = localInfo.getIdBinder();
                localDesc = localInfo.getBeanDescriptor();
            }
        } else {
            localType = null;
            localDesc = this.desc;
            localBean = this.desc.createBean(ctx.isVanillaMode());
            localIdBinder = this.idBinder;
        }
        SpiQuery.Mode queryMode = ctx.getQueryMode();
        PersistenceContext persistenceContext = ctx.getPersistenceContext();
        Object id = null;
        if (this.readId) {
            id = localIdBinder.readSet(ctx, localBean);
            if (id == null) {
                localBean = null;
            } else {
                contextBean = persistenceContext.putIfAbsent(id, localBean);
                if (contextBean == null) {
                    contextBean = localBean;
                } else if (queryMode.isLoadContextBean()) {
                    localBean = contextBean;
                    if (localBean instanceof EntityBean) {
                        ((EntityBean)localBean)._ebean_getIntercept().setIntercepting(false);
                    }
                } else {
                    localBean = null;
                }
            }
        }
        ctx.setCurrentPrefix(this.prefix, this.pathMap);
        ctx.propagateState(localBean);
        SqlBeanLoad sqlBeanLoad = new SqlBeanLoad(ctx, localType, localBean, queryMode);
        if (this.inheritInfo == null) {
            x = this.properties.length;
            for (i = 0; i < x; ++i) {
                this.properties[i].load(sqlBeanLoad);
            }
        } else {
            x = this.properties.length;
            for (i = 0; i < x; ++i) {
                BeanProperty p = localDesc.getBeanProperty(this.properties[i].getName());
                if (p != null) {
                    p.load(sqlBeanLoad);
                    continue;
                }
                this.properties[i].loadIgnore(ctx);
            }
        }
        x = this.tableJoins.length;
        for (i = 0; i < x; ++i) {
            this.tableJoins[i].load(sqlBeanLoad);
        }
        boolean lazyLoadMany = false;
        if (localBean == null && queryMode.equals((Object)SpiQuery.Mode.LAZYLOAD_MANY)) {
            localBean = contextBean;
            lazyLoadMany = true;
        }
        for (int i2 = 0; i2 < this.children.length; ++i2) {
            this.children[i2].load(ctx, localBean, parentState);
        }
        if (!lazyLoadMany && localBean != null) {
            ctx.setCurrentPrefix(this.prefix, this.pathMap);
            if (!ctx.isVanillaMode()) {
                this.createListProxies(localDesc, ctx, localBean);
            }
            localDesc.postLoad(localBean, this.includedProps);
            if (localBean instanceof EntityBean) {
                EntityBeanIntercept ebi = ((EntityBean)localBean)._ebean_getIntercept();
                ebi.setPersistenceContext(persistenceContext);
                ebi.setLoadedProps(this.includedProps);
                if (SpiQuery.Mode.LAZYLOAD_BEAN.equals((Object)queryMode)) {
                    ebi.setLoadedLazy();
                } else {
                    ebi.setLoaded();
                }
                if (this.partialObject) {
                    ctx.register(null, ebi);
                }
                if (this.disableLazyLoad) {
                    ebi.setDisableLazyLoad(true);
                }
                if (ctx.isAutoFetchProfiling()) {
                    ctx.profileBean(ebi, this.prefix);
                }
            }
        }
        if (parentBean != null && contextBean != null) {
            this.nodeBeanProp.setValue(parentBean, contextBean);
        }
        if (!this.readId) {
            this.postLoad(ctx, localBean, id);
        } else {
            this.postLoad(ctx, contextBean, id);
        }
    }

    private void createListProxies(BeanDescriptor<?> localDesc, DbReadContext ctx, Object localBean) {
        BeanPropertyAssocMany<?> fetchedMany = ctx.getManyProperty();
        BeanPropertyAssocMany<?>[] manys = localDesc.propertiesMany();
        for (int i = 0; i < manys.length; ++i) {
            BeanCollection<?> ref;
            if (fetchedMany != null && fetchedMany.equals(manys[i]) || (ref = manys[i].createReferenceIfNull(localBean)) == null) continue;
            ctx.register(manys[i].getName(), ref);
        }
    }

    @Override
    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
        ctx.pushJoin(this.prefix);
        ctx.pushTableAlias(this.prefix);
        if (this.nodeBeanProp != null) {
            ctx.append('\n').append("        ");
        }
        if (!subQuery && this.inheritInfo != null) {
            ctx.appendColumn(this.inheritInfo.getDiscriminatorColumn());
        }
        if (this.readId) {
            this.appendSelect(ctx, false, this.idBinder.getProperties());
        }
        this.appendSelect(ctx, subQuery, this.properties);
        this.appendSelectTableJoins(ctx);
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].appendSelect(ctx, subQuery);
        }
        ctx.popTableAlias();
        ctx.popJoin();
    }

    private void appendSelectTableJoins(DbSqlContext ctx) {
        String baseAlias = ctx.getTableAlias(this.prefix);
        for (int i = 0; i < this.tableJoins.length; ++i) {
            TableJoin join = this.tableJoins[i];
            String alias = baseAlias + i;
            ctx.pushSecondaryTableAlias(alias);
            join.appendSelect(ctx, false);
            ctx.popTableAlias();
        }
    }

    private void appendSelect(DbSqlContext ctx, boolean subQuery, BeanProperty[] props) {
        for (int i = 0; i < props.length; ++i) {
            props[i].appendSelect(ctx, subQuery);
        }
    }

    @Override
    public void appendWhere(DbSqlContext ctx) {
        if (this.inheritInfo != null && !this.inheritInfo.isRoot()) {
            if (ctx.length() > 0) {
                ctx.append(" and");
            }
            ctx.append(" ").append(ctx.getTableAlias(this.prefix)).append(".");
            ctx.append(this.inheritInfo.getWhere()).append(" ");
        }
        if (this.extraWhere != null) {
            if (ctx.length() > 0) {
                ctx.append(" and");
            }
            String ta = ctx.getTableAlias(this.prefix);
            String ew = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
            ctx.append(" ").append(ew).append(" ");
        }
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].appendWhere(ctx);
        }
    }

    @Override
    public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
        int i;
        ctx.pushJoin(this.prefix);
        ctx.pushTableAlias(this.prefix);
        forceOuterJoin = this.appendFromBaseTable(ctx, forceOuterJoin);
        for (i = 0; i < this.properties.length; ++i) {
            this.properties[i].appendFrom(ctx, forceOuterJoin);
        }
        for (i = 0; i < this.children.length; ++i) {
            this.children[i].appendFrom(ctx, forceOuterJoin);
        }
        ctx.popTableAlias();
        ctx.popJoin();
    }

    public boolean appendFromBaseTable(DbSqlContext ctx, boolean forceOuterJoin) {
        BeanPropertyAssocMany manyProp;
        if (this.nodeBeanProp instanceof BeanPropertyAssocMany && (manyProp = (BeanPropertyAssocMany)this.nodeBeanProp).isManyToMany()) {
            String alias = ctx.getTableAlias(this.prefix);
            String[] split = SplitName.split(this.prefix);
            String parentAlias = ctx.getTableAlias(split[0]);
            String alias2 = alias + "z_";
            TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
            manyToManyJoin.addJoin(forceOuterJoin, parentAlias, alias2, ctx);
            return this.nodeBeanProp.addJoin(forceOuterJoin, alias2, alias, ctx);
        }
        return this.nodeBeanProp.addJoin(forceOuterJoin, this.prefix, ctx);
    }

    public String toString() {
        return "SqlTreeNodeBean: " + this.desc;
    }
}

