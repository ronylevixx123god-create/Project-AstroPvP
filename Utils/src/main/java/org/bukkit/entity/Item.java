/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface Item
extends Entity {
    public ItemStack getItemStack();

    public void setItemStack(ItemStack var1);

    public int getPickupDelay();

    public void setPickupDelay(int var1);
}

