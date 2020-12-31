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
/*     */ 
/*     */ public class DeathKnightAbilitySet
/*     */   extends AbilityArmorSetInfo
/*     */ {
/*  24 */   private String color = CC.Blue;
/*  25 */   private String bulletPoint = this.color + CC.Bold + " * " + this.color;
/*     */   public Color getArmorColor() {
/*  27 */     return this.armorColor;
/*  28 */   } private Color armorColor = Color.fromRGB(2243001);
/*     */ 
/*     */   
/*     */   public List<String> getAbilityArmorLore() {
/*  32 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getHelmet(Player player, boolean applyTransmog, boolean mastery) {
/*  38 */     ItemStack helmet = setLeatherColor(
/*  39 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT.applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_HELMET, this.color + CC.Bold + "Crown of the Lich King", new String[0]))
/*  40 */               .build())).build(), HeroicArmorType.HELMET, true), this.armorColor);
/*     */     
/*  42 */     addNearlyMaxedEnchants(helmet, new String[] { "Armored", "Implants", "Drunk", "Arrow Deflect", "Death God", "Protection", "Ender Shift", "Aquatic", "Repair Guard" });
/*     */ 
/*     */     
/*  45 */     if (this.random.nextDouble() <= 0.6D)
/*  46 */       addNearlyMaxed(helmet, "Valor"); 
/*  47 */     if (this.random.nextDouble() <= 0.6D)
/*  48 */       addNearlyMaxed(helmet, "Angelic"); 
/*  49 */     if (this.random.nextDouble() <= 0.6D) {
/*  50 */       addNearlyMaxed(helmet, "Tank");
/*     */     }
/*     */     
/*  53 */     if (this.random.nextDouble() <= 0.2D) {
/*  54 */       addNearlyMaxedEnchants(helmet, new String[] { "Paladin Armored" });
/*     */     }
/*  56 */     if (this.random.nextDouble() <= 0.175D) {
/*  57 */       addNearlyMaxedEnchants(helmet, new String[] { "Alien Implants" });
/*     */     }
/*  59 */     if (this.random.nextDouble() <= 0.35D) {
/*  60 */       addNearlyMaxed(helmet, "Immortal");
/*     */     }
/*     */     
/*  63 */     if (mastery) {
/*  64 */       addRandomMasteryEnchants(helmet, 2);
/*     */     }
/*  66 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(helmet) : helmet;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(Player player, boolean applyTransmog, boolean mastery) {
/*  71 */     ItemStack chestplate = setLeatherColor(
/*  72 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT
/*  73 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_CHESTPLATE, this.color + CC.Bold + "Death Knight Platebody", new String[0]))
/*  74 */               .build())).build(), HeroicArmorType.CHESTPLATE, true), this.armorColor);
/*     */ 
/*     */     
/*  77 */     addNearlyMaxedEnchants(chestplate, new String[] { "Armored", "Overload", "Deathbringer", "Enlighted", "Diminish", "Aegis", "Leadership", "Blood Lust", "Blood Link" });
/*     */ 
/*     */     
/*  80 */     if (this.random.nextDouble() <= 0.6D)
/*  81 */       addNearlyMaxed(chestplate, "Valor"); 
/*  82 */     if (this.random.nextDouble() <= 0.6D)
/*  83 */       addNearlyMaxed(chestplate, "Angelic"); 
/*  84 */     if (this.random.nextDouble() <= 0.6D) {
/*  85 */       addNearlyMaxed(chestplate, "Tank");
/*     */     }
/*  87 */     if (this.random.nextDouble() <= 0.2D) {
/*  88 */       addNearlyMaxed(chestplate, "Paladin Armored");
/*     */     }
/*  90 */     if (this.random.nextDouble() <= 0.175D) {
/*  91 */       addNearlyMaxed(chestplate, "Godly Overload");
/*     */     }
/*  93 */     if (this.random.nextDouble() <= 0.225D) {
/*  94 */       addNearlyMaxed(chestplate, "Planetary Deathbringer");
/*     */     }
/*  96 */     if (this.random.nextDouble() <= 0.225D) {
/*  97 */       addNearlyMaxed(chestplate, "Divine Enlighted");
/*     */     }
/*  99 */     if (this.random.nextDouble() <= 0.15D) {
/* 100 */       addNearlyMaxed(chestplate, "Vengeful Diminish");
/*     */     }
/* 102 */     if (mastery) {
/* 103 */       addRandomMasteryEnchants(chestplate, 2);
/*     */     }
/* 105 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(chestplate) : chestplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(Player player, boolean applyTransmog, boolean mastery) {
/* 110 */     ItemStack leggings = setLeatherColor(
/* 111 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT
/* 112 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_LEGGINGS, this.color + CC.Bold + "Death Knight Leggings", new String[0]))
/*     */               
/* 114 */               .build())).build(), HeroicArmorType.LEGGINGS, true), this.armorColor);
/*     */ 
/*     */     
/* 117 */     addNearlyMaxedEnchants(leggings, new String[] { "Armored", "Nature Wrath", "Dodge", "Obsidianshield", "Cactus", "Lucky", "Voodoo", "Spirits" });
/*     */ 
/*     */     
/* 120 */     if (this.random.nextDouble() <= 0.6D)
/* 121 */       addNearlyMaxed(leggings, "Valor"); 
/* 122 */     if (this.random.nextDouble() <= 0.6D)
/* 123 */       addNearlyMaxed(leggings, "Angelic"); 
/* 124 */     if (this.random.nextDouble() <= 0.6D) {
/* 125 */       addNearlyMaxed(leggings, "Tank");
/*     */     }
/* 127 */     if (this.random.nextDouble() <= 0.2D) {
/* 128 */       addNearlyMaxed(leggings, "Paladin Armored");
/*     */     }
/* 130 */     if (this.random.nextDouble() <= 0.3D) {
/* 131 */       addNearlyMaxed(leggings, "Immortal");
/*     */     }
/* 133 */     if (mastery)
/* 134 */       addRandomMasteryEnchants(leggings, 2); 
/* 135 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(leggings) : leggings;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(Player player, boolean applyTransmog, boolean mastery) {
/* 140 */     ItemStack boots = setLeatherColor(
/* 141 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT
/* 142 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_BOOTS, this.color + CC.Bold + "Boots of the Scourge", new String[0]))
/*     */               
/* 144 */               .build())).build(), HeroicArmorType.BOOTS, true), this.armorColor);
/*     */ 
/*     */     
/* 147 */     addNearlyMaxedEnchants(boots, new String[] { "Armored", "Metaphysical", "Ender Walker", "Springs", "Rocket Escape", "Phoenix", "Guardians", "Clarity" });
/*     */ 
/*     */     
/* 150 */     addMaxedEnchant(boots, "Gears");
/*     */     
/* 152 */     if (this.random.nextDouble() <= 0.6D)
/* 153 */       addNearlyMaxed(boots, "Valor"); 
/* 154 */     if (this.random.nextDouble() <= 0.6D) {
/* 155 */       addNearlyMaxed(boots, "Angelic");
/*     */     }
/* 157 */     if (this.random.nextDouble() <= 0.2D) {
/* 158 */       addNearlyMaxed(boots, "Paladin Armored");
/*     */     }
/* 160 */     if (this.random.nextDouble() <= 0.225D) {
/* 161 */       addNearlyMaxed(boots, "Ethereal Dodge");
/*     */     }
/* 163 */     if (this.random.nextDouble() <= 0.125D) {
/* 164 */       addNearlyMaxed(boots, "Guided Rocket Escape");
/*     */     }
/* 166 */     if (mastery) {
/* 167 */       addRandomMasteryEnchants(boots, 2);
/*     */     }
/* 169 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(boots) : boots;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack addRandomMasteryEnchants(ItemStack item, int count) {
/* 174 */     List<String> enchants = Lists.newArrayList(new String[] { "Chain Lifesteal", "Death Pact", "Tombstone", "Permafrost" });
/*     */     
/* 176 */     for (int i = 0; i < count && 
/* 177 */       !enchants.isEmpty(); i++) {
/* 178 */       String random = enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));
/*     */       
/* 180 */       if (random != null) {
/* 181 */         enchants.remove(random);
/*     */       }
/* 183 */       addNearlyMaxed(item, random);
/*     */     } 
/*     */     
/* 186 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 191 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 201 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\abilityarmorsets\DeathKnightAbilitySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */