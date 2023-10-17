// Decompiled with: CFR 0.151
// Class Version: 8
package net.astropvp.loottable.struct;

import com.cosmicpvp.cosmicutils.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;


@AllArgsConstructor
public enum LootTable {
    GRINDER(Material.TRIPWIRE_HOOK, ChatColor.GOLD),
    GODLY(Material.TRIPWIRE_HOOK, ChatColor.YELLOW),
    OP(Material.TRIPWIRE_HOOK, ChatColor.DARK_RED),
    MUTATED(Material.EMERALD, ChatColor.DARK_GREEN),

    SLOTS(Material.PAPER, ChatColor.YELLOW),
    ENVOY(Material.FIREWORK, ChatColor.AQUA),
    GRINDING(Material.MOB_SPAWNER, ChatColor.YELLOW),
    RUINS(Material.PRISMARINE_SHARD, ChatColor.DARK_AQUA),

    ;

    private final Material material;
    private final ChatColor color;

    public String getFancy() {
        return StringUtils.getProperCase(this.name()).replace("_", " ");
    }


    public Material getMaterial() {
        return this.material;
    }

    public ChatColor getColor() {
        return this.color;
    }
}
