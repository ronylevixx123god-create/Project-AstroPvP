/*    */
package me.astropvp.astropvpskins.objects.skins.boots;
/*    */
/*    */

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
/*    */ import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
/*    */ import com.rit.sucy.EnchantmentAPI;
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import me.astropvp.astropvpskins.objects.ItemSkin;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Player;

/*    */
/*    */
/*    */
/*    */
/*    */ public class CorruptBootsSkin
        /*    */ extends ItemSkin
        /*    */ {
    /*    */
    public CorruptBootsSkin() {
        /* 18 */
        super(SkinType.CORRUPT_BOOTS);
        /*    */
    }

    /*    */
    /*    */
    public void onEquip(Player player) {
        /* 22 */
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        /* 23 */
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA ||
                /* 24 */       outpostTier == OutpostTier.HERO) {
            /*    */
            return;
            /*    */
        }
        /* 27 */
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public void onUpdate(Player player) {
        /* 32 */
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        /* 33 */
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA ||
                /* 34 */       outpostTier == OutpostTier.HERO) {
            /* 35 */
            onUnequip(player);
            /*    */
            return;
            /*    */
        }
        /*    */
    }

    public void onUnequip(Player player) {
        /* 41 */
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\objects\skins\boots\CorruptBootsSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */