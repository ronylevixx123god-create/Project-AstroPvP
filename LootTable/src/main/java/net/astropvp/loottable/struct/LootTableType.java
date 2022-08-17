package net.astropvp.loottable.struct;

import com.cosmicpvp.cosmicutils.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum LootTableType {
    SPACE(Material.NETHER_STAR, ChatColor.LIGHT_PURPLE),
    ASTRO(Material.TRIPWIRE_HOOK,  ChatColor.GREEN),
    LEGENDARY(Material.TRIPWIRE_HOOK,  ChatColor.GOLD),
    GODLY(Material.TRIPWIRE_HOOK,  ChatColor.DARK_RED),
    SEASON(Material.TRIPWIRE_HOOK,  ChatColor.YELLOW),
    MUTATED(Material.TRIPWIRE_HOOK,  ChatColor.DARK_GREEN),
    ;

    public String getFancy() {
        return StringUtils.getProperCase(this.name()).replace("_", " ");
    }
    private final Material material;
    private final ChatColor color;


}
