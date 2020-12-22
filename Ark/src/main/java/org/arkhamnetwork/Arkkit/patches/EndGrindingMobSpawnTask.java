/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicFund.CosmicFundAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Skeleton$SkeletonType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicFund.CosmicFundAPI;
import java.util.Map;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.EndGrinding;
import org.arkhamnetwork.Arkkit.patches.EndMonsterType;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGCTask;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

final class EndGrindingMobSpawnTask
extends BukkitRunnable {
    long tick = 0L;

    EndGrindingMobSpawnTask() {
    }

    public void run() {
        ++this.tick;
        if (EndGrinding.fundEnabled && !EndGrinding.hcf) {
            return;
        }
        if (ChunkGCTask.isEndDragonSpawned()) {
            return;
        }
        for (Map.Entry<Location, EndMonsterType> data : EndGrinding.mobSpawnpoints.entrySet()) {
            Location spawn = data.getKey();
            String region = WorldGuardUtils.getRegionName(spawn);
            EndMonsterType type = data.getValue();
            int playerCount = 0;
            for (Player p : spawn.getWorld().getPlayers()) {
                String pRegion = WorldGuardUtils.getRegionName(p.getLocation());
                if (!pRegion.equals(region)) continue;
                ++playerCount;
            }
            if (playerCount <= 0 || this.tick % 2L != 0L) continue;
            if (type == EndMonsterType.CREEPER) {
                this.spawnEndCreeper(spawn);
                if (playerCount >= 4) {
                    this.spawnEndCreeper(spawn);
                }
                if (playerCount < 8) continue;
                this.spawnEndCreeper(spawn);
                continue;
            }
            if (type != EndMonsterType.CUSTOM || this.tick % 6L != 0L) continue;
            this.spawnEndCustom(spawn);
            if (playerCount < 4) continue;
            this.spawnEndCustom(spawn);
        }
    }

    private Entity spawnEndCreeper(Location l) {
        l = LocationUtils.getNearbyLocation(l, 2, 10).add(0.0, 2.0, 0.0);
        int creeperCount = 0;
        for (Entity ent : l.getChunk().getEntities()) {
            if (!(ent instanceof Creeper)) continue;
            ++creeperCount;
        }
        if (creeperCount >= 4) {
            return null;
        }
        Creeper c = (Creeper)l.getWorld().spawnEntity(l, EntityType.CREEPER);
        c.setPowered(true);
        c.setCustomName((Object)ChatColor.LIGHT_PURPLE + "" + (Object)ChatColor.BOLD + "Ender Creeper");
        c.setCustomNameVisible(true);
        c.setMaxHealth(c.getMaxHealth() * 5.0);
        c.setHealth(c.getMaxHealth());
        c.setMetadata("endMonsterType", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)EndMonsterType.CREEPER.toString()));
        ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l.add(0.0, 0.5, 0.0), 0.7f, 16);
        return c;
    }

    private Entity spawnEndCustom(Location l) {
        l = LocationUtils.getNearbyLocation(l, 2, 10).add(0.0, 2.0, 0.0);
        int monsterCount = 0;
        for (Entity ent : l.getChunk().getEntities()) {
            if (!(ent instanceof Skeleton)) continue;
            ++monsterCount;
        }
        if (monsterCount >= 2) {
            return null;
        }
        Skeleton s = (Skeleton)l.getWorld().spawnEntity(l, EntityType.SKELETON);
        s.setSkeletonType(Skeleton.SkeletonType.WITHER);
        s.setMaxHealth(100.0);
        s.setHealth(100.0);
        s.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        s.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        s.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        s.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0));
        s.setFireTicks(32767);
        s.setCustomName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Ender Monster");
        s.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        s.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        s.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        s.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        s.setMetadata("endMonsterType", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)EndMonsterType.CUSTOM.toString()));
        double rand = Math.random();
        if (rand < 0.4) {
            s.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_AXE));
        } else {
            s.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        }
        s.setCustomNameVisible(true);
        ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l.add(0.0, 0.5, 0.0), 0.7f, 16);
        return s;
    }
}

