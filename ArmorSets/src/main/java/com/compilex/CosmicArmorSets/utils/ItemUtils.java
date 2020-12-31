/*    */ package com.compilex.CosmicArmorSets.utils;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.rit.sucy.CustomEnchantment;
/*    */ import com.rit.sucy.EnchantmentAPI;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ public class ItemUtils
/*    */ {
/*    */   public static ItemStack buildItem(Material m, short metaData, String name, String... description) {
/* 18 */     ItemStack is = new ItemStack(m, 1, metaData);
/* 19 */     ItemMeta im = is.getItemMeta();
/* 20 */     if (name != null) {
/* 21 */       im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
/*    */     }
/* 23 */     if (description != null) {
/* 24 */       List<String> lore = Lists.newArrayList();
/* 25 */       for (String desc : description) {
/* 26 */         if (desc != null)
/*    */         {
/*    */           
/* 29 */           lore.add(ChatColor.translateAlternateColorCodes('&', desc)); } 
/*    */       } 
/* 31 */       im.setLore(lore);
/*    */     } 
/*    */     
/* 34 */     is.setItemMeta(im);
/* 35 */     return is;
/*    */   }
/*    */   
/*    */   public static int getRandomArmorSetEnchantLevel(String enchantment) {
/* 39 */     CustomEnchantment ce = EnchantmentAPI.getEnchantment(enchantment);
/* 40 */     if (ce.heroicEnchantment) {
/* 41 */       return 1 + (new Random()).nextInt(ce.getMaxLevel());
/*    */     }
/* 43 */     return Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + (new Random()).nextInt(3));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack applyCustomEnchantments(ItemStack i, List<CustomEnchantment> enchants) {
/* 49 */     for (CustomEnchantment ce : enchants) {
/* 50 */       if (ce.heroicEnchantment) {
/* 51 */         ce.nonHeroicVersion.addToItem(i, ce.nonHeroicVersion.getMaxLevel());
/* 52 */         ce.addToItem(i, 1 + (new Random()).nextInt(ce.getMaxLevel())); continue;
/*    */       } 
/* 54 */       ce.addToItem(i, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + (new Random()).nextInt(3)));
/*    */     } 
/*    */ 
/*    */     
/* 58 */     i.addEnchantment(Enchantment.DURABILITY, 3);
/* 59 */     i.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
/*    */     
/* 61 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSet\\utils\ItemUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */