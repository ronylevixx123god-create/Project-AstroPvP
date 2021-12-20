package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Valor extends CustomEnchantment {
    public Valor() {
        super("Valor", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player))
            return;
        if (player.hasMetadata("noDefenseProcs"))
            return;
        double damage_negation = level * 0.005D;
        double hp_threshold = (level * 2 +6);
        if (player.getHealth() <= hp_threshold && (((Player) player).getItemInHand().getType() == Material.GOLD_SWORD || ((Player) player).getItemInHand().getType() == Material.DIAMOND_SWORD) && e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D - damage_negation) > 1.0D) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D - damage_negation));
            Location l = player.getLocation();
        }
    }
}
