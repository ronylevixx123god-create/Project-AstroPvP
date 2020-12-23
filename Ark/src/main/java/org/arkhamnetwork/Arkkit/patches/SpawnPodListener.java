/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

final class SpawnPodListener
        implements Listener {
    protected static HashSet<String> newPlayers = new HashSet();

    SpawnPodListener() {
    }

    @EventHandler
    public void onAsyncPlayerLoginEvent(AsyncPlayerPreLoginEvent e) {
        if (!Bukkit.getOfflinePlayer(e.getUniqueId()).hasPlayedBefore()) {
            newPlayers.add(e.getName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player pl = e.getPlayer();
        if (newPlayers.contains(pl.getName())) {
            pl.setAllowFlight(false);
            pl.teleport(pl.getWorld().getSpawnLocation().clone().add(0.0, 360.0, 0.0), PlayerTeleportEvent.TeleportCause.PLUGIN);
            pl.setPlayerTime(18000L, false);
            Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                @Override
                public void run() {
                    pl.sendMessage("");
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "YOU ARE ENTERING THE PLANET'S ATMOSPHERE!");
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.UNDERLINE.toString() + "BRACE FOR IMPACT!");
                    pl.sendMessage("");
                    pl.playSound(pl.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
                }
            }, 15L);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        newPlayers.remove(e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player && newPlayers.contains(e.getEntity().getName())) {
            Player pl = (Player) e.getEntity();
            newPlayers.remove(pl.getName());
            e.setCancelled(true);
            e.setDamage(0.0);
            pl.setAllowFlight(true);
            Location loc = pl.getLocation().clone().add(0.0, 1.0, 0.0);
            Block b = pl.getLocation().clone().subtract(0.0, 1.0, 0.0).getBlock();
            PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition(Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ())), b.getTypeId(), false);
            ParticleEffect.sendWorldEventPacket(particles, loc);
        }
    }

    private void makeCube(Location loc) {
        double ox = loc.getX();
        double oy = loc.getY();
        double oz = loc.getZ();
        ArrayList<Location> block = new ArrayList<Location>();
        for (int x = -2; x < 2; ++x) {
            for (int z = -2; z < 2; ++z) {
                for (int y = -2; y < 2; ++y) {
                    block.add(new Location(loc.getWorld(), ox + (double) x, oy + (double) y, oz + (double) y));
                }
            }
        }
        for (Location l : block) {
            FallingBlock fallingBlock = l.getWorld().spawnFallingBlock(l, Material.ICE.getId(), (byte) 0);
        }
    }
}

