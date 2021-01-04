/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  org.bukkit.Bukkit
 */
package com.massivecraft.factions.quests;

import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.massivecraft.factions.P;
import com.massivecraft.factions.quests.managers.FactionQuestManager;
import org.bukkit.Bukkit;

public class QuestUpdateTask
implements Runnable {
    public static long RESET_COOLDOWN = TimeUnit.DAYS.toMillis(7L);
    public static int RESET_DAY = 2;
    public static int RESET_HOUR = 15;

    @Override
    public void run() {
        if (P.getP().isFpointsEnabled() && RESET_DAY != 6) {
            RESET_DAY = 6;
        }
        FactionQuestManager manager = P.getP().getFactionQuestManager();
        Calendar cal = manager.createCalendar();
        long timeSinceLastReset = System.currentTimeMillis() - manager.getLastWeekendResetTime();
        boolean needsToReset = false;
        if (cal.get(7) == RESET_DAY && cal.get(11) == RESET_HOUR && timeSinceLastReset >= TimeUnit.HOURS.toMillis(3L)) {
            Bukkit.getLogger().info("[CosmicFactionQuests] Seems to be resetHour: " + RESET_HOUR + " and its been " + TimeUtils.formatSeconds((long)(timeSinceLastReset / 1000L)) + " since the last reset!");
            needsToReset = true;
        }
        int dayOfWeek = cal.get(7);
        for (Faction fac : Factions.i.get()) {
            Long creationTime;
            if (!fac.isNormal() || fac.getFPlayers().size() <= 0 || (creationTime = manager.getFactionCreationTime().get(fac.getId())) != null && System.currentTimeMillis() - creationTime < TimeUnit.HOURS.toMillis(12L)) continue;
            WeeklyProgress progress = manager.getWeeklyFactionProgress().computeIfAbsent(fac.getId(), s -> new WeeklyProgress());
            if (needsToReset) {
                progress.resetWeeklyQuests(fac, cal);
                continue;
            }
            progress.checkTime(fac, cal, dayOfWeek);
        }
        if (needsToReset) {
            for (FactionQuests quest : FactionQuests.values()) {
                quest.getQuest().getQuestDataMap().clear();
            }
            manager.setLastWeekendResetTime(System.currentTimeMillis());
        }
    }
}

