/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.DyeColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Tameable;

public interface Wolf
extends Animals,
Tameable {
    public boolean isAngry();

    public void setAngry(boolean var1);

    public boolean isSitting();

    public void setSitting(boolean var1);

    public DyeColor getCollarColor();

    public void setCollarColor(DyeColor var1);
}

