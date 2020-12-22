/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseBase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseChargingPlayer
extends PhaseBase {
    private static final Logger LOGGER = LogManager.getLogger();
    private Vec3D targetLocation;
    private int timeSinceCharge;

    public PhaseChargingPlayer(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doLocalUpdate() {
        if (this.targetLocation == null) {
            LOGGER.warn("Aborting charge player as no target was set.");
            this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        } else if (this.timeSinceCharge > 0 && this.timeSinceCharge++ >= 10) {
            this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        } else {
            double d0 = this.targetLocation.d(this.dragon.locX, this.dragon.locY, this.dragon.locZ);
            if (d0 < 100.0 || d0 > 22500.0 || this.dragon.bA) {
                ++this.timeSinceCharge;
            }
        }
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
        this.timeSinceCharge = 0;
    }

    public void setTarget(Vec3D target) {
        this.targetLocation = target;
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

    public PhaseList<PhaseChargingPlayer> getType() {
        return PhaseList.CHARGING_PLAYER;
    }
}

