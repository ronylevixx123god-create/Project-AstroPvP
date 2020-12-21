/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.material;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class Crops
extends MaterialData {
    public Crops() {
        super(Material.CROPS);
    }

    public Crops(CropState state) {
        this();
        this.setState(state);
    }

    @Deprecated
    public Crops(int type) {
        super(type);
    }

    public Crops(Material type) {
        super(type);
    }

    @Deprecated
    public Crops(int type, byte data) {
        super(type, data);
    }

    @Deprecated
    public Crops(Material type, byte data) {
        super(type, data);
    }

    public CropState getState() {
        return CropState.getByData(this.getData());
    }

    public void setState(CropState state) {
        this.setData(state.getData());
    }

    @Override
    public String toString() {
        return (Object)((Object)this.getState()) + " " + super.toString();
    }

    @Override
    public Crops clone() {
        return (Crops)super.clone();
    }
}

