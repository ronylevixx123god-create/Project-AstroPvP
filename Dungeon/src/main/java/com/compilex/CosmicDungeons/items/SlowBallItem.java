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

public class SlowBallItem
extends CosmicItem {
    public SlowBallItem() {
        super("slow_ball");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.SNOW_BALL, CC.translate((String)"&3&lSlow&f&lball"), new String[]{CC.Gray + "A special snowball that", CC.Gray + "when thrown, will slow down any", CC.Gray + "player in it's way."}).build())).build();
    }
}

