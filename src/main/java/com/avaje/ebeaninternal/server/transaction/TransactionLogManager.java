/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
import com.avaje.ebeaninternal.server.transaction.log.FileTransactionLoggerWrapper;
import com.avaje.ebeaninternal.server.transaction.log.JuliTransactionLogger;

public class TransactionLogManager {
    private final TransactionLogWriter logWriter;

    public TransactionLogManager(ServerConfig serverConfig) {
        this.logWriter = serverConfig.isLoggingToJavaLogger() ? new JuliTransactionLogger() : new FileTransactionLoggerWrapper(serverConfig);
    }

    public void shutdown() {
        this.logWriter.shutdown();
    }

    public void log(TransactionLogBuffer logBuffer) {
        this.logWriter.log(logBuffer);
    }
}

