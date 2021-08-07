/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.util.ArmorSlot
 *  org.arkhamnetwork.enchantments.util.ArmorUtil
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
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
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
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
import java.util.concurrent.ThreadLocalRandom;

public class YetiArmorSet
        extends ArmorSetInfo
        implements WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&b&lYeti Facemask", "&bThe savage mask of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Protection"), EnchantmentAPI.getEnchantment("Ender Shift"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Angelic"))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll(ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&b&lBloody Yeti Torso", "&bThe impermeable chestplate of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Leadership"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Blood Link"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Angelic")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll(ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&b&lFuzzy Yeti Leggings", "&bThe cozy yet monstrous legs of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Repair Guard"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Cactus")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll(ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&b&lBig-Yeti Boots", "&bBigfoot has nothing on these boots.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Repair Guard"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Tank")))));
    }

    @Override
    public List<String> getEquipDescription() {

        return Lists.newArrayList(ChatColor.WHITE + "" + ChatColor.BOLD + "YETI SET BONUS: " + ChatColor.WHITE + "+10% ATK / -10% DMG");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(event.getDamager())) {
            return false;
        }
        this.removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9);
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(event.getDamager())) {
            return false;
        }
        this.removeResistance(damager);
        if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 1, damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1);
        ItemStack item = damager.getItemInHand();
        if (event.getEntity() instanceof Player && item != null && item.getType() == Material.DIAMOND_AXE && new NBTWrapper(item).getString("cosmicType").equals("YETI_AXE")) {
            Player damaged = (Player) event.getEntity();
            int heroicArmorItems = 0;
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                if (!(Math.random() <= 0.75)) continue;
                ArmorUtil.modifyPlayerArmor(damaged, armorSlot, -1, false);
            }
            for (ItemStack armorSlot : damaged.getEquipment().getArmorContents()) {
                if (!CosmicArmorSetsAPI.isHeroicArmor(armorSlot)) continue;
                ++heroicArmorItems;
            }
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * (2.075 + (double) heroicArmorItems * 0.03));
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -2.5, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        this.modifyDamage(damager, event, 2.5, multiplier);
        return false;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        ItemStack item = new NBTWrapper(new ItemBuilder(Material.DIAMOND_AXE, CC.AquaB + "Yeti Maul", CC.Gray + "A penetrating axe used by a Yeti.", "", CC.AquaB + "YETI WEAPON BONUS", CC.Aqua + "Deal +75% Durability Damage.", CC.Aqua + "Deal +7.5% DMG to enemies", CC.Aqua + "Bypass 50% of Heroic Armor", CC.Gray + "(" + CC.GrayI + "Requires all 4 yeti items." + CC.Gray + ")").enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "YETI_AXE").build();
        this.addEnchantment("Silence", this.getMaxEnchantLevel("Silence"), item);
        this.addEnchantment("Lifesteal", this.getMaxEnchantLevel("Lifesteal"), item);
        if (Math.random() <= 0.25) {
            this.addEnchantment("Demonic Lifesteal", ThreadLocalRandom.current().nextInt(this.getMaxEnchantLevel("Demonic Lifesteal")) + 1, item);
        }
        return item;
    }
}

