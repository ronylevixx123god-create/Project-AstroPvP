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
import com.rit.sucy.CustomEnchantment;
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
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&a&lRanger Hood", "&a&oThe hood of a legendary", "&a&oRanger lost with time itself.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Drunk"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Implants"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Heavy"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Hardened")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&a&lRanger Cuirass", "&a&oThe lost cuirass of the,", "&a&olast known legendary archer.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Aegis"), EnchantmentAPI.getEnchantment((String) "Blood Lust"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Overload"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Heavy"), EnchantmentAPI.getEnchantment((String) "Arrow Deflect"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Hardened")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&a&lRanger Greaves", "&a&oThe fabled shin protectors,", "&a&oof an ancient agile Ranger.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Clarity"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Heavy"), EnchantmentAPI.getEnchantment((String) "Lucky"), EnchantmentAPI.getEnchantment((String) "Obsidianshield"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Cactus")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&a&lRanger Slippers", "&a&oLightweight footgear for", "&a&oa ancient nimble Ranger.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Gears"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Heavy"), EnchantmentAPI.getEnchantment((String) "Metaphysical"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Springs"), EnchantmentAPI.getEnchantment((String) "Rocket Escape")))));
    }

    @Override
    public boolean supportsBowDamage() {
        return true;
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList(ChatColor.RED + "" + (Object) ChatColor.BOLD + "RANGER SET BONUS: " + (Object) ChatColor.RED + "-25% INCOMING BOW DMG, +30% RANGER BOW DMG, Immune to Teleblock, Gears IV");
    }

    @Override
    public void onEquip(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            return;
        }
        EnchantmentAPI.getEnchantment((String) "Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment((String) "Gears").applyUnequipEffect(player, 4);
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.75);
            Bukkit.getLogger().info("lowering damage to due archer damaged: " + event.getDamage(EntityDamageEvent.DamageModifier.BASE) + " from " + (Object) event.getDamager());
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
        if (this.isBlockedOutpostWorld((Entity) damaged)) {
            return false;
        }
        if (event.getDamager() instanceof Projectile) {
            return this.modifyDamage(damaged, event, -5.0, multiplier);
        }
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damager)) {
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

