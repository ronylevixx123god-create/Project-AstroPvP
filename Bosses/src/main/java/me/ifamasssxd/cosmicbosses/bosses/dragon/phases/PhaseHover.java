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

public class PhaseHover
extends PhaseBase {
    private Vec3D targetLocation;

    public PhaseHover(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doLocalUpdate() {
        if (this.targetLocation == null) {
            this.targetLocation = Vec3D.a((double)this.dragon.locX, (double)this.dragon.locY, (double)this.dragon.locZ);
        }
    }

    @Override
    public boolean getIsStationary() {
        return true;
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
    }

    @Override
    public float getMaxRiseOrFall() {
        return 1.0f;
    }

    @Override
    @Nullable
    public Vec3D getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseList<PhaseHover> getType() {
        return PhaseList.HOVER;
    }
}

