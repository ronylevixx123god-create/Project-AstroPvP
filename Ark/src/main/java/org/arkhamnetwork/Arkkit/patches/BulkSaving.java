/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.plugin.Plugin
 *  org.spigotmc.AsyncCatcher
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.HashMap;
import org.arkhamnetwork.Arkkit.patches.BulkSavingListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.plugin.Plugin;
import org.spigotmc.AsyncCatcher;

public class BulkSaving
extends ArkPlugin {
    protected static HashMap<World, Integer> default_save_periods = new HashMap();

    public BulkSaving(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        for (World w : Bukkit.getWorlds()) {
            default_save_periods.put(w, ((CraftWorld)w).getHandle().getServer().getServer().autosavePeriod);
            ((CraftWorld)w).getHandle().getServer().getServer().autosavePeriod = 0;
            w.setAutoSave(false);
        }
        this.registerListener(new BulkSavingListener());
    }

    @Override
    public void disable() {
        AsyncCatcher.enabled = false;
        for (World w : new ArrayList(Bukkit.getWorlds())) {
            if (default_save_periods.containsKey((Object)w)) {
                ((CraftWorld)w).getHandle().getServer().getServer().autosavePeriod = default_save_periods.get((Object)w);
                w.setAutoSave(true);
            }
            for (Chunk c : (Chunk[])w.getLoadedChunks().clone()) {
                if (w.getPlayers().size() > 0) {
                    c.unload(true, true);
                    continue;
                }
                c.unload(true, false);
            }
        }
    }
}

