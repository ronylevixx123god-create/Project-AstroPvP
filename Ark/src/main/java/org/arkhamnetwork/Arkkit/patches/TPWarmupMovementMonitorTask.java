/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class TPWarmupMovementMonitorTask
        extends BukkitRunnable {
    TPWarmupMovementMonitorTask() {
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasMetadata("queuedTeleport")) continue;
            Location castLocation = ((QueuedTeleport) p.getMetadata("queuedTeleport").get(0).value()).castLocation;
            if (p.getLocation().getWorld().equals(castLocation.getWorld()) && p.getLocation().distanceSquared(castLocation) > 4.0) {
                TPWarmup.cancelQueuedTeleport(p);
                p.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.DARK_RED + "Pending teleport request cancelled due to movement.");
                continue;
            }
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 55);
            if (!p.hasMetadata("bannerTp")) continue;
            Location bannerLocation = (Location) p.getMetadata("bannerTp").get(0).value();
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, bannerLocation, 0.5f, 55);
        }
    }
}

