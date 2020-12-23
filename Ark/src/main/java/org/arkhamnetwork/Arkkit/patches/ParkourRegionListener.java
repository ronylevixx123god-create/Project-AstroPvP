/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.event.LandClaimEvent
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerAnimationEvent
 *  org.bukkit.event.player.PlayerToggleFlightEvent
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.event.LandClaimEvent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.Collections;

final class ParkourRegionListener
        implements Listener {
    ParkourRegionListener() {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerAnimation(PlayerAnimationEvent e) {
        Player pl;
        Block b;
        if (e.getPlayer().hasMetadata("worldguardRegion") && e.getPlayer().getMetadata("worldguardRegion").get(0).asString().contains("spleef") && (b = (pl = e.getPlayer()).getLineOfSight(Collections.singleton(Material.AIR), 4).get(0)).getTypeId() == 80) {
            b.setType(Material.AIR);
            Location l = b.getLocation().add(0.0, 0.5, 0.0);
            PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition(Math.round(l.getBlockX()), Math.round(l.getBlockY()), Math.round(l.getBlockZ())), 80, false);
            ParticleEffect.sendWorldEventPacket(particles, l);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        String regionName;
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER && (regionName = WorldGuardUtils.getRegionName(e.getLocation())).toLowerCase().contains("_ai")) {
            e.setCancelled(true);
            e.getLocation().getWorld().spawnEntity(e.getLocation(), e.getEntityType());
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        if (e.isFlying() && !e.getPlayer().hasPermission("arkkit.staff.hidden") && WorldGuardUtils.getRegionName(e.getPlayer().getLocation()).contains("parkour")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionClaimLand(LandClaimEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        Chunk c = e.getLocation().getWorld().getChunkAt((int) e.getLocation().getX(), (int) e.getLocation().getZ());
        Location l = c.getBlock(8, 0, 8).getLocation();
        if (WorldGuardUtils.getRegionName(l).contains("noClaim")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) You cannot claim land in this area.");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (WorldGuardUtils.getRegionName(e.getEntity().getLocation()).contains("keepInv")) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }
}

