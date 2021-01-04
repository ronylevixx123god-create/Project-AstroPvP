/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 */
package com.massivecraft.factions.quests.quests;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.quests.FactionQuests;
import com.massivecraft.factions.quests.QuestInfo;
import com.massivecraft.factions.quests.WeeklyProgress;

public abstract class TimedChallenge
extends GenericIncrementChallenge {
    private long expiration;

    public TimedChallenge(long expirationTime, int countNeeded, String description) {
        super(description, countNeeded);
        this.expiration = expirationTime;
    }

    public long getTimeExpires(FactionQuests parent, Faction faction) {
        WeeklyProgress progress = P.getP().getFactionQuestManager().getWeeklyFactionProgress().get(faction.getId());
        if (progress != null) {
            return this.getTimeExpires(parent, progress);
        }
        return System.currentTimeMillis() + this.expiration;
    }

    public long getTimeExpires(FactionQuests parent, WeeklyProgress progress) {
        QuestInfo info;
        if (progress != null && (info = (QuestInfo)progress.getActiveFactionQuests().values().stream().filter(q -> q.getQuest() == parent).findAny().orElse(null)) != null) {
            return info.getAssigned() + this.expiration;
        }
        return System.currentTimeMillis() + this.expiration;
    }
}

