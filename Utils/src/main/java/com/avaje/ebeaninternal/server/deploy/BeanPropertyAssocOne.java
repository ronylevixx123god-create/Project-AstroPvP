/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanEmbeddedMeta;
import com.avaje.ebeaninternal.server.deploy.BeanEmbeddedMetaFactory;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.ExportedProperty;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanPropertyAssocOne<T>
extends BeanPropertyAssoc<T> {
    private final boolean oneToOne;
    private final boolean oneToOneExported;
    private final boolean embeddedVersion;
    private final boolean importedPrimaryKey;
    private final LocalHelp localHelp;
    private final BeanProperty[] embeddedProps;
    private final HashMap<String, BeanProperty> embeddedPropsMap;
    private ImportedId importedId;
    private ExportedProperty[] exportedProperties;
    private String deleteByParentIdSql;
    private String deleteByParentIdInSql;

    public BeanPropertyAssocOne(BeanDescriptorMap owner, DeployBeanPropertyAssocOne<T> deploy) {
        this(owner, (BeanDescriptor<?>)null, deploy);
    }

    public BeanPropertyAssocOne(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssocOne<T> deploy) {
        super(owner, descriptor, deploy);
        this.importedPrimaryKey = deploy.isImportedPrimaryKey();
        this.oneToOne = deploy.isOneToOne();
        this.oneToOneExported = deploy.isOneToOneExported();
        if (this.embedded) {
            BeanEmbeddedMeta overrideMeta = BeanEmbeddedMetaFactory.create(owner, deploy, descriptor);
            this.embeddedProps = overrideMeta.getProperties();
            this.embeddedVersion = this.id ? false : overrideMeta.isEmbeddedVersion();
            this.embeddedPropsMap = new HashMap();
            for (int i = 0; i < this.embeddedProps.length; ++i) {
                this.embeddedPropsMap.put(this.embeddedProps[i].getName(), this.embeddedProps[i]);
            }
        } else {
            this.embeddedProps = null;
            this.embeddedPropsMap = null;
            this.embeddedVersion = false;
        }
        this.localHelp = this.createHelp(this.embedded, this.oneToOneExported);
    }

    @Override
    public void initialise() {
        super.initialise();
        if (!this.isTransient && !this.embedded) {
            if (!this.oneToOneExported) {
                this.importedId = this.createImportedId(this, this.targetDescriptor, this.tableJoin);
            } else {
                this.exportedProperties = this.createExported();
                String delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
                this.deleteByParentIdSql = delStmt + this.deriveWhereParentIdSql(false);
                this.deleteByParentIdInSql = delStmt + this.deriveWhereParentIdSql(true);
            }
        }
    }

    @Override
    public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
        if (this.embedded) {
            BeanProperty embProp = this.embeddedPropsMap.get(remainder);
            if (embProp == null) {
                String msg = "Embedded Property " + remainder + " not found in " + this.getFullBeanName();
                throw new PersistenceException(msg);
            }
            if (chain == null) {
                chain = new ElPropertyChainBuilder(true, propName);
            }
            chain.add(this);
            return chain.add(embProp).build();
        }
        return this.createElPropertyValue(propName, remainder, chain, propertyDeploy);
    }

    @Override
    public String getElPlaceholder(boolean encrypted) {
        return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder;
    }

    @Override
    public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
        this.localHelp.copyProperty(sourceBean, destBean, ctx, maxDepth);
    }

    public SqlUpdate deleteByParentId(Object parentId, List<Object> parentIdist) {
        if (parentId != null) {
            return this.deleteByParentId(parentId);
        }
        return this.deleteByParentIdList(parentIdist);
    }

    private SqlUpdate deleteByParentIdList(List<Object> parentIdist) {
        StringBuilder sb = new StringBuilder(100);
        sb.append(this.deleteByParentIdInSql);
        String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        sb.append(inClause);
        DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
        for (int i = 0; i < parentIdist.size(); ++i) {
            this.targetIdBinder.bindId(delete, parentIdist.get(i));
        }
        return delete;
    }

    private SqlUpdate deleteByParentId(Object parentId) {
        DefaultSqlUpdate delete = new DefaultSqlUpdate(this.deleteByParentIdSql);
        if (this.exportedProperties.length == 1) {
            delete.addParameter(parentId);
        } else {
            this.targetDescriptor.getIdBinder().bindId(delete, parentId);
        }
        return delete;
    }

    public List<Object> findIdsByParentId(Object parentId, List<Object> parentIdist, Transaction t) {
        if (parentId != null) {
            return this.findIdsByParentId(parentId, t);
        }
        return this.findIdsByParentIdList(parentIdist, t);
    }

    private List<Object> findIdsByParentId(Object parentId, Transaction t) {
        String rawWhere = this.deriveWhereParentIdSql(false);
        SpiEbeanServer server = this.getBeanDescriptor().getEbeanServer();
        Query<?> q = server.find(this.getPropertyType()).where().raw(rawWhere).query();
        this.bindWhereParendId(q, parentId);
        return server.findIds(q, t);
    }

    private List<Object> findIdsByParentIdList(List<Object> parentIdist, Transaction t) {
        String rawWhere = this.deriveWhereParentIdSql(true);
        String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        String expr = rawWhere + inClause;
        SpiEbeanServer server = this.getBeanDescriptor().getEbeanServer();
        Query q = (Query)((Object)server.find(this.getPropertyType()).where().raw(expr));
        for (int i = 0; i < parentIdist.size(); ++i) {
            this.bindWhereParendId(q, parentIdist.get(i));
        }
        return server.findIds(q, t);
    }

    private void bindWhereParendId(Query<?> q, Object parentId) {
        if (this.exportedProperties.length == 1) {
            q.setParameter(1, parentId);
        } else {
            int pos = 1;
            for (int i = 0; i < this.exportedProperties.length; ++i) {
                Object embVal = this.exportedProperties[i].getValue(parentId);
                q.setParameter(pos++, embVal);
            }
        }
    }

    public void addFkey() {
        if (this.importedId != null) {
            this.importedId.addFkeys(this.name);
        }
    }

    @Override
    public boolean isValueLoaded(Object value) {
        if (value instanceof EntityBean) {
            return ((EntityBean)value)._ebean_getIntercept().isLoaded();
        }
        return true;
    }

    @Override
    public InvalidValue validateCascade(Object value) {
        BeanDescriptor target = this.getTargetDescriptor();
        return target.validate(true, value);
    }

    private boolean hasChangedEmbedded(Object bean, Object oldValues) {
        Object embValue = this.getValue(oldValues);
        if (embValue instanceof EntityBean) {
            return ((EntityBean)embValue)._ebean_getIntercept().isNewOrDirty();
        }
        if (embValue == null) {
            return this.getValue(bean) != null;
        }
        return false;
    }

    @Override
    public boolean hasChanged(Object bean, Object oldValues) {
        if (this.embedded) {
            return this.hasChangedEmbedded(bean, oldValues);
        }
        Object value = this.getValue(bean);
        Object oldVal = this.getValue(oldValues);
        if (this.oneToOneExported) {
            return false;
        }
        if (value == null) {
            return oldVal != null;
        }
        if (oldValues == null) {
            return true;
        }
        return this.importedId.hasChanged(value, oldVal);
    }

    public BeanProperty[] getProperties() {
        return this.embeddedProps;
    }

    @Override
    public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
        prefix = SplitName.add(prefix, this.name);
        if (!this.embedded) {
            this.targetIdBinder.buildSelectExpressionChain(prefix, selectChain);
        } else {
            for (int i = 0; i < this.embeddedProps.length; ++i) {
                this.embeddedProps[i].buildSelectExpressionChain(prefix, selectChain);
            }
        }
    }

    public boolean isOneToOne() {
        return this.oneToOne;
    }

    public boolean isOneToOneExported() {
        return this.oneToOneExported;
    }

    public boolean isEmbeddedVersion() {
        return this.embeddedVersion;
    }

    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }

    @Override
    public Class<?> getTargetType() {
        return this.getPropertyType();
    }

    @Override
    public Object[] getAssocOneIdValues(Object bean) {
        return this.targetDescriptor.getIdBinder().getIdValues(bean);
    }

    @Override
    public String getAssocOneIdExpr(String prefix, String operator) {
        return this.targetDescriptor.getIdBinder().getAssocOneIdExpr(prefix, operator);
    }

    @Override
    public String getAssocIdInValueExpr(int size) {
        return this.targetDescriptor.getIdBinder().getIdInValueExpr(size);
    }

    @Override
    public String getAssocIdInExpr(String prefix) {
        return this.targetDescriptor.getIdBinder().getAssocIdInExpr(prefix);
    }

    @Override
    public boolean isAssocId() {
        return !this.embedded;
    }

    @Override
    public boolean isAssocProperty() {
        return !this.embedded;
    }

    public Object createEmbeddedId() {
        return this.getTargetDescriptor().createVanillaBean();
    }

    public Object createEmptyReference() {
        return this.targetDescriptor.createEntityBean();
    }

    @Override
    public void elSetReference(Object bean) {
        Object value = this.getValueIntercept(bean);
        if (value != null) {
            ((EntityBean)value)._ebean_getIntercept().setReference();
        }
    }

    @Override
    public Object elGetReference(Object bean) {
        Object value = this.getValueIntercept(bean);
        if (value == null) {
            value = this.targetDescriptor.createEntityBean();
            this.setValueIntercept(bean, value);
        }
        return value;
    }

    public ImportedId getImportedId() {
        return this.importedId;
    }

    private String deriveWhereParentIdSql(boolean inClause) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            String fkColumn = this.exportedProperties[i].getForeignDbColumn();
            if (i > 0) {
                String s = inClause ? "," : " and ";
                sb.append(s);
            }
            sb.append(fkColumn);
            if (inClause) continue;
            sb.append("=? ");
        }
        return sb.toString();
    }

    private ExportedProperty[] createExported() {
        BeanProperty[] uids = this.descriptor.propertiesId();
        ArrayList<ExportedProperty> list = new ArrayList<ExportedProperty>();
        if (uids.length == 1 && uids[0].isEmbedded()) {
            BeanPropertyAssocOne one = (BeanPropertyAssocOne)uids[0];
            BeanDescriptor targetDesc = one.getTargetDescriptor();
            BeanProperty[] emIds = targetDesc.propertiesBaseScalar();
            try {
                for (int i = 0; i < emIds.length; ++i) {
                    ExportedProperty expProp = this.findMatch(true, emIds[i]);
                    list.add(expProp);
                }
            }
            catch (PersistenceException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < uids.length; ++i) {
                ExportedProperty expProp = this.findMatch(false, uids[i]);
                list.add(expProp);
            }
        }
        return list.toArray(new ExportedProperty[list.size()]);
    }

    private ExportedProperty findMatch(boolean embeddedProp, BeanProperty prop) {
        String matchColumn = prop.getDbColumn();
        String searchTable = this.tableJoin.getTable();
        TableJoinColumn[] columns = this.tableJoin.columns();
        for (int i = 0; i < columns.length; ++i) {
            String matchTo = columns[i].getLocalDbColumn();
            if (!matchColumn.equalsIgnoreCase(matchTo)) continue;
            String foreignCol = columns[i].getForeignDbColumn();
            return new ExportedProperty(embeddedProp, foreignCol, prop);
        }
        String msg = "Error with the Join on [" + this.getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
        throw new PersistenceException(msg);
    }

    @Override
    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
        if (!this.isTransient) {
            this.localHelp.appendSelect(ctx, subQuery);
        }
    }

    @Override
    public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
        if (!this.isTransient) {
            this.localHelp.appendFrom(ctx, forceOuterJoin);
        }
    }

    @Override
    public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
        boolean assignable = type == null || this.owningType.isAssignableFrom(type);
        return this.localHelp.readSet(ctx, bean, assignable);
    }

    @Override
    public Object read(DbReadContext ctx) throws SQLException {
        return this.localHelp.read(ctx);
    }

    @Override
    public void loadIgnore(DbReadContext ctx) {
        this.localHelp.loadIgnore(ctx);
    }

    @Override
    public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
        Object dbVal = sqlBeanLoad.load(this);
        if (this.embedded && sqlBeanLoad.isLazyLoad() && dbVal instanceof EntityBean) {
            ((EntityBean)dbVal)._ebean_getIntercept().setLoaded();
        }
    }

    private LocalHelp createHelp(boolean embedded, boolean oneToOneExported) {
        if (embedded) {
            return new Embedded();
        }
        if (oneToOneExported) {
            return new ReferenceExported();
        }
        return new Reference(this);
    }

    @Override
    public void jsonWrite(WriteJsonContext ctx, Object bean) {
        Object value = this.getValueIntercept(bean);
        if (value == null) {
            ctx.beginAssocOneIsNull(this.name);
        } else if (!ctx.isParentBean(value)) {
            ctx.pushParentBean(bean);
            ctx.beginAssocOne(this.name);
            BeanDescriptor<?> refDesc = this.descriptor.getBeanDescriptor(value.getClass());
            refDesc.jsonWrite(ctx, value);
            ctx.endAssocOne();
            ctx.popParentBean();
        }
    }

    @Override
    public void jsonRead(ReadJsonContext ctx, Object bean) {
        Object assocBean = this.targetDescriptor.jsonReadBean(ctx, this.name);
        this.setValue(bean, assocBean);
    }

    private final class ReferenceExported
    extends LocalHelp {
        private ReferenceExported() {
        }

        void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
            this.copy(sourceBean, destBean, ctx, maxDepth);
        }

        void loadIgnore(DbReadContext ctx) {
            BeanPropertyAssocOne.this.targetDescriptor.getIdBinder().loadIgnore(ctx);
        }

        Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
            Object dbVal = this.read(ctx);
            if (bean != null && assignable) {
                BeanPropertyAssocOne.this.setValue(bean, dbVal);
                ctx.propagateState(dbVal);
            }
            return dbVal;
        }

        Object read(DbReadContext ctx) throws SQLException {
            IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
            Object id = idBinder.read(ctx);
            if (id == null) {
                return null;
            }
            PersistenceContext persistCtx = ctx.getPersistenceContext();
            Object existing = persistCtx.get(BeanPropertyAssocOne.this.targetType, id);
            if (existing != null) {
                return existing;
            }
            boolean vanillaMode = ctx.isVanillaMode();
            Object parent = null;
            Object ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, id, parent, null);
            if (!vanillaMode) {
                EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
                if (ctx.getParentState() != 0) {
                    ebi.setState(ctx.getParentState());
                }
                persistCtx.put(id, ref);
                ctx.register(BeanPropertyAssocOne.this.name, ebi);
            }
            return ref;
        }

        void appendSelect(DbSqlContext ctx, boolean subQuery) {
            String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
            ctx.pushTableAlias(relativePrefix);
            IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
            idBinder.appendSelect(ctx, subQuery);
            ctx.popTableAlias();
        }

        void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
            String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
            BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private final class Reference
    extends LocalHelp {
        private final BeanPropertyAssocOne<?> beanProp;

        Reference(BeanPropertyAssocOne<?> beanProp) {
            this.beanProp = beanProp;
        }

        @Override
        void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
            this.copy(sourceBean, destBean, ctx, maxDepth);
        }

        @Override
        void loadIgnore(DbReadContext ctx) {
            BeanPropertyAssocOne.this.targetIdBinder.loadIgnore(ctx);
            if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
                ctx.getDataReader().incrementPos(1);
            }
        }

        @Override
        Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
            Object val = this.read(ctx);
            if (bean != null && assignable) {
                BeanPropertyAssocOne.this.setValue(bean, val);
                ctx.propagateState(val);
            }
            return val;
        }

        @Override
        Object read(DbReadContext ctx) throws SQLException {
            Object existingBean;
            Object id;
            InheritInfo rowInheritInfo;
            BeanDescriptor<?> rowDescriptor = null;
            Class<?> rowType = BeanPropertyAssocOne.this.targetType;
            if (BeanPropertyAssocOne.this.targetInheritInfo != null && (rowInheritInfo = BeanPropertyAssocOne.this.targetInheritInfo.readType(ctx)) != null) {
                rowType = rowInheritInfo.getType();
                rowDescriptor = rowInheritInfo.getBeanDescriptor();
            }
            if ((id = BeanPropertyAssocOne.this.targetIdBinder.read(ctx)) == null) {
                return null;
            }
            Object existing = ctx.getPersistenceContext().get(rowType, id);
            if (existing != null) {
                return existing;
            }
            Object parent = null;
            Object ref = null;
            boolean vanillaMode = ctx.isVanillaMode();
            int parentState = ctx.getParentState();
            ReferenceOptions options = ctx.getReferenceOptionsFor(this.beanProp);
            if (options != null && options.isUseCache() && (ref = (Object)BeanPropertyAssocOne.this.targetDescriptor.cacheGet(id)) != null) {
                if (vanillaMode) {
                    ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(true), 5);
                } else if (parentState == 1) {
                    ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(false), 5);
                } else if (parentState == 0 && !options.isReadOnly()) {
                    ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(false), 5);
                }
            }
            boolean createReference = false;
            if (ref == null) {
                createReference = true;
                ref = BeanPropertyAssocOne.this.targetInheritInfo != null ? rowDescriptor.createReference(vanillaMode, id, parent, options) : BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, id, parent, options);
            }
            if ((existingBean = ctx.getPersistenceContext().putIfAbsent(id, ref)) != null) {
                ref = existingBean;
                createReference = false;
            }
            if (!vanillaMode) {
                EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
                if (createReference) {
                    if (parentState != 0) {
                        ebi.setState(parentState);
                    }
                    ctx.register(BeanPropertyAssocOne.this.name, ebi);
                }
            }
            return ref;
        }

        @Override
        void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
            if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
                String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.name);
                BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
            }
        }

        @Override
        void appendSelect(DbSqlContext ctx, boolean subQuery) {
            if (!subQuery && BeanPropertyAssocOne.this.targetInheritInfo != null) {
                String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
                String tableAlias = ctx.getTableAlias(relativePrefix);
                ctx.appendColumn(tableAlias, BeanPropertyAssocOne.this.targetInheritInfo.getDiscriminatorColumn());
            }
            BeanPropertyAssocOne.this.importedId.sqlAppend(ctx);
        }
    }

    private final class Embedded
    extends LocalHelp {
        private Embedded() {
        }

        void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
            Object srcEmb = BeanPropertyAssocOne.this.getValue(sourceBean);
            if (srcEmb != null) {
                Object dstEmb = BeanPropertyAssocOne.this.targetDescriptor.createBean(ctx.isVanillaMode());
                for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                    BeanPropertyAssocOne.this.embeddedProps[i].copyProperty(srcEmb, dstEmb, ctx, maxDepth);
                }
                BeanPropertyAssocOne.this.setValue(destBean, dstEmb);
            }
        }

        void loadIgnore(DbReadContext ctx) {
            for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                BeanPropertyAssocOne.this.embeddedProps[i].loadIgnore(ctx);
            }
        }

        Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
            Object dbVal = this.read(ctx);
            if (bean != null && assignable) {
                BeanPropertyAssocOne.this.setValue(bean, dbVal);
                ctx.propagateState(dbVal);
                return dbVal;
            }
            return null;
        }

        Object read(DbReadContext ctx) throws SQLException {
            EntityBean embeddedBean = BeanPropertyAssocOne.this.targetDescriptor.createEntityBean();
            boolean notNull = false;
            for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                Object value = BeanPropertyAssocOne.this.embeddedProps[i].readSet(ctx, embeddedBean, null);
                if (value == null) continue;
                notNull = true;
            }
            if (notNull) {
                ctx.propagateState(embeddedBean);
                return embeddedBean;
            }
            return null;
        }

        void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
        }

        void appendSelect(DbSqlContext ctx, boolean subQuery) {
            for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                BeanPropertyAssocOne.this.embeddedProps[i].appendSelect(ctx, subQuery);
            }
        }
    }

    private abstract class LocalHelp {
        private LocalHelp() {
        }

        abstract void copyProperty(Object var1, Object var2, CopyContext var3, int var4);

        abstract void loadIgnore(DbReadContext var1);

        abstract Object read(DbReadContext var1) throws SQLException;

        abstract Object readSet(DbReadContext var1, Object var2, boolean var3) throws SQLException;

        abstract void appendSelect(DbSqlContext var1, boolean var2);

        abstract void appendFrom(DbSqlContext var1, boolean var2);

        void copy(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
            Object value = BeanPropertyAssocOne.this.getValue(sourceBean);
            if (value != null) {
                BeanDescriptor<?> refDesc;
                Object refId;
                Class<?> valueClass = value.getClass();
                Object destRef = ctx.get(valueClass, refId = (refDesc = BeanPropertyAssocOne.this.descriptor.getBeanDescriptor(valueClass)).getId(value));
                if (destRef == null) {
                    destRef = maxDepth > 1 ? refDesc.createCopy(value, ctx, maxDepth - 1) : refDesc.createReference(ctx.isVanillaMode(), refId, destBean, null);
                }
                BeanPropertyAssocOne.this.setValue(destBean, destRef);
            }
        }
    }
}

