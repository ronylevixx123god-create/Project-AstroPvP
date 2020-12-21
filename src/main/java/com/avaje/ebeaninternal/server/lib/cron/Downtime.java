/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.cron;

import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lib.cron.CronManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Downtime
implements Runnable {
    private static final Logger logger = Logger.getLogger(Downtime.class.getName());
    private CronManager manager;

    public Downtime(CronManager manager) {
        this.manager = manager;
    }

    public void run() {
        String downtime = GlobalProperties.get("system.downtime.duration", null);
        if (downtime == null) {
            logger.info("system.downtime not set");
        } else {
            int downTimeSecs = Integer.parseInt(downtime);
            int offsetSecs = 2;
            long offsetTime = System.currentTimeMillis() + (long)(offsetSecs * 1000);
            long endTime = System.currentTimeMillis() + (long)(downTimeSecs * 1000);
            try {
                boolean isFinished = false;
                if (offsetSecs > 0) {
                    while (!isFinished) {
                        Thread.sleep(500L);
                        if (System.currentTimeMillis() < offsetTime) continue;
                        isFinished = true;
                    }
                }
                this.manager.setDowntime(true);
                isFinished = false;
                while (!isFinished) {
                    Thread.sleep(500L);
                    if (System.currentTimeMillis() < endTime) continue;
                    isFinished = true;
                }
            }
            catch (InterruptedException ex) {
                logger.log(Level.SEVERE, "", ex);
            }
            this.manager.setDowntime(false);
        }
    }

    public String toString() {
        return "System Downtime";
    }
}

