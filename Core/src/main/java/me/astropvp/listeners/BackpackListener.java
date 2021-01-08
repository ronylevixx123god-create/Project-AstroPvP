/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.listeners;

import me.astropvp.AstroBackpacksAPI;
import me.astropvp.events.ArmorEquipEvent;
import me.astropvp.objects.BackpackType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class BackpackListener
implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack cursor = e.getCursor();
        ItemStack clicked = e.getCurrentItem();
        if (e.getClickedInventory() != e.getView().getBottomInventory()) {
            return;
        }
        if (clicked == null || clicked.getType() == Material.AIR) {
            return;
        }
        if (!AstroBackpacksAPI.itemHasSkin(clicked) && AstroBackpacksAPI.isBackpack(cursor) && e.getClick().isLeftClick()) {
            BackpackType backpack = AstroBackpacksAPI.getBackpackByItem(cursor);
            if (backpack == null || !clicked.getType().toString().endsWith("_CHESTPLATE")) {
                return;
            }
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                backpack.getInstance().onEquip((Player)e.getWhoClicked());
            }
            e.setCurrentItem(backpack.getInstance().applyBackpack(clicked));
            e.setCursor(null);
            e.setCancelled(true);
            ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.0f);
        } else if (AstroBackpacksAPI.itemHasSkin(clicked) && e.getClick().isRightClick()) {
            BackpackType backpack = AstroBackpacksAPI.getBackpackByItem(clicked);
            if (backpack == null) {
                return;
            }
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                backpack.getInstance().onUnequip((Player)e.getWhoClicked());
            }
            e.setCurrentItem(AstroBackpacksAPI.removeBackpack(clicked, backpack));
            e.setCursor(backpack.getInstance().getItem());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onThrow(PlayerInteractEvent e) {
        ItemStack item = e.getPlayer().getItemInHand();
        if (item == null || item.getType() != Material.EYE_OF_ENDER) {
            return;
        }
        if (AstroBackpacksAPI.isBackpack(item)) {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        AstroBackpacksAPI.refreshBackpack(e.getPlayer());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        AstroBackpacksAPI.refreshBackpack(e.getPlayer());
    }

    @EventHandler
    public void onArmorChange(ArmorEquipEvent event) {
        BackpackType type;
        if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR && AstroBackpacksAPI.itemHasSkin(event.getOldArmorPiece())) {
            type = AstroBackpacksAPI.getBackpackByItem(event.getOldArmorPiece());
            type.getInstance().onUnequip(event.getPlayer());
        }
        if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR && AstroBackpacksAPI.itemHasSkin(event.getNewArmorPiece())) {
            type = AstroBackpacksAPI.getBackpackByItem(event.getNewArmorPiece());
            type.getInstance().onEquip(event.getPlayer());
        }
    }
}

