/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.LootGenerator
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.struct.LootTable
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.ReflectionUtils
 *  com.cosmicpvp.cosmicutils.utils.TitleItems
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  net.minecraft.util.com.google.common.reflect.TypeToken
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.massivecraft.factions.quests.managers;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.LootGenerator;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.struct.LootTable;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.cosmicpvp.cosmicutils.utils.TitleItems;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import com.massivecraft.factions.manager.QuestMenu;
import com.massivecraft.factions.quests.FactionQuest;
import com.massivecraft.factions.quests.FactionQuests;
import com.massivecraft.factions.quests.QuestData;
import com.massivecraft.factions.quests.WeeklyProgress;
import com.massivecraft.factions.quests.rewards.QuestReward;
import lombok.SneakyThrows;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionQuestManager {
    private File questDataFile;
    private File weeklyProgressFile;
    private File factionCreationTimeFile;
    private final Type questDataToken = new TypeToken<HashMap<FactionQuests, HashMap<String, QuestData>>>(){}.getType();
    private final Type weeklyProgressToken = new TypeToken<HashMap<String, WeeklyProgress>>(){}.getType();
    private final Type factionCreationTimeToken = new TypeToken<HashMap<String, Long>>(){}.getType();
    private HashMap<String, WeeklyProgress> weeklyFactionProgress = new HashMap();
    private final LootTable<QuestReward> rewardLootTable = new LootTable();
    private long lastWeekendResetTime;
    private Map<String, Long> factionCreationTime = new HashMap<String, Long>();

    public int clearFactionData(String factionId, boolean clearWeekly) {
        if (clearWeekly) {
            this.weeklyFactionProgress.remove(factionId);
        }
        this.factionCreationTime.remove(factionId);
        return (int)Arrays.stream(FactionQuests.values()).filter(quest -> quest.getQuest().questDataMap.remove(factionId) != null).count();
    }

    public void giveQuestPrize(FactionQuests quests, int previouslyCompleted, double modifier, Player player, Faction faction) {
        ItemStack points;
        LinkedList<JSONMessage> messages = new LinkedList<JSONMessage>();
        messages.add(this.giveRewardAndCreateMessage(player, faction, modifier));
        if (previouslyCompleted >= 3) {
            messages.add(this.giveRewardAndCreateMessage(player, faction, modifier));
        }
        if (previouslyCompleted >= 5) {
            messages.add(this.giveRewardAndCreateMessage(player, faction, modifier));
        }
        double d = Math.random();
        double d2 = previouslyCompleted >= 6 ? 0.25 : (double)previouslyCompleted * 0.01;
        if (d <= d2) {
            QuestReward reward = this.getRandomBonusItem();
            reward.givePrize(player, faction);
            messages.add(this.addRewardToMessage(reward));
            Bukkit.getLogger().info("[CosmicFactionQuests] Giving " + reward.getDisplayName() + " to " + player.getName() + "!");
        }
        if (previouslyCompleted >= 6 && (points = FactionPointUtils.getFactionPointsItem(faction, player, "f_quest", quests)) != null) {
            messages.add(this.addRewardToMessage(new QuestReward(points, 1.0)));
        }
        for (FPlayer fplayer : faction.getFPlayers()) {
            Player pl = fplayer.bukkitPlayer;
            if (pl == null) continue;
            pl.sendMessage("");
            pl.sendMessage(CC.AquaB + "(!) " + CC.AquaU + player.getName() + CC.LightPurple + " has claimed the " + CC.AquaB + quests.getQuestName() + " " + CC.LightPurpleU + "/f quest" + CC.LightPurple + " reward, they received:");
            messages.forEach(msg -> msg.sendToPlayer(pl));
            pl.sendMessage("");
        }
    }

    private JSONMessage giveRewardAndCreateMessage(Player player, Faction faction, double modifier) {
        QuestReward reward = this.getRandomLoot(modifier, true, 0);
        reward.givePrize(player, faction);
        return this.addRewardToMessage(reward);
    }

    private JSONMessage addRewardToMessage(QuestReward reward) {
        JSONMessage message = new JSONMessage("");
        message.addText(null, CC.AquaB + "  * " + CC.Aqua, ChatColor.AQUA);
        ItemStack received = reward.getRewardItems().get(0);
        message.addItem(received, reward.getDisplayName(), ChatColor.AQUA);
        return message;
    }

    public boolean triggerQuestIfActive(FactionQuests quests, Faction faction, Player player, Object event) {
        if (faction == null) {
            return false;
        }
        WeeklyProgress progress = this.weeklyFactionProgress.get(faction.getId());
        if (progress == null) {
            return false;
        }
        if (progress.hasQuestActive(quests)) {
            quests.getQuest().onQuestEvent(quests, player, faction, event);
            return true;
        }
        return false;
    }

    public List<FactionQuests> getQuestsCurrentlyDoing(Faction faction) {
        ArrayList quests = Lists.newArrayList();
        WeeklyProgress progress = this.weeklyFactionProgress.get(faction.getId());
        if (progress != null) {
            progress.getActiveFactionQuests().values().forEach(inf -> quests.add(inf.getQuest()));
        }
        return quests;
    }

    public List<FactionQuests> getQuestsDoingByClass(Faction faction, Class<? extends FactionQuest> ... clazz) {
        ArrayList quests = Lists.newArrayList();
        WeeklyProgress progress = this.weeklyFactionProgress.get(faction.getId());
        if (progress != null) {
            progress.getActiveFactionQuests().values().stream().filter(questInfo -> {
                for (Class c : clazz) {
                    if (!questInfo.getQuest().getQuest().getClass().isAssignableFrom(c)) continue;
                    return true;
                }
                return false;
            }).forEach(found -> quests.add(found.getQuest()));
        }
        return quests;
    }

    public void loadData(JavaPlugin plugin) throws IOException {
        this.questDataFile = JSONUtils.getOrCreateFile(plugin.getDataFolder(), "questData.json");
        this.weeklyProgressFile = JSONUtils.getOrCreateFile(plugin.getDataFolder(), "weeklyFactionProgress.json");
        this.factionCreationTimeFile = JSONUtils.getOrCreateFile(plugin.getDataFolder(), "factionCreationTime.json");
        HashMap storedQuestData = (HashMap)JSONUtils.fromJson(this.questDataFile, this.questDataToken);
        if (storedQuestData == null) {
            storedQuestData = new HashMap();
        }
        for (FactionQuests quest : FactionQuests.values()) {
            HashMap found = (HashMap)storedQuestData.get(quest);
            if (found == null) continue;
            quest.getQuest().questDataMap = found;
            Bukkit.getLogger().info("Loaded " + found.size() + " factions quest data info for " + quest.name());
        }
        this.weeklyFactionProgress = (HashMap)JSONUtils.fromJson(this.weeklyProgressFile, this.weeklyProgressToken);
        if (this.weeklyFactionProgress == null) {
            this.weeklyFactionProgress = new HashMap();
        }
        this.factionCreationTime = (Map)JSONUtils.fromJson(this.factionCreationTimeFile, this.factionCreationTimeToken);
        if (this.factionCreationTime == null) {
            this.factionCreationTime = new HashMap<String, Long>();
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this::loadLoot, 100L);
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> CosmicGUI.activeGUIs.values().stream().filter(gui -> gui.getClass().equals(QuestMenu.class)).forEach(CosmicGUI::redraw), 20L, 20L);
    }

    public Calendar createCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("EST"));
        return cal;
    }

    public void doWeeklyReset() {
        Calendar now = this.createCalendar();
        this.weeklyFactionProgress.forEach((key, progress) -> {
            Faction fac = FactionUtils.getFactionById(key);
            progress.resetWeeklyQuests(fac, now);
        });
        for (FactionQuests quests : FactionQuests.values()) {
            quests.getQuest().questDataMap.clear();
        }
        Bukkit.getLogger().info("[CosmicFactionQuests] Resetting all faction progress due to doWeeklyReset()!");
    }

    @SneakyThrows
    private void loadLoot() {
        HashMap godlyLoot = (HashMap)ReflectionUtils.getObject(LootGenerator.class, null, "godExtras");
        if (godlyLoot != null) {
            godlyLoot.forEach((item, chance) -> this.rewardLootTable.addItem(new QuestReward((ItemStack)item, (double)chance), ((double)chance*2)));
            Bukkit.getLogger().info("Loaded " + godlyLoot.size() + " loot items from the godlyExtras map in CosmicSpaceChests!");
        }
    }

    public ItemStack createQuestRerolltem(boolean applyIID) {
        ItemStack item = new ItemBuilder(Material.PAPER, CC.GreenB + "Faction Quest Re-roll " + CC.Gray + "(Right Click)", "", CC.Gray + "Re-roll a selected daily Faction Quest.").build();
        if (applyIID && ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            item = ItemID.assignItemID(null, item);
        }
        return new NBTWrapper(item).setString("cosmicType", "F_QUEST_REROLL").build();
    }

    public QuestReward getRandomBonusItem() {
        ItemStack retr;
        double random = Math.random();
        if (random <= 0.05) {
            retr = TitleItems.getTitleItem("QuestMaster");
        } else if (random <= 0.25) {
            retr = CosmicConquestAPI.createHeroicToken(2 + ThreadLocalRandom.current().nextInt(5));
        } else if (random < 0.56) {
            retr = ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), ThreadLocalRandom.current().nextInt(2) + 1);
        } else if (random < 0.75) {
            retr = EvolutionKitItems.getKitItem(EvolutionKits.values()[ThreadLocalRandom.current().nextInt(EvolutionKits.values().length)]);
        } else {
            ArmorSet set = ArmorSet.values()[ThreadLocalRandom.current().nextInt(ArmorSet.values().length)];
            retr = set.armorSetItems[ThreadLocalRandom.current().nextInt(set.armorSetItems.length)];
        }
        return new QuestReward(retr, 0.001);
    }

    public QuestReward getRandomLoot(double modifier, boolean reshuffle, int attempt) {
        if (Math.random() <= 0.2 || reshuffle) {
            Collections.shuffle(this.rewardLootTable.getLoot());
        }
        for (QuestReward item : this.rewardLootTable.getLoot()) {
            double chance = item.getWeight();
            if (!(Math.random() * modifier <= chance)) continue;
            return item;
        }
        if (attempt >= 5000) {
            Bukkit.getLogger().info("[CosmicFactionquests] Returning NULL for getRandomGodLoot due to attempt=" + attempt);
            return new QuestReward(new ItemStack(Material.DIRT, 1), 1.0);
        }
        return this.getRandomLoot(modifier, reshuffle, attempt + 1);
    }

    public void saveData() throws IOException {
        HashMap<FactionQuests, HashMap<String, QuestData>> storedQuestData = new HashMap<FactionQuests, HashMap<String, QuestData>>();
        for (FactionQuests quests : FactionQuests.values()) {
            storedQuestData.put(quests, quests.getQuest().getQuestDataMap());
        }
        JSONUtils.saveJSONToFile(this.questDataFile, storedQuestData, this.questDataToken);
        JSONUtils.saveJSONToFile(this.weeklyProgressFile, this.weeklyFactionProgress, this.weeklyProgressToken);
        JSONUtils.saveJSONToFile(this.factionCreationTimeFile, this.factionCreationTime, this.factionCreationTimeToken);
    }

    public HashMap<String, WeeklyProgress> getWeeklyFactionProgress() {
        return this.weeklyFactionProgress;
    }

    public long getLastWeekendResetTime() {
        return this.lastWeekendResetTime;
    }

    public void setLastWeekendResetTime(long lastWeekendResetTime) {
        this.lastWeekendResetTime = lastWeekendResetTime;
    }

    public Map<String, Long> getFactionCreationTime() {
        return this.factionCreationTime;
    }
}

