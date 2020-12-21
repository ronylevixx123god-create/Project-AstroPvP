/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.protection.flags.EnumFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.entity.EntityType;

public class EntityTypeFlag
extends EnumFlag<EntityType> {
    public EntityTypeFlag(String name, RegionGroup defaultGroup) {
        super(name, EntityType.class, defaultGroup);
    }

    public EntityTypeFlag(String name) {
        super(name, EntityType.class);
    }

    @Override
    public EntityType detectValue(String input) {
        EntityType lowMatch = null;
        for (EntityType type : EntityType.values()) {
            if (type.name().equalsIgnoreCase(input.trim())) {
                return type;
            }
            if (!type.name().toLowerCase().startsWith(input.toLowerCase().trim())) continue;
            lowMatch = type;
        }
        return lowMatch;
    }
}

