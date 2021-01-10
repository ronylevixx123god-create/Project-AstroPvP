/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.permissions.Permission
 *  org.bukkit.permissions.PermissionDefault
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CompilexPermissions.manager;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.configuration.PermissionsConfig;
import com.compilex.CompilexPermissions.logger.PermissionsLogger;
import com.compilex.CompilexPermissions.manager.CacheManager;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.compilex.CompilexPermissions.structure.PermissionUser;
import com.compilex.CompilexPermissions.structure.PermissionsManager;
import com.google.common.collect.HashMultimap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

public class PermissionManager
implements PermissionsManager {
    private static final CompilexPermissions plugin = CompilexPermissions.getInstance();
    private ConcurrentHashMap<String, PermissionGroup> cachedLoadedGroups = new ConcurrentHashMap();
    private ConcurrentHashMap<UUID, PermissionUser> cachedPermissionUsers = new ConcurrentHashMap();
    private ConcurrentHashMap<UUID, Long> cachedPlayer_timeout = new ConcurrentHashMap();
    private ConcurrentHashMap<UUID, PermissionUser> pendingSetupUsers = new ConcurrentHashMap();

    @Override
    public boolean onEnable() {
        FileConfiguration cachedWebConfiguration = plugin.getConfigurationManager().getCachedWebConfiguration();
        this.cachedLoadedGroups.clear();
        if (cachedWebConfiguration == null) {
            Bukkit.getLogger().info("[CompilexPermissions] cachedWebConfiguration found to be null, shutting server down.");
            Bukkit.shutdown();
            return false;
        }
        boolean loadAll = plugin.getConfig().getBoolean("LOAD_ALL_GROUPS");
        for (String serverName : cachedWebConfiguration.getConfigurationSection("servers").getKeys(false)) {
            if (!PermissionsConfig.SERVER_NAME.toLowerCase().equals(serverName.toLowerCase()) && !loadAll) continue;
            PermissionsLogger.logMessage("Loading groups for server " + serverName + "...");
            for (String groupName : cachedWebConfiguration.getConfigurationSection("servers." + serverName).getKeys(false)) {
                ConfigurationSection groupSection = cachedWebConfiguration.getConfigurationSection("servers." + serverName + "." + groupName);
                String prefix = "";
                if (groupSection.getString("prefix") != null) {
                    prefix = groupSection.getString("prefix");
                }
                String suffix = "";
                if (groupSection.getString("suffix") != null) {
                    suffix = groupSection.getString("suffix");
                }
                ArrayList<String> inherits_Groups = new ArrayList<String>();
                if (groupSection.getStringList("inheritance") != null) {
                    inherits_Groups.addAll(groupSection.getStringList("inheritance"));
                }
                ArrayList<String> permissions = new ArrayList<String>();
                if (groupSection.getShortList("permissions") != null) {
                    permissions.addAll(groupSection.getStringList("permissions"));
                }
                boolean isDefault = groupSection.getBoolean("default");
                if (this.cachedLoadedGroups.containsKey(groupName.toLowerCase())) {
                    Bukkit.getLogger().warning("[CompilexPermissions] Skipping cachedLoadedGroups.put() for " + groupName.toLowerCase() + " from " + serverName + " due to existing key.");
                    continue;
                }
                this.cachedLoadedGroups.put(groupName.toLowerCase(), new PermissionGroup(groupName.toLowerCase(), permissions, inherits_Groups, prefix, suffix, isDefault));
                PermissionsLogger.logMessage("Loaded group " + (Object)ChatColor.GREEN + groupName + (Object)ChatColor.WHITE + " for server " + serverName);
            }
        }
        for (PermissionGroup group : this.cachedLoadedGroups.values()) {
            group.getPermissions().addAll(this.getAllChildPermissionsFromGroup(group));
        }
        return true;
    }

    @Override
    public boolean onDisable() {
        return true;
    }

    public List<String> getValidGroups(List<String> groups) {
        ArrayList<String> valid = new ArrayList<String>();
        for (PermissionGroup defaultGroup : this.cachedLoadedGroups.values()) {
            if (!defaultGroup.isDefault()) continue;
            valid.add(defaultGroup.getName().toLowerCase());
        }
        for (String s : groups) {
            valid.add(s);
        }
        return valid;
    }

    public List<String> getValidPermissions(HashMultimap<String, String> permissions) {
        ArrayList<String> valid = new ArrayList<String>();
        for (Map.Entry permission : permissions.entries()) {
            String serverName = ((String)permission.getValue()).toLowerCase();
            if (!PermissionsConfig.SERVER_NAME.toLowerCase().equals(serverName.toLowerCase()) && !PermissionsConfig.SHARED_PERMISSIONS.contains(serverName) && !serverName.equals("global")) continue;
            valid.add((String)permission.getKey());
        }
        return valid;
    }

    private List<String> getAllChildPermissionsFromGroup(PermissionGroup group) {
        ArrayList<String> childPermissions = new ArrayList<String>();
        for (String childGroup : group.getInherits_Groups()) {
            PermissionGroup cGroup = this.cachedLoadedGroups.get(childGroup.toLowerCase());
            if (cGroup == null) continue;
            childPermissions.addAll(cGroup.getPermissions());
            childPermissions.addAll(this.getAllChildPermissionsFromGroup(cGroup));
        }
        return childPermissions;
    }

    public List<PermissionGroup> translateGroupStringsToGroupList(List<String> groups) {
        ArrayList<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>();
        for (String groupName : groups) {
            PermissionGroup group = this.cachedLoadedGroups.get(groupName.toLowerCase());
            if (group == null) continue;
            permissionGroups.add(group);
        }
        return permissionGroups;
    }

    public void handlePlayerAsyncJoin(UUID playerUUID) {
        this.handlePlayerAsyncJoin(playerUUID, true);
    }

    public void handlePlayerAsyncJoin(UUID playerUUID, boolean checkOnlinePlayer) {
        if (PermissionManager.plugin.getPermissionManager().cachedPermissionUsers.containsKey(playerUUID) && (!checkOnlinePlayer || Bukkit.getPlayer((UUID)playerUUID) != null)) {
            return;
        }
        String uuidString = playerUUID.toString();
        try {
            List<String> pgroups = CacheManager.getCachedPlayerGroups().get(uuidString);
            if (pgroups == null) {
                pgroups = new ArrayList<String>();
                CacheManager.getCachedPlayerGroups().put(uuidString, pgroups);
            }
            List<String> pperms = CacheManager.getCachedPlayerPermissions().get(uuidString);
            if (pperms == null) {
                pperms = new ArrayList<String>();
                CacheManager.getCachedPlayerPermissions().put(uuidString, pperms);
            }
            List<PermissionGroup> groups = plugin.getPermissionManager().translateGroupStringsToGroupList(plugin.getPermissionManager().getValidGroups(pgroups));
            List<String> permissionNodes = pperms;
            this.pendingSetupUsers.put(playerUUID, new PermissionUser(playerUUID, null, groups, permissionNodes));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handlePlayerJoin(Player player) {
        PermissionUser user = this.pendingSetupUsers.get(player.getUniqueId());
        boolean op = false;
        if (user != null) {
            List<Object> list;
            user.setCurrentAttachment(player.addAttachment((Plugin)plugin));
            Permission perm = new Permission(user.getPermissionName(), "InternalPermission", PermissionDefault.FALSE);
            if (user.getGroupsEditable() != null) {
                list = user.getGroupsEditable();
                synchronized (list) {
                    for (PermissionGroup group : user.getGroupsEditable()) {
                        for (String permission : group.getPermissions()) {
                            if (permission.equals("*") || permission.equals("*.*")) {
                                op = true;
                            }
                            if (!permission.startsWith("-")) {
                                perm.getChildren().put(permission, true);
                                continue;
                            }
                            permission = permission.substring(1);
                            if (perm.getChildren().containsKey(permission)) continue;
                            perm.getChildren().put(permission, false);
                        }
                    }
                }
            }
            list = user.getPermissionNodes();
            synchronized (list) {
                for (String permission : user.getPermissionNodes()) {
                    if (permission.equals("*") || permission.equals("*.*")) {
                        op = true;
                    }
                    if (!permission.startsWith("-")) {
                        perm.getChildren().put(permission, true);
                        continue;
                    }
                    permission = permission.substring(1);
                    if (perm.getChildren().containsKey(permission)) continue;
                    perm.getChildren().put(permission, false);
                }
            }
            perm.recalculatePermissibles();
            try {
                plugin.getServer().getPluginManager().addPermission(perm);
            }
            catch (IllegalArgumentException iae) {
                Bukkit.getLogger().info("IllegalArgumentException silenced on CompilexPermissions handleJoin() event for " + player.getName() + "(" + player.getUniqueId().toString() + ")");
            }
            user.getCurrentAttachment().setPermission(perm, true);
            PermissionManager.plugin.getPermissionManager().cachedPermissionUsers.put(player.getUniqueId(), user);
            PermissionManager.plugin.getPermissionManager().cachedPlayer_timeout.put(player.getUniqueId(), System.currentTimeMillis());
        } else {
            player.kickPlayer((Object)ChatColor.RED + "Unable to load your permissions.");
        }
        if (player.isOp() != op) {
            player.setOp(op);
            if (op) {
                Bukkit.getLogger().info("[CompilexPermissions] Player " + player.getName() + " has been OP'd due to *.* permission node.");
            } else {
                Bukkit.getLogger().info("[CompilexPermissions] Player " + player.getName() + " has been DE-OP'd due to no *.* permission node.");
            }
        }
        this.pendingSetupUsers.remove(player.getUniqueId());
    }

    public void handlePlayerLeave(Player player) {
        PermissionUser user = this.cachedPermissionUsers.get(player.getUniqueId());
        if (user == null) {
            return;
        }
        if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null && player.getOpenInventory().getTopInventory().getName().contains("Ender Chest")) {
            player.closeInventory();
            Bukkit.getLogger().info("Closing Ender Chest for " + player.getName() + " to avoid wiping their permissions!");
        }
        plugin.getServer().getPluginManager().removePermission(user.getPermissionName());
        if (user.getCurrentAttachment() != null) {
            player.removeAttachment(user.getCurrentAttachment());
        }
        this.cachedPermissionUsers.remove(player.getUniqueId());
    }

    @Deprecated
    public PermissionUser getUser(UUID playerUUID) {
        return PermissionsAPI.getPermissionUser(playerUUID);
    }

    @Deprecated
    public void reloadPlayer(Player p) {
        PermissionsAPI.reloadPlayerPermissions(p);
    }

    public ConcurrentHashMap<String, PermissionGroup> getCachedLoadedGroups() {
        return this.cachedLoadedGroups;
    }

    public ConcurrentHashMap<UUID, PermissionUser> getCachedPermissionUsers() {
        return this.cachedPermissionUsers;
    }

    public ConcurrentHashMap<UUID, Long> getCachedPlayer_timeout() {
        return this.cachedPlayer_timeout;
    }

    public ConcurrentHashMap<UUID, PermissionUser> getPendingSetupUsers() {
        return this.pendingSetupUsers;
    }
}

