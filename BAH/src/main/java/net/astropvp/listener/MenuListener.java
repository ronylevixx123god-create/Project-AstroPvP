// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.listener;

import net.astropvp.menu.Menu;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class MenuListener implements Listener
{
    @EventHandler(ignoreCancelled = true)
    private void onClick(final InventoryClickEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) {
            return;
        }
        ((Menu)holder).onClick((Player)event.getWhoClicked(), event);
    }
    
    @EventHandler
    private void onOpen(final InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) {
            ((Menu)holder).onOpen((Player)event.getPlayer());
        }
    }
    
    @EventHandler
    private void onClose(final InventoryCloseEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) {
            ((Menu)holder).onClose((Player)event.getPlayer());
        }
    }
}
