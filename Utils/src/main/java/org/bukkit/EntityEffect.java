/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum EntityEffect {
    HURT(2),
    DEATH(3),
    WOLF_SMOKE(6),
    WOLF_HEARTS(7),
    WOLF_SHAKE(8),
    SHEEP_EAT(10);

    private final byte data;
    private static final Map<Byte, EntityEffect> BY_DATA;

    private EntityEffect(int data) {
        this.data = (byte)data;
    }

    @Deprecated
    public byte getData() {
        return this.data;
    }

    @Deprecated
    public static EntityEffect getByData(byte data) {
        return BY_DATA.get(data);
    }

    static {
        BY_DATA = Maps.newHashMap();
        for (EntityEffect entityEffect : EntityEffect.values()) {
            BY_DATA.put(entityEffect.data, entityEffect);
        }
    }
}

