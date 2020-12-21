/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction.log;

import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
import com.avaje.ebeaninternal.server.transaction.log.FileTransactionLogger;
import java.util.logging.Logger;

public class FileTransactionLoggerWrapper
implements TransactionLogWriter {
    private static final Logger logger = Logger.getLogger(FileTransactionLoggerWrapper.class.getName());
    private final String serverName;
    private final String dir;
    private final int maxFileSize;
    private volatile FileTransactionLogger logWriter;

    public FileTransactionLoggerWrapper(ServerConfig serverConfig) {
        String evalDir = serverConfig.getLoggingDirectoryWithEval();
        this.dir = evalDir != null ? evalDir : "logs";
        this.maxFileSize = GlobalProperties.getInt("ebean.logging.maxFileSize", 0x6400000);
        this.serverName = serverConfig.getName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FileTransactionLogger initialiseLogger() {
        FileTransactionLoggerWrapper fileTransactionLoggerWrapper = this;
        synchronized (fileTransactionLoggerWrapper) {
            FileTransactionLogger newLogWriter;
            FileTransactionLogger writer = this.logWriter;
            if (writer != null) {
                return writer;
            }
            String middleName = GlobalProperties.get("ebean.logging.filename", "_txn_");
            String logPrefix = this.serverName + middleName;
            String threadName = "Ebean-" + this.serverName + "-TxnLogWriter";
            this.logWriter = newLogWriter = new FileTransactionLogger(threadName, this.dir, logPrefix, this.maxFileSize);
            newLogWriter.start();
            logger.info("Transaction logs in: " + this.dir);
            return newLogWriter;
        }
    }

    public void log(TransactionLogBuffer logBuffer) {
        FileTransactionLogger writer = this.logWriter;
        if (writer == null) {
            writer = this.initialiseLogger();
        }
        writer.log(logBuffer);
    }

    public void shutdown() {
        if (this.logWriter != null) {
            this.logWriter.shutdown();
        }
    }
}

