/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 */
package com.cosmicpvp.factionpoints.utils;

import org.bukkit.entity.Entity;

public class CustomTNTUtils {
    public static boolean isCustomTNT(Entity entity) {
        return entity.hasMetadata("custom_tnt") || entity.hasMetadata("fastExplosion");
    }
}

