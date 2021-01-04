/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  net.minecraft.server.v1_7_R4.NBTTagCompound
 *  net.minecraft.server.v1_7_R4.NBTTagList
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Location
 *  org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack
 *  org.bukkit.entity.EntityType
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.quests;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class QuestUtil {
    public static String convertNumberToDay(int num) {
        if (num == 1) {
            return "Sunday";
        }
        if (num == 2) {
            return "Monday";
        }
        if (num == 3) {
            return "Tuesday";
        }
        if (num == 4) {
            return "Wednesday";
        }
        if (num == 5) {
            return "Thursday";
        }
        if (num == 6) {
            return "Friday";
        }
        if (num == 7) {
            return "Saturday";
        }
        return "N/A";
    }

    public static String getEntityTypeName(EntityType type) {
        return StringUtils.capitaliseAllWords((String)type.name().replace("_", " ").toLowerCase());
    }

    public static boolean isInsideFactionClaim(Faction fac, Location location) {
        Faction at = Board.getFactionAt((FLocation)new FLocation(location));
        return at != null && at.equals((Object)fac);
    }

    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy((ItemStack)item);
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
        return CraftItemStack.asCraftMirror(nmsStack);
    }
}

