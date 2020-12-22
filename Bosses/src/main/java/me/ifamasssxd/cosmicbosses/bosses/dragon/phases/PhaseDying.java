/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  org.bukkit.Location
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Location;

public class PhaseDying
extends PhaseBase {
    private Vec3D targetLocation;
    private int time;

    public PhaseDying(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doClientRenderEffects() {
        if (this.time++ % 10 == 0) {
            float f = (this.dragon.random.nextFloat() - 0.5f) * 8.0f;
            float f1 = (this.dragon.random.nextFloat() - 0.5f) * 4.0f;
            float f2 = (this.dragon.random.nextFloat() - 0.5f) * 8.0f;
            this.dragon.world.addParticle("largeexplode", this.dragon.locX + (double)f, this.dragon.locY + 2.0 + (double)f1, this.dragon.locZ + (double)f2, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void doLocalUpdate() {
        double d0;
        ++this.time;
        if (this.targetLocation == null) {
            Location home = this.dragon.homeLocation;
            this.targetLocation = Vec3D.a((double)home.getX(), (double)home.getY(), (double)home.getZ());
        }
        if ((d0 = this.targetLocation.d(this.dragon.locX, this.dragon.locY, this.dragon.locZ)) >= 100.0 && d0 <= 22500.0 && !this.dragon.bA) {
            this.dragon.setHealth(1.0f);
        } else {
            this.dragon.setHealth(0.0f);
        }
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
        this.time = 0;
    }

    @Override
    public float getMaxRiseOrFall() {
        return 3.0f;
    }

    @Override
    @Nullable
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseList<PhaseDying> getType() {
        return PhaseList.DYING;
    }
}

