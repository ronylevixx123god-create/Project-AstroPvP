/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.utils.ItemUtils
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpawnerItemBuilder
extends ItemBuilder {
    private EntityType type;

    public SpawnerItemBuilder(EntityType type) {
        super((Material)null, 0);
        this.type = type;
    }

    private ItemStack getMobSpawner(EntityType entityType) {
        return ItemUtils.buildItem((Material)Material.MOB_SPAWNER, (short)entityType.getTypeId(), (String)(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + SpawnerItemBuilder.getEntityName(entityType) + ChatColor.GOLD.toString() + " Spawner"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Be careful where you place this!"});
    }

    @Override
    public ItemStack build() {
        if (this.type != null) {
            ItemStack item = this.getMobSpawner(this.type);
            if (this.amount > 0) {
                item.setAmount(this.amount);
            }
            return item;
        }
        return new ItemStack(Material.DIRT);
    }

    public static String getEntityName(EntityType et) {
        if (et == EntityType.IRON_GOLEM) {
            return "Iron Golem";
        }
        if (et == EntityType.MAGMA_CUBE) {
            return "Magma Cube";
        }
        return et.getName();
    }
}

