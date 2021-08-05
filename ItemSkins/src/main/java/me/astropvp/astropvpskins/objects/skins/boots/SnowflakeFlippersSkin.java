package me.astropvp.astropvpskins.objects.skins.boots;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SnowflakeFlippersSkin extends ItemSkin {
    public SnowflakeFlippersSkin() {
        super(SkinType.CHRISTMAS_BOOTS_SLIPPERS);
    }

    public void onEquip(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA ||
                outpostTier == OutpostTier.HERO) {
            return;
        }
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    public void onUpdate(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA ||
                outpostTier == OutpostTier.HERO) {
            onUnequip(player);
            return;
        }
    }

    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }
}