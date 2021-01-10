/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.astropvpskins.listeners;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.ItemSkinCacher;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSkinRemoveListener
implements Listener {
    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void removeFromItem(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getClick() != ClickType.RIGHT) {
            return;
        }
        Player player = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack current = e.getCurrentItem();
        if (cursor.getType() != Material.AIR || current == null) {
            return;
        }
        SkinType type = AstroItemSkinsAPI.getSkinTypeFromNBT(current);
        if (type == null) {
            return;
        }
        if (!type.getItemSkinType().isType(current)) {
            return;
        }
        ItemStack give = type.getItem();
        player.setItemOnCursor(give);
        e.setCurrentItem(AstroItemSkinsAPI.removeSkinFromItem(type, current));
        new ItemSkinCacher(player);
        e.setCancelled(true);
    }
}

