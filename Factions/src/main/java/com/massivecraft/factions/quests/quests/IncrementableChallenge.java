/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.quests.quests;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.quests.FactionQuest;
import com.massivecraft.factions.quests.FactionQuests;
import org.bukkit.entity.Player;

public abstract class IncrementableChallenge
extends FactionQuest {
    private int countNeeded = 0;

    public IncrementableChallenge(int countNeeded) {
        this.countNeeded = countNeeded;
    }

    @Override
    public String getProgressString(Faction faction) {
        long data = this.getQuestData(faction.getId(), this.getDataKey());
        return (int)data + " / " + this.countNeeded;
    }

    public abstract String getDataKey();

    public boolean incrementChallenge(FactionQuests parent, Player player, Faction faction, long incrementAmount, Object triggerEvent) {
        long currentValue = this.getQuestData(faction.getId(), this.getDataKey());
        long newValue = this.setQuestData(faction.getId(), this.getDataKey(), currentValue + incrementAmount);
        if (newValue >= (long)this.countNeeded) {
            this.onComplete(parent, player, faction, triggerEvent);
            return true;
        }
        return false;
    }

    public int getCountNeeded() {
        return this.countNeeded;
    }
}

