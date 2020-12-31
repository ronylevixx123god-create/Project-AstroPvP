/*     */ package com.compilex.CosmicArmorSets.enums;
/*     */ 
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ 
/*     */ 
/*     */ public abstract class AbilityArmorSetInfo
/*     */   extends ArmorSetInfo
/*     */ {
/*     */   public abstract List<String> getAbilityArmorLore();
/*     */   
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  22 */     return getHelmet(null, applyTransmog, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  27 */     return getChestplate(null, applyTransmog, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/*  32 */     return getLeggings(null, applyTransmog, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/*  37 */     return getBoots(null, applyTransmog, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract ItemStack getHelmet(Player paramPlayer, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public abstract ItemStack getChestplate(Player paramPlayer, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public abstract ItemStack getLeggings(Player paramPlayer, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public abstract ItemStack getBoots(Player paramPlayer, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   protected ItemStack setLeatherColor(ItemStack item, Color color) {
/*  59 */     ItemMeta im = item.getItemMeta();
/*  60 */     if (im instanceof LeatherArmorMeta) {
/*  61 */       LeatherArmorMeta lm = (LeatherArmorMeta)im;
/*  62 */       lm.setColor(color);
/*  63 */       item.setItemMeta((ItemMeta)lm);
/*     */     } 
/*  65 */     return item;
/*     */   }
/*     */   
/*     */   public ItemStack addMaxedEnchant(ItemStack item, String name) {
/*  69 */     CustomEnchantment enchant = EnchantmentAPI.getEnchantment(name);
/*     */     
/*  71 */     if (enchant != null) {
/*  72 */       enchant.addToItem(item, enchant.getMaxLevel());
/*     */     }
/*  74 */     return item;
/*     */   }
/*     */   
/*     */   public ItemStack addNearlyMaxedEnchants(ItemStack item, String... enchantNames) {
/*  78 */     item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
/*  79 */     item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
/*  80 */     for (String enchant : enchantNames) {
/*  81 */       addNearlyMaxed(item, enchant);
/*     */     }
/*  83 */     return item;
/*     */   }
/*     */   
/*     */   public ItemStack addNearlyMaxed(ItemStack item, String enchantName) {
/*  87 */     CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(enchantName);
/*  88 */     if (enchantment != null) {
/*  89 */       int level, maxLevel = enchantment.getMaxLevel();
/*     */ 
/*     */       
/*  92 */       if (enchantment.heroicEnchantment) {
/*  93 */         level = 1 + ThreadLocalRandom.current().nextInt(maxLevel);
/*     */       }
/*  95 */       else if (maxLevel == 1) {
/*  96 */         level = maxLevel;
/*  97 */       } else if (maxLevel == 2) {
/*  98 */         level = maxLevel - ThreadLocalRandom.current().nextInt(2);
/*  99 */       } else if (maxLevel == 3) {
/* 100 */         level = maxLevel - ThreadLocalRandom.current().nextInt(3);
/*     */       } else {
/* 102 */         level = maxLevel - ThreadLocalRandom.current().nextInt(4);
/*     */       } 
/*     */       
/* 105 */       if (level == maxLevel && level > 1 && Math.random() < 0.25D) {
/* 106 */         level--;
/*     */       }
/*     */       
/* 109 */       if (enchantment.heroicEnchantment && enchantment.nonHeroicVersion != null) {
/* 110 */         enchantment.nonHeroicVersion.addToItem(item, enchantment.nonHeroicVersion.getMaxLevel());
/*     */       }
/* 112 */       enchantment.addToItem(item, level);
/*     */     } 
/*     */     
/* 115 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\AbilityArmorSetInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */