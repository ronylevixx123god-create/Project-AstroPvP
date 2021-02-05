package me.ifamasssxd.cosmicsky.inventorypets.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class BansheeListener implements Listener {
    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
            Player damager = (Player)event.getDamager();
            if (damager.hasMetadata("banshee_active"))
                event.setDamage(event.getDamage() * 1.87D);
        }
    }
}
