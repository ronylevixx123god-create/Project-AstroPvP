/*    */ package me.astropvp.astropvpskins.enums;
/*    */ 
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public enum ItemSkinType
/*    */ {
/* 11 */   SWORD, AXE, BOW, WEAPON, HELMET, CHESTPLATE, LEGGINGS, BOOTS, AMULET, BELT, PICKAXE;
/*    */   
/*    */   public List<String> getLore() {
/* 14 */     return Arrays.asList("", String.valueOf(CC.GrayI) + "Attach this skin to any " + CC.WhiteU + getModifiedName(),
            /* 15 */           String.valueOf(CC.GrayI) + "to override its visual appeareance.", "",
            /* 16 */           String.valueOf(CC.RedB) + "NOTE: " + CC.Red + "Requires the Cosmic Client ", String.valueOf(CC.Red) + "to render custom item skins.",
            /* 17 */           String.valueOf(CC.Gray) + "* Download at " + CC.Underline + "cosmicclient.com", "",
            /* 18 */           String.valueOf(CC.Gray) + "Drag n' Drop onto item to attach.", String.valueOf(CC.Gray) + "Right-Click item to detach skin.");
/*    */   }
/*    */   
/*    */   public boolean isType(ItemStack item) {
/* 22 */     if (item == null) {
/* 23 */       return false;
/*    */     }
/* 25 */     String name = getModifiedName();
/* 26 */     String otherName = item.getType().name();
/* 27 */     return !(!otherName.endsWith(name) && (
/* 28 */       !name.contentEquals("WEAPON") || (!otherName.endsWith("_AXE") && !otherName.endsWith("_SWORD"))));
/*    */   }
/*    */   
/*    */   public String getModifiedName() {
/* 32 */     return (this == BELT) ? LEGGINGS.name() : ((this == AMULET) ? CHESTPLATE.name() : name());
/*    */   }
/*    */   
/*    */   public static boolean isSimilarItemSkin(ItemSkinType type, ItemSkinType other) {
/* 36 */     return !((other != WEAPON || (type != SWORD && type != AXE)) && 
/* 37 */       type != other);
/*    */   }
/*    */ }


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\enums\ItemSkinType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */