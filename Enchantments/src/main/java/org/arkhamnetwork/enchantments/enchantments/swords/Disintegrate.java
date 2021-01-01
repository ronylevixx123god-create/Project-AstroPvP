package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.util.*;

public class Disintegrate extends CustomEnchantment
{
    public Disintegrate() {
        super("Disintegrate", GeneralUtil.swords, 5);
        this.max = 4;
        this.base = 15.0;
        this.interval = 6.0;
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (Math.random() <= 0.02 * enchantLevel && target instanceof Player) {
            ((Player)user).playSound(target.getLocation(), Sound.ANVIL_BREAK, 0.3f, 0.8f);
            final Player pTarget = (Player)target;
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.HELMET, -1, false);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.CHESTPLATE, -2, false);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.LEGGINGS, -2, false);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.BOOTS, -1, false);
        }
    }
}
