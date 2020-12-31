/*    */ package com.compilex.CosmicArmorSets.cmds;
/*    */ 
/*    */ import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.NumberUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandMasteryShard
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
/* 18 */     if (!sender.isOp()) return true;
/*    */     
/* 20 */     if (args.length >= 3 && 
/* 21 */       args[0].equalsIgnoreCase("give")) {
/* 22 */       Player pl = Bukkit.getPlayer(args[1]);
/* 23 */       if (pl == null) {
/* 24 */         sender.sendMessage(CC.Red + "Invalid online player!");
/* 25 */         return true;
/*    */       } 
/*    */ 
/*    */       
/* 29 */       AbilityArmorSet set = AbilityArmorSet.fromName(args[2]);
/* 30 */       if (set == null) {
/* 31 */         sender.sendMessage(CC.Red + "Invalid Ability Armor set: " + Arrays.toString((Object[])AbilityArmorSet.values()));
/* 32 */         return true;
/*    */       } 
/*    */       
/* 35 */       if (args.length >= 4 && args[3].equalsIgnoreCase("anticrystal")) {
/* 36 */         Integer crystal = (args.length >= 5) ? NumberUtils.parseIntOrNull(args[4]) : null;
/* 37 */         PlayerUtil.giveOrDropProtectedItem(pl, set.createAntiMkitShard((crystal == null || crystal.intValue() <= 0) ? 1 : crystal.intValue()), -1);
/* 38 */         return true;
/*    */       } 
/*    */       
/* 41 */       PlayerUtil.giveOrDropProtectedItem(pl, set.createMasteryShard(true), -1);
/* 42 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 46 */     sender.sendMessage(CC.Red + "/masteryshard give <player> <abilitySet> [anticrystal]");
/* 47 */     return false;
/*    */   }
/*    */ }

