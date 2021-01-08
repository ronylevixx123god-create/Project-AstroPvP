/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

final class WarzoneMoneyLMSTask
        extends BukkitRunnable {
    private static final DecimalFormat df = new DecimalFormat("#,##0");
    protected static Player pLMS = null;

    WarzoneMoneyLMSTask() {
    }

    private static String insertCommas(double d) {
        return df.format(d);
    }

    private static LinkedHashMap<UUID, Integer> sortByComparator(Map<UUID, Integer> unsortMap, final boolean order) {
        LinkedList<Map.Entry<UUID, Integer>> list = new LinkedList<Map.Entry<UUID, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<UUID, Integer>>() {

            @Override
            public int compare(Map.Entry<UUID, Integer> o1, Map.Entry<UUID, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        LinkedHashMap<UUID, Integer> sortedMap = new LinkedHashMap<UUID, Integer>();
        for (Map.Entry entry : list) {
            sortedMap.put((UUID) entry.getKey(), (Integer) entry.getValue());
        }
        return sortedMap;
    }

    public void run() {
        if (pLMS == null) {
            if (WarzoneMoney.warzoneTimes.size() == 0) {
                return;
            }
            int mostMinutes = 0;
            for (Map.Entry<UUID, Integer> data : WarzoneMoney.warzoneTimes.entrySet()) {
                if (data.getValue() <= mostMinutes || Bukkit.getPlayer(data.getKey()) == null) continue;
                Player p = Bukkit.getPlayer(data.getKey());
                if (!WarzoneMoney.getPlayersInMainWarzone().contains(p)) {
                    Bukkit.getLogger().info("[Arkkit (WarzoneMoney)] Player " + p.getName() + " was found in warzoneTimes map, but is not in warzone!");
                    continue;
                }
                pLMS = p;
                mostMinutes = data.getValue();
            }
            if (pLMS != null) {
                FPlayer fp = FPlayers.i.get(pLMS);
                Bukkit.broadcastMessage(WarzoneMoney.LMS_PREFIX + ChatColor.YELLOW + (!fp.getFaction().isNone() ? fp.getFaction().getTag() + " " : "") + ChatColor.BOLD + pLMS.getName() + WarzoneMoney.WARZONE_LMS.intern() + ", they have survived in the main /spawn warzone for " + ChatColor.GOLD + mostMinutes + " " + "minutes(s)" + ChatColor.YELLOW + " at: " + ChatColor.GOLD + ChatColor.BOLD + pLMS.getLocation().getBlockX() + "x, " + pLMS.getLocation().getBlockY() + "y, " + pLMS.getLocation().getBlockZ() + "z!");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasMetadata("WarzoneLMSTip")) continue;
                    p.sendMessage(ChatColor.GRAY + "They will now recieve a " + ChatColor.UNDERLINE + "5.0x multiplier" + ChatColor.GRAY + " to all Warzone Rewards until killed. Use /warzone top to view other players.");
                    p.setMetadata("WarzoneLMSTip", new FixedMetadataValue(Arkkit.get(), true));
                }
            }
            return;
        }
        if (WarzoneMoney.warzoneTimes.containsKey(pLMS.getUniqueId())) {
            int minutes = WarzoneMoney.warzoneTimes.get(pLMS.getUniqueId());
            if (pLMS.hasMetadata("lastLMSAlert") && pLMS.getMetadata("lastLMSAlert").get(0).asInt() == minutes) {
                return;
            }
            if (minutes % 10 == 0) {
                FPlayer fp = FPlayers.i.get(pLMS);
                Bukkit.broadcastMessage(WarzoneMoney.LMS_PREFIX + ChatColor.YELLOW + (!fp.getFaction().isNone() ? fp.getFaction().getTag() + " " : "") + ChatColor.BOLD + pLMS.getName() + WarzoneMoney.WARZONE_LMS.intern() + ", they have survived in the main /spawn warzone for " + ChatColor.GOLD + minutes + "minutes(s)" + ChatColor.YELLOW + " at: " + ChatColor.GOLD + ChatColor.BOLD + pLMS.getLocation().getBlockX() + "x, " + pLMS.getLocation().getBlockY() + "y, " + pLMS.getLocation().getBlockZ() + "z!");
                if (WarzoneMoney.warzoneMoney.containsKey(pLMS.getUniqueId()) && WarzoneMoney.warzoneXp.containsKey(pLMS.getUniqueId())) {
                    Bukkit.broadcastMessage(WarzoneMoney.LMS_PREFIX + ChatColor.YELLOW + "They have gained " + ChatColor.GOLD + "$" + WarzoneMoneyLMSTask.insertCommas(WarzoneMoney.warzoneMoney.get(pLMS.getUniqueId()).intValue()) + ChatColor.YELLOW + " and " + ChatColor.GOLD + WarzoneMoneyLMSTask.insertCommas(WarzoneMoney.warzoneXp.get(pLMS.getUniqueId()).intValue()) + "XP");
                    pLMS.setMetadata("lastLMSAlert", new FixedMetadataValue(Arkkit.get(), minutes));
                }
            }
        }
    }
}

