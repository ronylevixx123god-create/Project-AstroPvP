/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  org.arkhamnetwork.enchantments.libs.AbstractPotionEffect
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicmasks.struct;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import me.ifamasssxd.cosmicmasks.CosmicMasks;
import org.arkhamnetwork.enchantments.libs.AbstractPotionEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class ReindeerMask
extends CosmicMask {
    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player);
        if (effects != null) {
            effects.getEffects().forEach((enchantment, effect) -> {
                for (AbstractPotionEffect pEffect : effect) {
                    if (pEffect.getEffectType().getId() != PotionEffectType.SPEED.getId() || pEffect.getPotionLevel() >= 3) continue;
                    Bukkit.getLogger().info("[" + this.getClass().getSimpleName() + "] Found equipped speed: " + pEffect.getPotionLevel());
                    pEffect.setPotionLevel(3);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, pEffect.getPotionLevel()), true);
                }
            });
        }
    }

    @Override
    public void onUnequip(Player player) {
        PlayerPotionEffects effects;
        if (this.hasEffect(player, PotionEffectType.SPEED, 3, false)) {
            player.removePotionEffect(PotionEffectType.SPEED);
        }
        if ((effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player)) != null) {
            effects.getEffects().forEach((enchantment, effect) -> {
                for (AbstractPotionEffect pEffect : effect) {
                    if (pEffect.getEffectType().getId() != PotionEffectType.SPEED.getId()) continue;
                    Bukkit.getLogger().info("[" + this.getClass().getSimpleName() + "] Found equipped speed: " + pEffect.getPotionLevel());
                    pEffect.resetOriginalLevel();
                }
            });
        }
    }

    @Override
    public void onUpdate(Player player) {
        OutpostTier tier;
        if (CosmicMasks.get().isOutposts() && (tier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player)) != null && tier != OutpostTier.COSMONAUT && tier != OutpostTier.NONE) {
            if (this.hasEffect(player, PotionEffectType.SPEED, 3, false)) {
                player.removePotionEffect(PotionEffectType.SPEED);
            }
            return;
        }
        boolean detectedNew = false;
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player);
        if (effects != null) {
            for (Collection<AbstractPotionEffect> e : effects.getEffects().values()) {
                for (AbstractPotionEffect eff : e) {
                    if (eff.getEffectType().getId() != PotionEffectType.SPEED.getId() || eff.getPotionLevel() != eff.getOriginalLevel() || eff.getPotionLevel() == 3) continue;
                    player.removePotionEffect(PotionEffectType.SPEED);
                    eff.setPotionLevel(3);
                    detectedNew = true;
                    Bukkit.getLogger().info("Removing reindeer speed boost being detected: " + (Object)eff.getEffectType());
                }
            }
        }
        if (!detectedNew && this.hasEffect(player, PotionEffectType.SPEED, 3, false) || !detectedNew && !player.hasPotionEffect(PotionEffectType.SPEED)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 3), true);
        }
    }
}

