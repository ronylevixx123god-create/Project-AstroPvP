/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.command.CommandSender;

public class SetFlag<T>
extends Flag<Set<T>> {
    private Flag<T> subFlag;

    public SetFlag(String name, RegionGroup defaultGroup, Flag<T> subFlag) {
        super(name, defaultGroup);
        this.subFlag = subFlag;
    }

    public SetFlag(String name, Flag<T> subFlag) {
        super(name);
        this.subFlag = subFlag;
    }

    @Override
    public Set<T> parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        HashSet<T> items = new HashSet<T>();
        for (String str : input.split(",")) {
            items.add(this.subFlag.parseInput(plugin, sender, str.trim()));
        }
        return new HashSet(items);
    }

    @Override
    public Set<T> unmarshal(Object o) {
        if (o instanceof Collection) {
            Collection collection = (Collection)o;
            HashSet<T> items = new HashSet<T>();
            for (Object sub : collection) {
                T item = this.subFlag.unmarshal(sub);
                if (item == null) continue;
                items.add(item);
            }
            return items;
        }
        return null;
    }

    @Override
    public Object marshal(Set<T> o) {
        ArrayList<Object> list = new ArrayList<Object>();
        for (T item : o) {
            list.add(this.subFlag.marshal(item));
        }
        return list;
    }
}

