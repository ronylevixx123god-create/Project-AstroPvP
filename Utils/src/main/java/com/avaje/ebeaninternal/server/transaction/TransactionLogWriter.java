/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;

public interface TransactionLogWriter {
    public void log(TransactionLogBuffer var1);

    public void shutdown();
}

