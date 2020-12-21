/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expression;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelpFactory;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.CollectionTypeConverter;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.ExportedProperty;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import com.avaje.ebeaninternal.server.deploy.ManyType;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanPropertyAssocMany<T>
extends BeanPropertyAssoc<T> {
    final TableJoin intersectionJoin;
    final TableJoin inverseJoin;
    final boolean unidirectional;
    final boolean manyToMany;
    final String fetchOrderBy;
    final String mapKey;
    final ManyType manyType;
    final String serverName;
    final BeanCollection.ModifyListenMode modifyListenMode;
    BeanProperty mapKeyProperty;
    ExportedProperty[] exportedProperties;
    BeanProperty childMasterProperty;
    boolean embeddedExportedProperties;
    BeanCollectionHelp<T> help;
    ImportedId importedId;
    String deleteByParentIdSql;
    String deleteByParentIdInSql;
    final CollectionTypeConverter typeConverter;

    public BeanPropertyAssocMany(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssocMany<T> deploy) {
        super(owner, descriptor, deploy);
        this.unidirectional = deploy.isUnidirectional();
        this.manyToMany = deploy.isManyToMany();
        this.serverName = descriptor.getServerName();
        this.manyType = deploy.getManyType();
        this.typeConverter = this.manyType.getTypeConverter();
        this.mapKey = deploy.getMapKey();
        this.fetchOrderBy = deploy.getFetchOrderBy();
        this.intersectionJoin = deploy.createIntersectionTableJoin();
        this.inverseJoin = deploy.createInverseTableJoin();
        this.modifyListenMode = deploy.getModifyListenMode();
    }

    @Override
    public void initialise() {
        super.initialise();
        if (!this.isTransient) {
            this.help = BeanCollectionHelpFactory.create(this);
            if (this.manyToMany) {
                this.importedId = this.createImportedId(this, this.targetDescriptor, this.tableJoin);
            } else {
                this.childMasterProperty = this.initChildMasterProperty();
            }
            if (this.mapKey != null) {
                this.mapKeyProperty = this.initMapKeyProperty();
            }
            this.exportedProperties = this.createExported();
            if (this.exportedProperties.length > 0) {
                this.embeddedExportedProperties = this.exportedProperties[0].isEmbedded();
            }
            String delStmt = this.manyToMany ? "delete from " + this.inverseJoin.getTable() + " where " : "delete from " + this.targetDescriptor.getBaseTable() + " where ";
            this.deleteByParentIdSql = delStmt + this.deriveWhereParentIdSql(false);
            this.deleteByParentIdInSql = delStmt + this.deriveWhereParentIdSql(true);
        }
    }

    public Object getValueUnderlying(Object bean) {
        Object value = this.getValue(bean);
        if (this.typeConverter != null) {
            value = this.typeConverter.toUnderlying(value);
        }
        return value;
    }

    @Override
    public Object getValue(Object bean) {
        return super.getValue(bean);
    }

    @Override
    public Object getValueIntercept(Object bean) {
        return super.getValueIntercept(bean);
    }

    @Override
    public void setValue(Object bean, Object value) {
        if (this.typeConverter != null) {
            value = this.typeConverter.toWrapped(value);
        }
        super.setValue(bean, value);
    }

    @Override
    public void setValueIntercept(Object bean, Object value) {
        if (this.typeConverter != null) {
            value = this.typeConverter.toWrapped(value);
        }
        super.setValueIntercept(bean, value);
    }

    @Override
    public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
        return this.createElPropertyValue(propName, remainder, chain, propertyDeploy);
    }

    @Override
    public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
        Object sourceCollection = this.getValueUnderlying(sourceBean);
        if (sourceCollection != null) {
            Object copyCollection = this.help.copyCollection(sourceCollection, ctx, maxDepth, destBean);
            this.setValue(destBean, copyCollection);
        }
    }

    public SqlUpdate deleteByParentId(Object parentId, List<Object> parentIdist) {
        if (parentId != null) {
            return this.deleteByParentId(parentId);
        }
        return this.deleteByParentIdList(parentIdist);
    }

    private SqlUpdate deleteByParentId(Object parentId) {
        DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByParentIdSql);
        this.bindWhereParendId(sqlDelete, parentId);
        return sqlDelete;
    }

    public List<Object> findIdsByParentId(Object parentId, List<Object> parentIdist, Transaction t, ArrayList<Object> excludeDetailIds) {
        if (parentId != null) {
            return this.findIdsByParentId(parentId, t, excludeDetailIds);
        }
        return this.findIdsByParentIdList(parentIdist, t, excludeDetailIds);
    }

    private List<Object> findIdsByParentId(Object parentId, Transaction t, ArrayList<Object> excludeDetailIds) {
        String rawWhere = this.deriveWhereParentIdSql(false);
        SpiEbeanServer server = this.getBeanDescriptor().getEbeanServer();
        Query<?> q = server.find(this.getPropertyType()).where().raw(rawWhere).query();
        this.bindWhereParendId(1, q, parentId);
        if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
            Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
            q.where().not(idIn);
        }
        return server.findIds(q, t);
    }

    private List<Object> findIdsByParentIdList(List<Object> parentIdist, Transaction t, ArrayList<Object> excludeDetailIds) {
        String rawWhere = this.deriveWhereParentIdSql(true);
        String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        String expr = rawWhere + inClause;
        SpiEbeanServer server = this.getBeanDescriptor().getEbeanServer();
        Query<?> q = server.find(this.getPropertyType()).where().raw(expr).query();
        int pos = 1;
        for (int i = 0; i < parentIdist.size(); ++i) {
            pos = this.bindWhereParendId(pos, q, parentIdist.get(i));
        }
        if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
            Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
            q.where().not(idIn);
        }
        return server.findIds(q, t);
    }

    private SqlUpdate deleteByParentIdList(List<Object> parentIdist) {
        StringBuilder sb = new StringBuilder(100);
        sb.append(this.deleteByParentIdInSql);
        String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        sb.append(inClause);
        DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
        for (int i = 0; i < parentIdist.size(); ++i) {
            this.bindWhereParendId(delete, parentIdist.get(i));
        }
        return delete;
    }

    public void setLoader(BeanCollectionLoader loader) {
        if (this.help != null) {
            this.help.setLoader(loader);
        }
    }

    public BeanCollection.ModifyListenMode getModifyListenMode() {
        return this.modifyListenMode;
    }

    @Override
    public boolean hasChanged(Object bean, Object oldValues) {
        return false;
    }

    @Override
    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
    }

    @Override
    public void loadIgnore(DbReadContext ctx) {
    }

    @Override
    public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
        sqlBeanLoad.loadAssocMany(this);
    }

    @Override
    public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
        return null;
    }

    @Override
    public Object read(DbReadContext ctx) throws SQLException {
        return null;
    }

    @Override
    public boolean isValueLoaded(Object value) {
        if (value instanceof BeanCollection) {
            return ((BeanCollection)value).isPopulated();
        }
        return true;
    }

    public void add(BeanCollection<?> collection, Object bean) {
        this.help.add(collection, bean);
    }

    @Override
    public InvalidValue validateCascade(Object manyValue) {
        ArrayList<InvalidValue> errs = this.help.validate(manyValue);
        if (errs == null) {
            return null;
        }
        return new InvalidValue("recurse.many", this.targetDescriptor.getFullName(), manyValue, InvalidValue.toArray(errs));
    }

    public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
        this.help.refresh(server, query, t, parentBean);
    }

    public void refresh(BeanCollection<?> bc, Object parentBean) {
        this.help.refresh(bc, parentBean);
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
        return true;
    }

    @Override
    public boolean isAssocProperty() {
        return true;
    }

    @Override
    public boolean containsMany() {
        return true;
    }

    public ManyType getManyType() {
        return this.manyType;
    }

    public boolean isManyToMany() {
        return this.manyToMany;
    }

    public TableJoin getIntersectionTableJoin() {
        return this.intersectionJoin;
    }

    public void setJoinValuesToChild(Object parent, Object child, Object mapKeyValue) {
        if (this.mapKeyProperty != null) {
            this.mapKeyProperty.setValue(child, mapKeyValue);
        }
        if (!this.manyToMany && this.childMasterProperty != null) {
            this.childMasterProperty.setValue(child, parent);
        }
    }

    public String getFetchOrderBy() {
        return this.fetchOrderBy;
    }

    public String getMapKey() {
        return this.mapKey;
    }

    public BeanCollection<?> createReferenceIfNull(Object parentBean) {
        Object v = this.getValue(parentBean);
        if (v instanceof BeanCollection) {
            BeanCollection bc = (BeanCollection)v;
            return bc.isReference() ? bc : null;
        }
        return this.createReference(parentBean);
    }

    public BeanCollection<?> createReference(Object parentBean) {
        BeanCollection<T> ref = this.help.createReference(parentBean, this.name);
        this.setValue(parentBean, ref);
        return ref;
    }

    public Object createEmpty(boolean vanilla) {
        return this.help.createEmpty(vanilla);
    }

    public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
        return this.help.getBeanCollectionAdd(bc, mapKey);
    }

    public Object getParentId(Object parentBean) {
        return this.descriptor.getId(parentBean);
    }

    private void bindWhereParendId(DefaultSqlUpdate sqlUpd, Object parentId) {
        if (this.exportedProperties.length == 1) {
            sqlUpd.addParameter(parentId);
            return;
        }
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            Object embVal = this.exportedProperties[i].getValue(parentId);
            sqlUpd.addParameter(embVal);
        }
    }

    private int bindWhereParendId(int pos, Query<?> q, Object parentId) {
        if (this.exportedProperties.length == 1) {
            q.setParameter(pos++, parentId);
        } else {
            for (int i = 0; i < this.exportedProperties.length; ++i) {
                Object embVal = this.exportedProperties[i].getValue(parentId);
                q.setParameter(pos++, embVal);
            }
        }
        return pos;
    }

    private String deriveWhereParentIdSql(boolean inClause) {
        StringBuilder sb = new StringBuilder();
        if (inClause) {
            sb.append("(");
        }
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
        if (inClause) {
            sb.append(")");
        }
        return sb.toString();
    }

    public void setPredicates(SpiQuery<?> query, Object parentBean) {
        if (this.manyToMany) {
            query.setIncludeTableJoin(this.inverseJoin);
        }
        if (this.embeddedExportedProperties) {
            BeanProperty[] uids = this.descriptor.propertiesId();
            parentBean = uids[0].getValue(parentBean);
        }
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            Object val = this.exportedProperties[i].getValue(parentBean);
            String fkColumn = this.exportedProperties[i].getForeignDbColumn();
            fkColumn = !this.manyToMany ? this.targetDescriptor.getBaseTableAlias() + "." + fkColumn : "int_." + fkColumn;
            query.where().eq(fkColumn, val);
        }
        if (this.extraWhere != null) {
            String ta = this.targetDescriptor.getBaseTableAlias();
            String where = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
            query.where().raw(where);
        }
        if (this.fetchOrderBy != null) {
            query.order(this.fetchOrderBy);
        }
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

    private ExportedProperty findMatch(boolean embedded, BeanProperty prop) {
        String searchTable;
        TableJoinColumn[] columns;
        String matchColumn = prop.getDbColumn();
        if (this.manyToMany) {
            columns = this.intersectionJoin.columns();
            searchTable = this.intersectionJoin.getTable();
        } else {
            columns = this.tableJoin.columns();
            searchTable = this.tableJoin.getTable();
        }
        for (int i = 0; i < columns.length; ++i) {
            String matchTo = columns[i].getLocalDbColumn();
            if (!matchColumn.equalsIgnoreCase(matchTo)) continue;
            String foreignCol = columns[i].getForeignDbColumn();
            return new ExportedProperty(embedded, foreignCol, prop);
        }
        String msg = "Error with the Join on [" + this.getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
        throw new PersistenceException(msg);
    }

    private BeanProperty initChildMasterProperty() {
        if (this.unidirectional) {
            return null;
        }
        Class beanType = this.descriptor.getBeanType();
        BeanDescriptor targetDesc = this.getTargetDescriptor();
        BeanPropertyAssocOne<?>[] ones = targetDesc.propertiesOne();
        for (int i = 0; i < ones.length; ++i) {
            BeanPropertyAssocOne<?> prop = ones[i];
            if (!(this.mappedBy != null ? this.mappedBy.equalsIgnoreCase(prop.getName()) : prop.getTargetType().equals(beanType))) continue;
            return prop;
        }
        String msg = "Can not find Master [" + beanType + "] in Child[" + targetDesc + "]";
        throw new RuntimeException(msg);
    }

    private BeanProperty initMapKeyProperty() {
        BeanDescriptor targetDesc = this.getTargetDescriptor();
        Iterator<BeanProperty> it = targetDesc.propertiesAll();
        while (it.hasNext()) {
            BeanProperty prop = it.next();
            if (!this.mapKey.equalsIgnoreCase(prop.getName())) continue;
            return prop;
        }
        String from = this.descriptor.getFullName();
        String to = targetDesc.getFullName();
        String msg = from + ": Could not find mapKey property [" + this.mapKey + "] on [" + to + "]";
        throw new PersistenceException(msg);
    }

    public IntersectionRow buildManyDeleteChildren(Object parentBean, ArrayList<Object> excludeDetailIds) {
        IntersectionRow row = new IntersectionRow(this.tableJoin.getTable());
        if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
            row.setExcludeIds(excludeDetailIds, this.getTargetDescriptor());
        }
        this.buildExport(row, parentBean);
        return row;
    }

    public IntersectionRow buildManyToManyDeleteChildren(Object parentBean) {
        IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
        this.buildExport(row, parentBean);
        return row;
    }

    public IntersectionRow buildManyToManyMapBean(Object parent, Object other) {
        IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
        this.buildExport(row, parent);
        this.buildImport(row, other);
        return row;
    }

    private void buildExport(IntersectionRow row, Object parentBean) {
        if (this.embeddedExportedProperties) {
            BeanProperty[] uids = this.descriptor.propertiesId();
            parentBean = uids[0].getValue(parentBean);
        }
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            Object val = this.exportedProperties[i].getValue(parentBean);
            String fkColumn = this.exportedProperties[i].getForeignDbColumn();
            row.put(fkColumn, val);
        }
    }

    private void buildImport(IntersectionRow row, Object otherBean) {
        this.importedId.buildImport(row, otherBean);
    }

    public boolean hasImportedId(Object otherBean) {
        return null != this.targetDescriptor.getId(otherBean);
    }

    @Override
    public void jsonWrite(WriteJsonContext ctx, Object bean) {
        Boolean include = ctx.includeMany(this.name);
        if (Boolean.FALSE.equals(include)) {
            return;
        }
        Object value = this.getValueIntercept(bean);
        if (value != null) {
            ctx.pushParentBeanMany(bean);
            this.help.jsonWrite(ctx, this.name, value, include != null);
            ctx.popParentBeanMany();
        }
    }

    @Override
    public void jsonRead(ReadJsonContext ctx, Object bean) {
        ReadJsonContext.ReadBeanState detailBeanState;
        if (!ctx.readArrayBegin()) {
            return;
        }
        Object collection = this.help.createEmpty(false);
        BeanCollectionAdd add = this.getBeanCollectionAdd(collection, null);
        while ((detailBeanState = this.targetDescriptor.jsonRead(ctx, this.name)) != null) {
            Object detailBean = detailBeanState.getBean();
            add.addBean(detailBean);
            if (bean != null && this.childMasterProperty != null) {
                this.childMasterProperty.setValue(detailBean, bean);
                detailBeanState.setLoaded(this.childMasterProperty.getName());
            }
            detailBeanState.setLoadedState();
            if (ctx.readArrayNext()) continue;
            break;
        }
        this.setValue(bean, collection);
    }
}

