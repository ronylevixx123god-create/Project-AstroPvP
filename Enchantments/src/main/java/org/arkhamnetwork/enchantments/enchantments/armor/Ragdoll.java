package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Ragdoll extends CustomEnchantment {
    public Ragdoll() {
        super("Ragdoll", GeneralUtil.armor, 3);
        this.max = 4;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player && attacker instanceof LivingEntity) {
            if (attacker instanceof IronGolem) {
                return;
            }
            if (player.getWorld().getEnvironment() == World.Environment.THE_END || GeneralUtil.isKOTHWorld(player.getWorld())) {
                return;
            }
            final Player p = (Player) player;
            if (Math.random() < level * 0.125) {
                GeneralUtil.pushAwayEntity(attacker, (Entity) p, 1.5 + 0.5 * level);
            }
        }
    }

    private void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
