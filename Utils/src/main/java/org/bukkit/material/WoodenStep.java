/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.MaterialData;

public class WoodenStep
extends MaterialData {
    public WoodenStep() {
        super(Material.WOOD_STEP);
    }

    @Deprecated
    public WoodenStep(int type) {
        super(type);
    }

    public WoodenStep(TreeSpecies species) {
        this();
        this.setSpecies(species);
    }

    public WoodenStep(TreeSpecies species, boolean inv) {
        this();
        this.setSpecies(species);
        this.setInverted(inv);
    }

    @Deprecated
    public WoodenStep(int type, byte data) {
        super(type, data);
    }

    @Deprecated
    public WoodenStep(Material type, byte data) {
        super(type, data);
    }

    public TreeSpecies getSpecies() {
        return TreeSpecies.getByData((byte)(this.getData() & 3));
    }

    public void setSpecies(TreeSpecies species) {
        this.setData((byte)(this.getData() & 0xC | species.getData()));
    }

    public boolean isInverted() {
        return (this.getData() & 8) != 0;
    }

    public void setInverted(boolean inv) {
        int dat = this.getData() & 7;
        if (inv) {
            dat |= 8;
        }
        this.setData((byte)dat);
    }

    @Override
    public WoodenStep clone() {
        return (WoodenStep)super.clone();
    }

    @Override
    public String toString() {
        return super.toString() + " " + (Object)((Object)this.getSpecies()) + (this.isInverted() ? " inverted" : "");
    }
}

