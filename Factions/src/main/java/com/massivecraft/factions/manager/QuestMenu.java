/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  me.ifamasssxd.factionstop.FactionsTop
 *  me.ifamasssxd.factionstop.struct.TopFaction
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.manager;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.quests.FactionQuests;
import com.massivecraft.factions.quests.QuestInfo;
import com.massivecraft.factions.quests.QuestUtil;
import com.massivecraft.factions.quests.WeeklyProgress;
import com.massivecraft.factions.quests.managers.FactionQuestManager;
import com.massivecraft.factions.quests.quests.TimedChallenge;
import com.massivecraft.factions.quests.rewards.QuestReward;
import com.massivecraft.factions.struct.Relation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.factionstop.FactionsTop;
import me.ifamasssxd.factionstop.struct.TopFaction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestMenu
extends CosmicGUI {
    FPlayer fplayer;
    Faction faction;
    private long lastRedraw = 0L;
    private boolean forceRedraw;
    private boolean reroll = false;
    private boolean rerollComplete = false;

    public QuestMenu(Player player, boolean reroll) {
        super(player, "Faction Quests", 18);
        this.fplayer = FactionUtils.getFPlayer((Player)player);
        this.faction = FactionUtils.getFaction((FPlayer)this.fplayer, (boolean)false);
        this.reroll = reroll;
    }

    private String getDayFromWeekIndex(int index) {
        if (index == 1) {
            return "Sunday";
        }
        if (index == 2) {
            return "Monday";
        }
        if (index == 3) {
            return "Tuesday";
        }
        if (index == 4) {
            return "Wednesday";
        }
        if (index == 5) {
            return "Thursday";
        }
        if (index == 6) {
            return "Friday";
        }
        if (index == 7) {
            return "Saturday";
        }
        return "N/A";
    }

    public void redraw() {
        if (this.faction == null || !this.faction.isNormal() || !this.faction.getFPlayers().contains((Object)this.fplayer) || this.faction.getFPlayers().size() <= 0) {
            this.player.closeInventory();
            return;
        }
        if (this.lastRedraw != 0L && this.lastRedraw > System.currentTimeMillis()) {
            if (!this.forceRedraw) {
                return;
            }
            this.forceRedraw = false;
        }
        this.lastRedraw = System.currentTimeMillis() + 500L;
        ItemStack sidePane = new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data((short)DyeColor.GRAY.getWoolData()).build();
        this.setItem(0, new InventoryItem(sidePane));
        this.setItem(8, new InventoryItem(sidePane));
        FactionQuestManager manager = P.getP().getFactionQuestManager();
        int index = 1;
        Long time = manager.getFactionCreationTime().get(this.faction.getId());
        boolean tooYoung = time != null && System.currentTimeMillis() - time <= TimeUnit.HOURS.toMillis(12L);
        Calendar todayCal = Calendar.getInstance();
        int currentDayOfWeek = todayCal.get(7);
        WeeklyProgress progress = manager.getWeeklyFactionProgress().get(this.faction.getId());
        Enchantment duelGlow = null;
        try {
            duelGlow = Enchantment.getById((int)120);
        }
        catch (Exception exception) {
            // empty catch block
        }
        for (int i = 1; i <= 7; ++i) {
            QuestInfo questInfo;
            int dayOfWeek = i;
            boolean isToday = dayOfWeek == currentDayOfWeek;
            int slot = index++;
            ItemStack paperItem = new ItemBuilder(new ItemStack(Material.PAPER)).name(CC.GreenB + this.getDayFromWeekIndex(i)).build();
            if (isToday && duelGlow != null) {
                paperItem.addUnsafeEnchantment(duelGlow, 1);
            }
            this.setItem(slot, new InventoryItem(paperItem));
            int newSlot = slot + 9;
            QuestInfo questInfo2 = questInfo = progress == null ? null : progress.getActiveFactionQuests().get(i);
            if (questInfo == null) {
                this.setItem(newSlot, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getWoolData())).name(CC.RedB + "???").lore((List)(tooYoung && progress == null ? Lists.newArrayList((Object[])new String[]{CC.Gray + "Your faction must be at least", CC.Gray + "12 hours old to receive Faction Quests!"}) : Lists.newArrayList())).build(), () -> {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "New Faction Quests are available once per day!");
                    this.player.sendMessage(CC.Gray + "Each faction quest completed will increase your next faction quest reward!");
                });
                continue;
            }
            FactionQuests found = questInfo.getQuest();
            boolean completed = progress.getCompletedQuests().contains((Object)found);
            boolean claimed = progress.getClaimedCompletedQuests().contains((Object)found);
            ArrayList lore = Lists.newArrayList((Object[])new String[]{CC.Gray + found.getQuest().getDescription(), "", CC.GreenB + "Progress", CC.Green + " " + found.getQuest().getProgressString(this.faction).trim()});
            lore.add("");
            if (completed) {
                lore.add(CC.GreenB + "COMPLETED" + (claimed ? CC.GreenB + " (" + CC.Green + "Claimed" + CC.GreenB + ")" : CC.RedB + " (" + CC.Red + "Unclaimed" + CC.RedB + ")"));
                if (!claimed) {
                    lore.add(CC.Gray + " Click to claim reward!");
                }
            } else {
                boolean added = false;
                if (found.getQuest() instanceof TimedChallenge) {
                    long expiration = ((TimedChallenge)found.getQuest()).getTimeExpires(found, progress);
                    if (expiration <= System.currentTimeMillis()) {
                        lore.add(CC.RedB + "RAN OUT OF TIME");
                        added = true;
                    } else {
                        lore.add(CC.YellowB + "IN PROGRESS");
                        lore.add(CC.Yellow + " Expires in " + CC.YellowB + TimeUtils.formatFutureTime((long)expiration));
                        added = true;
                    }
                }
                if (!added) {
                    lore.add(CC.YellowB + "IN PROGRESS");
                }
            }
            ItemStack item = new ItemBuilder(completed && !claimed ? new ItemStack(Material.CHEST, 1) : new ItemStack(Material.STAINED_GLASS_PANE, 1, completed ? (short)DyeColor.LIME.getWoolData() : (short)DyeColor.YELLOW.getWoolData())).name((completed ? CC.GreenB : CC.YellowB) + found.getQuestName()).lore((List)lore).build();
            if (!claimed && completed) {
                item = QuestUtil.addGlow(item);
            }
            this.setItem(newSlot, item, () -> {
                TopFaction tf;
                boolean noPermissions;
                boolean bl = noPermissions = this.faction.getFPlayers().size() <= 0 || this.faction.getRelationTo((RelationParticipator)this.fplayer) != Relation.MEMBER || !this.fplayer.getRole().isCoLeader();
                if (claimed) {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "Faction Quest Reward already claimed!");
                    return;
                }
                if (this.faction.getFPlayers().size() < 5) {
                    if (!this.player.isOp()) {
                        this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must have at least 4 Faction Members to claim Quest Rewards!");
                        return;
                    }
                    Bukkit.getLogger().info(this.player.getName() + " is op so they can claim it in a small faction!");
                }
                if (this.reroll) {
                    if (completed || progress.getClaimedCompletedQuests().contains((Object)found)) {
                        this.player.sendMessage(CC.RedB + "You cannot re-roll claimed or completed Faction Quests!");
                        return;
                    }
                    this.rerollComplete = true;
                    Calendar cal = Calendar.getInstance();
                    cal.set(7, dayOfWeek);
                    FactionQuests newQuest = progress.assignQuestForNewDay(this.faction, cal);
                    this.player.closeInventory();
                    this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
                    this.player.sendMessage(CC.GreenB + "(!) " + CC.Green + "New Faction Quest Received: " + CC.GreenU + newQuest.getQuestName());
                    return;
                }
                if (Bukkit.getPluginManager().isPluginEnabled("FactionsTop") && ((tf = FactionsTop.get().getTopManager().getTopFaction(this.faction.getId())) == null || tf.getRank() > 50)) {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction must be in the Top 50 " + CC.RedU + "/f top" + CC.Red + " to claim Quest Rewards!");
                    return;
                }
                if (noPermissions) {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be at least Co-Leader to claim this Faction Quest Reward!");
                    return;
                }
                if (completed && !progress.getClaimedCompletedQuests().contains((Object)found)) {
                    int previouslyCompleted = progress.getClaimedCompletedQuests().size();
                    progress.getClaimedCompletedQuests().add(found);
                    double modifier = 1.0 - (double)previouslyCompleted * 0.065;
                    manager.giveQuestPrize(questInfo.getQuest(), previouslyCompleted, modifier, this.player, this.faction);
                    this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
                    this.forceRedraw = true;
                    this.redraw();
                    return;
                }
            });
        }
        this.setItem(this.size - 1, new InventoryItem(sidePane));
        this.setItem(this.size - 9, new InventoryItem(sidePane));
    }

    public void onInventoryClose() {
        if (this.reroll && !this.rerollComplete) {
            this.rerollComplete = true;
            PlayerUtil.giveOrDropProtectedItem((Player)this.player, (ItemStack)P.getP().getFactionQuestManager().createQuestRerolltem(true), (int)-1);
            this.player.updateInventory();
        }
        super.onInventoryClose();
    }

    private JSONMessage giveRewardAndCreateMessage(double modifier, FactionQuestManager manager) {
        QuestReward reward = manager.getRandomLoot(modifier, true, 0);
        reward.givePrize(this.player, this.faction);
        return this.addRewardToMessage(reward);
    }

    private JSONMessage addRewardToMessage(QuestReward reward) {
        JSONMessage message = new JSONMessage("");
        message.addText(null, CC.AquaB + "  * " + CC.Aqua, ChatColor.AQUA);
        ItemStack received = reward.getRewardItems().get(0);
        message.addItem(received, reward.getDisplayName(), ChatColor.AQUA);
        return message;
    }
}

