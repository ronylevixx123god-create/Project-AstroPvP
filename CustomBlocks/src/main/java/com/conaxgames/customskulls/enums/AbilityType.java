package com.conaxgames.customskulls.enums;

import com.conaxgames.customskulls.util.CC;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AbilityType {
    FIVE_PERCENT_DAMAGE_INCREASE("FIVE_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE1MjYzMmE5YzhmN2JkZTk0NzE5MDY0MjM0OTIwZGVkNDg2MTRlMmJkOGJjOTFhZmU3ZmZjMmNkOGE0NmYxOSJ9fX0=",
            1000000, 300),
    TEN_PERCENT_DAMAGE_INCREASE("TEN_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2MzZiYTY5ODhjZTliNDBkZGM3NDlhMDljZTBmYjkzOWFmNTI2MDA1OTk1YzE4ZDMyM2FjOTY2MjVmMGQ2ZCJ9fX0=",
            1000000, 300);

    private final String name; // we will use this as a indexing name and for metadata.
    private final String displayName; // we will use this for the item
    private final String skinTexture; // this is the skin texture for the item
    private final int pickupPrice, pricePerAction;
}