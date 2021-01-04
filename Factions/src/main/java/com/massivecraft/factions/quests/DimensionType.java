/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 */
package com.massivecraft.factions.quests;

import org.bukkit.World;

public enum DimensionType {
    OVERWORLD(0, "Overworld"),
    THE_NETHER(1, "the Nether"),
    THE_END(-1, "the End");

    int dimension;
    private String name;

    public boolean isWorld(World world) {
        return this.getDimension() == world.getEnvironment().getId();
    }

    private DimensionType(int dimension, String name) {
        this.dimension = dimension;
        this.name = name;
    }

    public int getDimension() {
        return this.dimension;
    }

    public String getName() {
        return this.name;
    }
}

