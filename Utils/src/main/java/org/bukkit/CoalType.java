/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum CoalType {
    COAL(0),
    CHARCOAL(1);

    private final byte data;
    private static final Map<Byte, CoalType> BY_DATA;

    private CoalType(int data) {
        this.data = (byte)data;
    }

    @Deprecated
    public byte getData() {
        return this.data;
    }

    @Deprecated
    public static CoalType getByData(byte data) {
        return BY_DATA.get(data);
    }

    static {
        BY_DATA = Maps.newHashMap();
        for (CoalType type : CoalType.values()) {
            BY_DATA.put(type.data, type);
        }
    }
}

