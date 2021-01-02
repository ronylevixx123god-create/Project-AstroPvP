package org.arkhamnetwork.enchantments.enchantments.bow;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

class UnfocusListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            final Projectile pj = (Projectile) e.getDamager();
            if (pj.getShooter() instanceof Player) {
                final Player p = (Player) pj.getShooter();
                if (p.hasMetadata("unfocusEnchantmentExpire") && System.currentTimeMillis() < p.getMetadata("unfocusEnchantmentExpire").get(0).asLong()) {
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) / 2.0);
                    p.sendMessage(ChatColor.DARK_GREEN + "** UNFOCUSED [50% BOW DMG] **");
                }
            }
        }
    }
}
