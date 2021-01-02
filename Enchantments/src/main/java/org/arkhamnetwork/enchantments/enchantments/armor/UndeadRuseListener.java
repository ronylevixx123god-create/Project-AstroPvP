package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

final class UndeadRuseListener implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().hasMetadata("undeadruseSummoner")) {
            final Player p = (Player) e.getEntity();
            final String summoner = e.getDamager().getMetadata("undeadruseSummoner").get(0).asString();
            final Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtil.isAlly(p, pSummoner))) {
                e.setCancelled(true);
                e.setDamage(0.0);
            }
        }
        if (e.getEntity().hasMetadata("undeadruseSummoner") && e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            final String summoner = e.getEntity().getMetadata("undeadruseSummoner").get(0).asString();
            final Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtil.isAlly(p, pSummoner))) {
                e.setCancelled(true);
                e.setDamage(0.0);
            }
        }
    }

    @EventHandler
    public void onEntityTarget(final EntityTargetEvent e) {
        if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("undeadruseSummoner")) {
            final Player p = (Player) e.getTarget();
            final String summoner = e.getEntity().getMetadata("undeadruseSummoner").get(0).asString();
            final Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtil.isAlly(p, pSummoner) || p.hasMetadata("spectator"))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(final EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("undeadruseSummoner")) {
            e.getDrops().clear();
            e.getEntity().remove();
        }
    }
}
