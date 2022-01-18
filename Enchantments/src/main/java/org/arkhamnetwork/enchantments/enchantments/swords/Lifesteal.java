/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.MaskUtils
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.rit.sucy.CustomEnchantment;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Lifesteal
extends CustomEnchantment {
    public Lifesteal() {
        super("Lifesteal", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 8.0;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        if (target instanceof Player && MaskType.hasEquipped((Player)((Player)target), (String)"NECROMANCER")) {
            return;
        }
        double chance = enchantLevel > 3 ? 0.3 : 0.1 * (double)enchantLevel;
        int health = (int)user.getHealth() + enchantLevel;
        if ((double)health > user.getMaxHealth()) {
            health = (int)user.getMaxHealth();
        }
        if (random < chance) {
            GeneralUtil.setPlayerHealth(user, health);
        }
    }
}

