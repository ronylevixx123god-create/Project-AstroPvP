package me.astropvp.cratekeys.filter.impl;

import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.filter.Filter;
import org.bukkit.inventory.ItemStack;

public class CrateKeyFilter extends Filter<ItemStack> {
    public CrateKeyFilter(CrateType type) {
        super("crate_key_" + type.name().toLowerCase());
    }
}
