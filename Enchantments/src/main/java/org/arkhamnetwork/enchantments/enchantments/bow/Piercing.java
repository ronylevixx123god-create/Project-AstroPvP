package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Piercing extends CustomEnchantment {
    public Piercing() {
        super("Piercing", new Material[]{Material.BOW}, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double armor_percent_ignored = level * 2.5;
        if (e.isCancelled() || !(playerHit instanceof Player)) {
            return;
        }
        if (playerHit.hasMetadata("lastArrowDamageEvent")) {
            final long minDelayMilliseconds = level * 200;
            final long last = playerHit.getMetadata("lastArrowDamageEvent").get(0).asLong();
            if (System.currentTimeMillis() - last <= minDelayMilliseconds) {
                return;
            }
        }
        final double extra_dmg = armor_percent_ignored / 100.0 * e.getDamage(EntityDamageEvent.DamageModifier.BASE);
        playerHit.damage(extra_dmg);
        ((Player) player).playSound(player.getLocation(), Sound.ARROW_HIT, 1.2f, 0.6f);
        ParticleEffect.sendToLocation(ParticleEffect.CRIT, playerHit.getLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 20);
    }
}
