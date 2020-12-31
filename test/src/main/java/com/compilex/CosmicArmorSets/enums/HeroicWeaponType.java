/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Material
 */
package com.compilex.CosmicArmorSets.enums;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;

public enum HeroicWeaponType {
    AXE(Material.GOLD_AXE, CC.RedB + "Heroic Axe", 1952, 4.0, 3.0),
    SWORD(Material.GOLD_SWORD, CC.RedB + "Heroic Sword", 1952, 4.0, 4.0);

    public Material material;
    public String name;
    public int durability;
    private double attackModifier;
    private double vanillaBaseDamage;

    private HeroicWeaponType(Material material, String name, int durability, double attackModifier, double vanillaBaseDamage) {
        this.material = material;
        this.name = name;
        this.durability = durability;
        this.attackModifier = attackModifier;
        this.vanillaBaseDamage = vanillaBaseDamage;
    }

    public static HeroicWeaponType findFromMaterial(Material material) {
        String materialName = material.name();
        for (HeroicWeaponType type : HeroicWeaponType.values()) {
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

    public int getDurability() {
        return this.durability;
    }

    public double getAttackModifier() {
        return this.attackModifier;
    }

    public double getVanillaBaseDamage() {
        return this.vanillaBaseDamage;
    }
}

