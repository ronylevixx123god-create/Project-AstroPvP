/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.text.DecimalFormat;

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
                ((Player)damager).sendMessage((Object)ChatColor.DARK_PURPLE + "* HEX [" + (Object)ChatColor.RED + this.df.format(damage) + (Object)ChatColor.DARK_PURPLE + " DMG] *");
            }
        }
    }
}

