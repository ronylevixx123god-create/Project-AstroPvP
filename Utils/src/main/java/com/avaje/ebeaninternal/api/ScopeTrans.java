/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.TxScope;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiTransactionScopeManager;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScopeTrans
implements Thread.UncaughtExceptionHandler {
    private static final int OPCODE_ATHROW = 191;
    private final SpiTransactionScopeManager scopeMgr;
    private final SpiTransaction suspendedTransaction;
    private final SpiTransaction transaction;
    private final boolean rollbackOnChecked;
    private final boolean created;
    private final ArrayList<Class<? extends Throwable>> noRollbackFor;
    private final ArrayList<Class<? extends Throwable>> rollbackFor;
    private final Thread.UncaughtExceptionHandler originalUncaughtHandler;
    private boolean rolledBack;

    public ScopeTrans(boolean rollbackOnChecked, boolean created, SpiTransaction transaction, TxScope txScope, SpiTransaction suspendedTransaction, SpiTransactionScopeManager scopeMgr) {
        this.rollbackOnChecked = rollbackOnChecked;
        this.created = created;
        this.transaction = transaction;
        this.suspendedTransaction = suspendedTransaction;
        this.scopeMgr = scopeMgr;
        this.noRollbackFor = txScope.getNoRollbackFor();
        this.rollbackFor = txScope.getRollbackFor();
        Thread t = Thread.currentThread();
        this.originalUncaughtHandler = t.getUncaughtExceptionHandler();
        t.setUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        this.caughtThrowable(e);
        this.onFinally();
        if (this.originalUncaughtHandler != null) {
            this.originalUncaughtHandler.uncaughtException(thread, e);
        }
    }

    public void onExit(Object returnOrThrowable, int opCode) {
        if (opCode == 191) {
            this.caughtThrowable((Throwable)returnOrThrowable);
        }
        this.onFinally();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onFinally() {
        try {
            if (this.originalUncaughtHandler != null) {
                Thread.currentThread().setUncaughtExceptionHandler(this.originalUncaughtHandler);
            }
            if (!this.rolledBack && this.created) {
                this.transaction.commit();
            }
        }
        finally {
            if (this.suspendedTransaction != null) {
                this.scopeMgr.replace(this.suspendedTransaction);
            }
        }
    }

    public Error caughtError(Error e) {
        this.rollback(e);
        return e;
    }

    public <T extends Throwable> T caughtThrowable(T e) {
        if (this.isRollbackThrowable(e)) {
            this.rollback(e);
        }
        return e;
    }

    private void rollback(Throwable e) {
        if (this.transaction != null && this.transaction.isActive()) {
            this.transaction.rollback(e);
        }
        this.rolledBack = true;
    }

    private boolean isRollbackThrowable(Throwable e) {
        int i;
        if (e instanceof Error) {
            return true;
        }
        if (this.noRollbackFor != null) {
            for (i = 0; i < this.noRollbackFor.size(); ++i) {
                if (!this.noRollbackFor.get(i).equals(e.getClass())) continue;
                return false;
            }
        }
        if (this.rollbackFor != null) {
            for (i = 0; i < this.rollbackFor.size(); ++i) {
                if (!this.rollbackFor.get(i).equals(e.getClass())) continue;
                return true;
            }
        }
        if (e instanceof RuntimeException) {
            return true;
        }
        return this.rollbackOnChecked;
    }
}

