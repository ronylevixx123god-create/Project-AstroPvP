/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.arkhamnetwork.TeamAPI
 *  com.arkhamnetwork.TeamPlugin
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.utils;

import com.arkhamnetwork.TeamPlugin;
import java.util.ArrayList;
import java.util.HashSet;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class TeamAPI {
    private static HashSet<ChatColor> team_colors = null;

    public static final String getTeam(Player p) {
        return p.hasMetadata("arkkit_team") ? ((MetadataValue)p.getMetadata("arkkit_team").get(0)).asString() : null;
    }

    public static final boolean onTeam(Player p1, Player p2) {
        return TeamAPI.getTeam(p1).equals(TeamAPI.getTeam(p2));
    }

    public static final Player[] getMembers(String t) {
        ArrayList<Player> players = new ArrayList<Player>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (TeamAPI.getTeam(p) == null || t == null || !TeamAPI.getTeam(p).equals(t)) continue;
            players.add(p);
        }
        return players.toArray((T[])new Player[players.size()]);
    }

    public static final void setTeam(Player p, String team) {
        p.removeMetadata("arkkit_team_invite_" + team, (Plugin)Arkkit.get());
        p.setMetadata("arkkit_team", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)team));
    }

    public static final void leaveTeam(Player p) {
        String team = TeamAPI.getTeam(p);
        ChatColor team_color = TeamAPI.getTeamColor(team);
        p.removeMetadata("arkkit_team", (Plugin)Arkkit.get());
        p.removeMetadata("arkkit_team_color", (Plugin)Arkkit.get());
        if (p.hasMetadata("arkkit_rank_prefix")) {
            com.arkhamnetwork.TeamAPI.setGlobalPrefix((Player)p, (String)((MetadataValue)p.getMetadata("arkkit_rank_prefix").get(0)).asString());
            p.removeMetadata("arkkit_rank_prefix", (Plugin)Arkkit.get());
        } else {
            com.arkhamnetwork.TeamAPI.setGlobalPrefix((Player)p, (String)"");
        }
        if (TeamAPI.getMembers(team).length == 0 && team_color != null) {
            team_colors.add(team_color);
        }
    }

    public static final boolean hasInvite(Player p, String team) {
        return p.hasMetadata("arkkit_team_invite_" + team);
    }

    public static final void invite(Player p, String team) {
        p.setMetadata("arkkit_team_invite_" + team, (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
    }

    public static final void uninvite(Player p, String team) {
        p.removeMetadata("arkkit_team_invite_" + team, (Plugin)Arkkit.get());
    }

    public static ChatColor getRandomTeamColor() {
        if (team_colors == null) {
            team_colors = new HashSet();
            for (ChatColor cc : ChatColor.values()) {
                if (cc.isFormat()) continue;
                team_colors.add(cc);
            }
        } else if (team_colors.size() == 0) {
            return null;
        }
        ChatColor cc = team_colors.iterator().next();
        team_colors.remove((Object)cc);
        return cc;
    }

    public static final void setTeamColor(String team, ChatColor cc) {
        for (Player p : TeamAPI.getMembers(team)) {
            p.setMetadata("arkkit_team_color", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)cc));
            if (TeamPlugin.player_prefix_map.containsKey(p.getName())) {
                p.setMetadata("arkkit_rank_prefix", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), TeamPlugin.player_prefix_map.get(p.getName())));
            }
            com.arkhamnetwork.TeamAPI.setGlobalPrefix((Player)p, (String)("&" + cc.getChar() + "&lT"));
        }
    }

    public static final ChatColor getTeamColor(String team) {
        try {
            if (DyeColor.valueOf((String)team) != null) {
                return ColorUtils.getChatColor(DyeColor.valueOf((String)team));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        Player rand_team = TeamAPI.getMembers(team)[0];
        if (rand_team.hasMetadata("arkkit_team_color")) {
            return (ChatColor)((MetadataValue)rand_team.getMetadata("arkkit_team_color").get(0)).value();
        }
        return ChatColor.MAGIC;
    }
}

