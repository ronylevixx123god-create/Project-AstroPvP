package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Ravenous extends CustomEnchantment {
    public Ravenous() {
        super("Ravenous", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(user instanceof Player)) {
            return;
        }
        final Player playerAttacker = (Player) user;
        final double random = Math.random();
        final double chance = 0.05 * enchantLevel;
        if (random < chance && playerAttacker.getFoodLevel() < 20) {
            playerAttacker.setFoodLevel(playerAttacker.getFoodLevel() + 1);
        }
    }
}
