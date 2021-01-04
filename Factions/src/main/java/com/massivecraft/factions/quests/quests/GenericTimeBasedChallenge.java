/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.Faction
 */
package com.massivecraft.factions.quests.quests;

import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.Faction;

public class GenericTimeBasedChallenge
extends GenericIncrementChallenge {
    public GenericTimeBasedChallenge(String description, int countNeeded) {
        super(description, countNeeded);
    }

    @Override
    public String getProgressString(Faction faction) {
        long data = this.getQuestData(faction.getId(), this.getDataKey());
        return data == 0L ? "N/A" : TimeUtils.formatSeconds((long)data);
    }
}

