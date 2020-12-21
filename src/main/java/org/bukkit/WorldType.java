/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum WorldType {
    NORMAL("DEFAULT"),
    FLAT("FLAT"),
    VERSION_1_1("DEFAULT_1_1"),
    LARGE_BIOMES("LARGEBIOMES"),
    AMPLIFIED("AMPLIFIED");

    private static final Map<String, WorldType> BY_NAME;
    private final String name;

    private WorldType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static WorldType getByName(String name) {
        return BY_NAME.get(name.toUpperCase());
    }

    static {
        BY_NAME = Maps.newHashMap();
        for (WorldType type : WorldType.values()) {
            BY_NAME.put(type.name, type);
        }
    }
}

