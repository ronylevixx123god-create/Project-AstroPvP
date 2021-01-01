package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;

public class Dodge extends CustomEnchantment
{
    public Dodge() {
        super("Dodge", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.025 * level;
            final boolean sneaking = ((Player)player).isSneaking();
            if (sneaking) {
                chance += 0.15;
            }
            if (Math.random() < chance) {
                e.setCancelled(true);
                e.setDamage(0.0);
                ((Player)player).sendMessage(ChatColor.YELLOW + "*DODGE*");
                ParticleEffect.playEffect(ParticleEffect.CLOUD, player.getLocation().add(0.0, 1.0, 0.0), 0.2f, 10);
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
            }
        }
    }
}
