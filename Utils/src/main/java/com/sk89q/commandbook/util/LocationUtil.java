/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandException
 */
package com.sk89q.commandbook.util;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.CommandBookUtil;
import com.sk89q.commandbook.locations.HomesComponent;
import com.sk89q.commandbook.locations.LocationsComponent;
import com.sk89q.commandbook.locations.NamedLocation;
import com.sk89q.commandbook.locations.RootLocationManager;
import com.sk89q.commandbook.locations.WarpsComponent;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.minecraft.util.commands.CommandException;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationUtil {
    private LocationUtil() {
    }

    public static World matchWorld(CommandSender sender, String filter) throws CommandException {
        List<World> worlds = CommandBook.server().getWorlds();
        if (filter.charAt(0) == '#') {
            if (filter.equalsIgnoreCase("#main")) {
                return worlds.get(0);
            }
            if (filter.equalsIgnoreCase("#normal")) {
                for (World world : worlds) {
                    if (world.getEnvironment() != World.Environment.NORMAL) continue;
                    return world;
                }
                throw new CommandException("No normal world found.");
            }
            if (filter.equalsIgnoreCase("#nether")) {
                for (World world : worlds) {
                    if (world.getEnvironment() != World.Environment.NETHER) continue;
                    return world;
                }
                throw new CommandException("No nether world found.");
            }
            if (filter.equalsIgnoreCase("#skylands") || filter.equalsIgnoreCase("#theend") || filter.equalsIgnoreCase("#end")) {
                World.Environment skylandsEnv = CommandBookUtil.getSkylandsEnvironment();
                for (World world : worlds) {
                    if (world.getEnvironment() != skylandsEnv) continue;
                    return world;
                }
                throw new CommandException("No skylands world found.");
            }
            if (filter.matches("^#player$")) {
                String[] parts = filter.split(":", 2);
                if (parts.length == 1) {
                    throw new CommandException("Argument expected for #player.");
                }
                return PlayerUtil.matchPlayers(sender, parts[1]).iterator().next().getWorld();
            }
            throw new CommandException("Invalid identifier '" + filter + "'.");
        }
        for (World world : worlds) {
            if (!world.getName().equals(filter)) continue;
            return world;
        }
        throw new CommandException("No world by that exact name found.");
    }

    public static Location matchLocation(CommandSender source, String filter) throws CommandException {
        List<Player> players;
        if (filter.matches("^[\\-0-9\\.]+,[\\-0-9\\.]+,[\\-0-9\\.]+(?:.+)?$")) {
            double z;
            double y;
            double x;
            CommandBook.inst().checkPermission(source, "commandbook.locations.coords");
            String[] args = filter.split(":");
            String[] parts = args[0].split(",");
            try {
                x = Double.parseDouble(parts[0]);
                y = Double.parseDouble(parts[1]);
                z = Double.parseDouble(parts[2]);
            }
            catch (NumberFormatException e) {
                throw new CommandException("Coordinates expected numbers!");
            }
            if (args.length > 1) {
                return new Location(LocationUtil.matchWorld(source, args[1]), x, y, z);
            }
            Player player = PlayerUtil.checkPlayer(source);
            return new Location(player.getWorld(), x, y, z);
        }
        if (filter.charAt(0) == '#') {
            String[] args = filter.split(":");
            if (args[0].equalsIgnoreCase("#spawn")) {
                CommandBook.inst().checkPermission(source, "commandbook.spawn");
                if (args.length > 1) {
                    return LocationUtil.matchWorld(source, args[1]).getSpawnLocation();
                }
                Player sourcePlayer = PlayerUtil.checkPlayer(source);
                return sourcePlayer.getLocation().getWorld().getSpawnLocation();
            }
            if (args[0].equalsIgnoreCase("#target")) {
                CommandBook.inst().checkPermission(source, "commandbook.locations.target");
                Player player = PlayerUtil.checkPlayer(source);
                Location playerLoc = player.getLocation();
                Block targetBlock = player.getTargetBlock(null, 100);
                if (targetBlock == null) {
                    throw new CommandException("Failed to find a block in your target!");
                }
                Location loc = targetBlock.getLocation();
                playerLoc.setX(loc.getX());
                playerLoc.setY(loc.getY());
                playerLoc.setZ(loc.getZ());
                return CommandBookUtil.findFreePosition(playerLoc);
            }
            if (args[0].equalsIgnoreCase("#home") || args[0].equalsIgnoreCase("#warp")) {
                LocationsComponent component;
                String type = args[0].substring(1);
                CommandBook.inst().checkPermission(source, "commandbook.locations." + type);
                LocationsComponent locationsComponent = component = type.equalsIgnoreCase("warp") ? (LocationsComponent)CommandBook.inst().getComponentManager().getComponent(WarpsComponent.class) : (LocationsComponent)CommandBook.inst().getComponentManager().getComponent(HomesComponent.class);
                if (component == null) {
                    throw new CommandException("This type of location is not enabled!");
                }
                RootLocationManager<NamedLocation> manager = component.getManager();
                if (args.length == 1) {
                    if (type.equalsIgnoreCase("warp")) {
                        throw new CommandException("Please specify a warp name.");
                    }
                    Player ply = PlayerUtil.checkPlayer(source);
                    NamedLocation loc = manager.get(ply.getWorld(), ply.getName());
                    if (loc == null) {
                        throw new CommandException("You have not set your home yet.");
                    }
                    return loc.getLocation();
                }
                if (args.length == 2) {
                    Player player;
                    NamedLocation loc;
                    if (source instanceof Player && (loc = manager.get((player = (Player)source).getWorld(), args[1])) != null && !loc.getCreatorName().equalsIgnoreCase(player.getName())) {
                        CommandBook.inst().checkPermission(source, "commandbook.locations." + type + ".other");
                    }
                    return LocationUtil.getManagedLocation(manager, PlayerUtil.checkPlayer(source).getWorld(), args[1]);
                }
                if (args.length == 3) {
                    if (source instanceof Player) {
                        Player player = (Player)source;
                        NamedLocation loc = manager.get(LocationUtil.matchWorld(source, args[2]), args[1]);
                        if (loc != null && !loc.getCreatorName().equalsIgnoreCase(player.getName())) {
                            CommandBook.inst().checkPermission(source, "commandbook.locations." + type + ".other");
                        }
                    }
                    return LocationUtil.getManagedLocation(manager, LocationUtil.matchWorld(source, args[2]), args[1]);
                }
            } else {
                if (args[0].equalsIgnoreCase("#me")) {
                    return PlayerUtil.checkPlayer(source).getLocation();
                }
                throw new CommandException("Invalid group '" + filter + "'.");
            }
        }
        if ((players = PlayerUtil.matchPlayerNames(filter)).size() == 0) {
            throw new CommandException("No players matched query.");
        }
        return players.get(0).getLocation();
    }

    public static Location getManagedLocation(RootLocationManager<NamedLocation> manager, World world, String id) throws CommandException {
        NamedLocation loc = manager.get(world, id);
        if (loc == null) {
            throw new CommandException("A location by that name could not be found.");
        }
        return loc.getLocation();
    }

    public static String toFriendlyString(Location location) {
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "@" + location.getWorld().getName();
    }
}

