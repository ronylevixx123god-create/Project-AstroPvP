package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Armored extends CustomEnchantment {
    public Armored() {
        super("Armored", GeneralUtil.armor, 4);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player))
            return;
        if (player.hasMetadata("neutralized"))
            return;
        ItemStack i = null;
        if (attacker instanceof Player) {
            Player pAttacker = (Player) attacker;
            i = pAttacker.getItemInHand();
        }
        double damage_negation = level * 0.01875D;
        if (i != null && i.getType().name().contains("_SWORD") && e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D - damage_negation) > 1.0D) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D - damage_negation));
            Location l = player.getLocation();
        }
    }
}
