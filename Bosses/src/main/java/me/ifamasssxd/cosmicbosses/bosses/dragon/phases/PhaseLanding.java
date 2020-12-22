/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.Vec3D
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PhaseLanding
extends PhaseBase {
    private Vec3D targetLocation;

    public PhaseLanding(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doClientRenderEffects() {
        Vec3D vec3d = this.dragon.j(1.0f).a();
        vec3d.b(-0.7853982f);
        double d0 = this.dragon.head.locX;
        double d1 = this.dragon.head.locY + (double)(this.dragon.head.height / 2.0f);
        double d2 = this.dragon.head.locZ;
        for (int i = 0; i < 8; ++i) {
            double d3 = d0 + this.dragon.random.nextGaussian() / 2.0;
            double d4 = d1 + this.dragon.random.nextGaussian() / 2.0;
            double d5 = d2 + this.dragon.random.nextGaussian() / 2.0;
            this.dragon.world.addParticle("portal", d3, d4, d5, -vec3d.a * (double)0.08f + this.dragon.motX, -vec3d.b * (double)0.3f + this.dragon.motY, -vec3d.c * (double)0.08f + this.dragon.motZ);
            vec3d.b(0.19634955f);
        }
    }

    @Override
    public void doLocalUpdate() {
        if (this.targetLocation == null) {
            this.targetLocation = Vec3D.a((double)this.dragon.homeLocation.getX(), (double)this.dragon.homeLocation.getY(), (double)this.dragon.homeLocation.getZ());
        }
        this.doClientRenderEffects();
        if (this.targetLocation.d(this.dragon.locX, this.dragon.locY, this.dragon.locZ) < 1.0) {
            this.dragon.getPhaseManager().getPhase(PhaseList.SITTING_FLAMING).resetFlameCount();
            this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
        }
    }

    @Override
    public float getMaxRiseOrFall() {
        return 1.5f;
    }

    @Override
    public float getYawFactor() {
        float f = (float)(Math.sqrt(this.dragon.motX * this.dragon.motX + this.dragon.motZ * this.dragon.motZ) + 1.0);
        float f1 = Math.min(f, 40.0f);
        return f1 / f;
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
    }

    @Override
    @Nullable
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseList<PhaseLanding> getType() {
        return PhaseList.LANDING;
    }
}

