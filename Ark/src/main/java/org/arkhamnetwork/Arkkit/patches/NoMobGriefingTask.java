/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

final class NoMobGriefingTask
extends BukkitRunnable {
    NoMobGriefingTask() {
    }

    public void run() {
        for (World world : Bukkit.getWorlds()) {
            if (world.getGameRuleValue("mobGriefing").equals("false")) continue;
            Bukkit.getLogger().info("Setting mobGriefing = false on " + world.getName());
            world.setGameRuleValue("mobGriefing", "false");
        }
    }
}

