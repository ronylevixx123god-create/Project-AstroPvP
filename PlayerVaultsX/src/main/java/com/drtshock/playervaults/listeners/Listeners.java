package com.drtshock.playervaults.listeners;

import com.drtshock.playervaults.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import com.drtshock.playervaults.vaultmanagement.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.event.inventory.*;
import java.util.*;

public class Listeners implements Listener
{
    public final PlayerVaults plugin;
    private final VaultManager vaultManager;
    
    public Listeners(final PlayerVaults plugin) {
        this.vaultManager = VaultManager.getInstance();
        this.plugin = plugin;
    }
    
    public void saveVault(final Player player, final Inventory inventory) {
        if (this.plugin.getInVault().containsKey(player.getUniqueId().toString())) {
            final Inventory inventory2 = Bukkit.createInventory((InventoryHolder)null, 54);
            inventory2.setContents((ItemStack[])inventory.getContents().clone());
            if (inventory.getViewers().size() == 1) {
                final VaultViewInfo vaultViewInfo = this.plugin.getInVault().get(player.getUniqueId().toString());
                this.vaultManager.saveVault(inventory2, vaultViewInfo.getHolderUUID(), vaultViewInfo.getNumber());
                this.plugin.getOpenInventories().remove(vaultViewInfo.toString());
            }
            this.plugin.getInVault().remove(player.getUniqueId().toString());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(final PlayerTeleportEvent playerTeleportEvent) {
        if (playerTeleportEvent.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            return;
        }
        final Player player = playerTeleportEvent.getPlayer();
        if (this.plugin.getInVault().containsKey(player.getUniqueId().toString())) {
            return;
        }
        this.saveVault(player, player.getOpenInventory().getTopInventory());
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(final PlayerQuitEvent playerQuitEvent) {
        this.saveVault(playerQuitEvent.getPlayer(), playerQuitEvent.getPlayer().getOpenInventory().getTopInventory());
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(final PlayerDeathEvent playerDeathEvent) {
        this.saveVault(playerDeathEvent.getEntity(), playerDeathEvent.getEntity().getOpenInventory().getTopInventory());
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onClose(final InventoryCloseEvent inventoryCloseEvent) {
        final HumanEntity player = inventoryCloseEvent.getPlayer();
        if (player instanceof Player) {
            this.saveVault((Player)player, inventoryCloseEvent.getInventory());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteractEntity(final PlayerInteractEntityEvent playerInteractEntityEvent) {
        final Player player = playerInteractEntityEvent.getPlayer();
        final EntityType type = playerInteractEntityEvent.getRightClicked().getType();
        if ((type == EntityType.VILLAGER || type == EntityType.MINECART) && PlayerVaults.getInstance().getInVault().containsKey(player.getUniqueId().toString())) {
            playerInteractEntityEvent.setCancelled(true);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onClick(final InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (player.hasPermission("playervaults.bypassblockeditems")) {
            return;
        }
        final Inventory clickedInventory = inventoryClickEvent.getClickedInventory();
        if (clickedInventory != null) {
            final VaultViewInfo vaultViewInfo = PlayerVaults.getInstance().getInVault().get(player.getUniqueId().toString());
            if (vaultViewInfo != null) {
                final int number = vaultViewInfo.getNumber();
                final String title = clickedInventory.getTitle();
                final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(number)).replace("%p", vaultViewInfo.getHolder());
                if (((title != null && title.equalsIgnoreCase(replace)) || inventoryClickEvent.getCurrentItem() != null) && PlayerVaults.getInstance().isBlockedMaterial(inventoryClickEvent.getCurrentItem().getType())) {
                    inventoryClickEvent.setCancelled(true);
                    player.sendMessage(Lang.TITLE.toString() + Lang.BLOCKED_ITEM.toString().replace("%m", inventoryClickEvent.getCurrentItem().getType().name()));
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onDrag(final InventoryDragEvent inventoryDragEvent) {
        if (!(inventoryDragEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player)inventoryDragEvent.getWhoClicked();
        if (player.hasPermission("playervaults.bypassblockeditems")) {
            return;
        }
        final Inventory inventory = inventoryDragEvent.getInventory();
        if (inventory != null) {
            final VaultViewInfo vaultViewInfo = PlayerVaults.getInstance().getInVault().get(player.getUniqueId().toString());
            if (vaultViewInfo != null) {
                final int number = vaultViewInfo.getNumber();
                final String title = inventory.getTitle();
                final String replace = Lang.VAULT_TITLE.toString().replace("%number", String.valueOf(number)).replace("%p", vaultViewInfo.getHolder());
                if (title != null && title.equalsIgnoreCase(replace) && inventoryDragEvent.getNewItems() != null) {
                    for (final ItemStack itemStack : inventoryDragEvent.getNewItems().values()) {
                        if (PlayerVaults.getInstance().isBlockedMaterial(itemStack.getType())) {
                            inventoryDragEvent.setCancelled(true);
                            player.sendMessage(Lang.TITLE.toString() + Lang.BLOCKED_ITEM.toString().replace("%m", itemStack.getType().name()));
                        }
                    }
                }
            }
        }
    }
}
