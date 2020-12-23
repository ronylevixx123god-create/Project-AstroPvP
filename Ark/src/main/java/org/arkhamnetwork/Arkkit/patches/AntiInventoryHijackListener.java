/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Vehicle
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.vehicle.VehicleDamageEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class AntiInventoryHijackListener
        implements Listener {
    private final Map<Player, Block> openBlocks = new HashMap<Player, Block>();
    private final Map<Player, Entity> openEntities = new HashMap<Player, Entity>();
    private final List<Material> inventory_blocks;
    private final List<EntityType> inventory_entities;
    private final HashMap<Player, Location> openLocations = new HashMap();

    public AntiInventoryHijackListener() {
        this.inventory_blocks = new ArrayList<Material>();
        this.inventory_entities = new ArrayList<EntityType>();
        this.inventory_blocks.add(Material.CHEST);
        this.inventory_blocks.add(Material.TRAPPED_CHEST);
        this.inventory_blocks.add(Material.BREWING_STAND);
        this.inventory_blocks.add(Material.DISPENSER);
        this.inventory_blocks.add(Material.FURNACE);
        this.inventory_blocks.add(Material.MINECART);
        this.inventory_blocks.add(Material.STORAGE_MINECART);
        this.inventory_blocks.add(Material.HOPPER_MINECART);
        this.inventory_blocks.add(Material.HOPPER);
        this.inventory_blocks.add(Material.DROPPER);
        this.inventory_blocks.add(Material.BEACON);
        this.inventory_blocks.add(Material.WORKBENCH);
        this.inventory_blocks.add(Material.ANVIL);
        this.inventory_blocks.add(Material.BEACON);
        this.inventory_blocks.add(Material.POWERED_MINECART);
        this.inventory_entities.add(EntityType.HORSE);
        this.inventory_entities.add(EntityType.MINECART_CHEST);
        this.inventory_entities.add(EntityType.MINECART_FURNACE);
        this.inventory_entities.add(EntityType.MINECART_HOPPER);
        this.inventory_entities.add(EntityType.VILLAGER);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        this.openBlocks.remove(player);
        this.openEntities.remove(player);
        this.openLocations.remove(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        this.openBlocks.remove(player);
        this.openEntities.remove(player);
        this.openLocations.remove(player);
    }

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Block block = e.getClickedBlock();
        Material blockType = block.getType();
        for (Material inventoryBlock : this.inventory_blocks) {
            if (!blockType.equals(inventoryBlock)) continue;
            this.openBlocks.put(e.getPlayer(), block);
            return;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        EntityType entityType = entity.getType();
        for (EntityType inventoryEntity : this.inventory_entities) {
            Horse h;
            if (!entityType.equals(inventoryEntity)) continue;
            if (entityType == EntityType.HORSE && !(h = (Horse) entity).isCarryingChest()) {
                return;
            }
            this.openEntities.put(e.getPlayer(), entity);
            this.openLocations.put(e.getPlayer(), entity.getLocation());
            return;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!this.openBlocks.containsValue(block)) {
            return;
        }
        Player player = e.getPlayer();
        e.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " break that block, as a player is currently viewing its inventory.");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityHit(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity damaged = e.getEntity();
        if (!this.openEntities.containsValue(damaged)) {
            return;
        }
        Player player = (Player) damager;
        e.setCancelled(true);
        e.setDamage(0.0);
        player.sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " kill that entity, as a player is currently viewing its inventory.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleHit(VehicleDamageEvent e) {
        Entity damager = e.getAttacker();
        Vehicle damaged = e.getVehicle();
        if (!this.openEntities.containsValue(damaged)) {
            return;
        }
        Player player = (Player) damager;
        e.setCancelled(true);
        e.setDamage(0.0);
        player.sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " kill that entity, as a player is currently viewing its inventory.");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockExplodeMONITOR(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            if (e.getEntity() != null && e.getEntity().hasMetadata("fastExplosion") && this.inventory_blocks.contains(b.getType())) {
                e.blockList().remove(b);
            }
            if (this.openBlocks.containsValue(b)) {
                e.blockList().remove(b);
            }
            if (!this.openLocations.containsValue(b.getLocation())) continue;
            e.blockList().remove(b);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplodeLOWEST(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            if (e.getEntity() != null && e.getEntity().hasMetadata("fastExplosion") && this.inventory_blocks.contains(b.getType())) {
                e.blockList().remove(b);
            }
            if (this.openBlocks.containsValue(b)) {
                e.blockList().remove(b);
            }
            if (!this.openLocations.containsValue(b.getLocation())) continue;
            e.blockList().remove(b);
        }
    }
}

