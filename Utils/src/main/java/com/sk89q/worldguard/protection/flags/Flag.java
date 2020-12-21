/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import org.bukkit.command.CommandSender;

public abstract class Flag<T> {
    private String name;
    private RegionGroupFlag regionGroup;

    public Flag(String name, RegionGroup defaultGroup) {
        this.name = name;
        if (defaultGroup != null) {
            this.regionGroup = new RegionGroupFlag(name + "-group", defaultGroup);
        }
    }

    public Flag(String name) {
        this(name, RegionGroup.NON_MEMBERS);
    }

    public String getName() {
        return this.name;
    }

    public RegionGroupFlag getRegionGroupFlag() {
        return this.regionGroup;
    }

    public abstract T parseInput(WorldGuardPlugin var1, CommandSender var2, String var3) throws InvalidFlagFormat;

    public abstract T unmarshal(Object var1);

    public abstract Object marshal(T var1);
}

