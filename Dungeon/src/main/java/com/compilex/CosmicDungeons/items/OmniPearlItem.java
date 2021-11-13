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

public class OmniPearlItem
extends CosmicItem {
    public OmniPearlItem() {
        super("omni_pearl");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.ENDER_PEARL, CC.translate((String)"&3&lOmni Pearl"), new String[]{CC.translate((String)"&7An exclusive teleportation pearl"), CC.translate((String)"&7which combines the following pearl"), CC.translate((String)"&7abilities in to one:"), CC.translate((String)" &3&l* &3Soul Pearl"), CC.translate((String)" &3&l* &3Escapist Pearl"), CC.translate((String)" &3&l* &3Super Pearl")}).build())).build();
    }
}

