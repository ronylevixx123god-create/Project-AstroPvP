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
/*     */ public class ArchitectAbilitySet
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
/*  39 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.ARCHITECT.applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_HELMET, this.color + CC.Bold + "Crown of the Lich King", new String[0]))
/*     */               
/*  41 */               .build())).build(), HeroicArmorType.HELMET, true), this.armorColor);
/*     */     
/*  43 */     addNearlyMaxedEnchants(helmet, new String[] { "Armored", "Implants", "Drunk", "Arrow Deflect", "Death God", "Protection", "Ender Shift", "Aquatic", "Repair Guard" });
/*     */ 
/*     */     
/*  46 */     if (this.random.nextDouble() <= 0.6D)
/*  47 */       addNearlyMaxed(helmet, "Valor"); 
/*  48 */     if (this.random.nextDouble() <= 0.6D)
/*  49 */       addNearlyMaxed(helmet, "Angelic"); 
/*  50 */     if (this.random.nextDouble() <= 0.6D) {
/*  51 */       addNearlyMaxed(helmet, "Tank");
/*     */     }
/*     */     
/*  54 */     if (this.random.nextDouble() <= 0.2D) {
/*  55 */       addNearlyMaxedEnchants(helmet, new String[] { "Paladin Armored" });
/*     */     }
/*  57 */     if (this.random.nextDouble() <= 0.175D) {
/*  58 */       addNearlyMaxedEnchants(helmet, new String[] { "Alien Implants" });
/*     */     }
/*  60 */     if (this.random.nextDouble() <= 0.35D) {
/*  61 */       addNearlyMaxed(helmet, "Immortal");
/*     */     }
/*     */     
/*  64 */     if (mastery) {
/*  65 */       addRandomMasteryEnchants(helmet, 2);
/*     */     }
/*  67 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(helmet) : helmet;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(Player player, boolean applyTransmog, boolean mastery) {
/*  72 */     ItemStack chestplate = setLeatherColor(
/*  73 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.ARCHITECT
/*  74 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_CHESTPLATE, this.color + CC.Bold + "Death Knight Platebody", new String[0]))
/*  75 */               .build())).build(), HeroicArmorType.CHESTPLATE, true), this.armorColor);
/*     */ 
/*     */     
/*  78 */     addNearlyMaxedEnchants(chestplate, new String[] { "Armored", "Overload", "Deathbringer", "Enlighted", "Diminish", "Aegis", "Leadership", "Blood Lust", "Blood Link" });
/*     */ 
/*     */     
/*  81 */     if (this.random.nextDouble() <= 0.6D)
/*  82 */       addNearlyMaxed(chestplate, "Valor"); 
/*  83 */     if (this.random.nextDouble() <= 0.6D)
/*  84 */       addNearlyMaxed(chestplate, "Angelic"); 
/*  85 */     if (this.random.nextDouble() <= 0.6D) {
/*  86 */       addNearlyMaxed(chestplate, "Tank");
/*     */     }
/*  88 */     if (this.random.nextDouble() <= 0.2D) {
/*  89 */       addNearlyMaxed(chestplate, "Paladin Armored");
/*     */     }
/*  91 */     if (this.random.nextDouble() <= 0.175D) {
/*  92 */       addNearlyMaxed(chestplate, "Godly Overload");
/*     */     }
/*  94 */     if (this.random.nextDouble() <= 0.225D) {
/*  95 */       addNearlyMaxed(chestplate, "Planetary Deathbringer");
/*     */     }
/*  97 */     if (this.random.nextDouble() <= 0.225D) {
/*  98 */       addNearlyMaxed(chestplate, "Divine Enlighted");
/*     */     }
/* 100 */     if (this.random.nextDouble() <= 0.15D) {
/* 101 */       addNearlyMaxed(chestplate, "Vengeful Diminish");
/*     */     }
/* 103 */     if (mastery) {
/* 104 */       addRandomMasteryEnchants(chestplate, 2);
/*     */     }
/* 106 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(chestplate) : chestplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(Player player, boolean applyTransmog, boolean mastery) {
/* 111 */     ItemStack leggings = setLeatherColor(
/* 112 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.ARCHITECT
/* 113 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_LEGGINGS, this.color + CC.Bold + "Death Knight Leggings", new String[0]))
/*     */               
/* 115 */               .build())).build(), HeroicArmorType.LEGGINGS, true), this.armorColor);
/*     */ 
/*     */     
/* 118 */     addNearlyMaxedEnchants(leggings, new String[] { "Armored", "Nature Wrath", "Dodge", "Obsidianshield", "Cactus", "Lucky", "Voodoo", "Spirits" });
/*     */ 
/*     */     
/* 121 */     if (this.random.nextDouble() <= 0.6D)
/* 122 */       addNearlyMaxed(leggings, "Valor"); 
/* 123 */     if (this.random.nextDouble() <= 0.6D)
/* 124 */       addNearlyMaxed(leggings, "Angelic"); 
/* 125 */     if (this.random.nextDouble() <= 0.6D) {
/* 126 */       addNearlyMaxed(leggings, "Tank");
/*     */     }
/* 128 */     if (this.random.nextDouble() <= 0.2D) {
/* 129 */       addNearlyMaxed(leggings, "Paladin Armored");
/*     */     }
/* 131 */     if (this.random.nextDouble() <= 0.3D) {
/* 132 */       addNearlyMaxed(leggings, "Immortal");
/*     */     }
/* 134 */     if (mastery)
/* 135 */       addRandomMasteryEnchants(leggings, 2); 
/* 136 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(leggings) : leggings;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(Player player, boolean applyTransmog, boolean mastery) {
/* 141 */     ItemStack boots = setLeatherColor(
/* 142 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.ARCHITECT
/* 143 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_BOOTS, this.color + CC.Bold + "Boots of the Scourge", new String[0]))
/*     */               
/* 145 */               .build())).build(), HeroicArmorType.BOOTS, true), this.armorColor);
/*     */ 
/*     */     
/* 148 */     addNearlyMaxedEnchants(boots, new String[] { "Armored", "Metaphysical", "Ender Walker", "Springs", "Rocket Escape", "Phoenix", "Guardians", "Clarity" });
/*     */ 
/*     */     
/* 151 */     addMaxedEnchant(boots, "Gears");
/*     */     
/* 153 */     if (this.random.nextDouble() <= 0.6D)
/* 154 */       addNearlyMaxed(boots, "Valor"); 
/* 155 */     if (this.random.nextDouble() <= 0.6D) {
/* 156 */       addNearlyMaxed(boots, "Angelic");
/*     */     }
/* 158 */     if (this.random.nextDouble() <= 0.2D) {
/* 159 */       addNearlyMaxed(boots, "Paladin Armored");
/*     */     }
/* 161 */     if (this.random.nextDouble() <= 0.225D) {
/* 162 */       addNearlyMaxed(boots, "Ethereal Dodge");
/*     */     }
/* 164 */     if (this.random.nextDouble() <= 0.125D) {
/* 165 */       addNearlyMaxed(boots, "Guided Rocket Escape");
/*     */     }
/* 167 */     if (mastery) {
/* 168 */       addRandomMasteryEnchants(boots, 2);
/*     */     }
/* 170 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(boots) : boots;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack addRandomMasteryEnchants(ItemStack item, int count) {
/* 175 */     List<String> enchants = Lists.newArrayList(new String[] { "Chain Lifesteal", "Death Pact", "Tombstone", "Permafrost" });
/*     */     
/* 177 */     for (int i = 0; i < count && 
/* 178 */       !enchants.isEmpty(); i++) {
/* 179 */       String random = enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));
/*     */       
/* 181 */       if (random != null) {
/* 182 */         enchants.remove(random);
/*     */       }
/* 184 */       addNearlyMaxed(item, random);
/*     */     } 
/*     */     
/* 187 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 192 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 202 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\abilityarmorsets\ArchitectAbilitySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */