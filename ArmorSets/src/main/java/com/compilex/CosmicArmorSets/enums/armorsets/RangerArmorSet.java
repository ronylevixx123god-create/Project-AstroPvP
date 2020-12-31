/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */ 
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
/*     */ import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
/*     */ import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class RangerArmorSet
/*     */   extends ArmorSetInfo
/*     */   implements WeaponedArmorSet
/*     */ {
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  30 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(
/*  31 */           ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, "&a&lRanger Hood", new String[] { "&a&oThe hood of a legendary", "&a&oRanger lost with time itself.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  42 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/*  43 */                 EnchantmentAPI.getEnchantment("Armored"), 
/*  44 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/*  45 */                 EnchantmentAPI.getEnchantment("Drunk"), 
/*  46 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  47 */                 EnchantmentAPI.getEnchantment("Marksman"), 
/*  48 */                 EnchantmentAPI.getEnchantment("Implants"), 
/*  49 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/*  50 */                 EnchantmentAPI.getEnchantment("Heavy"), 
/*  51 */                 EnchantmentAPI.getEnchantment("Dodge"), 
/*  52 */                 EnchantmentAPI.getEnchantment("Sticky"), 
/*  53 */                 EnchantmentAPI.getEnchantment("Tank"), 
/*  54 */                 EnchantmentAPI.getEnchantment("Hardened") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  59 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, "&a&lRanger Cuirass", new String[] { "&a&oThe lost cuirass of the,", "&a&olast known legendary archer.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  70 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/*  71 */                 EnchantmentAPI.getEnchantment("Armored"), 
/*  72 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/*  73 */                 EnchantmentAPI.getEnchantment("Aegis"), 
/*  74 */                 EnchantmentAPI.getEnchantment("Blood Lust"), 
/*  75 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  76 */                 EnchantmentAPI.getEnchantment("Overload"), 
/*  77 */                 EnchantmentAPI.getEnchantment("Marksman"), 
/*  78 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/*  79 */                 EnchantmentAPI.getEnchantment("Heavy"), 
/*  80 */                 EnchantmentAPI.getEnchantment("Arrow Deflect"), 
/*  81 */                 EnchantmentAPI.getEnchantment("Tank"), 
/*  82 */                 EnchantmentAPI.getEnchantment("Hardened") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/*  87 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short)0, "&a&lRanger Greaves", new String[] { "&a&oThe fabled shin protectors,", "&a&oof an ancient agile Ranger.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  98 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/*  99 */                 EnchantmentAPI.getEnchantment("Armored"), 
/* 100 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/* 101 */                 EnchantmentAPI.getEnchantment("Clarity"), 
/* 102 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/* 103 */                 EnchantmentAPI.getEnchantment("Marksman"), 
/* 104 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/* 105 */                 EnchantmentAPI.getEnchantment("Heavy"), 
/* 106 */                 EnchantmentAPI.getEnchantment("Lucky"), 
/* 107 */                 EnchantmentAPI.getEnchantment("Obsidianshield"), 
/* 108 */                 EnchantmentAPI.getEnchantment("Tank"), 
/* 109 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/* 110 */                 EnchantmentAPI.getEnchantment("Cactus") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/* 115 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, "&a&lRanger Slippers", new String[] { "&a&oLightweight footgear for", "&a&oa ancient nimble Ranger.", "", "&a&lRANGER SET BONUS", "&a&l* &aEnemies bows deal 25% less damage to you.", "&a&l* &aRanger Bow grants 30% increased bow damage.", "&a&l* &aImmune to Teleblock", "&a&l* &aGears IV", "&7(&oRequires all 4 ranger items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 126 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/* 127 */                 EnchantmentAPI.getEnchantment("Armored"), 
/* 128 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/* 129 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/* 130 */                 EnchantmentAPI.getEnchantment("Gears"), 
/* 131 */                 EnchantmentAPI.getEnchantment("Marksman"), 
/* 132 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/* 133 */                 EnchantmentAPI.getEnchantment("Heavy"), 
/* 134 */                 EnchantmentAPI.getEnchantment("Metaphysical"), 
/* 135 */                 EnchantmentAPI.getEnchantment("Tank"), 
/* 136 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/* 137 */                 EnchantmentAPI.getEnchantment("Springs"), 
/* 138 */                 EnchantmentAPI.getEnchantment("Rocket Escape") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsBowDamage() {
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 148 */     return Lists.newArrayList(new String[] { ChatColor.RED + "" + ChatColor.BOLD + "RANGER SET BONUS: " + ChatColor.RED + "-25% INCOMING BOW DMG, +30% RANGER BOW DMG, Immune to Teleblock, Gears IV" });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEquip(Player player) {
/* 153 */     OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
/* 154 */     if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
/*     */       return;
/*     */     }
/*     */     
/* 158 */     EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnequip(Player player) {
/* 163 */     EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 168 */     if (event.getDamager() instanceof org.bukkit.entity.Projectile) {
/* 169 */       event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.75D);
/* 170 */       Bukkit.getLogger().info("lowering damage to due archer damaged: " + event.getDamage(EntityDamageEvent.DamageModifier.BASE) + " from " + event.getDamager());
/* 171 */       return true;
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 178 */     if (damager.getItemInHand() != null && ArmorSet.isRangerBow(damager.getItemInHand())) {
/*     */ 
/*     */       
/* 181 */       if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player)event.getEntity(), 1, damager)) {
/* 182 */         return false;
/*     */       }
/*     */       
/* 185 */       event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.3D);
/* 186 */       Bukkit.getLogger().info("Increasing arrow damage for " + damager.getName());
/* 187 */       return true;
/*     */     } 
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
/* 194 */     if (isBlockedOutpostWorld((Entity)damaged)) return false;
/*     */     
/* 196 */     if (event.getDamager() instanceof org.bukkit.entity.Projectile)
/* 197 */       return modifyDamage(damaged, event, -5.0D, multiplier); 
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
/* 203 */     if (isBlockedOutpostWorld((Entity)damager)) return false;
/*     */     
/* 205 */     if (event.getDamager() instanceof org.bukkit.entity.Projectile) {
/* 206 */       return modifyDamage(damager, event, 6.0D, multiplier);
/*     */     }
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getWeapon(boolean applyTransmog) {
/* 213 */     return ArmorSet.createRangerBow(applyTransmog);
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\RangerArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */