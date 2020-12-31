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
/*     */ public class GhostAbilitySet
/*     */   extends AbilityArmorSetInfo
/*     */ {
/*  23 */   private String color = CC.DarkAqua;
/*  24 */   private String bulletPoint = this.color + CC.Bold + " * " + this.color;
/*  25 */   private Color armorColor = Color.GRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getAbilityArmorLore() {
/*  31 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getHelmet(Player player, boolean applyTransmog, boolean mastery) {
/*  37 */     ItemStack helmet = setLeatherColor(
/*  38 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.GHOST.applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_HELMET, CC.DarkAquaB + "Ghostly Hood", new String[] {
/*     */                   
/*     */                   CC.GrayI + "A tattered hood haunted by", CC.GrayI + "the echoes of all lost souls."
/*  41 */                 })).build())).build(), HeroicArmorType.HELMET, true), this.armorColor);
/*     */ 
/*     */     
/*  44 */     addNearlyMaxedEnchants(helmet, new String[] { "Armored", "Implants", "Drunk", "Arrow Deflect", "Death God", "Resilience", "Protection", "Frozen" });
/*     */ 
/*     */     
/*  47 */     if (this.random.nextDouble() <= 0.6D)
/*  48 */       addNearlyMaxed(helmet, "Valor"); 
/*  49 */     if (this.random.nextDouble() <= 0.6D)
/*  50 */       addNearlyMaxed(helmet, "Angelic"); 
/*  51 */     if (this.random.nextDouble() <= 0.6D) {
/*  52 */       addNearlyMaxed(helmet, "Tank");
/*     */     }
/*     */     
/*  55 */     if (this.random.nextDouble() <= 0.175D) {
/*  56 */       addNearlyMaxedEnchants(helmet, new String[] { "Paladin Armored" });
/*     */     }
/*  58 */     if (this.random.nextDouble() <= 0.175D) {
/*  59 */       addNearlyMaxedEnchants(helmet, new String[] { "Alien Implants" });
/*     */     }
/*  61 */     if (mastery) {
/*  62 */       addRandomMasteryEnchants(helmet, 2);
/*     */     }
/*  64 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(helmet) : helmet;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(Player player, boolean applyTransmog, boolean mastery) {
/*  69 */     ItemStack chestplate = setLeatherColor(
/*  70 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.GHOST
/*  71 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_CHESTPLATE, CC.DarkAquaB + "Ghostly Shroud", new String[] {
/*     */                   
/*     */                   CC.GrayI + "A thin, sheer shroud that seems", CC.GrayI + "to defy gravity in its movements."
/*  74 */                 })).build())).build(), HeroicArmorType.CHESTPLATE, true), this.armorColor);
/*     */ 
/*     */     
/*  77 */     addNearlyMaxedEnchants(chestplate, new String[] { "Armored", "Overload", "Deathbringer", "Enlighted", "Diminish", "Aegis", "Leadership", "Blood Lust", "Shockwave" });
/*     */ 
/*     */ 
/*     */     
/*  81 */     if (this.random.nextDouble() <= 0.6D)
/*  82 */       addNearlyMaxed(chestplate, "Valor"); 
/*  83 */     if (this.random.nextDouble() <= 0.6D)
/*  84 */       addNearlyMaxed(chestplate, "Angelic"); 
/*  85 */     if (this.random.nextDouble() <= 0.6D) {
/*  86 */       addNearlyMaxed(chestplate, "Tank");
/*     */     }
/*     */     
/*  89 */     if (this.random.nextDouble() <= 0.2D) {
/*  90 */       addNearlyMaxed(chestplate, "Paladin Armored");
/*     */     }
/*  92 */     if (this.random.nextDouble() <= 0.175D) {
/*  93 */       addNearlyMaxed(chestplate, "Godly Overload");
/*     */     }
/*  95 */     if (this.random.nextDouble() <= 0.2D) {
/*  96 */       addNearlyMaxed(chestplate, "Planetary Deathbringer");
/*     */     }
/*  98 */     if (this.random.nextDouble() <= 0.2D) {
/*  99 */       addNearlyMaxed(chestplate, "Divine Enlighted");
/*     */     }
/* 101 */     if (this.random.nextDouble() <= 0.15D) {
/* 102 */       addNearlyMaxed(chestplate, "Vengeful Diminish");
/*     */     }
/* 104 */     if (mastery) {
/* 105 */       addRandomMasteryEnchants(chestplate, 2);
/*     */     }
/* 107 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(chestplate) : chestplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(Player player, boolean applyTransmog, boolean mastery) {
/* 112 */     ItemStack leggings = setLeatherColor(
/* 113 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.GHOST
/* 114 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_LEGGINGS, CC.DarkAquaB + "Ghostly Robes", new String[] {
/*     */                   
/*     */                   CC.GrayI + "A darkly woven silk robe imbued", CC.GrayI + "with onyx gemstones that", CC.GrayI + "emanate evil power."
/*     */                 
/* 118 */                 })).build())).build(), HeroicArmorType.LEGGINGS, true), this.armorColor);
/*     */ 
/*     */     
/* 121 */     addNearlyMaxedEnchants(leggings, new String[] { "Armored", "Nature Wrath", "Clarity", "Obsidianshield", "Cactus", "Lucky", "Voodoo" });
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (this.random.nextDouble() <= 0.6D)
/* 126 */       addNearlyMaxed(leggings, "Valor"); 
/* 127 */     if (this.random.nextDouble() <= 0.6D)
/* 128 */       addNearlyMaxed(leggings, "Angelic"); 
/* 129 */     if (this.random.nextDouble() <= 0.6D) {
/* 130 */       addNearlyMaxed(leggings, "Tank");
/*     */     }
/*     */     
/* 133 */     if (this.random.nextDouble() <= 0.15D) {
/* 134 */       addNearlyMaxed(leggings, "Paladin Armored");
/*     */     }
/* 136 */     if (this.random.nextDouble() <= 0.125D) {
/* 137 */       addNearlyMaxed(leggings, "Infinite Luck");
/*     */     }
/* 139 */     if (mastery)
/* 140 */       addRandomMasteryEnchants(leggings, 2); 
/* 141 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(leggings) : leggings;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(Player player, boolean applyTransmog, boolean mastery) {
/* 146 */     ItemStack boots = setLeatherColor(
/* 147 */         CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.GHOST
/* 148 */           .applyAbilityNBT(new NBTWrapper((new ItemBuilder(Material.DIAMOND_BOOTS, CC.DarkAquaB + "Ghostly Whisp", new String[] { CC.GrayI + "Ghost don't have feet."
/*     */                 
/* 150 */                 })).build())).build(), HeroicArmorType.BOOTS, true), this.armorColor);
/*     */ 
/*     */     
/* 153 */     addNearlyMaxedEnchants(boots, new String[] { "Armored", "Metaphysical", "Ender Walker", "Springs", "Rocket Escape", "Dodge", "Phoenix", "Undead Ruse" });
/*     */ 
/*     */ 
/*     */     
/* 157 */     addMaxedEnchant(boots, "Gears");
/*     */     
/* 159 */     if (this.random.nextDouble() <= 0.6D)
/* 160 */       addNearlyMaxed(boots, "Valor"); 
/* 161 */     if (this.random.nextDouble() <= 0.6D)
/* 162 */       addNearlyMaxed(boots, "Angelic"); 
/* 163 */     if (this.random.nextDouble() <= 0.6D) {
/* 164 */       addNearlyMaxed(boots, "Tank");
/*     */     }
/* 166 */     if (this.random.nextDouble() <= 0.25D) {
/* 167 */       addNearlyMaxed(boots, "Paladin Armored");
/*     */     }
/* 169 */     if (this.random.nextDouble() <= 0.3D) {
/* 170 */       addNearlyMaxed(boots, "Ethereal Dodge");
/*     */     }
/* 172 */     if (this.random.nextDouble() <= 0.45D) {
/* 173 */       addNearlyMaxed(boots, "Phoenix");
/*     */     }
/* 175 */     if (mastery) {
/* 176 */       addRandomMasteryEnchants(boots, 2);
/*     */     }
/* 178 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(boots) : boots;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack addRandomMasteryEnchants(ItemStack item, int count) {
/* 183 */     List<String> enchants = Lists.newArrayList(new String[] { "Horrify", "Feign Death", "Poltergeist", "Mark of the Beast" });
/*     */     
/* 185 */     for (int i = 0; i < count && 
/* 186 */       !enchants.isEmpty(); i++) {
/* 187 */       String random = enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));
/*     */       
/* 189 */       if (random != null) {
/* 190 */         enchants.remove(random);
/*     */       }
/* 192 */       addNearlyMaxed(item, random);
/*     */     } 
/*     */     
/* 195 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 200 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 210 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\abilityarmorsets\GhostAbilitySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */