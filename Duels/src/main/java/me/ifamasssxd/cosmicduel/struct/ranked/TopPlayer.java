/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicduel.struct.ranked;

import java.util.UUID;

public class TopPlayer {
    private UUID uuid;
    String name;
    int kills;
    int losses;
    double elo;
    private int position;

    public TopPlayer(UUID uuid, String name, int kills, int losses, double elo) {
        this.uuid = uuid;
        this.name = name;
        this.kills = kills;
        this.losses = losses;
        this.elo = elo;
    }

    public String toString() {
        return this.name;
    }

    public double getKDR() {
        if (this.losses == 0) {
            return this.kills;
        }
        return Double.valueOf(this.kills) / Double.valueOf(this.losses);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public int getKills() {
        return this.kills;
    }

    public int getLosses() {
        return this.losses;
    }

    public double getElo() {
        return this.elo;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

