package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;
import java.util.*;

public class SpiritLink extends CustomEnchantment
{
    public SpiritLink() {
        super("Spirit Link", GeneralUtil.chestplates, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double chance = level * 0.1;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            final Player playerHurt = (Player)player;
            final double healAmount = Math.min(4.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) / (this.max - (level - 1)) / 4.0);
            final double radius = level * 2;
            for (final Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                if (ent instanceof Player) {
                    final Player playerNear = (Player)ent;
                    if (!this.isAlly(playerHurt, playerNear)) {
                        continue;
                    }
                    GeneralUtil.healPlayer((LivingEntity)playerNear, healAmount);
                    ParticleEffect.sendToLocation(ParticleEffect.HEART, playerNear.getEyeLocation().add(0.0, 0.35, 0.0), 0.0f, (float)Math.random(), 0.0f, 0.05f, 1);
                }
            }
            ParticleEffect.sendToLocation(ParticleEffect.HEART, player.getLocation().add(0.0, 0.5, 0.0), (float)Math.random() / 2.0f, (float)Math.random() / 2.0f, (float)Math.random() / 2.0f, 0.05f, 3);
            player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.35f);
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
