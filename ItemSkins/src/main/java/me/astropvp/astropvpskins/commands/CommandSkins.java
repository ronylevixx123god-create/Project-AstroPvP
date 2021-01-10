/*    */ package me.astropvp.astropvpskins.commands;
/*    */ 
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import me.astropvp.astropvpskins.enums.ItemSkinType;
/*    */ import me.astropvp.astropvpskins.utils.InventoryItems;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSkins
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 22 */     if (sender instanceof Player) {
/* 23 */       ((Player)sender).openInventory(getInventory());
/*    */     }
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public static Inventory getInventory() {
/* 29 */     Inventory inventory = Bukkit.createInventory(null, 45, "§8Astro Item Skins");
/* 30 */     HashMap<ItemSkinType, Integer> map = new HashMap<>();
/* 31 */     map.put(ItemSkinType.HELMET, 4);
/* 32 */     map.put(ItemSkinType.CHESTPLATE, 13);
/* 33 */     map.put(ItemSkinType.AMULET, 14);
/* 34 */     map.put(ItemSkinType.LEGGINGS, 22);
/* 35 */     map.put(ItemSkinType.BELT, 23);
/* 36 */     map.put(ItemSkinType.BOOTS, 31);
/* 37 */     map.put(ItemSkinType.BOW, 40);
/* 38 */     map.put(ItemSkinType.SWORD, 39);
/* 39 */     map.put(ItemSkinType.AXE, 41);
/* 40 */     map.put(ItemSkinType.PICKAXE, 42);
/* 41 */     for (Map.Entry<ItemSkinType, Integer> entry : map.entrySet()) {
/* 42 */       inventory.setItem(entry.getValue(), InventoryItems.createItemSkinTemplate(entry.getKey()));
/*    */     }
/* 44 */     for (int i = 0; i < inventory.getSize(); i++) {
/* 45 */       if (inventory.getItem(i) == null) {
/* 46 */         inventory.setItem(i, InventoryItems.createPane(7));
/*    */       }
/*    */     } 
/* 49 */     return inventory;
/*    */   }
/*    */ }


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\commands\CommandSkins.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */