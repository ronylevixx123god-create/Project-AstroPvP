/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;

public class DragonFireball
extends BossAbility<EndDragon> {
    public DragonFireball(EndDragon boss) {
        super(boss);
    }

    @Override
    public void onAbility() {
        EndDragonEntity entity = ((EndDragon)this.boss).getNMSEntity();
        if (entity.shootFireball()) {
            this.activateCooldown();
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(3L) + (long)ThreadLocalRandom.current().nextInt(2000);
    }

    @Override
    public String getAbilityName() {
        return "Firespit";
    }
}

