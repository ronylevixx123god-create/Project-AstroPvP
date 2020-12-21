/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Vehicle;

public interface Boat
extends Vehicle {
    public double getMaxSpeed();

    public void setMaxSpeed(double var1);

    public double getOccupiedDeceleration();

    public void setOccupiedDeceleration(double var1);

    public double getUnoccupiedDeceleration();

    public void setUnoccupiedDeceleration(double var1);

    public boolean getWorkOnLand();

    public void setWorkOnLand(boolean var1);
}

