package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Heavy extends CustomEnchantment {
    public Heavy() {
        super("Heavy", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        ItemStack i = null;
        if (attacker instanceof Player) {
            final Player pAttacker = (Player) attacker;
            i = pAttacker.getItemInHand();
        }
        final double damage_negation = level * 0.02;
        if (i != null && i.getType() == Material.BOW && e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 - damage_negation) > 1.0) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 - damage_negation));
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 133, 2001);
        }
    }
}
