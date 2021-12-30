/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicKOTH.tasks;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.utils.TimeUtils;
import java.util.Calendar;
import org.bukkit.Bukkit;

public class DateWatcherTask implements Runnable {
    boolean ranThisSession = false;

    public void run() {
        if (LauncherTask.countdown == -1) {
            Calendar c = Calendar.getInstance();
            if (c.get(11) == 0 || c.get(11) == 20 || c.get(11) == 14) {
                Bukkit.getScheduler().runTaskTimer(CosmicKOTHPlugin.get_(), new LauncherTask(901), 20L, 20L);
                this.ranThisSession = true;
            }

        }
    }

    public static long getMilliUntilNextKOTH() {
        long nearestEvent = System.currentTimeMillis();
        Integer[] var5;
        int var4 = (var5 = new Integer[]{0, 20, 14}).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            int x = var5[var3];
            Calendar dayInMonth = nextDayOfWeek(Calendar.getInstance().get(7));
            dayInMonth.set(11, x);
            dayInMonth.set(12, 15);
            dayInMonth.set(13, 0);
            long msUntilEvent = dayInMonth.getTimeInMillis() - System.currentTimeMillis();
            if (msUntilEvent < nearestEvent && msUntilEvent > 0L) {
                nearestEvent = msUntilEvent;
            }
        }

        return nearestEvent;
    }

    public static String getTimeUntilNextKOTH() {
        long l = getMilliUntilNextKOTH() / 1000L;
        return TimeUtils.getSecondsAsString(l);
    }

    private static Calendar nextDayOfWeek(int dow) {
        Calendar date = Calendar.getInstance();
        int diff = dow - date.get(7);
        if (diff <= 0 && diff != 0) {
            diff += 7;
        }

        date.add(5, diff);
        return date;
    }
}
