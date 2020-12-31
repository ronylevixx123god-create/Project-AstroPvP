/*    */ package com.compilex.CosmicArmorSets.cmds;
/*    */ 
/*    */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*    */ import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
/*    */ import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
/*    */ import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
/*    */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*    */ import com.cosmicpvp.cosmicutils.utils.NumberUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.server.v1_7_R4.NBTBase;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandHeroicArmor
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
/* 28 */     if (!sender.isOp()) return true;
/*    */     
/* 30 */     if (args.length >= 2) {
/* 31 */       Player pl = Bukkit.getPlayer(args[1]);
/* 32 */       if (pl == null) {
/* 33 */         sender.sendMessage(CC.Red + "No player found!");
/* 34 */         return true;
/*    */       } 
/*    */       
/* 37 */       HeroicArmorType type = HeroicArmorType.getFromName(args[0]);
/* 38 */       if (type == null) {
/* 39 */         sender.sendMessage(CC.Red + "Invalid Heroic Armor type! Valid: " + Arrays.toString((Object[])HeroicArmorType.values()));
/* 40 */         return true;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 45 */       ItemStack item = CosmicArmorSetsAPI.createHeroicPeice(type);
/*    */       
/* 47 */       if (args.length == 3) {
/* 48 */         if (args[2].equalsIgnoreCase("ench")) {
/* 49 */           item = (new NBTWrapper(item)).setGlowing().build();
/*    */         } else {
/*    */           
/* 52 */           Integer parsed = NumberUtils.parseIntOrNull(args[2]);
/* 53 */           if (parsed != null && parsed.intValue() > 0) {
/*    */             
/* 55 */             NBTWrapper wrapper = new NBTWrapper(item);
/*    */ 
/*    */             
/* 58 */             item = wrapper.setNBTBase("cosmicData", (NBTBase)(new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"))).setInt("armorModifier", parsed).get()).build();
/* 59 */             sender.sendMessage(CC.Red + "Item given with armor modifier: " + parsed);
/*    */           } 
/*    */         } 
/*    */       }
/* 63 */       if (item != null)
/* 64 */         PlayerUtil.giveOrDropProtectedItem(pl, item, -1); 
/* 65 */     } else if (args.length == 1 && args[0].equalsIgnoreCase("convert")) {
/* 66 */       Player player = (Player)sender;
/* 67 */       ItemStack hand = player.getItemInHand();
/* 68 */       boolean armor = GeneralUtil.isArmor(hand), weapon = GeneralUtil.isWeapon(hand);
/* 69 */       if (!armor && !weapon) {
/* 70 */         sender.sendMessage(CC.Red + "Invalid armor item!");
/* 71 */         return true;
/*    */       } 
/*    */       
/* 74 */       if (armor) {
/* 75 */         HeroicArmorType type = HeroicArmorType.getFromMaterial(hand.getType());
/* 76 */         if (type == null) {
/* 77 */           sender.sendMessage(CC.Red + "Invalid armor type given!");
/* 78 */           return true;
/*    */         } 
/* 80 */         player.setItemInHand(CosmicArmorSetsAPI.convertItemToHeroic(hand, type, false));
/* 81 */       } else if (weapon) {
/* 82 */         HeroicWeaponType type = HeroicWeaponType.findFromMaterial(hand.getType());
/* 83 */         if (type == null) {
/* 84 */           sender.sendMessage(CC.Red + "Unable to find item to convert to!");
/* 85 */           return true;
/*    */         } 
/* 87 */         player.setItemInHand(CosmicArmorSetsAPI.convertIntoHeroicWeapon(type, hand, false));
/*    */       } 
/* 89 */       player.updateInventory();
/* 90 */       player.sendMessage(CC.Red + "Item converted.");
/* 91 */       return true;
/*    */     } 
/*    */     
/* 94 */     sender.sendMessage(CC.Red + "Usage: /heroicarmor <HELMET/CHESTPLATE/LEGGINGS/BOOTS> <name>");
/* 95 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\cmds\CommandHeroicArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */