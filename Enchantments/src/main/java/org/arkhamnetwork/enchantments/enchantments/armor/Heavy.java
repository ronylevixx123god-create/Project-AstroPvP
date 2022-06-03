package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class Heavy extends CustomEnchantment {
    public Heavy() {
        super("Heavy", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            ItemStack i = null;
            if (attacker instanceof Player) {
                Player pAttacker = (Player)attacker;
                i = pAttacker.getItemInHand();
            }

            double damage_negation = (double)level * 0.02D;
            if (i != null && i.getType() == Material.BOW && e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * (1.0D - damage_negation) > 1.0D) {
                e.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * (1.0D - damage_negation));
                Location var8 = player.getLocation();
            }

        }
    }
}
