/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  org.arkhamnetwork.Arkkit.patches.CombatLog
 *  org.bukkit.Bukkit
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.cmd;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import java.util.Arrays;

import com.massivecraft.factions.P;
import com.massivecraft.factions.manager.QuestMenu;
import com.massivecraft.factions.quests.FactionQuests;
import com.massivecraft.factions.quests.QuestInfo;
import com.massivecraft.factions.quests.QuestUpdateTask;
import com.massivecraft.factions.quests.WeeklyProgress;
import com.massivecraft.factions.quests.managers.FactionQuestManager;
import com.massivecraft.factions.quests.quests.IncrementableChallenge;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandFactionQuest
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp() || args.length == 0) {
            if (sender instanceof Player) {
                Player pl = (Player)sender;
                Faction faction = FactionUtils.getFaction((Player)pl, (boolean)false);
                if (faction == null || !faction.isNormal()) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to view Faction Quests!");
                    return true;
                }
                if (CombatLog.inCombat((Player)pl)) {
                    pl.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot use this command while in combat!");
                    return true;
                }
                pl.playSound(pl.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.4f);
                new QuestMenu(pl, false).openGUI(P.getP());
            }
            return true;
        }
        FactionQuestManager manager = P.getP().getFactionQuestManager();
        if (args[0].equalsIgnoreCase("rerollitem")) {
            Player player;
            Player player2 = player = args.length > 1 ? Bukkit.getPlayer((String)args[0]) : (Player)sender;
            if (player == null) {
                sender.sendMessage(CC.Red + "Invalid player given!");
                return true;
            }
            PlayerUtil.giveOrDropProtectedItem((Player)player, (ItemStack)manager.createQuestRerolltem(true), (int)-1);
            return true;
        }
        if (args[0].equalsIgnoreCase("giveloot")) {
            Integer completed = args.length == 2 ? NumberUtils.parseIntOrNull((String)args[1]) : 1;
            if (completed == null || completed < 0) {
                completed = 1;
            }
            manager.giveQuestPrize(FactionQuests.KILL_BLAZE_I, completed, 1.0 - (double)completed.intValue() * 0.065, (Player)sender, FactionUtils.getFaction((Player)((Player)sender)));
            return true;
        }
        if (args[0].equalsIgnoreCase("trigger") || args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("addexact") || args[0].equalsIgnoreCase("increment") || args[0].equalsIgnoreCase("give")) {
            if (args.length == 3 || args.length == 4) {
                WeeklyProgress progress;
                Integer increment;
                Faction found;
                FactionQuests toTrigger;
                FactionQuests factionQuests = toTrigger = args[0].equalsIgnoreCase("addexact") ? FactionQuests.getFactionQuestFromNameExact(args[1]) : FactionQuests.getFactionQuestFromName(args[1]);
                if (toTrigger == null) {
                    sender.sendMessage(CC.Red + "Invalid Quest Enum name given: " + args[1]);
                    sender.sendMessage(CC.Gray + "Valid: " + Arrays.toString((Object[])FactionQuests.values()));
                    return true;
                }
                FPlayer offline = null;
                Player online = Bukkit.getPlayer((String)args[2]);
                Faction faction = found = online != null ? FactionUtils.getFaction((Player)online, (boolean)false) : FactionUtils.getFactionByTag((String)args[2]);
                if (found == null) {
                    offline = FactionUtils.getFPlayerByName((String)args[2]);
                    if (offline != null) {
                        found = offline.getFaction();
                    }
                    if (found == null) {
                        sender.sendMessage(CC.Red + "Unable to find faction with tag or ID or for player: " + args[2]);
                        return true;
                    }
                }
                Integer n = increment = args.length == 4 ? NumberUtils.parseIntOrNull((String)args[3]) : null;
                if (increment == null || increment <= 0) {
                    increment = 1;
                }
                if ((progress = manager.getWeeklyFactionProgress().get(found.getId())) == null || !progress.hasQuestActive(toTrigger)) {
                    if (sender instanceof Player) {
                        sender.sendMessage(CC.Red + found.getTag() + " does not have " + toTrigger.name() + " quest, current: " + (progress == null ? "Null" : Arrays.toString(progress.getActiveFactionQuests().values().toArray(new QuestInfo[0]))));
                    }
                    return true;
                }
                if (progress.getCompletedQuests().contains((Object)toTrigger)) {
                    if (sender instanceof Player) {
                        sender.sendMessage(CC.Red + "Quest already completed by " + found.getTag());
                    }
                    return true;
                }
                if (args.length == 4 && args[3].equalsIgnoreCase("complete")) {
                    toTrigger.getQuest().onComplete(toTrigger, online, found, null);
                    sender.sendMessage(CC.Green + "Force completing quest..");
                    return true;
                }
                if (!(toTrigger.getQuest() instanceof IncrementableChallenge)) {
                    toTrigger.getQuest().onQuestEvent(toTrigger, online, found, null);
                    return true;
                }
                boolean completed = ((IncrementableChallenge)toTrigger.getQuest()).incrementChallenge(toTrigger, online, found, increment.intValue(), null);
                P.getP().log("Incrementing Quest " + toTrigger.name() + " for " + found.getTag() + ", completed: " + completed);
                return true;
            }
        } else {
            if (args[0].equalsIgnoreCase("info")) {
                if (args.length == 2) {
                    Faction fromTag;
                    Player pl = Bukkit.getPlayerExact((String)args[1]);
                    Faction faction = fromTag = pl != null ? FactionUtils.getFaction((Player)pl) : FactionUtils.getFactionByTag((String)args[1]);
                    if (fromTag != null) {
                        WeeklyProgress data = manager.getWeeklyFactionProgress().get(fromTag.getId());
                        if (data == null) {
                            sender.sendMessage(CC.Red + "No WeeklyProgress data found for " + fromTag.getTag());
                            return true;
                        }
                        sender.sendMessage(CC.RedB + "Active:");
                        data.getActiveFactionQuests().forEach((day, quest) -> {
                            sender.sendMessage(CC.Red + " " + day + " - " + quest.getQuest().name() + " given: " + quest.getAssigned() + " (" + TimeUtils.formatSeconds((long)((System.currentTimeMillis() - quest.getAssigned()) / 1000L)) + " ago)");
                            sender.sendMessage(CC.Gray + "  Progress: " + quest.getQuest().getQuest().getProgressString(fromTag));
                        });
                        sender.sendMessage(CC.RedB + "Completed: " + CC.Red + Arrays.toString((Object[])data.getCompletedQuests().toArray(new Object[0])));
                        return true;
                    }
                    sender.sendMessage(CC.Red + "Invalid faction from tag!");
                    return true;
                }
                long timeSince = manager.getLastWeekendResetTime();
                sender.sendMessage(CC.RedB + "Last Weekly Reset: " + TimeUtils.formatSeconds((long)(timeSince / 1000L)));
                sender.sendMessage(CC.RedB + "Reset Duration: " + TimeUtils.formatSeconds((long)(QuestUpdateTask.RESET_COOLDOWN / 1000L)));
                sender.sendMessage(CC.RedB + "Reset Day: " + QuestUpdateTask.RESET_DAY);
                sender.sendMessage(CC.RedB + "Reset Hour: " + QuestUpdateTask.RESET_HOUR);
                return true;
            }
            if (args[0].equalsIgnoreCase("assign") || args[0].equalsIgnoreCase("reassign")) {
                if (args.length >= 3) {
                    Faction faction;
                    Player pl = Bukkit.getPlayerExact((String)args[1]);
                    Faction faction2 = faction = pl != null ? FactionUtils.getFaction((Player)pl) : FactionUtils.getFactionByTag((String)args[1]);
                    if (faction == null) {
                        sender.sendMessage(CC.Red + "Unable to find faction from player or tag: " + args[1]);
                        return true;
                    }
                    Integer day2 = NumberUtils.parseIntOrNull((String)args[2]);
                    if (day2 == null || day2 < 1 || day2 > 7) {
                        sender.sendMessage(CC.Red + "Invalid Number entered! Must be (1-7) (Sunday-Saturday)");
                        return true;
                    }
                    FactionQuests found = null;
                    if (args.length == 4 && (found = FactionQuests.getFactionQuestFromName(args[3])) == null) {
                        sender.sendMessage(CC.Red + "Invalid Faction quest entered!");
                        return true;
                    }
                    WeeklyProgress progress = manager.getWeeklyFactionProgress().computeIfAbsent(faction.getId(), e -> new WeeklyProgress());
                    FactionQuests quest2 = progress.assignQuestForNewDay(faction, manager.createCalendar(), day2, found);
                    sender.sendMessage(CC.Red + "Set " + faction.getTag() + "'s Quest on day " + day2 + " to " + quest2.name());
                    return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("resetweekly") || args[0].equalsIgnoreCase("weeklyreset") || args[0].equalsIgnoreCase("resetall")) {
                    manager.doWeeklyReset();
                    sender.sendMessage(CC.Red + "Forced weekly reset, all faction progress is now lost and a quest will given for today.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(CC.RedB + "Quests: ");
                    Arrays.stream(FactionQuests.values()).forEach(quest -> sender.sendMessage(CC.Red + quest.getQuestName() + " - " + CC.Gray + quest.getQuest().getDescription()));
                    return true;
                }
            }
        }
        sender.sendMessage("");
        sender.sendMessage(CC.Red + "/fquest (trigger/add/increment)/addexact <QUEST_NAME> <factionTag/player> [incrementAmount]");
        sender.sendMessage("");
        sender.sendMessage(CC.Red + "/fquest info [factionTag]");
        sender.sendMessage("");
        sender.sendMessage(CC.Red + "/fquest list");
        sender.sendMessage("");
        sender.sendMessage(CC.Red + "/fquest assign [factionTag/player] <DAY(1-7)> [optionalNewQuest]");
        sender.sendMessage("");
        sender.sendMessage(CC.Red + "/fquest resetweekly");
        return false;
    }
}

