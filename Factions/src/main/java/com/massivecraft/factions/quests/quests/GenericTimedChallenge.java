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

public class GenericTimedChallenge
extends TimedChallenge {
    public GenericTimedChallenge(long expirationTime, String description, int countNeeded) {
        super(expirationTime, countNeeded, description);
    }

    @Override
    public String getDataKey() {
        return "count";
    }

    @Override
    public void onQuestEvent(FactionQuests parent, Player player, Faction faction, Object object) {
        if (System.currentTimeMillis() >= this.getTimeExpires(parent, faction)) {
            return;
        }
        this.incrementChallenge(parent, player, faction, 1L, object);
    }

    @Override
    public String getDescription() {
        return String.format(this.description, this.getCountNeeded());
    }
}

