/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class AMUtils {
    AMUtils() {
    }

    public static void setMonsterAmount(Entity e, int value) {
        e.setMetadata("monsterAmount", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)value));
        if (e instanceof LivingEntity) {
            ((LivingEntity)e).setCustomName(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + value + "X " + e.getType().name().replace("_", " "));
            ((LivingEntity)e).setCustomNameVisible(true);
        }
    }

    public static int getMonsterAmount(Entity e) {
        return e.hasMetadata("monsterAmount") ? ((MetadataValue)e.getMetadata("monsterAmount").get(0)).asInt() : 1;
    }
}

