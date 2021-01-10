/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicDungeons.utils;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.List;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PortalTrinketUtils {
    public static boolean portalHasTrinket(ItemStack item, String trinket) {
        if (!DungeonsAPI.get().isDungeonPortal(item)) {
            return false;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("conaxData");
        return data.getString("dungeonTrinket").equals(trinket);
    }

    public static boolean portalHasTrinket(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("conaxData");
        return data.hasKey("dungeonTrinket");
    }

    public static void applyTrinket(ItemStack item, String trinket, String displayName) {
        if (!DungeonsAPI.get().isDungeonPortal(item) || PortalTrinketUtils.portalHasTrinket(item, trinket)) {
            return;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("conaxData");
        data.setString("dungeonTrinket", trinket);
        wrapper.setNBTBase("conaxData", (NBTBase)data.get());
        item = wrapper.build();
        ItemMeta meta = item.getItemMeta();
        List lore = meta.getLore();
        lore.add(CC.GoldB + "TRINKET: " + CC.Gold + displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}

