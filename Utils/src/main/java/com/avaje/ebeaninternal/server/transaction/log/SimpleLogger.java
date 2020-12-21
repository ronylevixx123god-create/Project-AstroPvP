/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction.log;

import com.avaje.ebeaninternal.server.transaction.log.LogTime;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

public class SimpleLogger {
    private static final Logger logger = Logger.getLogger(SimpleLogger.class.getName());
    private static final String atString = "        at ";
    private PrintStream out;
    private boolean doAppend = true;
    private boolean open = true;
    private String currentPath;
    private final String filepath;
    private final boolean useFileSwitching;
    private final int maxStackTraceLines = 5;
    private final String deliminator;
    private final Object fileMonitor = new Object();
    private final String logFileName;
    private final String logFileSuffix;
    private final String newLineChar = "\\r\\n";
    private final boolean csv;

    public SimpleLogger(String dir, String logFileName, boolean useFileSwitching, String suffix) {
        this.logFileName = logFileName;
        this.useFileSwitching = useFileSwitching;
        this.logFileSuffix = "." + suffix;
        this.csv = "csv".equalsIgnoreCase(suffix);
        this.deliminator = this.csv ? "," : ", ";
        try {
            this.filepath = this.makeDirIfRequired(dir);
            this.switchFile(LogTime.nextDay());
        }
        catch (Exception e) {
            System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public SimpleLogger(String dir, String logFileName, boolean useFileSwitching) {
        this(dir, logFileName, useFileSwitching, "log");
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void close() {
        if (this.open) {
            this.out.flush();
            this.out.close();
            this.open = false;
        }
    }

    public void log(String msg) {
        this.log(null, msg, null);
    }

    public void log(String msg, Throwable e) {
        this.log(null, msg, e);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void log(String transId, String msg, Throwable e) {
        LogTime logTime = LogTime.get();
        if (logTime.isNextDay()) {
            logTime = LogTime.nextDay();
            try {
                this.switchFile(logTime);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        int roughSize = 40;
        if (msg != null) {
            roughSize += msg.length();
        }
        if (e != null) {
            roughSize += 200;
        }
        StringBuilder line = new StringBuilder(roughSize);
        if (transId != null) {
            line.append("trans[").append(transId).append("]").append(this.deliminator);
        }
        if (this.csv) {
            line.append("\"'");
        }
        line.append(logTime.getNow());
        if (this.csv) {
            line.append("'\"");
        }
        line.append(this.deliminator);
        if (msg != null) {
            line.append(msg).append(" ");
        }
        this.printThrowable(line, e, false);
        String lineString = line.toString();
        Object object = this.fileMonitor;
        synchronized (object) {
            this.out.println(lineString);
            this.out.flush();
        }
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void switchFile(LogTime logTime) throws Exception {
        String newFilePath = this.filepath + File.separator + this.logFileName;
        newFilePath = this.useFileSwitching ? newFilePath + logTime.getYMD() + this.logFileSuffix : newFilePath + this.logFileSuffix;
        Object object = this.fileMonitor;
        synchronized (object) {
            if (!newFilePath.equals(this.currentPath)) {
                this.currentPath = newFilePath;
                this.out = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, this.doAppend)));
            }
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

