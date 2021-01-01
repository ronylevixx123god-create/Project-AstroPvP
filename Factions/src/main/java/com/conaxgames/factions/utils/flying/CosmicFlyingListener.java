/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerToggleFlightEvent
 */
package com.conaxgames.factions.utils.flying;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class CosmicFlyingListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent e) {
        if (e.isFlying()) {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 0.75f);
        } else {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_BLAST, 1.0f, 0.75f);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String cmd;
        if (e.getMessage().equalsIgnoreCase("/toggle smoke")) {
            e.setMessage("/togglesmoke");
        }
        if (((cmd = e.getMessage().toLowerCase()).equals("/fly") || cmd.equals("/efly") || cmd.startsWith("/fly ") || cmd.startsWith("/efly ")) && CosmicFlyingTask.flyingOnlyInBases && !e.getPlayer().hasPermission("essentials.fly.noparticles") && !e.getPlayer().hasMetadata("inClaim")) {
            e.setCancelled(true);
            e.setMessage("/null");
            e.getPlayer().sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot /fly outside of your faction claim.");
            return;
        }
    }
}

