package net.astropvp.customblocks.struct;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astropvp.customblocks.struct.abilities.LevelAble;
import net.astropvp.customblocks.struct.impl.*;
import net.astropvp.customblocks.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
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
            ), HalloweenCandle.class),

    THANKSGIVING_PIE("Pumpkin Pie", ChatColor.YELLOW, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&e&l* &f+50% Chance to Silence Enemies"),
            CC.translate("&e&l* &f+5% Outgoing Damage"),
            CC.translate("&e&l* &f+25% Monopoly Chance")
    ), ThanksgivingPie.class),

    TREASURE_CHEST("Treasure Chest", ChatColor.GOLD, 1000000, 1000000, Arrays.asList(CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&7Increase the percentage of EXP"),
            CC.translate("&7dropped by 50% when mobs are killed"),
            CC.translate("&7in the chunk it's placed in.")
    ), TreasureChest.class),

    COSMIC_TROPHY("Cosmic Trophy", ChatColor.GOLD, 1000000, 1000000, Arrays.asList(
            CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&710% chance to roll the Astro-Slot"),
            CC.translate("&7Bot without consuming your ticket.")
    ), CosmicTrophy.class),

    LEVIATHAN_TOTEM("Leviathan Totem", ChatColor.DARK_AQUA, 1000000, 1000000, Arrays.asList(
            CC.translate("&7Receive all of the following"),
            CC.translate("&7Chance to double mob spawner"),
            CC.translate("&7drops in the chunk it's placed in."),
            "",
            CC.translate("&7Any mob spawner that is lucky enough"),
            CC.translate("&7to be in the orbit of this sacred holy"),
            CC.translate("&7object will have its mobs imbued with"),
            CC.translate("&7inner looting.")
    ), LeviathanTotem.class),

    HALLOWEEN_CAULDRON("Cauldron", ChatColor.GOLD, 1000000, 1000000, Arrays.asList(
            CC.translate("&7Receive all of the following"),
            CC.translate("&7buffs when standing in the same chunk."),
            "",
            CC.translate("&6&l* &ePhantasm: &f+5% Outgoing Damage"),
            CC.translate("&6&l* &eStolen Mind: &f-5% Incoming Damage"),
            CC.translate("&6&l* &eSpooky Aura: &f33% Less Phoenix Cooldown")
    ), Cauldron.class),

    COSMIC_TOKEN_GENERATOR("Slotbot Opener", ChatColor.GREEN, 1000000, 150000, Arrays.asList(
            CC.translate("&7Roll all your slotbot tickets"),
            CC.translate("&7at once using the slotbot opener."),
            "",
            CC.translate("&7Place chests around the block"),
            CC.translate("&7to collect all the loot from the bot.")
    ), SlotOpener.class),

    SECURITY_SYSTEM("Key Opener", ChatColor.AQUA, 1000000, 150000, Arrays.asList(
            CC.translate("&7Open all your cratekeys"),
            CC.translate("&7at once using the key opener."),
            "",
            CC.translate("&7Place chests around the block"),
            CC.translate("&7to collect all the loot from the keys.")
    ), KeyOpener.class),

    BLAZE_STONE_TOTEM("Auto Mob Grinder", ChatColor.RED, 1000000, 150000, Arrays.asList(
            CC.translate("&7Automatically grinds Blazes"),
            CC.translate("&7in a 1 block radius from the block."),
            "",
            CC.translate("&7Dug up from the deep depths of"),
            CC.translate("&7the gold mines, this custom block is"),
            CC.translate("&7essential for grinding Blazes.")
    ), AutoGrinder.class);

    private final String displayName;
    private final ChatColor color;
    private final double minePrice;
    private final double placePrice;
    private final List<String> lore;
    private final Class<? extends CustomBlock> customBlockClass;


    public ItemStack build(int level) {
        String title = level != 0 ? getColor() + "&l" + getDisplayName() + " &7(&fLVL " + level + "&7)": getColor() + "&l" + getDisplayName();
        ItemStack block = new ItemBuilder(Material.NOTE_BLOCK)
                .setName(title)
                .addLoreLine(getColor() + "Price to Place: &f$" + NumberUtils.formatMoney(getPlacePrice()))
                .addLoreLine(getColor() + "Price to Mine: &f$" + NumberUtils.formatMoney(getMinePrice()))
                .addLoreLine("")
                .addLoreLines(getLore())
                .addLoreLine("")
                .addLoreLine("&7Place custom block to use!")
                .colorize()
                .build();
        NBTWrapper nbtItem = new NBTWrapper(block);
        NBTTagCompoundBuilder cosmicData = nbtItem.hasKey("cosmicData") ? new NBTTagCompoundBuilder(nbtItem.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        NBTTagCompoundBuilder cosmicData2 = cosmicData.hasKey("customBlockData") ? new NBTTagCompoundBuilder(cosmicData.getNBTBase("customBlockData")) : new NBTTagCompoundBuilder();
        nbtItem.setString("cosmicType", "custom_block");
        if (level != 0) {
            cosmicData2.setInt("level", level);
        }
        cosmicData2.setString("type", name());
        cosmicData.setNBTBase("customBlockData", cosmicData2.get());
        nbtItem.setNBTBase("cosmicData", cosmicData.get());
        return nbtItem.build();
    }


}
