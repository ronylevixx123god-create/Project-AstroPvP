package me.kingsavaqe.astroenvoy.stats;


import java.util.UUID;

public class Stats {
    private UUID uuid;

    private String name;

    private int opened;

    public Stats(UUID uuid, String name, int opened) {
        this.uuid = uuid;
        this.name = name;
        this.opened = opened;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public int getOpened() {
        return this.opened;
    }
}
