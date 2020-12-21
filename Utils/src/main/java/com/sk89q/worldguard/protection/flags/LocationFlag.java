/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.worldedit.LocalWorld
 *  com.sk89q.worldedit.Location
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.bukkit.BukkitUtil
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Location;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationFlag
extends Flag<Location> {
    public LocationFlag(String name, RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    public LocationFlag(String name) {
        super(name);
    }

    @Override
    public Location parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        Player player;
        input = input.trim();
        try {
            player = plugin.checkPlayer(sender);
        }
        catch (CommandException e) {
            throw new InvalidFlagFormat(e.getMessage());
        }
        if ("here".equalsIgnoreCase(input)) {
            return BukkitUtil.toLocation((org.bukkit.Location)player.getLocation());
        }
        if ("none".equalsIgnoreCase(input)) {
            return null;
        }
        String[] split = input.split(",");
        if (split.length >= 3) {
            try {
                World world = player.getWorld();
                double x = Double.parseDouble(split[0]);
                double y = Double.parseDouble(split[1]);
                double z = Double.parseDouble(split[2]);
                float yaw = split.length < 4 ? 0.0f : Float.parseFloat(split[3]);
                float pitch = split.length < 5 ? 0.0f : Float.parseFloat(split[4]);
                return new Location(BukkitUtil.getLocalWorld((World)world), new Vector(x, y, z), yaw, pitch);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        throw new InvalidFlagFormat("Expected 'here' or x,y,z.");
    }

    @Override
    public Location unmarshal(Object o) {
        if (o instanceof Map) {
            Map map = (Map)o;
            Object rawWorld = map.get("world");
            if (rawWorld == null) {
                return null;
            }
            Object rawX = map.get("x");
            if (rawX == null) {
                return null;
            }
            Object rawY = map.get("y");
            if (rawY == null) {
                return null;
            }
            Object rawZ = map.get("z");
            if (rawZ == null) {
                return null;
            }
            Object rawYaw = map.get("yaw");
            if (rawYaw == null) {
                return null;
            }
            Object rawPitch = map.get("pitch");
            if (rawPitch == null) {
                return null;
            }
            World bukkitWorld = Bukkit.getServer().getWorld((String)rawWorld);
            LocalWorld world = BukkitUtil.getLocalWorld((World)bukkitWorld);
            Vector position = new Vector(this.toNumber(rawX), this.toNumber(rawY), this.toNumber(rawZ));
            float yaw = (float)this.toNumber(rawYaw);
            float pitch = (float)this.toNumber(rawPitch);
            return new Location(world, position, yaw, pitch);
        }
        return null;
    }

    @Override
    public Object marshal(Location o) {
        Vector position = o.getPosition();
        HashMap<String, Object> vec = new HashMap<String, Object>();
        vec.put("world", o.getWorld().getName());
        vec.put("x", position.getX());
        vec.put("y", position.getY());
        vec.put("z", position.getZ());
        vec.put("yaw", Float.valueOf(o.getYaw()));
        vec.put("pitch", Float.valueOf(o.getPitch()));
        return vec;
    }

    private double toNumber(Object o) {
        if (o instanceof Number) {
            return ((Number)o).doubleValue();
        }
        return 0.0;
    }
}

