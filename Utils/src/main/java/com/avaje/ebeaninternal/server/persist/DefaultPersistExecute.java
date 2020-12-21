/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.persist.BeanPersister;
import com.avaje.ebeaninternal.server.persist.Binder;
import com.avaje.ebeaninternal.server.persist.ExeCallableSql;
import com.avaje.ebeaninternal.server.persist.ExeOrmUpdate;
import com.avaje.ebeaninternal.server.persist.ExeUpdateSql;
import com.avaje.ebeaninternal.server.persist.PersistExecute;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DefaultPersistExecute
implements PersistExecute {
    private final ExeCallableSql exeCallableSql;
    private final ExeUpdateSql exeUpdateSql;
    private final ExeOrmUpdate exeOrmUpdate;
    private final int defaultBatchSize;
    private final boolean defaultBatchGenKeys;
    private final boolean validate;

    public DefaultPersistExecute(boolean validate, Binder binder, PstmtBatch pstmtBatch) {
        this.validate = validate;
        this.exeOrmUpdate = new ExeOrmUpdate(binder, pstmtBatch);
        this.exeUpdateSql = new ExeUpdateSql(binder, pstmtBatch);
        this.exeCallableSql = new ExeCallableSql(binder, pstmtBatch);
        this.defaultBatchGenKeys = GlobalProperties.getBoolean("batch.getgeneratedkeys", true);
        this.defaultBatchSize = GlobalProperties.getInt("batch.size", 20);
    }

    @Override
    public BatchControl createBatchControl(SpiTransaction t) {
        return new BatchControl(t, this.defaultBatchSize, this.defaultBatchGenKeys);
    }

    @Override
    public <T> void executeInsertBean(PersistRequestBean<T> request) {
        BeanManager<T> mgr = request.getBeanManager();
        BeanPersister persister = mgr.getBeanPersister();
        BeanPersistController controller = request.getBeanController();
        if (controller == null || controller.preInsert(request)) {
            if (this.validate) {
                request.validate();
            }
            persister.insert(request);
        }
    }

    @Override
    public <T> void executeUpdateBean(PersistRequestBean<T> request) {
        BeanManager<T> mgr = request.getBeanManager();
        BeanPersister persister = mgr.getBeanPersister();
        BeanPersistController controller = request.getBeanController();
        if (controller == null || controller.preUpdate(request)) {
            if (this.validate) {
                request.validate();
            }
            persister.update(request);
        }
    }

    @Override
    public <T> void executeDeleteBean(PersistRequestBean<T> request) {
        BeanManager<T> mgr = request.getBeanManager();
        BeanPersister persister = mgr.getBeanPersister();
        BeanPersistController controller = request.getBeanController();
        if (controller == null || controller.preDelete(request)) {
            persister.delete(request);
        }
    }

    @Override
    public int executeOrmUpdate(PersistRequestOrmUpdate request) {
        return this.exeOrmUpdate.execute(request);
    }

    @Override
    public int executeSqlUpdate(PersistRequestUpdateSql request) {
        return this.exeUpdateSql.execute(request);
    }

    @Override
    public int executeSqlCallable(PersistRequestCallableSql request) {
        return this.exeCallableSql.execute(request);
    }
}

