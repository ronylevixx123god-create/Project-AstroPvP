package org.arkhamnetwork.enchantments.enchantments.bow;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import org.bukkit.event.*;

class UnfocusListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            final Projectile pj = (Projectile)e.getDamager();
            if (pj.getShooter() instanceof Player) {
                final Player p = (Player)pj.getShooter();
                if (p.hasMetadata("unfocusEnchantmentExpire") && System.currentTimeMillis() < p.getMetadata("unfocusEnchantmentExpire").get(0).asLong()) {
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) / 2.0);
                    p.sendMessage(ChatColor.DARK_GREEN + "** UNFOCUSED [50% BOW DMG] **");
                }
            }
        }
    }
}
