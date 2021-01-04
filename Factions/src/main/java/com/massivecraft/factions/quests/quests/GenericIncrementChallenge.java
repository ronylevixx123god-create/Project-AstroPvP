/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.quests.quests;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.quests.FactionQuests;
import org.bukkit.entity.Player;

public class GenericIncrementChallenge
extends IncrementableChallenge {
    protected String description;

    public GenericIncrementChallenge(String description, int countNeeded) {
        super(countNeeded);
        this.description = description;
    }

    @Override
    public String getDataKey() {
        return "amount";
    }

    @Override
    public void onQuestEvent(FactionQuests parent, Player player, Faction faction, Object object) {
        this.incrementChallenge(parent, player, faction, 1L, object);
    }

    @Override
    public String getDescription() {
        return String.format(this.description, this.getCountNeeded());
    }
}

