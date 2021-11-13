/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.items;

import com.compilex.CosmicDungeons.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InfiniteLavaFatBucketItem
extends CosmicItem {
    public InfiniteLavaFatBucketItem() {
        super("infinite_lava_fat_bucket");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.LAVA_BUCKET, CC.translate((String)"&c&lInfinite Lava Fat Bucket"), new String[]{CC.translate((String)"&7An extremely large bucket that"), CC.translate((String)"&7contains infinite lava sources."), CC.translate((String)""), CC.translate((String)"&7Right-Click to use, exchange for money.")}).build())).build();
    }
}

