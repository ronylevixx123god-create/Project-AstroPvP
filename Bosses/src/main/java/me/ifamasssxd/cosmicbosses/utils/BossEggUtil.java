/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.utils;

import me.ifamasssxd.cosmicbosses.boss.type.Boss;
import me.ifamasssxd.cosmicbosses.boss.type.Bosses;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BossEggUtil {
    public static Boss getBoss(ItemStack egg) {
        if (egg.getType() == Material.getMaterial(383) && egg.hasItemMeta() && egg.getItemMeta().hasDisplayName()) {
            String egg_name = egg.getItemMeta().getDisplayName();
            if (!egg.getItemMeta().hasLore() || !egg.getItemMeta().getLore().get(0).contains("Be careful")) {
                return null;
            }
            String o_egg_name = egg_name;
            egg_name = egg_name.split(" ")[egg_name.split(" ").length - 1];
            Boss b = null;
            if (egg_name.contains("Goblin") && o_egg_name.startsWith(ChatColor.AQUA.toString())) {
                b = Bosses.PIG_ZOMBIE.b;
            }
            if (egg_name.contains("King") && o_egg_name.startsWith(ChatColor.GOLD.toString())) {
                b = Bosses.HEADLESS_KING.b;
            }
            if (b == null) {
                b = Bosses.valueOf((String)egg_name.toUpperCase()).b;
            }
            return b;
        }
        return null;
    }
}

