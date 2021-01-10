/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicCrate.utils;

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LocalItemUtils {
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
        return is;
    }

    public static ItemStack injectLore(ItemStack i, String newLore) {
        ItemMeta im = i.getItemMeta();
        List lore = im.getLore();
        lore.add("");
        lore.add(newLore);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack changeType(ItemStack i, Material newType, short newMetadata) {
        i.setType(newType);
        i.setDurability(newMetadata);
        return i;
    }

    public static ItemStack getMysteryPlaceholder() {
        return LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "???", new String[0]);
    }

    public static ItemStack getLockedPlaceholder(Player p, Version v) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        ItemStack crateItem = CCAPI.getCosmicCrate(v, p.getName());
        crateItem.setType(Material.STAINED_GLASS_PANE);
        crateItem.setDurability((short)14);
        ItemMeta im = crateItem.getItemMeta();
        im.setDisplayName(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "LOCKED: " + v.crateName.replace("*", "").replace("COSMIC CRATE: ", ""));
        List lore = im.getLore();
        lore.set(0, (Object)ChatColor.GRAY + "Unlock at " + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com");
        im.setLore(lore);
        crateItem.setItemMeta(im);
        return crateItem;
    }

    public static ItemStack setAmount(ItemStack item, int i) {
        item.setAmount(i);
        return item;
    }
}

