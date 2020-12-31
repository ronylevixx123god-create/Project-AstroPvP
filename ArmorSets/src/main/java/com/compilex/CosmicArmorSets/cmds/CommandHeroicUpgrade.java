/*    */ package com.compilex.CosmicArmorSets.cmds;
/*    */ 
/*    */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*    */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.NumberUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
/*    */ import java.util.Arrays;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.spigotmc.SpigotConfig;
/*    */ 
/*    */ public class CommandHeroicUpgrade
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 21 */     if (!sender.isOp()) {
/* 22 */       sender.sendMessage(SpigotConfig.unknownCommandMessage);
/* 23 */       return true;
/*    */     } 
/*    */     
/* 26 */     if (args.length >= 3) {
/* 27 */       if (args[0].equalsIgnoreCase("give")) {
/* 28 */         Player player = Bukkit.getPlayer(args[1]);
/* 29 */         if (player == null) {
/* 30 */           sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid player given!");
/* 31 */           return true;
/*    */         } 
/*    */         
/* 34 */         ArmorSet set = ArmorSet.fromName(args[2]);
/* 35 */         if (set == null) {
/* 36 */           sender.sendMessage(CC.Red + "Invalid armor set: " + Arrays.toString((Object[])ArmorSet.values()));
/* 37 */           return true;
/*    */         } 
/*    */         
/* 40 */         Double percent = (args.length == 4) ? NumberUtils.parseDoubleOrNull(args[3]) : null;
/* 41 */         if (percent == null) {
/* 42 */           percent = Double.valueOf((ThreadLocalRandom.current().nextInt(51) + 50));
/*    */         }
/* 44 */         PlayerUtil.giveOrDropProtectedItem(player, CosmicArmorSetsAPI.createArmorSetUpgrade(set, percent.doubleValue()), -1);
/* 45 */         sender.sendMessage(CC.Red + "Added " + set.name() + " ArmorSet at " + percent + "% to " + player.getName() + "'s Inventory");
/* 46 */         return true;
/*    */       } 
/* 48 */     } else if (args.length == 2 && 
/* 49 */       args[0].equalsIgnoreCase("giverandom")) {
/* 50 */       Player player = Bukkit.getPlayer(args[1]);
/* 51 */       if (player == null) {
/* 52 */         sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid player given!");
/* 53 */         return true;
/*    */       } 
/*    */       
/* 56 */       PlayerUtil.giveOrDropProtectedItem(player, CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), -1);
/* 57 */       sender.sendMessage(CC.Red + "Added Random Heroic Upgrade to " + player.getName() + "!");
/* 58 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 62 */     sender.sendMessage(CC.Red + "/heroicupgrade give <player> <armorset> [percent]");
/* 63 */     sender.sendMessage(CC.Red + "/heroicupgrade giverandom <player>");
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\cmds\CommandHeroicUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */