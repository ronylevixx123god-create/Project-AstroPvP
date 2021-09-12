package com.conaxgames.customskulls.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum AbilityType {
    HALLOWEEN_CAULDRON("HALLOWEEN_CAULDRON",
            "HALLOWEEN_CAULDRON",
            1000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGEyMjU3Y2UwMzQyMjA5NTg2MWMzMTE0NTUwYzg5YmQzYzkyMzk5NmExMzc1NDdlMDY3MjMyZGU1ZDU2MiJ9fX0="),
    THANKSGIVING_PIE("THANKSGIVING_PIE",
            "THANKSGIVING_PIE",
            10000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTA0NzcxMzRkMmFjNTI1NjlmMDYyNzA3YjA0ZTRhMzdhNzBhOWZlZDYxMTBjN2QyZjE0NWY3NmJlMWNkODI2ZSJ9fX0="),
    TREASURE_CHEST("TREASURE_CHEST",
            "TREASURE_CHEST",
            10000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZhY2ZjNjQzZjYwOGUxNmRlMTkzMzVkZGNhNzFhODI4ZGZiOGRhY2E1NzkzZWI1YmJjYjBjN2QxNTU5MjQ5In19fQ=="),
    COSMIC_TROPHY("COSMIC_TROPHY",
            "COSMIC_TROPHY",
            10000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM0YTU5MmE3OTM5N2E4ZGYzOTk3YzQzMDkxNjk0ZmMyZmI3NmM4ODNhNzZjY2U4OWYwMjI3ZTVjOWYxZGZlIn19fQ=="),
    LEVIATHAN_TOTEM("LEVIATHAN_TOTEM",
            "LEVIATHAN_TOTEM",
            15000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc1NmZiNjM0MTA4NmZjMmY2NDAxMzVmZDA5MTczN2VjMTdiYWUxYThmOGZkNWM5NTMyYTI4NjFkODIxZSJ9fX0="),
    HALLOWEEN_CANDLES("HALLOWEEN_CANDLES",
                            "HALLOWEEN_CANDLES",
                            25000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFjOTQ0OGU5MGUxZmZhMjhmYTcyNDM0YTAyNTUxZjI1NzRjNjhmZTZhN2FhOGE5OTVjZDE2OTcxYWE4YTMyMyJ9fX0="),
    TRIALS_FAME_AND_FORTUNE("TRIALS_FAME_AND_FORTUNE",
            "TRIALS_FAME_AND_FORTUNE",
            15000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ=="),
    COSMIC_TOKEN_GENERATOR("COSMIC_TOKEN_GENERATOR",
                                    "COSMIC_TOKEN_GENERATOR",
                                    15000000, 300, Material.NOTE_BLOCK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==");
    private final String name; // we will use this as a indexing name and for metadata.
    private final String customBlockSkin; // this is the skin texture for the item
    private final int pickupPrice, pricePerAction;
    private final Material material;
    private final String skinTexture;
}


//COSMIC_TOKEN_GENERATOR