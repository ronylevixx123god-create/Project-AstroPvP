/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.FinalBattle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

final class FinalBattleListener
implements Listener {
    FinalBattleListener() {
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("world_final_battle")) {
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.getDrops().clear();
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().getWorld().getName().equals("world_final_battle")) {
            e.setRespawnLocation(this.getRespawnLocation(e.getPlayer()));
            final Player p = e.getPlayer();
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    p.teleport(FinalBattleListener.this.getRespawnLocation(p), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                }
            }, 20L);
        }
    }

    public Location getRespawnLocation(Player p) {
        FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
        if (fp.getFaction().getId() == "2") {
            if (FinalBattle.wooflessRespawn) {
                return new Location(Bukkit.getWorld((String)"world_final_battle"), -9.0, 126.0, 75.0);
            }
            return Bukkit.getWorld((String)"world").getSpawnLocation();
        }
        if (fp.getFaction().getId() == "3") {
            if (FinalBattle.prestonRespawn) {
                return new Location(Bukkit.getWorld((String)"world_final_battle"), -4.0, 126.0, -143.0);
            }
            return Bukkit.getWorld((String)"world").getSpawnLocation();
        }
        return Bukkit.getWorld((String)"world").getSpawnLocation();
    }
}

