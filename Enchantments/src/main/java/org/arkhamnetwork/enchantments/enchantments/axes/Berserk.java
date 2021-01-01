package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;

public class Berserk extends CustomEnchantment
{
    public Berserk() {
        super("Berserk", GeneralUtil.axe, 7);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        final Player playerAttacker = (Player)player;
        final double random = Math.random();
        final double chance = 0.03 * level;
        final double duration = level * 2;
        if (random < chance) {
            playerAttacker.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int)duration * 20, 1));
        }
    }
}
