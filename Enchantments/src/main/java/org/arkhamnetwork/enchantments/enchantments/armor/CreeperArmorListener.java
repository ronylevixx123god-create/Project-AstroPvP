package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.event.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

final class CreeperArmorListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && CreeperArmor.creeperArmor.containsKey(((Player)e.getEntity()).getName())) {
            final Player player = (Player)e.getEntity();
            final int level = CreeperArmor.creeperArmor.get(player.getName());
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                e.setCancelled(true);
                e.setDamage(0.0);
                if (level >= 2) {
                    new BukkitRunnable() {
                        public void run() {
                            player.setVelocity(new Vector().zero());
                        }
                    }.runTaskLater((Plugin)Enchantments.getInstance(), 1L);
                }
                if (level == 3) {
                    GeneralUtil.healPlayer((LivingEntity)player, Math.min(6.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.1));
                }
            }
        }
    }
}
