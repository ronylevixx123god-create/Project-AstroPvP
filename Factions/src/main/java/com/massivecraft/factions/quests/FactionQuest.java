/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.massivecraft.factions.Faction
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.quests;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.massivecraft.factions.P;
import org.bukkit.entity.Player;

public abstract class FactionQuest {
    public HashMap<String, QuestData> questDataMap = new HashMap();

    public abstract void onQuestEvent(FactionQuests var1, Player var2, Faction var3, Object var4);

    public abstract String getProgressString(Faction var1);

    public abstract String getDescription();

    public void onComplete(FactionQuests parentEnum, Player player, Faction faction, Object eventThatTriggered) {
        WeeklyProgress progress;
        if (faction == null) {
            faction = FactionUtils.getFaction((Player)player);
        }
        if ((progress = P.getP().getFactionQuestManager().getWeeklyFactionProgress().get(faction.getId())) != null) {
            if (progress.getCompletedQuests().contains((Object)parentEnum)) {
                return;
            }
            progress.completeQuest(parentEnum);
        }
        faction.sendMessage(CC.GreenB + "(!) " + CC.Green + "Faction Quest Complete: " + CC.GreenU + parentEnum.getQuestName());
        faction.sendMessage(CC.Gray + "Use '/f quests' to claim the Godly Quest Reward!");
    }

    public void clearProgress(String id) {
        this.questDataMap.remove(id);
    }

    public long setQuestData(String id, String key, long newValue) {
        QuestData data = this.questDataMap.computeIfAbsent(id, a -> new QuestData());
        data.getFactionQuestData().put(key, newValue);
        return newValue;
    }

    public long getQuestData(String id, String key) {
        QuestData data = this.questDataMap.get(id);
        if (data == null) {
            return 0L;
        }
        Long found = data.getFactionQuestData().get(key);
        if (found != null) {
            return found;
        }
        return 0L;
    }

    public long getQuestData(Faction faction, UUID uuid, String key) {
        QuestData data = this.questDataMap.get(faction.getId());
        if (data == null) {
            return 0L;
        }
        Map found = data.getPlayerQuestData().get(uuid);
        if (found != null) {
            return (long) found.getOrDefault(key, 0L);
        }
        return 0L;
    }

    public HashMap<String, QuestData> getQuestDataMap() {
        return this.questDataMap;
    }
}

