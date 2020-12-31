/*    */ package com.compilex.CosmicArmorSets.menu;
/*    */ 
/*    */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*    */ import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
/*    */ import com.compilex.CosmicKOTH.utils.KothItemUtils;
/*    */ import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
/*    */ import com.cosmicpvp.cosmicutils.gui.InventoryItem;
/*    */ import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArmorSetHelpMenu
/*    */   extends CosmicGUI
/*    */ {
/*    */   public ArmorSetHelpMenu(Player player) {
/* 21 */     super(player, "Armor Set Help", 54);
/*    */   }
/*    */ 
/*    */   
/*    */   public void redraw() {
/* 26 */     int slotIndex = 10;
/* 27 */     for (ArmorSet armorSet : ArmorSet.values()) {
/* 28 */       int index = 0;
/*    */ 
/*    */       
/* 31 */       if (armorSet.getArmorSetInfo() instanceof WeaponedArmorSet) {
/* 32 */         setItem(slotIndex + index++ * 9, new InventoryItem(((WeaponedArmorSet)armorSet.getArmorSetInfo()).getWeapon(true)));
/* 33 */       } else if (armorSet.getArmorSetInfo() instanceof com.compilex.CosmicArmorSets.enums.armorsets.KothArmorSet) {
/*    */         
/* 35 */         setItem(8 + index * 9, new InventoryItem(KothItemUtils.getKothAxe(this.player.getName())));
/* 36 */         setItem(slotIndex + index++ * 9, new InventoryItem(KothItemUtils.getKothSwordPvP(this.player.getName())));
/* 37 */       } else if (armorSet.getArmorSetInfo() instanceof com.compilex.CosmicArmorSets.enums.armorsets.DragonSlayerArmorSet) {
/* 38 */         ItemStack item = (new MaskBuilder("DRAGON_MASK")).build();
/* 39 */         if (item != null)
/* 40 */           setItem(slotIndex + index++ * 9, new InventoryItem(item)); 
/*    */       } else {
/* 42 */         index++;
/*    */       } 
/* 44 */       for (ItemStack armorSetItem : armorSet.armorSetItems) {
/* 45 */         setItem(slotIndex + index++ * 9, new InventoryItem(armorSetItem));
/*    */       }
/* 47 */       slotIndex++;
/*    */     } 
/*    */     
/* 50 */     setItem(0, new InventoryItem((new ItemBuilder(Material.BOOK, CC.LightPurpleB + "Armor Sets", new String[] { "", CC.Gray + "Armor Sets are custom sets of armor", CC.Gray + "that have special 'Set Bonuses'.", CC.Gray + "These bonuses are only active if", CC.Gray + "you have ALL pieces of a specific", CC.Gray + "set equipped.", "", CC.Gray + "Sample versions of all available", CC.Gray + "armor sets are currently displayed."
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 59 */             })).build()));
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\menu\ArmorSetHelpMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */