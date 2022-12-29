package net.astropvp.customblocks.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CustomBlockType {

    HALLOWEEN_CANDLES("Halloween Candle", ChatColor.GOLD, 1000000, 1000000,
            Arrays.asList(
                    CC.translate("&7Chance to either not consume a"),
                    CC.translate("&7lootbox or give a guaranteed"),
                    CC.translate("&7item when opening the lootbox"),
                    CC.translate("&7in the chunk this block is placed in."),
                    CC.translate(""),
                    CC.translate("&7By successfully using this fiery"),
                    CC.translate("&7flames emmited from these candles"),
                    CC.translate("&7legend has it that priceless"),
                    CC.translate("&7lootboxes stay with the beholder")
    )),
    THANKSGIVING_PIE( "Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    TREASURE_CHEST( "Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    COSMIC_TROPHY( "Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    LEVIATHAN_TOTEM( "Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    HALLOWEEN_CAULDRON( "Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    COSMIC_TOKEN_GENERATOR("Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    SECURITY_SYSTEM("Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList("")),
    BLAZE_STONE_TOTEM("Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList(""))

    ;

    private final String displayName;
    private final ChatColor color;
    private final double minePrice;
    private final double placePrice;
    private final List<String> lore;

    CustomBlockType(String displayName, ChatColor color, double minePrice, double placePrice, List<String> lore) {
        this.displayName = displayName;
        this.color = color;
        this.minePrice = minePrice;
        this.placePrice = placePrice;
        this.lore = lore;
    }
}
