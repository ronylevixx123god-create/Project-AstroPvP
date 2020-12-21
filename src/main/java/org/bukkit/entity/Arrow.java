/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Projectile;

public interface Arrow
extends Projectile {
    public int getKnockbackStrength();

    public void setKnockbackStrength(int var1);

    public boolean isCritical();

    public void setCritical(boolean var1);
}

