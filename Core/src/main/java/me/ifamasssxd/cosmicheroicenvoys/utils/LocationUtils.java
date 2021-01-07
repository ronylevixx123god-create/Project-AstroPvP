/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package me.ifamasssxd.cosmicheroicenvoys.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static Location getLocation(String spawn) {
        if (spawn == null || !spawn.contains(":")) {
            return null;
        }
        String[] args = spawn.split(":");
        World bukkit = Bukkit.getWorld((String)args[0]);
        return new Location(bukkit, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
    }

    public static String getString(Location location) {
        return location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
    }
}

