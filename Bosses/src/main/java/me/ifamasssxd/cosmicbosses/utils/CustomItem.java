/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.ifamasssxd.cosmicbosses.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
    ItemStack is;

    public CustomItem(ItemStack is) {
        this.is = is;
    }

    public CustomItem addEnchant(Enchantment enchant, int level) {
        this.is.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemStack build() {
        return this.is;
    }

    public CustomItem setUnbreakable(boolean unbreak) {
        ItemMeta im = this.is.getItemMeta();
        im.spigot().setUnbreakable(unbreak);
        this.is.setItemMeta(im);
        return this;
    }
}

