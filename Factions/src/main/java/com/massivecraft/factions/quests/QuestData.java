/*
 * Decompiled with CFR 0.150.
 */
package com.massivecraft.factions.quests;

import java.util.HashMap;
import java.util.UUID;

public class QuestData {
    private HashMap<UUID, HashMap<String, Long>> playerQuestData = new HashMap();
    private HashMap<String, Long> factionQuestData = new HashMap();

    public HashMap<UUID, HashMap<String, Long>> getPlayerQuestData() {
        return this.playerQuestData;
    }

    public HashMap<String, Long> getFactionQuestData() {
        return this.factionQuestData;
    }
}

