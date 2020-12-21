/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.command.CommandSender;

public class EnumFlag<T extends Enum<T>>
extends Flag<T> {
    private Class<T> enumClass;

    public EnumFlag(String name, Class<T> enumClass, RegionGroup defaultGroup) {
        super(name, defaultGroup);
        this.enumClass = enumClass;
    }

    public EnumFlag(String name, Class<T> enumClass) {
        super(name);
        this.enumClass = enumClass;
    }

    private T findValue(String input) throws IllegalArgumentException {
        if (input != null) {
            input = input.toUpperCase();
        }
        try {
            return Enum.valueOf(this.enumClass, input);
        }
        catch (IllegalArgumentException e) {
            T val = this.detectValue(input);
            if (val != null) {
                return val;
            }
            throw e;
        }
    }

    public T detectValue(String input) {
        return null;
    }

    @Override
    public T parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        try {
            return this.findValue(input);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidFlagFormat("Unknown value '" + input + "' in " + this.enumClass.getName());
        }
    }

    @Override
    public T unmarshal(Object o) {
        try {
            return Enum.valueOf(this.enumClass, String.valueOf(o));
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Object marshal(T o) {
        return ((Enum)o).name();
    }
}

