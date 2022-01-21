/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  net.minecraft.server.v1_7_R4.NBTTagCompound
 *  net.minecraft.server.v1_7_R4.NBTTagList
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_7_R4.me.astropvp.cratekeys.inventory.CraftItemStack
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package net.astropvp.core.itemfilter.utils;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class Utils {
    public static org.bukkit.inventory.ItemStack addGlow(org.bukkit.inventory.ItemStack item) {
        if (item.getType() == Material.AIR) {
            return item;
        }
        ItemStack nmsStack = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack)item);
        if (nmsStack == null) {
            return item;
        }
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) {
            tag = nmsStack.getTag();
        }
        NBTTagList ench = new NBTTagList();
        tag.set("ench", (NBTBase)ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror((ItemStack)nmsStack);
    }

    public static int getInventorySize(int size) {
        for (int i = 9; i < 54; i += 9) {
            if (size > i) continue;
            return i;
        }
        return 54;
    }

    public static String getItemName(org.bukkit.inventory.ItemStack item) {
        if (item.getType() == Material.GREEN_RECORD) {
            return "Discs";
        }
        if (item.getType() == Material.DRAGON_EGG) {
            return "All Other Blocks";
        }
        if (item.getType() == Material.MOB_SPAWNER) {
            return "Monster Spawners";
        }
        if (item.getType() == Material.POTION) {
            return "Potions";
        }
        if (item.getType() == Material.MONSTER_EGG) {
            return "Spawner Eggs";
        }
        if (item.getType() == Material.DIODE) {
            return "Redstone Repeater";
        }
        if (item.getType() == Material.REDSTONE_TORCH_ON) {
            return "Redstone Torch";
        }
        if (item.getType() == Material.INK_SACK && item.getDurability() == 4) {
            return "Lapis Lazuli";
        }
        if (item.getType() == Material.PISTON_BASE) {
            return "Piston";
        }
        if (item.getType() == Material.PISTON_STICKY_BASE) {
            return "Sticky Piston";
        }
        if (item.getType() == Material.REDSTONE_LAMP_OFF) {
            return "Redstone Lamp";
        }
        if (item.getType() == Material.SKULL_ITEM) {
            return "Player Heads";
        }
        return StringUtils.capitaliseAllWords((String)item.getType().name().toLowerCase().replace("_", " "));
    }
}

