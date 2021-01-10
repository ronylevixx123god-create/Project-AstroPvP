/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CompilexPermissions;

import com.compilex.CompilexPermissions.configuration.PermissionsConfig;
import com.compilex.CompilexPermissions.event.PermissionChangeEvent;
import com.compilex.CompilexPermissions.event.PermissionGroupAddEvent;
import com.compilex.CompilexPermissions.manager.CacheManager;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.compilex.CompilexPermissions.structure.PermissionUser;
import com.compilex.CompilexPermissions.utils.RankItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PermissionsAPI {
    public static String getGlobalRank(UUID userUUID) {
        return CompilexPermissions.get().getGlobalRanks().containsKey(userUUID) ? CompilexPermissions.get().getGlobalRanks().get(userUUID) : null;
    }

    public static void cacheGlobalRank(UUID userUUID, String rankName) {
        CompilexPermissions.get().getGlobalRanks().put(userUUID, rankName);
    }

    public static void callPermissionChangeEvent(UUID uuid) {
        PermissionChangeEvent event = new PermissionChangeEvent(PermissionsAPI.getPermissionUser(uuid));
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled() && CompilexPermissions.syncPermissionChanges && CompilexPermissions.get().getPermissionSyncThread() != null) {
            CompilexPermissions.get().getPermissionSyncThread().uuid_to_reload.add(uuid.toString());
        }
    }

    public static void callGroupAddEvent(UUID uuid, String group) {
        PermissionGroupAddEvent event = new PermissionGroupAddEvent(PermissionsAPI.getPermissionUser(uuid), group);
        Bukkit.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled() && CompilexPermissions.syncPermissionChanges && CompilexPermissions.get().getPermissionSyncThread() != null) {
            CompilexPermissions.get().getPermissionSyncThread().uuid_to_reload.add(uuid.toString());
        }
    }

    public static int getHighestRankID(String playerUUID) {
        int currentGroupID = -1;
        if (CacheManager.getCachedPlayerGroups().containsKey(playerUUID)) {
            for (String group : CacheManager.getCachedPlayerGroups().get(playerUUID)) {
                if (RankItems.getRankID(group) <= currentGroupID) continue;
                currentGroupID = RankItems.getRankID(group);
            }
        }
        return currentGroupID;
    }

    public static int getVIPRankTierFromSQL(UUID playerUUID) {
        try {
            String lobbyGroup = CompilexPermissions.get().getDatabaseManager().getLobbyGroup(playerUUID);
            if (lobbyGroup != null && lobbyGroup.startsWith("VIP")) {
                if (lobbyGroup.length() > 3) {
                    return Integer.parseInt(lobbyGroup.substring(3, 4));
                }
                return 1;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    @Deprecated
    public static void reloadPlayerPermissions(final Player player) {
        if (player.hasMetadata("permissionReload")) {
            Bukkit.getLogger().info("[CompilexPermissions] Skipping reloadPlayer() for " + player.getName() + " due to UUID existing in lock table.");
            return;
        }
        player.setMetadata("permissionReload", (MetadataValue)new FixedMetadataValue((Plugin)CompilexPermissions.get(), (Object)System.currentTimeMillis()));
        CompilexPermissions.get().getPermissionManager().handlePlayerLeave(player);
        CompilexPermissions.get().getServer().getScheduler().runTaskAsynchronously((Plugin)CompilexPermissions.get(), new Runnable(){

            @Override
            public void run() {
                CompilexPermissions.get().getPermissionManager().handlePlayerAsyncJoin(player.getUniqueId());
                CompilexPermissions.get().getServer().getScheduler().runTask((Plugin)CompilexPermissions.get(), new Runnable(){

                    @Override
                    public void run() {
                        CompilexPermissions.get().getPermissionManager().handlePlayerJoin(player);
                        Bukkit.getPluginManager().callEvent((Event)new PermissionChangeEvent(PermissionsAPI.getPermissionUser(player.getUniqueId())));
                        player.removeMetadata("permissionReload", (Plugin)CompilexPermissions.get());
                    }
                });
            }
        });
    }

    public static PermissionUser getPermissionUser(UUID playerUUID) {
        return CompilexPermissions.get().getPermissionManager().getCachedPermissionUsers().get(playerUUID);
    }

    public static void addPlayerToGroup(final String userName, final String groupName, String serverName) {
        try {
            PermissionGroup cachedGroup;
            PermissionUser user;
            if (serverName == null) {
                serverName = PermissionsConfig.SERVER_NAME;
            }
            UUID userUUID = null;
            try {
                userUUID = Bukkit.getPlayer((String)userName) != null ? Bukkit.getPlayer((String)userName).getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
            }
            catch (Exception e) {
                userUUID = Bukkit.getOfflinePlayer((String)userName).getUniqueId();
            }
            if (userUUID == null) {
                userUUID = Bukkit.getOfflinePlayer((String)userName).getUniqueId();
            }
            if ((user = PermissionsAPI.getPermissionUser(userUUID)) != null && (cachedGroup = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(groupName.toLowerCase())) != null) {
                user.getGroupsEditable().add(cachedGroup);
                if (!user.getGroupNames().contains(cachedGroup.getName().toLowerCase())) {
                    user.getGroupNames().add(cachedGroup.getName().toLowerCase());
                }
                for (String permission : cachedGroup.getPermissions()) {
                    CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().put(permission, true);
                }
                CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
            }
            CacheManager.cacheNewPlayerGroup(userUUID.toString(), groupName);
            final UUID f_userUUID = userUUID;
            final String f_serverName = serverName;
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CompilexPermissions.get(), new Runnable(){

                @Override
                public void run() {
                    try {
                        CompilexPermissions.get().getDatabaseManager().addGroupToUserIfNotExists(f_userUUID, userName, groupName, f_serverName);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.WHITE + "[ArkhamPermissions] " + (Object)ChatColor.AQUA + userName + " is now a member of the group " + groupName + " on server " + serverName + ".");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removePlayerFromGroup(String userName, final String groupName, String serverName) {
        try {
            PermissionGroup cachedGroup;
            PermissionUser user;
            if (serverName == null) {
                serverName = PermissionsConfig.SERVER_NAME;
            }
            UUID userUUID = null;
            try {
                userUUID = Bukkit.getPlayer((String)userName) != null ? Bukkit.getPlayer((String)userName).getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
            }
            catch (Exception e) {
                userUUID = Bukkit.getOfflinePlayer((String)userName).getUniqueId();
            }
            if (userUUID == null) {
                userUUID = Bukkit.getOfflinePlayer((String)userName).getUniqueId();
            }
            if ((user = PermissionsAPI.getPermissionUser(userUUID)) != null && (cachedGroup = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(groupName.toLowerCase())) != null) {
                user.getGroupsEditable().remove(cachedGroup);
                user.getGroupNames().remove(cachedGroup.getName().toLowerCase());
                for (String permission : cachedGroup.getPermissions()) {
                    CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().remove(permission);
                }
                CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
            }
            CacheManager.uncachePlayerGroup(userUUID.toString(), groupName);
            final UUID f_userUUID = userUUID;
            final String f_serverName = serverName;
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CompilexPermissions.get(), new Runnable(){

                @Override
                public void run() {
                    try {
                        CompilexPermissions.get().getDatabaseManager().removeGroupFromUserIfExists(f_userUUID, groupName, f_serverName);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.WHITE + "[ArkhamPermissions] " + (Object)ChatColor.AQUA + userName + " is no longer a member of the group " + groupName + " on server " + serverName + ".");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

