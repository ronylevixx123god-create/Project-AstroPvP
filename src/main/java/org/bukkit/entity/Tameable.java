/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.AnimalTamer;

public interface Tameable {
    public boolean isTamed();

    public void setTamed(boolean var1);

    public AnimalTamer getOwner();

    public void setOwner(AnimalTamer var1);
}

