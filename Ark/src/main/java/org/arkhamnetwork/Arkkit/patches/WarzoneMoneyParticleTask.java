/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import lombok.SneakyThrows;
import org.bukkit.scheduler.BukkitRunnable;

final class WarzoneMoneyParticleTask
        extends BukkitRunnable {
    WarzoneMoneyParticleTask() {
    }

    @SneakyThrows
    public void run() {
        if (WarzoneMoneyLMSTask.pLMS != null) {
            //ParticleEffect.playEffect(ParticleEffect.RED_DUST, WarzoneMoneyLMSTask.pLMS.getEyeLocation().add(0.0, 0.5, 0.0), 0.7f, 16);
            de.inventivegames.particle.ParticleEffect.RED_DUST.sendToPlayer(WarzoneMoneyLMSTask.pLMS, WarzoneMoneyLMSTask.pLMS.getEyeLocation().add(0.0, 0.5, 0.0), 0, 0, 0, 0.6f, 16);
        }
    }
}

