/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.scheduler.BukkitRunnable;

final class WarzoneMoneyParticleTask
        extends BukkitRunnable {
    WarzoneMoneyParticleTask() {
    }

    public void run() {
        if (WarzoneMoneyLMSTask.pLMS != null) {
            ParticleEffect.playEffect(ParticleEffect.RED_DUST, WarzoneMoneyLMSTask.pLMS.getEyeLocation().add(0.0, 0.5, 0.0), 0.7f, 16);
        }
    }
}

