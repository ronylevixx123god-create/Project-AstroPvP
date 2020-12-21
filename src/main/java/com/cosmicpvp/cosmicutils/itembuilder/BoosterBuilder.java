/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters
 *  me.ifamasssxd.cosmicfactionboosters.enums.BoosterType
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BoosterBuilder
extends ItemBuilder {
    public String boosterType;
    public int duration;
    private double multiplier;

    public BoosterBuilder(String booster, int duration, double multiplier) {
        super((Material)null, 0);
        this.boosterType = booster;
        this.duration = duration;
        this.multiplier = multiplier;
    }

    @Override
    public ItemStack build() {
        BoosterType type = BoosterType.fromName((String)this.boosterType);
        if (type == null) {
            Bukkit.getLogger().info("Unable to find booster type from " + this.boosterType + " (" + this.duration + "s, " + this.multiplier + "x) ! Returning EXP by default!");
            type = BoosterType.EXP;
        }
        return CosmicFactionBoosters.get().getBoosterManager().createBooster(type, this.duration, this.multiplier);
    }

    public static BoosterBuilderBuilder builder() {
        return new BoosterBuilderBuilder();
    }

    public String getBoosterType() {
        return this.boosterType;
    }

    public int getDuration() {
        return this.duration;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public static class BoosterBuilderBuilder {
        private String boosterType;
        private int duration;
        private double multiplier;

        BoosterBuilderBuilder() {
        }

        public BoosterBuilderBuilder boosterType(String boosterType) {
            this.boosterType = boosterType;
            return this;
        }

        public BoosterBuilderBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public BoosterBuilderBuilder multiplier(double multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public BoosterBuilder build() {
            return new BoosterBuilder(this.boosterType, this.duration, this.multiplier);
        }

        public String toString() {
            return "BoosterBuilder.BoosterBuilderBuilder(boosterType=" + this.boosterType + ", duration=" + this.duration + ", multiplier=" + this.multiplier + ")";
        }
    }
}

