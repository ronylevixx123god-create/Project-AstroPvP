package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import java.util.concurrent.*;
import org.arkhamnetwork.enchantments.util.*;

public class Demonforged extends CustomEnchantment
{
    public Demonforged() {
        super("Demonforged", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 3.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.04 * level;
        if (random < chance) {
            this.damageRandomArmor((Player)player);
        }
    }
    
    private void damageRandomArmor(final Player p) {
        final ArmorSlot slot = ArmorSlot.getArmorSlotByIndex(ThreadLocalRandom.current().nextInt(4));
        if (slot != null) {
            ArmorUtil.modifyPlayerArmor(p, slot, -1, true);
        }
    }
}
