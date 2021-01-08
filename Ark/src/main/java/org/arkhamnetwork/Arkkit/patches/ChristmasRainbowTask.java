/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class ChristmasRainbowTask
        extends BukkitRunnable {
    private final Player player;
    public int christmasSugarLevel = 0;
    public long endTime;

    public ChristmasRainbowTask(Player p, int christmasSugarLevel) {
        this.christmasSugarLevel = christmasSugarLevel;
        this.player = p;
        this.endTime = System.currentTimeMillis() + (long) (1000 * (christmasSugarLevel + 5));
    }

    public void run() {
        if (this.endTime < System.currentTimeMillis()) {
            ChristmasListener.setSugarLevel(this.player, 0);
            this.cancel();
            return;
        }
        ParticleEffect.playEffect(ParticleEffect.RED_DUST, this.player.getLocation().add(0.0, 1.0, 0.0), 0.8f, this.christmasSugarLevel * 5);
    }
}

