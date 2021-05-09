/*    */
package me.astropvp.astropvpskins.objects.skins.belts;
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
/*    */ public class CorruptPowerBeltSkin
        /*    */ extends ItemSkin
        /*    */ {
    /*    */
    public CorruptPowerBeltSkin() {
        /* 18 */
        super(SkinType.CORRUPT_BELT_POWER);
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
        if (player.getLocation().getWorld().getName().equals("world_the_end")) {
            return;
        }
        /* 27 */
        EnchantmentAPI.getEnchantment("Godly Overload").applyEquipEffect(player, 5);
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
            /*    */
            return;
            /*    */
        }
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public void onUnequip(Player player) {
        /* 41 */
        EnchantmentAPI.getEnchantment("Godly Overload").applyUnequipEffect(player, 5);
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\objects\skins\belts\CorruptPowerBeltSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */