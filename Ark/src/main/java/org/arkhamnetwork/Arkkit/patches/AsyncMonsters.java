/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class AsyncMonsters
        extends ArkPlugin {
    public static boolean stackCreepers = true;
    public static int MAX_KILL_STACK = 100;
    public static double LAVA_KILL_PERCENT = 0.2;
    public Set<String> factionsWithEnemiesInThem = new HashSet<String>();
    private BukkitTask bukkitTask;

    public AsyncMonsters(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        MAX_KILL_STACK = Arkkit.get().getConfig().getInt("settings.asyncMonsters.maxKillStack", 100);
        LAVA_KILL_PERCENT = Arkkit.get().getConfig().getDouble("settings.asyncMonsters.lavaKillPercent", 0.2);
        this.registerListener(new AsyncMonstersListener(this));
        this.registerCommand(new AsyncMonstersCommand("asyncmonsters", "/<command>", "async monsters command"));
    }

    @Override
    public void disable() {
        this.unregisterCommands();
        this.unregisterListeners();
        if (this.bukkitTask != null && Bukkit.getScheduler().isCurrentlyRunning(this.bukkitTask.getTaskId())) {
            Bukkit.getScheduler().cancelTask(this.bukkitTask.getTaskId());
        }
    }
}

