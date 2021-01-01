/*
 * Decompiled with CFR 0.150.
 */
package com.conaxgames.factions.boosters.struct;

import com.conaxgames.factions.boosters.enums.BoosterType;

import java.util.concurrent.ConcurrentHashMap;

public class FactionBoosters
extends ConcurrentHashMap<BoosterType, ActiveBooster> {
    public boolean isBoosterActive(BoosterType type) {
        return this.containsKey((Object)type);
    }
}

