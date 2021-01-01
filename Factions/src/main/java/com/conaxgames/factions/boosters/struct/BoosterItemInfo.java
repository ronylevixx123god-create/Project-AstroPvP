/*
 * Decompiled with CFR 0.150.
 */
package com.conaxgames.factions.boosters.struct;


import com.conaxgames.factions.boosters.enums.BoosterType;

public class BoosterItemInfo {
    int duration;
    double multiplier;
    BoosterType type;

    public BoosterItemInfo(BoosterType type, double multiplier, String durationString) {
        this.multiplier = multiplier;
        this.type = type;
        int hours = 0;
        if (durationString.contains("h")) {
            hours = Integer.parseInt(durationString.split("h")[0]);
        }
        int minutes = 0;
        if (durationString.contains("m")) {
            String splt = durationString.split("m")[0];
            minutes = splt.contains("h") ? Integer.parseInt(splt.split("h ")[1].trim()) : Integer.parseInt(splt.trim());
        }
        int seconds = 0;
        if (durationString.contains("s")) {
            String splt = durationString.split("s")[0];
            seconds = splt.contains("m") ? Integer.parseInt(splt.split("m ")[1].trim()) : Integer.parseInt(splt.trim());
        }
        this.duration = seconds + hours * 60 * 60 + minutes * 60;
    }

    public int getDuration() {
        return this.duration;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public BoosterType getType() {
        return this.type;
    }
}

