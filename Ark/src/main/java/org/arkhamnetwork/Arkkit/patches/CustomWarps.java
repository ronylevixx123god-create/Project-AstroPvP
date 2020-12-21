/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Material;

class CustomWarps
extends ConcurrentHashMap<String, Material> {
    CustomWarps() {
    }

    public boolean isCustomWarp(String customWarp) {
        return this.containsKey(customWarp);
    }
}

