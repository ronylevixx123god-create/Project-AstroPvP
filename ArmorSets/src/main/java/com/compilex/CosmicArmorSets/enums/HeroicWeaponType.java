/*    */ package com.compilex.CosmicArmorSets.enums;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;

/*    */
/*    */ public enum HeroicWeaponType {
/*    */
/*    */   
/* 12 */   AXE(Material.GOLD_AXE, CC.RedB + "Heroic Axe", 1952, 4.0D, 3.0D),
/* 13 */   SWORD(Material.GOLD_SWORD, CC.RedB + "Heroic Sword", 1952, 4.0D, 4.0D); public Material material; public String name; public int durability; private double attackModifier; private double vanillaBaseDamage;

	HeroicWeaponType(Material material, String name, int durability, double attackModifier, double vanillaBaseDamage) {
		/*    */     this.material = material;
		/*    */     this.name = name;
		/*    */     this.durability = durability;
		/*    */     this.attackModifier = attackModifier;
		/*    */     this.vanillaBaseDamage = vanillaBaseDamage;
		/*    */   }
/*    */   
/*    */   public Material getMaterial() {
/* 16 */     return this.material; }
/* 17 */   public String getName() { return this.name; }
/* 18 */   public int getDurability() { return this.durability; }
/* 19 */   public double getAttackModifier() { return this.attackModifier; } public double getVanillaBaseDamage() {
/* 20 */     return this.vanillaBaseDamage;
/*    */   }
/*    */   
/*    */   public static HeroicWeaponType findFromMaterial(Material material) {
/* 24 */     String materialName = material.name();
/* 25 */     for (HeroicWeaponType type : values()) {
/* 26 */       if (materialName.endsWith(type.name())) return type; 
/*    */     } 
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\HeroicWeaponType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */