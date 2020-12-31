/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package com.compilex.CosmicArmorSets.enums;

import org.bukkit.Material;

public enum HeroicArmorType {
    HELMET(Material.LEATHER_HELMET, "Helmet", 3, 810),
    CHESTPLATE(Material.LEATHER_CHESTPLATE, "Chestplate", 8, 1000),
    LEGGINGS(Material.LEATHER_LEGGINGS, "Leggings", 6, 935),
    BOOTS(Material.LEATHER_BOOTS, "Boots", 3, 686);

    private Material material;
    private String name;
    private int armorModifier;
    private int durability;

    private HeroicArmorType(Material material, String name, int armorModifier, int durability) {
        this.material = material;
        this.name = name;
        this.armorModifier = armorModifier;
        this.durability = durability;
    }

    public static HeroicArmorType getFromName(String name) {
        for (HeroicArmorType type : HeroicArmorType.values()) {
            if (!type.name().equalsIgnoreCase(name)) continue;
            return type;
        }
        return null;
    }

    public static HeroicArmorType getFromMaterial(Material material) {
        String materialName = material.name();
        for (HeroicArmorType type : HeroicArmorType.values()) {
            if (!materialName.endsWith(type.name())) continue;
            return type;
        }
        return null;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getName() {
        return this.name;
    }

    public int getArmorModifier() {
        return this.armorModifier;
    }

    public int getDurability() {
        return this.durability;
    }
}

