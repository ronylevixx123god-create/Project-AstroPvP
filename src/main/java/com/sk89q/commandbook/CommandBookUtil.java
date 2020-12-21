/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.worldedit.blocks.BlockType
 */
package com.sk89q.commandbook;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.InventoryComponent;
import com.sk89q.commandbook.util.ItemUtil;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.blocks.BlockType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CommandBookUtil {
    public static String replaceColorMacros(String str) {
        str = str.replace("`r", ChatColor.RED.toString());
        str = str.replace("`R", ChatColor.DARK_RED.toString());
        str = str.replace("`y", ChatColor.YELLOW.toString());
        str = str.replace("`Y", ChatColor.GOLD.toString());
        str = str.replace("`g", ChatColor.GREEN.toString());
        str = str.replace("`G", ChatColor.DARK_GREEN.toString());
        str = str.replace("`c", ChatColor.AQUA.toString());
        str = str.replace("`C", ChatColor.DARK_AQUA.toString());
        str = str.replace("`b", ChatColor.BLUE.toString());
        str = str.replace("`B", ChatColor.DARK_BLUE.toString());
        str = str.replace("`p", ChatColor.LIGHT_PURPLE.toString());
        str = str.replace("`P", ChatColor.DARK_PURPLE.toString());
        str = str.replace("`0", ChatColor.BLACK.toString());
        str = str.replace("`1", ChatColor.DARK_GRAY.toString());
        str = str.replace("`2", ChatColor.GRAY.toString());
        str = str.replace("`w", ChatColor.WHITE.toString());
        str = str.replace("`k", ChatColor.MAGIC.toString());
        try {
            str = str.replace("`l", ChatColor.BOLD.toString());
            str = str.replace("`m", ChatColor.STRIKETHROUGH.toString());
            str = str.replace("`n", ChatColor.UNDERLINE.toString());
            str = str.replace("`o", ChatColor.ITALIC.toString());
            str = str.replace("`x", ChatColor.RESET.toString());
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        return str;
    }

    public static String getTimeString(long time) {
        int hours = (int)((time / 1000L + 8L) % 24L);
        int minutes = (int)(60L * (time % 1000L) / 1000L);
        return String.format("%02d:%02d (%d:%02d %s)", hours, minutes, hours % 12 == 0 ? 12 : hours % 12, minutes, hours < 12 ? "am" : "pm");
    }

    public static String getOnlineList(Player[] online) {
        return CommandBookUtil.getOnlineList(online, null);
    }

    public static String getOnlineList(Player[] online, ChatColor color) {
        StringBuilder out = new StringBuilder();
        boolean first = true;
        for (Player player : online) {
            if (!first) {
                out.append(", ");
            }
            if (CommandBook.inst().useDisplayNames) {
                out.append(player.getDisplayName());
            } else {
                out.append(player.getName());
            }
            if (color != null) {
                out.append((Object)color);
            }
            first = false;
        }
        return out.toString();
    }

    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90.0f) % 360.0f;
        if (rot < 0.0) {
            rot += 360.0;
        }
        return CommandBookUtil.getDirection(rot);
    }

    private static String getDirection(double rot) {
        if (0.0 <= rot && rot < 22.5) {
            return "North";
        }
        if (22.5 <= rot && rot < 67.5) {
            return "Northeast";
        }
        if (67.5 <= rot && rot < 112.5) {
            return "East";
        }
        if (112.5 <= rot && rot < 157.5) {
            return "Southeast";
        }
        if (157.5 <= rot && rot < 202.5) {
            return "South";
        }
        if (202.5 <= rot && rot < 247.5) {
            return "Southwest";
        }
        if (247.5 <= rot && rot < 292.5) {
            return "West";
        }
        if (292.5 <= rot && rot < 337.5) {
            return "Northwest";
        }
        if (337.5 <= rot && rot < 360.0) {
            return "North";
        }
        return null;
    }

    public static void giveItem(CommandSender sender, ItemStack item, int amt, Iterable<Player> targets, InventoryComponent component, boolean drop, boolean overrideStackSize) throws CommandException {
        boolean included = false;
        int maxStackSize = overrideStackSize ? 64 : item.getType().getMaxStackSize();
        component.checkAllowedItem(sender, item.getTypeId(), item.getDurability());
        if (amt == 0 || amt < -1) {
            throw new CommandException("Invalid item amount!");
        }
        if (amt == -1) {
            CommandBook.inst().checkPermission(sender, "commandbook.give.infinite");
        } else if (overrideStackSize) {
            CommandBook.inst().checkPermission(sender, "commandbook.override.maxstacksize");
        } else if (amt > maxStackSize * 5) {
            if (!CommandBook.inst().hasPermission(sender, "commandbook.give.stacks.unlimited")) {
                throw new CommandException("More than 5 stacks is too excessive.");
            }
        } else if (amt > maxStackSize) {
            CommandBook.inst().checkPermission(sender, "commandbook.give.stacks");
        }
        if (amt > 2240 && !drop) {
            amt = 2240;
        }
        String amtText = amt == -1 ? "an infinite stack of" : String.valueOf(amt);
        for (Player player : targets) {
            int left = amt;
            while (left > 0 || amt == -1) {
                int givenAmt = Math.min(maxStackSize, left);
                item.setAmount(givenAmt);
                left -= givenAmt;
                if (drop) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                } else {
                    player.getInventory().addItem(item);
                }
                if (amt != -1) continue;
                break;
            }
            player.updateInventory();
            if (player.equals(sender)) {
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "You've been given " + amtText + " " + ItemUtil.toItemName(item.getTypeId()) + ".");
                included = true;
                continue;
            }
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Given from " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW) + ": " + amtText + " " + ItemUtil.toItemName(item.getTypeId()) + ".");
        }
        if (!included) {
            sender.sendMessage(ChatColor.YELLOW.toString() + amtText + " " + ItemUtil.toItemName(item.getTypeId()) + " has been given.");
        }
    }

    public static void takeItem(CommandSender sender, ItemStack item, int amt, Player target) throws CommandException {
        if (amt <= 0) {
            throw new CommandException("Invalid item amount!");
        }
        item.setAmount(amt);
        if (target.getInventory().contains(item.getTypeId())) {
            target.getInventory().removeItem(item);
            target.sendMessage((Object)((Object)ChatColor.YELLOW) + "Taken from " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW) + ": " + amt + " " + ItemUtil.toItemName(item.getTypeId()) + ".");
            sender.sendMessage(ChatColor.YELLOW.toString() + amt + " " + ItemUtil.toItemName(item.getTypeId()) + " has been taken.");
        } else {
            sender.sendMessage(ChatColor.YELLOW.toString() + target.getName() + " has no " + ItemUtil.toItemName(item.getTypeId()) + ".");
        }
    }

    public static Location findFreePosition(Location searchPos) {
        int y;
        World world = searchPos.getWorld();
        Location loc = searchPos.clone();
        int x = searchPos.getBlockX();
        int origY = y = Math.max(0, searchPos.getBlockY());
        int z = searchPos.getBlockZ();
        int free = 0;
        while (y <= world.getMaxHeight() + 2) {
            free = BlockType.canPassThrough((int)world.getBlockTypeIdAt(x, y, z)) ? (int)((byte)(free + 1)) : 0;
            if (free == 2) {
                if (y - 1 != origY) {
                    loc.setX((double)x + 0.5);
                    loc.setY(y - 1);
                    loc.setZ((double)z + 0.5);
                }
                return loc;
            }
            ++y;
        }
        return null;
    }

    public static void sendArrowFromPlayer(Player player, Vector dir, float speed) {
        Location loc = player.getEyeLocation();
        Vector actualDir = dir.clone().normalize();
        Vector finalVecLoc = loc.toVector().add(actualDir.multiply(2));
        loc.setX(finalVecLoc.getX());
        loc.setY(finalVecLoc.getY());
        loc.setZ(finalVecLoc.getZ());
        Arrow arrow = player.getWorld().spawn(loc, Arrow.class);
        arrow.setShooter(player);
        arrow.setVelocity(dir.multiply(speed));
    }

    public static void sendFireballsFromPlayer(Player player, int amt) {
        Location loc = player.getEyeLocation();
        double tau = Math.PI * 2;
        double arc = Math.PI * 2 / (double)amt;
        for (double a = 0.0; a < Math.PI * 2; a += arc) {
            Vector dir = new Vector(Math.cos(a), 0.0, Math.sin(a));
            Location spawn = loc.toVector().add(dir.multiply(2)).toLocation(loc.getWorld(), 0.0f, 0.0f);
            Fireball fball = player.getWorld().spawn(spawn, Fireball.class);
            fball.setShooter(player);
            fball.setDirection(dir.multiply(10));
        }
    }

    public static void sendCannonToPlayer(Player player) {
        Location loc = player.getEyeLocation();
        loc.setX(loc.getX());
        loc.setY(loc.getY());
        loc.setZ(loc.getZ());
        player.getWorld().spawn(loc, Fireball.class);
    }

    public static void sendMessage(CommandSender sender, String message) {
        for (String line : message.split("\n")) {
            sender.sendMessage(line.replaceAll("[\r\n]", ""));
        }
    }

    public static void expandStack(ItemStack item, boolean infinite, boolean overrideStackSize) {
        int stackSize;
        if (item == null || item.getAmount() == 0 || item.getTypeId() <= 0) {
            return;
        }
        int n = stackSize = overrideStackSize ? 64 : item.getType().getMaxStackSize();
        if (item.getType().getMaxStackSize() == 1) {
            return;
        }
        if (infinite) {
            item.setAmount(-1);
        } else if (item.getAmount() < stackSize) {
            item.setAmount(stackSize);
        }
    }

    public static World.Environment getSkylandsEnvironment() {
        try {
            return World.Environment.THE_END;
        }
        catch (Throwable t) {
            return World.Environment.getEnvironment(1);
        }
    }

    public static String replaceMacros(CommandSender sender, String message) {
        Player[] online = CommandBook.server().getOnlinePlayers();
        message = message.replace("%name%", PlayerUtil.toName(sender));
        message = message.replace("%cname%", PlayerUtil.toColoredName(sender, null));
        message = message.replace("%id%", PlayerUtil.toUniqueName(sender));
        if ((message = message.replace("%online%", String.valueOf(online.length))).contains("%players%")) {
            message = message.replace("%players%", CommandBookUtil.getOnlineList(online, null));
        }
        if (sender instanceof Player) {
            Player player = (Player)sender;
            World world = player.getWorld();
            message = message.replace("%time%", CommandBookUtil.getTimeString(world.getTime()));
            message = message.replace("%world%", world.getName());
        }
        Pattern cmdPattern = Pattern.compile("%cmd:([^%]+)%");
        Matcher matcher = cmdPattern.matcher(message);
        try {
            StringBuffer buff = new StringBuffer();
            while (matcher.find()) {
                String s;
                Process p = new ProcessBuilder(matcher.group(1).split(" ")).start();
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuilder build = new StringBuilder();
                while ((s = stdInput.readLine()) != null) {
                    build.append(s).append(" ");
                }
                stdInput.close();
                build.delete(build.length() - 1, build.length());
                matcher.appendReplacement(buff, build.toString());
                p.destroy();
            }
            matcher.appendTail(buff);
            message = buff.toString();
        }
        catch (IOException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + "Error replacing macros: " + e.getMessage());
        }
        return message;
    }

    public static <T, K, V> Map<K, V> getNestedMap(Map<T, Map<K, V>> source, T key) {
        Map<K, V> value = source.get(key);
        if (value == null) {
            value = new HashMap();
            source.put(key, value);
        }
        return value;
    }

    public static <T, V> Set<V> getNestedSet(Map<T, Set<V>> source, T key) {
        Set<V> value = source.get(key);
        if (value == null) {
            value = new HashSet<V>();
            source.put(key, value);
        }
        return value;
    }

    public static <T, V> List<V> getNestedList(Map<T, List<V>> source, T key) {
        List<V> value = source.get(key);
        if (value == null) {
            value = new ArrayList<V>();
            source.put(key, value);
        }
        return value;
    }

    public static long matchDate(String filter) throws CommandException {
        if (filter == null) {
            return 0L;
        }
        if (filter.equalsIgnoreCase("now")) {
            return System.currentTimeMillis();
        }
        String[] groupings = filter.split("-");
        if (groupings.length == 0) {
            throw new CommandException("Invalid date specified");
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(0L);
        for (String str : groupings) {
            int type;
            switch (str.charAt(str.length() - 1)) {
                case 'm': {
                    type = 12;
                    break;
                }
                case 'h': {
                    type = 10;
                    break;
                }
                case 'd': {
                    type = 5;
                    break;
                }
                case 'w': {
                    type = 3;
                    break;
                }
                case 'y': {
                    type = 1;
                    break;
                }
                default: {
                    throw new CommandException("Unknown date value specified");
                }
            }
            ((Calendar)cal).add(type, Integer.valueOf(str.substring(0, str.length() - 1)));
        }
        return cal.getTimeInMillis();
    }

    public static long matchFutureDate(String filter) throws CommandException {
        return CommandBookUtil.matchDate(filter) + System.currentTimeMillis();
    }
}

