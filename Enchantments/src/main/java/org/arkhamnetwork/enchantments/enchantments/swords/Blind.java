package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.enchantments.armor.*;
import org.bukkit.potion.*;

public class Blind extends CustomEnchantment
{
    public static String RECENTLY_BLINDED_METADATA;
    
    public Blind() {
        super("Blind", GeneralUtil.swords_and_bow, 5);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final int time = enchantLevel * 30;
        final double random = Math.random();
        final double chance = 0.1 * enchantLevel;
        if (random < chance) {
            if (target instanceof Player && !Clarity.canBlindnessApply((Player)target, enchantLevel - 1)) {
                return;
            }
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, enchantLevel - 1));
        }
    }
    
    static {
        Blind.RECENTLY_BLINDED_METADATA = "recentlyBlinded";
    }
}
