/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicCrate.libs;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.api.CCAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class CosmicCrateData {
    protected Random random = ThreadLocalRandom.current();

    public abstract ItemStack getRandomMetaItem(Player var1);

    public abstract List<String> getDescriptionLore(String var1);

    public abstract List<ItemStack> getCrateLoot(Player var1);

    public abstract short getNonColorPane();

    public abstract short getColorPane();

    public abstract List<Color> getConclusionFireworkColors();

    public void playConclusion(Player player) {
        Location l = player.getEyeLocation();
        this.launchFireworks(player);
        ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)50);
        ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)75);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
    }

    public void launchFireworks(Player player) {
        Location l = player.getEyeLocation();
        List<Color> fwColors = this.getConclusionFireworkColors();
        if (fwColors.isEmpty()) {
            Bukkit.getLogger().info("MISSING CONCLUSION FIREWORK COLORS FOR CC: " + player.getName());
            return;
        }
        Color first = fwColors.get(0);
        Color second = fwColors.get(1);
        for (int i = 0; i < 8; ++i) {
            Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), () -> {
                CCAPI.launchFirework(l, first);
                CCAPI.launchFirework(l, second);
            }, (long)(20 * i));
        }
    }

    public ItemStack setAmount(ItemStack item, int amount) {
        return ItemUtils.setAmount((ItemStack)item, (int)amount);
    }
}

