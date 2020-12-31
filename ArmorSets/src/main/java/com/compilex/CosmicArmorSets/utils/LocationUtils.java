/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 */
package com.compilex.CosmicArmorSets.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Random;

public class LocationUtils {
    public static Location getNearbyLocation(Location l, int radius, int minDistance, int y_boost) {
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((double) ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + minDistance)), 0.0, (double) ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + minDistance)));
        rand_loc.add(0.5, (double) y_boost, 0.5);
        Block b = rand_loc.getWorld().getHighestBlockAt(rand_loc).getLocation().getBlock();
        return (double) b.getY() < rand_loc.getY() ? b.getLocation() : rand_loc;
    }
}

