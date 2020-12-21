/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction.log;

import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
import com.avaje.ebeaninternal.server.transaction.log.LogTime;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

public class FileTransactionLogger
implements Runnable,
TransactionLogWriter {
    private static final Logger logger = Logger.getLogger(FileTransactionLogger.class.getName());
    private static final String atString = "        at ";
    private final String newLinePlaceholder = "\\r\\n";
    private final int maxStackTraceLines = 5;
    private final ConcurrentLinkedQueue<TransactionLogBuffer> logBufferQueue = new ConcurrentLinkedQueue();
    private final Thread logWriterThread;
    private final String threadName;
    private final String filepath;
    private final String deliminator = ", ";
    private final String logFileName;
    private final String logFileSuffix;
    private volatile boolean shutdown;
    private volatile boolean shutdownComplete;
    private PrintStream out;
    private String currentPath;
    private int fileCounter;
    private long maxBytesPerFile;
    private long bytesWritten;

    public FileTransactionLogger(String threadName, String dir, String logFileName, int maxBytesPerFile) {
        this(threadName, dir, logFileName, "log", maxBytesPerFile);
    }

    public FileTransactionLogger(String threadName, String dir, String logFileName, String suffix, int maxBytesPerFile) {
        this.threadName = threadName;
        this.logFileName = logFileName;
        this.logFileSuffix = "." + suffix;
        this.maxBytesPerFile = maxBytesPerFile;
        try {
            this.filepath = this.makeDirIfRequired(dir);
            this.switchFile(LogTime.nextDay());
        }
        catch (Exception e) {
            System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            throw new RuntimeException(e);
        }
        this.logWriterThread = new Thread((Runnable)this, threadName);
        this.logWriterThread.setDaemon(true);
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void start() {
        this.logWriterThread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        this.shutdown = true;
        Thread thread = this.logWriterThread;
        synchronized (thread) {
            try {
                this.logWriterThread.wait(20000L);
                logger.fine("Shutdown LogBufferWriter " + this.threadName + " shutdownComplete:" + this.shutdownComplete);
            }
            catch (InterruptedException e) {
                logger.fine("InterruptedException:" + e);
            }
        }
        if (!this.shutdownComplete) {
            String m = "WARNING: Shutdown of LogBufferWriter " + this.threadName + " not completed.";
            System.err.println(m);
            logger.warning(m);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        Object object;
        int missCount = 0;
        while (!this.shutdown || missCount < 10) {
            if (missCount > 50) {
                if (this.out != null) {
                    this.out.flush();
                }
                try {
                    Thread.sleep(20L);
                }
                catch (InterruptedException e) {
                    logger.log(Level.INFO, "Interrupted TxnLogBufferWriter", e);
                }
            }
            object = this.logBufferQueue;
            synchronized (object) {
                if (this.logBufferQueue.isEmpty()) {
                    ++missCount;
                } else {
                    TransactionLogBuffer buffer = (TransactionLogBuffer)this.logBufferQueue.remove();
                    this.write(buffer);
                    missCount = 0;
                }
            }
        }
        this.close();
        this.shutdownComplete = true;
        object = this.logWriterThread;
        synchronized (object) {
            this.logWriterThread.notifyAll();
        }
    }

    public void log(TransactionLogBuffer logBuffer) {
        this.logBufferQueue.add(logBuffer);
    }

    private void write(TransactionLogBuffer logBuffer) {
        LogTime logTime = LogTime.get();
        if (logTime.isNextDay()) {
            logTime = LogTime.nextDay();
            this.switchFile(logTime);
        }
        if (this.bytesWritten > this.maxBytesPerFile) {
            ++this.fileCounter;
            this.switchFile(logTime);
        }
        String txnId = logBuffer.getTransactionId();
        List<TransactionLogBuffer.LogEntry> messages = logBuffer.messages();
        for (int i = 0; i < messages.size(); ++i) {
            TransactionLogBuffer.LogEntry msg = messages.get(i);
            this.printMessage(logTime, txnId, msg);
        }
    }

    private void printMessage(LogTime logTime, String txnId, TransactionLogBuffer.LogEntry logEntry) {
        String msg = logEntry.getMsg();
        int len = msg.length();
        if (len == 0) {
            return;
        }
        this.bytesWritten += 16L;
        this.bytesWritten += (long)len;
        if (txnId != null) {
            this.bytesWritten += 7L;
            this.bytesWritten += (long)txnId.length();
            this.out.append("txn[");
            this.out.append(txnId);
            this.out.append("]");
            this.out.append(", ");
        }
        this.out.append(logTime.getTimestamp(logEntry.getTimestamp()));
        this.out.append(", ");
        if (msg != null) {
            this.out.append(msg).append(" ");
        }
        this.out.append("\n");
    }

    protected void printThrowable(StringBuilder sb, Throwable e, boolean isCause) {
        if (e != null) {
            Throwable cause;
            if (isCause) {
                sb.append("Caused by: ");
            }
            sb.append(e.getClass().getName());
            sb.append(":");
            sb.append(e.getMessage()).append("\\r\\n");
            StackTraceElement[] ste = e.getStackTrace();
            int outputStackLines = ste.length;
            int notShownCount = 0;
            if (ste.length > 5) {
                outputStackLines = 5;
                notShownCount = ste.length - outputStackLines;
            }
            for (int i = 0; i < outputStackLines; ++i) {
                sb.append(atString);
                sb.append(ste[i].toString()).append("\\r\\n");
            }
            if (notShownCount > 0) {
                sb.append("        ... ");
                sb.append(notShownCount);
                sb.append(" more").append("\\r\\n");
            }
            if ((cause = e.getCause()) != null) {
                this.printThrowable(sb, cause, true);
            }
        }
    }

    private String newFileName(LogTime logTime) {
        return this.filepath + File.separator + this.logFileName + logTime.getYMD() + "-" + this.fileCounter + this.logFileSuffix;
    }

    protected void switchFile(LogTime logTime) {
        try {
            long currentFileLength = 0L;
            String newFilePath = null;
            do {
                File f;
                if (!(f = new File(newFilePath = this.newFileName(logTime))).exists()) {
                    currentFileLength = 0L;
                    continue;
                }
                if ((double)f.length() < (double)this.maxBytesPerFile * 0.8) {
                    currentFileLength = f.length();
                    continue;
                }
                ++this.fileCounter;
                newFilePath = null;
            } while (newFilePath == null);
            if (!newFilePath.equals(this.currentPath)) {
                PrintStream newOut = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, true)));
                this.close();
                this.bytesWritten = currentFileLength;
                this.currentPath = newFilePath;
                this.out = newOut;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Error switch log file", e);
        }
    }

    private void close() {
        if (this.out != null) {
            this.out.flush();
            this.out.close();
        }
    }

    protected String makeDirIfRequired(String dir) {
        File f = new File(dir);
        if (f.exists()) {
            if (!f.isDirectory()) {
                String msg = "Transaction logs directory is a file? " + dir;
                throw new PersistenceException(msg);
            }
        } else if (!f.mkdirs()) {
            String msg = "Failed to create transaction logs directory " + dir;
            logger.log(Level.SEVERE, msg);
        }
        return dir;
    }
}

