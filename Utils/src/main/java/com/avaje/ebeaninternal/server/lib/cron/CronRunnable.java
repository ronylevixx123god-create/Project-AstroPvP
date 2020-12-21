/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.cron;

import com.avaje.ebeaninternal.server.lib.cron.CronSchedule;
import java.util.Calendar;

public class CronRunnable {
    boolean isEnabled = true;
    CronSchedule schedule;
    Runnable runnable;

    public CronRunnable(String schedule, Runnable runnable) {
        this(new CronSchedule(schedule), runnable);
    }

    public CronRunnable(CronSchedule schedule, Runnable runnable) {
        this.schedule = schedule;
        this.runnable = runnable;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof CronRunnable) {
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

    public int hashCode() {
        int hc = CronRunnable.class.getName().hashCode();
        hc += 31 * hc + this.schedule.hashCode();
        hc += 31 * hc + this.runnable.hashCode();
        return hc;
    }

    public boolean isScheduledToRunNow(Calendar thisMinute) {
        return this.isEnabled && this.schedule.isScheduledToRunNow(thisMinute);
    }

    public void setSchedule(String scheduleLine) {
        this.schedule.setSchedule(scheduleLine);
    }

    public String getSchedule() {
        return this.schedule.getSchedule();
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String toString() {
        return "CronRunnable: isEnabled[" + this.isEnabled + "] sch[" + this.schedule.getSchedule() + "] [" + this.runnable.toString() + "]";
    }
}

