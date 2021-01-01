/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 */
package com.conaxgames.factions.boosters;

import com.conaxgames.factions.boosters.enums.BoosterType;
import com.conaxgames.factions.boosters.struct.ActiveBooster;
import com.conaxgames.factions.boosters.struct.FactionBoosters;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;

import java.util.List;

public class BoosterAPI {
    public static boolean hasActiveBooster(Faction faction, BoosterType type) {
        FactionBoosters boosters = P.getP().getBoosterManager().getFactionBooster(faction);
        if (boosters == null) {
            return false;
        }
        return boosters.isBoosterActive(type);
    }

    public static List<ActiveBooster> getActiveBoosters(Faction faction) {
        FactionBoosters boosters = P.getP().getBoosterManager().getFactionBooster(faction);
        if (boosters == null) {
            return null;
        }
        return Lists.newArrayList(boosters.values());
    }
}

