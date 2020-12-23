/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class TNTLimiter
        extends ArkPlugin {
    private final HashMap<String, Integer> default_max_tnt = new HashMap();

    public TNTLimiter(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        int max_tnt_ticks = this.getBukkitPlugin().getConfig().getInt("settings.tnt_limiter.max_tnt_per_tick");
        for (World w : Bukkit.getWorlds()) {
            this.default_max_tnt.put(w.getName(), ((CraftWorld) w).getHandle().spigotConfig.maxTntTicksPerTick);
            ((CraftWorld) w).getHandle().spigotConfig.maxTntTicksPerTick = max_tnt_ticks;
            Bukkit.getLogger().info("(TNTLimiter) Set maxTNTPerTick on " + w.getName() + " => " + max_tnt_ticks);
        }
    }

    @Override
    public void disable() {
        for (String s : this.default_max_tnt.keySet()) {
            if (Bukkit.getWorld(s) == null) continue;
            World w = Bukkit.getWorld(s);
            ((CraftWorld) w).getHandle().spigotConfig.maxTntTicksPerTick = this.default_max_tnt.get(s);
            Bukkit.getLogger().info("(TNTLimiter) Set maxTNTPerTick on " + w.getName() + " => " + this.default_max_tnt.get(s));
        }
        this.default_max_tnt.clear();
    }
}

