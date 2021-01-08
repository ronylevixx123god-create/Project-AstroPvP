/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package com.compilex.CosmicOutposts.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class OutpostWarpMenuListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("Astro Outposts")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            Player p = (Player)e.getWhoClicked();
            if (p.getWorld().getEnvironment() == World.Environment.THE_END) {
                p.closeInventory();
                p.sendMessage((Object)ChatColor.RED + "You cannot teleport to Astro Outpost from The End!");
                return;
            }
            if (e.getRawSlot() == 3) {
                p.closeInventory();
                if (CosmicOutpostsAPI.isDefender(p, CosmicKOTHPlugin.get_().getTraineeOutpost())) {
                    p.teleport(CosmicKOTHPlugin.get_().getTraineeOutpost().getDefenderSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                } else {
                    p.teleport(CosmicKOTHPlugin.get_().getTraineeOutpost().getAttackerSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            } else if (e.getRawSlot() == 5) {
                p.closeInventory();
                if (CosmicOutpostsAPI.isDefender(p, CosmicKOTHPlugin.get_().getHeroOutpost())) {
                    p.teleport(CosmicKOTHPlugin.get_().getHeroOutpost().getDefenderSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                } else {
                    p.teleport(CosmicKOTHPlugin.get_().getHeroOutpost().getAttackerSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            } else if (e.getRawSlot() == 7) {
                p.closeInventory();
                if (CosmicOutpostsAPI.isDefender(p, CosmicKOTHPlugin.get_().getCosmonautOutpost())) {
                    p.teleport(CosmicKOTHPlugin.get_().getCosmonautOutpost().getDefenderSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                } else {
                    p.teleport(CosmicKOTHPlugin.get_().getCosmonautOutpost().getAttackerSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            } else if (e.getRawSlot() == 1) {
                p.closeInventory();
                if (CosmicOutpostsAPI.isDefender(p, CosmicKOTHPlugin.get_().getVanillaOutpost())) {
                    p.teleport(CosmicKOTHPlugin.get_().getVanillaOutpost().getDefenderSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                } else {
                    p.teleport(CosmicKOTHPlugin.get_().getVanillaOutpost().getAttackerSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            }
            p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 1.0f, 2.0f);
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equals("/warp outpost") || e.getMessage().equals("/warp outposts")) {
            e.setMessage("/outposts warp");
        }
    }
}

