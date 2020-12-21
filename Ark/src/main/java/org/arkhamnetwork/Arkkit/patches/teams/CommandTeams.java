/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.teams;

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.utils.TeamAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeams
extends AbstractCommand {
    public CommandTeams(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player pl = (Player)sender;
            if (args.length == 0) {
                pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + this.toCamelCase(label) + " Commands");
                pl.sendMessage((Object)ChatColor.YELLOW + "/" + label + " list");
                pl.sendMessage((Object)ChatColor.YELLOW + "/" + label + " invite <player>");
                pl.sendMessage((Object)ChatColor.YELLOW + "/" + label + " join <team>");
                pl.sendMessage((Object)ChatColor.YELLOW + "/" + label + " quit");
                pl.sendMessage("");
                return true;
            }
            String subcmd = args[0];
            if (subcmd.equalsIgnoreCase("invite")) {
                if (args.length != 2) {
                    pl.sendMessage((Object)ChatColor.RED + "/" + label + " invite <player>");
                    pl.sendMessage((Object)ChatColor.GRAY + "EX) /" + label + " invite notch");
                    return true;
                }
                String team = TeamAPI.getTeam(pl);
                String invitee = args[1];
                Player p_invitee = Bukkit.getPlayer((String)invitee);
                if (p_invitee == null) {
                    pl.sendMessage((Object)ChatColor.RED + invitee + " is not online.");
                    return true;
                }
                if (pl.getName().equalsIgnoreCase(p_invitee.getName())) {
                    pl.sendMessage((Object)ChatColor.RED + "Sadly, you cannot invite yourself to your own party :(.");
                    return true;
                }
                if (TeamAPI.getTeam(p_invitee) != null) {
                    pl.sendMessage((Object)ChatColor.RED + p_invitee.getName() + " is already on " + (Object)ChatColor.BOLD + "Team " + TeamAPI.getTeam(p_invitee) + "!");
                    pl.sendMessage((Object)ChatColor.GRAY + "They need to /" + label + " quit before being invited to a new team.");
                    return true;
                }
                if (team == null) {
                    ChatColor team_color = TeamAPI.getRandomTeamColor();
                    if (team_color == null) {
                        pl.sendMessage((Object)ChatColor.RED + "The maximum team amount has been reached.");
                        return true;
                    }
                    TeamAPI.setTeam(pl, pl.getName());
                    team = TeamAPI.getTeam(pl);
                    pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Team '" + pl.getName() + "' Created..");
                    TeamAPI.setTeamColor(team, team_color);
                }
                pl.sendMessage((Object)ChatColor.YELLOW + "You have invited " + p_invitee.getName() + " to join your team!");
                TeamAPI.invite(p_invitee, pl.getName());
                p_invitee.sendMessage((Object)ChatColor.YELLOW + pl.getName() + " has invited you to join " + (Object)ChatColor.BOLD + "Team " + team + "!");
                p_invitee.sendMessage((Object)ChatColor.GRAY + "Type " + (Object)ChatColor.YELLOW + "/" + label + " join " + team + (Object)ChatColor.GRAY + " to accept.");
                p_invitee.playSound(p_invitee.getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 1.75f);
            } else {
                if (subcmd.equalsIgnoreCase("accept") || subcmd.equalsIgnoreCase("join")) {
                    if (args.length != 2) {
                        pl.sendMessage((Object)ChatColor.RED + "/" + label + " join <team name>");
                        pl.sendMessage((Object)ChatColor.GRAY + "EX) /" + label + " join jeb_");
                        return true;
                    }
                    String team = args[1];
                    if (TeamAPI.getTeam(pl) != null) {
                        pl.sendMessage((Object)ChatColor.RED + "You are already on " + (Object)ChatColor.BOLD + "Team " + TeamAPI.getTeam(pl) + "!");
                        pl.sendMessage((Object)ChatColor.GRAY + "You need to /" + label + " quit before joining a new team.");
                        return true;
                    }
                    if (!TeamAPI.hasInvite(pl, team)) {
                        pl.sendMessage((Object)ChatColor.RED + "You have not been invited to join " + (Object)ChatColor.BOLD + "Team " + team + "!");
                        pl.sendMessage((Object)ChatColor.GRAY + "The owner of the team must " + (Object)ChatColor.RED + "/" + label + " invite " + pl.getName());
                        return true;
                    }
                    ChatColor team_color = TeamAPI.getTeamColor(team);
                    TeamAPI.setTeam(pl, team);
                    TeamAPI.setTeamColor(team, team_color);
                    pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "You have joined Team " + team + "!");
                    for (Player p : TeamAPI.getMembers(team)) {
                        if (p.equals((Object)pl)) continue;
                        p.sendMessage((Object)ChatColor.GREEN + pl.getName() + " has joined your team!");
                    }
                    return true;
                }
                if (subcmd.equalsIgnoreCase("quit") || subcmd.equalsIgnoreCase("leave")) {
                    String team = TeamAPI.getTeam(pl);
                    if (team == null) {
                        pl.sendMessage((Object)ChatColor.RED + "You are not on a team!");
                        return true;
                    }
                    if (team.equalsIgnoreCase(pl.getName()) && TeamAPI.getMembers(team).length > 1) {
                        pl.sendMessage((Object)ChatColor.RED + "As the creator of the team, you cannot leave while other members are still in your team.");
                        return true;
                    }
                    TeamAPI.leaveTeam(pl);
                    for (Player p : TeamAPI.getMembers(team)) {
                        if (p.equals((Object)pl)) continue;
                        p.sendMessage((Object)ChatColor.RED + pl.getName() + " has left your team!");
                    }
                    pl.sendMessage((Object)ChatColor.YELLOW + "You have left " + (Object)ChatColor.BOLD + "Team " + team);
                } else if (subcmd.equalsIgnoreCase("list") || subcmd.equalsIgnoreCase("who")) {
                    String team = TeamAPI.getTeam(pl);
                    if (team == null) {
                        pl.sendMessage((Object)ChatColor.RED + "You are not on a team!");
                        return true;
                    }
                    String member_list = "";
                    pl.sendMessage("");
                    pl.sendMessage((Object)TeamAPI.getTeamColor(team) + ChatColor.BOLD.toString() + "Team " + team + (Object)ChatColor.GRAY + " [" + TeamAPI.getMembers(team).length + " player(s)]");
                    for (Player ply : TeamAPI.getMembers(team)) {
                        member_list = member_list + ply.getName() + ", ";
                    }
                    member_list.trim();
                    pl.sendMessage(member_list);
                }
            }
        }
        return true;
    }

    private String toCamelCase(String s) {
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = part != null && part.trim().length() > 0 ? camelCaseString + this.toProperCase(part) : camelCaseString + part + " ";
        }
        return camelCaseString.contains(" ") ? camelCaseString.substring(0, camelCaseString.length() - 1) : camelCaseString;
    }

    private String toProperCase(String s) {
        String temp = s.trim();
        String spaces = "";
        if (temp.length() != s.length()) {
            char startCharIndex = s.charAt(temp.indexOf(0));
            spaces = s.substring(0, startCharIndex);
        }
        temp = temp.substring(0, 1).toUpperCase() + spaces + temp.substring(1).toLowerCase() + " ";
        return temp;
    }
}

