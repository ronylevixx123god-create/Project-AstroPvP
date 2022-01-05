/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Barbarian
extends CustomEnchantment {
    public Barbarian() {
        super("Barbarian", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        boolean enemyHasAxe = playerHit.getEquipment().getItemInHand().getType().name().contains("_AXE");
        if (!enemyHasAxe) {
            return;
        }
        double mod = 1.0 + 0.05 * (double)level;
        e.setDamage(e.getDamage() * mod);
    }
}

