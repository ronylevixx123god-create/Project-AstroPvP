/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

final class ParkourRegionTask
extends BukkitRunnable {
    ParkourRegionTask() {
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            String region = WorldGuardUtils.getRegionName(p.getLocation());
            p.setMetadata("worldguardRegion", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)region));
            boolean parkour = region.contains("parkour");
            boolean removeEnchants = !region.contains("enchants");
            boolean hidePlayers = region.contains("hidePlayers");
            if (parkour && !this.canSeePlayersInParkour(p)) {
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                }
                if (p.isFlying()) {
                    p.setFlying(false);
                }
                if (removeEnchants) {
                    for (PotionEffect pe : p.getActivePotionEffects()) {
                        p.removePotionEffect(pe.getType());
                    }
                }
            }
            if (hidePlayers) {
                if (this.canSeePlayersInParkour(p)) continue;
                p.setMetadata("hidePlayers", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (this.canSeePlayersInParkour(pl) || pl.equals((Object)p) || !pl.hasMetadata("worldguardRegion") || !((MetadataValue)pl.getMetadata("worldguardRegion").get(0)).asString().contains("hidePlayers") || !p.canSee(pl)) continue;
                    p.hidePlayer(pl);
                }
                continue;
            }
            if (!p.hasMetadata("hidePlayers")) continue;
            p.removeMetadata("hidePlayers", (Plugin)Arkkit.get());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (this.canSeePlayersInParkour(pl) || pl.equals((Object)p) || p.canSee(pl)) continue;
                p.showPlayer(pl);
            }
        }
    }

    private boolean canSeePlayersInParkour(Player p) {
        if (p.getName().equals("TBNRfrags") || p.getName().equals("Woofless")) {
            return false;
        }
        return p.hasPermission("arkkit.staff.hidden");
    }
}

