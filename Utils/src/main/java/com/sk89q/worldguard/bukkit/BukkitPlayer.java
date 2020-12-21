/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BukkitPlayer
extends LocalPlayer {
    private Player player;
    private WorldGuardPlugin plugin;

    public BukkitPlayer(WorldGuardPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    @Override
    public boolean hasGroup(String group) {
        return this.plugin.inGroup(this.player, group);
    }

    @Override
    public Vector getPosition() {
        Location loc = this.player.getLocation();
        return new Vector(loc.getX(), loc.getY(), loc.getZ());
    }

    @Override
    public void kick(String msg) {
        this.player.kickPlayer(msg);
    }

    @Override
    public void ban(String msg) {
        this.player.setBanned(true);
        this.player.kickPlayer(msg);
    }

    @Override
    public String[] getGroups() {
        return this.plugin.getGroups(this.player);
    }

    @Override
    public void printRaw(String msg) {
        this.player.sendMessage(msg);
    }

    @Override
    public boolean hasPermission(String perm) {
        return this.plugin.hasPermission(this.player, perm);
    }

    public Player getPlayer() {
        return this.player;
    }
}

