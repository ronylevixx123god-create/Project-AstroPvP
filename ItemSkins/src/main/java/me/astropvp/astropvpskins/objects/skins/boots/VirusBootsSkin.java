/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffectType
 */
package me.astropvp.astropvpskins.objects.skins.boots;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class VirusBootsSkin
extends ItemSkin {
    public VirusBootsSkin() {
        super(SkinType.VIRUS_BOOTS);
    }

    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect((Player)player, (PotionEffectType)PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect((Player)player, (PotionEffectType)PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public void onUpdate(Player player) {
        PlayerPotionEffects.untrackPotionEffect((Player)player, (PotionEffectType)PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect((Player)player, (PotionEffectType)PotionEffectType.SLOW_DIGGING);
    }
}

