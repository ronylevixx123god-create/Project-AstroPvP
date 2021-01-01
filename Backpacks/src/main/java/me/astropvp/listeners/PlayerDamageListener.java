package me.astropvp.listeners;

import me.astropvp.AstroBackpacks;
import me.astropvp.AstroBackpacksAPI;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {
    @EventHandler
    public void playerAttackPlayer(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity damaged = e.getEntity();
        if(damager instanceof Player) {
            BackpackType type = AstroBackpacksAPI.getBackpackByItem(((Player) damager).getInventory().getChestplate());
            if(type == null)
                return;
            type.getInstance().onAttack((Player) damager, damaged, e);
        }
        if(damaged instanceof Player) {
            BackpackType type = AstroBackpacksAPI.getBackpackByItem(((Player) damaged).getInventory().getChestplate());
            if(type == null)
                return;
            type.getInstance().onAttack((Player) damaged, damager, e);
        }
    }
}
