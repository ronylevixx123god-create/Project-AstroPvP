/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.StringUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.quests;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.massivecraft.factions.P;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WeeklyProgress {
    private List<FactionQuests> lastWeeksQuests = Lists.newArrayList();
    private Map<Integer, QuestInfo> activeFactionQuests = new HashMap<Integer, QuestInfo>();
    private Set<FactionQuests> completedQuests = new HashSet<FactionQuests>();
    private Set<FactionQuests> claimedCompletedQuests = new HashSet<FactionQuests>();
    private long lastQuestGiven;

    public boolean hasQuestActive(FactionQuests quests) {
        return this.activeFactionQuests.values().stream().filter(questInfo -> questInfo.getQuest() == quests).findFirst().orElse(null) != null;
    }

    public FactionQuests resetWeeklyQuests(Faction faction, Calendar now) {
        this.completedQuests.clear();
        this.claimedCompletedQuests.clear();
        this.lastWeeksQuests.clear();
        this.activeFactionQuests.values().forEach(p -> this.lastWeeksQuests.add(p.getQuest()));
        this.activeFactionQuests.clear();
        P.getP().log("Clearing quest data for factionId: " + faction.getId());
        return this.assignQuestForNewDay(faction, now);
    }

    public void completeQuest(FactionQuests complete) {
        this.completedQuests.add(complete);
        Bukkit.getLogger().info("[CosmicFactionQuests] Completing quest " + complete.getQuestName());
    }

    public void checkTime(Faction faction, Calendar calendar, int dayOfWeek) {
        if (!this.activeFactionQuests.containsKey(dayOfWeek)) {
            FactionQuests newQuest = this.assignQuestForNewDay(faction, calendar);
            faction.getFPlayers().forEach(pl -> {
                Player stored = pl.getPlayer();
                if (stored == null) {
                    return;
                }
                stored.sendMessage("");
                stored.sendMessage(StringUtils.getCenteredMessage((String)(CC.GreenB + "(!) " + CC.Green + "New Faction Quest Available " + CC.GreenB + "(!)")));
                stored.sendMessage(StringUtils.getCenteredMessage((String)(CC.GreenU + newQuest.getQuestName())));
                stored.sendMessage(StringUtils.getCenteredMessage((String)(CC.Gray + newQuest.getQuest().getDescription())));
                stored.sendMessage(StringUtils.getCenteredMessage((String)(CC.Gray + "Use " + CC.GrayU + "/f quests" + CC.Gray + " to view more info!")));
                stored.sendMessage("");
            });
        }
    }

    public FactionQuests assignQuestForNewDay(Faction faction, Calendar cal) {
        return this.assignQuestForNewDay(faction, cal, -1, null);
    }

    public FactionQuests assignQuestForNewDay(Faction faction, Calendar cal, int forcedDay, FactionQuests forced) {
        ArrayList availableQuests = Lists.newArrayList((Object[])FactionQuests.values());
        for (FactionQuests quests : FactionQuests.values()) {
            if (quests.canActivate()) continue;
            availableQuests.remove((Object)quests);
        }
        availableQuests.removeAll(this.lastWeeksQuests);
        this.lastWeeksQuests.forEach(availableQuests::remove);
        this.activeFactionQuests.values().forEach(in -> availableQuests.remove((Object)in.getQuest()));
        this.lastQuestGiven = System.currentTimeMillis();
        if (availableQuests.size() <= 0) {
            Bukkit.getLogger().info("UNABLE TO FIND ANYMORE QUESTS: lastWeeksQuests: " + Arrays.toString((Object[])this.lastWeeksQuests.toArray(new Object[0])) + ", active: " + Arrays.toString((Object[])this.lastWeeksQuests.toArray(new Object[0])) + " defaulting to ALL..");
            availableQuests.addAll(Lists.newArrayList((Object[])FactionQuests.values()));
        }
        int dayOfWeek = forcedDay != -1 ? forcedDay : cal.get(7);
        FactionQuests newQuest = forced != null ? forced : (FactionQuests)((Object)availableQuests.get(ThreadLocalRandom.current().nextInt(availableQuests.size())));
        QuestInfo previous = this.activeFactionQuests.put(dayOfWeek, new QuestInfo(System.currentTimeMillis(), newQuest));
        if (previous != null) {
            previous.getQuest().getQuest().clearProgress(faction.getId());
        }
        Bukkit.getLogger().info("Assigning quest for day: " + dayOfWeek + " to quest: " + newQuest.name() + ", previous=" + previous + " for faction " + faction.getId() + " (" + faction.getTag() + ")");
        return newQuest;
    }

    public Map<Integer, QuestInfo> getActiveFactionQuests() {
        return this.activeFactionQuests;
    }

    public Set<FactionQuests> getCompletedQuests() {
        return this.completedQuests;
    }

    public Set<FactionQuests> getClaimedCompletedQuests() {
        return this.claimedCompletedQuests;
    }
}

