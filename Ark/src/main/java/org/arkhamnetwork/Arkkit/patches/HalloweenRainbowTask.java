/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.arkhamnetwork.enchantments.util.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class HalloweenRainbowTask
        extends BukkitRunnable {
    private final Player player;
    public int sugarLevel = 0;
    public long endTime;

    public HalloweenRainbowTask(Player p, int sugarLevel) {
        this.sugarLevel = sugarLevel;
        this.player = p;
        this.endTime = System.currentTimeMillis() + (long) (1000 * (sugarLevel + 5));
    }

    @SneakyThrows
    public void run() {
        if (this.endTime < System.currentTimeMillis()) {
            HalloweenListener.setSugarLevel(this.player, 0);
            this.cancel();
            this.player.removeMetadata("sugarTask", Arkkit.get());
            return;
        }
        if (this.player.hasMetadata("spectator")) {
            return;
        }
        //ParticleEffect.playEffect(ParticleEffect.RED_DUST, this.player.getLocation().add(0.0, 1.0, 0.0), 0.8f, this.sugarLevel * 5);
        ParticleEffect.RED_DUST.sendToPlayer(this.player, this.player.getLocation(), 0, 1, 0, 0.8f, this.sugarLevel * 5);
    }
}

