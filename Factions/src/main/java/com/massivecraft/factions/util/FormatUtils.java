package com.massivecraft.factions.util;

import org.bukkit.entity.*;
import com.compilex.CompilexPermissions.*;
import com.cosmicpvp.cosmicutils.compat.*;
import com.massivecraft.factions.struct.*;
import com.cosmicpvp.titles.*;
import org.bukkit.*;
import com.compilex.CompilexPermissions.structure.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.*;
import com.cosmicpvp.cosmicutils.utils.*;
import java.util.*;

public class FormatUtils
{
    private static HashMap<String, String> playerTeamIds;

    public static String getPlayerPrefix(final Player player) {
        String prefix = "";
        try {
            if (P.getP().getPrefixType() == PrefixType.RANK || player.hasPermission("arkkit.staff.hidden")) {
                prefix = PermissionsAPI.getPermissionUser(player.getUniqueId()).getPrefix() + " ";
            }
            else if (P.getP().getPrefixType() == PrefixType.FACTION) {
                final Faction fac = FactionUtils.getFaction(player);
                if (fac != null && (boolean)FactionUtils.callFactionMethod(fac, "isNormal", null)) {
                    prefix = (String)FactionUtils.callFactionMethod(fac, "getTag", null);
                }
                else {
                    prefix = getRankColorEnding(player);
                }
            }
        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
            return null;
        }
        if (!TextHelper.isEmpty(prefix) && prefix.length() > 2) {
            if (prefix.contains("<") && prefix.contains(">")) {
                prefix = prefix.substring(prefix.indexOf("<") + 1, prefix.indexOf(">"));
            }
            prefix = TextHelper.shrink(ChatColor.translateAlternateColorCodes('&', prefix).replace(ChatColor.ITALIC.toString(), "").replace(ChatColor.STRIKETHROUGH.toString(), "").replace(ChatColor.BOLD.toString(), "").replace(ChatColor.MAGIC.toString(), ""), 13);
            prefix = removeTrailingColors(prefix);
        }
        return prefix;
    }

    public static String beautifyPrefix(String s) {
        if (s == null) {
            return null;
        }
        s = optimizeTitle(s);
        if ((!s.endsWith(ChatColor.RESET.toString()) && s.length() > 13) || (s.endsWith(ChatColor.RESET.toString()) && s.length() > 15)) {
            s = s.substring(0, s.endsWith(ChatColor.RESET.toString()) ? 15 : 12);
        }
        if (s.endsWith("&") || s.endsWith("�")) {
            s = s.substring(0, s.length() - 1);
        }
        if (!s.endsWith(" " + ChatColor.RESET.toString()) && !s.endsWith(ChatColor.RESET.toString() + " ")) {
            if (s.endsWith(" ")) {
                s += ChatColor.RESET;
            }
            else {
                s = s + ChatColor.RESET + " ";
            }
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Deprecated
    public static String beautifySuffix(String s) {
        if (s == null) {
            return null;
        }
        s = optimizeTitle(s);
        if (!s.startsWith(" ") && s.length() > 13) {
            s = " &f" + s.substring(0, 13);
        }
        if (s.endsWith("&")) {
            s = " " + s.substring(0, s.length() - 1);
        }
        if (!s.startsWith(" ")) {
            s = " &f" + s;
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String beautifySuffix(String s, final String chatColor) {
        if (s == null) {
            return null;
        }
        s = optimizeTitle(s);
        if (!s.startsWith(" ") && s.length() >= 13) {
            s = " " + chatColor + s.substring(0, 13);
        }
        if (s.endsWith("&")) {
            s = " " + s.substring(0, s.length() - 1);
        }
        if (!s.startsWith(" ")) {
            s = " " + chatColor + s;
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String optimizeTitle(final String title) {
        String optimizedTitle = "";
        String lastcolor = "";
        for (final String s : splitTitleAtColorCodes(title)) {
            if (isColorCode(s)) {
                if (!lastcolor.equalsIgnoreCase(s)) {
                    optimizedTitle += s.toLowerCase();
                }
                lastcolor = s;
            }
            else {
                optimizedTitle += s;
            }
        }
        boolean next_char_is_uppercase = false;
        for (final String s2 : optimizedTitle.split("~u")) {
            if (s2 == "~u") {
                next_char_is_uppercase = true;
            }
            else if (next_char_is_uppercase) {
                optimizedTitle = optimizedTitle + Character.toUpperCase(s2.charAt(0)) + optimizedTitle.substring(1);
            }
        }
        return optimizedTitle;
    }

    public static String trimPrefix(String prefix) {
        if (prefix.length() > 13) {
            prefix = prefix.substring(0, 13);
        }
        return prefix;
    }

    public static String removeTrailingColors(String prefix) {
        prefix = prefix.trim();
        if (prefix.endsWith("�")) {
            prefix = prefix.substring(0, prefix.length() - 1).trim();
        }
        if (prefix.indexOf(167) != -1 && prefix.lastIndexOf(167) == prefix.length() - 2) {
            prefix = prefix.trim().substring(0, prefix.lastIndexOf(167));
            return removeTrailingColors(prefix);
        }
        return prefix;
    }

    public static String getFactionEnding(final Faction faction, final Faction fac) {
        ChatColor factionRelationColor = ChatColor.RESET;
        if (isNormal(faction) && isNormal(fac)) {
            Relation rel = getRelationTo(faction, fac);
            if (rel == Relation.NEUTRAL) {
                rel = Relation.ENEMY;
            }
            factionRelationColor = rel.getColor();
        }
        return factionRelationColor.toString();
    }

    public static String getEquippedTitle(final UUID uuid, final boolean checkHCF) {
        return (PlayerTitleAPI.getEquippedTitle(uuid));
    }

    public static String getEquippedTitle(final UUID uuid) {
        return PlayerTitleAPI.getEquippedTitle(uuid);
    }

    public static String getEquippedTitle(final Player ply) {
        return PlayerTitleAPI.getEquippedTitle(ply.getUniqueId());
    }

    public static String getRankColorEnding(final Player p) {
        final PermissionUser user = PermissionsAPI.getPermissionUser(p.getUniqueId());
        if (user == null) {
            return ChatColor.GRAY.toString();
        }
        String prefix = user.getPrefix();
        if (prefix != null) {
            prefix = prefix.replace("&f&l<", "");
        }
        if (prefix.length() > 2) {
            prefix = prefix.substring(0, 2);
        }
        else {
            Bukkit.getLogger().info("Busted Ranking for " + p.getName() + " : " + prefix);
        }
        String rankColor = ChatColor.translateAlternateColorCodes('&', prefix);
        rankColor = rankColor.replace("4", "e").replace("5", "e");
        rankColor = rankColor.replace("7", "f");
        return rankColor;
    }

    public static Relation getRelation(RelationParticipator part, RelationParticipator part2) {
        if (part instanceof FPlayer) {
            part = (RelationParticipator)((FPlayer)part).getFaction();
        }
        if (part2 instanceof FPlayer) {
            part2 = (RelationParticipator)((FPlayer)part2).getFaction();
        }
        if (part instanceof Faction && !FactionUtils.isNormal((Faction)part)) {
            return Relation.ENEMY;
        }
        if (part2 instanceof Faction && !FactionUtils.isNormal((Faction)part2)) {
            return Relation.ENEMY;
        }
        return FactionUtils.getRelationTo(part, part2);
    }

    public static String getArcherPrefix() {
        return ChatColor.AQUA + ChatColor.MAGIC.toString() + "!!!!!!" + CC.Aqua + " ";
    }

    public static String getArcherSuffix() {
        return " " + ChatColor.AQUA + ChatColor.MAGIC.toString() + "!!!!!!";
    }

    public static String getFocusedPlayerPrefix() {
        return ChatColor.YELLOW.toString() + ChatColor.MAGIC + "*******";
    }

    public static String getFocusedPlayerSuffix() {
        return ChatColor.YELLOW.toString() + ChatColor.MAGIC + "*******";
    }

    public static String cleanupPrefix(String tag) {
        if (tag == null) {
            return tag;
        }
        if (tag.contains("<") && tag.contains(">")) {
            tag = tag.substring(tag.indexOf("<") + 1, tag.indexOf(">"));
        }
        tag = TextHelper.shrink(ChatColor.translateAlternateColorCodes('&', tag).replace(ChatColor.ITALIC.toString(), "").replace(ChatColor.STRIKETHROUGH.toString(), "").replace(ChatColor.BOLD.toString(), "").replace(ChatColor.MAGIC.toString(), ""), 13);
        if (tag.length() > 16) {
            tag = tag.substring(0, 16);
        }
        return tag;
    }

    public static ArrayList<String> splitTitleAtColorCodes(final String title) {
        final ArrayList<String> preretlist = new ArrayList<String>();
        String strbuf = "";
        for (int i = 0; i <= title.length() - 1; ++i) {
            if (title.charAt(i) == '&') {
                if (strbuf != "") {
                    preretlist.add(strbuf);
                }
                strbuf = "";
                if (title.length() > i + 2 && title.substring(i, i + 2) != "") {
                    preretlist.add(title.substring(i, i + 2));
                }
                ++i;
            }
            else {
                strbuf += title.charAt(i);
            }
        }
        if (strbuf != "") {
            preretlist.add(strbuf);
        }
        boolean last_string_was_color = false;
        final ArrayList<String> retlist = new ArrayList<String>();
        String strbf = "";
        for (final String s : preretlist) {
            if (isColorCode(s)) {
                if (last_string_was_color) {
                    strbf += s;
                }
                else {
                    last_string_was_color = true;
                    strbf = s;
                }
            }
            else {
                last_string_was_color = false;
                retlist.add(strbf);
                strbf = "";
                retlist.add(s);
            }
        }
        return retlist;
    }

    public static String removeColorCodes(final String title) {
        final StringBuffer newTitle = new StringBuffer();
        for (final String s : splitTitleAtColorCodes(title)) {
            if (!isColorCode(s)) {
                newTitle.append(s);
            }
        }
        return newTitle.toString();
    }

    public static boolean isColorCode(final String cc) {
        return cc.startsWith("&");
    }

    public static String getFactionPrefixForOtherFaction(final Player player, final Faction faction) {
        final Faction fac = FactionUtils.getFaction(player);
        final boolean hasFac = isNormal(fac);
        if (faction == null || (boolean)FactionUtils.callFactionMethod(faction, "isNone", null) || !(boolean)FactionUtils.callFactionMethod(faction, "isNormal", null)) {
            return cleanupPrefix(hasFac ? ((ChatColor.WHITE.toString()) + FactionUtils.callFactionMethod(fac, "getTag", null)) : null);
        }
        String color = ChatColor.WHITE.toString();
        if (hasFac) {
            final Relation relation = getRelationTo(fac, faction);
            color = relation.getColor().toString();
            if (color == ChatColor.GRAY.toString()) {
                color = ChatColor.WHITE.toString();
            }
            String tag = color + FactionUtils.callFactionMethod(fac, "getTag", null);
            tag = cleanupPrefix(tag);
            return tag;
        }
        return null;
    }

    private static boolean isNormal(final Faction faction) {
        return faction != null && (boolean)FactionUtils.callFactionMethod(faction, "isNormal", (ParamBuilder)null) && !(boolean)FactionUtils.callFactionMethod(faction, "isNone", (ParamBuilder)null);
    }

    private static Relation getRelationTo(final Faction fac, final Faction faction) {
        return (Relation)FactionUtils.callFactionMethod(fac, "getRelationTo", new ParamBuilder((Class)RelationParticipator.class, (Object)faction));
    }

    public static String getFactionColorForOtherFaction(final Player player, final Faction faction) {
        final Faction fac = FactionUtils.getFaction(player);
        if (!isNormal(faction)) {
            return ChatColor.WHITE.toString();
        }
        String color = ChatColor.WHITE.toString();
        if (isNormal(fac)) {
            color = getRelationTo(fac, faction).getColor().toString();
            if (color == ChatColor.GRAY.toString()) {
                color = ChatColor.WHITE.toString();
            }
            return color;
        }
        return ChatColor.WHITE.toString();
    }

    @Deprecated
    public static String getTeamName(final String playerName) {
        final Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            return "z_" + ((playerName.length() > 14) ? playerName.substring(0, 14) : playerName);
        }
        final Faction faction = FactionUtils.getFaction(player);
        if (isNormal(faction)) {
            final String id = generatePlayerTeamId(player);
            final String s = FactionUtils.callFactionMethod(faction, "getTag", (ParamBuilder)null) + "_" + id;
            return (s.length() > 16) ? s.substring(0, 16) : s;
        }
        return "z_" + ((playerName.length() > 14) ? playerName.substring(0, 14) : playerName);
    }

    public static String getTeamName(final Player p) {
        final Player player = p;
        final String playerName = p.getName();
        if (player == null) {
            return "z_" + ((playerName.length() > 14) ? playerName.substring(0, 14) : playerName);
        }
        final Faction faction = FactionUtils.getFaction(player);
        if (isNormal(faction)) {
            final String id = generatePlayerTeamId(player);
            final String s = FactionUtils.callFactionMethod(faction, "getTag", (ParamBuilder)null) + "_" + id;
            return (s.length() > 16) ? s.substring(0, 16) : s;
        }
        return "z_" + ((playerName.length() > 14) ? playerName.substring(0, 14) : playerName);
    }

    @Deprecated
    public static String getTeamDisplayName(final String playerName) {
        final Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            return "z_" + playerName;
        }
        final FPlayer fp = FactionUtils.getFPlayer(player);
        final Faction faction = FactionUtils.getFaction(player);
        if (isNormal(faction)) {
            final String s = FactionUtils.callFPlayerMethod(fp, "getTag") + "_" + player.getName();
            return (s.length() > 32) ? s.substring(0, 32) : s;
        }
        return "z_" + playerName;
    }

    public static String getTeamDisplayName(final Player p) {
        final Player player = p;
        final String playerName = player.getName();
        final FPlayer fp = FactionUtils.getFPlayer(player);
        final Faction faction = FactionUtils.getFaction(player);
        if (isNormal(faction)) {
            final String s = FactionUtils.callFPlayerMethod(fp, "getTag") + "_" + player.getName();
            return (s.length() > 32) ? s.substring(0, 32) : s;
        }
        return "z_" + playerName;
    }

    private static String generatePlayerTeamId(final Player p) {
        if (FormatUtils.playerTeamIds.containsKey(p.getName())) {
            return FormatUtils.playerTeamIds.get(p.getName());
        }
        String uuid;
        for (uuid = UUID.randomUUID().toString().substring(0, 5); FormatUtils.playerTeamIds.containsValue(uuid); uuid = UUID.randomUUID().toString().substring(0, 5)) {}
        FormatUtils.playerTeamIds.put(p.getName(), uuid);
        return uuid;
    }

    static {
        FormatUtils.playerTeamIds = new HashMap<String, String>();
    }
}
