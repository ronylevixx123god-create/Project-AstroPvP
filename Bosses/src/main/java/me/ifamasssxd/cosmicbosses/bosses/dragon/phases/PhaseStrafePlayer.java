/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityLargeFireball
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.MovingObjectPosition
 *  net.minecraft.server.v1_8_R3.Vec3D
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.struct.BossPath;
import me.ifamasssxd.cosmicbosses.struct.BossPathPoint;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLargeFireball;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseStrafePlayer
extends PhaseBase {
    private static final Logger LOGGER = LogManager.getLogger();
    private int fireballCharge;
    private BossPath currentPath;
    private Vec3D targetLocation;
    private EntityLiving attackTarget;
    private boolean holdingPatternClockwise;

    public PhaseStrafePlayer(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doLocalUpdate() {
        if (this.attackTarget == null) {
            LOGGER.warn("Skipping player strafe phase because no player was found");
            this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        } else {
            double d12;
            if (this.currentPath != null && this.currentPath.isFinished()) {
                double d0 = this.attackTarget.locX;
                double d1 = this.attackTarget.locZ;
                double d2 = d0 - this.dragon.locX;
                double d3 = d1 - this.dragon.locZ;
                double d4 = Math.sqrt(d2 * d2 + d3 * d3);
                double d5 = Math.min((double)0.4f + d4 / 80.0 - 1.0, 10.0);
                this.targetLocation = Vec3D.a((double)d0, (double)(this.attackTarget.locY + d5), (double)d1);
            }
            double d = d12 = this.targetLocation == null ? 0.0 : this.targetLocation.d(this.dragon.locX, this.dragon.locY, this.dragon.locZ);
            if (d12 < 100.0 || d12 > 22500.0) {
                this.findNewTarget();
            }
            double d13 = 64.0;
            if (this.attackTarget.f((Entity)this.dragon) < 4096.0) {
                if (this.dragon.hasLineOfSight((Entity)this.attackTarget)) {
                    ++this.fireballCharge;
                    Vec3D vec3d1 = Vec3D.a((double)(this.attackTarget.locX - this.dragon.locX), (double)0.0, (double)(this.attackTarget.locZ - this.dragon.locZ)).a();
                    Vec3D vec3d = Vec3D.a((double)Math.sin(this.dragon.yaw * ((float)Math.PI / 180)), (double)0.0, (double)(-Math.cos(this.dragon.yaw * ((float)Math.PI / 180)))).a();
                    float f1 = (float)vec3d.b(vec3d1);
                    float f = (float)(Math.acos(f1) * 57.29577951308232);
                    f += 0.5f;
                    if (this.fireballCharge >= 3 && f >= 0.0f && f < 10.0f) {
                        double d14 = 1.0;
                        Vec3D vec3d2 = this.dragon.j(1.0f);
                        double d6 = this.dragon.head.locX - vec3d2.a * 1.0;
                        double d7 = this.dragon.head.locY + (double)(this.dragon.head.height / 2.0f) + 0.5;
                        double d8 = this.dragon.head.locZ - vec3d2.c * 1.0;
                        double d9 = this.attackTarget.locX - d6;
                        double d10 = this.attackTarget.locY + (double)(this.attackTarget.height / 2.0f) - (d7 + (double)(this.dragon.head.height / 2.0f));
                        double d11 = this.attackTarget.locZ - d8;
                        EntityLargeFireball entitydragonfireball = new EntityLargeFireball(this.dragon.world, (EntityLiving)this.dragon, d9, d10, d11){

                            protected void a(MovingObjectPosition movingobjectposition) {
                                if (movingobjectposition.entity != null && !(movingobjectposition.entity instanceof EntityHuman)) {
                                    return;
                                }
                                super.a(movingobjectposition);
                            }
                        };
                        entitydragonfireball.setPositionRotation(d6, d7, d8, 0.0f, 0.0f);
                        this.dragon.world.addEntity((Entity)entitydragonfireball);
                        this.fireballCharge = 0;
                        entitydragonfireball.isIncendiary = false;
                        if (this.currentPath != null) {
                            while (!this.currentPath.isFinished()) {
                                this.currentPath.incrementPathIndex();
                            }
                        }
                        this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
                    }
                } else if (this.fireballCharge > 0) {
                    --this.fireballCharge;
                }
            } else if (this.fireballCharge > 0) {
                --this.fireballCharge;
            }
        }
    }

    private void findNewTarget() {
        if (this.currentPath == null || this.currentPath.isFinished()) {
            int i;
            int j = i = this.dragon.initPathPoints();
            if (this.dragon.random.nextInt(8) == 0) {
                this.holdingPatternClockwise = !this.holdingPatternClockwise;
                j = i + 6;
            }
            j = this.holdingPatternClockwise ? ++j : --j;
            if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() > 0) {
                if ((j %= 12) < 0) {
                    j += 12;
                }
            } else {
                j -= 12;
                j &= 7;
                j += 12;
            }
            this.currentPath = this.dragon.findPath(i, j, null);
            if (this.currentPath != null) {
                this.currentPath.incrementPathIndex();
            }
        }
        this.navigateToNextPathNode();
    }

    private void navigateToNextPathNode() {
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            double d1;
            Vec3D vec3d = this.currentPath.getCurrentPos();
            this.currentPath.incrementPathIndex();
            double d0 = vec3d.a;
            double d2 = vec3d.c;
            while (!((d1 = vec3d.b + (double)(this.dragon.random.nextFloat() * 20.0f)) >= vec3d.b)) {
            }
            this.targetLocation = Vec3D.a((double)d0, (double)d1, (double)d2);
        }
    }

    @Override
    public void initPhase() {
        this.fireballCharge = 0;
        this.targetLocation = null;
        this.currentPath = null;
        this.attackTarget = null;
        this.dragon.target = null;
    }

    public void setTarget(EntityLiving target) {
        this.attackTarget = target;
        this.dragon.target = target;
        int i = this.dragon.initPathPoints();
        int j = this.dragon.getNearestPpIdx(this.attackTarget.locX, this.attackTarget.locY, this.attackTarget.locZ);
        int k = (int)Math.floor(this.attackTarget.locX);
        int l = (int)Math.floor(this.attackTarget.locZ);
        double d0 = (double)k - this.dragon.locX;
        double d1 = (double)l - this.dragon.locZ;
        double d2 = Math.sqrt(d0 * d0 + d1 * d1);
        double d3 = Math.min((double)0.4f + d2 / 80.0 - 1.0, 10.0);
        int i1 = (int)Math.floor(this.attackTarget.locY + d3);
        BossPathPoint pathpoint = new BossPathPoint(k, i1, l);
        this.currentPath = this.dragon.findPath(i, j, pathpoint);
        if (this.currentPath != null) {
            this.currentPath.incrementPathIndex();
            this.navigateToNextPathNode();
        }
    }

    @Override
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseList<PhaseStrafePlayer> getType() {
        return PhaseList.STRAFE_PLAYER;
    }
}

