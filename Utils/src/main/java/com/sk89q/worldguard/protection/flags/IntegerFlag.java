/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.command.CommandSender;

public class IntegerFlag
extends Flag<Integer> {
    public IntegerFlag(String name, RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    public IntegerFlag(String name) {
        super(name);
    }

    @Override
    public Integer parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        input = input.trim();
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidFlagFormat("Not a number: " + input);
        }
    }

    @Override
    public Integer unmarshal(Object o) {
        if (o instanceof Integer) {
            return (Integer)o;
        }
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        return null;
    }

    @Override
    public Object marshal(Integer o) {
        return o;
    }
}

