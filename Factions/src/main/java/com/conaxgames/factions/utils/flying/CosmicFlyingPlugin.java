/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.flying;

import com.conaxgames.factions.utils.libs.AsyncPlayerMap;
import com.massivecraft.factions.P;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CosmicFlyingPlugin {
    private static CosmicFlyingPlugin _;
    private AsyncPlayerMap asyncPlayerMap;

    public void onEnable() {
        _ = this;
        if (P.getP().getConfig().getBoolean("flying-mechanics")) {
            this.asyncPlayerMap = new AsyncPlayerMap(P.getP());
            this.registerListener();
            this.registerTasks();
        }
    }

    public void onDisable() {
    }

    private void registerListener() {
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new CosmicFlyingListener(), P.getP());
    }

    private void registerTasks() {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(P.getP(), (Runnable)new CosmicFlyingParticleTask(), 40L, 2L);
        Bukkit.getServer().getScheduler().runTaskTimer(P.getP(), (Runnable)new CosmicFlyingTask(), 30L, 30L);
    }

    public static CosmicFlyingPlugin get_() {
        return _;
    }

    public AsyncPlayerMap getAsyncPlayerMap() {
        return this.asyncPlayerMap;
    }
}

