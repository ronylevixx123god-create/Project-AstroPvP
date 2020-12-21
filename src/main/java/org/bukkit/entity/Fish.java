/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Projectile;

public interface Fish
extends Projectile {
    public double getBiteChance();

    public void setBiteChance(double var1) throws IllegalArgumentException;
}

