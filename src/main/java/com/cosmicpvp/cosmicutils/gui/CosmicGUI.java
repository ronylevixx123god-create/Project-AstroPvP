/*
 * Decompiled with CFR 0.150.
 */
package com.cosmicpvp.cosmicutils.gui;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CosmicGUI {
    public static Set<String> allGUINames = new HashSet<String>();
    public static Map<UUID, CosmicGUI> activeGUIs = new ConcurrentHashMap<UUID, CosmicGUI>();
    public CosmicGUI parentGUI;
    protected String title;
    protected int size;
    protected Player player;
    protected Inventory inventory;
    private Map<Integer, InventoryItem> inventoryItems = new HashMap<Integer, InventoryItem>();
    private String owningPluginName;
    private Runnable closeRunnable;

    public CosmicGUI(Player player, String title, int size) {
        this(player, title, size, InventoryType.CHEST);
    }

    public CosmicGUI(Player player, String title, int size, InventoryType type) {
        this.inventory = type == InventoryType.CHEST ? Bukkit.createInventory(null, size, title) : Bukkit.createInventory(null, (InventoryType)type, (String)title);
        this.player = player;
        this.size = size;
        this.title = title;
        allGUINames.add(this.title);
    }

    public CosmicGUI setParentGUI(CosmicGUI parent) {
        this.parentGUI = parent;
        return this;
    }

    public void onUnknownItemClick(InventoryClickEvent event) {
    }

    public abstract void redraw();

    public void openGUI(JavaPlugin owning) {
        this.owningPluginName = owning.getName();
        CosmicGUI currentlyActive = activeGUIs.get(this.player.getUniqueId());
        if (currentlyActive != null) {
            Bukkit.getLogger().info("Closing already open menu first!");
            Bukkit.getScheduler().scheduleSyncDelayedTask(owning, () -> {
                currentlyActive.close();
                activeGUIs.put(this.player.getUniqueId(), this);
                this.redraw();
                this.player.openInventory(this.inventory);
            });
            return;
        }
        activeGUIs.put(this.player.getUniqueId(), this);
        this.redraw();
        this.player.openInventory(this.inventory);
    }

    public void setItem(int slot, InventoryItem inventoryItem) {
        if (inventoryItem == null || inventoryItem.getItem() == null) {
            this.removeItem(slot);
            return;
        }
        this.inventoryItems.put(slot, inventoryItem);
        this.inventory.setItem(slot, inventoryItem.getItem());
    }

    public void closeWithDelay() {
        this.closeWithDelay(null);
    }

    public void closeWithDelay(Consumer<Player> afterClose) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicUtils.get(), () -> {
            this.player.closeInventory();
            if (afterClose != null) {
                afterClose.accept(this.player);
            }
        }, 1L);
    }

    public void setItem(int slot, ItemStack item, Runnable runnable) {
        this.setItem(slot, new InventoryItem(item).click(runnable));
    }

    public void onInventoryClose() {
        if (this.closeRunnable != null) {
            this.closeRunnable.run();
        }
    }

    public void close() {
        this.onInventoryClose();
        this.player.closeInventory();
    }

    public void removeItem(int slot) {
        this.inventory.setItem(slot, null);
        this.inventoryItems.remove(slot);
    }

    public CosmicGUI setOnClose(Runnable runnable) {
        this.closeRunnable = runnable;
        return this;
    }

    public boolean isInventory(Inventory inventory) {
        return this.inventory.equals(inventory);
    }

    public static CosmicGUI getActiveGUI(UUID uuid) {
        return activeGUIs.get(uuid);
    }

    public static void removeGUI(UUID uuid) {
        activeGUIs.remove(uuid);
    }

    public CosmicGUI getParentGUI() {
        return this.parentGUI;
    }

    public Map<Integer, InventoryItem> getInventoryItems() {
        return this.inventoryItems;
    }

    public String getOwningPluginName() {
        return this.owningPluginName;
    }

    public void setOwningPluginName(String owningPluginName) {
        this.owningPluginName = owningPluginName;
    }

    public Runnable getCloseRunnable() {
        return this.closeRunnable;
    }
}

