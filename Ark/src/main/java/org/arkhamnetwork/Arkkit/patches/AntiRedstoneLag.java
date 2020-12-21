/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.concurrent.ConcurrentHashMap;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiRedstoneLagListener;
import org.arkhamnetwork.Arkkit.patches.AntiRedstoneLagTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AntiRedstoneLag
extends ArkPlugin {
    protected static int max_redstone_per_iteration = 200;

    public AntiRedstoneLag(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (Arkkit.get().getConfig().contains("settings.redstone_event_threshold")) {
            max_redstone_per_iteration = Arkkit.get().getConfig().getInt("settings.redstone_event_threshold");
        } else {
            Arkkit.get().getConfig().set("settings.redstone_event_threshold", (Object)250);
            max_redstone_per_iteration = 250;
        }
        Bukkit.getLogger().info("Setting MAX_REDSTONE_PER_ITERATION = " + (max_redstone_per_iteration *= 2) + " ticks/10s per chunk.");
        AntiRedstoneLagTask.laggingChunks = new ConcurrentHashMap();
        this.registerListener(new AntiRedstoneLagListener());
        this.registerTask(new AntiRedstoneLagTask().runTaskTimer(this.getBukkitPlugin(), 20L, 600L), AntiRedstoneLagListener.class);
    }

    @Override
    public void disable() {
        AntiRedstoneLagListener.redstone_tracker.clear();
    }
}

