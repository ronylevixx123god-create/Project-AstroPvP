package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;
import com.rit.sucy.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.enchantments.libs.*;

public class HeroicDodge extends HeroicCustomEnchantment
{
    public HeroicDodge() {
        super("Ethereal Dodge", "Dodge", GeneralUtil.boots, 2);
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.05 * level;
            final boolean sneaking = ((Player)player).isSneaking();
            if (sneaking) {
                chance += 0.2;
            }
            if (Math.random() < chance) {
                e.setCancelled(true);
                e.setDamage(0.0);
                ((Player)player).sendMessage(ChatColor.LIGHT_PURPLE + "*DODGE*");
                ParticleEffect.playEffect(ParticleEffect.CLOUD, player.getLocation().add(0.0, 1.0, 0.0), 0.2f, 10);
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
                final double chanceForSpeed = 5 + (level - 1) * 2;
                if (Math.random() < chanceForSpeed * 0.01) {
                    PlayerPotionEffects.trackPotionEffect((Player)player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.SPEED, 60, Math.max(2, 2 + level / 2)));
                }
            }
        }
        else if (e != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (player.getFallDistance() > 5.0f) {
                ((Player)player).sendMessage(ChatColor.YELLOW + "* FALL DODGED *");
            }
            e.setDamage(0.0);
            e.setCancelled(true);
        }
    }
}
