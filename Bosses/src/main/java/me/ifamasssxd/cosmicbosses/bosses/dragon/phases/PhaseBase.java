/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.DamageSource
 *  net.minecraft.server.v1_7_R4.EntityComplexPart
 *  net.minecraft.server.v1_7_R4.EntityEnderCrystal
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  org.bukkit.Location
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.IPhase;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Location;

public abstract class PhaseBase
implements IPhase {
    protected final EndDragonEntity dragon;

    public PhaseBase(EndDragonEntity dragonIn) {
        this.dragon = dragonIn;
    }

    @Override
    public boolean getIsStationary() {
        return false;
    }

    public void doClientRenderEffects() {
    }

    @Override
    public void doLocalUpdate() {
    }

    @Override
    public void onCrystalDestroyed(EntityEnderCrystal crystal, Location pos, DamageSource dmgSrc, EntityPlayer plyr) {
    }

    @Override
    public void initPhase() {
    }

    @Override
    public void removeAreaEffect() {
    }

    @Override
    public float getMaxRiseOrFall() {
        return 0.6f;
    }

    @Override
    public Vec3D getTargetLocation() {
        return null;
    }

    @Override
    public float getAdjustedDamage(EntityComplexPart pt, DamageSource src, float damage) {
        return damage;
    }

    @Override
    public float getYawFactor() {
        float f = MathHelper.sqrt((double)(this.dragon.motX * this.dragon.motX + this.dragon.motZ * this.dragon.motZ)) + 1.0f;
        float f1 = Math.min(f, 40.0f);
        return 0.7f / f1 / f;
    }
}

