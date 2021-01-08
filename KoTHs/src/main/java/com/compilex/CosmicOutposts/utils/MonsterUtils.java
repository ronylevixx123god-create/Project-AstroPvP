/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Ghast
 *  org.bukkit.entity.IronGolem
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.PigZombie
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Skeleton$SkeletonType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.compilex.CosmicOutposts.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MonsterUtils {
    public static HashMap<ItemStack, Double> potionDrops;
    public static HashMap<ItemStack, Double> bookDrops;

    public static LivingEntity spawnSoulDemon(Location l) {
        EntityType et = null;
        double rand = Math.random();
        if (rand <= 0.1) {
            et = EntityType.GHAST;
        }
        if (rand <= 0.35) {
            et = EntityType.IRON_GOLEM;
        }
        if (rand <= 0.7) {
            et = EntityType.SKELETON;
        }
        if (et == null) {
            et = EntityType.PIG_ZOMBIE;
        }
        PigZombie le = null;
        if (et == EntityType.PIG_ZOMBIE) {
            PigZombie b = (PigZombie)l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Soul " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Demon");
            b.setCustomNameVisible(true);
            b.setBaby(true);
            b.setAngry(true);
            le = b;
        } else if (et == EntityType.IRON_GOLEM) {
            IronGolem b2 = (IronGolem)l.getWorld().spawnEntity(l, EntityType.IRON_GOLEM);
            b2.setMaxHealth(b2.getMaxHealth() * 2.0);
            b2.setHealth(b2.getMaxHealth());
            b2.setPlayerCreated(false);
            b2.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Soul " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Golem");
            b2.setCustomNameVisible(true);
            le = (PigZombie) b2;
        } else if (et == EntityType.SKELETON) {
            Skeleton b3 = (Skeleton)l.getWorld().spawnEntity(l, EntityType.SKELETON);
            b3.setSkeletonType(Skeleton.SkeletonType.WITHER);
            b3.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            b3.setMaxHealth(b3.getMaxHealth() * 2.0);
            b3.setHealth(b3.getMaxHealth());
            b3.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Soul " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Hunter");
            b3.setCustomNameVisible(true);
            le = (PigZombie) b3;
        } else if (et == EntityType.GHAST) {
            Ghast b4 = (Ghast)l.getWorld().spawnEntity(l, EntityType.GHAST);
            b4.setMaxHealth(b4.getMaxHealth() * 5.0);
            b4.setHealth(b4.getMaxHealth());
            b4.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Soul " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Phantom");
            b4.setCustomNameVisible(true);
            le = (PigZombie) b4;
        }
        le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, new Random().nextInt(3)));
        le.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, new Random().nextInt(2)));
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
        i.addEnchantment(Enchantment.KNOCKBACK, new Random().nextBoolean() ? 1 : 2);
        le.getEquipment().setItemInHand(i);
        le.getEquipment().setHelmet(new ItemStack(new Random().nextBoolean() ? Material.IRON_HELMET : Material.DIAMOND_HELMET));
        le.getEquipment().setChestplate(new ItemStack(new Random().nextBoolean() ? Material.IRON_CHESTPLATE : Material.DIAMOND_CHESTPLATE));
        le.getEquipment().setLeggings(new ItemStack(new Random().nextBoolean() ? Material.IRON_LEGGINGS : Material.DIAMOND_LEGGINGS));
        le.getEquipment().setBoots(new ItemStack(new Random().nextBoolean() ? Material.IRON_BOOTS : Material.DIAMOND_BOOTS));
        le.setCanPickupItems(false);
        le.setMetadata("soulDemon", new FixedMetadataValue(CosmicKOTHPlugin.get_(), true));
        ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, le.getLocation().add(0.0, 1.0, 0.0), 0.15f, 25);
        return le;
    }

    public static LivingEntity spawnMonster(Location l) {
        EntityType et = null;
        double rand = Math.random();
        if (rand <= 0.7) {
            et = EntityType.SKELETON;
        }
        if (rand <= 0.35) {
            et = EntityType.ZOMBIE;
        }
        if (rand <= 0.1) {
            et = EntityType.CREEPER;
        }
        if (et == null) {
            et = EntityType.CAVE_SPIDER;
        }
        LivingEntity le = (LivingEntity)l.getWorld().spawnEntity(l, et);
        le.setMaxHealth(le.getMaxHealth() * 2.0);
        le.setHealth(le.getMaxHealth());
        le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, new Random().nextInt(3)));
        le.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, new Random().nextInt(2)));
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
        i.addEnchantment(Enchantment.KNOCKBACK, new Random().nextBoolean() ? 1 : 2);
        le.getEquipment().setItemInHand(i);
        le.getEquipment().setHelmet(new ItemStack(new Random().nextBoolean() ? Material.IRON_HELMET : Material.DIAMOND_HELMET));
        le.getEquipment().setChestplate(new ItemStack(new Random().nextBoolean() ? Material.IRON_CHESTPLATE : Material.DIAMOND_CHESTPLATE));
        le.getEquipment().setLeggings(new ItemStack(new Random().nextBoolean() ? Material.IRON_LEGGINGS : Material.DIAMOND_LEGGINGS));
        le.getEquipment().setBoots(new ItemStack(new Random().nextBoolean() ? Material.IRON_BOOTS : Material.DIAMOND_BOOTS));
        le.setCanPickupItems(false);
        le.setMetadata("vanillaOutpostMonster", new FixedMetadataValue(CosmicKOTHPlugin.get_(), true));
        ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, le.getLocation().add(0.0, 1.0, 0.0), 0.15f, 25);
        return le;
    }

    public static void populateLootTables() {
        potionDrops = new HashMap();
        bookDrops = new HashMap();
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8257), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8289), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8259), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8297), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8290), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8269), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8289), 0.02);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8291), 0.02);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8297), 0.02);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8290), 0.02);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16449), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16481), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16451), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16489), 0.1);
        potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16482), 0.1);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.DAMAGE_ARTHROPODS, 1, true), 0.15);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.DAMAGE_UNDEAD, 1, true), 0.15);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.WATER_WORKER, 1, true), 0.15);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.OXYGEN, 1, true), 0.15);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.DEPTH_STRIDER, 1, true), 0.02);
        bookDrops.put(ItemUtils.buildLootingSword(4), 0.02);
        bookDrops.put(ItemUtils.buildLootingSword(5), 0.02);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.LUCK, 1, true), 0.15);
        bookDrops.put(ItemUtils.buildEnchantedBook(Enchantment.LURE, 1, true), 0.15);
    }

    public static ItemStack getVanillaMonsterDrop() {
        double d = Math.random();
        if (d < 0.6) {
            return null;
        }
        if (d < 0.9) {
            for (Map.Entry<ItemStack, Double> data : potionDrops.entrySet()) {
                if (!(Math.random() < data.getValue())) continue;
                return data.getKey().clone();
            }
        } else {
            for (Map.Entry<ItemStack, Double> data : bookDrops.entrySet()) {
                if (!(Math.random() < data.getValue())) continue;
                return data.getKey().clone();
            }
        }
        return null;
    }
}

