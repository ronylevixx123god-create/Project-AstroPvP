package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.util.*;

public class ArrowLifesteal extends CustomEnchantment
{
    public ArrowLifesteal() {
        super("Arrow Lifesteal", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 8.0;
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double random = Math.random();
        final double chance = (enchantLevel > 3) ? 0.3 : (0.1 * enchantLevel);
        int health = (int)user.getHealth() + enchantLevel;
        if (health > user.getMaxHealth()) {
            health = (int)user.getMaxHealth();
        }
        if (random < chance) {
            GeneralUtil.setPlayerHealth(user, health);
        }
    }
}
