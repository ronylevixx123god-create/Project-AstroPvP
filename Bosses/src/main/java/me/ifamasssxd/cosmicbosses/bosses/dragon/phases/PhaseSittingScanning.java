/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.Vec3D
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseSittingBase;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PhaseSittingScanning
extends PhaseSittingBase {
    private int scanningTime;

    public PhaseSittingScanning(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doLocalUpdate() {
        ++this.scanningTime;
        EntityHuman entitylivingbase = this.dragon.world.findNearbyVulnerablePlayer((Entity)this.dragon, 20.0);
        if (entitylivingbase != null) {
            if (this.dragon.ticksLived % 30 == 0) {
                this.dragon.shootTNT();
            }
            if (this.scanningTime > 40) {
                this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_ATTACKING);
                this.dragon.shootTNT();
            } else {
                Vec3D vec3d = Vec3D.a((double)(entitylivingbase.locX - this.dragon.locX), (double)0.0, (double)(entitylivingbase.locZ - this.dragon.locZ)).a();
                Vec3D vec3d1 = Vec3D.a((double)MathHelper.sin((float)(this.dragon.yaw * ((float)Math.PI / 180))), (double)0.0, (double)(-MathHelper.cos((float)(this.dragon.yaw * ((float)Math.PI / 180))))).a();
                float f = (float)vec3d1.b(vec3d);
                float f1 = (float)(Math.acos(f) * 57.29577951308232) + 0.5f;
                if (f1 < 0.0f || f1 > 10.0f) {
                    float f2;
                    double d0 = entitylivingbase.locX - this.dragon.head.locX;
                    double d1 = entitylivingbase.locZ - this.dragon.head.locZ;
                    double d2 = MathHelper.a((double)MathHelper.g((double)(180.0 - Math.atan2(d0, d1) * 57.29577951308232 - (double)this.dragon.yaw)), (double)-100.0, (double)100.0);
                    this.dragon.setRandomYawVelocity(this.dragon.getRandomYawVelocity() * 0.8f);
                    float f3 = f2 = MathHelper.sqrt((double)(d0 * d0 + d1 * d1)) + 1.0f;
                    if (f2 > 40.0f) {
                        f2 = 40.0f;
                    }
                    this.dragon.setRandomYawVelocity((float)((double)this.dragon.getRandomYawVelocity() + d2 * (double)(0.7f / f2 / f3)));
                    this.dragon.yaw += this.dragon.getRandomYawVelocity();
                }
            }
        } else if (this.scanningTime >= 60) {
            entitylivingbase = this.dragon.world.findNearbyVulnerablePlayer((Entity)this.dragon, 100.0);
            this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
            this.dragon.shootTNT();
            if (entitylivingbase != null) {
                this.dragon.getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
                this.dragon.getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER).setTarget(Vec3D.a((double)entitylivingbase.locX, (double)entitylivingbase.locY, (double)entitylivingbase.locZ));
            }
        }
    }

    @Override
    public void initPhase() {
        this.scanningTime = 0;
    }

    public PhaseList<PhaseSittingScanning> getType() {
        return PhaseList.SITTING_SCANNING;
    }
}

