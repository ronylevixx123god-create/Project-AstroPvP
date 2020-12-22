/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.IPhase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseChargingPlayer;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseDying;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseHoldingPattern;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseHover;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseLanding;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseLandingApproach;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseSittingAttacking;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseSittingFlaming;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseSittingScanning;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseStrafePlayer;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseTakeoff;

public class PhaseList<T extends IPhase> {
    private static PhaseList<?>[] phases = new PhaseList[0];
    public static final PhaseList<PhaseHoldingPattern> HOLDING_PATTERN = PhaseList.create(PhaseHoldingPattern.class, "HoldingPattern");
    public static final PhaseList<PhaseStrafePlayer> STRAFE_PLAYER = PhaseList.create(PhaseStrafePlayer.class, "StrafePlayer");
    public static final PhaseList<PhaseLandingApproach> LANDING_APPROACH = PhaseList.create(PhaseLandingApproach.class, "LandingApproach");
    public static final PhaseList<PhaseLanding> LANDING = PhaseList.create(PhaseLanding.class, "Landing");
    public static final PhaseList<PhaseTakeoff> TAKEOFF = PhaseList.create(PhaseTakeoff.class, "Takeoff");
    public static final PhaseList<PhaseSittingFlaming> SITTING_FLAMING = PhaseList.create(PhaseSittingFlaming.class, "SittingFlaming");
    public static final PhaseList<PhaseSittingScanning> SITTING_SCANNING = PhaseList.create(PhaseSittingScanning.class, "SittingScanning");
    public static final PhaseList<PhaseSittingAttacking> SITTING_ATTACKING = PhaseList.create(PhaseSittingAttacking.class, "SittingAttacking");
    public static final PhaseList<PhaseChargingPlayer> CHARGING_PLAYER = PhaseList.create(PhaseChargingPlayer.class, "ChargingPlayer");
    public static final PhaseList<PhaseDying> DYING = PhaseList.create(PhaseDying.class, "Dying");
    public static final PhaseList<PhaseHover> HOVER = PhaseList.create(PhaseHover.class, "Hover");
    private final Class<? extends IPhase> clazz;
    private final int id;
    private final String name;

    private PhaseList(int idIn, Class<? extends IPhase> clazzIn, String nameIn) {
        this.id = idIn;
        this.clazz = clazzIn;
        this.name = nameIn;
    }

    public IPhase createPhase(EndDragonEntity dragon) {
        try {
            Constructor<IPhase> constructor = this.getConstructor();
            return constructor.newInstance(new Object[]{dragon});
        }
        catch (Exception exception) {
            throw new Error(exception);
        }
    }

    protected Constructor<? extends IPhase> getConstructor() throws NoSuchMethodException {
        return this.clazz.getConstructor(EndDragonEntity.class);
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.name + " (#" + this.id + ")";
    }

    public static PhaseList<?> getById(int idIn) {
        return idIn >= 0 && idIn < phases.length ? phases[idIn] : HOLDING_PATTERN;
    }

    public static int getTotalPhases() {
        return phases.length;
    }

    private static <T extends IPhase> PhaseList<T> create(Class<T> phaseIn, String nameIn) {
        PhaseList<T> phaselist = new PhaseList<T>(phases.length, phaseIn, nameIn);
        phases = Arrays.copyOf(phases, phases.length + 1);
        PhaseList.phases[phaselist.getId()] = phaselist;
        return phaselist;
    }
}

