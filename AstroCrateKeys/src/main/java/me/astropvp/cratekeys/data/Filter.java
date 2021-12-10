package me.astropvp.cratekeys.data;

import me.astropvp.cratekeys.enums.CrateType;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Filter {
    public CrateType crateType;
    private final ArrayList<ItemStack> blockedLoot;

    public Filter(CrateType type, ArrayList<ItemStack> blockedLoot) {
        this.crateType = type;
        this.blockedLoot = blockedLoot;
    }

    public CrateType getCrateType() {
        return crateType;
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
