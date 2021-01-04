/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.org.apache.commons.lang3.StringUtils
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.killtracker.commands;

import me.ifamasssxd.killtracker.KillTracker;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.ifamasssxd.killtracker.listeners.Tracker;
import me.ifamasssxd.killtracker.utils.ItemUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandWithdrawSouls
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
        int shards;
        Tracker tracker;
        int kills;
        ItemStack is;
        int amount;
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player)sender;
        if (args.length == 1) {
            if (!StringUtils.isNumeric((CharSequence)args[0])) {
                p.sendMessage((Object)ChatColor.RED + "Please enter a valid number!");
                return true;
            }
            amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                p.sendMessage((Object)ChatColor.RED + "Please enter a valid amount.");
                return true;
            }
            if (p.getItemInHand() == null || !ItemUtils.isTrackable(p.getItemInHand())) {
                if (p.getItemInHand() != null && KillTrackerAPI.isSoulGem(p.getItemInHand())) {
                    if (p.getItemInHand().getAmount() > 1) {
                        p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot split stacked Soul Gems.");
                        return true;
                    }
                    int souls = KillTrackerAPI.getSouls(p.getItemInHand());
                    if (souls <= amount) {
                        p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have enough souls on this item.");
                        p.sendMessage((Object)ChatColor.GRAY + "Item Souls: " + souls);
                        return true;
                    }
                    if (p.getInventory().firstEmpty() == -1) {
                        p.sendMessage((Object)ChatColor.RED + "You do not have enough inventory space to withdraw souls!");
                        return true;
                    }
                    int newSouls = souls - amount;
                    if (newSouls > 0) {
                        KillTrackerAPI.setSouls(p.getItemInHand(), newSouls);
                    } else {
                        p.getInventory().removeItem(new ItemStack[]{p.getItemInHand()});
                        p.updateInventory();
                    }
                    ItemStack newSoulItem = KillTrackerAPI.createSoulShard(amount);
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        long currentID = ItemID.getItemID((ItemStack)p.getItemInHand());
                        if (currentID == -1L) {
                            ItemID.assignItemID(null, (ItemStack)p.getItemInHand());
                        }
                        newSoulItem = ItemID.assignItemID(null, (ItemStack)newSoulItem);
                    }
                    p.getInventory().addItem(new ItemStack[]{newSoulItem});
                    p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Your Soul Gem has been split into two gems successfully.");
                    p.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + amount + " Soul Gem(s)");
                    p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0f, 1.4f);
                    return true;
                }
                p.sendMessage((Object)ChatColor.RED + "You must have a valid Soul Gem in your hand to withdraw souls from.");
                return true;
            }
            is = p.getItemInHand();
            kills = ItemUtils.getTrackedKills(is);
            tracker = Tracker.getTrackerType(is);
            if (kills == -1) {
                p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "This item has no harvested souls.");
                p.sendMessage((Object)ChatColor.GRAY + "Equip this item with a Soul Tracker to capture souls.");
                return true;
            }
            if (tracker == null) {
                p.sendMessage((Object)ChatColor.RED + "There is no valid tracking rarity on this item.");
                return true;
            }
            if (tracker.trackMobKills()) {
                if (amount > kills / 100) {
                    p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have enough mob souls (" + amount * 100 + ") on this item.");
                    p.sendMessage((Object)ChatColor.RED + "Mob Souls: " + (Object)ChatColor.GRAY + kills);
                    p.sendMessage((Object)ChatColor.RED + "Exchange: " + (Object)ChatColor.GRAY + "100 mob souls = 1 player soul");
                    return true;
                }
            } else if (amount > kills) {
                p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have enough player souls on this item.");
                p.sendMessage((Object)ChatColor.RED + "Player Souls: " + (Object)ChatColor.GRAY + kills);
                return true;
            }
            int n = shards = tracker.trackMobKills() ? amount : amount * tracker.getRank() * KillTracker.get().getConfig().getInt("soulMultiplier", 5);
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage((Object)ChatColor.RED + "You do not have enough inventory space for the Soul Gem.");
                return true;
            }
        } else {
            sender.sendMessage("");
            sender.sendMessage((Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + "Soul Commands");
            sender.sendMessage("");
            sender.sendMessage((Object)ChatColor.WHITE + "/withdrawsouls <amount>");
            sender.sendMessage((Object)ChatColor.GRAY + "Withdraw harvested souls from the item in your hand.");
            sender.sendMessage("");
            sender.sendMessage((Object)ChatColor.WHITE + "/splitsouls <amount>");
            sender.sendMessage((Object)ChatColor.GRAY + "Split harvested souls from a Soul Gem in your hand.");
            sender.sendMessage("");
            return true;
        }
        ItemStack shard = KillTrackerAPI.createSoulShard(shards);
        ItemUtils.setTrackedKills(is, tracker, tracker.trackMobKills() ? kills - amount * 100 : kills - amount);
        p.getInventory().addItem(new ItemStack[]{shard});
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0f, 1.9f);
        p.sendMessage((Object)tracker.getColor() + ChatColor.BOLD.toString() + "- " + (tracker.trackMobKills() ? amount * 100 : amount) + " " + tracker.getName() + " Souls");
        p.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + shards + " Soul Gem(s)");
        p.updateInventory();
        return true;
    }

    public int getEmptyInventorySpace(Player p) {
        int num = 0;
        for (ItemStack is : p.getInventory()) {
            if (is != null && is.getType() != Material.AIR) continue;
            ++num;
        }
        return num;
    }
}

