/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.material;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public abstract class TexturedMaterial
extends MaterialData {
    public TexturedMaterial(Material m) {
        super(m);
    }

    @Deprecated
    public TexturedMaterial(int type) {
        super(type);
    }

    @Deprecated
    public TexturedMaterial(int type, byte data) {
        super(type, data);
    }

    @Deprecated
    public TexturedMaterial(Material type, byte data) {
        super(type, data);
    }

    public abstract List<Material> getTextures();

    public Material getMaterial() {
        int n = this.getTextureIndex();
        if (n > this.getTextures().size() - 1) {
            n = 0;
        }
        return this.getTextures().get(n);
    }

    public void setMaterial(Material material) {
        if (this.getTextures().contains((Object)material)) {
            this.setTextureIndex(this.getTextures().indexOf((Object)material));
        } else {
            this.setTextureIndex(0);
        }
    }

    @Deprecated
    protected int getTextureIndex() {
        return this.getData();
    }

    @Deprecated
    protected void setTextureIndex(int idx) {
        this.setData((byte)idx);
    }

    @Override
    public String toString() {
        return (Object)((Object)this.getMaterial()) + " " + super.toString();
    }

    @Override
    public TexturedMaterial clone() {
        return (TexturedMaterial)super.clone();
    }
}

