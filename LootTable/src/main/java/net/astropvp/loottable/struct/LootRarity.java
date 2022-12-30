package net.astropvp.loottable.struct;

import com.cosmicpvp.cosmicutils.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public enum LootRarity {
    COMMON(30, DyeColor.GRAY, ChatColor.GRAY),
    UNCOMMON(25, DyeColor.GREEN, ChatColor.GREEN),
    RARE(12,DyeColor.LIGHT_BLUE, ChatColor.AQUA),
    GODLY(4,DyeColor.RED, ChatColor.DARK_RED),
    SUPERIOR(0.7,DyeColor.YELLOW, ChatColor.YELLOW),
    ;
    private final double chance;

    public String getFancy() {
        return StringUtils.getProperCase(this.name()).replace("_", " ");
    }
    private final DyeColor dyeColor;
    private final ChatColor color;

    public static  List<LootRarity> getReversed() {
        List<LootRarity> reversed =  Arrays.asList(LootRarity.values().clone());
        Collections.reverse(reversed);
        return reversed;
    }

}
