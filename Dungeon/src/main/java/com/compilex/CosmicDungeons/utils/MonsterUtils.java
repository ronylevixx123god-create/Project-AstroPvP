/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
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
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.compilex.CosmicDungeons.utils;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MonsterUtils {
    public static LivingEntity spawnOutpostPirate(Location location) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        LivingEntity ent = (LivingEntity)location.getWorld().spawnEntity(location, EntityType.SKELETON);
        if (((Random)random).nextInt(10) <= 8) {
            Skeleton skeleton = (Skeleton)ent;
            skeleton.setSkeletonType(Skeleton.SkeletonType.WITHER);
        }
        EntityEquipment equipment = ent.getEquipment();
        equipment.setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.DURABILITY, 100).build());
        equipment.setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.DURABILITY, 100).build());
        equipment.setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.DURABILITY, 100).build());
        equipment.setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.DURABILITY, 100).build());
        equipment.setItemInHand(((Random)random).nextBoolean() ? new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 5).enchant(Enchantment.ARROW_FIRE, 1).enchant(Enchantment.DURABILITY, 10).build() : new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 4).enchant(Enchantment.DURABILITY, 5).enchant(Enchantment.KNOCKBACK, ((Random)random).nextBoolean() ? 2 : 1).build());
        equipment.setItemInHandDropChance(0.0f);
        equipment.setHelmetDropChance(0.0f);
        equipment.setChestplateDropChance(0.0f);
        equipment.setLeggingsDropChance(0.0f);
        equipment.setBootsDropChance(0.0f);
        ent.setMaxHealth(ent.getMaxHealth() * 2.0);
        ent.setHealth(ent.getMaxHealth());
        ent.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Fallen Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Cadet");
        ent.setCustomNameVisible(true);
        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        ent.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
        ent.setMetadata("destroyedSpacePirate", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)true));
        try {
            de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), ent.getLocation().add(0, 1, 0), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.8f, 85);

        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return ent;
    }

    public static LivingEntity spawnSpacePirate(Location l) throws Exception {
        Ghast ghast = null;
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
        Object le = null;
        if (et == EntityType.PIG_ZOMBIE) {
            PigZombie b = (PigZombie)l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Pirate");
            b.setCustomNameVisible(true);
            b.setBaby(true);
            b.setAngry(true);
        } else if (et == EntityType.IRON_GOLEM) {
            IronGolem b2 = (IronGolem)l.getWorld().spawnEntity(l, EntityType.IRON_GOLEM);
            b2.setMaxHealth(b2.getMaxHealth() * 2.0);
            b2.setHealth(b2.getMaxHealth());
            b2.setPlayerCreated(false);
            b2.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Golem");
            b2.setCustomNameVisible(true);
        } else if (et == EntityType.SKELETON) {
            Skeleton b3 = (Skeleton)l.getWorld().spawnEntity(l, EntityType.SKELETON);
            b3.setSkeletonType(Skeleton.SkeletonType.WITHER);
            b3.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            b3.setMaxHealth(b3.getMaxHealth() * 2.0);
            b3.setHealth(b3.getMaxHealth());
            b3.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Pirate");
            b3.setCustomNameVisible(true);
        } else if (et == EntityType.GHAST) {
            Ghast b4 = (Ghast)l.getWorld().spawnEntity(l, EntityType.GHAST);
            b4.setMaxHealth(b4.getMaxHealth() * 5.0);
            b4.setHealth(b4.getMaxHealth());
            b4.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Phantom");
            b4.setCustomNameVisible(true);
            ghast = b4;
        }
        ghast.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        ghast.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, new Random().nextInt(3)));
        ghast.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, new Random().nextInt(2)));
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
        i.addEnchantment(Enchantment.KNOCKBACK, new Random().nextBoolean() ? 1 : 2);
        ghast.getEquipment().setItemInHand(i);
        ghast.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        ghast.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        ghast.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        ghast.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        ghast.setCanPickupItems(false);
        ghast.setMetadata("spacePirate", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)true));
        try {
            de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), ghast.getLocation().add(0, 1, 0), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.8f, 85);

        } catch (Exception e2) {
            e2.printStackTrace();
        }        return ghast;
    }
}

