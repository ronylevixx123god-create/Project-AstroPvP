package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class ArrowBreak extends CustomEnchantment
{
    public ArrowBreak() {
        super("Arrow Break", GeneralUtil.axe, 2);
        this.max = 6;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)e;
            if (edbee.getDamager() instanceof Projectile && Math.random() < (0.1 + 0.04 * level) / 2.0) {
                ParticleEffect.playEffect(ParticleEffect.CRIT, edbee.getDamager().getLocation(), 0.6f, 16);
                ((Player)player).playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
                e.setCancelled(true);
                e.setDamage(0.0);
                edbee.getDamager().remove();
            }
        }
    }
}
