package com.conaxgames.customskulls.enums;

import com.conaxgames.customskulls.util.CC;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AbilityType {
    FIVE_PERCENT_DAMAGE_INCREASE("5_PERCENT_DAMAGE_INCREASE", CC.RED + "5% Damage Increase", "adad828da8d8a", 1000000, 300);

    private final String name; // we will use this as a indexing name and for metadata.
    private final String displayName; // we will use this for the item
    private final String skinTexture; // this is the skin texture for the item
    private final int pickupPrice, pricePerAction;
}