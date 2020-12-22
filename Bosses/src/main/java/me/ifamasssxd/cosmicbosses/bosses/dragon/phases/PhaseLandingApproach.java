/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.EntityHuman
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
import me.ifamasssxd.cosmicbosses.struct.BossPathPoint;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PhaseLandingApproach
extends PhaseBase {
    private BossPath currentPath;
    private Vec3D targetLocation;

    public PhaseLandingApproach(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    public PhaseList<PhaseLandingApproach> getType() {
        return PhaseList.LANDING_APPROACH;
    }

    @Override
    public void initPhase() {
        this.currentPath = null;
        this.targetLocation = null;
    }

    @Override
    public void doLocalUpdate() {
        double d0;
        double d = d0 = this.targetLocation == null ? 0.0 : this.targetLocation.d(this.dragon.locX, this.dragon.locY, this.dragon.locZ);
        if (d0 < 100.0 || d0 > 22500.0 || this.dragon.bA) {
            this.findNewTarget();
        }
    }

    @Override
    @Nullable
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    private void findNewTarget() {
        if (this.currentPath == null || this.currentPath.isFinished()) {
            int j;
            int i = this.dragon.initPathPoints();
            Location blockloc = this.dragon.homeLocation;
            EntityHuman entityplayer = this.dragon.world.findNearbyVulnerablePlayer(blockloc.getX(), blockloc.getY(), blockloc.getZ(), 128.0);
            if (entityplayer != null) {
                Vec3D vec3d = Vec3D.a((double)entityplayer.locX, (double)0.0, (double)entityplayer.locZ).a();
                j = this.dragon.getNearestPpIdx(entityplayer.locX, 105.0, entityplayer.locZ);
                Bukkit.getLogger().info("Searching for player landing path: " + entityplayer.locX + " , z: " + entityplayer.locZ);
            } else {
                j = this.dragon.getNearestPpIdx(this.dragon.getHomeLocation().getX(), blockloc.getY(), 0.0 + this.dragon.getHomeLocation().getZ());
            }
            BossPathPoint pathpoint = new BossPathPoint(blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ());
            this.currentPath = this.dragon.findPath(i, j, pathpoint);
            if (this.currentPath != null) {
                this.currentPath.incrementPathIndex();
            }
        }
        this.navigateToNextPathNode();
        if (this.currentPath != null && this.currentPath.isFinished()) {
            this.dragon.getPhaseManager().setPhase(PhaseList.LANDING);
        }
    }

    private void navigateToNextPathNode() {
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            double d2;
            Vec3D vec3d = this.currentPath.getCurrentPos();
            this.currentPath.incrementPathIndex();
            double d0 = vec3d.a;
            double d1 = vec3d.c;
            while (!((d2 = vec3d.b + (double)(this.dragon.random.nextFloat() * 20.0f)) >= vec3d.b)) {
            }
            this.targetLocation = Vec3D.a((double)d0, (double)d2, (double)d1);
        }
    }
}

