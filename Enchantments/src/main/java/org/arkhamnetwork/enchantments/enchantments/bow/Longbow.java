package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Longbow extends CustomEnchantment {
    public Longbow() {
        super("Longbow", new Material[]{Material.BOW}, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit.getEquipment().getItemInHand().getType() == Material.BOW) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 + 0.25 * level));
            if (playerHit instanceof Player) {
                ((Player) playerHit).playSound(playerHit.getLocation(), Sound.ARROW_HIT, 1.0f, 0.4f);
            }
        }
    }
}
