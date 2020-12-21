/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest
 *  me.ifamasssxd.cosmicmysterychest.struct.MysteryChest
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootboxItemBuilder
extends ItemBuilder {
    private String lootboxInternal;

    public LootboxItemBuilder(String internal) {
        super(Material.CHEST);
        this.lootboxInternal = internal;
    }

    @Override
    public ItemStack build() {
        if (this.lootboxInternal == null || !CosmicUtils.get().isLootboxesEnabled()) {
            return null;
        }
        MysteryChest chest = (MysteryChest)CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get(this.lootboxInternal);
        if (chest == null) {
            return null;
        }
        ItemStack retr = chest.createClaimableItem(true);
        if (this.amount > 1) {
            retr.setAmount(this.amount);
        }
        return retr;
    }
}

