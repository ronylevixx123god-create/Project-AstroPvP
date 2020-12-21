/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.util;

import com.sk89q.commandbook.util.PlayerIteratorAction;
import com.sk89q.commandbook.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportPlayerIterator
extends PlayerIteratorAction {
    protected final Location loc;
    protected Location oldLoc;
    protected final boolean silent;
    protected final boolean[] relative;

    public TeleportPlayerIterator(CommandSender sender, Location loc) {
        this(sender, loc, false);
    }

    public TeleportPlayerIterator(CommandSender sender, Location loc, boolean silent) {
        this(sender, loc, silent, new boolean[]{false, false, false});
    }

    public TeleportPlayerIterator(CommandSender sender, Location loc, boolean silent, boolean[] relative) {
        super(sender);
        this.loc = loc;
        this.silent = silent;
        this.relative = relative;
    }

    @Override
    public void perform(Player player) {
        this.oldLoc = player.getLocation();
        Location newLoc = this.loc;
        if (this.relative[0]) {
            newLoc.setX(this.oldLoc.getX() + this.loc.getX());
        }
        if (this.relative[1]) {
            newLoc.setY(this.oldLoc.getY() + this.loc.getY());
        }
        if (this.relative[2]) {
            newLoc.setZ(this.oldLoc.getZ() + this.loc.getZ());
        }
        if ((double)newLoc.getPitch() == 0.0 && (double)newLoc.getYaw() == 0.0) {
            newLoc.setPitch(this.oldLoc.getPitch());
            newLoc.setYaw(this.oldLoc.getYaw());
        }
        newLoc.getChunk().load(true);
        if (player.getVehicle() != null) {
            player.getVehicle().eject();
        }
        player.teleport(newLoc);
    }

    @Override
    public void onCaller(Player player) {
        player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Teleported.");
    }

    @Override
    public void onVictim(CommandSender sender, Player player) {
        if (this.silent) {
            return;
        }
        if (this.oldLoc.getWorld().equals(this.loc.getWorld())) {
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "You've been teleported by " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW) + ".");
        } else {
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "You've been teleported by " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW) + " to world '" + this.loc.getWorld().getName() + "'.");
        }
    }

    @Override
    public void onInformMany(CommandSender sender, int affected) {
        sender.sendMessage(ChatColor.YELLOW.toString() + affected + " teleported.");
    }
}

