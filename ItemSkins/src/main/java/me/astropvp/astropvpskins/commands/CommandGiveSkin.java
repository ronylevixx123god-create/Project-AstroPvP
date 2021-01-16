/*    */ package me.astropvp.astropvpskins.commands;
/*    */ 
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import me.astropvp.astropvpskins.utils.Utils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandGiveSkin
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
/* 19 */     if (!sender.hasPermission("skin.give")) {
/* 20 */       return false;
/*    */     }
/* 22 */     if (args.length < 3) {
/* 23 */       sender.sendMessage("/giveskin <type of skin> <amount> <player>"); byte b; int i; SkinType[] arrayOfSkinType;
/* 24 */       for (i = (arrayOfSkinType = SkinType.values()).length, b = 0; b < i; ) { SkinType skinType1 = arrayOfSkinType[b];
/* 25 */         sender.sendMessage(skinType1.name() + CC.WhiteB + " - " + skinType1.getDisplayName()); b++; }
/*    */       
/* 27 */       return false;
/*    */     } 
/* 29 */     String type = args[0];
/* 30 */     int amount = Integer.parseInt(args[1]);
/* 31 */     Player player = Bukkit.getPlayer(args[2]);
/* 32 */     SkinType skinType = SkinType.valueOf(type);
/* 33 */     ItemStack item = skinType.getItem();
/* 34 */     if (Utils.isInventoryFull(player)) {
/* 35 */       player.getWorld().dropItem(player.getLocation(), item);
/*    */     } else {
/* 37 */       player.getInventory().addItem(item);
/*    */     } 
/* 39 */     player.sendMessage(CC.Green + "Added item " + amount + "x " + skinType.getDisplayName() + CC.Green + " to " +
/* 40 */         player.getName() + "'s Inventory.");
/* 41 */     return false;
/*    */   }
/*    */ }
