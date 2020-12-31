/*     */ package com.compilex.CosmicArmorSets.enums.abilityarmorsets;
/*     */ 
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
/*     */ import com.compilex.CosmicArmorSets.enums.AbilityArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import org.arkhamnetwork.enchantments.EnchantmentBook;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class NecromancerAbilitySet
/*     */   extends AbilityArmorSetInfo {
/*  22 */   private String color = CC.DarkGreen;
/*  23 */   private String bulletPoint = this.color + CC.Bold + " * " + this.color;
/*  24 */   private Color armorColor = Color.GREEN;
/*     */ 
/*     */   
/*     */   public List<String> getAbilityArmorLore() {
/*  28 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getHelmet(Player player, boolean applyTransmog, boolean mastery) {
/*  33 */     ItemStack helmet = setLeatherColor(
/*  34 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.NECROMANCER.applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_HELMET, this.color + CC.Bold + "Skull of Souls", new String[] {
/*     */                   
/*     */                   CC.GrayI + "A floating skull imbued with", CC.GrayI + "evil enchantments and demonic energy."
/*  37 */                 })).build())).build(), HeroicArmorType.HELMET, true), this.armorColor);
/*     */ 
/*     */     
/*  40 */     addNearlyMaxedEnchants(helmet, new String[] { "Armored", "Implants", "Drunk", "Arrow Deflect", "Death God", "Resilience", "Protection", "Ender Shift" });
/*     */ 
/*     */     
/*  43 */     if (this.random.nextDouble() <= 0.6D)
/*  44 */       addNearlyMaxed(helmet, "Valor"); 
/*  45 */     if (this.random.nextDouble() <= 0.6D)
/*  46 */       addNearlyMaxed(helmet, "Angelic"); 
/*  47 */     if (this.random.nextDouble() <= 0.6D) {
/*  48 */       addNearlyMaxed(helmet, "Tank");
/*     */     }
/*     */     
/*  51 */     if (this.random.nextDouble() <= 0.2D) {
/*  52 */       addNearlyMaxedEnchants(helmet, new String[] { "Paladin Armored" });
/*     */     }
/*  54 */     if (this.random.nextDouble() <= 0.175D) {
/*  55 */       addNearlyMaxedEnchants(helmet, new String[] { "Alien Implants" });
/*     */     }
/*  57 */     if (mastery) {
/*  58 */       addRandomMasteryEnchants(helmet, 2);
/*     */     }
/*  60 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(helmet) : helmet;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(Player player, boolean applyTransmog, boolean mastery) {
/*  65 */     ItemStack chestplate = setLeatherColor(
/*  66 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.NECROMANCER
/*  67 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_CHESTPLATE, this.color + CC.Bold + "Robe of the Necromancer", new String[] {
/*     */                   
/*     */                   CC.GrayI + "Spider Silk woven robes that camouflage", CC.GrayI + "into the darkness of the night"
/*  70 */                 })).build())).build(), HeroicArmorType.CHESTPLATE, true), this.armorColor);
/*     */ 
/*     */     
/*  73 */     addNearlyMaxedEnchants(chestplate, new String[] { "Armored", "Overload", "Deathbringer", "Enlighted", "Diminish", "Aegis", "Leadership", "Blood Lust", "Shockwave" });
/*     */ 
/*     */ 
/*     */     
/*  77 */     if (this.random.nextDouble() <= 0.6D)
/*  78 */       addNearlyMaxed(chestplate, "Valor"); 
/*  79 */     if (this.random.nextDouble() <= 0.6D)
/*  80 */       addNearlyMaxed(chestplate, "Angelic"); 
/*  81 */     if (this.random.nextDouble() <= 0.6D) {
/*  82 */       addNearlyMaxed(chestplate, "Tank");
/*     */     }
/*  84 */     if (this.random.nextDouble() <= 0.2D) {
/*  85 */       addNearlyMaxed(chestplate, "Paladin Armored");
/*     */     }
/*  87 */     if (this.random.nextDouble() <= 0.175D) {
/*  88 */       addNearlyMaxed(chestplate, "Godly Overload");
/*     */     }
/*  90 */     if (this.random.nextDouble() <= 0.225D) {
/*  91 */       addNearlyMaxed(chestplate, "Planetary Deathbringer");
/*     */     }
/*  93 */     if (this.random.nextDouble() <= 0.225D) {
/*  94 */       addNearlyMaxed(chestplate, "Divine Enlighted");
/*     */     }
/*  96 */     if (this.random.nextDouble() <= 0.15D) {
/*  97 */       addNearlyMaxed(chestplate, "Vengeful Diminish");
/*     */     }
/*  99 */     if (mastery) {
/* 100 */       addRandomMasteryEnchants(chestplate, 2);
/*     */     }
/* 102 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(chestplate) : chestplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(Player player, boolean applyTransmog, boolean mastery) {
/* 107 */     ItemStack leggings = setLeatherColor(
/* 108 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.NECROMANCER
/* 109 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_LEGGINGS, this.color + CC.Bold + "Tattered Wrappings of Death", new String[] {
/*     */                   
/*     */                   CC.GrayI + "Once tormented by death, it now", CC.GrayI + "loyally serves and protects you."
/* 112 */                 })).build())).build(), HeroicArmorType.LEGGINGS, true), this.armorColor);
/*     */ 
/*     */     
/* 115 */     addNearlyMaxedEnchants(leggings, new String[] { "Armored", "Nature Wrath", "Clarity", "Obsidianshield", "Cactus", "Lucky", "Voodoo", "Spirits" });
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (this.random.nextDouble() <= 0.6D)
/* 120 */       addNearlyMaxed(leggings, "Valor"); 
/* 121 */     if (this.random.nextDouble() <= 0.6D)
/* 122 */       addNearlyMaxed(leggings, "Angelic"); 
/* 123 */     if (this.random.nextDouble() <= 0.6D) {
/* 124 */       addNearlyMaxed(leggings, "Tank");
/*     */     }
/* 126 */     if (this.random.nextDouble() <= 0.2D) {
/* 127 */       addNearlyMaxed(leggings, "Paladin Armored");
/*     */     }
/* 129 */     if (this.random.nextDouble() <= 0.3D) {
/* 130 */       addNearlyMaxed(leggings, "Immortal");
/*     */     }
/* 132 */     if (mastery)
/* 133 */       addRandomMasteryEnchants(leggings, 2); 
/* 134 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(leggings) : leggings;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(Player player, boolean applyTransmog, boolean mastery) {
/* 139 */     ItemStack boots = setLeatherColor(
/* 140 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.NECROMANCER
/* 141 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_BOOTS, this.color + CC.Bold + "Demon Foot", new String[] {
/*     */                   
/*     */                   CC.GrayI + "A good luck charm for those who are", CC.GrayI + "masters of the dark magick of necromancy."
/* 144 */                 })).build())).build(), HeroicArmorType.BOOTS, true), this.armorColor);
/*     */ 
/*     */     
/* 147 */     addNearlyMaxedEnchants(boots, new String[] { "Armored", "Metaphysical", "Ender Walker", "Springs", "Rocket Escape", "Dodge", "Phoenix", "Guardians" });
/*     */ 
/*     */     
/* 150 */     addMaxedEnchant(boots, "Gears");
/*     */     
/* 152 */     if (this.random.nextDouble() <= 0.6D)
/* 153 */       addNearlyMaxed(boots, "Valor"); 
/* 154 */     if (this.random.nextDouble() <= 0.6D)
/* 155 */       addNearlyMaxed(boots, "Angelic"); 
/* 156 */     if (this.random.nextDouble() <= 0.6D) {
/* 157 */       addNearlyMaxed(boots, "Tank");
/*     */     }
/* 159 */     if (this.random.nextDouble() <= 0.2D) {
/* 160 */       addNearlyMaxed(boots, "Paladin Armored");
/*     */     }
/* 162 */     if (this.random.nextDouble() <= 0.225D) {
/* 163 */       addNearlyMaxed(boots, "Ethereal Dodge");
/*     */     }
/* 165 */     if (this.random.nextDouble() <= 0.35D) {
/* 166 */       addNearlyMaxed(boots, "Phoenix");
/*     */     }
/* 168 */     if (this.random.nextDouble() <= 0.125D) {
/* 169 */       addNearlyMaxed(boots, "Guided Rocket Escape");
/*     */     }
/* 171 */     if (mastery) {
/* 172 */       addRandomMasteryEnchants(boots, 2);
/*     */     }
/* 174 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(boots) : boots;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack addRandomMasteryEnchants(ItemStack item, int count) {
/* 179 */     List<String> enchants = Lists.newArrayList(new String[] { "Mortal Coil", "Soul Siphon", "Rot and Decay", "Demonic Gateway" });
/*     */     
/* 181 */     for (int i = 0; i < count && 
/* 182 */       !enchants.isEmpty(); i++) {
/* 183 */       String random = enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));
/*     */       
/* 185 */       if (random != null) {
/* 186 */         enchants.remove(random);
/*     */       }
/* 188 */       addNearlyMaxed(item, random);
/*     */     } 
/*     */     
/* 191 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 196 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 206 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\abilityarmorsets\NecromancerAbilitySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */