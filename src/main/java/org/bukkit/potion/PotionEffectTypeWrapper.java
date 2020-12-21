/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.potion;

import org.bukkit.potion.PotionEffectType;

public class PotionEffectTypeWrapper
extends PotionEffectType {
    protected PotionEffectTypeWrapper(int id) {
        super(id);
    }

    @Override
    public double getDurationModifier() {
        return this.getType().getDurationModifier();
    }

    @Override
    public String getName() {
        return this.getType().getName();
    }

    public PotionEffectType getType() {
        return PotionEffectType.getById(this.getId());
    }

    @Override
    public boolean isInstant() {
        return this.getType().isInstant();
    }
}

