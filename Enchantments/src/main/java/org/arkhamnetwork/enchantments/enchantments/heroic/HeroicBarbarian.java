package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class HeroicBarbarian extends HeroicCustomEnchantment
{
    public HeroicBarbarian() {
        super("Brutal Barbarian", "Barbarian", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasAxe = playerHit.getEquipment().getItemInHand().getType().name().contains("_AXE");
        if (!enemyHasAxe) {
            return;
        }
        e.setDamage(e.getDamage() * 1.225D);    }
}
