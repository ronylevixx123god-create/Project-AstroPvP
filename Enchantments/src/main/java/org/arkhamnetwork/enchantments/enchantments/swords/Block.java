package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Block extends CustomEnchantment
{
    public Block() {
        super("Block", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player player2 = (Player)player;
            if (player2.hasMetadata("heroicCorruptTaskID") && Math.random() < 0.2) {
                return;
            }
            final double random = Math.random();
            final double chance = 0.1 * level;
            if (random < chance && player2.isBlocking()) {
                player2.playSound(player2.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
            }
        }
    }
}
