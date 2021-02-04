/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.craftbukkit.libs.com.google.gson.Gson
 *  org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package com.massivecraft.factions;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.structure.PermissionUser;
import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.google.common.reflect.TypeToken;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.util.PrefixType;
import com.massivecraft.factions.util.TextHelper;
import com.massivecraft.factions.zcore.persist.PlayerEntityCollection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FPlayers
        extends PlayerEntityCollection<FPlayer> {
    public static FPlayers i = new FPlayers();
    P p = P.p;

    private FPlayers() {
        super(FPlayer.class, new CopyOnWriteArrayList<>(), new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER), new File(P.p.getDataFolder(), "players.json"), P.p.gson);
        this.setCreative(true);
    }

    public static String getPlayerPrefix(final Player player) {
        String prefix = "";
        try {
             if (P.getP().getPrefixType() == PrefixType.FACTION) {
                final Faction fac = FactionUtils.getFaction(player);
                if (fac != null && (boolean) FactionUtils.callFactionMethod(fac, "isNormal", null)) {
                    prefix = (String) FactionUtils.callFactionMethod(fac, "getTag", null);
                } else {
                    prefix = getRankColorEnding(player);
                }
            }
        } catch (NullPointerException npe) {
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

    private static boolean isNormal(final Faction faction) {
        return faction != null && (boolean)FactionUtils.callFactionMethod(faction, "isNormal", null) && !(boolean)FactionUtils.callFactionMethod(faction, "isNone", (ParamBuilder)null);
    }

    private static Relation getRelationTo(final Faction fac, final Faction faction) {
        return (Relation)FactionUtils.callFactionMethod(fac, "getRelationTo", new ParamBuilder(RelationParticipator.class, (Object)faction));
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

    public static String trimPrefix(String prefix) {
        if (prefix.length() > 13) {
            prefix = prefix.substring(0, 13);
        }
        return prefix;
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

    @Override
    public Type getMapType() {
        return new TypeToken<Map<String, FPlayer>>() {
        }.getType();
    }

    public void clean() {
        P.getP().getServer().getScheduler().runTaskAsynchronously(this.p, () -> {
            for (FPlayer fplayer : FPlayers.this.get()) {
                if (Factions.i.exists(fplayer.getFactionId())) continue;
                FPlayers.this.p.log("Reset faction data (invalid faction) for player " + fplayer.getNameAsync());
                fplayer.resetFactionData();
            }
        });
    }

    public void cleanSync() {
        for (FPlayer fplayer : this.get()) {
            if (Factions.i.exists(fplayer.getFactionId())) continue;
            this.p.log("Reset faction data (invalid faction) for player " + fplayer.getNameAsync());
            fplayer.resetFactionData();
        }
    }

    @Override
    public FPlayer get(String id) {
        FPlayer fp = super.get(id);
        if (fp == null) {
            fp = this.create(id);
        }
        return fp;
    }

}

