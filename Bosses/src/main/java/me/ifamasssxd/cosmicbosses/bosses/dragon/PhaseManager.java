/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.IPhase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EndDragonEntity dragon;
    private final IPhase[] phases = new IPhase[PhaseList.getTotalPhases()];
    private IPhase phase;

    public PhaseManager(EndDragonEntity dragonIn) {
        this.dragon = dragonIn;
        this.setPhase(PhaseList.HOVER);
    }

    public void setPhase(PhaseList<?> phaseIn) {
        if (this.phase == null || phaseIn != this.phase.getType()) {
            if (this.phase != null) {
                this.phase.removeAreaEffect();
            }
            this.phase = this.getPhase(phaseIn);
            if (!this.dragon.world.isStatic) {
                this.dragon.phase = this.phase;
            }
            LOGGER.debug("Dragon is now in phase {} on the {}", new Object[]{phaseIn, this.dragon.world.isStatic ? "client" : "server"});
            this.phase.initPhase();
        }
    }

    public IPhase getCurrentPhase() {
        return this.phase;
    }

    public <T extends IPhase> T getPhase(PhaseList<T> phaseIn) {
        int i = phaseIn.getId();
        if (this.phases[i] == null) {
            this.phases[i] = phaseIn.createPhase(this.dragon);
        }
        return (T)this.phases[i];
    }
}

