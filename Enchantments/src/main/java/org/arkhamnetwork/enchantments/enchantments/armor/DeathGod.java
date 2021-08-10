package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathGod extends CustomEnchantment {
    public DeathGod() {
        super("Death God", GeneralUtil.helmets, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        double random = Math.random();
        double procChance = 0.015D * level;
        if (random < procChance &&
                player.getHealth() - e.getFinalDamage() <= (4 + level)) {
            e.setCancelled(true);
            e.setDamage(0.0D);
            GeneralUtil.healPlayer(player, (5 + level));
            ((Player)player).sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "* DEATH GOD [" + ChatColor.GOLD + "+" + (5 + level) + "HP" + ChatColor.BOLD + "] *");
        }
    }
}
