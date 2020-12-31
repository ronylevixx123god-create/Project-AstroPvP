package me.ily2bb.slotbot.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Cracked implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void damageModi(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
            return;
        }
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getItemInHand().getType() == Material.STONE_SWORD || p.getItemInHand().getType() == Material.WOOD_SWORD || p.getItemInHand().getType() == Material.IRON_SWORD) {
                e.setDamage(-100);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager().getType() == EntityType.CREEPER)
            event.setCancelled(true);
    }
}
