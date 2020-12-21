/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Update;
import java.util.Collection;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Persister {
    public void forceUpdate(Object var1, Set<String> var2, Transaction var3, boolean var4, boolean var5);

    public void forceInsert(Object var1, Transaction var2);

    public void save(Object var1, Transaction var2);

    public void saveManyToManyAssociations(Object var1, String var2, Transaction var3);

    public void saveAssociation(Object var1, String var2, Transaction var3);

    public int deleteManyToManyAssociations(Object var1, String var2, Transaction var3);

    public int delete(Class<?> var1, Object var2, Transaction var3);

    public void delete(Object var1, Transaction var2);

    public void deleteMany(Class<?> var1, Collection<?> var2, Transaction var3);

    public int executeOrmUpdate(Update<?> var1, Transaction var2);

    public int executeSqlUpdate(SqlUpdate var1, Transaction var2);

    public int executeCallable(CallableSql var1, Transaction var2);
}

