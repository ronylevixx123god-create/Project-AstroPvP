/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.thread;

import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import com.avaje.ebeaninternal.server.lib.thread.Work;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PooledThread
implements Runnable {
    private static final Logger logger = Logger.getLogger(PooledThread.class.getName());
    private boolean wasInterrupted = false;
    private long lastUsedTime;
    private Work work = null;
    private boolean isStopping = false;
    private boolean isStopped = false;
    private Thread thread = null;
    private ThreadPool threadPool;
    private String name = null;
    private Object threadMonitor = new Object();
    private Object workMonitor = new Object();
    private int totalWorkCount = 0;
    private long totalWorkExecutionTime = 0L;

    protected PooledThread(ThreadPool threadPool, String name, boolean isDaemon, Integer threadPriority) {
        this.name = name;
        this.threadPool = threadPool;
        this.lastUsedTime = System.currentTimeMillis();
        this.thread = new Thread((Runnable)this, name);
        this.thread.setDaemon(isDaemon);
        if (threadPriority != null) {
            this.thread.setPriority(threadPriority);
        }
    }

    protected void start() {
        this.thread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean assignWork(Work work) {
        Object object = this.workMonitor;
        synchronized (object) {
            this.work = work;
            this.workMonitor.notifyAll();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        Object object = this.workMonitor;
        synchronized (object) {
            while (!this.isStopping) {
                try {
                    if (this.work == null) {
                        this.workMonitor.wait();
                    }
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                this.doTheWork();
            }
        }
        object = this.threadMonitor;
        synchronized (object) {
            this.threadMonitor.notifyAll();
        }
        this.isStopped = true;
    }

    private void doTheWork() {
        long startTime;
        block6: {
            if (this.isStopping) {
                return;
            }
            startTime = System.currentTimeMillis();
            if (this.work != null) {
                try {
                    this.work.setStartTime(startTime);
                    this.work.getRunnable().run();
                }
                catch (Throwable ex) {
                    logger.log(Level.SEVERE, null, ex);
                    if (!this.wasInterrupted) break block6;
                    this.isStopping = true;
                    this.threadPool.removeThread(this);
                    logger.info("PooledThread [" + this.name + "] removed due to interrupt");
                    try {
                        this.thread.interrupt();
                    }
                    catch (Exception e) {
                        String msg = "Error interrupting PooledThead[" + this.name + "]";
                        logger.log(Level.SEVERE, msg, e);
                    }
                    return;
                }
            }
        }
        this.lastUsedTime = System.currentTimeMillis();
        ++this.totalWorkCount;
        this.totalWorkExecutionTime = this.totalWorkExecutionTime + this.lastUsedTime - startTime;
        this.work = null;
        this.threadPool.returnThread(this);
    }

    public void interrupt() {
        this.wasInterrupted = true;
        try {
            this.thread.interrupt();
        }
        catch (SecurityException ex) {
            this.wasInterrupted = false;
            throw ex;
        }
    }

    public boolean isStopped() {
        return this.isStopped;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void stop() {
        this.isStopping = true;
        Object object = this.threadMonitor;
        synchronized (object) {
            this.assignWork(null);
            try {
                this.threadMonitor.wait(10000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        this.thread = null;
        this.threadPool.removeThread(this);
    }

    public String getName() {
        return this.name;
    }

    public Work getWork() {
        return this.work;
    }

    public int getTotalWorkCount() {
        return this.totalWorkCount;
    }

    public long getTotalWorkExecutionTime() {
        return this.totalWorkExecutionTime;
    }

    public long getLastUsedTime() {
        return this.lastUsedTime;
    }
}

