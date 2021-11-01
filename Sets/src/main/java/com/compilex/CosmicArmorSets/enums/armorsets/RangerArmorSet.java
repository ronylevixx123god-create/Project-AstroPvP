/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RangerArmorSet
        extends ArmorSetInfo
        implements WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&a&lRanger Hood", "&a&oThe hood of a legendary", "&a&oRanger lost with time itself.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Hardened")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&a&lRanger Cuirass", "&a&oThe lost cuirass of the,", "&a&olast known legendary archer.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Overload"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Hardened")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&a&lRanger Greaves", "&a&oThe fabled shin protectors,", "&a&oof an ancient agile Ranger.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Cactus")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&a&lRanger Slippers", "&a&oLightweight footgear for", "&a&oa ancient nimble Ranger.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Rocket Escape")))));
    }

    @Override
    public boolean supportsBowDamage() {
        return true;
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList(ChatColor.RED + "" + ChatColor.BOLD + "RANGER SET BONUS: " + ChatColor.RED + "-25% INCOMING BOW DMG, +30% RANGER BOW DMG, Immune to Teleblock, Gears IV");
    }

    @Override
    public void onEquip(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld(player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            return;
        }
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.75);
            Bukkit.getLogger().info("lowering damage to due archer damaged: " + event.getDamage(EntityDamageEvent.DamageModifier.BASE) + " from " + event.getDamager());
            return true;
        }
        return false;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (damager.getItemInHand() != null && ArmorSet.isRangerBow(damager.getItemInHand())) {
            if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 1, damager)) {
                return false;
            }
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.3);
            Bukkit.getLogger().info("Increasing arrow damage for " + damager.getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        if (event.getDamager() instanceof Projectile) {
            return this.modifyDamage(damaged, event, -5.0, multiplier);
        }
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (event.getDamager() instanceof Projectile) {
            return this.modifyDamage(damager, event, 6.0, multiplier);
        }
        return false;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        return ArmorSet.createRangerBow(applyTransmog);
    }
}

