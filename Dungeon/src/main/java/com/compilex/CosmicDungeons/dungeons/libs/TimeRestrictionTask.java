/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  org.bukkit.ChatColor
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicDungeons.dungeons.libs;

import com.compilex.CosmicDungeons.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.utils.TitleUtil;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimeRestrictionTask
extends BukkitRunnable {
    public Dungeon dungeon;
    private long expireTime;
    private long initialDifference;
    private String objectiveName;
    private CopyOnWriteArrayList<Long> notifcationThresholds;

    public long getExpireTime() {
        return this.expireTime;
    }

    public long getInitialDifference() {
        return this.initialDifference;
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public TimeRestrictionTask(Dungeon d, long expireTime, String objectiveName) {
        this.dungeon = d;
        this.expireTime = expireTime;
        this.setObjectiveName(objectiveName);
        this.refreshNotificaitonThresholds();
    }

    private void refreshNotificaitonThresholds() {
        this.initialDifference = this.expireTime - System.currentTimeMillis();
        this.notifcationThresholds = new CopyOnWriteArrayList<Long>(Arrays.asList(5000L, 10000L, 30000L, 60000L, 120000L, 300000L, 600000L, 900000L));
    }

    public void setExpireTime(long l) {
        this.expireTime = l;
        this.refreshNotificaitonThresholds();
    }

    public void setObjectiveName(String newObjective) {
        this.objectiveName = newObjective;

        TitleUtil.sendTitleList(dungeon.getPlayers(), CC.Yellow + "Time Limit: " + ChatColor.GRAY + TimeUtils.formatDifference(this.expireTime - System.currentTimeMillis()));
    }

    private String getObjectiveString(String newObjective) {
        String toUse = CC.White + newObjective;
        if (toUse.length() <= 19 || !this.dungeon.isDestroyedOutpost()) {
            toUse = "Objective: " + toUse;
        }
        return toUse;
    }

    public void run() {
        if (this.dungeon.getDungeonStatus() == DungeonState.EMPTY) {
            return;
        }
        if (System.currentTimeMillis() >= this.expireTime) {
            if (this.dungeon.getDungeonStatus() != DungeonState.COMPLETE) {
                this.dungeon.endDungeon(DungeonEndReason.TIME_EXPIRE);
                return;
            }
            this.dungeon.endDungeon(DungeonEndReason.DUNGEON_CLEARED);
        }
        if (this.dungeon.getDungeonStatus() == DungeonState.COMPLETE) {
            return;
        }
        long millisLeft = this.expireTime - System.currentTimeMillis();
        for (long l : this.notifcationThresholds) {
            if (millisLeft >= l || this.initialDifference <= l + 1000L) continue;
            this.dungeon.broadcastMessage("You have " + ChatColor.UNDERLINE + TimeUtils.formatDifference(millisLeft) + ChatColor.WHITE + " left to complete objective:");
            this.dungeon.broadcastMessage(ChatColor.YELLOW + this.objectiveName + ChatColor.WHITE + " or you will fail the dungeon!");
            TitleUtil.sendTitleList(this.dungeon.getPlayers(), CC.Yellow + "Time Left: " + ChatColor.GRAY + TimeUtils.formatDifference(millisLeft));
            this.notifcationThresholds.remove(l);
        }
    }
}

