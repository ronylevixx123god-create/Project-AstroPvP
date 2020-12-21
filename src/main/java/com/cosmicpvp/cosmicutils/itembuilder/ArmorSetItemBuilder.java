/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.enums.WeaponedArmorSet
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.struct.EquipmentType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorSetItemBuilder
extends ItemBuilder {
    public String armorsetName;
    private EquipmentType type;
    private boolean transmog = false;
    private double weaponChance = 0.2;

    public ArmorSetItemBuilder(String armorset, EquipmentType type, boolean transmog) {
        super(Material.DIRT);
        this.type = type;
        this.armorsetName = armorset;
        this.transmog = transmog;
    }

    public ArmorSetItemBuilder setWeaponChance(double val) {
        this.weaponChance = val;
        return this;
    }

    @Override
    public ItemStack build() {
        if (!CosmicUtils.get().isArmorsetEnabled()) {
            return null;
        }
        ArmorSet set = ArmorSet.fromName((String)this.armorsetName);
        if (set != null) {
            return this.type == EquipmentType.WEAPON && set.getArmorSetInfo() instanceof WeaponedArmorSet && Math.random() <= this.weaponChance ? ((WeaponedArmorSet)set.getArmorSetInfo()).getWeapon(this.transmog) : set.getArmorPeice(this.type.getIndex(), this.transmog);
        }
        return null;
    }
}

