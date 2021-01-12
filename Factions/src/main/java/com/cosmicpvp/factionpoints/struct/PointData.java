/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 */
package com.cosmicpvp.factionpoints.struct;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;

import java.util.Calendar;

public class PointData {
    public long points;
    private long lastPointPurchased;
    private int pointsPurchasedToday;

    public PointData(long points) {
        this.points = points;
    }

    public int getPointsPurchasedToday() {
        if (this.pointsPurchasedToday != 0 && this.lastPointPurchased != 0L && this.isDifferentDay(this.lastPointPurchased)) {
            P.debugCosmicFactionPoints("Different day detected, resetting points purchased today: " + this.pointsPurchasedToday + ", last=" + this.lastPointPurchased);
            this.pointsPurchasedToday = 0;
        }
        return this.pointsPurchasedToday;
    }

    private boolean isDifferentDay(long pastTime) {
        Calendar today = Calendar.getInstance();
        int dayToday = today.get(5);
        today.setTimeInMillis(pastTime);
        int lastDayWePurchasedSome = today.get(5);
        return dayToday != lastDayWePurchasedSome;
    }

    public long addPointsPurchased(double pointsAdded, Faction faction) {
        int currentPoints2 = this.getPointsPurchasedToday();
        this.lastPointPurchased = System.currentTimeMillis();
        this.pointsPurchasedToday = (int)((double)currentPoints2 + pointsAdded);
        this.points = (long)((double)this.points + pointsAdded);
        P.debugCosmicFactionPoints("Giving " + pointsAdded + " points to " + faction.getTag() + " id=" + faction.getId());
        return this.points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void setLastPointPurchased(long lastPointPurchased) {
        this.lastPointPurchased = lastPointPurchased;
    }

    public void setPointsPurchasedToday(int pointsPurchasedToday) {
        this.pointsPurchasedToday = pointsPurchasedToday;
    }

    public long getPoints() {
        return this.points;
    }

    public long getLastPointPurchased() {
        return this.lastPointPurchased;
    }
}

