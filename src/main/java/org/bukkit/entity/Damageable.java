/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Entity;

public interface Damageable
extends Entity {
    public void damage(double var1);

    @Deprecated
    public void _INVALID_damage(int var1);

    public void damage(double var1, Entity var3);

    @Deprecated
    public void _INVALID_damage(int var1, Entity var2);

    public double getHealth();

    @Deprecated
    public int _INVALID_getHealth();

    public void setHealth(double var1);

    @Deprecated
    public void _INVALID_setHealth(int var1);

    public double getMaxHealth();

    @Deprecated
    public int _INVALID_getMaxHealth();

    public void setMaxHealth(double var1);

    @Deprecated
    public void _INVALID_setMaxHealth(int var1);

    public void resetMaxHealth();
}

