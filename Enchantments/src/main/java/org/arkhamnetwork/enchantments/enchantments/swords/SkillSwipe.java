package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import java.util.*;
import org.bukkit.entity.*;

public class SkillSwipe extends CustomEnchantment
{
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
            int amountToSteal = enchantLevel * 6;
            final int targetXp = ExpUtils.getTotalExperience((Player)target);
            if (targetXp - amountToSteal < 0) {
                amountToSteal = targetXp;
            }
            if (amountToSteal <= 0) {
                return;
            }
            if (random < chance) {
                ExpUtils.setTotalExperience((Player)target, targetXp - amountToSteal);
                ExpUtils.setTotalExperience((Player)user, ExpUtils.getTotalExperience((Player)user) + amountToSteal);
                user.getWorld().spawnEntity(user.getTargetBlock((HashSet<Byte>) null, 1).getLocation(), EntityType.EXPERIENCE_ORB);
            }
        }
    }
}
