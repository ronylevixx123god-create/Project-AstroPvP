/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.worldedit.blocks.BlockType
 *  com.sk89q.worldedit.blocks.ClothColor
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.commandbook.util;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.blocks.ClothColor;
import com.sk89q.worldedit.blocks.ItemType;
import java.util.Random;
import org.bukkit.DyeColor;

public class ItemUtil {
    public static String toItemName(int id) {
        ItemType type = ItemType.fromID((int)id);
        if (type != null) {
            return type.getName();
        }
        return "#" + id;
    }

    public static int matchItemData(int id, String filter) throws CommandException {
        try {
            return Integer.parseInt(filter);
        }
        catch (NumberFormatException ignored) {
            switch (id) {
                case 5: {
                    if (filter.equalsIgnoreCase("redwood")) {
                        return 1;
                    }
                    if (filter.equalsIgnoreCase("birch")) {
                        return 2;
                    }
                    throw new CommandException("Unknown wood type name of '" + filter + "'.");
                }
                case 43: 
                case 44: {
                    BlockType dataType = BlockType.lookup((String)filter);
                    if (dataType != null) {
                        if (dataType == BlockType.STONE) {
                            return 0;
                        }
                        if (dataType == BlockType.SANDSTONE) {
                            return 1;
                        }
                        if (dataType == BlockType.WOOD) {
                            return 2;
                        }
                        if (dataType == BlockType.COBBLESTONE) {
                            return 3;
                        }
                        throw new CommandException("Invalid slab material of '" + filter + "'.");
                    }
                    throw new CommandException("Unknown slab material of '" + filter + "'.");
                }
                case 35: {
                    ClothColor col = ClothColor.lookup((String)filter);
                    if (col != null) {
                        return col.getID();
                    }
                    throw new CommandException("Unknown wool color name of '" + filter + "'.");
                }
                case 351: {
                    ClothColor dyeCol = ClothColor.lookup((String)filter);
                    if (dyeCol != null) {
                        return 15 - dyeCol.getID();
                    }
                    throw new CommandException("Unknown dye color name of '" + filter + "'.");
                }
            }
            throw new CommandException("Invalid data value of '" + filter + "'.");
        }
    }

    public static DyeColor matchDyeColor(String filter) throws CommandException {
        if (filter.equalsIgnoreCase("random")) {
            return DyeColor.getByData((byte)new Random().nextInt(15));
        }
        try {
            DyeColor match = DyeColor.valueOf(filter.toUpperCase());
            if (match != null) {
                return match;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        throw new CommandException("Unknown dye color name of '" + filter + "'.");
    }
}

