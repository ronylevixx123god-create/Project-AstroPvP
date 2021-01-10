/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ASound
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.astropvp.astropvpskins.objects.skins.helmets;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HazmatHelmetSkin
extends ItemSkin {
    public HazmatHelmetSkin() {
        super(SkinType.VIRUS_HELMET_HAZMAT);
    }

    @Override
    public void onUpdate(Player player) {
        if (player.getWalkSpeed() <= 0.1f || player.hasPotionEffect(PotionEffectType.SLOW) && player.getActivePotionEffects().stream().filter(x -> x.getType() == PotionEffectType.SLOW).findFirst().orElse(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 0, 0)).getAmplifier() > 20) {
            player.sendMessage(CC.translate((String)"&2&L* PRISON BREAK &7(Hazmat Helmet) &2&L*"));
            ASound.ANVIL_LAND.play(player, 2.0f, 1.4f);
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.JUMP);
            PlayerPotionEffects.applyPotionEffects((Player)player, (boolean)false);
            player.setWalkSpeed(0.2f);
        }
    }

    @Override
    public void onEquip(Player player) {
        if (player.getWalkSpeed() <= 0.1f || player.hasPotionEffect(PotionEffectType.SLOW) && player.getActivePotionEffects().stream().filter(x -> x.getType() == PotionEffectType.SLOW).findFirst().orElse(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 0, 0)).getAmplifier() > 20) {
            player.sendMessage(CC.translate((String)"&2&L* PRISON BREAK &7(Hazmat Helmet) &2&L*"));
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.JUMP);
            PlayerPotionEffects.applyPotionEffects((Player)player, (boolean)false);
            ASound.ANVIL_LAND.play(player, 2.0f, 1.4f);
            player.setWalkSpeed(0.2f);
        }
    }
}

