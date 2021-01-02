package org.arkhamnetwork.enchantments.enchantments.axes;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.text.DecimalFormat;

final class HexListener implements Listener {
    private DecimalFormat df;

    HexListener() {
        this.df = new DecimalFormat("#.##");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof LivingEntity)) {
            return;
        }


        final LivingEntity damager = (LivingEntity) e.getDamager();
        if (Hex.isHexed(damager) && e.getFinalDamage() > 1.0) {
            final double damage = Math.min(e.getFinalDamage(), 5 + Math.max(0, Hex.getHexLevel(damager) - 2));
            damager.damage(damage);
            if (damager instanceof Player) {
                ((Player) damager).sendMessage(ChatColor.DARK_PURPLE + "* HEX [" + ChatColor.RED + this.df.format(damage) + ChatColor.DARK_PURPLE + " DMG] *");
            }
        }
    }
}
