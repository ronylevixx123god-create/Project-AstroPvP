/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicbosses.utils;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BossEggUtils {
    private static HashMap<BossType, Boss> deadInstances = new HashMap();

    public static ItemStack getBossEgg(BossType type) {
        Boss boss = deadInstances.containsKey((Object)type) ? deadInstances.get((Object)type) : type.createInstance(null);
        deadInstances.put(type, boss);
        for (BossType bossType : BossType.values()) {
            if (!boss.getClass().isAssignableFrom(bossType.getBossClass())) continue;
            ArrayList lore = Lists.newArrayList();
            lore.add((Object)ChatColor.GRAY + "This monster egg contains a");
            lore.add((Object)ChatColor.RED + ChatColor.UNDERLINE.toString() + "dangerous" + (Object)ChatColor.GRAY + " warzone boss");
            lore.add("");
            lore.add(boss.getColor(true) + "LORE");
            lore.addAll(BossEggUtils.formatLore(type.getLore(), ChatColor.GRAY.toString() + (Object)ChatColor.ITALIC));
            lore.add("");
            lore.add(boss.getColor(true) + "DIFFICULTY");
            lore.add(boss.getColor(false) + boss.getDifficulty().getName());
            ItemStack is = ItemBuilder.buildItem(Material.MONSTER_EGG, bossType.getEggData(), bossType.getEggName(), lore);
            return is;
        }
        return null;
    }

    public static List<String> formatLore(String s, String color) {
        ArrayList<String> lore_l = new ArrayList<String>();
        if (s == null) {
            return lore_l;
        }
        if (s.length() <= 38) {
            lore_l.add(color + s);
            return lore_l;
        }
        if (s.contains(" ")) {
            String line = color;
            for (String word : s.split(" ")) {
                if (line.length() - 1 + word.length() <= 38) {
                    line = line + word + " ";
                    continue;
                }
                if (line.endsWith(" ")) {
                    line = line.substring(0, line.length() - 1);
                }
                lore_l.add(line);
                line = color + word + " ";
            }
            if (ChatColor.stripColor((String)line).length() > 0) {
                lore_l.add(line);
            }
        } else {
            lore_l.add(s);
            return lore_l;
        }
        return lore_l;
    }

    public static Boss getBossInstance(BossType boss) {
        Boss retr = null;
        if (deadInstances.containsKey((Object)boss)) {
            retr = deadInstances.get((Object)boss);
        } else {
            retr = boss.createInstance(null);
            deadInstances.put(boss, retr);
        }
        return retr;
    }

    public static BossType getBossType(ItemStack is) {
        if (is == null || !is.hasItemMeta() || !is.getItemMeta().hasDisplayName()) {
            return null;
        }
        for (BossType bossType : BossType.values()) {
            if (bossType.getEggData() != is.getDurability() || !is.getItemMeta().getDisplayName().equals(bossType.getEggName())) continue;
            return bossType;
        }
        return null;
    }
}

