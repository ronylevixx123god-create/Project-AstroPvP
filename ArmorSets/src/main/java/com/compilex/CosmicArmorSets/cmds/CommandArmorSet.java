/*    */ package com.compilex.CosmicArmorSets.cmds;
/*    */ 
/*    */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*    */ import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
/*    */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*    */ import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
/*    */ import com.compilex.CosmicArmorSets.enums.armorsets.DimensionalTravelerSet;
/*    */ import com.compilex.CosmicArmorSets.enums.armorsets.MotherOfYijki;
/*    */ import com.compilex.CosmicArmorSets.menu.ArmorSetHelpMenu;
/*    */ import com.compilex.CosmicKOTH.utils.KothItemUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.NumberUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class CommandArmorSet
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 27 */     if (args.length == 1 && args[0].equalsIgnoreCase("help") && sender instanceof Player) {
/* 28 */       (new ArmorSetHelpMenu((Player)sender)).openGUI((JavaPlugin)CosmicArmorSetsPlugin.get_());
/* 29 */       return true;
/*    */     } 
/*    */     
/* 32 */     if (!sender.isOp()) {
/* 33 */       return true;
/*    */     }
/* 35 */     if (args.length == 0) {
/* 36 */       sender.sendMessage("/armorset <armorSet>/AXE/SWORD/extractioncrystal [crystal]");
/* 37 */       return true;
/*    */     } 
/* 39 */     if (args.length == 1 && (args[0].equals("travelerpassive") || args[0].equalsIgnoreCase("dimensionalpassive"))) {
/* 40 */       ((DimensionalTravelerSet) ArmorSet.DIMENSIONAL_TRAVELER.getArmorSetInfo()).onDimensionalRift((Player)sender);
/* 41 */       sender.sendMessage(CC.Red + "Proc'ing Dimensional Traveler!");
/* 42 */       return true;
/*    */     } 
/*    */     
/* 45 */     if (args.length == 1 && args[0].equals("yijkirev")) {
/* 46 */       Player p = (Player)sender;
/* 47 */       ((MotherOfYijki)ArmorSet.MOTHER_OF_YIJKI.getArmorSetInfo()).activateYijki(p.getLocation(), p);
/* 48 */       return true;
/* 49 */     }  if (args.length == 1 && args[0].equalsIgnoreCase("rangerbow")) {
/* 50 */       ((Player)sender).getInventory().addItem(new ItemStack[] { ArmorSet.createRangerBow() });
/* 51 */       return true;
/*    */     } 
/*    */     
/* 54 */     if (args.length >= 1) {
/*    */       
/* 56 */       if (args[0].equalsIgnoreCase("AXE") || args[0].equalsIgnoreCase("SWORD")) {
/* 57 */         HeroicWeaponType type = HeroicWeaponType.valueOf(args[0]);
/*    */         
/* 59 */         ((Player)sender).getInventory().addItem(new ItemStack[] { CosmicArmorSetsAPI.createHeroicWeapon(type) });
/* 60 */         return true;
/*    */       } 
/*    */       
/* 63 */       ArmorSet as = null;
/*    */       try {
/* 65 */         as = ArmorSet.valueOf(args[0].toUpperCase());
/* 66 */       } catch (Exception exception) {}
/*    */ 
/*    */       
/* 69 */       if (args.length >= 2 && args[1].equalsIgnoreCase("crystal") && as != null) {
/* 70 */         Player pSender = (Player)sender;
/* 71 */         Double given = (args.length == 3) ? NumberUtils.parseDoubleOrNull(args[2]) : null;
/* 72 */         PlayerUtil.giveOrDropProtectedItem(pSender, ArmorSet.createCrystal(Lists.newArrayList(new ArmorSet[] { as }), (given == null) ? 20.0D : given.doubleValue()), -1);
/* 73 */         sender.sendMessage(CC.Red + "Crystal added!");
/* 74 */         return true;
/*    */       } 
/*    */       
/* 77 */       if (as != null && as.getArmorSetInfo() != null && 
/* 78 */         sender instanceof Player) {
/* 79 */         Player pSender = (Player)sender;
/* 80 */         for (ItemStack i : CosmicArmorSetsAPI.getArmorSet(as)) {
/* 81 */           pSender.getInventory().addItem(new ItemStack[] { i });
/*    */         } 
/*    */         
/* 84 */         ItemStack weapon = CosmicArmorSetsAPI.getWeaponFromArmorSet(as);
/* 85 */         if (weapon != null) {
/* 86 */           pSender.getInventory().addItem(new ItemStack[] { weapon });
/*    */         }
/* 88 */         if (as == ArmorSet.KOTH) {
/* 89 */           pSender.getInventory().addItem(new ItemStack[] { KothItemUtils.getKothAxe(pSender.getName()) });
/* 90 */           pSender.getInventory().addItem(new ItemStack[] { KothItemUtils.getKothSwordPvP(pSender.getName()) });
/*    */         } 
/*    */         
/* 93 */         return true;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 98 */     sender.sendMessage(CC.Red + "Armor Sets: " + Arrays.toString((Object[])ArmorSet.values()));
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\cmds\CommandArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */