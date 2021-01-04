/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  org.apache.commons.lang.StringUtils
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.quests.rewards;

import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestReward {
    private List<ItemStack> rewardItems;
    private List<String> commandsToRunOnLeader;
    private double weight;
    private String displayName;

    public QuestReward(ItemStack item, double weight) {
        this.weight = weight;
        this.rewardItems = Lists.newArrayList(item);
        String string = this.displayName = item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords((String)item.getType().name().replace("_", " ").toLowerCase());
        if (item.getAmount() > 1) {
            this.displayName = item.getAmount() + "x " + this.displayName;
        }
    }

    public void givePrize(Player player, Faction faction) {
        if (this.rewardItems != null) {
            this.rewardItems.forEach(item -> {
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    long id = ItemID.getItemID((ItemStack)item);
                    ItemStack clone = item.clone();
                    if (id != -1L) {
                        item = ItemID.removeItemID((ItemStack)clone);
                    }
                    if (item.getAmount() == 1 && ItemID.needsItemID((ItemStack)item)) {
                        item = ItemID.assignItemID(null, (ItemStack)clone);
                    }
                }
                PlayerUtil.giveOrDropProtectedItem((Player)player, (ItemStack)item, (int)-1);
            });
        }
        if (this.commandsToRunOnLeader != null) {
            this.commandsToRunOnLeader.forEach(command -> Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)command.replace("{PLAYER}", player.getName()).replace("{FACTION_ID}", faction != null ? faction.getId() : "")));
        }
    }

    public String toString() {
        return "chance=" + this.weight + ", rewardItems=" + (this.rewardItems != null ? Arrays.toString((Object[])this.rewardItems.toArray(new Object[0])) : "Null") + ", commandsToRunOnLeader=" + (this.commandsToRunOnLeader != null ? Arrays.toString(this.commandsToRunOnLeader.toArray(new String[0])) : "Null");
    }

    public List<ItemStack> getRewardItems() {
        return this.rewardItems;
    }

    public double getWeight() {
        return this.weight;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

