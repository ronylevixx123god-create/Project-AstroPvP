/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebean.Query;
import com.avaje.ebean.config.TableName;
import com.avaje.ebean.config.dbplatform.IdGenerator;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistController;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistListener;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanQueryAdapter;
import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployBeanDescriptor<T> {
    private static final PropOrder PROP_ORDER = new PropOrder();
    private static final String I_SCALAOBJECT = "scala.ScalaObject";
    private static final Logger logger = Logger.getLogger(DeployBeanDescriptor.class.getName());
    private static final String META_BEAN_PREFIX = MetaAutoFetchStatistic.class.getName().substring(0, 20);
    private LinkedHashMap<String, DeployBeanProperty> propMap = new LinkedHashMap();
    private final Class<T> beanType;
    private BeanDescriptor.EntityType entityType;
    private final Map<String, DeployNamedQuery> namedQueries = new LinkedHashMap<String, DeployNamedQuery>();
    private final Map<String, DeployNamedUpdate> namedUpdates = new LinkedHashMap<String, DeployNamedUpdate>();
    private final Map<String, DRawSqlMeta> rawSqlMetas = new LinkedHashMap<String, DRawSqlMeta>();
    private DeployBeanPropertyAssocOne<?> unidirectional;
    private IdType idType;
    private String idGeneratorName;
    private IdGenerator idGenerator;
    private String sequenceName;
    private String ldapBaseDn;
    private String[] ldapObjectclasses;
    private String selectLastInsertedId;
    private String lazyFetchIncludes;
    private ConcurrencyMode concurrencyMode = ConcurrencyMode.ALL;
    private boolean updateChangesOnly;
    private String[] dependantTables;
    private List<CompoundUniqueContraint> compoundUniqueConstraints;
    private HashMap<String, String> extraAttrMap = new HashMap();
    private String baseTable;
    private TableName baseTableFull;
    private BeanReflect beanReflect;
    private Class<?> factoryType;
    private List<BeanPersistController> persistControllers = new ArrayList<BeanPersistController>();
    private List<BeanPersistListener<T>> persistListeners = new ArrayList<BeanPersistListener<T>>();
    private List<BeanQueryAdapter> queryAdapters = new ArrayList<BeanQueryAdapter>();
    private ReferenceOptions referenceOptions;
    private BeanFinder<T> beanFinder;
    private Query.UseIndex useIndex;
    private IndexDefn<?> indexDefn;
    private ArrayList<DeployTableJoin> tableJoinList = new ArrayList();
    private InheritInfo inheritInfo;
    private String name;
    private boolean processedRawSqlExtend;

    public DeployBeanDescriptor(Class<T> beanType) {
        this.beanType = beanType;
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.beanType.getModifiers());
    }

    public Query.UseIndex getUseIndex() {
        return this.useIndex;
    }

    public void setUseIndex(Query.UseIndex useIndex) {
        this.useIndex = useIndex;
    }

    public IndexDefn<?> getIndexDefn() {
        return this.indexDefn;
    }

    public void setIndexDefn(IndexDefn<?> indexDefn) {
        this.indexDefn = indexDefn;
    }

    public boolean isScalaObject() {
        Class<?>[] interfaces = this.beanType.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            String iname = interfaces[i].getName();
            if (!I_SCALAOBJECT.equals(iname)) continue;
            return true;
        }
        return false;
    }

    public Collection<DRawSqlMeta> getRawSqlMeta() {
        if (!this.processedRawSqlExtend) {
            this.rawSqlProcessExtend();
            this.processedRawSqlExtend = true;
        }
        return this.rawSqlMetas.values();
    }

    private void rawSqlProcessExtend() {
        for (DRawSqlMeta rawSqlMeta : this.rawSqlMetas.values()) {
            String extend = rawSqlMeta.getExtend();
            if (extend == null) continue;
            DRawSqlMeta parentQuery = this.rawSqlMetas.get(extend);
            if (parentQuery == null) {
                throw new RuntimeException("parent query [" + extend + "] not found for sql-select " + rawSqlMeta.getName());
            }
            rawSqlMeta.extend(parentQuery);
        }
    }

    public DeployBeanTable createDeployBeanTable() {
        DeployBeanTable beanTable = new DeployBeanTable(this.getBeanType());
        beanTable.setBaseTable(this.baseTable);
        beanTable.setIdProperties(this.propertiesId());
        return beanTable;
    }

    public boolean checkReadAndWriteMethods() {
        if (this.isMeta()) {
            return true;
        }
        boolean missingMethods = false;
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (prop.isTransient()) continue;
            String m = "";
            if (prop.getReadMethod() == null) {
                m = m + " missing readMethod ";
            }
            if (prop.getWriteMethod() == null) {
                m = m + " missing writeMethod ";
            }
            if ("".equals(m)) continue;
            m = m + ". Should it be transient?";
            String msg = "Bean property " + this.getFullName() + "." + prop.getName() + " has " + m;
            logger.log(Level.SEVERE, msg);
            missingMethods = true;
        }
        return !missingMethods;
    }

    public void setEntityType(BeanDescriptor.EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isEmbedded() {
        return BeanDescriptor.EntityType.EMBEDDED.equals((Object)this.entityType);
    }

    public boolean isBaseTableType() {
        BeanDescriptor.EntityType et = this.getEntityType();
        return BeanDescriptor.EntityType.ORM.equals((Object)et);
    }

    public BeanDescriptor.EntityType getEntityType() {
        if (this.entityType == null) {
            this.entityType = this.isMeta() ? BeanDescriptor.EntityType.META : BeanDescriptor.EntityType.ORM;
        }
        return this.entityType;
    }

    private boolean isMeta() {
        return this.beanType.getName().startsWith(META_BEAN_PREFIX);
    }

    public void add(DRawSqlMeta rawSqlMeta) {
        this.rawSqlMetas.put(rawSqlMeta.getName(), rawSqlMeta);
        if ("default".equals(rawSqlMeta.getName())) {
            this.setEntityType(BeanDescriptor.EntityType.SQL);
        }
    }

    public void add(DeployNamedUpdate namedUpdate) {
        this.namedUpdates.put(namedUpdate.getName(), namedUpdate);
    }

    public void add(DeployNamedQuery namedQuery) {
        this.namedQueries.put(namedQuery.getName(), namedQuery);
        if ("default".equals(namedQuery.getName())) {
            this.setEntityType(BeanDescriptor.EntityType.SQL);
        }
    }

    public Map<String, DeployNamedQuery> getNamedQueries() {
        return this.namedQueries;
    }

    public Map<String, DeployNamedUpdate> getNamedUpdates() {
        return this.namedUpdates;
    }

    public BeanReflect getBeanReflect() {
        return this.beanReflect;
    }

    public Class<T> getBeanType() {
        return this.beanType;
    }

    public Class<?> getFactoryType() {
        return this.factoryType;
    }

    public void setFactoryType(Class<?> factoryType) {
        this.factoryType = factoryType;
    }

    public void setBeanReflect(BeanReflect beanReflect) {
        this.beanReflect = beanReflect;
    }

    public InheritInfo getInheritInfo() {
        return this.inheritInfo;
    }

    public void setInheritInfo(InheritInfo inheritInfo) {
        this.inheritInfo = inheritInfo;
    }

    public ReferenceOptions getReferenceOptions() {
        return this.referenceOptions;
    }

    public void setReferenceOptions(ReferenceOptions referenceOptions) {
        this.referenceOptions = referenceOptions;
    }

    public DeployBeanPropertyAssocOne<?> getUnidirectional() {
        return this.unidirectional;
    }

    public void setUnidirectional(DeployBeanPropertyAssocOne<?> unidirectional) {
        this.unidirectional = unidirectional;
    }

    public ConcurrencyMode getConcurrencyMode() {
        return this.concurrencyMode;
    }

    public void setConcurrencyMode(ConcurrencyMode concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }

    public String getLdapBaseDn() {
        return this.ldapBaseDn;
    }

    public void setLdapBaseDn(String ldapBaseDn) {
        this.ldapBaseDn = ldapBaseDn;
    }

    public String[] getLdapObjectclasses() {
        return this.ldapObjectclasses;
    }

    public void setLdapObjectclasses(String[] ldapObjectclasses) {
        this.ldapObjectclasses = ldapObjectclasses;
    }

    public boolean isUpdateChangesOnly() {
        return this.updateChangesOnly;
    }

    public void setUpdateChangesOnly(boolean updateChangesOnly) {
        this.updateChangesOnly = updateChangesOnly;
    }

    public String[] getDependantTables() {
        return this.dependantTables;
    }

    public void addCompoundUniqueConstraint(CompoundUniqueContraint c) {
        if (this.compoundUniqueConstraints == null) {
            this.compoundUniqueConstraints = new ArrayList<CompoundUniqueContraint>();
        }
        this.compoundUniqueConstraints.add(c);
    }

    public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
        if (this.compoundUniqueConstraints == null) {
            return null;
        }
        return this.compoundUniqueConstraints.toArray(new CompoundUniqueContraint[this.compoundUniqueConstraints.size()]);
    }

    public void setDependantTables(String[] dependantTables) {
        this.dependantTables = dependantTables;
    }

    public BeanFinder<T> getBeanFinder() {
        return this.beanFinder;
    }

    public void setBeanFinder(BeanFinder<T> beanFinder) {
        this.beanFinder = beanFinder;
    }

    public BeanPersistController getPersistController() {
        if (this.persistControllers.size() == 0) {
            return null;
        }
        if (this.persistControllers.size() == 1) {
            return this.persistControllers.get(0);
        }
        return new ChainedBeanPersistController(this.persistControllers);
    }

    public BeanPersistListener<T> getPersistListener() {
        if (this.persistListeners.size() == 0) {
            return null;
        }
        if (this.persistListeners.size() == 1) {
            return this.persistListeners.get(0);
        }
        return new ChainedBeanPersistListener<T>(this.persistListeners);
    }

    public BeanQueryAdapter getQueryAdapter() {
        if (this.queryAdapters.size() == 0) {
            return null;
        }
        if (this.queryAdapters.size() == 1) {
            return this.queryAdapters.get(0);
        }
        return new ChainedBeanQueryAdapter(this.queryAdapters);
    }

    public void addPersistController(BeanPersistController controller) {
        this.persistControllers.add(controller);
    }

    public void addPersistListener(BeanPersistListener<T> listener) {
        this.persistListeners.add(listener);
    }

    public void addQueryAdapter(BeanQueryAdapter queryAdapter) {
        this.queryAdapters.add(queryAdapter);
    }

    public boolean isUseIdGenerator() {
        return this.idType == IdType.GENERATOR;
    }

    public String getBaseTable() {
        return this.baseTable;
    }

    public TableName getBaseTableFull() {
        return this.baseTableFull;
    }

    public void setBaseTable(TableName baseTableFull) {
        this.baseTableFull = baseTableFull;
        this.baseTable = baseTableFull == null ? null : baseTableFull.getQualifiedName();
    }

    public void sortProperties() {
        ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
        list.addAll(this.propMap.values());
        Collections.sort(list, PROP_ORDER);
        this.propMap = new LinkedHashMap(list.size());
        for (int i = 0; i < list.size(); ++i) {
            this.addBeanProperty((DeployBeanProperty)list.get(i));
        }
    }

    public DeployBeanProperty addBeanProperty(DeployBeanProperty prop) {
        return this.propMap.put(prop.getName(), prop);
    }

    public DeployBeanProperty getBeanProperty(String propName) {
        return this.propMap.get(propName);
    }

    public Map<String, String> getExtraAttributeMap() {
        return this.extraAttrMap;
    }

    public String getExtraAttribute(String key) {
        return this.extraAttrMap.get(key);
    }

    public void setExtraAttribute(String key, String value) {
        this.extraAttrMap.put(key, value);
    }

    public String getFullName() {
        return this.beanType.getName();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdType getIdType() {
        return this.idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getSelectLastInsertedId() {
        return this.selectLastInsertedId;
    }

    public void setSelectLastInsertedId(String selectLastInsertedId) {
        this.selectLastInsertedId = selectLastInsertedId;
    }

    public String getIdGeneratorName() {
        return this.idGeneratorName;
    }

    public void setIdGeneratorName(String idGeneratorName) {
        this.idGeneratorName = idGeneratorName;
    }

    public IdGenerator getIdGenerator() {
        return this.idGenerator;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        if (idGenerator != null && idGenerator.isDbSequence()) {
            this.setSequenceName(idGenerator.getName());
        }
    }

    public String getLazyFetchIncludes() {
        return this.lazyFetchIncludes;
    }

    public void setLazyFetchIncludes(String lazyFetchIncludes) {
        if (lazyFetchIncludes != null && lazyFetchIncludes.length() > 0) {
            this.lazyFetchIncludes = lazyFetchIncludes;
        }
    }

    public String toString() {
        return this.getFullName();
    }

    public void addTableJoin(DeployTableJoin join) {
        this.tableJoinList.add(join);
    }

    public List<DeployTableJoin> getTableJoins() {
        return this.tableJoinList;
    }

    public Iterator<DeployBeanProperty> propertiesAll() {
        return this.propMap.values().iterator();
    }

    public String getDefaultSelectClause() {
        StringBuilder sb = new StringBuilder();
        boolean hasLazyFetch = false;
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (prop.isTransient() || prop instanceof DeployBeanPropertyAssocMany) continue;
            if (prop.isFetchEager()) {
                sb.append(prop.getName()).append(",");
                continue;
            }
            hasLazyFetch = true;
        }
        if (!hasLazyFetch) {
            return null;
        }
        String selectClause = sb.toString();
        return selectClause.substring(0, selectClause.length() - 1);
    }

    public String[] getDefaultSelectDbArray(Set<String> defaultSelect) {
        ArrayList<String> list = new ArrayList<String>();
        for (DeployBeanProperty p : this.propMap.values()) {
            if (defaultSelect != null) {
                if (!defaultSelect.contains(p.getName())) continue;
                list.add(p.getDbColumn());
                continue;
            }
            if (p.isTransient() || !p.isDbRead()) continue;
            list.add(p.getDbColumn());
        }
        return list.toArray(new String[list.size()]);
    }

    public Set<String> parseDefaultSelectClause(String rawList) {
        if (rawList == null) {
            return null;
        }
        String[] res = rawList.split(",");
        LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
        String temp = null;
        for (int i = 0; i < res.length; ++i) {
            temp = res[i].trim();
            if (temp.length() <= 0) continue;
            set.add(temp);
        }
        return Collections.unmodifiableSet(set);
    }

    public String getSinglePrimaryKeyColumn() {
        List<DeployBeanProperty> ids = this.propertiesId();
        if (ids.size() == 1) {
            DeployBeanProperty p = ids.get(0);
            if (p instanceof DeployBeanPropertyAssoc) {
                return null;
            }
            return p.getDbColumn();
        }
        return null;
    }

    public List<DeployBeanProperty> propertiesId() {
        ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>(2);
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (!prop.isId()) continue;
            list.add(prop);
        }
        return list;
    }

    public DeployBeanPropertyAssocOne<?> findJoinToTable(String tableName) {
        List<DeployBeanPropertyAssocOne<?>> assocOne = this.propertiesAssocOne();
        for (DeployBeanPropertyAssocOne<?> prop : assocOne) {
            DeployTableJoin tableJoin = prop.getTableJoin();
            if (tableJoin == null || !tableJoin.getTable().equalsIgnoreCase(tableName)) continue;
            return prop;
        }
        return null;
    }

    public List<DeployBeanPropertyAssocOne<?>> propertiesAssocOne() {
        ArrayList list = new ArrayList();
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (!(prop instanceof DeployBeanPropertyAssocOne) || prop.isEmbedded()) continue;
            list.add((DeployBeanPropertyAssocOne)prop);
        }
        return list;
    }

    public List<DeployBeanPropertyAssocMany<?>> propertiesAssocMany() {
        ArrayList list = new ArrayList();
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (!(prop instanceof DeployBeanPropertyAssocMany)) continue;
            list.add((DeployBeanPropertyAssocMany)prop);
        }
        return list;
    }

    public List<DeployBeanProperty> propertiesVersion() {
        ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (prop instanceof DeployBeanPropertyAssoc || prop.isId() || !prop.isVersionColumn()) continue;
            list.add(prop);
        }
        return list;
    }

    public List<DeployBeanProperty> propertiesBase() {
        ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
        for (DeployBeanProperty prop : this.propMap.values()) {
            if (prop instanceof DeployBeanPropertyAssoc || prop.isId()) continue;
            list.add(prop);
        }
        return list;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class PropOrder
    implements Comparator<DeployBeanProperty> {
        PropOrder() {
        }

        @Override
        public int compare(DeployBeanProperty o1, DeployBeanProperty o2) {
            int v2 = o1.getSortOrder();
            int v1 = o2.getSortOrder();
            return v1 < v2 ? -1 : (v1 == v2 ? 0 : 1);
        }
    }
}

