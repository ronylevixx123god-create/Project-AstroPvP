/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.PermissionsAPI
 *  net.minecraft.util.org.apache.commons.lang3.text.WordUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexPermissions.PermissionsAPI;
import org.apache.commons.lang3.text.WordUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.SeasonalWin;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SeasonalPrefixes
        extends ArkPlugin {
    public static String rootPerm = "cosmicpvp.seasonal.win";

    public SeasonalPrefixes(Plugin p) {
        super(p);
    }

    public static int getSeasonalWins(Player p) {
        if (p.hasMetadata("seasonalWins")) {
            return p.getMetadata("seasonalWins").get(0).asInt();
        }
        int i = 0;
        for (String perm : PermissionsAPI.getPermissionUser(p.getUniqueId()).getPermissionNodes()) {
            if (!perm.startsWith(rootPerm)) continue;
            ++i;
        }
        p.setMetadata("seasonalWins", new FixedMetadataValue(Arkkit.get(), i));
        return i;
    }

    public static String getSeasonalPrefix(Player p) {
        if (p.hasMetadata("seasonalPrefix")) {
            return p.getMetadata("seasonalPrefix").get(0).asString();
        }
        int i = SeasonalPrefixes.getSeasonalWins(p);
        if (i <= 0) {
            return null;
        }
        String roman = "";
        if (i == 1) {
            roman = "&b&lI";
        }
        if (i == 2) {
            roman = "&b&lII";
        }
        if (i == 3) {
            roman = "&b&lIII";
        }
        if (i == 4) {
            roman = "&b&lIV";
        }
        String s = ChatColor.translateAlternateColorCodes('&', "&d&l<" + roman + "&d&l>");
        p.setMetadata("seasonalPrefix", new FixedMetadataValue(Arkkit.get(), s));
        return s;
    }

    public static List<String> getSeasonalPermNodes(Player p) {
        ArrayList<String> perms = new ArrayList<String>();
        for (String perm : PermissionsAPI.getPermissionUser(p.getUniqueId()).getPermissionNodes()) {
            if (!perm.startsWith(rootPerm)) continue;
            perms.add(perm);
        }
        return perms;
    }

    public static List<SeasonalWin> getSeasonalWinDetails(Player p) {
        ArrayList<SeasonalWin> wins = new ArrayList<SeasonalWin>();
        List<String> permNodes = SeasonalPrefixes.getSeasonalPermNodes(p);
        for (String permNode : permNodes) {
            String raw = permNode.replace(rootPerm + ".", "");
            String planet = raw.split("\\.")[0];
            int season = Integer.parseInt(planet.substring(planet.length() - 1));
            planet = planet.substring(0, planet.length() - 1);
            String faction = raw.split("\\.")[1];
            wins.add(new SeasonalWin(p.getName(), WordUtils.capitalize(faction), WordUtils.capitalize(planet), season));
        }
        return wins;
    }

    @Override
    public void enable() {
        this.registerListener(new SeasonalPrefixesListener());
    }

    @Override
    public void disable() {
    }
}

