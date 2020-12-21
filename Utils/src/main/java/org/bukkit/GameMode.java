/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum GameMode {
    CREATIVE(1),
    SURVIVAL(0),
    ADVENTURE(2);

    private final int value;
    private static final Map<Integer, GameMode> BY_ID;

    private GameMode(int value) {
        this.value = value;
    }

    @Deprecated
    public int getValue() {
        return this.value;
    }

    @Deprecated
    public static GameMode getByValue(int value) {
        return BY_ID.get(value);
    }

    static {
        BY_ID = Maps.newHashMap();
        for (GameMode mode : GameMode.values()) {
            BY_ID.put(mode.getValue(), mode);
        }
    }
}

