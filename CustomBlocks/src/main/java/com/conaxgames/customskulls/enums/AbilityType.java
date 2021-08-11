package com.conaxgames.customskulls.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum AbilityType {
    HALLOWEEN_CAULDRON("HALLOWEEN_CAULDRON",
            "HALLOWEEN_CAULDRON",
            1000000, 300, Material.NOTE_BLOCK),
    PUMPKINPIE("THANKSGIVING_PIE",
            "THANKSGIVING_PIE",
            10000000, 300, Material.NOTE_BLOCK),
    TREASURE_CHEST("TREASURE_CHEST",
            "TREASURE_CHEST",
            10000000, 300, Material.NOTE_BLOCK),
    COSMIC_TROPHY("COSMIC_TROPHY",
            "COSMIC_TROPHY",
            10000000, 300, Material.NOTE_BLOCK),
    LEVIATHAN_TOTEM("LEVIATHAN_TOTEM",
            "LEVIATHAN_TOTEM",
            15000000, 300, Material.NOTE_BLOCK),
    HALLOWEEN_CANDLES("HALLOWEEN_CANDLES",
                            "HALLOWEEN_CANDLES",
                            25000000, 300, Material.NOTE_BLOCK);
    private final String name; // we will use this as a indexing name and for metadata.
    private final String customBlockSkin; // this is the skin texture for the item
    private final int pickupPrice, pricePerAction;
    private final Material material;
}