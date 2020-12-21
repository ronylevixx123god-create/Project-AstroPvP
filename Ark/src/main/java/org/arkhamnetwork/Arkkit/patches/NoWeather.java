/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.NoWeatherListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class NoWeather
extends ArkPlugin {
    public NoWeather(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        for (World w : Bukkit.getWorlds()) {
            w.setStorm(false);
            w.setThundering(false);
            w.setWeatherDuration(Integer.MAX_VALUE);
        }
        this.registerListener(new NoWeatherListener());
    }

    @Override
    public void disable() {
        if (!Arkkit.get().shutting_down) {
            for (World w : Bukkit.getWorlds()) {
                w.setWeatherDuration(0);
            }
        }
    }
}

