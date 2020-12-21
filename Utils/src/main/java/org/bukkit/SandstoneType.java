/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SandstoneType {
    CRACKED(0),
    GLYPHED(1),
    SMOOTH(2);

    private final byte data;
    private static final Map<Byte, SandstoneType> BY_DATA;

    private SandstoneType(int data) {
        this.data = (byte)data;
    }

    @Deprecated
    public byte getData() {
        return this.data;
    }

    @Deprecated
    public static SandstoneType getByData(byte data) {
        return BY_DATA.get(data);
    }

    static {
        BY_DATA = Maps.newHashMap();
        for (SandstoneType type : SandstoneType.values()) {
            BY_DATA.put(type.data, type);
        }
    }
}

