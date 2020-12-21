/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.CallableSql;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.TransactionEventTable;

public interface SpiCallableSql
extends CallableSql {
    public BindParams getBindParams();

    public TransactionEventTable getTransactionEventTable();
}

