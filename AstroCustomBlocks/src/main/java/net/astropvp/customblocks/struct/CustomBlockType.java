package net.astropvp.customblocks.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import net.astropvp.customblocks.struct.impl.*;
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
            ), new HalloweenCandle()),

    THANKSGIVING_PIE("Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&e&l* &f+50% Chance to Silence Enemies"),
            CC.translate("&e&l* &f+5% Outgoing Damage"),
            CC.translate("&e&l* &f+25% Monopoly Chance")
    ), new ThanksgivingPie()),

    TREASURE_CHEST("Treasure Chest", ChatColor.GOLD, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&7Increase the percentage of EXP"),
            CC.translate("&7dropped by 50% when mobs are killed"),
            CC.translate("&7in the chunk it's placed in.")
    ), new TreasureChest()),

    COSMIC_TROPHY("Cosmic Trophy", ChatColor.GOLD, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&710% chance to roll the Astro-Slot"),
            CC.translate("&7Bot without consuming your ticket.")
    ), new CosmicTrophy()),

    LEVIATHAN_TOTEM("Leviathan Totem", ChatColor.DARK_AQUA, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7Chance to double mob spawner"),
            CC.translate("&7drops in the chunk it's placed in."),
            "",
            CC.translate("&7Any mob spawner that is lucky enough"),
            CC.translate("&7to be in the orbit of this sacred holy"),
            CC.translate("&7object will have its mobs imbued with"),
            CC.translate("&7inner looting.")
    ), new LeviathanTotem()),

    HALLOWEEN_CAULDRON("Cauldron", ChatColor.GOLD, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&6&l* &ePhantasm: &f+5% Outgoing Damage"),
            CC.translate("&6&l* &eStolen Mind: &f-5% Incoming Damage"),
            CC.translate("&6&l* &eSpooky Aura: &f33% Less Phoenix Cooldown")
    ), new Cauldron()),

    COSMIC_TOKEN_GENERATOR("Slotbot Opener", ChatColor.GREEN, 1000000, 1000000, Arrays.asList(""
    ), new SlotOpener()),

    SECURITY_SYSTEM("Key Opener", ChatColor.AQUA, 1000000, 1000000, Arrays.asList(""
    ), new KeyOpener()),

    BLAZE_STONE_TOTEM("Auto Mob Grinder", ChatColor.RED, 1000000, 1000000, Arrays.asList(
            CC.translate("&7Automatically grinds Blazes"),
            CC.translate("&7in a 1 block radius from the block."),
            "",
            CC.translate("&7Dug up from the deep depths of"),
            CC.translate("&7the gold mines, this custom block is"),
            CC.translate("&7essential for grinding Blazes.")
    ), new AutoGrinder());

    private final String displayName;
    private final ChatColor color;
    private final double minePrice;
    private final double placePrice;
    private final List<String> lore;
    private final CustomBlock customBlock;

    CustomBlockType(String displayName, ChatColor color, double minePrice, double placePrice, List<String> lore, CustomBlock customBlock) {
        this.displayName = displayName;
        this.color = color;
        this.minePrice = minePrice;
        this.placePrice = placePrice;
        this.lore = lore;
        this.customBlock = customBlock;
    }
}
