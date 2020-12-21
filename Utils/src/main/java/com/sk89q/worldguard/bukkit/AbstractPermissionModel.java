/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.command.CommandSender;
import org.enginehub.util.PermissionModel;

abstract class AbstractPermissionModel
implements PermissionModel {
    private final WorldGuardPlugin plugin;
    private final CommandSender sender;

    public AbstractPermissionModel(WorldGuardPlugin plugin, CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    protected WorldGuardPlugin getPlugin() {
        return this.plugin;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    protected boolean hasPluginPermission(String permission) {
        return this.plugin.hasPermission(this.getSender(), "worldguard." + permission);
    }
}

