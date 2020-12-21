/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.blocks.BlockType
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BlockType;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemStack;

public class BukkitUtil {
    private BukkitUtil() {
    }

    public static BlockVector toVector(Block block) {
        return new BlockVector(block.getX(), block.getY(), block.getZ());
    }

    public static Vector toVector(Location loc) {
        return new Vector(loc.getX(), loc.getY(), loc.getZ());
    }

    public static Vector toVector(org.bukkit.util.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static Location toLocation(World world, Vector vec) {
        return new Location(world, vec.getX(), vec.getY(), vec.getZ());
    }

    @Deprecated
    public static Player matchSinglePlayer(Server server, String name) {
        List<Player> players = server.matchPlayer(name);
        if (players.size() == 0) {
            return null;
        }
        return players.get(0);
    }

    @Deprecated
    public static void dropSign(Block block) {
        block.setTypeId(0);
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(323, 1));
    }

    public static void setBlockToWater(World world, int ox, int oy, int oz) {
        Block block = world.getBlockAt(ox, oy, oz);
        int id = block.getTypeId();
        if (id == 0) {
            block.setTypeId(8);
        }
    }

    public static boolean isBlockWater(World world, int ox, int oy, int oz) {
        Block block = world.getBlockAt(ox, oy, oz);
        int id = block.getTypeId();
        return id == 8 || id == 9;
    }

    public static boolean isWaterPotion(ItemStack item) {
        return (item.getDurability() & 0x3F) == 0;
    }

    public static int getPotionEffectBits(ItemStack item) {
        return item.getDurability() & 0x3F;
    }

    public static void findFreePosition(Player player) {
        int y;
        Location loc = player.getLocation();
        int x = loc.getBlockX();
        int origY = y = Math.max(0, loc.getBlockY());
        int z = loc.getBlockZ();
        World world = player.getWorld();
        int free = 0;
        while (y <= world.getMaxHeight() + 1) {
            free = BlockType.canPassThrough((int)world.getBlockTypeIdAt(x, y, z)) ? (int)((byte)(free + 1)) : 0;
            if (free == 2) {
                if (y - 1 != origY || y == 1) {
                    loc.setX((double)x + 0.5);
                    loc.setY(y);
                    loc.setZ((double)z + 0.5);
                    if (y <= 2 && world.getBlockAt(x, 0, z).getTypeId() == 0) {
                        world.getBlockAt(x, 0, z).setTypeId(20);
                        loc.setY(2.0);
                    }
                    player.setFallDistance(0.0f);
                    player.teleport(loc);
                }
                return;
            }
            ++y;
        }
    }

    public static String replaceColorMacros(String str) {
        str = str.replace("&r", ChatColor.RED.toString());
        str = str.replace("&R", ChatColor.DARK_RED.toString());
        str = str.replace("&y", ChatColor.YELLOW.toString());
        str = str.replace("&Y", ChatColor.GOLD.toString());
        str = str.replace("&g", ChatColor.GREEN.toString());
        str = str.replace("&G", ChatColor.DARK_GREEN.toString());
        str = str.replace("&c", ChatColor.AQUA.toString());
        str = str.replace("&C", ChatColor.DARK_AQUA.toString());
        str = str.replace("&b", ChatColor.BLUE.toString());
        str = str.replace("&B", ChatColor.DARK_BLUE.toString());
        str = str.replace("&p", ChatColor.LIGHT_PURPLE.toString());
        str = str.replace("&P", ChatColor.DARK_PURPLE.toString());
        str = str.replace("&0", ChatColor.BLACK.toString());
        str = str.replace("&1", ChatColor.DARK_GRAY.toString());
        str = str.replace("&2", ChatColor.GRAY.toString());
        str = str.replace("&w", ChatColor.WHITE.toString());
        str = str.replace("&k", ChatColor.MAGIC.toString());
        str = str.replace("&l", ChatColor.BOLD.toString());
        str = str.replace("&m", ChatColor.STRIKETHROUGH.toString());
        str = str.replace("&n", ChatColor.UNDERLINE.toString());
        str = str.replace("&o", ChatColor.ITALIC.toString());
        str = str.replace("&x", ChatColor.RESET.toString());
        return str;
    }

    public static boolean isIntensiveEntity(Entity entity) {
        return entity instanceof Item || entity instanceof TNTPrimed || entity instanceof ExperienceOrb || entity instanceof FallingBlock || entity instanceof LivingEntity && !(entity instanceof Tameable) && !(entity instanceof Player);
    }

    public static <T extends Enum<T>> T tryEnum(Class<T> enumType, String ... values) {
        for (String val : values) {
            try {
                return Enum.valueOf(enumType, val);
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
        }
        return null;
    }
}

