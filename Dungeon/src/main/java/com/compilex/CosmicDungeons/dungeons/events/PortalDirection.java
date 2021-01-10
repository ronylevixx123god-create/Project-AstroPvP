package com.compilex.CosmicDungeons.dungeons.events;

import org.bukkit.map.MapPalette;

public enum PortalDirection {
    LEFT((byte)16, "R"),
    MIDDLE((byte)28, "G"),
    RIGHT(MapPalette.matchColor(148, 0, 211), "P");

    public byte color;

    private String loreName;

    PortalDirection(byte color, String loreName) {
        this.color = color;
        this.loreName = loreName;
    }

    public byte getColor() {
        return this.color;
    }

    public String getLoreName() {
        return this.loreName;
    }
}
