package org.arkhamnetwork.enchantments.enchantments.axes;

import java.text.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

final class HexListener implements Listener
{
    private final DecimalFormat df;

    HexListener() {
        this.df = new DecimalFormat("#.##");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof LivingEntity)) {
            return;
        }
        final LivingEntity damager = (LivingEntity)e.getDamager();
        if (Hex.isHexed(damager) && e.getFinalDamage() > 0.0) {
            final double damage = Math.min(e.getFinalDamage(), 5 + Math.max(0, Hex.getHexLevel(damager) - 2));
            damager.damage(damage);
            if (damager instanceof Player) {
                ((Player)damager).sendMessage(ChatColor.DARK_PURPLE + "* HEX [" + ChatColor.RED + this.df.format(damage) + ChatColor.DARK_PURPLE + " DMG] *");
            }
        }
    }
}
