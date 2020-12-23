/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class NetherPortalTrapListener
        extends BukkitRunnable {
    NetherPortalTrapListener() {
    }

    public void run() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.getLocation().getWorld().getName().equals("dungeon_destroyed_outpost")) continue;
            if (pl.getLocation().getBlock().getType() == Material.PORTAL && !pl.getLocation().getBlock().hasMetadata("dungeonPortalBlock") && !pl.getLocation().getBlock().hasMetadata("dungeonExitPortalBlock")) {
                if (pl.hasMetadata("in_nether_portal")) {
                    Location l = (Location) pl.getMetadata("in_nether_portal").get(0).value();
                    if (!pl.getLocation().getWorld().equals(l.getWorld())) continue;
                    pl.sendMessage(ChatColor.RED + "Woah I found you in a trap, teleporting to spawn.");
                    pl.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    pl.removeMetadata("in_nether_portal", Arkkit.get());
                    continue;
                }
                pl.setMetadata("in_nether_portal", new FixedMetadataValue(Arkkit.get(), pl.getLocation()));
                continue;
            }
            pl.removeMetadata("in_nether_portal", Arkkit.get());
        }
    }
}

