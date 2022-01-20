package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class Heavy extends CustomEnchantment
{
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
            final Player pAttacker = (Player)attacker;
            i = pAttacker.getItemInHand();
        }
        final double damage_negation = level * 0.02;
        if (i != null && i.getType() == Material.BOW && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            AstroDamageHandler.getInstance().getDamageHandler().reduceDamage((double)(level * 2), e, "&e&lHeavy");
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 133, 2001);
        }
    }
}
