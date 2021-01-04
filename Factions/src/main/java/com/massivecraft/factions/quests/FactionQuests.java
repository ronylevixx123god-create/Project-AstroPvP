/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicFund.CosmicFundAPI
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  org.apache.commons.lang.StringUtils
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.Arkkit.utils.EssentialsUtils
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 */
package com.massivecraft.factions.quests;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.massivecraft.factions.P;
import com.massivecraft.factions.quests.quests.*;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum FactionQuests {
    KILL_CREEPER_I(QuestType.KILL, new KillEntityChallenge(EntityType.CREEPER, "world_end", null, 300)),
    KILL_BLAZE_I(QuestType.KILL, new KillEntityChallenge(EntityType.BLAZE, "Kill %s Blazes inside your claims", 1500)),
    KILL_BOSS_KING_SLIME(QuestType.KILL, new KillBossChallenge(BossType.KING_SLIME, 1)),
    KILL_BOSS_UNDEAD_ASSASSIN(QuestType.KILL, new KillBossChallenge(BossType.SKELETON_HORSE, 1)),
    KILL_BOSS_BROOD_MOTHER(QuestType.KILL, new KillBossChallenge(BossType.BROOD_MOTHER, 1)),
    KILL_BOSS_PLAGUE_BLOATER(QuestType.KILL, new KillBossChallenge(BossType.PLAGUE_BLOATER, 1)),
    KILL_BOSS_YIJKI_WORLD_DESTROYER(QuestType.KILL, new KillBossChallenge(BossType.DEMONIC_WITHER, 1)),
    TOP_DOG(QuestType.KILL, new GenericIncrementChallenge("Get top damage on a WarZone Boss", 1)),
    KILL_VANILLA_OUTPOST_I(QuestType.INCREMENTABLE, new KillEntityChallenge(EntityType.UNKNOWN, "cosmic-station-vanilla", "Kill %s Mobs at the Vanilla Outpost", 100)),
    KILL_COSMONAUT_OUTPOST_I(QuestType.INCREMENTABLE, new KillEntityChallenge(EntityType.UNKNOWN, "cosmic-station-3", "Kill %s Mobs at the Cosmonaut Outpost", 100)),
    HOLD_TRAINEE_OUTPOST_I(QuestType.INCREMENTABLE, new OutpostHoldChallenge("Trainee", "Trainee", (int)TimeUnit.HOURS.toSeconds(3L))),
    HOLD_VANILLA_OUTPOST_I(QuestType.INCREMENTABLE, new OutpostHoldChallenge("Vanilla", "Vanilla", (int)TimeUnit.HOURS.toSeconds(3L))),
    HOLD_HERO_OUTPOST_I(QuestType.INCREMENTABLE, new OutpostHoldChallenge("Hero", "Hero", (int)TimeUnit.HOURS.toSeconds(3L))),
    HOLD_COSMONAUT_OUTPOST_I(QuestType.INCREMENTABLE, new OutpostHoldChallenge("Cosmonaut", "Cosmonaut", (int)TimeUnit.HOURS.toSeconds(3L))),
    DUNGEON_MASTER_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Complete %s Dungeons", 3)),
    DUNGEON_RACER_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Complete Dungeon in 15m or less", 1)),
    DUNGEON_RACER_II(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Complete Dungeon in 10m or less", 1)),
    DUNGEON_PORTALS_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Spawn %s Dungeon Portals", 5)),
    CONQUEST_BREAKER_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Land the last hit on %s Conquest Chests", 3)),
    KILL_CONQUEST_BOSSES_I(QuestType.INCREMENTABLE, new KillBossChallenge(Lists.newArrayList(BossType.EXTERMINATOR, BossType.ENFORCER, BossType.MARKSMAN), 5)),
    F_UPGRADES_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Purchase %s Faction Upgrades", 5)),
    KOTH_CAPTURER_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Capture a KOTH", 1)),
    DAILY_CHALLENGE_MASTER_I(QuestType.INCREMENTABLE, new GenericIncrementChallenge("Place #1 in a Daily Challenge", 1)),
    LMS_DEFENDER_I(QuestType.INCREMENTABLE, new GenericTimeBasedChallenge("Hold LMS for 15 Minutes", 900)),
    ENVOY_LOOTER_I(QuestType.INCREMENTABLE, new GenericTimedChallenge(TimeUnit.DAYS.toMillis(1L), "Loot %s Envoy Chests in 1 Day", 50)),
    IRON_KOTH_MERCHANT_I(QuestType.INCREMENTABLE, new SellItemChallenge(Material.IRON_BLOCK, "cosmic-station-vanilla", "Sell %s Iron Blocks at Vanilla Outpost", 1000)),
    EMERALD_KOTH_MERCHANT_I(QuestType.INCREMENTABLE, new SellItemChallenge(Material.EMERALD_BLOCK, "cosmic-station-vanilla", "Sell %s Emerald Blocks at Vanilla Outpost", 1000)),
    ULTIMATE_ENCHANTER_I(QuestType.INCREMENTABLE, new GenericTimedChallenge(TimeUnit.DAYS.toMillis(1L), "Purchase %s Ultimate Books in a Day", 30)),
    LEGENDARY_ENCHANTER_I(QuestType.INCREMENTABLE, new GenericTimedChallenge(TimeUnit.DAYS.toMillis(1L), "Purchase %s Legendary Books in a Day", 15));

    private QuestType type;
    private FactionQuest quest;

    public String getQuestName() {
        return StringUtils.capitaliseAllWords((String)this.name().toLowerCase().replace("_", " ")).replace("Lms", "LMS").replace("Ii", "II").replace("Iii", "III");
    }

    public boolean canActivate() {
        if (this == KILL_CREEPER_I) {
            if (!ArkPlugins.END_GRINDING.getArkPlugin().isRunning()) {
                return false;
            }
            if (EssentialsUtils.getEssentials() == null || !EssentialsUtils.getEssentials().getWarps().getList().contains("end")) {
                return false;
            }
            if (P.getP().isFundEnabled()) {
                return false;
            }
        }
        if (this == LMS_DEFENDER_I) {
            return ArkPlugins.WARZONE_MONEY.getArkPlugin().isRunning();
        }
        return true;
    }

    public static List<FactionQuests> getQuestsFromClass(Class<? extends FactionQuest> clazz) {
        return Arrays.stream(FactionQuests.values()).filter(que -> que.getQuest().getClass().isInstance(clazz)).collect(Collectors.toList());
    }

    public static FactionQuests getFactionQuestFromName(String name) {
        String finalName = name = name.toUpperCase();
        return Arrays.stream(FactionQuests.values()).filter(que -> que.name().startsWith(finalName)).findFirst().orElse(null);
    }

    public static FactionQuests getFactionQuestFromNameExact(String name) {
        return Arrays.stream(FactionQuests.values()).filter(que -> que.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    private FactionQuests(QuestType type, FactionQuest quest) {
        this.type = type;
        this.quest = quest;
    }

    public QuestType getType() {
        return this.type;
    }

    public FactionQuest getQuest() {
        return this.quest;
    }
}

