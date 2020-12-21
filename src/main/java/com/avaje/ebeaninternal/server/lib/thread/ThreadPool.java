/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.thread;

import com.avaje.ebeaninternal.server.lib.thread.PooledThread;
import com.avaje.ebeaninternal.server.lib.thread.Work;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ThreadPool {
    private static final Logger logger = Logger.getLogger(ThreadPool.class.getName());
    private long maxIdleTime;
    private String poolName;
    private int minSize;
    private boolean isDaemon;
    private boolean isStopping = false;
    private Integer threadPriority;
    private int uniqueThreadID;
    private Vector<PooledThread> freeList = new Vector();
    private Vector<PooledThread> busyList = new Vector();
    private Vector<Work> workOverflowQueue = new Vector();
    private int maxSize = 100;
    private boolean stopThePool;

    public ThreadPool(String poolName, boolean isDaemon, Integer threadPriority) {
        this.poolName = poolName;
        this.stopThePool = false;
        this.isDaemon = isDaemon;
        this.threadPriority = threadPriority;
    }

    public boolean isStopping() {
        return this.isStopping;
    }

    public String getName() {
        return this.poolName;
    }

    public void setMinSize(int minSize) {
        if (minSize > 0) {
            if (minSize > this.maxSize) {
                this.maxSize = minSize;
            }
            this.minSize = minSize;
            this.maintainPoolSize();
        }
    }

    public int getMinSize() {
        return this.minSize;
    }

    public void setMaxSize(int maxSize) {
        if (maxSize > 0) {
            if (this.minSize > maxSize) {
                this.minSize = maxSize;
            }
            this.maxSize = maxSize;
            this.maintainPoolSize();
        }
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public int size() {
        return this.busyList.size() + this.freeList.size();
    }

    public int getBusyCount() {
        return this.busyList.size();
    }

    public boolean assign(Runnable work, boolean addToQueueIfFull) {
        if (this.stopThePool) {
            throw new RuntimeException("Pool is stopping... no more work please.");
        }
        Work runWork = new Work(work);
        PooledThread thread = this.getNextAvailableThread();
        if (thread != null) {
            this.busyList.add(thread);
            thread.assignWork(runWork);
            return true;
        }
        if (addToQueueIfFull) {
            runWork.setEnterQueueTime(System.currentTimeMillis());
            this.workOverflowQueue.add(runWork);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void removeThread(PooledThread thread) {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            this.busyList.remove(thread);
            this.freeList.remove(thread);
            this.freeList.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void returnThread(PooledThread thread) {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            this.busyList.remove(thread);
            if (!this.workOverflowQueue.isEmpty()) {
                Work queuedWork = this.workOverflowQueue.remove(0);
                queuedWork.setExitQueueTime(System.currentTimeMillis());
                this.busyList.add(thread);
                thread.assignWork(queuedWork);
            } else {
                this.freeList.add(thread);
                this.freeList.notify();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PooledThread getNextAvailableThread() {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            if (!this.freeList.isEmpty()) {
                return this.freeList.remove(0);
            }
            if (this.size() < this.maxSize) {
                return this.growPool(true);
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Iterator<PooledThread> getBusyThreads() {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            return this.busyList.iterator();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void shutdown() {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            String msg;
            this.isStopping = true;
            int size = this.size();
            if (size > 0) {
                msg = null;
                msg = "ThreadPool [" + this.poolName + "] Shutting down; threadCount[" + this.size() + "] busyCount[" + this.getBusyCount() + "]";
                logger.info(msg);
            }
            this.stopThePool = true;
            while (!this.freeList.isEmpty()) {
                PooledThread thread = this.freeList.remove(0);
                thread.stop();
            }
            try {
                while (this.getBusyCount() > 0) {
                    msg = "ThreadPool [" + this.poolName + "] has [" + this.getBusyCount() + "] busy threads, waiting for those to finish.";
                    logger.info(msg);
                    Iterator<PooledThread> busyThreads = this.getBusyThreads();
                    while (busyThreads.hasNext()) {
                        PooledThread busyThread = busyThreads.next();
                        String threadName = busyThread.getName();
                        Work work = busyThread.getWork();
                        String busymsg = "Busy thread [" + threadName + "] work[" + work + "]";
                        logger.info(busymsg);
                    }
                    this.freeList.wait();
                    PooledThread thread = this.freeList.remove(0);
                    if (thread == null) continue;
                    thread.stop();
                }
            }
            catch (InterruptedException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void maintainPoolSize() {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            int numToAdd;
            if (this.isStopping) {
                return;
            }
            int numToStop = this.size() - this.minSize;
            if (numToStop > 0) {
                long usedAfter = System.currentTimeMillis() - this.maxIdleTime;
                ArrayList<PooledThread> stopList = new ArrayList<PooledThread>();
                Iterator<PooledThread> it = this.freeList.iterator();
                while (it.hasNext() && numToStop > 0) {
                    PooledThread thread = it.next();
                    if (thread.getLastUsedTime() >= usedAfter) continue;
                    stopList.add(thread);
                    --numToStop;
                }
                for (PooledThread thread : stopList) {
                    thread.stop();
                }
            }
            if ((numToAdd = this.minSize - this.size()) > 0) {
                for (int i = 0; i < numToAdd; ++i) {
                    this.growPool(false);
                }
            }
        }
    }

    public PooledThread interrupt(String threadName) {
        PooledThread thread = this.getBusyThread(threadName);
        if (thread != null) {
            thread.interrupt();
            return thread;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PooledThread getBusyThread(String threadName) {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            Iterator<PooledThread> it = this.getBusyThreads();
            while (it.hasNext()) {
                PooledThread pt = it.next();
                if (!pt.getName().equals(threadName)) continue;
                return pt;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PooledThread growPool(boolean andReturn) {
        Vector<PooledThread> vector = this.freeList;
        synchronized (vector) {
            String threadName = this.poolName + "." + this.uniqueThreadID++;
            PooledThread bgw = new PooledThread(this, threadName, this.isDaemon, this.threadPriority);
            bgw.start();
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("ThreadPool grow created [" + threadName + "] size[" + this.size() + "]");
            }
            if (andReturn) {
                return bgw;
            }
            this.freeList.add(bgw);
            return null;
        }
    }

    public long getMaxIdleTime() {
        return this.maxIdleTime;
    }

    public void setMaxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }
}

