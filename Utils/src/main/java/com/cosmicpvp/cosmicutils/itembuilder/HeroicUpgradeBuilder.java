/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class HeroicUpgradeBuilder
        extends ItemBuilder {
    private String armorSetName;
    private boolean random;
    private double percent;
    private double min;
    private double max;

    public HeroicUpgradeBuilder(String armorSetName, double min, double max) {
        super(Material.EMERALD);
        this.min = min;
        this.max = max;
        this.armorSetName = armorSetName;
    }

    public HeroicUpgradeBuilder(boolean random, double min, double max) {
        super(Material.EMERALD);
        this.min = min;
        this.max = max;
        this.random = random;
    }

    public HeroicUpgradeBuilder(String armorSetName, double percent) {
        super(Material.EMERALD);
        this.percent = percent;
        this.armorSetName = armorSetName;
    }

    @Override
    public ItemStack build() {
        ArmorSet set;
        ArmorSet armorSet = set = this.random ? CosmicArmorSetsAPI.getRandomArmorSetFromUpgrade() : ArmorSet.fromName((String) this.armorSetName);
        if (set == null) {
            return null;
        }
        return CosmicArmorSetsAPI.createArmorSetUpgrade((ArmorSet) set, (double) (this.percent != 0.0 ? this.percent : ThreadLocalRandom.current().nextDouble(this.max - this.min + 1.0) + this.min));
    }
}

