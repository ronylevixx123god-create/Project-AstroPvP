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
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&b&lYeti Facemask", "&bThe savage mask of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Drunk"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Implants"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Protection"), EnchantmentAPI.getEnchantment((String) "Ender Shift"), EnchantmentAPI.getEnchantment((String) "Arrow Deflect"), EnchantmentAPI.getEnchantment((String) "Death God"), EnchantmentAPI.getEnchantment((String) "Angelic")})));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll((ItemStack) ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&b&lBloody Yeti Torso", "&bThe impermeable chestplate of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Godly Overload"), EnchantmentAPI.getEnchantment((String) "Blood Lust"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Leadership"), EnchantmentAPI.getEnchantment((String) "Spirit Link"), EnchantmentAPI.getEnchantment((String) "Aegis"), EnchantmentAPI.getEnchantment((String) "Blood Link"), EnchantmentAPI.getEnchantment((String) "Guardians"), EnchantmentAPI.getEnchantment((String) "Angelic")}))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll((ItemStack) ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&b&lFuzzy Yeti Leggings", "&bThe cozy yet monstrous legs of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Clarity"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Obsidianshield"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Repair Guard"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Lucky"), EnchantmentAPI.getEnchantment((String) "Cactus")}))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll((ItemStack) ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&b&lBig-Yeti Boots", "&bBigfoot has nothing on these boots.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"), Arrays.asList(new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Gears"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Springs"), EnchantmentAPI.getEnchantment((String) "Rocket Escape"), EnchantmentAPI.getEnchantment((String) "Ender Walker"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Metaphysical"), EnchantmentAPI.getEnchantment((String) "Repair Guard"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Tank")}))));
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList((Object[]) new String[]{(Object) ChatColor.WHITE + "" + (Object) ChatColor.BOLD + "YETI SET BONUS: " + (Object) ChatColor.WHITE + "+10% ATK / -10% DMG"});
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
                ArmorUtil.modifyPlayerArmor((Player) damaged, (ArmorSlot) armorSlot, (int) -1, (boolean) false);
            }
            for (ArmorSlot armorSlot : damaged.getEquipment().getArmorContents()) {
                if (!CosmicArmorSetsAPI.isHeroicArmor((ItemStack) armorSlot)) continue;
                ++heroicArmorItems;
            }
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * (2.075 + (double) heroicArmorItems * 0.03));
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -2.5, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        this.modifyDamage(damager, event, 2.5, multiplier);
        return false;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        ItemStack item = new NBTWrapper(new ItemBuilder(Material.DIAMOND_AXE, CC.AquaB + "Yeti Maul", new String[]{CC.Gray + "A penetrating axe used by a Yeti.", "", CC.AquaB + "YETI WEAPON BONUS", CC.Aqua + "Deal +75% Durability Damage.", CC.Aqua + "Deal +7.5% DMG to enemies", CC.Aqua + "Bypass 50% of Heroic Armor", CC.Gray + "(" + CC.GrayI + "Requires all 4 yeti items." + CC.Gray + ")"}).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "YETI_AXE").build();
        this.addEnchantment("Silence", this.getMaxEnchantLevel("Silence"), item);
        this.addEnchantment("Lifesteal", this.getMaxEnchantLevel("Lifesteal"), item);
        if (Math.random() <= 0.25) {
            this.addEnchantment("Demonic Lifesteal", ThreadLocalRandom.current().nextInt(this.getMaxEnchantLevel("Demonic Lifesteal")) + 1, item);
        }
        return item;
    }
}

