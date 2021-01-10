/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.enchantments.libs.AbstractPotionEffect
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.astropvp.astropvpskins.objects;

import java.util.concurrent.atomic.AtomicBoolean;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.libs.AbstractPotionEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemSkin {
    protected SkinType type;

    public ItemSkin(SkinType type) {
        this.type = type;
    }

    public boolean modifyStoredEffect(Player player, PotionEffectType type, boolean subtract) {
        AtomicBoolean retr = new AtomicBoolean();
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player);
        if (effects != null) {
            effects.getEffects().forEach((enchantment, effect) -> {
                for (AbstractPotionEffect pEffect : effect) {
                    if (pEffect.getEffectType().getId() != type.getId()) continue;
                    Bukkit.getLogger().info("[" + this.getClass().getSimpleName() + "] Found equipped health boost: " + pEffect.getPotionLevel());
                    if (subtract) {
                        pEffect.resetOriginalLevel();
                        player.removePotionEffect(type);
                    } else if (pEffect.getPotionLevel() == pEffect.getOriginalLevel()) {
                        pEffect.setPotionLevel(pEffect.getPotionLevel() + 1);
                        player.removePotionEffect(type);
                    }
                    retr.set(true);
                }
            });
        }
        return retr.get();
    }

    public boolean hasEffect(Player player, PotionEffectType type, int level, boolean greater) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().getId() != type.getId() || (greater ? effect.getAmplifier() < level : effect.getAmplifier() != level)) continue;
            return true;
        }
        return false;
    }

    public void onEquip(Player player) {
    }

    public void onUpdate(Player player) {
    }

    public void onUnequip(Player player) {
    }

    public ItemStack getItem() {
        return null;
    }
}

