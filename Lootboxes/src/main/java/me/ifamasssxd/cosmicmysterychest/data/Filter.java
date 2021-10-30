package me.ifamasssxd.cosmicmysterychest.data;

import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Filter {



    public MysteryChest mysteryChest;
    private final ArrayList<ItemStack> blockedLoot;

    public Filter(MysteryChest mysteryChest, ArrayList<ItemStack> blockedLoot) {
        this.mysteryChest = mysteryChest;
        this.blockedLoot = blockedLoot;
    }

    public MysteryChest getMysteryChest() {
        return mysteryChest;
    }

    public ArrayList<ItemStack> getBlockedLoot() {
        return this.blockedLoot;
    }

    public void addBlockedLootItem(ItemStack itemStack) {
        this.blockedLoot.add(itemStack);
    }

    public void removeBlockedLootItem(ItemStack itemStack) {
        this.blockedLoot.remove(itemStack);
    }

    public boolean isBlockedItem(ItemStack itemStack) {
        return this.blockedLoot.contains(itemStack);
    }

}
