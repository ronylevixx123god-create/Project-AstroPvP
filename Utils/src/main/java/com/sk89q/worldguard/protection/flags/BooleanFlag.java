/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.command.CommandSender;

public class BooleanFlag
extends Flag<Boolean> {
    public BooleanFlag(String name, RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    public BooleanFlag(String name) {
        super(name);
    }

    @Override
    public Boolean parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        if ((input = input.trim()).equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("on") || input.equalsIgnoreCase("1")) {
            return true;
        }
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("off") || input.equalsIgnoreCase("0")) {
            return false;
        }
        throw new InvalidFlagFormat("Not a yes/no value: " + input);
    }

    @Override
    public Boolean unmarshal(Object o) {
        if (o instanceof Boolean) {
            return (Boolean)o;
        }
        return null;
    }

    @Override
    public Object marshal(Boolean o) {
        return o;
    }
}

