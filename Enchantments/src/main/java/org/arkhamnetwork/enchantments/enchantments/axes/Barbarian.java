package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import net.astropvp.damagehandler.AstroDamageHandler;
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
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage((double)level, e, "&6&lBarbarian");
    }
}
