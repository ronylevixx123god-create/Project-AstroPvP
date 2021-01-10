/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.enchantments.libs.AbstractPotionEffect
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.arkhamnetwork.enchantments.mastery.MasteryMortalCoil
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicmasks.struct;

import org.arkhamnetwork.enchantments.libs.AbstractPotionEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.mastery.MasteryMortalCoil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class SantaMask
extends CosmicMask {
    @Override
    public void onEquip(Player player) {
        if (!this.modifyStoredEffect(player, PotionEffectType.HEALTH_BOOST, false)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 200, 0), true);
        }
    }

    @Override
    public void onUnequip(Player player) {
        if (this.modifyStoredEffect(player, PotionEffectType.HEALTH_BOOST, true)) {
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            Bukkit.getLogger().info("[SantaMask] Unequipping health boost from santa mask for " + player.getName());
        }
    }

    public int getMaxActivePotionLevel(Player player) {
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player);
        int maxLevel = -1;
        if (effects != null) {
            for (Collection<AbstractPotionEffect> e : effects.getEffects().values()) {
                for (AbstractPotionEffect eff : e) {
                    if (eff.getEffectType().getId() != PotionEffectType.HEALTH_BOOST.getId() || eff.getPotionLevel() <= maxLevel) continue;
                    maxLevel = eff.getPotionLevel();
                    Bukkit.getLogger().info("Removing santa bonus due to health boost being detected: " + (Object)eff.getEffectType());
                }
            }
        }
        return maxLevel;
    }

    @Override
    public void onUpdate(Player player) {
        try {
            if (MasteryMortalCoil.isAffected((Player)player)) {
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (!player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
            int storedMmax = this.getMaxActivePotionLevel(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 200, storedMmax == -1 ? 0 : 0), true);
            return;
        }
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player);
        boolean detectedNew = false;
        if (effects != null) {
            for (Collection<AbstractPotionEffect> e : effects.getEffects().values()) {
                for (AbstractPotionEffect eff : e) {
                    if (eff.getEffectType().getId() != PotionEffectType.HEALTH_BOOST.getId() || eff.getPotionLevel() != eff.getOriginalLevel()) continue;
                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    eff.setPotionLevel(eff.getPotionLevel() + 1);
                    Bukkit.getLogger().info("Removing santa bonus due to health boost being detected: " + (Object)eff.getEffectType());
                    detectedNew = true;
                }
            }
        }
        if (!detectedNew && this.hasEffect(player, PotionEffectType.HEALTH_BOOST, 0, false) || !detectedNew && !player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
            int stored = this.getMaxActivePotionLevel(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 200, stored == -1 ? 0 : 0), true);
        }
    }
}

