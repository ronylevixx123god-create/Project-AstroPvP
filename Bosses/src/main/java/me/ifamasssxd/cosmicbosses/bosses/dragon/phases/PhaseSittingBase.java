/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.DamageSource
 *  net.minecraft.server.v1_7_R4.EntityArrow
 *  net.minecraft.server.v1_7_R4.EntityComplexPart
 *  net.minecraft.server.v1_7_R4.EntityFireball
 *  net.minecraft.server.v1_7_R4.EntityWitherSkull
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityFireball;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;

public abstract class PhaseSittingBase
extends PhaseBase {
    public PhaseSittingBase(EndDragonEntity p_i46794_1_) {
        super(p_i46794_1_);
    }

    @Override
    public boolean getIsStationary() {
        return true;
    }

    @Override
    public float getAdjustedDamage(EntityComplexPart pt, DamageSource src, float damage) {
        if (src.i() instanceof EntityArrow || src.i() instanceof EntityWitherSkull || src.i() instanceof EntityFireball) {
            src.i().setOnFire(1);
            return 0.0f;
        }
        return super.getAdjustedDamage(pt, src, damage);
    }
}

