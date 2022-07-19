/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CompilexPermissions.manager;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.configuration.PermissionsConfig;
import com.compilex.CompilexPermissions.database.DatabaseConnection;
import com.compilex.CompilexPermissions.logger.PermissionsLogger;
import com.compilex.CompilexPermissions.structure.PermissionsManager;
import com.google.common.collect.HashMultimap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class DatabaseManager
implements PermissionsManager {
    private final String CREATE_USER_GROUPS_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS `player_groups` (`player_uuid` varchar(50) NOT NULL, `group_name` varchar(50) NOT NULL, `server_name` varchar(50) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    private final String CREATE_USER_PERMISSIONS_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS `player_permissions` (`player_uuid` varchar(50) NOT NULL, `permission_node` varchar(50) NOT NULL, `server_name` varchar(50) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    private final String SELECT_ALL_GROUPS_STATEMENT = "SELECT group_name, server_name FROM `player_groups` WHERE `player_uuid`=? && (`server_name`=? || `server_name`='global')";
    private final String SELECT_VIP_GROUPS_STATEMENT = "SELECT group_name FROM `player_groups` WHERE `player_uuid`=? && `server_name`='hub'";
    private static final String SELECT_ALL_GROUPS_ALL_SERVERS_STATEMENT = "SELECT group_name, server_name FROM `player_groups` WHERE `player_uuid`=?";
    private static final String SELECT_ALL_PERMISSIONS_STATEMENT = "SELECT permission_node, server_name FROM `player_permissions` WHERE `player_uuid`=? && (`server_name`=? || `server_name`='global')";
    private static final String SELECT_ALL_PERMISSIONS_ALL_SERVERS_STATEMENT = "SELECT permission_node, server_name FROM `player_permissions` WHERE `player_uuid`=?";
    private final String INSERT_GROUP_IN_SERVER_STATEMENT = "INSERT IGNORE INTO `player_groups` (`player_uuid`, `player_name`, `group_name`, `server_name`) VALUES ('%s', '%s', '%s', '%s')";
    private final String DELETE_GROUP_STATEMENT = "DELETE FROM `player_groups` WHERE player_uuid='%s' AND group_name='%s' AND server_name='%s'";
    private String RESET_GROUP_STATEMENT = "DELETE FROM `player_groups` WHERE player_uuid='%s' AND server_name='%s' AND LOWER(group_name)!='developer' AND LOWER(group_name)!='mod' AND LOWER(group_name)!='helper' AND LOWER(group_name)!='admin' AND LOWER(group_name)!='owner'";
    private final String INSERT_PERMISSION_IN_SERVER_STATEMENT = "INSERT IGNORE INTO `player_permissions` (`player_uuid`, `player_name`, `permission_node`, `server_name`) VALUES ('%s', '%s', '%s', '%s')";
    private final String DELETE_PERMISSION_STATEMENT = "DELETE FROM `player_permissions` WHERE player_uuid='%s' AND permission_node='%s' AND server_name='%s'";
    private final String DELETE_EXTRA_HOMES_PERMISSION_STATEMENT = "DELETE FROM `player_permissions` WHERE player_uuid='%s' AND permission_node like 'essentials.sethome.multiple.%s' AND server_name='%s'";

    private final String SELECT_ALL_GROUPS_STATEMENT_BRY = "SELECT group_name FROM `player_groups` WHERE `player_uuid`=? && `server_name`='factions'";
    private static final String SELECT_ALL_PERMISSIONS_ALL_SERVERS_STATEMENT_BRY = "SELECT permission_node FROM `player_permissions` WHERE `player_uuid`=? && `server_name`='factions'";

    @Override
    public boolean onEnable() {
        PermissionsLogger.logMessage("Attempting to connect to MySQL.");
        try {
            Statement statement = null;
            try {
                statement = DatabaseConnection.getConnection().createStatement();
                statement.execute("CREATE TABLE IF NOT EXISTS `player_groups` (`player_uuid` varchar(50) NOT NULL, `group_name` varchar(50) NOT NULL, `server_name` varchar(50) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
                statement.execute("CREATE TABLE IF NOT EXISTS `player_permissions` (`player_uuid` varchar(50) NOT NULL, `permission_node` varchar(50) NOT NULL, `server_name` varchar(50) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
            }
            finally {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                    statement = null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            PermissionsLogger.logMessage("Connection to MySQL was a failure. Plugin disabling.");
            return false;
        }
        PermissionsLogger.logMessage("Connection to MySQL was a success.");
        this.RESET_GROUP_STATEMENT = this.RESET_GROUP_STATEMENT + "AND group_name != 'moderator' AND group_name != 'srmod' AND group_name != 'streamer' AND group_name != 'youtuber' AND group_name != 'chatmod';";
        return true;
    }

    @Override
    public boolean onDisable() {
        try {
            DatabaseConnection.closeConnection();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return true;
    }

    public void removeExtraEssentialHomePerms(UUID userUUID, String serverName) {
        CompilexPermissions.get().getSqlQueryList().add(String.format("DELETE FROM `player_permissions` WHERE player_uuid='%s' AND permission_node like 'essentials.sethome.multiple.%s' AND server_name='%s'", userUUID.toString(), "%", serverName));
        PermissionsAPI.callPermissionChangeEvent(userUUID);
    }

    public void removePermissionFromUserIfExists(UUID userUUID, String permissionName, String serverName) throws Exception {
        CompilexPermissions.get().getSqlQueryList().add(String.format("DELETE FROM `player_permissions` WHERE player_uuid='%s' AND permission_node='%s' AND server_name='%s'", userUUID.toString(), permissionName, serverName));
        PermissionsAPI.callPermissionChangeEvent(userUUID);
    }

    public void addPermissionToUserIfNotExists(UUID userUUID, String player_name, String permissionName, String serverName) throws Exception {
        CompilexPermissions.get().getSqlQueryList().add(String.format("INSERT IGNORE INTO `player_permissions` (`player_uuid`, `player_name`, `permission_node`, `server_name`) VALUES ('%s', '%s', '%s', '%s')", userUUID.toString(), player_name, permissionName, serverName));
        PermissionsAPI.callPermissionChangeEvent(userUUID);
    }

    @Deprecated
    public void addPermissionToUserIfNotExists(UUID userUUID, String permissionName, String serverName) throws Exception {
        CompilexPermissions.get().getSqlQueryList().add(String.format("INSERT IGNORE INTO `player_permissions` (`player_uuid`, `player_name`, `permission_node`, `server_name`) VALUES ('%s', '%s', '%s', '%s')", userUUID.toString(), "Unknown", permissionName, serverName));
        PermissionsAPI.callPermissionChangeEvent(userUUID);
    }

    public void removeGroupFromUserIfExists(UUID userUUID, String groupName, String serverName) throws Exception {
        CompilexPermissions.get().getSqlQueryList().add(String.format("DELETE FROM `player_groups` WHERE player_uuid='%s' AND group_name='%s' AND server_name='%s'", userUUID.toString(), groupName, serverName));
        PermissionsAPI.callPermissionChangeEvent(userUUID);
    }

    public void addGroupToUserIfNotExists(final UUID userUUID, final String player_name, final String groupName, final String serverName) throws Exception {
        if (Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CompilexPermissions.getInstance(), new Runnable(){

                @Override
                public void run() {
                    if (!(groupName.equalsIgnoreCase("helper") || groupName.equalsIgnoreCase("moderator") || groupName.equalsIgnoreCase("admin") || groupName.equalsIgnoreCase("owner") || groupName.equalsIgnoreCase("youtuber") || groupName.equalsIgnoreCase("streamer") || groupName.equalsIgnoreCase("mod") || groupName.equalsIgnoreCase("srmod") || groupName.equalsIgnoreCase("chatmod"))) {
                        CompilexPermissions.get().getSqlWorker().executeQuery(String.format(DatabaseManager.this.RESET_GROUP_STATEMENT, userUUID.toString(), serverName));
                    }
                    CompilexPermissions.get().getSqlWorker().executeQuery(String.format("INSERT IGNORE INTO `player_groups` (`player_uuid`, `player_name`, `group_name`, `server_name`) VALUES ('%s', '%s', '%s', '%s')", userUUID.toString(), player_name, groupName, serverName));
                    PermissionsAPI.callPermissionChangeEvent(userUUID);
                    PermissionsAPI.callGroupAddEvent(userUUID, groupName);
                }
            });
        } else {
            if (!(groupName.equalsIgnoreCase("helper") || groupName.equalsIgnoreCase("moderator") || groupName.equalsIgnoreCase("admin") || groupName.equalsIgnoreCase("owner") || groupName.equalsIgnoreCase("youtuber"))) {
                CompilexPermissions.get().getSqlWorker().executeQuery(String.format(this.RESET_GROUP_STATEMENT, userUUID.toString(), serverName));
            }
            CompilexPermissions.get().getSqlWorker().executeQuery(String.format("INSERT IGNORE INTO `player_groups` (`player_uuid`, `player_name`, `group_name`, `server_name`) VALUES ('%s', '%s', '%s', '%s')", userUUID.toString(), player_name, groupName, serverName));
            PermissionsAPI.callPermissionChangeEvent(userUUID);
            PermissionsAPI.callGroupAddEvent(userUUID, groupName);
        }
    }

    @Deprecated
    public void addGroupToUserIfNotExists(UUID userUUID, String groupName, String serverName) throws Exception {
        if (!(groupName.equalsIgnoreCase("helper") || groupName.equalsIgnoreCase("moderator") || groupName.equalsIgnoreCase("admin") || groupName.equalsIgnoreCase("owner") || groupName.equalsIgnoreCase("youtuber"))) {
            CompilexPermissions.get().getSqlQueryList().add(String.format(this.RESET_GROUP_STATEMENT, userUUID.toString(), serverName));
        }
        CompilexPermissions.get().getSqlQueryList().add(String.format("INSERT IGNORE INTO `player_groups` (`player_uuid`, `player_name`, `group_name`, `server_name`) VALUES ('%s', '%s', '%s', '%s')", userUUID.toString(), "Unknown", groupName, serverName));
        PermissionsAPI.callPermissionChangeEvent(userUUID);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public HashMultimap<String, String> getAllUserGroups(UUID userUUID) throws Exception {
        HashMultimap groups = HashMultimap.create();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("SELECT group_name, server_name FROM `player_groups` WHERE `player_uuid`=? && (`server_name`=? || `server_name`='global')");
            statement.setString(1, userUUID.toString());
            statement.setString(2, PermissionsConfig.SERVER_NAME.toLowerCase());
            results = statement.executeQuery();
            while (results.next()) {
                if (results.getString("server_name").equalsIgnoreCase("global")) {
                    PermissionsAPI.cacheGlobalRank(userUUID, results.getString("group_name"));
                }
                groups.put((Object)results.getString("group_name"), (Object)results.getString("server_name").toLowerCase());
            }
            HashMultimap hashMultimap = groups;
            return hashMultimap;
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
            if (results != null && !results.isClosed()) {
                results.close();
                results = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getLobbyGroup(UUID userUUID) throws Exception {
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("SELECT group_name FROM `player_groups` WHERE `player_uuid`=? && `server_name`='hub'");
            statement.setString(1, userUUID.toString());
            results = statement.executeQuery();
            if (results.next()) {
                String string = results.getString("group_name");
                return string;
            }
            String string = null;
            return string;
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
            if (results != null && !results.isClosed()) {
                results.close();
                results = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public HashMultimap<String, String> getAllUserGroupsAllServers(UUID userUUID) throws Exception {
        HashMultimap groups = HashMultimap.create();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(SELECT_ALL_GROUPS_ALL_SERVERS_STATEMENT);
            statement.setString(1, userUUID.toString());
            results = statement.executeQuery();
            while (results.next()) {
                groups.put((Object)results.getString("group_name"), (Object)results.getString("server_name").toLowerCase());
            }
            HashMultimap hashMultimap = groups;
            return hashMultimap;
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
            if (results != null && !results.isClosed()) {
                results.close();
                results = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public HashMultimap<String, String> getAllUserPermissions(UUID userUUID) throws Exception {
        HashMultimap permissions = HashMultimap.create();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(SELECT_ALL_PERMISSIONS_STATEMENT);
            statement.setString(1, userUUID.toString());
            statement.setString(2, PermissionsConfig.SERVER_NAME.toLowerCase());
            results = statement.executeQuery();
            while (results.next()) {
                permissions.put((Object)results.getString("permission_node").toLowerCase(), (Object)results.getString("server_name").toLowerCase());
            }
            HashMultimap hashMultimap = permissions;
            return hashMultimap;
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
            if (results != null && !results.isClosed()) {
                results.close();
                results = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public HashMultimap<String, String> getAllUserPermissionsAllServers(UUID userUUID) throws Exception {
        HashMultimap permissions = HashMultimap.create();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(SELECT_ALL_PERMISSIONS_ALL_SERVERS_STATEMENT);
            statement.setString(1, userUUID.toString());
            results = statement.executeQuery();
            while (results.next()) {
                permissions.put((Object)results.getString("permission_node").toLowerCase(), (Object)results.getString("server_name").toLowerCase());
            }
            HashMultimap hashMultimap = permissions;
            return hashMultimap;
        }
        finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
            if (results != null && !results.isClosed()) {
                results.close();
                results = null;
            }
        }
    }

}

