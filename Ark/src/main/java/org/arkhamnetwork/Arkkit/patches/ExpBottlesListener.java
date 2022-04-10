/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.wimbli.WorldBorder.WorldBorder
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.wimbli.WorldBorder.WorldBorder;
import org.arkhamnetwork.Arkkit.libs.ExpUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

final class ExpBottlesListener
        implements Listener {
    ExpBottlesListener() {
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getFrom().getWorld().getName().equals("world_duels") || e.getTo().getWorld().getName().equals("world_duels")) {
            return;
        }
        if (e.getFrom().getWorld().getName().equals("world_duels2") || e.getTo().getWorld().getName().equals("world_duels2")) {
            return;
        }
        if (e.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN && e.getCause() != PlayerTeleportEvent.TeleportCause.COMMAND) {
            return;
        }
        if (WorldBorder.plugin.getWorldBorder(e.getFrom().getWorld().getName()) != null && !WorldBorder.plugin.getWorldBorder(e.getFrom().getWorld().getName()).insideBorder(e.getPlayer().getLocation())) {
            return;
        }
        Player p = e.getPlayer();
        if (!ExpBottlesCommand.canMakeXPBottle(p)) {
            boolean noLimit = true;
            if (p.hasPermission("arkkit.xpbottles.immune")) {
                if (p.getName().equals("Woofless") || p.getName().equals("TBNRfrags")) {
                    if (p.getGameMode() == GameMode.CREATIVE) {
                        noLimit = true;
                    }
                } else {
                    noLimit = true;
                }
            }
            if (Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts") && CosmicOutpostsAPI.controlsHeroOutpost(p)) {
                p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "No XP Exhaustion " + ChatColor.GRAY + "[Hero Outpost Perk]");
                noLimit = true;
            }
            if (!noLimit) {
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot teleport for " + ExpBottlesCommand.getTimeString(p.getMetadata("nextExpBottle").get(0).asLong() - System.currentTimeMillis()) + " due to EXP exhaustion.");
                if (!p.hasPermission("playervaults.amount.15")) {
                    p.sendMessage(ChatColor.GRAY + "Upgrade your rank at " + ChatColor.UNDERLINE + "buy.astropvp.net" + ChatColor.GRAY + " to decrease this delay.");
                }
                e.setCancelled(true);
            }
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onExpBottleUse(PlayerInteractEvent e) {
        if (e.hasItem() && ExpBottles.isXpBottle(e.getItem())) {
            Player p = e.getPlayer();
            int xpValue = ExpBottles.getXpAmount(e.getItem()) * e.getItem().getAmount();
            e.setCancelled(true);
            //e.setUseInteractedBlock(Event.Result.DENY);
            //e.setUseItemInHand(Event.Result.DENY);
            p.setItemInHand(null);
            int exp = (p.getTotalExperience() +xpValue);
            p.setTotalExperience(0);
            p.setExp(0);
            p.setLevel(0);
            p.giveExp(exp);

            p.playSound(p.getLocation(), Sound.GLASS, 1.0f, 0.8f);
            p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "+ " + xpValue + " xp");
        }
    }
}

