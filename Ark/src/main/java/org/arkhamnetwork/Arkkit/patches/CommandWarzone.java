/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  net.minecraft.util.org.apache.commons.lang3.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayer;
import org.apache.commons.lang3.StringUtils;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

final class CommandWarzone
        extends AbstractCommand {
    WarzoneTopManager.TopManager topManager = new WarzoneTopManager.TopManager();

    public CommandWarzone(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0 || args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Warzone Rewards");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GRAY + "Whenever you enter the planet's main " + ChatColor.YELLOW + ChatColor.BOLD + "WARZONE " + ChatColor.GRAY + "(located directly under /spawn) you will start to " + ChatColor.YELLOW + "automatically recieve $ and EXP." + ChatColor.GRAY + " The longer you stay in this PvP-enabled area, and the closer you are to the center of the area (" + ChatColor.YELLOW + WarzoneMoney.warzoneCenter.getBlockX() + "x, " + WarzoneMoney.warzoneCenter.getBlockZ() + "z" + ChatColor.GRAY + "), the more $ and XP you will recieve " + ChatColor.UNDERLINE + "every minute!");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GRAY + "If you manage to reach the " + ChatColor.YELLOW + "#1" + ChatColor.GRAY + " rank on " + ChatColor.YELLOW + "/warzone top" + ChatColor.GRAY + " you will also recieve a 5.0x \"Last Man Standing\" multiplier.");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.YELLOW + "* You need to " + ChatColor.UNDERLINE + "KEEP MOVING" + ChatColor.YELLOW + " and not /fly to stay eligible for the rewards.");
            sender.sendMessage("");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("top")) {
            sender.sendMessage(ChatColor.YELLOW + "Loading top warzone survivors...");
            int page = args.length == 1 ? 1 : (StringUtils.isNumeric(args[1]) ? Integer.parseInt(args[1]) : 1);
            int perPage = 10;
            this.topManager.loadTopFund(TopPlayers -> {
                TopPlayer funder;
                int totalPages = Math.max(1, TopPlayers.size() / perPage);
                int finalPage = page > totalPages ? totalPages : page;
                sender.sendMessage("");
                sender.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Top Warzone Survivors (" + ChatColor.GOLD + ChatColor.BOLD + finalPage + ChatColor.YELLOW + ChatColor.BOLD + "/" + ChatColor.GOLD + ChatColor.BOLD + totalPages + ChatColor.YELLOW + ChatColor.BOLD + ")");
                int startIndex = finalPage * perPage - perPage + 1;
                int totalIndex = startIndex + perPage;
                for (int i = startIndex; i < totalIndex && (funder = TopPlayers.get(i)) != null; ++i) {
                    FPlayer fplayer = funder.getFPlayer();
                    if (fplayer == null) continue;
                    if (i == 1) {
                        sender.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + i + ". " + ChatColor.GOLD + fplayer.getNameAndTagAsync() + ":" + ChatColor.YELLOW + " " + funder.getMinutes() + " " + "minutes(s)" + " " + ChatColor.GOLD.toString() + ChatColor.BOLD + "- LMS 5.0x BONUS");
                        continue;
                    }
                    sender.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + i + ". " + ChatColor.GOLD + fplayer.getNameAndTagAsync() + ":" + ChatColor.YELLOW + " " + funder.getMinutes() + " " + "minutes(s)");
                }
                sender.sendMessage("");
            });
            return true;
        }
        return true;
    }
}

