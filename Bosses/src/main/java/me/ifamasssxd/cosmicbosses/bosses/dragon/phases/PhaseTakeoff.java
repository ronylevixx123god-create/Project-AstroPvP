/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.struct.BossPath;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PhaseTakeoff
extends PhaseBase {
    private boolean firstTick;
    private BossPath currentPath;
    private Vec3D targetLocation;

    public PhaseTakeoff(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doLocalUpdate() {
        if (!this.firstTick && this.currentPath != null) {
            Location blockpos = this.dragon.homeLocation;
            double d0 = this.dragon.distanceSqToCenter(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            if (d0 > 100.0) {
                this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
            }
        } else {
            this.firstTick = false;
            this.findNewTarget();
        }
    }

    @Override
    public void initPhase() {
        this.firstTick = true;
        this.currentPath = null;
        this.targetLocation = null;
    }

    private void findNewTarget() {
        int i = this.dragon.initPathPoints();
        Vec3D vec3d = this.dragon.getHeadLookVec(1.0f);
        Bukkit.getLogger().info("Vec3d HeadLook: " + vec3d.a + ", b" + vec3d.b + " z: " + vec3d.c);
        int j = this.dragon.getNearestPpIdx(-vec3d.a * this.dragon.getHomeLocation().getX(), 105.0, -vec3d.c * this.dragon.getHomeLocation().getZ());
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
            this.navigateToNextPathNode();
        }
    }

    private void navigateToNextPathNode() {
        double d0;
        Vec3D vec3d = this.currentPath.getCurrentPos();
        this.currentPath.incrementPathIndex();
        while (!((d0 = vec3d.b + (double)(this.dragon.random.nextFloat() * 20.0f)) >= vec3d.b)) {
        }
        this.targetLocation = Vec3D.a((double)vec3d.a, (double)d0, (double)vec3d.c);
    }

    @Override
    @Nullable
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseList<PhaseTakeoff> getType() {
        return PhaseList.TAKEOFF;
    }
}

