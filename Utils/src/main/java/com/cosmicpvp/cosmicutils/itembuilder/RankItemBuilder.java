/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicRankQuests.utils.PlayerRankUtils
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.CosmicRankQuests.utils.PlayerRankUtils;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RankItemBuilder
extends ItemBuilder {
    private int rankTier;

    public RankItemBuilder(int rankTier) {
        super((Material)null, 0);
        this.rankTier = rankTier;
    }

    @Override
    public ItemStack build() {
        String rank = PlayerRankUtils.getRank((int)this.rankTier);
        if (rank == null) {
            Bukkit.getLogger().info("Unable to find proper rank tier: " + this.rankTier + "!");
            return new ItemStack(Material.PAPER, 1);
        }
        return PlayerRankUtils.getRankItem((String)rank);
    }
}

