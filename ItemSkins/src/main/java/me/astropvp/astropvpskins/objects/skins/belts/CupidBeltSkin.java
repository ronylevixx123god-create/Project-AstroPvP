package me.astropvp.astropvpskins.objects.skins.belts;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class CupidBeltSkin extends ItemSkin {

    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }


    //TODO: ON UPDATE HERE
}


