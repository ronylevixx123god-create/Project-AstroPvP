/*    */ package me.astropvp.astropvpskins.listeners;
/*    */ 
/*    */ import me.astropvp.astropvpskins.AstroItemSkinsAPI;
/*    */ import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import me.astropvp.astropvpskins.events.ItemSkinApplyEvent;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */
public class ItemSkinApplyListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void skinApplyEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player player = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack current = e.getCurrentItem();
        if (cursor == null || current == null || cursor.getType() != Material.EYE_OF_ENDER)
            return;
        if (!cursor.hasItemMeta() || !cursor.getItemMeta().hasLore() || !cursor.getItemMeta().hasDisplayName())
            return;
        SkinType type = AstroItemSkinsAPI.getSkinTypeFromNBT(cursor);
        if (type == null)
            return;
        if (!type.getItemSkinType().isType(current))
            return;
        if (AstroItemSkinsAPI.getSkinTypeFromNBT(current) != null)
            return;
        ItemSkinApplyEvent event = new ItemSkinApplyEvent(player, current, type, e);
        if (e.getCursor().getAmount() == 1) {
            e.setCancelled(true);
            player.setItemOnCursor(null);
        } else {
            e.setCancelled(true);
            cursor.setAmount(cursor.getAmount() - 1);
        }
        Bukkit.getServer().getPluginManager().callEvent((Event)event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void skinApplyEvent(ItemSkinApplyEvent e) {
        SkinType type = e.getSkinType();
        ItemStack item = e.getItem();
        item = AstroItemSkinsAPI.addSkinToItem(item, type);
    }
}
