/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

final class LeadershipListener
implements Listener {
    LeadershipListener() {
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Leadership.Leaderships.remove(e.getPlayer().getName());
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getDamager().hasMetadata("leadershipLevel")) {
            int level = ((MetadataValue)((Player)e.getDamager()).getMetadata("leadershipLevel").get(0)).asInt();
            boolean koth = e.getDamager().getWorld().getName().equals("world_koth");
            boolean outpost = e.getDamager().getWorld().getName().startsWith("cosmic-station");
            double incre = Math.min(1.75, 1.0 + (double)level * (koth || outpost ? 0.05 : 0.1));
            double damage = e.getDamage();
            //AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(incre*10, e, "Leadership");

        }
    }
}

