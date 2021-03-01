package com.conaxgames.customskulls.enums;

import com.conaxgames.customskulls.util.CC;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AbilityType {
    CAULDRON("FIVE_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZmMTkxODBhMzJlZmI3NjQ4YThhYmFjYTkzMjZhYTAyNDNkY2FjMjJmYTFiNTg2ZDM4MjlkYmFkYWQ0YmVjZiJ9fX0=",
            1000000, 300),
    PUMPKINPIE("TEN_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJhMDU1NmZhZTViYjlkYTU4M2IwZWQ1YTI1ZTRjZTIxNDc5MTBiNzM0MWMyYmRmOGJjYzlkMWNjN2U2YzFhNyJ9fX0=",
            1000000, 300),
    TREASURECHEST("TEN_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5NmJlNzg4NmViN2RmNzU1MjVhMzYzZTVmNTQ5NjI2YzIxMzg4ZjBmZGE5ODhhNmU4YmY0ODdhNTMifX19",
            1000000, 300),
    ASTROTROPHY("TEN_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVkZmEyODRhYTE1MzI0ZTUxNzg1NjFmODAzZjU5NzYyMjhkOTUxMTU1ODNhYjAzMTI2NmFlMjRlZTFhOTlkMSJ9fX0=",
            1000000, 300);


    private final String name; // we will use this as a indexing name and for metadata.
    private final String displayName; // we will use this for the item
    private final String skinTexture; // this is the skin texture for the item
    private final int pickupPrice, pricePerAction;
}