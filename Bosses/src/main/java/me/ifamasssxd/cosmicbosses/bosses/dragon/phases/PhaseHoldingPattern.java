/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.DamageSource
 *  net.minecraft.server.v1_7_R4.EntityEnderCrystal
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  org.bukkit.Location
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.struct.BossPath;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Location;

public class PhaseHoldingPattern
extends PhaseBase {
    private BossPath currentPath;
    private Vec3D targetLocation;
    private boolean clockwise;

    public PhaseHoldingPattern(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    public PhaseList<PhaseHoldingPattern> getType() {
        return PhaseList.HOLDING_PATTERN;
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
    public void initPhase() {
        this.currentPath = null;
        this.targetLocation = null;
    }

    @Override
    @Nullable
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    private void findNewTarget() {
        if (this.currentPath != null && this.currentPath.isFinished()) {
            int secondsSince;
            int offset;
            Location blockpos = this.dragon.homeLocation;
            int i = this.dragon.getFightManager() == null ? 2 : this.dragon.getFightManager().getNumAliveCrystals() + 2;
            if (this.dragon.random.nextInt(i + 3 + (offset = Math.max(0, 15 - (secondsSince = (int)((System.currentTimeMillis() - this.dragon.timeSinceLastApproach) / 1000L))))) == 0) {
                this.dragon.timeSinceLastApproach = System.currentTimeMillis();
                this.dragon.getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
                return;
            }
            double d0 = 128.0;
            EntityHuman entityplayer = this.dragon.world.findNearbyVulnerablePlayer(blockpos.getX(), blockpos.getY(), blockpos.getZ(), d0);
            if (entityplayer != null) {
                d0 = entityplayer.e((double)blockpos.getBlockX(), (double)blockpos.getBlockY(), (double)blockpos.getBlockZ()) / 512.0;
            }
            if (entityplayer != null && (this.dragon.random.nextInt(Math.abs((int)d0) + 2) == 0 || this.dragon.random.nextInt(i + 2) == 0)) {
                this.strafePlayer((EntityPlayer)entityplayer);
                return;
            }
        }
        if (this.currentPath == null || this.currentPath.isFinished()) {
            int j;
            int k = j = this.dragon.initPathPoints();
            if (this.dragon.random.nextInt(8) == 0) {
                this.clockwise = !this.clockwise;
                k = j + 6;
            }
            k = this.clockwise ? ++k : --k;
            if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() >= 0) {
                if ((k %= 12) < 0) {
                    k += 12;
                }
            } else {
                k -= 12;
                k &= 7;
                k += 12;
            }
            this.currentPath = this.dragon.findPath(j, k, null);
            if (this.currentPath != null) {
                this.currentPath.incrementPathIndex();
            }
        }
        this.navigateToNextPathNode();
    }

    private void strafePlayer(EntityPlayer player) {
        this.dragon.getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
        this.dragon.getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER).setTarget((EntityLiving)player);
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

    @Override
    public void onCrystalDestroyed(EntityEnderCrystal crystal, Location pos, DamageSource dmgSrc, EntityPlayer plyr) {
        if (plyr != null && !plyr.abilities.isInvulnerable) {
            this.strafePlayer(plyr);
        }
    }
}

