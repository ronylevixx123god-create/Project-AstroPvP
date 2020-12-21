/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.thread;

import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lib.BackgroundThread;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ThreadPoolManager
implements Runnable {
    private static int debugLevel = 0;
    private boolean isShuttingDown = false;
    private ConcurrentHashMap<String, ThreadPool> threadPoolCache = new ConcurrentHashMap();
    private long defaultIdleTime;

    private ThreadPoolManager() {
        this.initialise();
    }

    private void initialise() {
        debugLevel = GlobalProperties.getInt("threadpool.debugLevel", 0);
        this.defaultIdleTime = 1000 * GlobalProperties.getInt("threadpool.idletime", 60);
        int freqIsSecs = GlobalProperties.getInt("threadpool.sleeptime", 30);
        BackgroundThread.add(freqIsSecs, this);
    }

    public static void setDebugLevel(int level) {
        debugLevel = level;
    }

    public static int getDebugLevel() {
        return debugLevel;
    }

    @Override
    public void run() {
        if (!this.isShuttingDown) {
            this.maintainPoolSize();
        }
    }

    public static ThreadPool getThreadPool(String poolName) {
        return Single.me.getPool(poolName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ThreadPool getPool(String poolName) {
        ThreadPoolManager threadPoolManager = this;
        synchronized (threadPoolManager) {
            ThreadPool threadPool = this.threadPoolCache.get(poolName);
            if (threadPool == null) {
                threadPool = this.createThreadPool(poolName);
                this.threadPoolCache.put(poolName, threadPool);
            }
            return threadPool;
        }
    }

    public static Iterator<ThreadPool> pools() {
        return Single.me.threadPoolCache.values().iterator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void maintainPoolSize() {
        if (this.isShuttingDown) {
            return;
        }
        ThreadPoolManager threadPoolManager = this;
        synchronized (threadPoolManager) {
            Iterator<ThreadPool> e = ThreadPoolManager.pools();
            while (e.hasNext()) {
                ThreadPool pool = e.next();
                pool.maintainPoolSize();
            }
        }
    }

    public static void shutdown() {
        Single.me.shutdownPools();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void shutdownPools() {
        ThreadPoolManager threadPoolManager = this;
        synchronized (threadPoolManager) {
            this.isShuttingDown = true;
            Iterator<ThreadPool> i = ThreadPoolManager.pools();
            while (i.hasNext()) {
                ThreadPool pool = i.next();
                pool.shutdown();
            }
        }
    }

    private ThreadPool createThreadPool(String poolName) {
        int min = GlobalProperties.getInt("threadpool." + poolName + ".min", 0);
        int max = GlobalProperties.getInt("threadpool." + poolName + ".max", 100);
        long idle = 1000 * GlobalProperties.getInt("threadpool." + poolName + ".idletime", -1);
        if (idle < 0L) {
            idle = this.defaultIdleTime;
        }
        boolean isDaemon = true;
        Integer priority = null;
        String threadPriority = GlobalProperties.get("threadpool." + poolName + ".priority", null);
        if (threadPriority != null) {
            priority = new Integer(threadPriority);
        }
        ThreadPool newPool = new ThreadPool(poolName, isDaemon, priority);
        newPool.setMaxSize(max);
        newPool.setMinSize(min);
        newPool.setMaxIdleTime(idle);
        return newPool;
    }

    private static final class Single {
        private static final ThreadPoolManager me = new ThreadPoolManager();

        private Single() {
        }
    }
}

