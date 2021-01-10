/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.utils.WorldGuardUtils
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.enchantments.libs.AbstractPotionEffect
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.astropvp.astropvpskins.objects.skins.boots;

import com.compilex.CosmicDungeons.utils.WorldGuardUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.rit.sucy.EnchantmentAPI;
import java.util.Collection;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.libs.AbstractPotionEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RainbowSlidesSkin
extends ItemSkin {
    public RainbowSlidesSkin() {
        super(SkinType.SPRING_2020_BOOTS);
    }

    @Override
    public void onEquip(Player player) {
        String region;
        EnchantmentAPI.getEnchantment((String)"Gears").applyEquipEffect(player, 4);
        String string = region = !WorldGuardUtils.getRegionName((Location)player.getLocation()).equals("") ? WorldGuardUtils.getRegionName((Location)player.getLocation()) : null;
        if (!(region == null || region.contentEquals("parkour_a") || region.contentEquals("parkour_b") || region.contentEquals("bridge") || region.contentEquals("parkour_tower") || region.contentEquals("parkour_tower_start") || region.equals("yijki_realm"))) {
            return;
        }
        if (!this.modifyStoredEffect(player, PotionEffectType.JUMP, false)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 0), true);
        }
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment((String)"Gears").applyUnequipEffect(player, 4);
        if (this.modifyStoredEffect(player, PotionEffectType.JUMP, true)) {
            player.removePotionEffect(PotionEffectType.JUMP);
            Bukkit.getLogger().info("[Rainbow Slides] Unequipping jump boost from rainbow slides item skin for " + player.getName());
        }
    }

    public int getMaxActivePotionLevel(Player player) {
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects((Player)player);
        int maxLevel = -1;
        if (effects != null) {
            for (Collection e : effects.getEffects().values()) {
                for (AbstractPotionEffect eff : e) {
                    if (eff.getEffectType().getId() != PotionEffectType.JUMP.getId() || eff.getPotionLevel() <= maxLevel) continue;
                    maxLevel = eff.getPotionLevel();
                    Bukkit.getLogger().info("Removing rainbow slides bonus due to jump boost being detected: " + (Object)eff.getEffectType());
                }
            }
        }
        return maxLevel;
    }

    @Override
    public void onUpdate(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            this.onUnequip(player);
            return;
        }
        if (!player.getWorld().getName().contains("dungeon")) {
            return;
        }
        String region = WorldGuardUtils.getRegionName((Location)player.getLocation());
        if (!(region.contentEquals("parkour_a") || region.contentEquals("parkour_b") || region.contentEquals("bridge") || region.contentEquals("parkour_tower") || region.contentEquals("parkour_tower_start") || region.equals("yijki_realm"))) {
            return;
        }
        if (!player.hasPotionEffect(PotionEffectType.JUMP)) {
            int storedMmax = this.getMaxActivePotionLevel(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, storedMmax == -1 ? 0 : 0), true);
            return;
        }
        if (this.hasEffect(player, PotionEffectType.JUMP, 0, false)) {
            int stored = this.getMaxActivePotionLevel(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, stored == -1 ? 0 : 0), true);
        }
    }
}

