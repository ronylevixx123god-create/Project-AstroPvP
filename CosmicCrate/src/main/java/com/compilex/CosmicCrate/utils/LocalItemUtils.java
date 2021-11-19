// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.utils;

import com.compilex.CosmicCrate.api.CCAPI;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class LocalItemUtils
{
    public static ItemStack buildItem(final Material m, final short metaData, final String name, final String... description) {
        final ItemStack is = new ItemStack(m, 1, metaData);
        final ItemMeta im = is.getItemMeta();
        if (name != null) {
            im.setDisplayName(name);
        }
        if (description != null) {
            im.setLore((List)Arrays.asList(description));
        }
        is.setItemMeta(im);
        return is;
    }
    
    public static ItemStack getMysteryPlaceholder() {
        return buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "???", new String[0]);
    }


    public static ItemStack changeType(ItemStack i, Material newType, short newMetadata) {
        i.setType(newType);
        i.setDurability(newMetadata);
        return i;
    }


    public static ItemStack getLockedPlaceholder(Player p, String s) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        ItemStack crateItem = CCAPI.getCosmicCrate(s);
        crateItem.setType(Material.STAINED_GLASS_PANE);
        crateItem.setDurability((short) 14);
        ItemMeta im = crateItem.getItemMeta();
        im.setDisplayName(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "LOCKED: " + s.replace("*", "").replace("COSMIC CRATE: ", ""));
        List<String> lore = im.getLore();
        lore.set(0, ChatColor.GRAY + "Unlock at " + ChatColor.UNDERLINE + "buy.cosmicpvp.com");
        im.setLore(lore);
        crateItem.setItemMeta(im);
        return crateItem;
    }

}
