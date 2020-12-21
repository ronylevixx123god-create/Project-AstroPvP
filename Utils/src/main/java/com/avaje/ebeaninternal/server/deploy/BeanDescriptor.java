/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.config.dbplatform.IdGenerator;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
import com.avaje.ebean.validation.factory.Validator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.api.SpiUpdatePlan;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanRefreshFromCacheHelp;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistController;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistListener;
import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.server.deploy.DeployPropertyParser;
import com.avaje.ebeaninternal.server.deploy.DeployUpdateParser;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.PersistListenerManager;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyLists;
import com.avaje.ebeaninternal.server.el.ElComparator;
import com.avaje.ebeaninternal.server.el.ElComparatorCompound;
import com.avaje.ebeaninternal.server.el.ElComparatorProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.persist.DmlUtil;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebeaninternal.util.SortByClause;
import com.avaje.ebeaninternal.util.SortByClauseParser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InvalidNameException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanDescriptor<T> {
    private static final Logger logger = Logger.getLogger(BeanDescriptor.class.getName());
    private final ConcurrentHashMap<Integer, SpiUpdatePlan> updatePlanCache = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, CQueryPlan> queryPlanCache = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, ElPropertyValue> elGetCache = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, ElComparator<T>> comparatorCache = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, BeanFkeyProperty> fkeyMap = new ConcurrentHashMap();
    private final String serverName;
    private final EntityType entityType;
    private final IdType idType;
    private final IdGenerator idGenerator;
    private final String sequenceName;
    private final String ldapBaseDn;
    private final String[] ldapObjectclasses;
    private final String selectLastInsertedId;
    private final boolean autoFetchTunable;
    private final String lazyFetchIncludes;
    private final ConcurrencyMode concurrencyMode;
    private final String[] dependantTables;
    private final CompoundUniqueContraint[] compoundUniqueConstraints;
    private final Map<String, String> extraAttrMap;
    private final String baseTable;
    private final BeanReflect beanReflect;
    private final LinkedHashMap<String, BeanProperty> propMap;
    private final LinkedHashMap<String, BeanProperty> propMapByDbColumn;
    private final Class<T> beanType;
    private final BeanDescriptorMap owner;
    private final Class<?> factoryType;
    private final boolean enhancedBean;
    private volatile BeanPersistController persistController;
    private volatile BeanPersistListener<T> persistListener;
    private volatile BeanQueryAdapter queryAdapter;
    private final BeanFinder<T> beanFinder;
    private final IndexDefn<?> luceneIndexDefn;
    private final TableJoin[] derivedTableJoins;
    private final InheritInfo inheritInfo;
    private final BeanProperty[] propertiesId;
    private final BeanProperty[] propertiesVersion;
    private final BeanProperty[] propertiesLocal;
    private final BeanPropertyAssocOne<?> unidirectional;
    private final int namesOfManyPropsHash;
    private final Set<String> namesOfManyProps;
    private final BeanPropertyAssocMany<?>[] propertiesMany;
    private final BeanPropertyAssocMany<?>[] propertiesManySave;
    private final BeanPropertyAssocMany<?>[] propertiesManyDelete;
    private final BeanPropertyAssocMany<?>[] propertiesManyToMany;
    private final BeanPropertyAssocOne<?>[] propertiesOne;
    private final BeanPropertyAssocOne<?>[] propertiesOneImported;
    private final BeanPropertyAssocOne<?>[] propertiesOneImportedSave;
    private final BeanPropertyAssocOne<?>[] propertiesOneImportedDelete;
    private final BeanPropertyAssocOne<?>[] propertiesOneExported;
    private final BeanPropertyAssocOne<?>[] propertiesOneExportedSave;
    private final BeanPropertyAssocOne<?>[] propertiesOneExportedDelete;
    private final BeanPropertyAssocOne<?>[] propertiesEmbedded;
    private final BeanProperty[] propertiesBaseScalar;
    private final BeanPropertyCompound[] propertiesBaseCompound;
    private final BeanProperty[] propertiesTransient;
    final BeanProperty[] propertiesNonTransient;
    private final BeanProperty propertyFirstVersion;
    private final BeanProperty propertySingleId;
    private final String fullName;
    private final Map<String, DeployNamedQuery> namedQueries;
    private final Map<String, DeployNamedUpdate> namedUpdates;
    private final boolean hasLocalValidation;
    private final boolean hasCascadeValidation;
    private final BeanProperty[] propertiesValidationLocal;
    private final BeanProperty[] propertiesValidationCascade;
    private final Validator[] beanValidators;
    private boolean saveRecurseSkippable;
    private boolean deleteRecurseSkippable;
    private final TypeManager typeManager;
    private final IdBinder idBinder;
    private String idBinderInLHSSql;
    private String idBinderIdSql;
    private String deleteByIdSql;
    private String deleteByIdInSql;
    private final String name;
    private final String baseTableAlias;
    private final boolean updateChangesOnly;
    private final ServerCacheManager cacheManager;
    private final ReferenceOptions referenceOptions;
    private final String defaultSelectClause;
    private final Set<String> defaultSelectClauseSet;
    private final String[] defaultSelectDbArray;
    private final String descriptorId;
    private final Query.UseIndex useIndex;
    private SpiEbeanServer ebeanServer;
    private ServerCache beanCache;
    private ServerCache queryCache;
    private LIndex luceneIndex;
    private Set<IndexInvalidate> luceneIndexInvalidations;

    public BeanDescriptor(BeanDescriptorMap owner, TypeManager typeManager, DeployBeanDescriptor<T> deploy, String descriptorId) {
        this.owner = owner;
        this.cacheManager = owner.getCacheManager();
        this.serverName = owner.getServerName();
        this.luceneIndexDefn = deploy.getIndexDefn();
        this.entityType = deploy.getEntityType();
        this.name = InternString.intern(deploy.getName());
        this.baseTableAlias = "t0";
        this.fullName = InternString.intern(deploy.getFullName());
        this.descriptorId = descriptorId;
        this.useIndex = deploy.getUseIndex();
        this.typeManager = typeManager;
        this.beanType = deploy.getBeanType();
        this.factoryType = deploy.getFactoryType();
        this.enhancedBean = this.beanType.equals(this.factoryType);
        this.namedQueries = deploy.getNamedQueries();
        this.namedUpdates = deploy.getNamedUpdates();
        this.inheritInfo = deploy.getInheritInfo();
        this.beanFinder = deploy.getBeanFinder();
        this.persistController = deploy.getPersistController();
        this.persistListener = deploy.getPersistListener();
        this.queryAdapter = deploy.getQueryAdapter();
        this.referenceOptions = deploy.getReferenceOptions();
        this.defaultSelectClause = deploy.getDefaultSelectClause();
        this.defaultSelectClauseSet = deploy.parseDefaultSelectClause(this.defaultSelectClause);
        this.defaultSelectDbArray = deploy.getDefaultSelectDbArray(this.defaultSelectClauseSet);
        this.idType = deploy.getIdType();
        this.idGenerator = deploy.getIdGenerator();
        this.ldapBaseDn = deploy.getLdapBaseDn();
        this.ldapObjectclasses = deploy.getLdapObjectclasses();
        this.sequenceName = deploy.getSequenceName();
        this.selectLastInsertedId = deploy.getSelectLastInsertedId();
        this.lazyFetchIncludes = InternString.intern(deploy.getLazyFetchIncludes());
        this.concurrencyMode = deploy.getConcurrencyMode();
        this.updateChangesOnly = deploy.isUpdateChangesOnly();
        this.dependantTables = deploy.getDependantTables();
        this.compoundUniqueConstraints = deploy.getCompoundUniqueConstraints();
        this.extraAttrMap = deploy.getExtraAttributeMap();
        this.baseTable = InternString.intern(deploy.getBaseTable());
        this.beanReflect = deploy.getBeanReflect();
        this.autoFetchTunable = EntityType.ORM.equals((Object)this.entityType) && this.beanFinder == null;
        DeployBeanPropertyLists listHelper = new DeployBeanPropertyLists(owner, this, deploy);
        this.propMap = listHelper.getPropertyMap();
        this.propMapByDbColumn = this.getReverseMap(this.propMap);
        this.propertiesTransient = listHelper.getTransients();
        this.propertiesNonTransient = listHelper.getNonTransients();
        this.propertiesBaseScalar = listHelper.getBaseScalar();
        this.propertiesBaseCompound = listHelper.getBaseCompound();
        this.propertiesId = listHelper.getId();
        this.propertiesVersion = listHelper.getVersion();
        this.propertiesEmbedded = listHelper.getEmbedded();
        this.propertiesLocal = listHelper.getLocal();
        this.unidirectional = listHelper.getUnidirectional();
        this.propertiesOne = listHelper.getOnes();
        this.propertiesOneExported = listHelper.getOneExported();
        this.propertiesOneExportedSave = listHelper.getOneExportedSave();
        this.propertiesOneExportedDelete = listHelper.getOneExportedDelete();
        this.propertiesOneImported = listHelper.getOneImported();
        this.propertiesOneImportedSave = listHelper.getOneImportedSave();
        this.propertiesOneImportedDelete = listHelper.getOneImportedDelete();
        this.propertiesMany = listHelper.getMany();
        this.propertiesManySave = listHelper.getManySave();
        this.propertiesManyDelete = listHelper.getManyDelete();
        this.propertiesManyToMany = listHelper.getManyToMany();
        this.namesOfManyProps = this.deriveManyPropNames();
        this.namesOfManyPropsHash = ((Object)this.namesOfManyProps).hashCode();
        this.derivedTableJoins = listHelper.getTableJoin();
        this.propertyFirstVersion = listHelper.getFirstVersion();
        this.propertySingleId = this.propertiesId.length == 1 ? this.propertiesId[0] : null;
        this.saveRecurseSkippable = 0 == this.propertiesOneExportedSave.length + this.propertiesOneImportedSave.length + this.propertiesManySave.length;
        this.deleteRecurseSkippable = 0 == this.propertiesOneExportedDelete.length + this.propertiesOneImportedDelete.length + this.propertiesManyDelete.length;
        this.propertiesValidationLocal = listHelper.getPropertiesWithValidators(false);
        this.propertiesValidationCascade = listHelper.getPropertiesWithValidators(true);
        this.beanValidators = listHelper.getBeanValidators();
        this.hasLocalValidation = this.propertiesValidationLocal.length > 0 || this.beanValidators.length > 0;
        this.hasCascadeValidation = this.propertiesValidationCascade.length > 0 || this.beanValidators.length > 0;
        this.idBinder = owner.createIdBinder(this.propertiesId);
    }

    private LinkedHashMap<String, BeanProperty> getReverseMap(LinkedHashMap<String, BeanProperty> propMap) {
        LinkedHashMap<String, BeanProperty> revMap = new LinkedHashMap<String, BeanProperty>(propMap.size() * 2);
        for (BeanProperty prop : propMap.values()) {
            if (prop.getDbColumn() == null) continue;
            revMap.put(prop.getDbColumn(), prop);
        }
        return revMap;
    }

    public void setEbeanServer(SpiEbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
        for (int i = 0; i < this.propertiesMany.length; ++i) {
            this.propertiesMany[i].setLoader(ebeanServer);
        }
    }

    public T createCopy(Object source, CopyContext ctx, int maxDepth) {
        Object destBean = this.createBean(ctx.isVanillaMode());
        for (int j = 0; j < this.propertiesId.length; ++j) {
            this.propertiesId[j].copyProperty(source, destBean, ctx, maxDepth);
        }
        Object destId = this.getId(destBean);
        Object existing = ctx.putIfAbsent(destId, destBean);
        if (existing != null) {
            return (T)existing;
        }
        for (int i = 0; i < this.propertiesNonTransient.length; ++i) {
            this.propertiesNonTransient[i].copyProperty(source, destBean, ctx, maxDepth);
        }
        if (destBean instanceof EntityBean) {
            EntityBeanIntercept copyEbi = ((EntityBean)destBean)._ebean_getIntercept();
            if (source instanceof EntityBean) {
                EntityBeanIntercept origEbi = ((EntityBean)source)._ebean_getIntercept();
                origEbi.copyStateTo(copyEbi);
            }
            copyEbi.setBeanLoader(0, this.ebeanServer);
            copyEbi.setPersistenceContext(ctx.getPersistenceContext());
            if (ctx.isSharing()) {
                copyEbi.setSharedInstance();
            }
        }
        return (T)destBean;
    }

    public T createCopyForUpdate(Object orig, boolean vanilla) {
        return this.createCopy(orig, new CopyContext(false, false), 3);
    }

    public T createCopyForSharing(Object orig) {
        return this.createCopy(orig, new CopyContext(false, true), 3);
    }

    public ConcurrencyMode determineConcurrencyMode(Object bean) {
        if (this.propertyFirstVersion == null) {
            return ConcurrencyMode.NONE;
        }
        Object v = this.propertyFirstVersion.getValue(bean);
        return v == null ? ConcurrencyMode.NONE : ConcurrencyMode.VERSION;
    }

    public Set<String> getDirtyEmbeddedProperties(Object bean) {
        HashSet<String> dirtyProperties = null;
        for (int i = 0; i < this.propertiesEmbedded.length; ++i) {
            Object embValue = this.propertiesEmbedded[i].getValue(bean);
            if (embValue instanceof EntityBean) {
                if (!((EntityBean)embValue)._ebean_getIntercept().isDirty()) continue;
                if (dirtyProperties == null) {
                    dirtyProperties = new HashSet();
                }
                dirtyProperties.add(this.propertiesEmbedded[i].getName());
                continue;
            }
            if (dirtyProperties == null) {
                dirtyProperties = new HashSet<String>();
            }
            dirtyProperties.add(this.propertiesEmbedded[i].getName());
        }
        return dirtyProperties;
    }

    public Set<String> determineLoadedProperties(Object bean) {
        HashSet<String> nonNullProps = new HashSet<String>();
        for (int j = 0; j < this.propertiesId.length; ++j) {
            if (this.propertiesId[j].getValue(bean) == null) continue;
            nonNullProps.add(this.propertiesId[j].getName());
        }
        for (int i = 0; i < this.propertiesNonTransient.length; ++i) {
            if (this.propertiesNonTransient[i].getValue(bean) == null) continue;
            nonNullProps.add(this.propertiesNonTransient[i].getName());
        }
        return nonNullProps;
    }

    public SpiEbeanServer getEbeanServer() {
        return this.ebeanServer;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public IndexDefn<?> getLuceneIndexDefn() {
        return this.luceneIndexDefn;
    }

    public Query.UseIndex getUseIndex() {
        return this.useIndex;
    }

    public LIndex getLuceneIndex() {
        return this.luceneIndex;
    }

    public void setLuceneIndex(LIndex luceneIndex) {
        this.luceneIndex = luceneIndex;
    }

    public void addIndexInvalidate(IndexInvalidate e) {
        if (this.luceneIndexInvalidations == null) {
            this.luceneIndexInvalidations = new HashSet<IndexInvalidate>();
        }
        this.luceneIndexInvalidations.add(e);
    }

    public boolean isNotifyLucene(TransactionEvent txnEvent) {
        if (this.luceneIndexInvalidations != null) {
            for (IndexInvalidate invalidate : this.luceneIndexInvalidations) {
                txnEvent.addIndexInvalidate(invalidate);
            }
        }
        return this.luceneIndex != null;
    }

    public void initialiseId() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("BeanDescriptor initialise " + this.fullName);
        }
        if (this.inheritInfo != null) {
            this.inheritInfo.setDescriptor(this);
        }
        if (this.isEmbedded()) {
            Iterator<BeanProperty> it = this.propertiesAll();
            while (it.hasNext()) {
                BeanProperty prop = it.next();
                prop.initialise();
            }
        } else {
            BeanProperty[] idProps = this.propertiesId();
            for (int i = 0; i < idProps.length; ++i) {
                idProps[i].initialise();
            }
        }
    }

    public void initialiseOther() {
        if (!this.isEmbedded()) {
            Iterator<BeanProperty> it = this.propertiesAll();
            while (it.hasNext()) {
                BeanProperty prop = it.next();
                if (prop.isId()) continue;
                prop.initialise();
            }
        }
        if (this.unidirectional != null) {
            this.unidirectional.initialise();
        }
        this.idBinder.initialise();
        this.idBinderInLHSSql = this.idBinder.getBindIdInSql(this.baseTableAlias);
        this.idBinderIdSql = this.idBinder.getBindIdSql(this.baseTableAlias);
        String idBinderInLHSSqlNoAlias = this.idBinder.getBindIdInSql(null);
        String idEqualsSql = this.idBinder.getBindIdSql(null);
        this.deleteByIdSql = "delete from " + this.baseTable + " where " + idEqualsSql;
        this.deleteByIdInSql = "delete from " + this.baseTable + " where " + idBinderInLHSSqlNoAlias + " ";
        if (!this.isEmbedded()) {
            for (DeployNamedUpdate namedUpdate : this.namedUpdates.values()) {
                DeployUpdateParser parser = new DeployUpdateParser(this);
                namedUpdate.initialise(parser);
            }
        }
    }

    public void initInheritInfo() {
        if (this.inheritInfo != null) {
            if (this.saveRecurseSkippable) {
                this.saveRecurseSkippable = this.inheritInfo.isSaveRecurseSkippable();
            }
            if (this.deleteRecurseSkippable) {
                this.deleteRecurseSkippable = this.inheritInfo.isDeleteRecurseSkippable();
            }
        }
    }

    public void cacheInitialise() {
        if (this.referenceOptions != null && this.referenceOptions.isUseCache()) {
            this.beanCache = this.cacheManager.getBeanCache(this.beanType);
        }
    }

    protected boolean hasInheritance() {
        return this.inheritInfo != null;
    }

    protected boolean isDynamicSubclass() {
        return !this.beanType.equals(this.factoryType);
    }

    public void setLdapObjectClasses(Attributes attributes) {
        if (this.ldapObjectclasses != null) {
            BasicAttribute ocAttrs = new BasicAttribute("objectclass");
            for (int i = 0; i < this.ldapObjectclasses.length; ++i) {
                ocAttrs.add(this.ldapObjectclasses[i]);
            }
            attributes.put(ocAttrs);
        }
    }

    public Attributes createAttributes() {
        BasicAttributes attrs = new BasicAttributes(true);
        this.setLdapObjectClasses(attrs);
        return attrs;
    }

    public String getLdapBaseDn() {
        return this.ldapBaseDn;
    }

    public LdapName createLdapNameById(Object id) throws InvalidNameException {
        LdapName baseDn = new LdapName(this.ldapBaseDn);
        this.idBinder.createLdapNameById(baseDn, id);
        return baseDn;
    }

    public LdapName createLdapName(Object bean) {
        try {
            LdapName name = new LdapName(this.ldapBaseDn);
            if (bean != null) {
                this.idBinder.createLdapNameByBean(name, bean);
            }
            return name;
        }
        catch (InvalidNameException e) {
            throw new LdapPersistenceException(e);
        }
    }

    public SqlUpdate deleteById(Object id, List<Object> idList) {
        if (id != null) {
            return this.deleteById(id);
        }
        return this.deleteByIdList(idList);
    }

    private SqlUpdate deleteByIdList(List<Object> idList) {
        StringBuilder sb = new StringBuilder(this.deleteByIdInSql);
        String inClause = this.idBinder.getIdInValueExprDelete(idList.size());
        sb.append(inClause);
        DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
        for (int i = 0; i < idList.size(); ++i) {
            this.idBinder.bindId(delete, idList.get(i));
        }
        return delete;
    }

    private SqlUpdate deleteById(Object id) {
        DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByIdSql);
        Object[] bindValues = this.idBinder.getBindValues(id);
        for (int i = 0; i < bindValues.length; ++i) {
            sqlDelete.addParameter(bindValues[i]);
        }
        return sqlDelete;
    }

    public void add(BeanFkeyProperty fkey) {
        this.fkeyMap.put(fkey.getName(), fkey);
    }

    public void initialiseFkeys() {
        for (int i = 0; i < this.propertiesOneImported.length; ++i) {
            this.propertiesOneImported[i].addFkey();
        }
    }

    public boolean calculateUseCache(Boolean queryUseCache) {
        if (queryUseCache != null) {
            return queryUseCache;
        }
        if (this.referenceOptions != null) {
            return this.referenceOptions.isUseCache();
        }
        return false;
    }

    public boolean calculateReadOnly(Boolean queryReadOnly) {
        if (queryReadOnly != null) {
            return queryReadOnly;
        }
        if (this.referenceOptions != null) {
            return this.referenceOptions.isReadOnly();
        }
        return false;
    }

    public ReferenceOptions getReferenceOptions() {
        return this.referenceOptions;
    }

    public EncryptKey getEncryptKey(BeanProperty p) {
        return this.owner.getEncryptKey(this.baseTable, p.getDbColumn());
    }

    public EncryptKey getEncryptKey(String tableName, String columnName) {
        return this.owner.getEncryptKey(tableName, columnName);
    }

    public void runCacheWarming() {
        if (this.referenceOptions == null) {
            return;
        }
        String warmingQuery = this.referenceOptions.getWarmingQuery();
        if (warmingQuery != null && warmingQuery.trim().length() > 0) {
            Query<T> query = this.ebeanServer.createQuery(this.beanType, warmingQuery);
            query.setUseCache(true);
            query.setReadOnly(true);
            query.setLoadBeanCache(true);
            List<T> list = query.findList();
            if (logger.isLoggable(Level.INFO)) {
                String msg = "Loaded " + this.beanType + " cache with [" + list.size() + "] beans";
                logger.info(msg);
            }
        }
    }

    public boolean hasDefaultSelectClause() {
        return this.defaultSelectClause != null;
    }

    public String getDefaultSelectClause() {
        return this.defaultSelectClause;
    }

    public Set<String> getDefaultSelectClauseSet() {
        return this.defaultSelectClauseSet;
    }

    public String[] getDefaultSelectDbArray() {
        return this.defaultSelectDbArray;
    }

    public boolean isInheritanceRoot() {
        return this.inheritInfo == null || this.inheritInfo.isRoot();
    }

    public boolean isQueryCaching() {
        return this.queryCache != null;
    }

    public boolean isBeanCaching() {
        return this.beanCache != null;
    }

    public boolean isCacheNotify(boolean isInsertRequest) {
        if (isInsertRequest) {
            return this.queryCache != null;
        }
        return this.beanCache != null || this.queryCache != null;
    }

    public boolean isUsingL2Cache() {
        return this.beanCache != null || this.luceneIndex != null;
    }

    public boolean isLuceneIndexed() {
        return this.luceneIndex != null;
    }

    public void cacheNotify(TransactionEventTable.TableIUD tableIUD) {
        if (tableIUD.isUpdateOrDelete()) {
            this.cacheClear();
        }
        this.queryCacheClear();
    }

    public void queryCacheClear() {
        if (this.queryCache != null) {
            this.queryCache.clear();
        }
    }

    public BeanCollection<T> queryCacheGet(Object id) {
        if (this.queryCache == null) {
            return null;
        }
        return (BeanCollection)this.queryCache.get(id);
    }

    public void queryCachePut(Object id, BeanCollection<T> query) {
        if (this.queryCache == null) {
            this.queryCache = this.cacheManager.getQueryCache(this.beanType);
        }
        this.queryCache.put(id, query);
    }

    public void cacheClear() {
        if (this.beanCache != null) {
            this.beanCache.clear();
        }
    }

    public T cachePutObject(Object bean) {
        T cacheBean = this.createCopyForSharing(bean);
        return this.cachePut(cacheBean, true);
    }

    public T cachePut(T bean, boolean alreadyShared) {
        if (this.beanCache == null) {
            this.beanCache = this.cacheManager.getBeanCache(this.beanType);
        }
        if (!alreadyShared) {
            bean = this.createCopyForSharing(bean);
        }
        Object id = this.getId(bean);
        return (T)this.beanCache.put(id, bean);
    }

    public T cacheGet(Object id) {
        if (this.beanCache == null) {
            return null;
        }
        return (T)this.beanCache.get(id);
    }

    public void cacheRemove(Object id) {
        if (this.beanCache != null) {
            this.beanCache.remove(id);
        }
    }

    public String getBaseTableAlias() {
        return this.baseTableAlias;
    }

    public boolean refreshFromCache(EntityBeanIntercept ebi, Object id) {
        T cacheBean;
        if (ebi.isUseCache() && ebi.isReference() && (cacheBean = this.cacheGet(id)) != null) {
            String lazyLoadProperty = ebi.getLazyLoadProperty();
            Set<String> loadedProps = ((EntityBean)cacheBean)._ebean_getIntercept().getLoadedProps();
            if (loadedProps == null || loadedProps.contains(lazyLoadProperty)) {
                this.refreshFromCacheBean(ebi, cacheBean, true);
                return true;
            }
        }
        return false;
    }

    private void refreshFromCacheBean(EntityBeanIntercept ebi, Object cacheBean, boolean isLazyLoad) {
        new BeanRefreshFromCacheHelp(this, ebi, cacheBean, isLazyLoad).refresh();
    }

    public void preAllocateIds(int batchSize) {
        if (this.idGenerator != null) {
            this.idGenerator.preAllocateIds(batchSize);
        }
    }

    public Object nextId(Transaction t) {
        if (this.idGenerator != null) {
            return this.idGenerator.nextId(t);
        }
        return null;
    }

    public DeployPropertyParser createDeployPropertyParser() {
        return new DeployPropertyParser(this);
    }

    public String convertOrmUpdateToSql(String ormUpdateStatement) {
        return new DeployUpdateParser(this).parse(ormUpdateStatement);
    }

    public void clearQueryStatistics() {
        for (CQueryPlan queryPlan : this.queryPlanCache.values()) {
            queryPlan.resetStatistics();
        }
    }

    public void postLoad(Object bean, Set<String> includedProperties) {
        BeanPersistController c = this.persistController;
        if (c != null) {
            c.postLoad(bean, includedProperties);
        }
    }

    public Iterator<CQueryPlan> queryPlans() {
        return this.queryPlanCache.values().iterator();
    }

    public CQueryPlan getQueryPlan(Integer key) {
        return this.queryPlanCache.get(key);
    }

    public void putQueryPlan(Integer key, CQueryPlan plan) {
        this.queryPlanCache.put(key, plan);
    }

    public SpiUpdatePlan getUpdatePlan(Integer key) {
        return this.updatePlanCache.get(key);
    }

    public void putUpdatePlan(Integer key, SpiUpdatePlan plan) {
        this.updatePlanCache.put(key, plan);
    }

    public TypeManager getTypeManager() {
        return this.typeManager;
    }

    public boolean isUpdateChangesOnly() {
        return this.updateChangesOnly;
    }

    public boolean isSaveRecurseSkippable() {
        return this.saveRecurseSkippable;
    }

    public boolean isDeleteRecurseSkippable() {
        return this.deleteRecurseSkippable;
    }

    public boolean hasLocalValidation() {
        return this.hasLocalValidation;
    }

    public boolean hasCascadeValidation() {
        return this.hasCascadeValidation;
    }

    public InvalidValue validate(boolean cascade, Object bean) {
        Object value;
        List<InvalidValue> errs;
        if (!this.hasCascadeValidation) {
            return null;
        }
        ArrayList<InvalidValue> errList = null;
        Set<String> loadedProps = null;
        if (bean instanceof EntityBean) {
            EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
            loadedProps = ebi.getLoadedProps();
        }
        if (loadedProps != null) {
            for (String propName : loadedProps) {
                BeanProperty property = this.getBeanProperty(propName);
                if (property == null || !property.hasValidationRules(cascade) || (errs = property.validate(cascade, value = property.getValue(bean))) == null) continue;
                if (errList == null) {
                    errList = new ArrayList();
                }
                errList.addAll(errs);
            }
        } else {
            BeanProperty[] props = cascade ? this.propertiesValidationCascade : this.propertiesValidationLocal;
            for (int i = 0; i < props.length; ++i) {
                BeanProperty prop = props[i];
                value = prop.getValue(bean);
                errs = prop.validate(cascade, value);
                if (errs == null) continue;
                if (errList == null) {
                    errList = new ArrayList();
                }
                errList.addAll(errs);
            }
        }
        for (int i = 0; i < this.beanValidators.length; ++i) {
            if (this.beanValidators[i].isValid(bean)) continue;
            if (errList == null) {
                errList = new ArrayList<InvalidValue>();
            }
            Validator v = this.beanValidators[i];
            errList.add(new InvalidValue(v.getKey(), v.getAttributes(), this.getFullName(), null, bean));
        }
        if (errList == null) {
            return null;
        }
        return new InvalidValue(null, this.getFullName(), bean, InvalidValue.toArray(errList));
    }

    public BeanPropertyAssocMany<?> getManyProperty(SpiQuery<?> query) {
        OrmQueryDetail detail = query.getDetail();
        for (int i = 0; i < this.propertiesMany.length; ++i) {
            if (!detail.includes(this.propertiesMany[i].getName())) continue;
            return this.propertiesMany[i];
        }
        return null;
    }

    public IdBinder getIdBinder() {
        return this.idBinder;
    }

    public String getIdBinderIdSql() {
        return this.idBinderIdSql;
    }

    public String getIdBinderInLHSSql() {
        return this.idBinderInLHSSql;
    }

    public void bindId(DataBind dataBind, Object idValue) throws SQLException {
        this.idBinder.bindId(dataBind, idValue);
    }

    public Object[] getBindIdValues(Object idValue) {
        return this.idBinder.getBindValues(idValue);
    }

    public DeployNamedQuery getNamedQuery(String name) {
        return this.namedQueries.get(name);
    }

    public DeployNamedQuery addNamedQuery(DeployNamedQuery deployNamedQuery) {
        return this.namedQueries.put(deployNamedQuery.getName(), deployNamedQuery);
    }

    public DeployNamedUpdate getNamedUpdate(String name) {
        return this.namedUpdates.get(name);
    }

    public Object createBean(boolean vanillaMode) {
        return vanillaMode ? this.createVanillaBean() : this.createEntityBean();
    }

    public Object createVanillaBean() {
        return this.beanReflect.createVanillaBean();
    }

    public EntityBean createEntityBean() {
        try {
            EntityBean eb = (EntityBean)this.beanReflect.createEntityBean();
            return eb;
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }

    public T createReference(boolean vanillaMode, Object id, Object parent, ReferenceOptions options) {
        try {
            Object bean = this.createBean(vanillaMode);
            this.convertSetId(id, bean);
            if (!vanillaMode) {
                EntityBean eb = (EntityBean)bean;
                EntityBeanIntercept ebi = eb._ebean_getIntercept();
                ebi.setBeanLoader(0, this.ebeanServer);
                if (parent != null) {
                    ebi.setParentBean(parent);
                }
                if (options != null) {
                    ebi.setUseCache(options.isUseCache());
                    ebi.setReadOnly(options.isReadOnly());
                }
                ebi.setReference();
            }
            return (T)bean;
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }

    public BeanProperty getBeanPropertyFromDbColumn(String dbColumn) {
        return this.propMapByDbColumn.get(dbColumn);
    }

    public BeanProperty getBeanPropertyFromPath(String path) {
        String[] split = SplitName.splitBegin(path);
        if (split[1] == null) {
            return this._findBeanProperty(split[0]);
        }
        BeanPropertyAssoc assocProp = (BeanPropertyAssoc)this._findBeanProperty(split[0]);
        BeanDescriptor targetDesc = assocProp.getTargetDescriptor();
        return targetDesc.getBeanPropertyFromPath(split[1]);
    }

    public BeanDescriptor<?> getBeanDescriptor(String path) {
        if (path == null) {
            return this;
        }
        String[] splitBegin = SplitName.splitBegin(path);
        BeanProperty beanProperty = this.propMap.get(splitBegin[0]);
        if (beanProperty instanceof BeanPropertyAssoc) {
            BeanPropertyAssoc assocProp = (BeanPropertyAssoc)beanProperty;
            return assocProp.getTargetDescriptor().getBeanDescriptor(splitBegin[1]);
        }
        throw new RuntimeException("Error getting BeanDescriptor for path " + path + " from " + this.getFullName());
    }

    public <U> BeanDescriptor<U> getBeanDescriptor(Class<U> otherType) {
        return this.owner.getBeanDescriptor(otherType);
    }

    public BeanPropertyAssocOne<?> getUnidirectional() {
        if (this.unidirectional != null) {
            return this.unidirectional;
        }
        if (this.inheritInfo != null && !this.inheritInfo.isRoot()) {
            return this.inheritInfo.getParent().getBeanDescriptor().getUnidirectional();
        }
        return null;
    }

    public Object getValue(Object bean, String property) {
        return this.getBeanProperty(property).getValue(bean);
    }

    public boolean isUseIdGenerator() {
        return this.idGenerator != null;
    }

    public String getDescriptorId() {
        return this.descriptorId;
    }

    public Class<T> getBeanType() {
        return this.beanType;
    }

    public Class<?> getFactoryType() {
        return this.factoryType;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.fullName;
    }

    public Object getId(Object bean) {
        if (this.propertySingleId != null) {
            if (this.inheritInfo != null && !this.enhancedBean) {
                return this.propertySingleId.getValueViaReflection(bean);
            }
            return this.propertySingleId.getValue(bean);
        }
        LinkedHashMap<String, Object> idMap = new LinkedHashMap<String, Object>();
        for (int i = 0; i < this.propertiesId.length; ++i) {
            Object value = this.propertiesId[i].getValue(bean);
            idMap.put(this.propertiesId[i].getName(), value);
        }
        return idMap;
    }

    public boolean isComplexId() {
        return this.idBinder.isComplexId();
    }

    public String getDefaultOrderBy() {
        return this.idBinder.getDefaultOrderBy();
    }

    public Object convertId(Object idValue) {
        return this.idBinder.convertSetId(idValue, null);
    }

    public Object convertSetId(Object idValue, Object bean) {
        return this.idBinder.convertSetId(idValue, bean);
    }

    public BeanProperty getBeanProperty(String propName) {
        return this.propMap.get(propName);
    }

    public void sort(List<T> list, String sortByClause) {
        ElComparator<T> comparator = this.getElComparator(sortByClause);
        Collections.sort(list, comparator);
    }

    public ElComparator<T> getElComparator(String propNameOrSortBy) {
        ElComparator<T> c = this.comparatorCache.get(propNameOrSortBy);
        if (c == null) {
            c = this.createComparator(propNameOrSortBy);
            this.comparatorCache.put(propNameOrSortBy, c);
        }
        return c;
    }

    public boolean lazyLoadMany(EntityBeanIntercept ebi) {
        String lazyLoadProperty = ebi.getLazyLoadProperty();
        BeanProperty lazyLoadBeanProp = this.getBeanProperty(lazyLoadProperty);
        if (lazyLoadBeanProp instanceof BeanPropertyAssocMany) {
            BeanPropertyAssocMany manyProp = (BeanPropertyAssocMany)lazyLoadBeanProp;
            manyProp.createReference(ebi.getOwner());
            Set<String> loadedProps = ebi.getLoadedProps();
            HashSet<String> newLoadedProps = new HashSet<String>();
            if (loadedProps != null) {
                newLoadedProps.addAll(loadedProps);
            }
            newLoadedProps.add(lazyLoadProperty);
            ebi.setLoadedProps(newLoadedProps);
            ebi.setLoadedLazy();
            return true;
        }
        return false;
    }

    private ElComparator<T> createComparator(String sortByClause) {
        SortByClause sortBy = SortByClauseParser.parse(sortByClause);
        if (sortBy.size() == 1) {
            return this.createPropertyComparator(sortBy.getProperties().get(0));
        }
        ElComparator[] comparators = new ElComparator[sortBy.size()];
        List<SortByClause.Property> sortProps = sortBy.getProperties();
        for (int i = 0; i < sortProps.size(); ++i) {
            SortByClause.Property sortProperty = sortProps.get(i);
            comparators[i] = this.createPropertyComparator(sortProperty);
        }
        return new ElComparatorCompound(comparators);
    }

    private ElComparator<T> createPropertyComparator(SortByClause.Property sortProp) {
        ElPropertyValue elGetValue = this.getElGetValue(sortProp.getName());
        Boolean nullsHigh = sortProp.getNullsHigh();
        if (nullsHigh == null) {
            nullsHigh = Boolean.TRUE;
        }
        return new ElComparatorProperty(elGetValue, sortProp.isAscending(), nullsHigh);
    }

    public ElPropertyValue getElGetValue(String propName) {
        return this.getElPropertyValue(propName, false);
    }

    public ElPropertyDeploy getElPropertyDeploy(String propName) {
        ElPropertyDeploy fk = this.fkeyMap.get(propName);
        if (fk != null) {
            return fk;
        }
        return this.getElPropertyValue(propName, true);
    }

    private ElPropertyValue getElPropertyValue(String propName, boolean propertyDeploy) {
        ElPropertyValue elGetValue = this.elGetCache.get(propName);
        if (elGetValue == null) {
            elGetValue = this.buildElGetValue(propName, null, propertyDeploy);
            if (elGetValue == null) {
                return null;
            }
            if (elGetValue instanceof BeanFkeyProperty) {
                this.fkeyMap.put(propName, (BeanFkeyProperty)elGetValue);
            } else {
                this.elGetCache.put(propName, elGetValue);
            }
        }
        return elGetValue;
    }

    protected ElPropertyValue buildElGetValue(String propName, ElPropertyChainBuilder chain, boolean propertyDeploy) {
        BeanFkeyProperty fk;
        if (propertyDeploy && chain != null && (fk = this.fkeyMap.get(propName)) != null) {
            return fk.create(chain.getExpression());
        }
        int basePos = propName.indexOf(46);
        if (basePos > -1) {
            String baseName = propName.substring(0, basePos);
            String remainder = propName.substring(basePos + 1);
            BeanProperty assocProp = this._findBeanProperty(baseName);
            if (assocProp == null) {
                return null;
            }
            return assocProp.buildElPropertyValue(propName, remainder, chain, propertyDeploy);
        }
        BeanProperty property = this._findBeanProperty(propName);
        if (chain == null) {
            return property;
        }
        if (property == null) {
            throw new PersistenceException("No property found for [" + propName + "] in expression " + chain.getExpression());
        }
        if (property.containsMany()) {
            chain.setContainsMany(true);
        }
        return chain.add(property).build();
    }

    public BeanProperty findBeanProperty(String propName) {
        int basePos = propName.indexOf(46);
        if (basePos > -1) {
            String baseName = propName.substring(0, basePos);
            return this._findBeanProperty(baseName);
        }
        return this._findBeanProperty(propName);
    }

    private BeanProperty _findBeanProperty(String propName) {
        BeanProperty prop = this.propMap.get(propName);
        if (prop == null && this.inheritInfo != null) {
            return this.inheritInfo.findSubTypeProperty(propName);
        }
        return prop;
    }

    protected Object getBeanPropertyWithInheritance(Object bean, String propName) {
        BeanDescriptor<?> desc = this.getBeanDescriptor(bean.getClass());
        BeanProperty beanProperty = desc.findBeanProperty(propName);
        return beanProperty.getValue(bean);
    }

    public String getServerName() {
        return this.serverName;
    }

    public boolean isAutoFetchTunable() {
        return this.autoFetchTunable;
    }

    public InheritInfo getInheritInfo() {
        return this.inheritInfo;
    }

    public boolean isEmbedded() {
        return EntityType.EMBEDDED.equals((Object)this.entityType);
    }

    public boolean isBaseTableType() {
        return EntityType.ORM.equals((Object)this.entityType);
    }

    public ConcurrencyMode getConcurrencyMode() {
        return this.concurrencyMode;
    }

    public String[] getDependantTables() {
        return this.dependantTables;
    }

    public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
        return this.compoundUniqueConstraints;
    }

    public BeanPersistListener<T> getPersistListener() {
        return this.persistListener;
    }

    public BeanFinder<T> getBeanFinder() {
        return this.beanFinder;
    }

    public BeanQueryAdapter getQueryAdapter() {
        return this.queryAdapter;
    }

    public void deregister(BeanPersistListener<?> listener) {
        BeanPersistListener<T> currListener = this.persistListener;
        if (currListener != null) {
            BeanPersistListener<?> deregListener = listener;
            if (currListener instanceof ChainedBeanPersistListener) {
                this.persistListener = ((ChainedBeanPersistListener)currListener).deregister(deregListener);
            } else if (currListener.equals(deregListener)) {
                this.persistListener = null;
            }
        }
    }

    public void deregister(BeanPersistController controller) {
        BeanPersistController c = this.persistController;
        if (c != null) {
            if (c instanceof ChainedBeanPersistController) {
                this.persistController = ((ChainedBeanPersistController)c).deregister(controller);
            } else if (c.equals(controller)) {
                this.persistController = null;
            }
        }
    }

    public void register(BeanPersistListener<?> newPersistListener) {
        if (PersistListenerManager.isRegisterFor(this.beanType, newPersistListener)) {
            BeanPersistListener<?> newListener = newPersistListener;
            BeanPersistListener<T> currListener = this.persistListener;
            this.persistListener = currListener == null ? newListener : (currListener instanceof ChainedBeanPersistListener ? ((ChainedBeanPersistListener)currListener).register(newListener) : new ChainedBeanPersistListener<T>(currListener, newListener));
        }
    }

    public void register(BeanPersistController newController) {
        if (newController.isRegisterFor(this.beanType)) {
            BeanPersistController c = this.persistController;
            this.persistController = c == null ? newController : (c instanceof ChainedBeanPersistController ? ((ChainedBeanPersistController)c).register(newController) : new ChainedBeanPersistController(c, newController));
        }
    }

    public BeanPersistController getPersistController() {
        return this.persistController;
    }

    public boolean isSqlSelectBased() {
        return EntityType.SQL.equals((Object)this.entityType);
    }

    public boolean isLdapEntityType() {
        return EntityType.LDAP.equals((Object)this.entityType);
    }

    public String getBaseTable() {
        return this.baseTable;
    }

    public String getExtraAttribute(String key) {
        return this.extraAttrMap.get(key);
    }

    public IdType getIdType() {
        return this.idType;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public String getSelectLastInsertedId() {
        return this.selectLastInsertedId;
    }

    public IdGenerator getIdGenerator() {
        return this.idGenerator;
    }

    public String getLazyFetchIncludes() {
        return this.lazyFetchIncludes;
    }

    public TableJoin[] tableJoins() {
        return this.derivedTableJoins;
    }

    public Iterator<BeanProperty> propertiesAll() {
        return this.propMap.values().iterator();
    }

    public BeanProperty[] propertiesId() {
        return this.propertiesId;
    }

    public BeanProperty[] propertiesNonTransient() {
        return this.propertiesNonTransient;
    }

    public BeanProperty[] propertiesTransient() {
        return this.propertiesTransient;
    }

    public BeanProperty getSingleIdProperty() {
        return this.propertySingleId;
    }

    public BeanPropertyAssocOne<?>[] propertiesEmbedded() {
        return this.propertiesEmbedded;
    }

    public BeanPropertyAssocOne<?>[] propertiesOne() {
        return this.propertiesOne;
    }

    public BeanPropertyAssocOne<?>[] propertiesOneImported() {
        return this.propertiesOneImported;
    }

    public BeanPropertyAssocOne<?>[] propertiesOneImportedSave() {
        return this.propertiesOneImportedSave;
    }

    public BeanPropertyAssocOne<?>[] propertiesOneImportedDelete() {
        return this.propertiesOneImportedDelete;
    }

    public BeanPropertyAssocOne<?>[] propertiesOneExported() {
        return this.propertiesOneExported;
    }

    public BeanPropertyAssocOne<?>[] propertiesOneExportedSave() {
        return this.propertiesOneExportedSave;
    }

    public BeanPropertyAssocOne<?>[] propertiesOneExportedDelete() {
        return this.propertiesOneExportedDelete;
    }

    private Set<String> deriveManyPropNames() {
        LinkedHashSet<String> names = new LinkedHashSet<String>();
        for (int i = 0; i < this.propertiesMany.length; ++i) {
            names.add(this.propertiesMany[i].getName());
        }
        return Collections.unmodifiableSet(names);
    }

    public int getNamesOfManyPropsHash() {
        return this.namesOfManyPropsHash;
    }

    public Set<String> getNamesOfManyProps() {
        return this.namesOfManyProps;
    }

    public BeanPropertyAssocMany<?>[] propertiesMany() {
        return this.propertiesMany;
    }

    public BeanPropertyAssocMany<?>[] propertiesManySave() {
        return this.propertiesManySave;
    }

    public BeanPropertyAssocMany<?>[] propertiesManyDelete() {
        return this.propertiesManyDelete;
    }

    public BeanPropertyAssocMany<?>[] propertiesManyToMany() {
        return this.propertiesManyToMany;
    }

    public BeanProperty firstVersionProperty() {
        return this.propertyFirstVersion;
    }

    public boolean isVanillaInsert(Object bean) {
        if (this.propertyFirstVersion == null) {
            return true;
        }
        Object versionValue = this.propertyFirstVersion.getValue(bean);
        return DmlUtil.isNullOrZero(versionValue);
    }

    public boolean isStatelessUpdate(Object bean) {
        if (this.propertyFirstVersion == null) {
            Object versionValue = this.getId(bean);
            return !DmlUtil.isNullOrZero(versionValue);
        }
        Object versionValue = this.propertyFirstVersion.getValue(bean);
        return !DmlUtil.isNullOrZero(versionValue);
    }

    public BeanProperty[] propertiesVersion() {
        return this.propertiesVersion;
    }

    public BeanProperty[] propertiesBaseScalar() {
        return this.propertiesBaseScalar;
    }

    public BeanPropertyCompound[] propertiesBaseCompound() {
        return this.propertiesBaseCompound;
    }

    public BeanProperty[] propertiesLocal() {
        return this.propertiesLocal;
    }

    public void jsonWrite(WriteJsonContext ctx, Object bean) {
        if (bean != null) {
            ctx.appendObjectBegin();
            WriteJsonContext.WriteBeanState prevState = ctx.pushBeanState(bean);
            if (this.inheritInfo != null) {
                InheritInfo localInheritInfo = this.inheritInfo.readType(bean.getClass());
                String discValue = localInheritInfo.getDiscriminatorStringValue();
                String discColumn = localInheritInfo.getDiscriminatorColumn();
                ctx.appendKeyValue(discColumn, "\"" + discValue + "\"");
                BeanDescriptor<?> localDescriptor = localInheritInfo.getBeanDescriptor();
                super.jsonWriteProperties(ctx, bean);
            } else {
                this.jsonWriteProperties(ctx, bean);
            }
            ctx.pushPreviousState(prevState);
            ctx.appendObjectEnd();
        }
    }

    private void jsonWriteProperties(WriteJsonContext ctx, Object bean) {
        boolean explicitAllProps;
        boolean referenceBean = ctx.isReferenceBean();
        JsonWriteBeanVisitor<?> beanVisitor = ctx.getBeanVisitor();
        Set<String> props = ctx.getIncludeProperties();
        if (props == null) {
            explicitAllProps = false;
        } else {
            explicitAllProps = props.contains("*");
            if (explicitAllProps || props.isEmpty()) {
                props = null;
            }
        }
        for (int i = 0; i < this.propertiesId.length; ++i) {
            Object idValue = this.propertiesId[i].getValue(bean);
            if (idValue == null || props != null && !props.contains(this.propertiesId[i].getName())) continue;
            this.propertiesId[i].jsonWrite(ctx, bean);
        }
        if (!explicitAllProps && props == null) {
            props = ctx.getLoadedProps();
        }
        if (props != null) {
            for (String prop : props) {
                BeanProperty p = this.getBeanProperty(prop);
                if (p.isId()) continue;
                p.jsonWrite(ctx, bean);
            }
        } else if (!referenceBean) {
            for (int j = 0; j < this.propertiesNonTransient.length; ++j) {
                this.propertiesNonTransient[j].jsonWrite(ctx, bean);
            }
        }
        if (beanVisitor != null) {
            beanVisitor.visit(bean, ctx);
        }
    }

    public T jsonReadBean(ReadJsonContext ctx, String path) {
        ReadJsonContext.ReadBeanState beanState = this.jsonRead(ctx, path);
        if (beanState == null) {
            return null;
        }
        beanState.setLoadedState();
        return (T)beanState.getBean();
    }

    public ReadJsonContext.ReadBeanState jsonRead(ReadJsonContext ctx, String path) {
        if (!ctx.readObjectBegin()) {
            return null;
        }
        if (this.inheritInfo == null) {
            return this.jsonReadObject(ctx, path);
        }
        String discColumn = this.inheritInfo.getRoot().getDiscriminatorColumn();
        if (!ctx.readKeyNext()) {
            String msg = "Error reading inheritance discriminator - expected [" + discColumn + "] but no json key?";
            throw new TextException(msg);
        }
        String propName = ctx.getTokenKey();
        if (!propName.equalsIgnoreCase(discColumn)) {
            String msg = "Error reading inheritance discriminator - expected [" + discColumn + "] but read [" + propName + "]";
            throw new TextException(msg);
        }
        String discValue = ctx.readScalarValue();
        if (!ctx.readValueNext()) {
            String msg = "Error reading inheritance discriminator [" + discColumn + "]. Expected more json name values?";
            throw new TextException(msg);
        }
        InheritInfo localInheritInfo = this.inheritInfo.readType(discValue);
        BeanDescriptor<?> localDescriptor = localInheritInfo.getBeanDescriptor();
        return super.jsonReadObject(ctx, path);
    }

    private ReadJsonContext.ReadBeanState jsonReadObject(ReadJsonContext ctx, String path) {
        EntityBean bean = this.createEntityBean();
        ctx.pushBean(bean, path, this);
        while (ctx.readKeyNext()) {
            String propName = ctx.getTokenKey();
            BeanProperty p = this.getBeanProperty(propName);
            if (p != null) {
                p.jsonRead(ctx, bean);
                ctx.setProperty(propName);
            } else {
                ctx.readUnmappedJson(propName);
            }
            if (ctx.readValueNext()) continue;
            break;
        }
        return ctx.popBeanState();
    }

    public void setLoadedProps(EntityBeanIntercept ebi, Set<String> loadedProps) {
        if (this.isLoadedReference(loadedProps)) {
            ebi.setReference();
        } else {
            ebi.setLoadedProps(loadedProps);
        }
    }

    public boolean isLoadedReference(Set<String> loadedProps) {
        if (loadedProps != null && loadedProps.size() == this.propertiesId.length) {
            for (int i = 0; i < this.propertiesId.length; ++i) {
                if (loadedProps.contains(this.propertiesId[i].getName())) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum EntityType {
        ORM,
        EMBEDDED,
        SQL,
        META,
        LDAP,
        XMLELEMENT;

    }
}

