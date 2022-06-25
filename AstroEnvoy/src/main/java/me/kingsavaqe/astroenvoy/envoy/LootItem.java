package me.kingsavaqe.astroenvoy.envoy;

import org.bukkit.inventory.ItemStack;

public class LootItem {
    private ItemStack item;

    private int chance;

    public LootItem(ItemStack item, int chance) {
        this.item = item;
        this.chance = chance;
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getChance() {
        return this.chance;
    }


}

