/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

public interface FallingBlock
extends Entity {
    public Material getMaterial();

    @Deprecated
    public int getBlockId();

    @Deprecated
    public byte getBlockData();

    public boolean getDropItem();

    public void setDropItem(boolean var1);
}

