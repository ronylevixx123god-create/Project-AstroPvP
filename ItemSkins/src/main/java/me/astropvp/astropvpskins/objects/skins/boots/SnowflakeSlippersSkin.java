package me.astropvp.astropvpskins.objects.skins.boots;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SnowflakeSlippersSkin extends ItemSkin {

    public void onEquip(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA ||
                outpostTier == OutpostTier.HERO) {
            return;
        }
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    public void onUpdate(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA ||
                outpostTier == OutpostTier.HERO) {
            onUnequip(player);
            return;
        }

        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    //TODO: UPDATE





    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }
}