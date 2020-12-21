/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.AdminAutofetch;
import com.avaje.ebean.AdminLogging;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.BeanState;
import com.avaje.ebean.CallableSql;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.Filter;
import com.avaje.ebean.FutureIds;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.SqlFutureList;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.TxCallable;
import com.avaje.ebean.TxIsolation;
import com.avaje.ebean.TxRunnable;
import com.avaje.ebean.TxScope;
import com.avaje.ebean.Update;
import com.avaje.ebean.ValuePair;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.lucene.LuceneIndex;
import com.avaje.ebean.text.csv.CsvReader;
import com.avaje.ebean.text.json.JsonContext;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.OptimisticLockException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface EbeanServer {
    public AdminLogging getAdminLogging();

    public AdminAutofetch getAdminAutofetch();

    public LuceneIndex getLuceneIndex(Class<?> var1);

    public String getName();

    public ExpressionFactory getExpressionFactory();

    public BeanState getBeanState(Object var1);

    public Object getBeanId(Object var1);

    public Map<String, ValuePair> diff(Object var1, Object var2);

    public InvalidValue validate(Object var1);

    public InvalidValue[] validate(Object var1, String var2, Object var3);

    public <T> T createEntityBean(Class<T> var1);

    public ObjectInputStream createProxyObjectInputStream(InputStream var1);

    public <T> CsvReader<T> createCsvReader(Class<T> var1);

    public <T> Query<T> createNamedQuery(Class<T> var1, String var2);

    public <T> Query<T> createQuery(Class<T> var1, String var2);

    public <T> Query<T> createQuery(Class<T> var1);

    public <T> Query<T> find(Class<T> var1);

    public Object nextId(Class<?> var1);

    public <T> Filter<T> filter(Class<T> var1);

    public <T> void sort(List<T> var1, String var2);

    public <T> Update<T> createNamedUpdate(Class<T> var1, String var2);

    public <T> Update<T> createUpdate(Class<T> var1, String var2);

    public SqlQuery createSqlQuery(String var1);

    public SqlQuery createNamedSqlQuery(String var1);

    public SqlUpdate createSqlUpdate(String var1);

    public CallableSql createCallableSql(String var1);

    public SqlUpdate createNamedSqlUpdate(String var1);

    public Transaction createTransaction();

    public Transaction createTransaction(TxIsolation var1);

    public Transaction beginTransaction();

    public Transaction beginTransaction(TxIsolation var1);

    public Transaction currentTransaction();

    public void commitTransaction();

    public void rollbackTransaction();

    public void endTransaction();

    public void logComment(String var1);

    public void refresh(Object var1);

    public void refreshMany(Object var1, String var2);

    public <T> T find(Class<T> var1, Object var2);

    public <T> T getReference(Class<T> var1, Object var2);

    public <T> int findRowCount(Query<T> var1, Transaction var2);

    public <T> List<Object> findIds(Query<T> var1, Transaction var2);

    public <T> QueryIterator<T> findIterate(Query<T> var1, Transaction var2);

    public <T> void findVisit(Query<T> var1, QueryResultVisitor<T> var2, Transaction var3);

    public <T> List<T> findList(Query<T> var1, Transaction var2);

    public <T> FutureRowCount<T> findFutureRowCount(Query<T> var1, Transaction var2);

    public <T> FutureIds<T> findFutureIds(Query<T> var1, Transaction var2);

    public <T> FutureList<T> findFutureList(Query<T> var1, Transaction var2);

    public SqlFutureList findFutureList(SqlQuery var1, Transaction var2);

    public <T> PagingList<T> findPagingList(Query<T> var1, Transaction var2, int var3);

    public <T> Set<T> findSet(Query<T> var1, Transaction var2);

    public <T> Map<?, T> findMap(Query<T> var1, Transaction var2);

    public <T> T findUnique(Query<T> var1, Transaction var2);

    public List<SqlRow> findList(SqlQuery var1, Transaction var2);

    public Set<SqlRow> findSet(SqlQuery var1, Transaction var2);

    public Map<?, SqlRow> findMap(SqlQuery var1, Transaction var2);

    public SqlRow findUnique(SqlQuery var1, Transaction var2);

    public void save(Object var1) throws OptimisticLockException;

    public int save(Iterator<?> var1) throws OptimisticLockException;

    public int save(Collection<?> var1) throws OptimisticLockException;

    public void delete(Object var1) throws OptimisticLockException;

    public int delete(Iterator<?> var1) throws OptimisticLockException;

    public int delete(Collection<?> var1) throws OptimisticLockException;

    public int delete(Class<?> var1, Object var2);

    public int delete(Class<?> var1, Object var2, Transaction var3);

    public void delete(Class<?> var1, Collection<?> var2);

    public void delete(Class<?> var1, Collection<?> var2, Transaction var3);

    public int execute(SqlUpdate var1);

    public int execute(Update<?> var1);

    public int execute(Update<?> var1, Transaction var2);

    public int execute(CallableSql var1);

    public void externalModification(String var1, boolean var2, boolean var3, boolean var4);

    public <T> T find(Class<T> var1, Object var2, Transaction var3);

    public void save(Object var1, Transaction var2) throws OptimisticLockException;

    public int save(Iterator<?> var1, Transaction var2) throws OptimisticLockException;

    public void update(Object var1);

    public void update(Object var1, Transaction var2);

    public void update(Object var1, Set<String> var2);

    public void update(Object var1, Set<String> var2, Transaction var3);

    public void update(Object var1, Set<String> var2, Transaction var3, boolean var4, boolean var5);

    public void insert(Object var1);

    public void insert(Object var1, Transaction var2);

    public int deleteManyToManyAssociations(Object var1, String var2);

    public int deleteManyToManyAssociations(Object var1, String var2, Transaction var3);

    public void saveManyToManyAssociations(Object var1, String var2);

    public void saveManyToManyAssociations(Object var1, String var2, Transaction var3);

    public void saveAssociation(Object var1, String var2);

    public void saveAssociation(Object var1, String var2, Transaction var3);

    public void delete(Object var1, Transaction var2) throws OptimisticLockException;

    public int delete(Iterator<?> var1, Transaction var2) throws OptimisticLockException;

    public int execute(SqlUpdate var1, Transaction var2);

    public int execute(CallableSql var1, Transaction var2);

    public void execute(TxScope var1, TxRunnable var2);

    public void execute(TxRunnable var1);

    public <T> T execute(TxScope var1, TxCallable<T> var2);

    public <T> T execute(TxCallable<T> var1);

    public ServerCacheManager getServerCacheManager();

    public BackgroundExecutor getBackgroundExecutor();

    public void runCacheWarming();

    public void runCacheWarming(Class<?> var1);

    public JsonContext createJsonContext();
}

