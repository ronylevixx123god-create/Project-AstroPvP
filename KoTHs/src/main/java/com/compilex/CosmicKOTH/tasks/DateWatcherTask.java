/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicKOTH.tasks;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.tasks.LauncherTask;
import com.compilex.CosmicKOTH.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class DateWatcherTask
        implements Runnable {
    boolean ranThisSession = false;
    public static List<Long> events;
    private Calendar mondayEvent;
    private Calendar tuesdayEvent;
    private Calendar thursdayEvent;
    private Calendar wednsdayEvent;
    private Calendar fridayEvent;
    private Calendar saturdayEvent;
    private Calendar sundayEvent;

    public DateWatcherTask() {
        events = new ArrayList<Long>();
        LocalDate localDate = LocalDate.now();
        ArrayList<LocalDate> days = Lists.newArrayList();
        for (int i = 1; i <= 7; ++i) {
            LocalDate day = localDate.with(TemporalAdjusters.next(DayOfWeek.of(i)));
            days.add(day);
            events.add(day.atStartOfDay(ZoneId.of("America/New_York")).toEpochSecond());
            events.add(day.atStartOfDay(ZoneId.of("America/New_York")).toEpochSecond() + TimeUnit.HOURS.toSeconds(12L));
        }
        LocalDate day = localDate.with(localDate.getDayOfWeek());
        if (!days.contains(day)) {
            events.add(day.atStartOfDay(ZoneId.of("America/New_York")).toEpochSecond());
            events.add(day.atStartOfDay(ZoneId.of("America/New_York")).toEpochSecond() + TimeUnit.HOURS.toSeconds(12L));
        }
    }

    @Override
    public void run() {
        if (this.ranThisSession) {
            return;
        }
        if (LauncherTask.countdown != -1) {
            return;
        }
        Calendar c = Calendar.getInstance();
        for (Long event : events) {
            LocalDate date = Instant.ofEpochSecond(event).atZone(ZoneId.of("America/New_York")).toLocalDate();
            if (date.getDayOfWeek() != LocalDate.now().getDayOfWeek() || event >= System.currentTimeMillis() / 1000L) continue;
            Bukkit.getScheduler().runTaskTimer((Plugin)CosmicKOTHPlugin.get_(), new LauncherTask(901), 20L, 20L);
            this.ranThisSession = true;
        }
    }

    public static long getMilliUntilNextKOTH() {
        long nearestEvent = System.currentTimeMillis();
        for (Long event : events) {
            long msUntilEvent = event - System.currentTimeMillis() / 1000L;
            if (msUntilEvent >= nearestEvent || msUntilEvent <= 0L) continue;
            nearestEvent = msUntilEvent;
        }
        return nearestEvent;
    }

    public static String getTimeUntilNextKOTH() {
        long l = DateWatcherTask.getMilliUntilNextKOTH();
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
