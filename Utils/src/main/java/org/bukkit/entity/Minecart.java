/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;

public interface Minecart
extends Vehicle {
    @Deprecated
    public void _INVALID_setDamage(int var1);

    public void setDamage(double var1);

    @Deprecated
    public int _INVALID_getDamage();

    public double getDamage();

    public double getMaxSpeed();

    public void setMaxSpeed(double var1);

    public boolean isSlowWhenEmpty();

    public void setSlowWhenEmpty(boolean var1);

    public Vector getFlyingVelocityMod();

    public void setFlyingVelocityMod(Vector var1);

    public Vector getDerailedVelocityMod();

    public void setDerailedVelocityMod(Vector var1);
}

