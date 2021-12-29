package net.astropvp.struct;

import org.bukkit.Material;

public enum CollectType {
    DEATH_INVENTORY(Material.ENDER_CHEST),
    SLOTBOT(Material.PAPER),
    CRTAEKEY(Material.TRIPWIRE_HOOK),
    ITEMFLIP(Material.ENCHANTMENT_TABLE),
    GIVEALL(Material.GOLD_NUGGET);

    Material material;

    CollectType(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }
}
