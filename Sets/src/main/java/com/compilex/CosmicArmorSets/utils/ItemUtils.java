/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.me.astropvp.cratekeys.inventory.meta.ItemMeta
 */
package com.compilex.CosmicArmorSets.utils;

import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemUtils {
    public static ItemStack buildItem(Material m, short metaData, String name, String... description) {
        ItemStack is = new ItemStack(m, 1, metaData);
        ItemMeta im = is.getItemMeta();
        if (name != null) {
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        if (description != null) {
            ArrayList lore = Lists.newArrayList();
            for (String desc : description) {
                if (desc == null) continue;
                lore.add(ChatColor.translateAlternateColorCodes('&', desc));
            }
            im.setLore(lore);
        }
        is.setItemMeta(im);
        return is;
    }

    public static int getRandomArmorSetEnchantLevel(String enchantment) {
        CustomEnchantment ce = EnchantmentAPI.getEnchantment(enchantment);
        if (ce.heroicEnchantment) {
            return 1 + new Random().nextInt(ce.getMaxLevel());
        }
        return Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + new Random().nextInt(3));
    }

    public static ItemStack applyCustomEnchantments(ItemStack i, List<CustomEnchantment> enchants) {
        for (CustomEnchantment ce : enchants) {
            if (ce.heroicEnchantment) {
                ce.nonHeroicVersion.addToItem(i, ce.nonHeroicVersion.getMaxLevel());
                ce.addToItem(i, 1 + new Random().nextInt(ce.getMaxLevel()));
                continue;
            }
            ce.addToItem(i, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + new Random().nextInt(3)));
        }
        i.addEnchantment(Enchantment.DURABILITY, 3);
        i.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        return i;
    }
}

