/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.BeanState;
import com.avaje.ebean.CallableSql;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.Filter;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.TxCallable;
import com.avaje.ebean.TxIsolation;
import com.avaje.ebean.TxRunnable;
import com.avaje.ebean.TxScope;
import com.avaje.ebean.Update;
import com.avaje.ebean.ValuePair;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.text.csv.CsvReader;
import com.avaje.ebean.text.json.JsonContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class Ebean {
    private static final Logger logger = Logger.getLogger(Ebean.class.getName());
    private static final String EBVERSION = "2.7.3";
    private static final ServerManager serverMgr;

    private Ebean() {
    }

    public static EbeanServer getServer(String name) {
        return Ebean.serverMgr.get(name);
    }

    public static ExpressionFactory getExpressionFactory() {
        return Ebean.serverMgr.getPrimaryServer().getExpressionFactory();
    }

    protected static void register(EbeanServer server, boolean isPrimaryServer) {
        Ebean.serverMgr.register(server, isPrimaryServer);
    }

    public static Object nextId(Class<?> beanType) {
        return Ebean.serverMgr.getPrimaryServer().nextId(beanType);
    }

    public static void logComment(String msg) {
        Ebean.serverMgr.getPrimaryServer().logComment(msg);
    }

    public static Transaction beginTransaction() {
        return Ebean.serverMgr.getPrimaryServer().beginTransaction();
    }

    public static Transaction beginTransaction(TxIsolation isolation) {
        return Ebean.serverMgr.getPrimaryServer().beginTransaction(isolation);
    }

    public static Transaction currentTransaction() {
        return Ebean.serverMgr.getPrimaryServer().currentTransaction();
    }

    public static void commitTransaction() {
        Ebean.serverMgr.getPrimaryServer().commitTransaction();
    }

    public static void rollbackTransaction() {
        Ebean.serverMgr.getPrimaryServer().rollbackTransaction();
    }

    public static void endTransaction() {
        Ebean.serverMgr.getPrimaryServer().endTransaction();
    }

    public static InvalidValue validate(Object bean) {
        return Ebean.serverMgr.getPrimaryServer().validate(bean);
    }

    public static InvalidValue[] validate(Object bean, String propertyName, Object value) {
        return Ebean.serverMgr.getPrimaryServer().validate(bean, propertyName, value);
    }

    public static Map<String, ValuePair> diff(Object a, Object b) {
        return Ebean.serverMgr.getPrimaryServer().diff(a, b);
    }

    public static void save(Object bean) throws OptimisticLockException {
        Ebean.serverMgr.getPrimaryServer().save(bean);
    }

    public static void update(Object bean) {
        Ebean.serverMgr.getPrimaryServer().update(bean);
    }

    public static void update(Object bean, Set<String> updateProps) {
        Ebean.serverMgr.getPrimaryServer().update(bean, updateProps);
    }

    public static int save(Iterator<?> iterator) throws OptimisticLockException {
        return Ebean.serverMgr.getPrimaryServer().save(iterator);
    }

    public static int save(Collection<?> c) throws OptimisticLockException {
        return Ebean.save(c.iterator());
    }

    public static int deleteManyToManyAssociations(Object ownerBean, String propertyName) {
        return Ebean.serverMgr.getPrimaryServer().deleteManyToManyAssociations(ownerBean, propertyName);
    }

    public static void saveManyToManyAssociations(Object ownerBean, String propertyName) {
        Ebean.serverMgr.getPrimaryServer().saveManyToManyAssociations(ownerBean, propertyName);
    }

    public static void saveAssociation(Object ownerBean, String propertyName) {
        Ebean.serverMgr.getPrimaryServer().saveAssociation(ownerBean, propertyName);
    }

    public static void delete(Object bean) throws OptimisticLockException {
        Ebean.serverMgr.getPrimaryServer().delete(bean);
    }

    public static int delete(Class<?> beanType, Object id) {
        return Ebean.serverMgr.getPrimaryServer().delete(beanType, id);
    }

    public static void delete(Class<?> beanType, Collection<?> ids) {
        Ebean.serverMgr.getPrimaryServer().delete(beanType, ids);
    }

    public static int delete(Iterator<?> it) throws OptimisticLockException {
        return Ebean.serverMgr.getPrimaryServer().delete(it);
    }

    public static int delete(Collection<?> c) throws OptimisticLockException {
        return Ebean.delete(c.iterator());
    }

    public static void refresh(Object bean) {
        Ebean.serverMgr.getPrimaryServer().refresh(bean);
    }

    public static void refreshMany(Object bean, String manyPropertyName) {
        Ebean.serverMgr.getPrimaryServer().refreshMany(bean, manyPropertyName);
    }

    public static <T> T getReference(Class<T> beanType, Object id) {
        return Ebean.serverMgr.getPrimaryServer().getReference(beanType, id);
    }

    public static <T> void sort(List<T> list, String sortByClause) {
        Ebean.serverMgr.getPrimaryServer().sort(list, sortByClause);
    }

    public static <T> T find(Class<T> beanType, Object id) {
        return Ebean.serverMgr.getPrimaryServer().find(beanType, id);
    }

    public static SqlQuery createSqlQuery(String sql) {
        return Ebean.serverMgr.getPrimaryServer().createSqlQuery(sql);
    }

    public static SqlQuery createNamedSqlQuery(String namedQuery) {
        return Ebean.serverMgr.getPrimaryServer().createNamedSqlQuery(namedQuery);
    }

    public static SqlUpdate createSqlUpdate(String sql) {
        return Ebean.serverMgr.getPrimaryServer().createSqlUpdate(sql);
    }

    public static CallableSql createCallableSql(String sql) {
        return Ebean.serverMgr.getPrimaryServer().createCallableSql(sql);
    }

    public static SqlUpdate createNamedSqlUpdate(String namedQuery) {
        return Ebean.serverMgr.getPrimaryServer().createNamedSqlUpdate(namedQuery);
    }

    public static <T> Query<T> createNamedQuery(Class<T> beanType, String namedQuery) {
        return Ebean.serverMgr.getPrimaryServer().createNamedQuery(beanType, namedQuery);
    }

    public static <T> Query<T> createQuery(Class<T> beanType, String query) {
        return Ebean.serverMgr.getPrimaryServer().createQuery(beanType, query);
    }

    public static <T> Update<T> createNamedUpdate(Class<T> beanType, String namedUpdate) {
        return Ebean.serverMgr.getPrimaryServer().createNamedUpdate(beanType, namedUpdate);
    }

    public static <T> Update<T> createUpdate(Class<T> beanType, String ormUpdate) {
        return Ebean.serverMgr.getPrimaryServer().createUpdate(beanType, ormUpdate);
    }

    public static <T> CsvReader<T> createCsvReader(Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().createCsvReader(beanType);
    }

    public static <T> Query<T> createQuery(Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().createQuery(beanType);
    }

    public static <T> Query<T> find(Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().find(beanType);
    }

    public static <T> Filter<T> filter(Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().filter(beanType);
    }

    public static int execute(SqlUpdate sqlUpdate) {
        return Ebean.serverMgr.getPrimaryServer().execute(sqlUpdate);
    }

    public static int execute(CallableSql callableSql) {
        return Ebean.serverMgr.getPrimaryServer().execute(callableSql);
    }

    public static void execute(TxScope scope, TxRunnable r) {
        Ebean.serverMgr.getPrimaryServer().execute(scope, r);
    }

    public static void execute(TxRunnable r) {
        Ebean.serverMgr.getPrimaryServer().execute(r);
    }

    public static <T> T execute(TxScope scope, TxCallable<T> c) {
        return Ebean.serverMgr.getPrimaryServer().execute(scope, c);
    }

    public static <T> T execute(TxCallable<T> c) {
        return Ebean.serverMgr.getPrimaryServer().execute(c);
    }

    public static void externalModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
        Ebean.serverMgr.getPrimaryServer().externalModification(tableName, inserts, updates, deletes);
    }

    public static BeanState getBeanState(Object bean) {
        return Ebean.serverMgr.getPrimaryServer().getBeanState(bean);
    }

    public static ServerCacheManager getServerCacheManager() {
        return Ebean.serverMgr.getPrimaryServer().getServerCacheManager();
    }

    public static BackgroundExecutor getBackgroundExecutor() {
        return Ebean.serverMgr.getPrimaryServer().getBackgroundExecutor();
    }

    public static void runCacheWarming() {
        Ebean.serverMgr.getPrimaryServer().runCacheWarming();
    }

    public static void runCacheWarming(Class<?> beanType) {
        Ebean.serverMgr.getPrimaryServer().runCacheWarming(beanType);
    }

    public static JsonContext createJsonContext() {
        return Ebean.serverMgr.getPrimaryServer().createJsonContext();
    }

    static {
        String version = System.getProperty("java.version");
        logger.info("Ebean Version[2.7.3] Java Version[" + version + "]");
        serverMgr = new ServerManager();
    }

    private static final class ServerManager {
        private final ConcurrentHashMap<String, EbeanServer> concMap = new ConcurrentHashMap();
        private final HashMap<String, EbeanServer> syncMap = new HashMap();
        private final Object monitor = new Object();
        private EbeanServer primaryServer;

        private ServerManager() {
            if (GlobalProperties.isSkipPrimaryServer()) {
                logger.fine("GlobalProperties.isSkipPrimaryServer()");
            } else {
                String primaryName = this.getPrimaryServerName();
                logger.fine("primaryName:" + primaryName);
                if (primaryName != null && primaryName.trim().length() > 0) {
                    this.primaryServer = this.getWithCreate(primaryName.trim());
                }
            }
        }

        private String getPrimaryServerName() {
            String serverName = GlobalProperties.get("ebean.default.datasource", null);
            return GlobalProperties.get("datasource.default", serverName);
        }

        private EbeanServer getPrimaryServer() {
            if (this.primaryServer == null) {
                String msg = "The default EbeanServer has not been defined?";
                msg = msg + " This is normally set via the ebean.datasource.default property.";
                msg = msg + " Otherwise it should be registered programatically via registerServer()";
                throw new PersistenceException(msg);
            }
            return this.primaryServer;
        }

        private EbeanServer get(String name) {
            if (name == null || name.length() == 0) {
                return this.primaryServer;
            }
            EbeanServer server = this.concMap.get(name);
            if (server != null) {
                return server;
            }
            return this.getWithCreate(name);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private EbeanServer getWithCreate(String name) {
            Object object = this.monitor;
            synchronized (object) {
                EbeanServer server = this.syncMap.get(name);
                if (server == null) {
                    server = EbeanServerFactory.create(name);
                    this.register(server, false);
                }
                return server;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void register(EbeanServer server, boolean isPrimaryServer) {
            Object object = this.monitor;
            synchronized (object) {
                this.concMap.put(server.getName(), server);
                EbeanServer existingServer = this.syncMap.put(server.getName(), server);
                if (existingServer != null) {
                    String msg = "Existing EbeanServer [" + server.getName() + "] is being replaced?";
                    logger.warning(msg);
                }
                if (isPrimaryServer) {
                    this.primaryServer = server;
                }
            }
        }
    }
}

