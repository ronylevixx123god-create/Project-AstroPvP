/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.EnchantmentStorageMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicOutposts.utils;

import java.util.Arrays;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static ItemStack buildItem(Material m, short metaData, String name, String ... description) {
        ItemStack is = new ItemStack(m, 1, metaData);
        ItemMeta im = is.getItemMeta();
        if (name != null) {
            im.setDisplayName(name);
        }
        if (description != null) {
            im.setLore(Arrays.asList(description));
        }
        is.setItemMeta(im);
        is.setDurability(metaData);
        return is;
    }

    public static ItemStack buildEnchantedBook(Enchantment e, int level, boolean randomLevel) {
        ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta esm = (EnchantmentStorageMeta)i.getItemMeta();
        esm.addStoredEnchant(e, randomLevel && e.getMaxLevel() > 1 ? 1 + new Random().nextInt(e.getMaxLevel()) : level, true);
        i.setItemMeta((ItemMeta)esm);
        return i;
    }

    public static ItemStack buildLootingSword(int lootingLevel) {
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD, 1);
        i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, lootingLevel);
        i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        i.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        return i;
    }
}

