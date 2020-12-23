/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.struct.Relation
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

final class NoVoidDamageListener
        implements Listener {
    NoVoidDamageListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            Location l = e.getEntity().getLocation().clone();
            if (l.getWorld().getName().equals("world_cosmic_arena") || l.getWorld().getName().equals("cannonworld")) {
                e.getEntity().teleport(l.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                return;
            }
            if (e.getEntity().getWorld().getEnvironment() == World.Environment.THE_END || e.getEntity().getWorld().getName().equalsIgnoreCase("dungeon_destroyed_outpost")) {
                return;
            }
            if (l.getY() <= 0.0) {
                Player pEnt;
                FPlayer fp;
                l.setY(0.0);
                if (l.getBlock().getType() != Material.BEDROCK) {
                    l.getBlock().setType(Material.BEDROCK);
                }
                if (!(fp = FPlayers.i.get(pEnt = (Player) e.getEntity())).getRelationToLocation().isAtLeast(Relation.ALLY)) {
                    pEnt.teleport(l.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    pEnt.sendMessage(ChatColor.RED + "You were under y:0 in non-ally territory, and have been force teleported to spawn.");
                    Bukkit.getLogger().info("[Arkkit (NoVoidDamage)] Teleported player " + pEnt.getName() + " to spawn due to invalid location: " + l);
                } else {
                    e.getEntity().teleport(l.add(0.0, 2.0, 0.0), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                }
            }
        }
    }
}

