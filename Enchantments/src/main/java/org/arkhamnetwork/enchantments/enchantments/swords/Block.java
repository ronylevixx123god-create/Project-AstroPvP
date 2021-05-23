package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Block extends CustomEnchantment {
    public Block() {
        super("Block", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player player2 = (Player) player;
            final double random = Math.random();
            final double chance = 0.1 * level;
            if (random < chance && player2.isBlocking()) {
                final double reduceDamage = 0.5 * (level / 2);
               // e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 - reduceDamage));
                player2.playSound(player2.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
            }
        }
    }
}
