/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Block
extends CustomEnchantment {
    public Block() {
        super("Block", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double chance;
            Player player1 = (Player)player;
            double random = Math.random();
            if (random < (chance = 0.1 * (double)level) && player1.isBlocking()) {
                double reduceDamage = 0.5 * (double)(level / 2);
                e.setDamage(e.getDamage() * (1.0 - reduceDamage));
                player1.playSound(player1.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
            }
        }
    }
}

