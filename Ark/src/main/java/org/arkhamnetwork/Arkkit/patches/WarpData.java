/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Material;

final class WarpData {
    String warpName;
    String displayName;
    String[] description;
    Material displayMaterial;

    protected WarpData(String warpName, String displayName, Material displayMaterial, String... description) {
        this.warpName = warpName;
        this.displayName = displayName;
        this.description = description;
        this.displayMaterial = displayMaterial;
    }

    public String getWarpName() {
        return this.warpName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String[] getDescription() {
        return this.description;
    }

    public Material getDisplayMaterial() {
        return this.displayMaterial;
    }
}

