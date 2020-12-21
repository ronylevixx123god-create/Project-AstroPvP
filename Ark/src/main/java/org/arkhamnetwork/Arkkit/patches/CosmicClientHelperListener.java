/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CosmicClientHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class CosmicClientHelperListener
implements Listener {
    CosmicClientHelperListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                if (p.isOnline()) {
                    if (!p.hasMetadata("cosmicClientVersion")) {
                        p.sendMessage("");
                        p.sendMessage(ChatColor.AQUA.toString() + (Object)ChatColor.BOLD + "COSMIC CLIENT");
                        p.sendMessage((Object)ChatColor.GRAY + "Hello " + (Object)ChatColor.AQUA + p.getName() + (Object)ChatColor.GRAY + ", we have noticed you are not using the " + (Object)ChatColor.UNDERLINE + "official Cosmic Client" + (Object)ChatColor.GRAY + " to play on CosmicPvP. Download and install it today, and connect to CosmicPvP with our client from now on to experience...");
                        p.sendMessage("");
                        p.sendMessage((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "1.25x XP/mcMMO Multiplier");
                        p.sendMessage((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "VIP 2X XP/mcMMO Weekends");
                        p.sendMessage("");
                        p.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "READ MORE: " + (Object)ChatColor.WHITE + (Object)ChatColor.UNDERLINE + "http://cosmicclient.com");
                        p.sendMessage("");
                    } else if (p.hasMetadata("cosmicClientVersion") && !p.hasMetadata("cosmicClientThankYou")) {
                        p.setMetadata("cosmicClientThankYou", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                        p.sendMessage("");
                        p.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!)" + (Object)ChatColor.YELLOW + " Thank you for using the Cosmic Client, " + p.getName() + "!");
                        p.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Passive 1.25x XP/mcMMO multiplier activated!");
                        if (CosmicClientHelper.extraXPMod > 1.0) {
                            p.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Bonus " + CosmicClientHelper.extraXPMod + "x bonus XP/mcMMO multiplier activated!");
                        }
                        p.sendMessage("");
                    }
                }
            }
        }, 300L);
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            return;
        }
        if (e.getEntity().getKiller() != null && e.getEntity().getKiller().hasMetadata("cosmicClientVersion")) {
            e.setDroppedExp((int)Math.round((double)e.getDroppedExp() * 1.25 * CosmicClientHelper.extraXPMod));
        }
    }

    @EventHandler
    public void onMcMMOGainXP(McMMOPlayerXpGainEvent e) {
        if (e.getPlayer().hasMetadata("cosmicClientVersion")) {
            e.setRawXpGained((float)((double)e.getRawXpGained() * 1.25 * CosmicClientHelper.extraXPMod));
        }
    }
}

