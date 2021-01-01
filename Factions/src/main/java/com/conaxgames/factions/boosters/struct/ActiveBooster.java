/*
 * Decompiled with CFR 0.150.
 */
package com.conaxgames.factions.boosters.struct;


import com.conaxgames.factions.boosters.enums.BoosterType;
import com.conaxgames.factions.boosters.utils.TimeUtil;

public class ActiveBooster {
    String whoApplied;
    double multiplier;
    long timeApplied;
    int secondsElapsed;
    int maxSeconds;
    BoosterType boosterType;

    public String getFormattedTimeLeft() {
        return TimeUtil.formatDifference(this.maxSeconds - this.secondsElapsed);
    }

    public String toString() {
        return this.getSecondsElapsed() + ":" + this.getWhoApplied() + ":" + this.getMultiplier() + ":" + this.getTimeApplied() + ":" + this.getMaxSeconds() + ":" + this.boosterType.name();
    }

    public ActiveBooster(String whoApplied, double multiplier, long timeApplied, int secondsElapsed, int maxSeconds, BoosterType boosterType) {
        this.whoApplied = whoApplied;
        this.multiplier = multiplier;
        this.timeApplied = timeApplied;
        this.secondsElapsed = secondsElapsed;
        this.maxSeconds = maxSeconds;
        this.boosterType = boosterType;
    }

    public String getWhoApplied() {
        return this.whoApplied;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public long getTimeApplied() {
        return this.timeApplied;
    }

    public void setTimeApplied(long timeApplied) {
        this.timeApplied = timeApplied;
    }

    public int getSecondsElapsed() {
        return this.secondsElapsed;
    }

    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    public int getMaxSeconds() {
        return this.maxSeconds;
    }

    public void setMaxSeconds(int maxSeconds) {
        this.maxSeconds = maxSeconds;
    }

    public BoosterType getBoosterType() {
        return this.boosterType;
    }
}

