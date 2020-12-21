/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.inventory.ItemStack;

final class StoredIIDItem {
    private ItemStack item;
    private int slot;

    public ItemStack getItem() {
        return this.item;
    }

    public int getSlot() {
        return this.slot;
    }

    public StoredIIDItem(ItemStack item, int slot) {
        this.item = item;
        this.slot = slot;
    }
}

