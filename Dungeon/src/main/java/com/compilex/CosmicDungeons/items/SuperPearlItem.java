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

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SuperPearlItem
extends CosmicItem {
    public SuperPearlItem() {
        super("super_pearl");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(new ItemBuilder(Material.ENDER_PEARL, CC.translate((String)"&6&lSuper Pearl"), new String[]{CC.Gray + "This mythical enderpearl will travel up to", CC.Gray + "2.5x further than a normal enderpearl."}).build()).build();
    }
}

