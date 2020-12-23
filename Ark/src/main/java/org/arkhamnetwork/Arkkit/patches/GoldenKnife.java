/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class GoldenKnife
        extends ArkPlugin {
    public GoldenKnife(Plugin p) {
        super(p);
    }

    public static ItemStack getGoldenKnife() {
        ItemStack i = ItemUtils.buildItem(Material.GOLD_SWORD, (short) 0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "The Golden Knife", ChatColor.GOLD.toString() + "Vanquish the soul of anything you strike,", ChatColor.GOLD.toString() + "in exchange for this divine blade.");
        i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1000);
        i.setDurability((short) 1);
        return i;
    }

    public static boolean isGoldenKnife(ItemStack i) {
        return i.getType() == Material.GOLD_SWORD && i.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= 1000;
    }

    @Override
    public void enable() {
        this.registerListener(new GoldenKnifeListener());
    }

    @Override
    public void disable() {
    }
}

