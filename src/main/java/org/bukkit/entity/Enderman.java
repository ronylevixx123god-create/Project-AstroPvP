/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Monster;
import org.bukkit.material.MaterialData;

public interface Enderman
extends Monster {
    public MaterialData getCarriedMaterial();

    public void setCarriedMaterial(MaterialData var1);
}

