/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package me.ifamasssxd.cosmicbosses.struct;

import org.bukkit.Material;

public class NewBlockData {
    Material material;
    byte data;

    public NewBlockData(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public Material getMaterial() {
        return this.material;
    }

    public byte getData() {
        return this.data;
    }
}

