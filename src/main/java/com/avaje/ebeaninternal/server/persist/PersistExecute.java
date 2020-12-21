/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
import com.avaje.ebeaninternal.server.persist.BatchControl;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface PersistExecute {
    public BatchControl createBatchControl(SpiTransaction var1);

    public <T> void executeInsertBean(PersistRequestBean<T> var1);

    public <T> void executeUpdateBean(PersistRequestBean<T> var1);

    public <T> void executeDeleteBean(PersistRequestBean<T> var1);

    public int executeOrmUpdate(PersistRequestOrmUpdate var1);

    public int executeSqlCallable(PersistRequestCallableSql var1);

    public int executeSqlUpdate(PersistRequestUpdateSql var1);
}

