/*    */ package me.astropvp.astropvpskins.objects.skins.boots;
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
/*    */ 
/*    */ 
/*    */ public class VirusBootsSkin
/*    */   extends ItemSkin
/*    */ {
/*    */   public VirusBootsSkin() {
/* 19 */     super(SkinType.VIRUS_BOOTS);
/*    */   }
/*    */   
/*    */   public void onEquip(Player player) {
/* 23 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
/* 24 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
/*    */   }
/*    */   
/*    */   public void onUpdate(Player player) {
/* 28 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
/* 29 */     PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
/*    */   }
/*    */ }


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\objects\skins\boots\VirusBootsSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */