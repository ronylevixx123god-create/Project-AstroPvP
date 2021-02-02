package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.ExpUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;

public class SkillSwipe extends CustomEnchantment {
    public SkillSwipe() {
        super("Skill Swipe", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (target instanceof Player) {
            final double random = Math.random();
            final double chance = 0.2 * enchantLevel;
            float amountToSteal = enchantLevel * 6;
            final float targetXp = ((Player) target).getTotalExperience();
            if (targetXp - amountToSteal < 0) {
                amountToSteal = targetXp;
            }
            if (amountToSteal <= 0) {
                return;
            }
        }
    }
}
