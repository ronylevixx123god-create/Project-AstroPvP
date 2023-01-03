package net.astropvp.loottable.struct;

import com.cosmicpvp.cosmicutils.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum LootTable {
    SPACE(Material.NETHER_STAR, ChatColor.LIGHT_PURPLE),
    GRINDER(Material.TRIPWIRE_HOOK,  ChatColor.GOLD),
    GODLY(Material.TRIPWIRE_HOOK,  ChatColor.YELLOW),
    OP(Material.TRIPWIRE_HOOK,  ChatColor.DARK_RED),
    SLOTS(Material.PAPER,  ChatColor.YELLOW),
    ENVOY(Material.FIREWORK,  ChatColor.AQUA),
    ;

    public String getFancy() {
        return StringUtils.getProperCase(this.name()).replace("_", " ");
    }
    private final Material material;
    private final ChatColor color;


}
