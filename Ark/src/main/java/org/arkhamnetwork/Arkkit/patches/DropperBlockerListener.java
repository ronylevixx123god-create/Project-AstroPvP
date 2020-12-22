/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Dropper
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockDispenseEvent
 *  org.bukkit.event.inventory.InventoryMoveItemEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

final class DropperBlockerListener
implements Listener {
    private boolean newWay = false;

    public DropperBlockerListener() {
        try {
            Method newMethod = InventoryMoveItemEvent.class.getDeclaredMethod("getSourceHolderClassType", new Class[0]);
            if (newMethod != null) {
                this.newWay = true;
            }
        }
        catch (NoSuchMethodException e) {
            Bukkit.getLogger().info("[DropperBlocker] Using old .getSource().getHolder() for droppers due to missing spigot optimization.");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        boolean dropper;
        if (this.newWay) {
            try {
                dropper = e.getSourceHolderClassType() != null && e.getSourceHolderClassType().equals(Dropper.class);
            }
            catch (Exception | NoSuchMethodError ex) {
                this.newWay = false;
                dropper = e.getSource().getHolder() instanceof Dropper;
            }
        } else {
            dropper = e.getSource().getHolder() instanceof Dropper;
        }
        if (dropper && (DropperBlocker.no_dropper.contains((Object)e.getItem().getType()) || e.getItem().getType().name().toLowerCase().contains("diamond"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onProjectileLaunchMONITOR(BlockDispenseEvent e) {
        if (e.getBlock().getType() == Material.DROPPER) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onProjectileLaunchLOWEST(BlockDispenseEvent e) {
        if (e.getBlock().getType() == Material.DROPPER) {
            e.setCancelled(true);
        }
    }
}

