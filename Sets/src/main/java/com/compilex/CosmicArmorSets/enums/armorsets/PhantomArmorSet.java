/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PhantomArmorSet
        extends ArmorSetInfo
        implements WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&c&lPhantom Hood", "&cThe fabled hood of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom me.astropvp.cratekeys.items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&c&lPhantom Shroud", "&cThe legendary shroud of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom me.astropvp.cratekeys.items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Overload"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&c&lPhantom Robeset", "&cThe demonic robe of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom me.astropvp.cratekeys.items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Cactus"))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&c&lPhantom Sandals", "&cThe silent sandals of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom me.astropvp.cratekeys.items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"))));
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList((ChatColor.RED + "" + ChatColor.BOLD + "PHANTOM SET BONUS: " + ChatColor.RED + "+25% DMG"));
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        this.removeResistance(damaged);
        return false;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        Material type;
        this.removeResistance(damager);
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 3, damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.25);
        ItemStack handItem = damager.getItemInHand();
        if (handItem != null && (type = handItem.getType()) != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && new NBTWrapper(handItem).getString("cosmicType").equals("PHANTOM_SWORD")) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1);
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int applied, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        return this.modifyDamage(damager, event, 5.0, applied);
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        return new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD, CC.RedB + "Phantom Scythe", CC.Gray + "An eerie blade designed to", CC.Gray + "cut through the enemies.", "", CC.RedB + "PHANTOM WEAPON BONUS", CC.Red + "Deal +10% damage to all enemies.", CC.Gray + "(" + CC.GrayI + "Requires all 4 phantom me.astropvp.cratekeys.items." + CC.Gray + ")").enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "PHANTOM_SWORD").build();
    }
}

