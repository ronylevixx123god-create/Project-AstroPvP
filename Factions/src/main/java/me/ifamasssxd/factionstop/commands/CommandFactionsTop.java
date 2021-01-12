/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  net.minecraft.util.org.apache.commons.lang3.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.factionstop.commands;

import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import me.ifamasssxd.factionstop.manager.TopManager;
import me.ifamasssxd.factionstop.struct.TopFaction;
import me.ifamasssxd.factionstop.utils.JSONMessage;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CommandFactionsTop
implements CommandExecutor {
    DecimalFormat format = new DecimalFormat("#,##0");

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player) || !((Player)sender).isValid()) {
            return true;
        }
        TopManager manager = P.getP().getTopManager();
        if (manager.isLoadingData()) {
            sender.sendMessage((Object)ChatColor.RED + "Please wait while we calculate /ftop...");
            return true;
        }
        int page = 1;
        int perPage = 10;
        boolean showWealth = false;
        if (args.length >= 1 && args[0].equalsIgnoreCase("wealth")) {
            showWealth = true;
            if (args.length > 1 && StringUtils.isNumeric((CharSequence)args[1])) {
                page = Math.min(5, Integer.parseInt(args[1]));
            }
            Bukkit.getLogger().info("ShowWealth: " + showWealth);
        } else if (args.length == 1 && StringUtils.isNumeric((CharSequence)args[0])) {
            page = Math.min(5, Integer.parseInt(args[0]));
        }
        if (page < 0) {
            page = 1;
        }
        if (page > 5) {
            page = 5;
        }
        if (System.currentTimeMillis() - (showWealth ? manager.getLastWealthUpdate() : manager.getLastUpdate()) > TimeUnit.MINUTES.toMillis(manager.getRefreshTimer())) {
            sender.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Please wait while we calculate top Factions..");
            manager.loadTopFactions(() -> Bukkit.getScheduler().scheduleSyncDelayedTask(P.getP(), () -> this.onCommand(sender, command, s, args)), 0, showWealth);
            return true;
        }
        int startPosition = (page - 1) * perPage;
        sender.sendMessage("");
        sender.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Top Factions " + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "(" + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + page + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "/" + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "5" + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + ")");
        Player pl = (Player)sender;
        FPlayer fplayer = FPlayers.i.get(pl.getUniqueId().toString());
        Faction current = fplayer.getFaction();
        int place = 0;
        ArrayList topFactions = Lists.newArrayList(showWealth && P.getP().factionPointsEnabled ? manager.getWealthTopFactions().values() : manager.getTopFactions().values());
        for (int i = startPosition; i < startPosition + perPage && place < perPage && i < topFactions.size() && i <= 50; ++i) {
            TopFaction faction = (TopFaction)topFactions.get(i);
            JSONMessage message = new JSONMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + (++place + startPosition) + ". ");
            if (faction.getStoredFaction().getFaction() == null) continue;
            String worth = P.getP().factionPointsEnabled && !showWealth ? (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + this.format.format(faction.getStoredFaction().getFactionPoints()) + (Object)ChatColor.LIGHT_PURPLE + " Faction Points" : ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "$" + this.format.format((long)faction.getStoredFaction().getTotalWorth());
            message.addRunCommand((Object)ChatColor.WHITE + (current != null && current.isNormal() ? faction.getStoredFaction().getFaction().getTag(current) : faction.getStoredFaction().getFaction().getTag()) + (Object)ChatColor.AQUA + " - " + worth, ChatColor.WHITE, "/f who " + faction.getStoredFaction().getFaction().getTag(), faction.getStoredFaction().getHoverData(current));
            message.sendToPlayer(pl);
        }
        sender.sendMessage("");
        return false;
    }
}

