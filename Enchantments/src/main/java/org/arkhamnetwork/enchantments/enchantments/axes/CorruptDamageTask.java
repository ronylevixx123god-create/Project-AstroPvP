package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class CorruptDamageTask extends BukkitRunnable {
    Player p;
    long durationTicks;
    double dmgValue;

    public CorruptDamageTask(final Player p, final long durationTicks, final double dmgAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.dmgValue = dmgAmount;
    }

    @SneakyThrows
    public void run() {
        this.dmgPlayer(this.p, this.dmgValue);
    }

    private void dmgPlayer(final Player p, final double amount) throws Exception {
        if (p.getHealth() <= 0.0 || p.isDead()) {
            return;
        }
        p.damage(amount);
        ParticleEffect.RED_DUST.sendToPlayer(p.getPlayer(),p.getLocation(), 0,0, 0,1.5f,10);
    }
}
