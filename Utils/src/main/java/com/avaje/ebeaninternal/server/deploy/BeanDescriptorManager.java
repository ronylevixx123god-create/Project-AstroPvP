/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.config.EncryptKeyManager;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.DbIdentity;
import com.avaje.ebean.config.dbplatform.IdGenerator;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebean.validation.factory.LengthValidatorFactory;
import com.avaje.ebean.validation.factory.NotNullValidatorFactory;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.core.InternalConfiguration;
import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.core.XmlConfig;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanFinderManager;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.server.deploy.BeanManagerFactory;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanQueryAdapterManager;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import com.avaje.ebeaninternal.server.deploy.DNativeQuery;
import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
import com.avaje.ebeaninternal.server.deploy.DRawSqlSelectBuilder;
import com.avaje.ebeaninternal.server.deploy.DefaultBeanFinderManager;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.deploy.DeployOrmXml;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.ParamTypeUtil;
import com.avaje.ebeaninternal.server.deploy.PersistControllerManager;
import com.avaje.ebeaninternal.server.deploy.PersistListenerManager;
import com.avaje.ebeaninternal.server.deploy.ReflectGetter;
import com.avaje.ebeaninternal.server.deploy.ReflectSetter;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderEmbedded;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderFactory;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
import com.avaje.ebeaninternal.server.deploy.parse.DeployCreateProperties;
import com.avaje.ebeaninternal.server.deploy.parse.DeployInherit;
import com.avaje.ebeaninternal.server.deploy.parse.DeployUtil;
import com.avaje.ebeaninternal.server.deploy.parse.ReadAnnotations;
import com.avaje.ebeaninternal.server.deploy.parse.TransientProperties;
import com.avaje.ebeaninternal.server.idgen.UuidIdGenerator;
import com.avaje.ebeaninternal.server.lib.util.Dnode;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import com.avaje.ebeaninternal.server.reflect.BeanReflectFactory;
import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
import com.avaje.ebeaninternal.server.reflect.EnhanceBeanReflectFactory;
import com.avaje.ebeaninternal.server.subclass.SubClassManager;
import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import com.avaje.ebeaninternal.server.type.TypeManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanDescriptorManager
implements BeanDescriptorMap {
    private static final Logger logger = Logger.getLogger(BeanDescriptorManager.class.getName());
    private static final BeanDescComparator beanDescComparator = new BeanDescComparator();
    private final ReadAnnotations readAnnotations = new ReadAnnotations();
    private final TransientProperties transientProperties;
    private final DeployInherit deplyInherit;
    private final BeanReflectFactory reflectFactory;
    private final DeployUtil deployUtil;
    private final TypeManager typeManager;
    private final PersistControllerManager persistControllerManager;
    private final BeanFinderManager beanFinderManager;
    private final PersistListenerManager persistListenerManager;
    private final BeanQueryAdapterManager beanQueryAdapterManager;
    private final SubClassManager subClassManager;
    private final NamingConvention namingConvention;
    private final DeployCreateProperties createProperties;
    private final DeployOrmXml deployOrmXml;
    private final BeanManagerFactory beanManagerFactory;
    private int enhancedClassCount;
    private int subclassClassCount;
    private final HashSet<String> subclassedEntities = new HashSet();
    private final boolean updateChangesOnly;
    private final BootupClasses bootupClasses;
    private final String serverName;
    private Map<Class<?>, DeployBeanInfo<?>> deplyInfoMap = new HashMap();
    private final Map<Class<?>, BeanTable> beanTableMap = new HashMap();
    private final Map<String, BeanDescriptor<?>> descMap = new HashMap();
    private final Map<String, BeanDescriptor<?>> idDescMap = new HashMap();
    private final Map<String, BeanManager<?>> beanManagerMap = new HashMap();
    private final Map<String, List<BeanDescriptor<?>>> tableToDescMap = new HashMap();
    private List<BeanDescriptor<?>> immutableDescriptorList;
    private final Set<Integer> descriptorUniqueIds = new HashSet<Integer>();
    private final DbIdentity dbIdentity;
    private final DataSource dataSource;
    private final DatabasePlatform databasePlatform;
    private final UuidIdGenerator uuidIdGenerator = new UuidIdGenerator();
    private final ServerCacheManager cacheManager;
    private final BackgroundExecutor backgroundExecutor;
    private final int dbSequenceBatchSize;
    private final EncryptKeyManager encryptKeyManager;
    private final IdBinderFactory idBinderFactory;
    private final XmlConfig xmlConfig;
    private final LuceneIndexManager luceneManager;

    public BeanDescriptorManager(InternalConfiguration config, LuceneIndexManager luceneManager) {
        this.luceneManager = luceneManager;
        this.serverName = InternString.intern(config.getServerConfig().getName());
        this.cacheManager = config.getCacheManager();
        this.xmlConfig = config.getXmlConfig();
        this.dbSequenceBatchSize = config.getServerConfig().getDatabaseSequenceBatchSize();
        this.backgroundExecutor = config.getBackgroundExecutor();
        this.dataSource = config.getServerConfig().getDataSource();
        this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
        this.databasePlatform = config.getServerConfig().getDatabasePlatform();
        this.idBinderFactory = new IdBinderFactory(this.databasePlatform.isIdInExpandedForm());
        this.bootupClasses = config.getBootupClasses();
        this.createProperties = config.getDeployCreateProperties();
        this.subClassManager = config.getSubClassManager();
        this.typeManager = config.getTypeManager();
        this.namingConvention = config.getServerConfig().getNamingConvention();
        this.dbIdentity = config.getDatabasePlatform().getDbIdentity();
        this.deplyInherit = config.getDeployInherit();
        this.deployOrmXml = config.getDeployOrmXml();
        this.deployUtil = config.getDeployUtil();
        this.beanManagerFactory = new BeanManagerFactory(config.getServerConfig(), config.getDatabasePlatform());
        this.updateChangesOnly = config.getServerConfig().isUpdateChangesOnly();
        this.persistControllerManager = new PersistControllerManager(this.bootupClasses);
        this.persistListenerManager = new PersistListenerManager(this.bootupClasses);
        this.beanQueryAdapterManager = new BeanQueryAdapterManager(this.bootupClasses);
        this.beanFinderManager = new DefaultBeanFinderManager();
        this.reflectFactory = this.createReflectionFactory();
        this.transientProperties = new TransientProperties();
    }

    public BeanDescriptor<?> getBeanDescriptorById(String descriptorId) {
        return this.idDescMap.get(descriptorId);
    }

    @Override
    public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> entityType) {
        String className = SubClassUtil.getSuperClassName(entityType.getName());
        return this.descMap.get(className);
    }

    public <T> BeanDescriptor<T> getBeanDescriptor(String entityClassName) {
        entityClassName = SubClassUtil.getSuperClassName(entityClassName);
        return this.descMap.get(entityClassName);
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public ServerCacheManager getCacheManager() {
        return this.cacheManager;
    }

    public NamingConvention getNamingConvention() {
        return this.namingConvention;
    }

    public void setEbeanServer(SpiEbeanServer internalEbean) {
        for (BeanDescriptor<?> desc : this.immutableDescriptorList) {
            desc.setEbeanServer(internalEbean);
        }
    }

    @Override
    public IdBinder createIdBinder(BeanProperty[] uids) {
        return this.idBinderFactory.createIdBinder(uids);
    }

    public void deploy() {
        try {
            this.createListeners();
            this.readEmbeddedDeployment();
            this.readEntityDeploymentInitial();
            this.assignLuceneIndexDefns();
            this.readEntityBeanTable();
            this.readEntityDeploymentAssociations();
            this.readInheritedIdGenerators();
            this.readEntityRelationships();
            this.readRawSqlQueries();
            ArrayList list = new ArrayList(this.descMap.values());
            Collections.sort(list, beanDescComparator);
            this.immutableDescriptorList = Collections.unmodifiableList(list);
            for (BeanDescriptor beanDescriptor : list) {
                this.idDescMap.put(beanDescriptor.getDescriptorId(), beanDescriptor);
            }
            this.initialiseAll();
            this.readForeignKeys();
            this.readTableToDescriptor();
            this.logStatus();
            this.deplyInfoMap.clear();
            this.deplyInfoMap = null;
        }
        catch (RuntimeException e) {
            String msg = "Error in deployment";
            logger.log(Level.SEVERE, msg, e);
            throw e;
        }
    }

    @Override
    public EncryptKey getEncryptKey(String tableName, String columnName) {
        return this.encryptKeyManager.getEncryptKey(tableName, columnName);
    }

    public void cacheNotify(TransactionEventTable.TableIUD tableIUD) {
        List<BeanDescriptor<?>> list = this.getBeanDescriptors(tableIUD.getTable());
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).cacheNotify(tableIUD);
            }
        }
    }

    public List<BeanDescriptor<?>> getBeanDescriptors(String tableName) {
        return this.tableToDescMap.get(tableName.toLowerCase());
    }

    private void readTableToDescriptor() {
        for (BeanDescriptor<?> desc : this.descMap.values()) {
            String baseTable = desc.getBaseTable();
            if (baseTable == null) continue;
            List<BeanDescriptor<?>> list = this.tableToDescMap.get(baseTable = baseTable.toLowerCase());
            if (list == null) {
                list = new ArrayList(1);
                this.tableToDescMap.put(baseTable, list);
            }
            list.add(desc);
        }
    }

    private void readForeignKeys() {
        for (BeanDescriptor<?> d : this.descMap.values()) {
            d.initialiseFkeys();
        }
    }

    private void initialiseAll() {
        for (BeanDescriptor<?> d : this.descMap.values()) {
            d.initialiseId();
        }
        for (BeanDescriptor<?> d : this.descMap.values()) {
            d.initInheritInfo();
        }
        for (BeanDescriptor<?> d : this.descMap.values()) {
            d.initialiseOther();
        }
        for (BeanDescriptor<?> d : this.descMap.values()) {
            IndexDefn<?> luceneIndexDefn = d.getLuceneIndexDefn();
            if (luceneIndexDefn == null) continue;
            try {
                LIndex luceneIndex = this.luceneManager.create(luceneIndexDefn, d);
                d.setLuceneIndex(luceneIndex);
            }
            catch (IOException e) {
                String msg = "Error creating Lucene Index " + luceneIndexDefn.getClass().getName();
                logger.log(Level.SEVERE, msg, e);
            }
        }
        for (BeanDescriptor<?> d : this.descMap.values()) {
            if (d.isEmbedded()) continue;
            BeanManager<?> m = this.beanManagerFactory.create(d);
            this.beanManagerMap.put(d.getFullName(), m);
            this.checkForValidEmbeddedId(d);
        }
    }

    private void checkForValidEmbeddedId(BeanDescriptor<?> d) {
        IdBinder idBinder = d.getIdBinder();
        if (idBinder != null && idBinder instanceof IdBinderEmbedded) {
            IdBinderEmbedded embId = (IdBinderEmbedded)idBinder;
            BeanDescriptor<?> idBeanDescriptor = embId.getIdBeanDescriptor();
            Class<?> idType = idBeanDescriptor.getBeanType();
            try {
                idType.getDeclaredMethod("hashCode", new Class[0]);
                idType.getDeclaredMethod("equals", Object.class);
            }
            catch (NoSuchMethodException e) {
                this.checkMissingHashCodeOrEquals(e, idType, d.getBeanType());
            }
        }
    }

    private void checkMissingHashCodeOrEquals(Exception source, Class<?> idType, Class<?> beanType) {
        String msg = "SERIOUS ERROR: The hashCode() and equals() methods *MUST* be implemented ";
        msg = msg + "on Embedded bean " + idType + " as it is used as an Id for " + beanType;
        if (GlobalProperties.getBoolean("ebean.strict", true)) {
            throw new PersistenceException(msg, source);
        }
        logger.log(Level.SEVERE, msg, source);
    }

    public List<BeanDescriptor<?>> getBeanDescriptorList() {
        return this.immutableDescriptorList;
    }

    public Map<Class<?>, BeanTable> getBeanTables() {
        return this.beanTableMap;
    }

    public BeanTable getBeanTable(Class<?> type) {
        return this.beanTableMap.get(type);
    }

    public Map<String, BeanDescriptor<?>> getBeanDescriptors() {
        return this.descMap;
    }

    public <T> BeanManager<T> getBeanManager(Class<T> entityType) {
        return this.getBeanManager(entityType.getName());
    }

    public BeanManager<?> getBeanManager(String beanClassName) {
        beanClassName = SubClassUtil.getSuperClassName(beanClassName);
        return this.beanManagerMap.get(beanClassName);
    }

    public DNativeQuery getNativeQuery(String name) {
        return this.deployOrmXml.getNativeQuery(name);
    }

    private void assignLuceneIndexDefns() {
        List<IndexDefn<?>> list = this.bootupClasses.getLuceneIndexInstances();
        for (int i = 0; i < list.size(); ++i) {
            IndexDefn<?> indexDefn = list.get(i);
            Class<?> entityClass = this.getIndexDefnEntityClass(indexDefn.getClass());
            DeployBeanInfo<?> deployBeanInfo = this.deplyInfoMap.get(entityClass);
            if (deployBeanInfo == null) {
                String msg = "Could not find entity deployment for " + entityClass;
                throw new PersistenceException(msg);
            }
            deployBeanInfo.getDescriptor().setIndexDefn(indexDefn);
        }
    }

    private Class<?> getIndexDefnEntityClass(Class<?> controller) {
        Class<?> cls = ParamTypeUtil.findParamType(controller, IndexDefn.class);
        if (cls == null) {
            String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
            throw new PersistenceException(msg);
        }
        return cls;
    }

    private void createListeners() {
        int qa = this.beanQueryAdapterManager.getRegisterCount();
        int cc = this.persistControllerManager.getRegisterCount();
        int lc = this.persistListenerManager.getRegisterCount();
        int fc = this.beanFinderManager.createBeanFinders(this.bootupClasses.getBeanFinders());
        logger.fine("BeanPersistControllers[" + cc + "] BeanFinders[" + fc + "] BeanPersistListeners[" + lc + "] BeanQueryAdapters[" + qa + "]");
    }

    private void logStatus() {
        String msg = "Entities enhanced[" + this.enhancedClassCount + "] subclassed[" + this.subclassClassCount + "]";
        logger.info(msg);
        if (this.enhancedClassCount > 0 && this.subclassClassCount > 0) {
            String subclassEntityNames = this.subclassedEntities.toString();
            String m = "Mixing enhanced and subclassed entities. Subclassed classes:" + subclassEntityNames;
            logger.warning(m);
        }
    }

    private <T> BeanDescriptor<T> createEmbedded(Class<T> beanClass) {
        DeployBeanInfo<T> info = this.createDeployBeanInfo(beanClass);
        this.readDeployAssociations(info);
        Integer key = this.getUniqueHash(info.getDescriptor());
        return new BeanDescriptor<T>(this, this.typeManager, info.getDescriptor(), key.toString());
    }

    private void registerBeanDescriptor(BeanDescriptor<?> desc) {
        this.descMap.put(desc.getBeanType().getName(), desc);
    }

    private void readEmbeddedDeployment() {
        ArrayList<Class<?>> embeddedClasses = this.bootupClasses.getEmbeddables();
        for (int i = 0; i < embeddedClasses.size(); ++i) {
            Class<?> cls = embeddedClasses.get(i);
            if (logger.isLoggable(Level.FINER)) {
                String msg = "load deployinfo for embeddable:" + cls.getName();
                logger.finer(msg);
            }
            BeanDescriptor<?> embDesc = this.createEmbedded(cls);
            this.registerBeanDescriptor(embDesc);
        }
    }

    private void readEntityDeploymentInitial() {
        ArrayList<Class<?>> entityClasses = this.bootupClasses.getEntities();
        for (Class<?> entityClass : entityClasses) {
            DeployBeanInfo<?> info = this.createDeployBeanInfo(entityClass);
            this.deplyInfoMap.put(entityClass, info);
        }
    }

    private void readEntityBeanTable() {
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            BeanTable beanTable = this.createBeanTable(info);
            this.beanTableMap.put(beanTable.getBeanType(), beanTable);
        }
    }

    private void readEntityDeploymentAssociations() {
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            this.readDeployAssociations(info);
        }
    }

    private void readInheritedIdGenerators() {
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            DeployBeanInfo<?> rootBeanInfo;
            IdGenerator rootIdGen;
            DeployBeanDescriptor<?> descriptor = info.getDescriptor();
            InheritInfo inheritInfo = descriptor.getInheritInfo();
            if (inheritInfo == null || inheritInfo.isRoot() || (rootIdGen = (rootBeanInfo = this.deplyInfoMap.get(inheritInfo.getRoot().getType())).getDescriptor().getIdGenerator()) == null) continue;
            descriptor.setIdGenerator(rootIdGen);
        }
    }

    private BeanTable createBeanTable(DeployBeanInfo<?> info) {
        DeployBeanDescriptor<?> deployDescriptor = info.getDescriptor();
        DeployBeanTable beanTable = deployDescriptor.createDeployBeanTable();
        return new BeanTable(beanTable, this);
    }

    private void readRawSqlQueries() {
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            DeployBeanDescriptor<?> deployDesc = info.getDescriptor();
            BeanDescriptor<?> desc = this.getBeanDescriptor(deployDesc.getBeanType());
            for (DRawSqlMeta rawSqlMeta : deployDesc.getRawSqlMeta()) {
                if (rawSqlMeta.getQuery() == null) continue;
                DeployNamedQuery nq = new DRawSqlSelectBuilder(this.namingConvention, desc, rawSqlMeta).parse();
                desc.addNamedQuery(nq);
            }
        }
    }

    private void readEntityRelationships() {
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            this.checkMappedBy(info);
        }
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            this.secondaryPropsJoins(info);
        }
        for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            DeployBeanDescriptor<?> deployBeanDescriptor = info.getDescriptor();
            Integer key = this.getUniqueHash(deployBeanDescriptor);
            this.registerBeanDescriptor(new BeanDescriptor(this, this.typeManager, info.getDescriptor(), key.toString()));
        }
    }

    private Integer getUniqueHash(DeployBeanDescriptor<?> deployBeanDescriptor) {
        int hashCode = deployBeanDescriptor.getFullName().hashCode();
        for (int i = 0; i < 100000; ++i) {
            Integer key = hashCode + i;
            if (this.descriptorUniqueIds.contains(key)) continue;
            return key;
        }
        throw new RuntimeException("Failed to generate a unique hash for " + deployBeanDescriptor.getFullName());
    }

    private void secondaryPropsJoins(DeployBeanInfo<?> info) {
        DeployBeanDescriptor<?> descriptor = info.getDescriptor();
        for (DeployBeanProperty prop : descriptor.propertiesBase()) {
            if (!prop.isSecondaryTable()) continue;
            String tableName = prop.getSecondaryTable();
            DeployBeanPropertyAssocOne<?> assocOne = descriptor.findJoinToTable(tableName);
            if (assocOne == null) {
                String msg = "Error with property " + prop.getFullBeanName() + ". Could not find a Relationship to table " + tableName + ". Perhaps you could use a @JoinColumn instead.";
                throw new RuntimeException(msg);
            }
            DeployTableJoin tableJoin = assocOne.getTableJoin();
            prop.setSecondaryTableJoin(tableJoin, assocOne.getName());
        }
    }

    private void checkMappedBy(DeployBeanInfo<?> info) {
        for (DeployBeanPropertyAssocOne<?> deployBeanPropertyAssocOne : info.getDescriptor().propertiesAssocOne()) {
            if (deployBeanPropertyAssocOne.isTransient() || deployBeanPropertyAssocOne.getMappedBy() == null) continue;
            this.checkMappedByOneToOne(info, deployBeanPropertyAssocOne);
        }
        for (DeployBeanPropertyAssocMany deployBeanPropertyAssocMany : info.getDescriptor().propertiesAssocMany()) {
            if (deployBeanPropertyAssocMany.isTransient()) continue;
            if (deployBeanPropertyAssocMany.isManyToMany()) {
                this.checkMappedByManyToMany(info, deployBeanPropertyAssocMany);
                continue;
            }
            this.checkMappedByOneToMany(info, deployBeanPropertyAssocMany);
        }
    }

    private DeployBeanDescriptor<?> getTargetDescriptor(DeployBeanPropertyAssoc<?> prop) {
        Class<?> targetType = prop.getTargetType();
        DeployBeanInfo<?> info = this.deplyInfoMap.get(targetType);
        if (info == null) {
            String msg = "Can not find descriptor [" + targetType + "] for " + prop.getFullBeanName();
            throw new PersistenceException(msg);
        }
        return info.getDescriptor();
    }

    private boolean findMappedBy(DeployBeanPropertyAssocMany<?> prop) {
        String targetType;
        String shortTypeName;
        String name;
        int p;
        Class<?> owningType = prop.getOwningType();
        HashSet<String> matchSet = new HashSet<String>();
        DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        List<DeployBeanPropertyAssocOne<?>> ones = targetDesc.propertiesAssocOne();
        for (DeployBeanPropertyAssocOne<?> possibleMappedBy : ones) {
            Class possibleMappedByType = possibleMappedBy.getTargetType();
            if (!possibleMappedByType.equals(owningType)) continue;
            prop.setMappedBy(possibleMappedBy.getName());
            matchSet.add(possibleMappedBy.getName());
        }
        if (matchSet.size() == 0) {
            return false;
        }
        if (matchSet.size() == 1) {
            return true;
        }
        if (matchSet.size() == 2 && (p = (name = prop.getName()).indexOf(shortTypeName = (targetType = prop.getTargetType().getName()).substring(targetType.lastIndexOf(".") + 1))) > 1) {
            String searchName = name.substring(0, p).toLowerCase();
            for (String possibleMappedBy : matchSet) {
                String possibleLower = possibleMappedBy.toLowerCase();
                if (possibleLower.indexOf(searchName) <= -1) continue;
                prop.setMappedBy(possibleMappedBy);
                String m = "Implicitly found mappedBy for " + targetDesc + "." + prop;
                m = m + " by searching for [" + searchName + "] against " + matchSet;
                logger.fine(m);
                return true;
            }
        }
        String msg = "Error on " + prop.getFullBeanName() + " missing mappedBy.";
        msg = msg + " There are [" + matchSet.size() + "] possible properties in " + targetDesc;
        msg = msg + " that this association could be mapped to. Please specify one using ";
        msg = msg + "the mappedBy attribute on @OneToMany.";
        throw new PersistenceException(msg);
    }

    private void makeUnidirectional(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> oneToMany) {
        DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(oneToMany);
        Class<?> owningType = oneToMany.getOwningType();
        if (!oneToMany.getCascadeInfo().isSave()) {
            Class targetType = oneToMany.getTargetType();
            String msg = "Error on " + oneToMany.getFullBeanName() + ". @OneToMany MUST have ";
            msg = msg + "Cascade.PERSIST or Cascade.ALL because this is a unidirectional ";
            msg = msg + "relationship. That is, there is no property of type " + owningType + " on " + targetType;
            throw new PersistenceException(msg);
        }
        oneToMany.setUnidirectional(true);
        DeployBeanPropertyAssocOne unidirectional = new DeployBeanPropertyAssocOne(targetDesc, owningType);
        unidirectional.setUndirectionalShadow(true);
        unidirectional.setNullable(false);
        unidirectional.setDbRead(true);
        unidirectional.setDbInsertable(true);
        unidirectional.setDbUpdateable(false);
        targetDesc.setUnidirectional(unidirectional);
        BeanTable beanTable = this.getBeanTable(owningType);
        unidirectional.setBeanTable(beanTable);
        unidirectional.setName(beanTable.getBaseTable());
        info.setBeanJoinType(unidirectional, true);
        DeployTableJoin oneToManyJoin = oneToMany.getTableJoin();
        if (!oneToManyJoin.hasJoinColumns()) {
            throw new RuntimeException("No join columns");
        }
        DeployTableJoin unidirectionalJoin = unidirectional.getTableJoin();
        unidirectionalJoin.setColumns(oneToManyJoin.columns(), true);
    }

    private void checkMappedByOneToOne(DeployBeanInfo<?> info, DeployBeanPropertyAssocOne<?> prop) {
        String mappedBy = prop.getMappedBy();
        DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
        if (mappedProp == null) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + "  Can not find mappedBy property [" + targetDesc + "." + mappedBy + "] ";
            throw new PersistenceException(m);
        }
        if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
            throw new PersistenceException(m);
        }
        DeployBeanPropertyAssocOne mappedAssocOne = (DeployBeanPropertyAssocOne)mappedProp;
        if (!mappedAssocOne.isOneToOne()) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
            throw new PersistenceException(m);
        }
        DeployTableJoin tableJoin = prop.getTableJoin();
        if (!tableJoin.hasJoinColumns()) {
            DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
            otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
        }
    }

    private void checkMappedByOneToMany(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> prop) {
        if (prop.getMappedBy() == null && !this.findMappedBy(prop)) {
            this.makeUnidirectional(info, prop);
            return;
        }
        String mappedBy = prop.getMappedBy();
        DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
        if (mappedProp == null) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
            m = m + "in [" + targetDesc + "]";
            throw new PersistenceException(m);
        }
        if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + mappedBy + "]is not a ManyToOne?";
            m = m + "in [" + targetDesc + "]";
            throw new PersistenceException(m);
        }
        DeployBeanPropertyAssocOne mappedAssocOne = (DeployBeanPropertyAssocOne)mappedProp;
        DeployTableJoin tableJoin = prop.getTableJoin();
        if (!tableJoin.hasJoinColumns()) {
            DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
            otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
        }
    }

    private void checkMappedByManyToMany(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> prop) {
        String mappedBy = prop.getMappedBy();
        if (mappedBy == null) {
            return;
        }
        DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
        if (mappedProp == null) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
            m = m + "in [" + targetDesc + "]";
            throw new PersistenceException(m);
        }
        if (!(mappedProp instanceof DeployBeanPropertyAssocMany)) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
            throw new PersistenceException(m);
        }
        DeployBeanPropertyAssocMany mappedAssocMany = (DeployBeanPropertyAssocMany)mappedProp;
        if (!mappedAssocMany.isManyToMany()) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
            throw new PersistenceException(m);
        }
        DeployTableJoin mappedIntJoin = mappedAssocMany.getIntersectionJoin();
        DeployTableJoin mappendInverseJoin = mappedAssocMany.getInverseJoin();
        String intTableName = mappedIntJoin.getTable();
        DeployTableJoin tableJoin = prop.getTableJoin();
        mappedIntJoin.copyTo(tableJoin, true, targetDesc.getBaseTable());
        DeployTableJoin intJoin = new DeployTableJoin();
        mappendInverseJoin.copyTo(intJoin, false, intTableName);
        prop.setIntersectionJoin(intJoin);
        DeployTableJoin inverseJoin = new DeployTableJoin();
        mappedIntJoin.copyTo(inverseJoin, false, intTableName);
        prop.setInverseJoin(inverseJoin);
    }

    private <T> void setBeanControllerFinderListener(DeployBeanDescriptor<T> descriptor) {
        Class<T> beanType = descriptor.getBeanType();
        this.persistControllerManager.addPersistControllers(descriptor);
        this.persistListenerManager.addPersistListeners(descriptor);
        this.beanQueryAdapterManager.addQueryAdapter(descriptor);
        BeanFinder<T> beanFinder = this.beanFinderManager.getBeanFinder(beanType);
        if (beanFinder != null) {
            descriptor.setBeanFinder(beanFinder);
            logger.fine("BeanFinder on[" + descriptor.getFullName() + "] " + beanFinder.getClass().getName());
        }
    }

    private <T> DeployBeanInfo<T> createDeployBeanInfo(Class<T> beanClass) {
        DeployBeanDescriptor<T> desc = new DeployBeanDescriptor<T>(beanClass);
        desc.setUpdateChangesOnly(this.updateChangesOnly);
        this.setBeanControllerFinderListener(desc);
        this.deplyInherit.process(desc);
        this.createProperties.createProperties(desc);
        DeployBeanInfo<T> info = new DeployBeanInfo<T>(this.deployUtil, desc);
        this.readAnnotations.readInitial(info);
        return info;
    }

    private <T> void readDeployAssociations(DeployBeanInfo<T> info) {
        DeployBeanDescriptor<T> desc = info.getDescriptor();
        this.readAnnotations.readAssociations(info, this);
        this.readXml(desc);
        if (!BeanDescriptor.EntityType.ORM.equals((Object)desc.getEntityType())) {
            desc.setBaseTable(null);
        }
        this.transientProperties.process(desc);
        this.setScalarType(desc);
        if (!desc.isEmbedded()) {
            this.setIdGeneration(desc);
            this.setConcurrencyMode(desc);
        }
        this.autoAddValidators(desc);
        this.createByteCode(desc);
    }

    private <T> IdType setIdGeneration(DeployBeanDescriptor<T> desc) {
        String genName;
        if (desc.propertiesId().size() == 0) {
            if (desc.isBaseTableType() && desc.getBeanFinder() == null) {
                logger.warning(Message.msg("deploy.nouid", desc.getFullName()));
            }
            return null;
        }
        if (IdType.SEQUENCE.equals((Object)desc.getIdType()) && !this.dbIdentity.isSupportsSequence()) {
            logger.info("Explicit sequence on " + desc.getFullName() + " but not supported by DB Platform - ignored");
            desc.setIdType(null);
        }
        if (IdType.IDENTITY.equals((Object)desc.getIdType()) && !this.dbIdentity.isSupportsIdentity()) {
            logger.info("Explicit Identity on " + desc.getFullName() + " but not supported by DB Platform - ignored");
            desc.setIdType(null);
        }
        if (desc.getIdType() == null) {
            desc.setIdType(this.dbIdentity.getIdType());
        }
        if (IdType.GENERATOR.equals((Object)desc.getIdType()) && "auto.uuid".equals(genName = desc.getIdGeneratorName())) {
            desc.setIdGenerator(this.uuidIdGenerator);
            return IdType.GENERATOR;
        }
        if (desc.getBaseTable() == null) {
            return null;
        }
        if (IdType.IDENTITY.equals((Object)desc.getIdType())) {
            String selectLastInsertedId = this.dbIdentity.getSelectLastInsertedId(desc.getBaseTable());
            desc.setSelectLastInsertedId(selectLastInsertedId);
            return IdType.IDENTITY;
        }
        String seqName = desc.getIdGeneratorName();
        if (seqName != null) {
            logger.fine("explicit sequence " + seqName + " on " + desc.getFullName());
        } else {
            String primaryKeyColumn = desc.getSinglePrimaryKeyColumn();
            seqName = this.namingConvention.getSequenceName(desc.getBaseTable(), primaryKeyColumn);
        }
        IdGenerator seqIdGen = this.createSequenceIdGenerator(seqName);
        desc.setIdGenerator(seqIdGen);
        return IdType.SEQUENCE;
    }

    private IdGenerator createSequenceIdGenerator(String seqName) {
        return this.databasePlatform.createSequenceIdGenerator(this.backgroundExecutor, this.dataSource, seqName, this.dbSequenceBatchSize);
    }

    private void createByteCode(DeployBeanDescriptor<?> deploy) {
        this.setEntityBeanClass(deploy);
        this.setBeanReflect(deploy);
    }

    private void autoAddValidators(DeployBeanDescriptor<?> deployDesc) {
        for (DeployBeanProperty prop : deployDesc.propertiesBase()) {
            this.autoAddValidators(prop);
        }
    }

    private void autoAddValidators(DeployBeanProperty prop) {
        if (String.class.equals(prop.getPropertyType()) && prop.getDbLength() > 0 && !prop.containsValidatorType(LengthValidatorFactory.LengthValidator.class)) {
            prop.addValidator(LengthValidatorFactory.create(0, prop.getDbLength()));
        }
        if (!(prop.isNullable() || prop.isId() || prop.isGenerated() || prop.containsValidatorType(NotNullValidatorFactory.NotNullValidator.class))) {
            prop.addValidator(NotNullValidatorFactory.NOT_NULL);
        }
    }

    private void setScalarType(DeployBeanDescriptor<?> deployDesc) {
        Iterator<DeployBeanProperty> it = deployDesc.propertiesAll();
        while (it.hasNext()) {
            DeployBeanProperty prop = it.next();
            if (prop instanceof DeployBeanPropertyAssoc) continue;
            this.deployUtil.setScalarType(prop);
        }
    }

    private void readXml(DeployBeanDescriptor<?> deployDesc) {
        List<Dnode> eXml = this.xmlConfig.findEntityXml(deployDesc.getFullName());
        this.readXmlRawSql(deployDesc, eXml);
        Dnode entityXml = this.deployOrmXml.findEntityDeploymentXml(deployDesc.getFullName());
        if (entityXml != null) {
            this.readXmlNamedQueries(deployDesc, entityXml);
            this.readXmlSql(deployDesc, entityXml);
        }
    }

    private void readXmlSql(DeployBeanDescriptor<?> deployDesc, Dnode entityXml) {
        List<Dnode> sqlSelectList = entityXml.findAll("sql-select", entityXml.getLevel() + 1);
        for (int i = 0; i < sqlSelectList.size(); ++i) {
            Dnode sqlSelect = sqlSelectList.get(i);
            this.readSqlSelect(deployDesc, sqlSelect);
        }
    }

    private String findContent(Dnode node, String nodeName) {
        Dnode found = node.find(nodeName);
        if (found != null) {
            return found.getNodeContent();
        }
        return null;
    }

    private void readSqlSelect(DeployBeanDescriptor<?> deployDesc, Dnode sqlSelect) {
        String name = sqlSelect.getStringAttr("name", "default");
        String extend = sqlSelect.getStringAttr("extend", null);
        String queryDebug = sqlSelect.getStringAttr("debug", null);
        boolean debug = queryDebug != null && queryDebug.equalsIgnoreCase("true");
        String query = this.findContent(sqlSelect, "query");
        String where = this.findContent(sqlSelect, "where");
        String having = this.findContent(sqlSelect, "having");
        String columnMapping = this.findContent(sqlSelect, "columnMapping");
        DRawSqlMeta m = new DRawSqlMeta(name, extend, query, debug, where, having, columnMapping);
        deployDesc.add(m);
    }

    private void readXmlRawSql(DeployBeanDescriptor<?> deployDesc, List<Dnode> entityXml) {
        List<Dnode> rawSqlQueries = this.xmlConfig.find(entityXml, "raw-sql");
        for (int i = 0; i < rawSqlQueries.size(); ++i) {
            Dnode rawSqlDnode = rawSqlQueries.get(i);
            String name = rawSqlDnode.getAttribute("name");
            if (this.isEmpty(name)) {
                throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing name attribute");
            }
            Dnode queryNode = rawSqlDnode.find("query");
            if (queryNode == null) {
                throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing query element");
            }
            String sql = queryNode.getNodeContent();
            if (this.isEmpty(sql)) {
                throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " has empty sql in the query element?");
            }
            List<Dnode> columnMappings = rawSqlDnode.findAll("columnMapping", 1);
            RawSqlBuilder rawSqlBuilder = RawSqlBuilder.parse(sql);
            for (int j = 0; j < columnMappings.size(); ++j) {
                Dnode cm = columnMappings.get(j);
                String column = cm.getAttribute("column");
                String property = cm.getAttribute("property");
                rawSqlBuilder.columnMapping(column, property);
            }
            RawSql rawSql = rawSqlBuilder.create();
            DeployNamedQuery namedQuery = new DeployNamedQuery(name, rawSql);
            deployDesc.add(namedQuery);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    private void readXmlNamedQueries(DeployBeanDescriptor<?> deployDesc, Dnode entityXml) {
        List<Dnode> namedQueries = entityXml.findAll("named-query", 1);
        for (Dnode namedQueryXml : namedQueries) {
            String name = namedQueryXml.getAttribute("name");
            Dnode query = namedQueryXml.find("query");
            if (query == null) {
                logger.warning("orm.xml " + deployDesc.getFullName() + " named-query missing query element?");
                continue;
            }
            String oql = query.getNodeContent();
            if (name == null || oql == null) {
                logger.warning("orm.xml " + deployDesc.getFullName() + " named-query has no query content?");
                continue;
            }
            DeployNamedQuery q = new DeployNamedQuery(name, oql, null);
            deployDesc.add(q);
        }
    }

    private BeanReflectFactory createReflectionFactory() {
        return new EnhanceBeanReflectFactory();
    }

    private void setBeanReflect(DeployBeanDescriptor<?> desc) {
        Class<?> beanType = desc.getBeanType();
        Class<?> factType = desc.getFactoryType();
        BeanReflect beanReflect = this.reflectFactory.create(beanType, factType);
        desc.setBeanReflect(beanReflect);
        try {
            Iterator<DeployBeanProperty> it = desc.propertiesAll();
            while (it.hasNext()) {
                DeployBeanProperty prop = it.next();
                String propName = prop.getName();
                if (desc.isAbstract() || beanReflect.isVanillaOnly()) {
                    prop.setGetter(ReflectGetter.create(prop));
                    prop.setSetter(ReflectSetter.create(prop));
                    continue;
                }
                BeanReflectGetter getter = beanReflect.getGetter(propName);
                BeanReflectSetter setter = beanReflect.getSetter(propName);
                prop.setGetter(getter);
                prop.setSetter(setter);
                if (getter != null) continue;
                String m = "BeanReflectGetter for " + prop.getFullBeanName() + " was not found?";
                throw new RuntimeException(m);
            }
        }
        catch (IllegalArgumentException e) {
            Class<?> superClass = desc.getBeanType().getSuperclass();
            String msg = "Error with [" + desc.getFullName() + "] I believe it is not enhanced but it's superClass [" + superClass + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)";
            throw new PersistenceException(msg, e);
        }
    }

    private void setConcurrencyMode(DeployBeanDescriptor<?> desc) {
        if (!desc.getConcurrencyMode().equals((Object)ConcurrencyMode.ALL)) {
            return;
        }
        if (this.checkForVersionProperties(desc)) {
            desc.setConcurrencyMode(ConcurrencyMode.VERSION);
        }
    }

    private boolean checkForVersionProperties(DeployBeanDescriptor<?> desc) {
        boolean hasVersionProperty = false;
        List<DeployBeanProperty> props = desc.propertiesBase();
        for (int i = 0; i < props.size(); ++i) {
            if (!props.get(i).isVersionColumn()) continue;
            hasVersionProperty = true;
        }
        return hasVersionProperty;
    }

    private boolean hasEntityBeanInterface(Class<?> beanClass) {
        Class<?>[] interfaces = beanClass.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            if (!interfaces[i].equals(EntityBean.class)) continue;
            return true;
        }
        return false;
    }

    private void setEntityBeanClass(DeployBeanDescriptor<?> desc) {
        block13: {
            Class<?> beanClass = desc.getBeanType();
            if (desc.isAbstract()) {
                if (this.hasEntityBeanInterface(beanClass)) {
                    this.checkEnhanced(desc, beanClass);
                } else {
                    this.checkSubclass(desc, beanClass);
                }
                return;
            }
            try {
                Object testBean = null;
                try {
                    testBean = beanClass.newInstance();
                }
                catch (InstantiationException e) {
                    logger.fine("no default constructor on " + beanClass + " e:" + e);
                }
                catch (IllegalAccessException e) {
                    logger.fine("no default constructor on " + beanClass + " e:" + e);
                }
                if (!(testBean instanceof EntityBean)) {
                    this.checkSubclass(desc, beanClass);
                    break block13;
                }
                String className = beanClass.getName();
                try {
                    String marker = ((EntityBean)testBean)._ebean_getMarker();
                    if (!marker.equals(className)) {
                        String msg = "Error with [" + desc.getFullName() + "] It has not been enhanced but it's superClass [" + beanClass.getSuperclass() + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)" + " marker[" + marker + "] className[" + className + "]";
                        throw new PersistenceException(msg);
                    }
                }
                catch (AbstractMethodError e) {
                    throw new PersistenceException("Old Ebean v1.0 enhancement detected in Ebean v1.1 - please do a clean enhancement.", e);
                }
                this.checkEnhanced(desc, beanClass);
            }
            catch (PersistenceException ex) {
                throw ex;
            }
            catch (Exception ex) {
                throw new PersistenceException(ex);
            }
        }
    }

    private void checkEnhanced(DeployBeanDescriptor<?> desc, Class<?> beanClass) {
        this.checkInheritedClasses(true, beanClass);
        desc.setFactoryType(beanClass);
        if (!beanClass.getName().startsWith("com.avaje.ebean.meta")) {
            ++this.enhancedClassCount;
        }
    }

    private void checkSubclass(DeployBeanDescriptor<?> desc, Class<?> beanClass) {
        this.checkInheritedClasses(false, beanClass);
        desc.checkReadAndWriteMethods();
        ++this.subclassClassCount;
        BeanDescriptor.EntityType entityType = desc.getEntityType();
        if (BeanDescriptor.EntityType.XMLELEMENT.equals((Object)entityType)) {
            desc.setFactoryType(beanClass);
        } else {
            Class<?> subClass = this.subClassManager.resolve(beanClass.getName());
            desc.setFactoryType(subClass);
            this.subclassedEntities.add(desc.getName());
        }
    }

    private void checkInheritedClasses(boolean ensureEnhanced, Class<?> beanClass) {
        Class<?> superclass = beanClass.getSuperclass();
        if (Object.class.equals(superclass)) {
            return;
        }
        boolean isClassEnhanced = EntityBean.class.isAssignableFrom(superclass);
        if (ensureEnhanced != isClassEnhanced) {
            String msg = ensureEnhanced ? "Class [" + superclass + "] is not enhanced and [" + beanClass + "] is - (you can not mix!!)" : "Class [" + superclass + "] is enhanced and [" + beanClass + "] is not - (you can not mix!!)";
            throw new IllegalStateException(msg);
        }
        this.checkInheritedClasses(ensureEnhanced, superclass);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class BeanDescComparator
    implements Comparator<BeanDescriptor<?>>,
    Serializable {
        private static final long serialVersionUID = 1L;

        private BeanDescComparator() {
        }

        @Override
        public int compare(BeanDescriptor<?> o1, BeanDescriptor<?> o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

