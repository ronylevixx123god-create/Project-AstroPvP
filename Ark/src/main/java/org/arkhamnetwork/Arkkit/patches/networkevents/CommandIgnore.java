/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ParamBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ReflectionUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  net.minecraft.util.org.apache.commons.lang3.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandIgnore
extends AbstractCommand {
    public CommandIgnore(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("blockf")) {
            if (args.length == 1) {
                Faction ftag = FactionUtils.getFactionByTag((String)args[0]);
                if (ftag == null || !((Boolean)FactionUtils.callFactionMethod((Faction)ftag, (String)"isNormal", null)).booleanValue()) {
                    sender.sendMessage(CC.Red + "You must enter a valid faction to block!");
                    return true;
                }
                Player player = (Player)sender;
                FPlayer fplayers = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                if ((Relation)ReflectionUtils.callReflectionMethod((Object)ftag, RelationParticipator.class, (String)"getRelationTo", (ParamBuilder)new ParamBuilder(RelationParticipator.class, (Object)fplayers)) == Relation.MEMBER) {
                    player.sendMessage(CC.Red + "You cannot block your own faction!");
                    return true;
                }
                List<String> currentlyBlocked = NetworkEvents.ignored_players.get(player.getUniqueId().toString());
                int blocked = 0;
                int unblocked = 0;
                for (FPlayer fplayer : (Set)FactionUtils.callFactionMethod((Faction)ftag, (String)"getFPlayers", null)) {
                    String name = (String)FactionUtils.callFPlayerMethod((FPlayer)fplayer, (String)"getNameAsync");
                    player.performCommand("block " + name);
                    if (currentlyBlocked == null) {
                        currentlyBlocked = NetworkEvents.ignored_players.get(player.getUniqueId().toString());
                    }
                    if (currentlyBlocked != null && currentlyBlocked.contains(name.toLowerCase())) {
                        ++blocked;
                        continue;
                    }
                    ++unblocked;
                }
                if (blocked > 0) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You have blocked " + CC.RedB + blocked + CC.Red + " Members from " + args[0]);
                }
                if (unblocked > 0) {
                    sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have unblocked " + CC.GreenB + unblocked + CC.Green + " Members from " + args[0]);
                }
                return true;
            }
            sender.sendMessage(CC.Red + "Usage: /blockf <factionTag> - Runs /block on all given faction members.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "BLOCK COMMANDS");
            sender.sendMessage((Object)ChatColor.WHITE + "/" + label + " [player]" + (Object)ChatColor.GRAY + " - toggles all interaction from [player]");
            sender.sendMessage((Object)ChatColor.WHITE + "/" + label + " list" + (Object)ChatColor.GRAY + " - display all currently blocked players.");
            sender.sendMessage((Object)ChatColor.WHITE + "/" + label + " clear" + (Object)ChatColor.GRAY + " - clear all currently blocked players.");
            return true;
        }
        String target = args[0];
        if (target.length() > 16 || !target.matches("[A-Za-z0-9_]+")) {
            sender.sendMessage((Object)ChatColor.RED + target + " is not a valid player name.");
            return true;
        }
        if (target.equalsIgnoreCase(sender.getName())) {
            sender.sendMessage((Object)ChatColor.RED + "You cannot ignore yourself.");
            return true;
        }
        if (target.equalsIgnoreCase("ypperin") || target.equalsIgnoreCase("Vaquxine") || target.equalsIgnoreCase("iFamasssxD")) {
            sender.sendMessage((Object)ChatColor.RED + "You cannot ignore that player.");
            return true;
        }
        if ((target.equalsIgnoreCase("clear") || target.equalsIgnoreCase("listclear")) && sender instanceof Player) {
            Player p = (Player)sender;
            NetworkEvents.ignored_players.put(p.getUniqueId().toString(), new ArrayList());
            p.sendMessage("");
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!)" + (Object)ChatColor.YELLOW + " Your /block list has been cleared!");
            p.sendMessage("");
            return true;
        }
        if (target.equalsIgnoreCase("list") && sender instanceof Player) {
            int startIndex;
            int currentPage;
            Player p = (Player)sender;
            if (!NetworkEvents.ignored_players.containsKey(p.getUniqueId().toString()) || NetworkEvents.ignored_players.get(p.getUniqueId().toString()).size() < 1) {
                p.sendMessage("");
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "BLOCK LIST");
                p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Nobody!");
                return true;
            }
            List<String> blockedPlayers = NetworkEvents.ignored_players.get(p.getUniqueId().toString());
            double maxPerPage = 10.0;
            int maxPages = (int)Math.ceil((double)blockedPlayers.size() / maxPerPage);
            int n = currentPage = args.length == 2 && StringUtils.isNumeric((CharSequence)args[1]) ? Integer.parseInt(args[1]) : 0;
            if (currentPage > maxPages) {
                currentPage = maxPages;
            }
            if (currentPage <= 0) {
                currentPage = 1;
            }
            p.sendMessage("");
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "BLOCK LIST (" + (blockedPlayers.size() == 0 ? 0 : currentPage) + " / " + maxPages + ")");
            for (int i = startIndex = (int)((double)currentPage * maxPerPage - maxPerPage); (double)i < (double)startIndex + maxPerPage && blockedPlayers.size() > i; ++i) {
                String bully = blockedPlayers.get(i);
                p.sendMessage((Object)ChatColor.GRAY + bully);
            }
            p.sendMessage("");
            return true;
        }
        IgnoreUtils.toggleIgnoreSlave((Player)sender, target);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> results = new ArrayList<String>();
        if (args.length != 1) {
            return null;
        }
        String s = args[0];
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!pl.getName().toLowerCase().startsWith(s.toLowerCase())) continue;
            results.add(pl.getName());
        }
        return results.size() > 0 ? results : null;
    }
}

