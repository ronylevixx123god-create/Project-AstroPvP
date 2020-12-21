/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class IgnoreUtils {
    protected static void toggleIgnoreSlave(Player master, String slave) {
        ArrayList<String> ignored;
        List<String> list = ignored = NetworkEvents.ignored_players.containsKey(master.getUniqueId().toString()) ? NetworkEvents.ignored_players.get(master.getUniqueId().toString()) : new ArrayList<String>();
        if (ignored.contains(slave.toLowerCase())) {
            ignored.remove(slave.toLowerCase());
            master.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GREEN + "You have unblocked " + slave + "!");
            master.sendMessage((Object)ChatColor.GRAY + "Use /block if you want to re-ignore them.");
        } else {
            ignored.add(slave.toLowerCase());
            master.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You have blocked " + slave + "!");
            master.sendMessage((Object)ChatColor.GRAY + "Use /unblock if you want to un-ignore them.");
        }
        NetworkEvents.ignored_players.put(master.getUniqueId().toString(), ignored);
    }

    public static boolean isIgnored(Player victim, String jerk) {
        return victim != null && jerk != null && NetworkEvents.ignored_players.containsKey(victim.getUniqueId().toString()) && NetworkEvents.ignored_players.get(victim.getUniqueId().toString()).contains(jerk.toLowerCase());
    }
}

