/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicduel.struct;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.utils.DuelUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomKit {
    private List<ItemStack> kitItems = Lists.newArrayList();
    private Kit kitType;
    private boolean isDefault = false;

    public CustomKit(Kit kitType) {
        this.kitType = kitType;
        kitType.getItems().stream().filter(is -> is != null && (DuelUtils.isArmor(is) || DuelUtils.isWeapon(is))).forEach(is -> this.kitItems.add((ItemStack)is));
    }

    public void addKitItems(List<ItemStack> items) {
        this.kitItems.addAll(items);
    }

    public List<ItemStack> getKitItems() {
        return this.kitItems;
    }

    public void setKitItems(List<ItemStack> kitItems) {
        this.kitItems = kitItems;
    }

    public Kit getKitType() {
        return this.kitType;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}

