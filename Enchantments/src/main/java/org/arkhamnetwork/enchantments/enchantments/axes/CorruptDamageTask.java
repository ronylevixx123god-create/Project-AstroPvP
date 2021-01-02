package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class CorruptDamageTask extends BukkitRunnable {
    Player p;
    long durationTicks;
    double dmgValue;

    CorruptDamageTask(final Player p, final long durationTicks, final double dmgAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.dmgValue = dmgAmount;
    }

    public void run() {
        this.dmgPlayer(this.p, this.dmgValue);
    }

    private void dmgPlayer(final Player p, final double amount) {
        if (p.getHealth() <= 0.0 || p.isDead()) {
            return;
        }
        p.damage(amount);
        WorldUtil.sendWorldEffect(p.getLocation().add(0.0, 1.0, 0.0).getBlock(), Material.REDSTONE_WIRE.getId(), 2001);
    }
}
