/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.Faction
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.quests.quests;

import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.quests.FactionQuests;
import org.bukkit.entity.Player;

public class OutpostHoldChallenge
extends IncrementableChallenge {
    private String outpostType;
    private String outpostDisplayName;

    public OutpostHoldChallenge(String outpostType, String displayName, int countNeeded) {
        super(countNeeded);
        this.outpostType = outpostType;
        this.outpostDisplayName = displayName;
    }

    @Override
    public void onQuestEvent(FactionQuests parent, Player player, Faction faction, Object object) {
        this.onQuestEvent(parent, player, faction, object);
    }

    @Override
    public String getProgressString(Faction faction) {
        long data = this.getQuestData(faction.getId(), this.getDataKey());
        return data <= 0L ? "N/A" : TimeUtils.formatSeconds((long)data);
    }

    @Override
    public String getDescription() {
        return "Hold " + this.outpostDisplayName + " Outpost for " + TimeUtils.formatSeconds((long)this.getCountNeeded());
    }

    @Override
    public String getDataKey() {
        return "time";
    }
}

