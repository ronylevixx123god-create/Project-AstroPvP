/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.command.CommandSender;

public class StateFlag
extends Flag<State> {
    private boolean def;

    public StateFlag(String name, boolean def, RegionGroup defaultGroup) {
        super(name, defaultGroup);
        this.def = def;
    }

    public StateFlag(String name, boolean def) {
        super(name);
        this.def = def;
    }

    public boolean getDefault() {
        return this.def;
    }

    @Override
    public State parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
        if ((input = input.trim()).equalsIgnoreCase("allow")) {
            return State.ALLOW;
        }
        if (input.equalsIgnoreCase("deny")) {
            return State.DENY;
        }
        if (input.equalsIgnoreCase("none")) {
            return null;
        }
        throw new InvalidFlagFormat("Expected none/allow/deny but got '" + input + "'");
    }

    @Override
    public State unmarshal(Object o) {
        String str = o.toString();
        if (str.equalsIgnoreCase("allow")) {
            return State.ALLOW;
        }
        if (str.equalsIgnoreCase("deny")) {
            return State.DENY;
        }
        return null;
    }

    @Override
    public Object marshal(State o) {
        if (o == State.ALLOW) {
            return "allow";
        }
        if (o == State.DENY) {
            return "deny";
        }
        return null;
    }

    public static enum State {
        ALLOW,
        DENY;

    }
}

