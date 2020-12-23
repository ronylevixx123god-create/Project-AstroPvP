/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BarAnnouncerParser {
    public static String parseAnnouncement(String announcement, Player player) {
        return BarAnnouncerParser.translateToColorCode(announcement.replace("{PLAYER}", player.getName()));
    }

    public static String translateToColorCode(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}

