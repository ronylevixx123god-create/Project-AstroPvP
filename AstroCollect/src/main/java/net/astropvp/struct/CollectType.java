package net.astropvp.struct;

import org.bukkit.Material;

public enum CollectType {
    DEATH_INVENTORY(Material.DIAMOND_SWORD),
    GIVEALL(Material.GOLD_NUGGET);

    Material material;

    CollectType(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }
}
