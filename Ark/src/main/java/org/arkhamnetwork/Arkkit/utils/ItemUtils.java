/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package org.arkhamnetwork.Arkkit.utils;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static ItemStack buildItem(Material m, short metaData, String name, String ... description) {
        ItemStack is = new ItemStack(m, 1, metaData);
        ItemMeta im = is.getItemMeta();
        if (name != null) {
            im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        }
        if (description != null) {
            ArrayList lore = Lists.newArrayList();
            for (String desc : description) {
                if (desc == null) continue;
                lore.add(ChatColor.translateAlternateColorCodes((char)'&', (String)desc));
            }
            im.setLore((List)lore);
        }
        is.setItemMeta(im);
        return is;
    }
}

