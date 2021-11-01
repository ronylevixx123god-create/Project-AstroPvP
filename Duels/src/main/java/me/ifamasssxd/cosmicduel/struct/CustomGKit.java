/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  org.bukkit.Material
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.cosmicduel.struct;

import com.google.common.collect.Lists;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicduel.utils.KitUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomGKit {
    List<ItemStack> items = Lists.newArrayList();
    private GlobalKits globalKit;
    private boolean isDefault = false;
    private List<ItemStack> gkitItems;

    public CustomGKit(GlobalKits globalKit) {
        this.globalKit = globalKit;
    }

    public List<ItemStack> getGKitItems() {
        if (this.gkitItems == null) {
            this.gkitItems = KitUtils.getMaxedEnchantsFromGKit(this.globalKit);
            this.gkitItems.add(new ItemStack(Material.GOLDEN_APPLE, 2));
        }
        return this.gkitItems;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public GlobalKits getGlobalKit() {
        return this.globalKit;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}

