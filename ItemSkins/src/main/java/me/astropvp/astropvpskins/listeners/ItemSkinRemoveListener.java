/*    */
package me.astropvp.astropvpskins.listeners;
/*    */
/*    */

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import me.astropvp.astropvpskins.objects.ItemSkin;
import me.astropvp.astropvpskins.objects.skins.ItemSkinCacher;
import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;

/*    */
/*    */ public class ItemSkinRemoveListener
        /*    */ implements Listener {
    /*    */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    /*    */ public void removeFromItem(InventoryClickEvent e) {
        /* 18 */
        if (!(e.getWhoClicked() instanceof Player)) {
            /*    */
            return;
            /*    */
        }
        /* 21 */
        if (e.getClick() != ClickType.RIGHT) {
            /*    */
            return;
            /*    */
        }
        /* 24 */
        Player player = (Player) e.getWhoClicked();
        /* 25 */
        ItemStack cursor = e.getCursor();
        /* 26 */
        ItemStack current = e.getCurrentItem();
        /* 27 */
        if (cursor.getType() != Material.AIR || current == null) {
            /*    */
            return;
            /*    */
        }
        /* 30 */
        SkinType type = AstroItemSkinsAPI.getSkinTypeFromNBT(current);
        /* 31 */
        if (type == null) {
            /*    */
            return;
            /*    */
        }
        /* 34 */
        if (!type.getItemSkinType().isType(current)) {
            /*    */
            return;
            /*    */
        }
        /* 37 */
        ItemStack give = type.getItem();
        /* 38 */
        player.setItemOnCursor(give);
        /* 39 */
        e.setCurrentItem(AstroItemSkinsAPI.removeSkinFromItem(type, current));
        new ItemSkinCacher(player);
        /* 40 */
        e.setCancelled(true);
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\listeners\ItemSkinRemoveListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */