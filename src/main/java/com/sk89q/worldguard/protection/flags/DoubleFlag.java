/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.command.CommandSender;

public class DoubleFlag
extends Flag<Double> {
    public DoubleFlag(String name, RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    public DoubleFlag(String name) {
        super(name);
    }

    @Override
    public Double parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        input = input.trim();
        try {
            return Double.parseDouble(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidFlagFormat("Not a number: " + input);
        }
    }

    @Override
    public Double unmarshal(Object o) {
        if (o instanceof Double) {
            return (Double)o;
        }
        if (o instanceof Number) {
            return ((Number)o).doubleValue();
        }
        return null;
    }

    @Override
    public Object marshal(Double o) {
        return o;
    }
}

