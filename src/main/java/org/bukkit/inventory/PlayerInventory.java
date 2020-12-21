/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface PlayerInventory
extends Inventory {
    public ItemStack[] getArmorContents();

    public ItemStack getHelmet();

    public ItemStack getChestplate();

    public ItemStack getLeggings();

    public ItemStack getBoots();

    public void setArmorContents(ItemStack[] var1);

    public void setHelmet(ItemStack var1);

    public void setChestplate(ItemStack var1);

    public void setLeggings(ItemStack var1);

    public void setBoots(ItemStack var1);

    public ItemStack getItemInHand();

    public void setItemInHand(ItemStack var1);

    public int getHeldItemSlot();

    public void setHeldItemSlot(int var1);

    @Deprecated
    public int clear(int var1, int var2);

    @Override
    public HumanEntity getHolder();
}

