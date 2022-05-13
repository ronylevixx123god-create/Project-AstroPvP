package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Barbarian extends CustomEnchantment
{
    public Barbarian() {
        super("Barbarian", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasAxe = playerHit.getEquipment().getItemInHand().getType().name().contains("_AXE");
        if (!enemyHasAxe) {
            return;
        }
        double mod = 1.0D + 0.05D * (double)level;
        e.setDamage(EntityDamageEvent.DamageModifier.BASE,e.getDamage() * mod);    }
}
