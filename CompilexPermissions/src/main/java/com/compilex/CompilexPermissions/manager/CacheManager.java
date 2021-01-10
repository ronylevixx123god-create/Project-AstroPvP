/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  org.bukkit.Bukkit
 */
package com.compilex.CompilexPermissions.manager;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.configuration.PermissionsConfig;
import com.compilex.CompilexPermissions.database.DatabaseConnection;
import com.google.common.base.Charsets;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CacheManager {
    private static volatile HashMap<String, List<String>> cachedPlayerGroups;
    private static volatile HashMap<String, List<String>> cachedPlayerPermissions;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean cachePlayerPermissions() {
        cachedPlayerPermissions = new HashMap();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM player_permissions WHERE server_name='" + PermissionsConfig.SERVER_NAME.toLowerCase() + "' || server_name='global'");
            results = statement.executeQuery();
            while (results.next()) {
                try {
                    String userUUID = results.getString("player_uuid");
                    String username = results.getString("player_name");
                    String permissionNode = results.getString("permission_node");
                    /*if (userUUID != null && username != null) {
                        UUIDFetcher.cacheUUID(username, UUID.fromString(userUUID));
                    }*/
                    if (cachedPlayerPermissions.containsKey(userUUID)) {
                        List<String> ls = cachedPlayerPermissions.get(userUUID);
                        if (!ls.contains(results.getString("permission_node"))) {
                            ls.add(results.getString("permission_node"));
                        }
                        cachedPlayerPermissions.put(userUUID, ls);
                        continue;
                    }
                    cachedPlayerPermissions.put(userUUID, new ArrayList<String>(Arrays.asList(results.getString("permission_node"))));
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }
        catch (Exception err) {
            err.printStackTrace();
            boolean bl = false;
            return bl;
        }
        finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                    statement = null;
                }
                if (results != null && !results.isClosed()) {
                    results.close();
                    results = null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getLogger().info("Cached " + cachedPlayerPermissions.size() + " player permission nodes!");
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean cachePlayerGroups() {
        cachedPlayerGroups = new HashMap();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM player_groups WHERE server_name='" + PermissionsConfig.SERVER_NAME.toLowerCase() + "' || server_name='global'");
            results = statement.executeQuery();
            while (results.next()) {
                try {
                    String userUUID = results.getString("player_uuid");
                    String username = results.getString("player_name");
                    String groupName = results.getString("group_name");
                    /*if (userUUID != null && username != null) {
                        UUIDFetcher.cacheUUID(username, UUID.fromString(userUUID));
                    }*/
                    if (results.getString("server_name").equalsIgnoreCase("global")) {
                        PermissionsAPI.cacheGlobalRank(UUID.fromString(userUUID), results.getString("group_name"));
                    }
                    if (cachedPlayerGroups.containsKey(userUUID)) {
                        List<String> ls = cachedPlayerGroups.get(userUUID);
                        if (!ls.contains(results.getString("group_name"))) {
                            ls.add(results.getString("group_name"));
                        }
                        cachedPlayerGroups.put(userUUID, ls);
                        continue;
                    }
                    cachedPlayerGroups.put(userUUID, new ArrayList<String>(Arrays.asList(results.getString("group_name"))));
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }
        catch (Exception err) {
            err.printStackTrace();
            boolean bl = false;
            return bl;
        }
        finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                    statement = null;
                }
                if (results != null && !results.isClosed()) {
                    results.close();
                    results = null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getLogger().info("Cached " + cachedPlayerGroups.size() + " player's permission groups!");
        return true;
    }

    public static void cacheNewPlayerGroup(String playerUUID, String newGroup) {
        List<String> currentCache = currentCache = cachedPlayerGroups.containsKey(playerUUID) ? cachedPlayerGroups.get(playerUUID) : new ArrayList();
        if (!currentCache.contains(newGroup)) {
            currentCache.add(newGroup);
            cachedPlayerGroups.put(playerUUID, currentCache);
        }
    }

    public static void uncachePlayerGroup(String playerUUID, String oldGroup) {
        List<String> currentCache = currentCache = cachedPlayerGroups.containsKey(playerUUID) ? cachedPlayerGroups.get(playerUUID) : new ArrayList();
        if (currentCache.contains(oldGroup)) {
            currentCache.remove(oldGroup);
            cachedPlayerGroups.put(playerUUID, currentCache);
        }
    }

    public static UUID getSpoofedUUIDFromName(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
    }

    public static HashMap<String, List<String>> getCachedPlayerGroups() {
        return cachedPlayerGroups;
    }

    public static HashMap<String, List<String>> getCachedPlayerPermissions() {
        return cachedPlayerPermissions;
    }
}

