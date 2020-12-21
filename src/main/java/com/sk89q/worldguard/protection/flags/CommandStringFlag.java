/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.command.CommandSender;

public class CommandStringFlag
extends Flag<String> {
    public CommandStringFlag(String name, RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    public CommandStringFlag(String name) {
        super(name);
    }

    @Override
    public String parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        if (!(input = input.trim()).startsWith("/")) {
            input = "/" + input;
        }
        return input.toLowerCase();
    }

    @Override
    public String unmarshal(Object o) {
        if (o instanceof String) {
            return (String)o;
        }
        return null;
    }

    @Override
    public Object marshal(String o) {
        return o;
    }
}

