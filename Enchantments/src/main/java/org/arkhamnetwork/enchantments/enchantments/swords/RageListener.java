package org.arkhamnetwork.enchantments.enchantments.swords;

import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.CacheUtils;
import org.arkhamnetwork.enchantments.util.RageStacksCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

final class RageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            RageStacksCache victimRageStack = CacheUtils.getRageStackCache(player);
            if (victimRageStack.isSameVictim((Player) event.getDamager())) {
                if (victimRageStack.getRageStack() > 0) {
                    victimRageStack.resetRageStack();
                }
            }

        }
    }
}
