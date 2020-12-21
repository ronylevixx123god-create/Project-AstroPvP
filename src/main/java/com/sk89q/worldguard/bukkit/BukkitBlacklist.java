/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.blacklist.Blacklist;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class BukkitBlacklist
extends Blacklist {
    private WorldGuardPlugin plugin;

    public BukkitBlacklist(Boolean useAsWhitelist, WorldGuardPlugin plugin) {
        super(useAsWhitelist, plugin.getLogger());
        this.plugin = plugin;
    }

    @Override
    public void broadcastNotification(String msg) {
        this.plugin.broadcastNotification(msg);
    }
}

