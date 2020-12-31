/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Color
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 */
package com.compilex.CosmicArmorSets.enums;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbilityArmorSetInfo
        extends ArmorSetInfo {
    public abstract List<String> getAbilityArmorLore();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getHelmet(null, applyTransmog, false);
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getChestplate(null, applyTransmog, false);
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getLeggings(null, applyTransmog, false);
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getBoots(null, applyTransmog, false);
    }

    public abstract ItemStack getHelmet(Player var1, boolean var2, boolean var3);

    public abstract ItemStack getChestplate(Player var1, boolean var2, boolean var3);

    public abstract ItemStack getLeggings(Player var1, boolean var2, boolean var3);

    public abstract ItemStack getBoots(Player var1, boolean var2, boolean var3);

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        return false;
    }

    protected ItemStack setLeatherColor(ItemStack item, Color color) {
        ItemMeta im = item.getItemMeta();
        if (im instanceof LeatherArmorMeta) {
            LeatherArmorMeta lm = (LeatherArmorMeta) im;
            lm.setColor(color);
            item.setItemMeta((ItemMeta) lm);
        }
        return item;
    }

    public ItemStack addMaxedEnchant(ItemStack item, String name) {
        CustomEnchantment enchant = EnchantmentAPI.getEnchantment((String) name);
        if (enchant != null) {
            enchant.addToItem(item, enchant.getMaxLevel());
        }
        return item;
    }

    public ItemStack addNearlyMaxedEnchants(ItemStack item, String... enchantNames) {
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        for (String enchant : enchantNames) {
            this.addNearlyMaxed(item, enchant);
        }
        return item;
    }

    public ItemStack addNearlyMaxed(ItemStack item, String enchantName) {
        CustomEnchantment enchantment = EnchantmentAPI.getEnchantment((String) enchantName);
        if (enchantment != null) {
            int maxLevel;
            int level = enchantment.heroicEnchantment ? 1 + ThreadLocalRandom.current().nextInt(maxLevel) : (maxLevel == 1 ? maxLevel : (maxLevel == 2 ? maxLevel - ThreadLocalRandom.current().nextInt(2) : (maxLevel == 3 ? maxLevel - ThreadLocalRandom.current().nextInt(3) : maxLevel - ThreadLocalRandom.current().nextInt(4))));
            if (level == (maxLevel = enchantment.getMaxLevel()) && level > 1 && Math.random() < 0.25) {
                --level;
            }
            if (enchantment.heroicEnchantment && enchantment.nonHeroicVersion != null) {
                enchantment.nonHeroicVersion.addToItem(item, enchantment.nonHeroicVersion.getMaxLevel());
            }
            enchantment.addToItem(item, level);
        }
        return item;
    }
}

