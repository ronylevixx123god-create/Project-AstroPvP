/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.MaskUtils
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicLifesteal
extends HeroicCustomEnchantment {
    public HeroicLifesteal() {
        super("Demonic Lifesteal", "Lifesteal", GeneralUtil.swords, 2);
        this.max = 3;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = enchantLevel == 1 ? 0.15 : (enchantLevel == 2 ? 0.2 : (enchantLevel == 3 ? 0.25 : 0.25));
        int toHeal = enchantLevel == 1 ? 4 : (enchantLevel == 2 ? 6 : (enchantLevel == 3 ? 8 : 4));
        int health = (int)user.getHealth() + toHeal;
        if ((double)health > user.getMaxHealth()) {
            health = (int)user.getMaxHealth();
        }
        if (random < chance) {
            if (target instanceof Player && MaskUtils.hasMaskEquipped((Player)((Player)target), (String)"NECROMANCER")) {
                return;
            }
            GeneralUtil.setPlayerHealth(user, health);
        }
    }
}

