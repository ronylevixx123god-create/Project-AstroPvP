/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.cron;

import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lib.ShutdownManager;
import com.avaje.ebeaninternal.server.lib.cron.CronRunnable;
import com.avaje.ebeaninternal.server.lib.cron.Downtime;
import com.avaje.ebeaninternal.server.lib.cron.HelloWorld;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class CronManager {
    private static final Logger logger = Logger.getLogger(CronManager.class.getName());
    private boolean running = true;
    private ThreadPool threadPool;
    private Vector<CronRunnable> runList = new Vector();
    private Thread backgroundThread;
    private boolean isDowntime = false;
    private static final long SMALL_DELAY = 10L;

    private CronManager() {
        this.threadPool = ThreadPoolManager.getThreadPool("CronManager");
        this.backgroundThread = new Thread((Runnable)new Runner(), "CronManager Daemon");
        this.backgroundThread.setDaemon(true);
        this.backgroundThread.start();
    }

    private void init() {
        CronRunnable sr = new CronRunnable("* * * * *", (Runnable)new HelloWorld());
        sr.setEnabled(false);
        CronManager.add(sr);
        CronRunnable dt = new CronRunnable("25 23 * * *", (Runnable)new Downtime(this));
        dt.setEnabled(false);
        String downtimeSchedule = GlobalProperties.get("system.downtime.schedule", null);
        if (downtimeSchedule != null) {
            dt.setSchedule(downtimeSchedule);
            dt.setEnabled(true);
        }
        CronManager.add(dt);
    }

    public static boolean isDowntime() {
        return CronManager.getInstance().isDowntime;
    }

    protected void setDowntime(boolean isDowntime) {
        this.isDowntime = isDowntime;
        if (isDowntime) {
            String duration = GlobalProperties.get("system.downtime.duration", null);
            logger.warning("System downtime has started for [" + duration + "] seconds");
        } else {
            logger.warning("System downtime has finished.");
        }
    }

    public static void setRunning(boolean running) {
        CronManagerHolder.me.running = running;
    }

    private void runScheduledJobs() {
        if (!this.running) {
            return;
        }
        Date nowDate = new Date((System.currentTimeMillis() + 5000L) / 60000L * 60000L);
        GregorianCalendar thisMinute = new GregorianCalendar();
        thisMinute.setTime(nowDate);
        Enumeration<CronRunnable> en = this.runList.elements();
        while (en.hasMoreElements()) {
            CronRunnable sr = en.nextElement();
            if (!sr.isScheduledToRunNow(thisMinute)) continue;
            this.threadPool.assign(sr.getRunnable(), true);
        }
    }

    private static CronManager getInstance() {
        return CronManagerHolder.me;
    }

    public static void add(String schedule, Runnable runnable) {
        CronRunnable sr = new CronRunnable(schedule, runnable);
        CronManager.add(sr);
    }

    public static void add(CronRunnable runnable) {
        CronManager.getInstance().runList.add(runnable);
    }

    public static Iterator<CronRunnable> iterator() {
        return CronManager.getInstance().runList.iterator();
    }

    private class Runner
    implements Runnable {
        private Runner() {
        }

        public void run() {
            CronManager.this.init();
            while (true) {
                try {
                    while (true) {
                        boolean stopping;
                        long additionalDelay;
                        long now;
                        long nextMinute;
                        long nextSleepTime;
                        if ((nextSleepTime = (nextMinute = System.currentTimeMillis() / 60000L * 60000L + 60000L) - (now = System.currentTimeMillis()) + 10L) > 0L) {
                            Thread.sleep(nextSleepTime);
                        }
                        if ((additionalDelay = nextMinute - System.currentTimeMillis()) > 0L) {
                            Thread.sleep(additionalDelay + 20L);
                        }
                        if (!(stopping = ShutdownManager.isStopping())) {
                            CronManager.this.runScheduledJobs();
                        }
                        Thread.sleep(5000L);
                    }
                }
                catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "", e);
                    continue;
                }
                break;
            }
        }
    }

    private static class CronManagerHolder {
        private static CronManager me = new CronManager();

        private CronManagerHolder() {
        }
    }
}

