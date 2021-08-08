package com.conaxgames.customskulls.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum AbilityType {
    CAULDRON("CAULDRON",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZmMTkxODBhMzJlZmI3NjQ4YThhYmFjYTkzMjZhYTAyNDNkY2FjMjJmYTFiNTg2ZDM4MjlkYmFkYWQ0YmVjZiJ9fX0=",
            1000000, 300, Material.NOTE_BLOCK),
    PUMPKINPIE("PUMPKINPIE",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJhMDU1NmZhZTViYjlkYTU4M2IwZWQ1YTI1ZTRjZTIxNDc5MTBiNzM0MWMyYmRmOGJjYzlkMWNjN2U2YzFhNyJ9fX0=",
            10000000, 300, Material.NOTE_BLOCK),
    TREASURECHEST("TREASURECHEST",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5NmJlNzg4NmViN2RmNzU1MjVhMzYzZTVmNTQ5NjI2YzIxMzg4ZjBmZGE5ODhhNmU4YmY0ODdhNTMifX19",
            10000000, 300, Material.NOTE_BLOCK),
    ASTROTROPHY("ASTROTROPHY",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVkZmEyODRhYTE1MzI0ZTUxNzg1NjFmODAzZjU5NzYyMjhkOTUxMTU1ODNhYjAzMTI2NmFlMjRlZTFhOTlkMSJ9fX0=",
            10000000, 300, Material.NOTE_BLOCK),
    LEVIATHAN_TOTEM("LEVIATHAN_TOTEM",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVkZmEyODRhYTE1MzI0ZTUxNzg1NjFmODAzZjU5NzYyMjhkOTUxMTU1ODNhYjAzMTI2NmFlMjRlZTFhOTlkMSJ9fX0=",
            15000000, 300, Material.NOTE_BLOCK),
    HALLOWEEN_CANDLE("HALLOWEEN_CANDLE",
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVkZmEyODRhYTE1MzI0ZTUxNzg1NjFmODAzZjU5NzYyMjhkOTUxMTU1ODNhYjAzMTI2NmFlMjRlZTFhOTlkMSJ9fX0=",
                            25000000, 300, Material.NOTE_BLOCK);
    private final String name; // we will use this as a indexing name and for metadata.
    private final String customBlockSkin; // this is the skin texture for the item
    private final int pickupPrice, pricePerAction;
    private final Material material;
}