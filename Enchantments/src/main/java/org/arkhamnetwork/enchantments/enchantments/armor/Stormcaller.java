package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Stormcaller extends CustomEnchantment {
    public Stormcaller() {
        super("Stormcaller", GeneralUtil.armor, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.045 * level;
        if (random < chance && attacker != null) {
            if (attacker.equals(player)) {
                return;
            }
            player.getWorld().strikeLightning(attacker.getLocation());
            player.damage(10.0);
            GeneralUtil.pushAwayEntity(player, (Entity) attacker, 1.5);
        }
    }

    private void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
