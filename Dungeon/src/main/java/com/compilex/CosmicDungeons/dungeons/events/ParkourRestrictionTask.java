/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.WorldGuardUtils
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicDungeons.dungeons.events;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

class ParkourRestrictionTask
extends BukkitRunnable {
    Dungeon dungeon;
    private List<String> regions;

    public ParkourRestrictionTask(Dungeon d, List<String> regions) {
        this.dungeon = d;
        this.regions = regions;
    }

    public void run() {
        for (Player p : this.dungeon.getPlayers()) {
            DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(p);
            if (dp.getCurrentDungeon() == null || !dp.getCurrentDungeon().equals(this.dungeon)) continue;
            if (p.getAllowFlight() && !p.isOp()) {
                p.setAllowFlight(false);
            }
            if (p.isFlying() && !p.isOp()) {
                p.setFlying(false);
            }
            String region = WorldGuardUtils.getRegionName((Location)p.getLocation());
            if (p.hasMetadata("inDungeonParkour")) {
                for (PotionEffect pe : p.getActivePotionEffects()) {
                    if (!pe.getType().equals((Object)PotionEffectType.JUMP)) continue;
                    p.removePotionEffect(pe.getType());
                }
            }
            if (this.regions.contains(region)) {
                for (PotionEffect pe : p.getActivePotionEffects()) {
                    if (pe.getType().equals((Object)PotionEffectType.JUMP)) continue;
                    p.removePotionEffect(pe.getType());
                }
                p.setMetadata("inDungeonParkour", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)region));
                continue;
            }
            p.removeMetadata("inDungeonParkour", (Plugin)CosmicDungeonsPlugin.get());
        }
    }
}

