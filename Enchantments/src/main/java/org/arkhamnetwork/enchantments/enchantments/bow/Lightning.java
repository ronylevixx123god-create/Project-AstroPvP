package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Lightning extends CustomEnchantment {
    public Lightning() {
        super("Lightning", new Material[]{Material.BOW}, 2);
        this.max = 3;
        this.base = 20.0;
        this.interval = 0.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random() * 1.0;
        final double chance = 0.1 * level;
        if (random < chance) {
            if (!(playerHit instanceof Player)) {
                return;
            }
            playerHit.getLocation().getWorld().strikeLightningEffect(playerHit.getLocation());
            playerHit.damage(5.0, (Entity) player);
        }
    }
}
