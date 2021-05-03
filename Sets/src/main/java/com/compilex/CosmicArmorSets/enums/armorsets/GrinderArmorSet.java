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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GrinderArmorSet extends ArmorSetInfo
        implements WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&8&lGrinder Helmet", "&8The Old School Grinder Helmet.", "", "&8&lGRINDER SET BONUS", "&8150% Extra Exp.", "&7(&oRequires all 4 grinder items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&8&lGrinder Chestplate", "&8The Old School Grinder Chestplate.", "", "&8&lGRINDER SET BONUS", "&8150% Extra Exp.", "&7(&oRequires all 4 grinder items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&8&lGrinder Leggings", "&8The Old School Grinder Leggings.", "", "&8&lGRINDER SET BONUS", "&8150% Extra Exp.", "&7(&oRequires all 4 grinder items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0,  "&8&lGrinder Chestplate", "&8The Old School Grinder Boots.", "", "&8&lGRINDER SET BONUS", "&8150% Extra Exp.", "&7(&oRequires all 4 grinder items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList((ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "GRINDER SET BONUS: " + ChatColor.DARK_GRAY + "150% Extra EXP"));
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        //this.removeResistance(damaged);
        return false;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {

        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int applied, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        return new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD, CC.RedB + "Phantom Scythe", CC.Gray + "An eerie blade designed to", CC.Gray + "cut through the enemies.", "", CC.RedB + "PHANTOM WEAPON BONUS", CC.Red + "Deal +10% damage to all enemies.", CC.Gray + "(" + CC.GrayI + "Requires all 4 phantom items." + CC.Gray + ")").enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "PHANTOM_SWORD").build();
    }
}

