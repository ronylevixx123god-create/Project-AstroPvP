/*
 * Decompiled with CFR 0.150.
 */
package com.massivecraft.factions.quests;

public class QuestInfo {
    private long assigned;
    private FactionQuests quest;

    public String toString() {
        return "assigned=" + this.assigned + ", quest: " + (Object)((Object)this.quest);
    }

    public QuestInfo(long assigned, FactionQuests quest) {
        this.assigned = assigned;
        this.quest = quest;
    }

    public long getAssigned() {
        return this.assigned;
    }

    public FactionQuests getQuest() {
        return this.quest;
    }
}

