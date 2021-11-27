/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.me.astropvp.cratekeys.enums.OutpostTier
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
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.ifamasssxd.cosmicmasks.CosmicMasks;
import org.arkhamnetwork.enchantments.libs.AbstractPotionEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ReindeerMask extends CosmicMask {

    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects(player);
        String regionName = WorldGuardUtils.getRegionName(player.getLocation());
        if (regionName.contains("parkour") || regionName.contains("find_corrupt_room") || regionName.contains("maze") || regionName.equalsIgnoreCase("fireball_alley"))
            return;
        if (effects != null)
            effects.getEffects().forEach((enchantment, effect) -> {
                Iterator<AbstractPotionEffect> iterator = effect.iterator();
                while (iterator.hasNext()) {
                    AbstractPotionEffect pEffect = iterator.next();
                    if (pEffect.getEffectType().getId() == PotionEffectType.SPEED.getId() && pEffect.getPotionLevel() < 3) {
                        Bukkit.getLogger().info("[" + getClass().getSimpleName() + "] Found equipped speed: " + pEffect.getPotionLevel());
                        pEffect.setPotionLevel(3);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, pEffect.getPotionLevel()), true);
                    }
                }
            });
    }

    @Override
    public void onUnequip(Player player) {
        if (hasEffect(player, PotionEffectType.SPEED, 3, false))
            player.removePotionEffect(PotionEffectType.SPEED);
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects(player);
        if (effects != null)
            effects.getEffects().forEach((enchantment, effect) -> {
                Iterator<AbstractPotionEffect> iterator = effect.iterator();
                while (iterator.hasNext()) {
                    AbstractPotionEffect pEffect = iterator.next();
                    if (pEffect.getEffectType().getId() == PotionEffectType.SPEED.getId()) {
                        Bukkit.getLogger().info("[" + getClass().getSimpleName() + "] Found equipped speed: " + pEffect.getPotionLevel());
                        pEffect.resetOriginalLevel();
                    }
                }
            });
    }

    @Override
    public void onUpdate(Player player) {
        if (CosmicMasks.get().isOutposts()) {
            OutpostTier tier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
            if (tier != null && tier != OutpostTier.COSMONAUT && tier != OutpostTier.NONE) {
                if (hasEffect(player, PotionEffectType.SPEED, 3, false))
                    player.removePotionEffect(PotionEffectType.SPEED);
                return;
            }
        }
        boolean detectedNew = false;
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects(player);
        if (effects != null)
            for (Collection<AbstractPotionEffect> e : effects.getEffects().values()) {
                for (AbstractPotionEffect eff : e) {
                    if (eff.getEffectType().getId() == PotionEffectType.SPEED.getId() &&
                            eff.getPotionLevel() == eff.getOriginalLevel() && eff.getPotionLevel() != 3) {
                        //player.removePotionEffect(PotionEffectType.SPEED);
                        //eff.setPotionLevel(3);
                        //detectedNew = true;
                        //Bukkit.getLogger().info("Removing reindeer speed boost being detected: " + eff.getEffectType());
                    }
                }
            }
        String regionName = WorldGuardUtils.getRegionName(player.getLocation());
        if (regionName.contains("parkour") || regionName.contains("find_corrupt_room") || regionName.contains("maze") || regionName.equalsIgnoreCase("fireball_alley"))
            return;
        if ((!detectedNew && hasEffect(player, PotionEffectType.SPEED, 3, false)) || (
                !detectedNew && !player.hasPotionEffect(PotionEffectType.SPEED)))
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 3), true);
    }
}
