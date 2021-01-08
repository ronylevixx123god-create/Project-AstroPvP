/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package me.astropvp.listeners;

import me.astropvp.AstroBackpacksAPI;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener
implements Listener {
    @EventHandler
    public void playerAttackPlayer(EntityDamageByEntityEvent e) {
        BackpackType type;
        Entity damager = e.getDamager();
        Entity damaged = e.getEntity();
        if (damager instanceof Player) {
            type = AstroBackpacksAPI.getBackpackByItem(((Player)damager).getInventory().getChestplate());
            if (type == null) {
                return;
            }
            type.getInstance().onAttack((Player)damager, damaged, e);
        }
        if (damaged instanceof Player) {
            type = AstroBackpacksAPI.getBackpackByItem(((Player)damaged).getInventory().getChestplate());
            if (type == null) {
                return;
            }
            type.getInstance().onAttack((Player)damaged, damager, e);
        }
    }
}

