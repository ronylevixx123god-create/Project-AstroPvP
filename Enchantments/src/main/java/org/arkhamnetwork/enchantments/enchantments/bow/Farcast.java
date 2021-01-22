package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Farcast extends CustomEnchantment {
    public Farcast() {
        super("Farcast", new Material[]{Material.BOW}, 3);
        this.max = 5;
        this.base = 17.0;
        this.interval = 4.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity entityHit, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final float procChance = 0.1f + (float) (0.025 * level / Math.max(0.25, entityHit.getHealth() / entityHit.getMaxHealth()));
        if (Math.random() <= procChance && attacker != null && !GeneralUtil.canEffectEntity((Entity) entityHit)) {
            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), attacker.getLocation(), 0,1,0,1f,3);
            GeneralUtil.pushAwayEntity(entityHit, (Entity) attacker, 2.3);
        }
    }

    private void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
