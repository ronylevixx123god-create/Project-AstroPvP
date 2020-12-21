/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.transaction.TransactionMap;

public final class DefaultTransactionThreadLocal {
    private static ThreadLocal<TransactionMap> local = new ThreadLocal<TransactionMap>(){

        @Override
        protected synchronized TransactionMap initialValue() {
            return new TransactionMap();
        }
    };

    private DefaultTransactionThreadLocal() {
    }

    private static TransactionMap.State getState(String serverName) {
        return local.get().getState(serverName);
    }

    public static SpiTransaction get(String serverName) {
        return DefaultTransactionThreadLocal.getState((String)serverName).transaction;
    }

    public static void set(String serverName, SpiTransaction trans) {
        DefaultTransactionThreadLocal.getState(serverName).set(trans);
    }

    public static void commit(String serverName) {
        DefaultTransactionThreadLocal.getState(serverName).commit();
    }

    public static void rollback(String serverName) {
        DefaultTransactionThreadLocal.getState(serverName).rollback();
    }

    public static void end(String serverName) {
        DefaultTransactionThreadLocal.getState(serverName).end();
    }

    public static void replace(String serverName, SpiTransaction trans) {
        DefaultTransactionThreadLocal.getState(serverName).replace(trans);
    }
}

