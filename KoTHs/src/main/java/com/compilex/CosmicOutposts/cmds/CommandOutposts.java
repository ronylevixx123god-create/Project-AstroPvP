/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 */
package com.compilex.CosmicOutposts.cmds;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandOutposts
implements CommandExecutor {
    List<String> warpAliases = Arrays.asList("warp", "teleport", "tp");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player)sender;
        if (args.length == 0) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "CosmicPvP:" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + " OUTPOSTS");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Vanilla Outpost: " + (CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapStatus() + ChatColor.GRAY + " (" + ChatColor.GREEN + CosmicOutpostsPlugin.get_().getVanillaOutpost().getControllingFaction() + ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapStatus()) + ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapTask().getCapPercent() * 100.0f + "%]");
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Trainee Outpost: " + (CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapStatus() + ChatColor.GRAY + " (" + ChatColor.GREEN + CosmicOutpostsPlugin.get_().getTraineeOutpost().getControllingFaction() + ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapStatus()) + ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapTask().getCapPercent() * 100.0f + "%]");
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Hero Outpost: " + (CosmicOutpostsPlugin.get_().getHeroOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getHeroOutpost().getCapStatus() + ChatColor.GRAY + " (" + ChatColor.GREEN + CosmicOutpostsPlugin.get_().getHeroOutpost().getControllingFaction() + ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getHeroOutpost().getCapStatus()) + ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getHeroOutpost().getCapTask().getCapPercent() * 100.0f + "%]");
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Cosmonaut Outpost: " + (CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapStatus() + ChatColor.GRAY + " (" + ChatColor.GREEN + CosmicOutpostsPlugin.get_().getCosmonautOutpost().getControllingFaction() + ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapStatus()) + ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapTask().getCapPercent() * 100.0f + "%]");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "* " + ChatColor.GRAY + "Use " + ChatColor.AQUA + "/warp outpost" + ChatColor.GRAY + " to select an outpost to teleport to. Or use " + ChatColor.AQUA + "/outpost help" + ChatColor.GRAY + " for more information.");
            sender.sendMessage("");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("");
                sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "CosmicPvP:" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + " OUTPOSTS");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.GRAY + "** CosmicPvP Outposts are " + ChatColor.UNDERLINE + "24/7 running, capturable PvP zones." + ChatColor.GRAY + " Once your faction controls an Outpost, you will recieve powerful " + ChatColor.UNDERLINE + "global buffs and perks" + ChatColor.GRAY + " for as long as your faction controls it. Use " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "/warp outpost" + ChatColor.GRAY + " to view, assault, or defend the active Outpost campaigns.");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.GRAY + "*** Note that Outposts are special PvP zones. They " + ChatColor.UNDERLINE + "limit the rarity" + ChatColor.GRAY + " of enchantments that can be used within them.");
                return true;
            }
            if (this.warpAliases.contains(args[0].toLowerCase())) {
                p.openInventory(this.getOutpostWarpMenu(p));
            }
        }
        return true;
    }

    private Inventory getOutpostWarpMenu(Player p) {
        Inventory i = Bukkit.createInventory(null, 9, "Astro Outposts");
        Outpost traineeOutpost = CosmicOutpostsPlugin.get_().getTraineeOutpost();
        Outpost heroOutpost = CosmicOutpostsPlugin.get_().getHeroOutpost();
        Outpost cosmonautOutpost = CosmicOutpostsPlugin.get_().getCosmonautOutpost();
        Outpost vanillaOutpost = CosmicOutpostsPlugin.get_().getVanillaOutpost();
        i.setItem(3, ItemUtils.buildItem(Material.CHAINMAIL_CHESTPLATE, (short)0, ChatColor.DARK_GRAY.toString() + ChatColor.UNDERLINE + ChatColor.BOLD + "Trainee Outpost", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "(!) " + ChatColor.WHITE + "Click to " + (CosmicOutpostsAPI.isDefender(p, traineeOutpost) ? ChatColor.DARK_GRAY.toString() + ChatColor.UNDERLINE + "DEFEND" : ChatColor.RED.toString() + ChatColor.UNDERLINE + "ASSAULT") + ChatColor.WHITE + " this outpost!", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Status: " + traineeOutpost.getCapStatus(), ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Attacking: " + ChatColor.WHITE + (traineeOutpost.getCapTask().getAttackingFaction() != null ? traineeOutpost.getCapTask().getAttackingFaction().getTag() : "N/A"), ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + (traineeOutpost.getControllingFaction() != null ? "Controlling: " : "Defending: ") + ChatColor.WHITE + (traineeOutpost.getCapTask().getDefendingFaction() != null ? traineeOutpost.getCapTask().getDefendingFaction().getTag() : "N/A") + ChatColor.GRAY + " [" + ChatColor.WHITE + traineeOutpost.getTimeStringSinceLastCap() + ChatColor.GRAY + "]", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Cap %: " + ChatColor.WHITE + traineeOutpost.getCapTask().getCapPercent() * 100.0f + "%", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Limits: ", CC.DarkGrayB + "* " + CC.White + "No Armor Set Bonuses", ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "* " + ChatColor.WHITE + "<= " + ChatColor.GREEN + ChatColor.UNDERLINE + "Unique" + ChatColor.WHITE + " enchantments", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Rewards: ", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.25x XP", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.25x mcMMO", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "2x Mobs from Spawners", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "+$25k /f money per 5m", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "-35% /alchemist cost", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "Custom Sell Signs", CC.DarkGrayB + "* " + CC.White + "+10% DMG to players in heroic armor", CC.DarkGrayB + "* " + CC.White + "Larger /f map for members"));
        i.setItem(5, ItemUtils.buildItem(Material.IRON_CHESTPLATE, (short)0, ChatColor.AQUA.toString() + ChatColor.UNDERLINE + ChatColor.BOLD + "Hero Outpost", "", ChatColor.AQUA.toString() + ChatColor.BOLD + "(!) " + ChatColor.WHITE + "Click to " + (CosmicOutpostsAPI.isDefender(p, heroOutpost) ? ChatColor.AQUA.toString() + ChatColor.UNDERLINE + "DEFEND" : ChatColor.RED.toString() + ChatColor.UNDERLINE + "ASSAULT") + ChatColor.WHITE + " this outpost!", "", ChatColor.AQUA.toString() + ChatColor.BOLD + "Status: " + heroOutpost.getCapStatus(), ChatColor.AQUA.toString() + ChatColor.BOLD + "Attacking: " + ChatColor.WHITE + (heroOutpost.getCapTask().getAttackingFaction() != null ? heroOutpost.getCapTask().getAttackingFaction().getTag() : "N/A"), ChatColor.AQUA.toString() + ChatColor.BOLD + (heroOutpost.getControllingFaction() != null ? "Controlling: " : "Defending: ") + ChatColor.WHITE + (heroOutpost.getCapTask().getDefendingFaction() != null ? heroOutpost.getCapTask().getDefendingFaction().getTag() : "N/A") + ChatColor.GRAY + " [" + ChatColor.WHITE + heroOutpost.getTimeStringSinceLastCap() + ChatColor.GRAY + "]", ChatColor.AQUA.toString() + ChatColor.BOLD + "Cap %: " + ChatColor.WHITE + heroOutpost.getCapTask().getCapPercent() * 100.0f + "%", "", ChatColor.AQUA.toString() + ChatColor.BOLD + "Limits: ", CC.DarkAquaB + "* " + CC.White + "No Armor Set Bonuses", ChatColor.AQUA + "" + ChatColor.BOLD + "* " + ChatColor.WHITE + "<= " + ChatColor.AQUA + ChatColor.UNDERLINE + "Elite" + ChatColor.WHITE + " enchantments", "", ChatColor.AQUA.toString() + ChatColor.BOLD + "Rewards: ", ChatColor.AQUA.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.5x XP", ChatColor.AQUA.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.5x mcMMO", ChatColor.AQUA.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "+10% Book Success", ChatColor.AQUA.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "-25% Book Destroy", ChatColor.AQUA.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "No XP Exhaustion", ChatColor.AQUA.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "Custom Sell Signs", CC.AquaB + "* " + CC.White + "2x Loot Multiplier in /warp end", CC.AquaB + "* " + CC.White + "2x Faster /stronghold cap time"));
        i.setItem(7, ItemUtils.buildItem(Material.GOLD_CHESTPLATE, (short)0, ChatColor.GOLD.toString() + ChatColor.UNDERLINE + ChatColor.BOLD + "Cosmonaut Outpost", "", ChatColor.GOLD.toString() + ChatColor.BOLD + "(!) " + ChatColor.WHITE + "Click to " + (CosmicOutpostsAPI.isDefender(p, cosmonautOutpost) ? ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "DEFEND" : ChatColor.RED.toString() + ChatColor.UNDERLINE + "ASSAULT") + ChatColor.WHITE + " this outpost!", "", ChatColor.GOLD.toString() + ChatColor.BOLD + "Status: " + cosmonautOutpost.getCapStatus(), ChatColor.GOLD.toString() + ChatColor.BOLD + "Attacking: " + ChatColor.WHITE + (cosmonautOutpost.getCapTask().getAttackingFaction() != null ? cosmonautOutpost.getCapTask().getAttackingFaction().getTag() : "N/A"), ChatColor.GOLD.toString() + ChatColor.BOLD + (cosmonautOutpost.getControllingFaction() != null ? "Controlling: " : "Defending: ") + ChatColor.WHITE + (cosmonautOutpost.getCapTask().getDefendingFaction() != null ? cosmonautOutpost.getCapTask().getDefendingFaction().getTag() : "N/A") + ChatColor.GRAY + " [" + ChatColor.WHITE + cosmonautOutpost.getTimeStringSinceLastCap() + ChatColor.GRAY + "]", ChatColor.GOLD.toString() + ChatColor.BOLD + "Cap %: " + ChatColor.WHITE + cosmonautOutpost.getCapTask().getCapPercent() * 100.0f + "%", "", ChatColor.GOLD.toString() + ChatColor.BOLD + "Limits: ", ChatColor.GOLD + "" + ChatColor.BOLD + "* " + ChatColor.WHITE + "<= " + ChatColor.GOLD + ChatColor.UNDERLINE + "Legendary" + ChatColor.WHITE + " enchantments", "", ChatColor.GOLD.toString() + ChatColor.BOLD + "Rewards: ", ChatColor.GOLD.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "2x XP", ChatColor.GOLD.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "2x mcMMO", ChatColor.GOLD.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "50% Soul Costs", ChatColor.GOLD.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "Soul Demon Spawns", ChatColor.GOLD.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "Soul Reaper Boss", ChatColor.GOLD.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "Custom Sell Signs", CC.GoldB + "* " + CC.White + "Able to use /warp end exit portal in combat", CC.GoldB + "* " + CC.White + "10% chance to negate Holy White Scrolls"));
        ArrayList<String> vanillaLore = Lists.newArrayList("", ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.WHITE + "Click to " + (CosmicOutpostsAPI.isDefender(p, vanillaOutpost) ? ChatColor.RED.toString() + ChatColor.UNDERLINE + "DEFEND" : ChatColor.RED.toString() + ChatColor.UNDERLINE + "ASSAULT") + ChatColor.WHITE + " this outpost!", "", ChatColor.RED.toString() + ChatColor.BOLD + "Status: " + vanillaOutpost.getCapStatus(), ChatColor.RED.toString() + ChatColor.BOLD + "Attacking: " + ChatColor.WHITE + (vanillaOutpost.getCapTask().getAttackingFaction() != null ? vanillaOutpost.getCapTask().getAttackingFaction().getTag() : "N/A"), ChatColor.RED.toString() + ChatColor.BOLD + (vanillaOutpost.getControllingFaction() != null ? "Controlling: " : "Defending: ") + ChatColor.WHITE + (vanillaOutpost.getCapTask().getDefendingFaction() != null ? vanillaOutpost.getCapTask().getDefendingFaction().getTag() : "N/A") + ChatColor.GRAY + " [" + ChatColor.WHITE + vanillaOutpost.getTimeStringSinceLastCap() + ChatColor.GRAY + "]", ChatColor.RED.toString() + ChatColor.BOLD + "Cap %: " + ChatColor.WHITE + vanillaOutpost.getCapTask().getCapPercent() * 100.0f + "%", "", ChatColor.RED.toString() + ChatColor.BOLD + "Limits: ", ChatColor.RED + "" + ChatColor.BOLD + "*" + ChatColor.WHITE + " No Custom Enchantments", ChatColor.RED + "" + ChatColor.BOLD + "*" + ChatColor.WHITE + " No mcMMO", ChatColor.RED + "" + ChatColor.BOLD + "*" + ChatColor.WHITE + " No Golden Apples", CC.RedB + "* " + CC.White + "No Armor Set Bonuses", "", ChatColor.RED.toString() + ChatColor.BOLD + "Rewards: ", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.75x XP", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.75x mcMMO", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "1.5x Mobs from Spawners", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "200% Tinkerer XP Payout", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "+10% Black Scroll Return", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "Custom Monster Spawns", ChatColor.RED.toString() + ChatColor.BOLD + "* " + ChatColor.WHITE + "OP Custom Sell Signs");
        if (FactionPointUtils.isPointsEnabled()) {
            vanillaLore.add(ChatColor.RED + ChatColor.BOLD.toString() + "* " + ChatColor.WHITE + "5x Faction Points per hour");
        }
        i.setItem(1, ItemUtils.buildItem(Material.LEATHER_CHESTPLATE, (short)0, ChatColor.RED.toString() + ChatColor.UNDERLINE + ChatColor.BOLD + "Vanilla Outpost", vanillaLore.toArray(new String[0])));
        for (int x = 0; x < i.getSize(); ++x) {
            if (i.getItem(x) != null && i.getItem(x).getType() != Material.AIR) continue;
            i.setItem(x, ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.GRAY + "" + ChatColor.BOLD + "", null));
        }
        return i;
    }
}

