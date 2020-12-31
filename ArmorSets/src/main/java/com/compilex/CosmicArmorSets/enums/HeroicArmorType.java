/*    */ package com.compilex.CosmicArmorSets.enums;

import org.bukkit.Material;

/*    */ public enum HeroicArmorType {
/*    */   
/* 10 */   HELMET(Material.LEATHER_HELMET, "Helmet", 3, 810),
/* 11 */   CHESTPLATE(Material.LEATHER_CHESTPLATE, "Chestplate", 8, 1000),
/* 12 */   LEGGINGS(Material.LEATHER_LEGGINGS, "Leggings", 6, 935),
/* 13 */   BOOTS(Material.LEATHER_BOOTS, "Boots", 3, 686); private Material material; private String name; private int armorModifier; private int durability; public Material getMaterial() {
/* 14 */     return this.material; }
			HeroicArmorType(Material material, String name, int armorModifier, int durability) {
		/*    */     this.material = material;
		/*    */     this.name = name;
		/*    */     this.armorModifier = armorModifier;
		/*    */     this.durability = durability;
		/*    */   }
/* 15 */   public String getName() { return this.name; }
/* 16 */   public int getArmorModifier() { return this.armorModifier; } public int getDurability() { return this.durability; }
/*    */   
/*    */   public static HeroicArmorType getFromName(String name) {
/* 19 */     for (HeroicArmorType type : values()) {
/* 20 */       if (type.name().equalsIgnoreCase(name)) return type; 
/*    */     } 
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   public static HeroicArmorType getFromMaterial(Material material) {
/* 26 */     String materialName = material.name();
/* 27 */     for (HeroicArmorType type : values()) {
/* 28 */       if (materialName.endsWith(type.name())) return type; 
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\HeroicArmorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */