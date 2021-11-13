/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.decimatemc.decimatecommons.util.CommonsUtil
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  net.minecraft.server.v1_8_R3.NBTBase
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.items;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.decimatemc.decimatecommons.util.CommonsUtil;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FatBucketItem
extends CosmicItem {
    public FatBucketItem() {
        super("fat_bucket");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        NBTWrapper wrapper = this.applyItemData(CommonsUtil.createItem((Material)Material.BUCKET, (String)"&c&lFat Bucket &7(&f0% full&7)", (String[])new String[]{"&7An extremely large bucket that", "&7can hold up to 256 lava sources."}));
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        cosmicData.setInt("percentFull", Integer.valueOf(0));
        cosmicData.setInt("uses", Integer.valueOf(0));
        cosmicData.setString("mode", "fill");
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }
}

