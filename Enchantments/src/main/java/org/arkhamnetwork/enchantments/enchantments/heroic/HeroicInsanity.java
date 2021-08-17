package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicInsanity extends CustomEnchantment {
    public HeroicInsanity() {
        super("Extreme Insanity", GeneralUtil.axe, 8);
        this.max = 8;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasSword = playerHit.getEquipment().getItemInHand().getType().name().contains("_SWORD");
        if (!enemyHasSword) {
            return;
        }
        if (GeneralCooldown.isOnGeneralCooldown((Player) player)) {
            return;
        }
        final double mod = 1.0 + 0.04 * level;
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
        GeneralCooldown.enterCooldown((Player) player);
    }
}

