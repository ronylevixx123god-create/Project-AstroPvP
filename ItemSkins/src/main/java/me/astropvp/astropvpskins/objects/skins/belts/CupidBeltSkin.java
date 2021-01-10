/*    */ package me.astropvp.astropvpskins.objects.skins.belts;
/*    */ 
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import me.astropvp.astropvpskins.objects.ItemSkin;
/*    */ import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CupidBeltSkin
/*    */   extends ItemSkin
/*    */ {
/*    */   public CupidBeltSkin() {
/* 17 */     super(SkinType.CUPID_BELT);
/*    */   }
/*    */   
/*    */   public void onEquip(Player player) {
/* 21 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
/* 22 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
/*    */   }
/*    */   
/*    */   public void onUpdate(Player player) {
/* 26 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
/* 27 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
/*    */   }
/*    */ }


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\objects\skins\belts\CupidBeltSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */