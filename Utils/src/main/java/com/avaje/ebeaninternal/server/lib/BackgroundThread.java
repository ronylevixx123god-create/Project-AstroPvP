/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib;

import com.avaje.ebeaninternal.server.lib.BackgroundRunnable;
import com.avaje.ebeaninternal.server.lib.ShutdownManager;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BackgroundThread {
    private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
    private Vector<BackgroundRunnable> list = new Vector();
    private final Object monitor = new Object();
    private final Thread thread;
    private long sleepTime = 1000L;
    private long count;
    private long exeTime;
    private boolean stopped;
    private Object threadMonitor = new Object();

    private BackgroundThread() {
        this.thread = new Thread((Runnable)new Runner(), "EbeanBackgroundThread");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    public static void add(int freqInSecs, Runnable runnable) {
        BackgroundThread.add(new BackgroundRunnable(runnable, freqInSecs));
    }

    public static void add(BackgroundRunnable backgroundRunnable) {
        Single.me.addTask(backgroundRunnable);
    }

    public static void shutdown() {
        Single.me.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Iterator<BackgroundRunnable> runnables() {
        Object object = Single.me.monitor;
        synchronized (object) {
            return Single.me.list.iterator();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addTask(BackgroundRunnable backgroundRunnable) {
        Object object = this.monitor;
        synchronized (object) {
            this.list.add(backgroundRunnable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void stop() {
        this.stopped = true;
        Object object = this.threadMonitor;
        synchronized (object) {
            try {
                this.threadMonitor.wait(10000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        Object object = this.monitor;
        synchronized (object) {
            StringBuffer sb = new StringBuffer();
            Iterator<BackgroundRunnable> it = BackgroundThread.runnables();
            while (it.hasNext()) {
                BackgroundRunnable bgr = it.next();
                sb.append(bgr);
            }
            return sb.toString();
        }
    }

    private class Runner
    implements Runnable {
        private Runner() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void run() {
            if (ShutdownManager.isStopping()) {
                return;
            }
            while (!BackgroundThread.this.stopped) {
                try {
                    long actualSleep = BackgroundThread.this.sleepTime - BackgroundThread.this.exeTime;
                    if (actualSleep < 0L) {
                        actualSleep = BackgroundThread.this.sleepTime;
                    }
                    Thread.sleep(actualSleep);
                    Object object = BackgroundThread.this.monitor;
                    synchronized (object) {
                        this.runJobs();
                    }
                }
                catch (InterruptedException e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
            Object object = BackgroundThread.this.threadMonitor;
            synchronized (object) {
                BackgroundThread.this.threadMonitor.notifyAll();
            }
        }

        private void runJobs() {
            long startTime = System.currentTimeMillis();
            for (BackgroundRunnable bgr : BackgroundThread.this.list) {
                if (!bgr.isActive()) continue;
                int freqInSecs = bgr.getFreqInSecs();
                if (BackgroundThread.this.count % (long)freqInSecs != 0L) continue;
                Runnable runable = bgr.getRunnable();
                if (!bgr.runNow(startTime)) continue;
                bgr.runStart();
                if (logger.isLoggable(Level.FINER)) {
                    String msg = BackgroundThread.this.count + " BGRunnable running [" + runable.getClass().getName() + "]";
                    logger.finer(msg);
                }
                runable.run();
                bgr.runEnd();
            }
            BackgroundThread.this.exeTime = System.currentTimeMillis() - startTime;
            BackgroundThread.this.count++;
            if (BackgroundThread.this.count == 86400L) {
                BackgroundThread.this.count = 0L;
            }
        }
    }

    private static class Single {
        private static BackgroundThread me = new BackgroundThread();

        private Single() {
        }
    }
}

