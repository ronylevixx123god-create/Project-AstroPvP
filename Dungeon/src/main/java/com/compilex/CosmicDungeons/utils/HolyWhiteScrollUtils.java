/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_8_R3.NBTBase
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicDungeons.utils;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HolyWhiteScrollUtils {
    private static String corruptLoreLine = CC.RedB + "CORRUPT (" + CC.Gray + "cannot be protected" + CC.RedB + ")";

    public static int getHolyScrollsApplied(NBTWrapper wrapper) {
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("cosmicData");
        return data.getInt("holyScrollsApplied");
    }

    public static ItemStack applyCorruptionToItem(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("cosmicData");
        builder.setBoolean("corrupted", Boolean.valueOf(true));
        wrapper.setNBTBase("cosmicData", (NBTBase)builder.get());
        item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List lore = im.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }
        if (!lore.contains(corruptLoreLine)) {
            lore.add(corruptLoreLine);
        }
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static NBTWrapper setHolyWhiteScrollsApplied(NBTWrapper wrapper, int applied) {
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("cosmicData");
        data.setInt("holyScrollsApplied", Integer.valueOf(applied));
        wrapper.setNBTBase("cosmicData", (NBTBase)data.get());
        return wrapper;
    }

    public static void applyHolyWhitescroll(ItemStack i, int applied, int max, boolean fart) {
        List<String> lore = i.getItemMeta().getLore();
        LinkedList<String> newLore = new LinkedList<String>();
        boolean found = false;
        String newCorrupt = CC.RedB + "SEMI CORRUPT (" + CC.Gray + applied + CC.Red + "/" + CC.Gray + max + " holy whitescrolls applied" + CC.RedB + ")";
        for (String s : lore) {
            if (s.equals((Object)ChatColor.WHITE + "" + (Object)ChatColor.BOLD + "PROTECTED") && fart) {
                newLore.add(ChatColor.WHITE.toString() + (Object)ChatColor.BOLD + "PROTECTED (" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "*HOLY*" + (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + ")");
                continue;
            }
            if (s.startsWith(CC.RedB + "SEMI CORRUPT")) {
                newLore.add(newCorrupt);
                found = true;
                continue;
            }
            newLore.add(s);
        }
        if (!found) {
            newLore.add(newCorrupt);
        }
        newLore.remove(corruptLoreLine);
        ItemMeta im = i.getItemMeta();
        im.setLore(newLore);
        i.setItemMeta(im);
    }

    public static ItemStack removeHolyWhitescroll(ItemStack i) {
        NBTWrapper wrapper = new NBTWrapper(i);
        int applied = HolyWhiteScrollUtils.getHolyScrollsApplied(wrapper);
        boolean turnCorrupted = applied >= HolyWhiteScrollUtils.getMaxHolyAllowed(i);
        List<String> lore = i.getItemMeta().getLore();
        LinkedList<String> newLore = new LinkedList<String>();
        for (String s : lore) {
            if (s.equals(CC.WhiteB + "PROTECTED (" + CC.GoldB + "*HOLY*" + CC.WhiteB + ")")) {
                if (!turnCorrupted || newLore.contains(corruptLoreLine)) continue;
                newLore.add(corruptLoreLine);
                continue;
            }
            if (turnCorrupted && s.startsWith(CC.RedB + "SEMI CORRUPT")) continue;
            newLore.add(s);
        }
        if (turnCorrupted && !newLore.contains(corruptLoreLine)) {
            newLore.add(corruptLoreLine);
        }
        ItemMeta im = i.getItemMeta();
        im.setLore(newLore);
        i.setItemMeta(im);
        if (turnCorrupted) {
            i = HolyWhiteScrollUtils.applyCorruptionToItem(i);
        }
        return i;
    }

    public static boolean itemHasHolyWhitescroll(ItemStack i) {
        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
            for (String s : i.getItemMeta().getLore()) {
                if (!s.equals(ChatColor.WHITE.toString() + (Object)ChatColor.BOLD + "PROTECTED (" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "*HOLY*" + (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + ")")) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isHolyWhiteScroll(ItemStack i) {
        return CosmicDungeonsPlugin.get().getHolyWhitescrollItem().isItem(i);
    }

    public static boolean isOldHolyWhiteScroll(ItemStack i) {
        return i != null && i.hasItemMeta() && i.getType() == Material.PAPER && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals(DungeonsRewardsAPI.getHolyWhiteScroll().getItemMeta().getDisplayName()) && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).equals(DungeonsRewardsAPI.getHolyWhiteScroll().getItemMeta().getLore().get(0));
    }

    public static int getMaxHolyAllowed(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        return cosmicData.hasKey("holyWater") ? 4 : 3;
    }
}

