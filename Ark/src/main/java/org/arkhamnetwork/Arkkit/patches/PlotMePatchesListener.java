/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.worldcretornica.plotme.Plot
 *  com.worldcretornica.plotme.PlotManager
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Chest
 *  org.bukkit.block.Dispenser
 *  org.bukkit.block.DoubleChest
 *  org.bukkit.block.Dropper
 *  org.bukkit.block.Hopper
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.worldcretornica.plotme.Plot;
import com.worldcretornica.plotme.PlotManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

final class PlotMePatchesListener
implements Listener {
    PlotMePatchesListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntityType() == EntityType.PRIMED_TNT) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest || e.getInventory().getHolder() instanceof Hopper || e.getInventory().getHolder() instanceof Dispenser || e.getInventory().getHolder() instanceof Dropper) {
            Plot p;
            Block b = null;
            if (e.getInventory().getHolder() instanceof Chest) {
                b = ((Chest)e.getInventory().getHolder()).getBlock();
            } else if (e.getInventory().getHolder() instanceof DoubleChest) {
                b = ((DoubleChest)e.getInventory().getHolder()).getLocation().getBlock();
            } else if (e.getInventory().getHolder() instanceof Hopper) {
                b = ((Hopper)e.getInventory().getHolder()).getLocation().getBlock();
            } else if (e.getInventory().getHolder() instanceof Dispenser) {
                b = ((Dispenser)e.getInventory().getHolder()).getLocation().getBlock();
            } else if (e.getInventory().getHolder() instanceof Dropper) {
                b = ((Dropper)e.getInventory().getHolder()).getLocation().getBlock();
            }
            Player pl = (Player)e.getPlayer();
            if (pl.isOp()) {
                return;
            }
            if (b.getType() != Material.CHEST && (p = PlotManager.getPlotById((Location)b.getLocation())) != null && pl != null && !p.isAllowed(pl.getName(), pl.getUniqueId())) {
                pl.sendMessage((Object)ChatColor.RED + "You are not permitted to access chests in this plot.");
                e.setCancelled(true);
                return;
            }
        }
    }
}

