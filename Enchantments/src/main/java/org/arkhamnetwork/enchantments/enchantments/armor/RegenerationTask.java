package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class RegenerationTask extends BukkitRunnable {
    Player p;
    long durationTicks;
    long delayTicks;
    double healValue;

    RegenerationTask(final Player p, final long durationTicks, final long delayTicks, final double healAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.delayTicks = delayTicks;
        this.healValue = healAmount;
    }

    public void run() {
        this.healPlayer(this.p, this.healValue);
    }

    private void healPlayer(final Player p, final double amount) {
        if (p.getHealth() == p.getMaxHealth() || p.isDead()) {
            return;
        }
        GeneralUtil.healPlayer((LivingEntity) p, amount);
        ParticleEffect.playEffect(ParticleEffect.SPELL, this.p.getEyeLocation().add(0.0, 0.5, 0.0), 0.04f, 15);
    }
}
