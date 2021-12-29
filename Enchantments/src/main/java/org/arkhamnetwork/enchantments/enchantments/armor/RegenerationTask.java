/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class RegenerationTask
extends BukkitRunnable {
    Player p;
    long durationTicks;
    long delayTicks;
    double healValue;

    RegenerationTask(Player p, long durationTicks, long delayTicks, double healAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.delayTicks = delayTicks;
        this.healValue = healAmount;
    }

    public void run() {
        this.healPlayer(this.p, this.healValue);
    }

    private void healPlayer(Player p, double amount) {
        if (p.getHealth() == p.getMaxHealth() || p.isDead()) {
            return;
        }
        GeneralUtil.healPlayer((LivingEntity)p, amount);
        try {
            ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), this.p.getEyeLocation().add(0.0, 0.5, 0.0), 0.04f, 0.04f, 0.04f, 0.1f, 15);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

