/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.world.ChunkLoadEvent
 */
package com.compilex.CosmicDungeons.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class DungeonSanityListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerTeleportIntoDungeon(PlayerTeleportEvent e) {
        if (!e.getFrom().getWorld().equals((Object)e.getTo().getWorld()) && e.getTo().getWorld().getName().startsWith("dungeon_")) {
            Player p = e.getPlayer();
            if (p.isOp() || p.hasPermission("essentials.tpo")) {
                return;
            }
            if (!e.getFrom().getBlock().hasMetadata("dungeonPortalBlock") && !e.getFrom().clone().add(0.0, 1.0, 0.0).getBlock().hasMetadata("dungeonPortalBlock")) {
                e.setCancelled(true);
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You can only teleport into dungeons through dungeon portals.");
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onChunkGenerateInDungeon(ChunkLoadEvent e) {
        if (e.getWorld().getName().startsWith("dungeon_") && e.isNewChunk()) {
            e.getChunk().unload(false, false);
        }
    }
}

