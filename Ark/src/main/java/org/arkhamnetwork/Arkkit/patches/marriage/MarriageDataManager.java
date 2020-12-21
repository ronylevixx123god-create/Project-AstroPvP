/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.patches.marriage.MA_SQLConnectionThread;
import org.arkhamnetwork.Arkkit.patches.marriage.Marriage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MarriageDataManager
implements Listener {
    public static HashMap<UUID, UUID> couples = new HashMap();
    public static HashMap<UUID, Boolean> requestsenabled = new HashMap();

    public static UUID getPartnerUUID(UUID uuid) {
        if (couples.containsKey(uuid)) {
            return couples.get(uuid);
        }
        if (!couples.containsValue(uuid)) {
            return null;
        }
        for (UUID p : couples.keySet()) {
            if (couples.get(p) != uuid) continue;
            return p;
        }
        return null;
    }

    public static void createMarriage(UUID partner1, UUID partner2) {
        Marriage.sql_query.add("INSERT INTO marriage_couples(player_uuid, partner_uuid) VALUES ('" + partner1.toString() + "','" + partner2.toString() + "') ON DUPLICATE KEY UPDATE partner_uuid='" + partner2.toString() + "'");
        couples.put(partner1, partner2);
    }

    public static void deleteMarriage(UUID partner) {
        if (Bukkit.isPrimaryThread()) {
            Bukkit.getLogger().info("Attempted to run SQL transaction in main thread deleteMarriage()");
            return;
        }
        ResultSet rs = MA_SQLConnectionThread.getResultSet("SELECT partner_uuid FROM marriage_couples WHERE player_uuid='" + partner.toString() + "'");
        String spouse_uuid = null;
        try {
            if (rs.next()) {
                spouse_uuid = rs.getString("partner_uuid");
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (spouse_uuid != null) {
            Marriage.sql_query.add("DELETE FROM `marriage_couples` WHERE `player_uuid`='" + partner.toString() + "' OR `player_uuid`='" + spouse_uuid + "'");
        }
        couples.remove(UUID.fromString(spouse_uuid));
        couples.remove(partner);
    }

    public static boolean canMarry(UUID uuid) {
        if (Bukkit.getPlayer((UUID)uuid) == null) {
            return false;
        }
        return Bukkit.getPlayer((UUID)uuid).hasPermission("marriage.canmarry") || Bukkit.getPlayer((UUID)uuid).hasPermission("arkkit.marriage.canmarry") || Bukkit.getPlayer((UUID)uuid).isOp();
    }

    public static boolean toggleRequests(UUID uuid) {
        if (requestsenabled.get(uuid) == null) {
            requestsenabled.put(uuid, false);
        }
        if (requestsenabled.get(uuid).booleanValue()) {
            requestsenabled.put(uuid, false);
        } else {
            requestsenabled.put(uuid, true);
        }
        return requestsenabled.get(uuid);
    }

    public static boolean requestsEnabled(UUID uuid) {
        return requestsenabled.get(uuid);
    }

    public static void loadPlayerData(UUID uuid) {
        ResultSet rs_couples = MA_SQLConnectionThread.getResultSet("SELECT * FROM `marriage_couples` WHERE `player_uuid`='" + uuid.toString() + "'");
        ResultSet rs_reqenabled = MA_SQLConnectionThread.getResultSet("SELECT `marriageRequestsEnabled` FROM `marriage_settings` WHERE `player_uuid`='" + uuid.toString() + "'");
        try {
            if (rs_couples.next()) {
                couples.put(UUID.fromString(rs_couples.getString("player_uuid")), UUID.fromString(rs_couples.getString("partner_uuid")));
            }
            if (rs_reqenabled.next()) {
                requestsenabled.put(uuid, rs_reqenabled.getBoolean("marriageRequestsEnabled"));
            } else {
                Marriage.sql_query.add("INSERT IGNORE `marriage_settings` VALUES ('" + uuid.toString() + "',true)");
                requestsenabled.put(uuid, true);
            }
            rs_couples.close();
            rs_reqenabled.close();
        }
        catch (SQLException e) {
            Bukkit.getLogger().warning("Failed to load marriage data for " + uuid + "!");
            e.printStackTrace();
        }
    }

    public static void savePlayerData(UUID uuid) {
        if (requestsenabled.containsKey(uuid)) {
            if (requestsenabled.get(uuid).booleanValue()) {
                Marriage.sql_query.add("INSERT INTO `marriage_settings` VALUES ('" + uuid.toString() + "',1) ON DUPLICATE KEY UPDATE `marriageRequestsEnabled`=1");
            } else {
                Marriage.sql_query.add("INSERT INTO `marriage_settings` VALUES ('" + uuid.toString() + "',0) ON DUPLICATE KEY UPDATE `marriageRequestsEnabled`=0");
            }
        }
        requestsenabled.remove(uuid);
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            return;
        }
        try {
            MarriageDataManager.loadPlayerData(event.getUniqueId());
        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
            requestsenabled.put(event.getUniqueId(), true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        MarriageDataManager.savePlayerData(event.getPlayer().getUniqueId());
    }
}

