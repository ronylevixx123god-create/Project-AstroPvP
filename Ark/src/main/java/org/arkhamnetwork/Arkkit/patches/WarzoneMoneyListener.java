/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerToggleFlightEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoneyTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

final class WarzoneMoneyListener
implements Listener {
    WarzoneMoneyListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        WarzoneMoneyTask.disqualifyPlayer(p);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerToggleFly(PlayerToggleFlightEvent e) {
        if (e.isFlying() && WarzoneMoneyTask.getSecondsInWarzone(e.getPlayer()) > 60) {
            e.getPlayer().sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) WARNING: " + (Object)ChatColor.RED + "If you /fly, your warzone rewards reset!");
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Faction f;
        if (WarzoneMoneyTask.getSecondsInWarzone(e.getPlayer()) > 60 && !(f = Board.getFactionAt((FLocation)new FLocation(e.getTo()))).isWarZone()) {
            Player p = e.getPlayer();
            WarzoneMoneyTask.disqualifyPlayer(p);
        }
    }
}

