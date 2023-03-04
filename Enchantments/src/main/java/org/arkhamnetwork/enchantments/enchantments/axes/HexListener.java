package org.arkhamnetwork.enchantments.enchantments.axes;

import java.text.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

final class HexListener
        implements Listener {
    private DecimalFormat df = new DecimalFormat("#.##");

    HexListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof LivingEntity)) {
            return;
        }
        LivingEntity damager = (LivingEntity)e.getDamager();
        if (Hex.isHexed(damager) && e.getFinalDamage() > 0.0) {
            double damage = Math.min(e.getFinalDamage(), (double)(5 + Math.max(0, Hex.getHexLevel(damager) - 2)));
            damager.damage(damage);
            if (damager instanceof Player) {
                ((Player)damager).sendMessage((Object)((Object)ChatColor.DARK_PURPLE) + "* HEX [" + (Object)((Object)ChatColor.RED) + this.df.format(damage) + (Object)((Object)ChatColor.DARK_PURPLE) + " DMG] *");
            }
        }
    }
}
