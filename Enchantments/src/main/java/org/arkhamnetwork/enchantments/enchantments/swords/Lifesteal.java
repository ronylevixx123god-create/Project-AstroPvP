package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Lifesteal extends CustomEnchantment {
    public Lifesteal() {
        super("Lifesteal", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 8.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double random = Math.random();
        if (target instanceof Player && MaskType.hasEquipped((Player) target, "NECROMANCER")) {
            return;
        }
        final double chance = (enchantLevel > 3) ? 0.3 : (0.1 * enchantLevel);
        int health = (int) user.getHealth() + enchantLevel;
        if (health > user.getMaxHealth()) {
            health = (int) user.getMaxHealth();
        }

        if (random < chance) {
            GeneralUtil.setPlayerHealth(user, health);
        }
    }
}
