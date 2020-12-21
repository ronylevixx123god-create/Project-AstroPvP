/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface HorseInventory
extends Inventory {
    public ItemStack getSaddle();

    public ItemStack getArmor();

    public void setSaddle(ItemStack var1);

    public void setArmor(ItemStack var1);
}

