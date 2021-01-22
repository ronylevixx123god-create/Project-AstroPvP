package org.arkhamnetwork.enchantments.enchantments.armor;

import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
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

    @SneakyThrows
    public void run() {
        this.healPlayer(this.p, this.healValue);
    }

    private void healPlayer(final Player p, final double amount) throws Exception {
        if (p.getHealth() == p.getMaxHealth() || p.isDead()) {
            return;
        }
        GeneralUtil.healPlayer(p, amount);
        de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), this.p.getEyeLocation(), 0f, 1f,0f, 0.4f, 14);

    }
}
