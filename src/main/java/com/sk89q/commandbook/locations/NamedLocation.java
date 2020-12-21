/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.locations;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NamedLocation {
    private String name;
    private String creatorName;
    private String worldName;
    private Location loc;

    public NamedLocation(String name, Location loc) {
        this.name = name;
        this.loc = loc;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Location getLocation() {
        return this.loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public void teleport(Player player) {
        this.loc.getChunk().load(true);
        player.teleport(this.loc);
    }
}

