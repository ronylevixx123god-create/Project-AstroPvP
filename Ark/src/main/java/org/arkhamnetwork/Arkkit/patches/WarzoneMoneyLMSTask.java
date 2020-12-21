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
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoney;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class WarzoneMoneyLMSTask
extends BukkitRunnable {
    protected static Player pLMS = null;
    private static DecimalFormat df = new DecimalFormat("#,##0");

    WarzoneMoneyLMSTask() {
    }

    public void run() {
        if (pLMS == null) {
            if (WarzoneMoney.warzoneTimes.size() == 0) {
                return;
            }
            int mostMinutes = 0;
            for (Map.Entry<UUID, Integer> data : WarzoneMoney.warzoneTimes.entrySet()) {
                if (data.getValue() <= mostMinutes || Bukkit.getPlayer((UUID)data.getKey()) == null) continue;
                Player p = Bukkit.getPlayer((UUID)data.getKey());
                if (!WarzoneMoney.getPlayersInMainWarzone().contains((Object)p)) {
                    Bukkit.getLogger().info("[Arkkit (WarzoneMoney)] Player " + p.getName() + " was found in warzoneTimes map, but is not in warzone!");
                    continue;
                }
                pLMS = p;
                mostMinutes = data.getValue();
            }
            if (pLMS != null) {
                FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pLMS);
                Bukkit.broadcastMessage((String)(WarzoneMoney.LMS_PREFIX + (Object)ChatColor.YELLOW + (!fp.getFaction().isNone() ? fp.getFaction().getTag() + " " : "") + (Object)ChatColor.BOLD + pLMS.getName() + WarzoneMoney.WARZONE_LMS.intern() + ", they have survived in the main /spawn warzone for " + (Object)ChatColor.GOLD + mostMinutes + " " + "minutes(s)" + (Object)ChatColor.YELLOW + " at: " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + pLMS.getLocation().getBlockX() + "x, " + pLMS.getLocation().getBlockY() + "y, " + pLMS.getLocation().getBlockZ() + "z!"));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasMetadata("WarzoneLMSTip")) continue;
                    p.sendMessage((Object)ChatColor.GRAY + "They will now recieve a " + (Object)ChatColor.UNDERLINE + "5.0x multiplier" + (Object)ChatColor.GRAY + " to all Warzone Rewards until killed. Use /warzone top to view other players.");
                    p.setMetadata("WarzoneLMSTip", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                }
            }
            return;
        }
        if (WarzoneMoney.warzoneTimes.containsKey(pLMS.getUniqueId())) {
            int minutes = WarzoneMoney.warzoneTimes.get(pLMS.getUniqueId());
            if (pLMS.hasMetadata("lastLMSAlert") && ((MetadataValue)pLMS.getMetadata("lastLMSAlert").get(0)).asInt() == minutes) {
                return;
            }
            if (minutes % 10 == 0) {
                FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pLMS);
                Bukkit.broadcastMessage((String)(WarzoneMoney.LMS_PREFIX + (Object)ChatColor.YELLOW + (!fp.getFaction().isNone() ? fp.getFaction().getTag() + " " : "") + (Object)ChatColor.BOLD + pLMS.getName() + WarzoneMoney.WARZONE_LMS.intern() + ", they have survived in the main /spawn warzone for " + (Object)ChatColor.GOLD + minutes + "minutes(s)" + (Object)ChatColor.YELLOW + " at: " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + pLMS.getLocation().getBlockX() + "x, " + pLMS.getLocation().getBlockY() + "y, " + pLMS.getLocation().getBlockZ() + "z!"));
                if (WarzoneMoney.warzoneMoney.containsKey(pLMS.getUniqueId()) && WarzoneMoney.warzoneXp.containsKey(pLMS.getUniqueId())) {
                    Bukkit.broadcastMessage((String)(WarzoneMoney.LMS_PREFIX + (Object)ChatColor.YELLOW + "They have gained " + (Object)ChatColor.GOLD + "$" + WarzoneMoneyLMSTask.insertCommas(WarzoneMoney.warzoneMoney.get(pLMS.getUniqueId()).intValue()) + (Object)ChatColor.YELLOW + " and " + (Object)ChatColor.GOLD + WarzoneMoneyLMSTask.insertCommas(WarzoneMoney.warzoneXp.get(pLMS.getUniqueId()).intValue()) + "XP"));
                    pLMS.setMetadata("lastLMSAlert", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)minutes));
                }
            }
        }
    }

    private static String insertCommas(double d) {
        return df.format(d);
    }

    private static LinkedHashMap<UUID, Integer> sortByComparator(Map<UUID, Integer> unsortMap, final boolean order) {
        LinkedList<Map.Entry<UUID, Integer>> list = new LinkedList<Map.Entry<UUID, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<UUID, Integer>>(){

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
            sortedMap.put((UUID)entry.getKey(), (Integer)entry.getValue());
        }
        return sortedMap;
    }
}

