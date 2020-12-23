/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Deathpoint
        extends ArkPlugin {
    protected static HashMap<UUID, Location> death_locations;

    public Deathpoint(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        death_locations = new HashMap();
        this.registerListener(new DeathpointListener());
        this.registerCommand(new CommandDeathpoint("deathpoint", "/<command>", "Returns to last point of death.", new ArrayList<String>(Arrays.asList("back", "return"))));
    }

    @Override
    public void disable() {
        death_locations.clear();
    }
}

