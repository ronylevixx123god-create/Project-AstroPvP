/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */ 
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
/*     */ import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ public class PhantomArmorSet
/*     */   extends ArmorSetInfo
/*     */   implements WeaponedArmorSet
/*     */ {
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  29 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, "&c&lPhantom Hood", new String[] { "&cThe fabled hood of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  36 */             }), Arrays.asList(new CustomEnchantment[] {
/*  37 */               EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  38 */               EnchantmentAPI.getEnchantment("Drunk"), 
/*  39 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/*  40 */               EnchantmentAPI.getEnchantment("Implants"), 
/*  41 */               EnchantmentAPI.getEnchantment("Tank"), 
/*  42 */               EnchantmentAPI.getEnchantment("Armored"), 
/*  43 */               EnchantmentAPI.getEnchantment("Angelic")
/*     */             })));
/*     */   }
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  48 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, "&c&lPhantom Shroud", new String[] { "&cThe legendary shroud of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  55 */             }), Arrays.asList(new CustomEnchantment[] {
/*  56 */               EnchantmentAPI.getEnchantment("Overload"), 
/*  57 */               EnchantmentAPI.getEnchantment("Blood Lust"), 
/*  58 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/*  59 */               EnchantmentAPI.getEnchantment("Armored"), 
/*  60 */               EnchantmentAPI.getEnchantment("Tank"), 
/*  61 */               EnchantmentAPI.getEnchantment("Dodge"), 
/*  62 */               EnchantmentAPI.getEnchantment("Angelic")
/*     */             })));
/*     */   }
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/*  67 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short)0, "&c&lPhantom Robeset", new String[] { "&cThe demonic robe of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  74 */             }), Arrays.asList(new CustomEnchantment[] {
/*  75 */               EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  76 */               EnchantmentAPI.getEnchantment("Clarity"), 
/*  77 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/*  78 */               EnchantmentAPI.getEnchantment("Obsidianshield"), 
/*  79 */               EnchantmentAPI.getEnchantment("Tank"), 
/*  80 */               EnchantmentAPI.getEnchantment("Armored"), 
/*  81 */               EnchantmentAPI.getEnchantment("Angelic"), 
/*  82 */               EnchantmentAPI.getEnchantment("Cactus")
/*     */             })));
/*     */   }
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/*  87 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, "&c&lPhantom Sandals", new String[] { "&cThe silent sandals of the Phantom.", "", "&c&lPHANTOM SET BONUS", "&cDeal +25% damage to all enemies.", "&7(&oRequires all 4 phantom items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  94 */             }), Arrays.asList(new CustomEnchantment[] {
/*  95 */               EnchantmentAPI.getEnchantment("Gears"), 
/*  96 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/*  97 */               EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  98 */               EnchantmentAPI.getEnchantment("Rocket Escape"), 
/*  99 */               EnchantmentAPI.getEnchantment("Springs"), 
/* 100 */               EnchantmentAPI.getEnchantment("Armored"), 
/* 101 */               EnchantmentAPI.getEnchantment("Tank")
/*     */             })));
/*     */   }
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 106 */     return Lists.newArrayList(new String[] { ChatColor.RED + "" + ChatColor.BOLD + "PHANTOM SET BONUS: " + ChatColor.RED + "+25% DMG" });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 111 */     removeResistance(damaged);
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 118 */     removeResistance(damager);
/* 119 */     if (isBlockedOutpostWorld((Entity)damager)) {
/* 120 */       return false;
/*     */     }
/* 122 */     if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player)event.getEntity(), 3, damager)) {
/* 123 */       return false;
/*     */     }
/* 125 */     event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.25D);
/*     */ 
/*     */     
/* 128 */     ItemStack handItem = damager.getItemInHand();
/* 129 */     if (handItem != null) {
/* 130 */       Material type = handItem.getType();
/* 131 */       if (type != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && (new NBTWrapper(handItem)).getString("cosmicType").equals("PHANTOM_SWORD"))
/*     */       {
/* 133 */         event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1D);
/*     */       }
/*     */     } 
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int applied, EntityDamageByEntityEvent event) {
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
/* 146 */     if (isBlockedOutpostWorld((Entity)damager)) return false; 
/* 147 */     return modifyDamage(damager, event, 5.0D, applied);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getWeapon(boolean applyTransmog) {
/* 152 */     return (new NBTWrapper((new ItemBuilder(Material.DIAMOND_SWORD, CC.RedB + "Phantom Scythe", new String[] { CC.Gray + "An eerie blade designed to", CC.Gray + "cut through the enemies.", "", CC.RedB + "PHANTOM WEAPON BONUS", CC.Red + "Deal +10% damage to all enemies.", CC.Gray + "(" + CC.GrayI + "Requires all 4 phantom items." + CC.Gray + ")"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 160 */           })).enchant(Enchantment.DAMAGE_ALL, 5)
/* 161 */         .enchant(Enchantment.DURABILITY, 3).build()))
/* 162 */       .setString("cosmicType", "PHANTOM_SWORD").build();
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\PhantomArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */