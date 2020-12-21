/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandException
 */
package com.sk89q.commandbook.util;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.minecraft.util.commands.CommandException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerUtil {
    private PlayerUtil() {
    }

    public static Player checkPlayer(CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            return (Player)sender;
        }
        throw new CommandException("A player context is required. (Specify a world or player if the command supports it.)");
    }

    public static List<Player> matchPlayerNames(String filter) {
        Player[] players = CommandBook.server().getOnlinePlayers();
        boolean useDisplayNames = CommandBook.inst().lookupWithDisplayNames;
        if ((filter = filter.toLowerCase()).charAt(0) == '@' && filter.length() >= 2) {
            filter = filter.substring(1);
            for (Player player : players) {
                if (!player.getName().equalsIgnoreCase(filter) && (!useDisplayNames || !ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(filter))) continue;
                ArrayList<Player> list = new ArrayList<Player>();
                list.add(player);
                return list;
            }
            return new ArrayList<Player>();
        }
        if (filter.charAt(0) == '*' && filter.length() >= 2) {
            filter = filter.substring(1);
            ArrayList<Player> list = new ArrayList<Player>();
            for (Player player : players) {
                if (!player.getName().toLowerCase().contains(filter) && (!useDisplayNames || !ChatColor.stripColor(player.getDisplayName().toLowerCase()).contains(filter))) continue;
                list.add(player);
            }
            return list;
        }
        ArrayList<Player> list = new ArrayList<Player>();
        for (Player player : players) {
            if (!player.getName().toLowerCase().startsWith(filter) && (!useDisplayNames || !ChatColor.stripColor(player.getDisplayName().toLowerCase()).startsWith(filter))) continue;
            list.add(player);
        }
        return list;
    }

    protected static Iterable<Player> checkPlayerMatch(List<Player> players) throws CommandException {
        if (players.size() == 0) {
            throw new CommandException("No players matched query.");
        }
        return players;
    }

    public static Iterable<Player> matchPlayers(CommandSender source, String filter) throws CommandException {
        if (CommandBook.server().getOnlinePlayers().length == 0) {
            throw new CommandException("No players matched query.");
        }
        if (filter.equals("*")) {
            CommandBook.inst().checkPermission(source, "commandbook.targets.everyone");
            return PlayerUtil.checkPlayerMatch(Arrays.asList(CommandBook.server().getOnlinePlayers()));
        }
        if (filter.charAt(0) == '#') {
            if (filter.equalsIgnoreCase("#world")) {
                ArrayList<Player> players = new ArrayList<Player>();
                Player sourcePlayer = PlayerUtil.checkPlayer(source);
                World sourceWorld = sourcePlayer.getWorld();
                CommandBook.inst().checkPermission(source, "commandbook.targets.world." + sourceWorld.getName());
                for (Player player : CommandBook.server().getOnlinePlayers()) {
                    if (!player.getWorld().equals(sourceWorld)) continue;
                    players.add(player);
                }
                return PlayerUtil.checkPlayerMatch(players);
            }
            if (filter.equalsIgnoreCase("#near")) {
                CommandBook.inst().checkPermission(source, "commandbook.targets.near");
                ArrayList<Player> players = new ArrayList<Player>();
                Player sourcePlayer = PlayerUtil.checkPlayer(source);
                World sourceWorld = sourcePlayer.getWorld();
                Vector sourceVector = sourcePlayer.getLocation().toVector();
                for (Player player : CommandBook.server().getOnlinePlayers()) {
                    if (!player.getWorld().equals(sourceWorld) || !(player.getLocation().toVector().distanceSquared(sourceVector) < 900.0)) continue;
                    players.add(player);
                }
                return PlayerUtil.checkPlayerMatch(players);
            }
            throw new CommandException("Invalid group '" + filter + "'.");
        }
        List<Player> players = PlayerUtil.matchPlayerNames(filter);
        return PlayerUtil.checkPlayerMatch(players);
    }

    public static Player matchPlayerExactly(CommandSender sender, String filter) throws CommandException {
        Player[] players;
        for (Player player : players = CommandBook.server().getOnlinePlayers()) {
            if (!player.getName().equalsIgnoreCase(filter) && (!CommandBook.inst().lookupWithDisplayNames || !player.getDisplayName().equalsIgnoreCase(filter))) continue;
            return player;
        }
        throw new CommandException("No player found!");
    }

    public static Player matchSinglePlayer(CommandSender sender, String filter) throws CommandException {
        Iterator<Player> players = PlayerUtil.matchPlayers(sender, filter).iterator();
        Player match = players.next();
        if (players.hasNext()) {
            throw new CommandException("More than one player found! Use @<name> for exact matching.");
        }
        return match;
    }

    public static CommandSender matchPlayerOrConsole(CommandSender sender, String filter) throws CommandException {
        if (filter.equalsIgnoreCase("#console") || filter.equalsIgnoreCase("*console*") || filter.equalsIgnoreCase("!")) {
            return CommandBook.server().getConsoleSender();
        }
        return PlayerUtil.matchSinglePlayer(sender, filter);
    }

    public static Iterable<Player> matchPlayers(Player player) {
        return Arrays.asList(player);
    }

    public static String toName(CommandSender sender) {
        return ChatColor.stripColor(PlayerUtil.toColoredName(sender, null));
    }

    public static String toColoredName(CommandSender sender, ChatColor endColor) {
        if (sender instanceof Player) {
            String name;
            String string = name = CommandBook.inst().useDisplayNames ? ((Player)sender).getDisplayName() : sender.getName();
            if (endColor != null && name.contains("\u00a7")) {
                name = name + (Object)((Object)endColor);
            }
            return name;
        }
        if (sender instanceof ConsoleCommandSender) {
            return "*Console*";
        }
        return sender.getName();
    }

    public static String toUniqueName(CommandSender sender) {
        if (sender instanceof Player) {
            return sender.getName();
        }
        return "*Console*";
    }
}

