package org.arkhamnetwork.enchantments.enchantments.heroic;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class HeroicInsanity extends HeroicCustomEnchantment
{
    public HeroicInsanity() {
        super("Extreme Insanity", "Insanity", GeneralUtil.axe, 7);
        this.max = 8;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasSword = playerHit.getEquipment().getItemInHand().getType().name().contains("_SWORD");
        if (!enemyHasSword) {
            return;
        }
        if (playerHit instanceof Player) {
            return;
        }
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage((double)(level * 3), e, "&6&lInsanity");
    }
}
