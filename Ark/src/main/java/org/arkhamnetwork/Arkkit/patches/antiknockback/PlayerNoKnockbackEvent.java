/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.util.Vector
 */
package org.arkhamnetwork.Arkkit.patches.antiknockback;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import java.util.List;

public class PlayerNoKnockbackEvent
        extends Event {
    private static final HandlerList handlerList = new HandlerList();
    public Player player;
    public Vector velocity;
    public Location startingLocation;
    public double highestHeightDetected = 0.0;
    List<Double> yChanges;
    double differenceDetected;

    public PlayerNoKnockbackEvent(Player player, Vector vel, Location starting, double highestHeightDetected, double difference, List<Double> recentYChanges) {
        this.player = player;
        this.differenceDetected = difference;
        this.yChanges = recentYChanges;
        this.highestHeightDetected = highestHeightDetected;
        this.velocity = vel;
        this.startingLocation = starting;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public Location getStartingLocation() {
        return this.startingLocation;
    }

    public double getHighestHeightDetected() {
        return this.highestHeightDetected;
    }

    public List<Double> getYChanges() {
        return this.yChanges;
    }

    public double getDifferenceDetected() {
        return this.differenceDetected;
    }
}

