/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.libs.RankItems
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CompilexPermissions.cmd;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.configuration.PermissionsConfig;
import com.compilex.CompilexPermissions.manager.CacheManager;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.compilex.CompilexPermissions.structure.PermissionUser;
import com.compilex.CompilexPermissions.utils.RankItems;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class CmdPermissions
implements CommandExecutor {
    private static final HashMap<String, UUID> cached_uuid = new HashMap();
    private final CompilexPermissions plugin = CompilexPermissions.get();
    private final boolean rankItemLibrary = new File("plugins/CosmicDropPackages.jar").exists() || new File("plugins/CosmicDropPackages_Vanilla.jar").exists();

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        Player p;
        String pName;
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 2 && (args[0].toLowerCase().startsWith("user") || args[0].toLowerCase().startsWith("userinfo") || args[0].toLowerCase().startsWith("player"))) {
            Player p2;
            if (sender instanceof Player && !(p2 = (Player)sender).hasPermission("arkhampermissions.lookup") && !p2.hasPermission("compilexpermissions.lookup")) {
                return true;
            }
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    HashMultimap<String, String> permissions;
                    HashMultimap<String, String> groups;
                    UUID userUUID;
                    String userName = args[1];
                    UUID uUID = userUUID = cached_uuid.containsKey(userName) ? cached_uuid.get(userName) : null;
                    if (userUUID == null) {
                        try {
                            userUUID = Bukkit.getPlayerExact(userName) != null ? Bukkit.getPlayerExact(userName).getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
                        }
                        catch (Exception e) {
                            userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                        }
                        if (userUUID == null) {
                            userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                        }
                        cached_uuid.put(userName, userUUID);
                    } else {
                        Bukkit.getLogger().info("Cached UUID of " + userName + " caught as " + userUUID);
                    }
                    try {
                        groups = CmdPermissions.this.plugin.getDatabaseManager().getAllUserGroupsAllServers(userUUID);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }
                    try {
                        permissions = CmdPermissions.this.plugin.getDatabaseManager().getAllUserPermissionsAllServers(userUUID);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }
                    sender.sendMessage(ChatColor.WHITE + "======== " + ChatColor.YELLOW + userName + ChatColor.WHITE + " ========");
                    sender.sendMessage(ChatColor.WHITE + "Permissions Information:");
                    sender.sendMessage(" ");
                    sender.sendMessage("Permissions:");
                    for (Map.Entry permission : permissions.entries()) {
                        sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + permission.getKey() + ChatColor.GRAY + ": " + ChatColor.AQUA + permission.getValue());
                    }
                    sender.sendMessage(" ");
                    sender.sendMessage(ChatColor.WHITE + "Groups: ");
                    for (Map.Entry permissionGroup : groups.entries()) {
                        sender.sendMessage(ChatColor.GOLD + (String)permissionGroup.getKey() + ChatColor.GRAY + ": " + ChatColor.AQUA + permissionGroup.getValue());
                    }
                    sender.sendMessage(ChatColor.WHITE + "======== " + ChatColor.YELLOW + userName + ChatColor.WHITE + " ========");
                }
            });
            return true;
        }
        if (!(!(sender instanceof Player) || (pName = (p = (Player)sender).getName()).equals("Bryside") || pName.equals("Venir") || pName.equals("MattHawkk"))) {
            p.sendMessage(ChatColor.RED + "You are not authorized to use that command.");
            p.sendMessage(ChatColor.GRAY + "Please contact an administrator.");
            return true;
        }
        if ((args.length == 4 || args.length == 5) && args[2].toLowerCase().startsWith("remove")) {
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    try {
                        PermissionUser user;
                        UUID userUUID;
                        String userName = args[1];
                        final String permissionName = args[3];
                        String serverName = PermissionsConfig.SERVER_NAME;
                        UUID uUID = userUUID = cached_uuid.getOrDefault(userName, null);
                        if (userUUID == null) {
                            try {
                                userUUID = Bukkit.getPlayerExact(userName) != null ? Bukkit.getPlayerExact(userName).getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            catch (Exception e) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            if (userUUID == null) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            cached_uuid.put(userName, userUUID);
                        } else {
                            Bukkit.getLogger().info("Cached UUID of " + userName + " caught as " + userUUID);
                        }
                        if (args.length == 5) {
                            serverName = args[4];
                        }
                        if ((user = CmdPermissions.this.plugin.getPermissionManager().getCachedPermissionUsers().get(userUUID)) != null) {
                            CmdPermissions.this.plugin.getServer().getScheduler().runTask(CmdPermissions.this.plugin, new Runnable(){

                                @Override
                                public void run() {
                                    CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().remove(permissionName);
                                    CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
                                    user.getPermissionNodes().remove(permissionName);
                                }
                            });
                        }
                        CmdPermissions.this.plugin.getDatabaseManager().removePermissionFromUserIfExists(userUUID, permissionName, serverName);

                        ArrayList<String> ls;
                        Bukkit.getLogger().info("[Permissions] Removing " + permissionName + " from " + userUUID + "'s getCachedPlayerPermissions() list.");
                        List<String> list = ls = CacheManager.getCachedPlayerPermissions().containsKey(userUUID.toString()) ? (ArrayList<String>) CacheManager.getCachedPlayerPermissions().get(userUUID.toString()) : new ArrayList();
                        ls.remove(permissionName);
                        CacheManager.getCachedPlayerPermissions().put(userUUID.toString(), ls);
                        sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.AQUA + userName + " no longer has the permission " + permissionName + " on server " + serverName + ".");
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return true;
        }
        if ((args.length == 4 || args.length == 5) && args[2].toLowerCase().startsWith("add")) {
            if (!sender.isOp()) {
                return true;
            }
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    try {
                        String userName = args[1];
                        String permissionName = args[3];
                        String serverName = PermissionsConfig.SERVER_NAME;
                        UUID userUUID = null;
                        if (userName.length() > 16 && userName.contains("-")) {
                            try {
                                userUUID = UUID.fromString(userName);
                                userName = Bukkit.getOfflinePlayer(userName).getName();
                                Bukkit.getLogger().info("[CompilexPermissions] Using given uuid: " + userUUID + " and found name: " + userName + " associated!");
                            }
                            catch (Exception e) {
                                sender.sendMessage(CC.Red + "Invalid uuid given!");
                            }
                        }
                        if (userUUID == null) {
                            userUUID = cached_uuid.get(userName);
                        }
                        if (userUUID == null) {
                            try {
                                Player online = Bukkit.getPlayerExact(userName);
                                userUUID = online != null ? online.getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            catch (Exception e) {
                                Bukkit.getLogger().info("[CompilexPermissions] Unable to retrieve UUID from " + userName + "!");
                                e.printStackTrace();
                            }
                            if (userUUID == null) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                                Bukkit.getLogger().info("Unable to find UUID from player or Mojang, trying .getOfflinePlayer() = " + userUUID);
                            }
                            if (userUUID != null) {
                                cached_uuid.put(userName, userUUID);
                            }
                        } else {
                            Bukkit.getLogger().info("Cached UUID of " + userName + " caught as " + userUUID);
                        }
                        if (args.length == 5) {
                            serverName = args[4];
                        }
                        if (userUUID != null) {
                            PermissionUser user = CmdPermissions.this.plugin.getPermissionManager().getCachedPermissionUsers().get(userUUID);
                            if (user != null) {
                                CmdPermissions.this.plugin.getServer().getScheduler().runTask(CmdPermissions.this.plugin, () -> {
                                    CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().put(permissionName, true);
                                    CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
                                    user.getPermissionNodes().add(permissionName);
                                });
                            }
                            CmdPermissions.this.plugin.getDatabaseManager().addPermissionToUserIfNotExists(userUUID, userName, permissionName, serverName);

                            ArrayList<String> ls;
                            Bukkit.getLogger().info("[Permissions] Adding " + permissionName + " to " + userUUID + "'s getCachedPlayerPermissions() list.");
                            List<String> list = ls = CacheManager.getCachedPlayerPermissions().containsKey(userUUID.toString()) ? (ArrayList<String>) CacheManager.getCachedPlayerPermissions().get(userUUID.toString()) : new ArrayList<String>();
                            if (!ls.contains(permissionName)) {
                                ls.add(permissionName);
                            }
                            CacheManager.getCachedPlayerPermissions().put(userUUID.toString(), ls);
                        }
                        sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.AQUA + userName + " (" + userUUID + ") now has the permission " + permissionName + " on server " + serverName + ".");
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return true;
        }
        if (args.length == 2 && args[0].toLowerCase().equals("flush")) {
            final String pName2 = args[1];
            Bukkit.getScheduler().runTaskAsynchronously(CompilexPermissions.get(), new Runnable(){

                @Override
                public void run() {
                    UUID playerUUID = Bukkit.getOfflinePlayer(pName2).getUniqueId();
                    try {
                        CacheManager.getCachedPlayerGroups().put(playerUUID.toString(), CmdPermissions.this.plugin.getPermissionManager().getValidGroups(new ArrayList<String>(CompilexPermissions.get().getDatabaseManager().getAllUserGroups(playerUUID).keySet())));
                        CacheManager.getCachedPlayerPermissions().put(playerUUID.toString(), new ArrayList(CompilexPermissions.get().getDatabaseManager().getAllUserPermissions(playerUUID).keySet()));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage("Permission group/node cache for " + pName2 + " flushed.");
                }
            });
            return true;
        }
        if ((args.length == 5 || args.length == 6) && args[2].toLowerCase().startsWith("group") && args[3].toLowerCase().startsWith("add")) {
            if (!sender.isOp()) {
                return true;
            }
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    try {
                        PermissionGroup cachedGroup;
                        UUID userUUID;
                        String userName = args[1];
                        String groupName = args[4];
                        String serverName = PermissionsConfig.SERVER_NAME;
                        UUID uUID = userUUID = cached_uuid.containsKey(userName) ? cached_uuid.get(userName) : null;
                        if (userUUID == null) {
                            try {
                                userUUID = Bukkit.getPlayerExact(userName) != null ? Bukkit.getPlayerExact(userName).getUniqueId() :Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            catch (Exception e) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            if (userUUID == null) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            cached_uuid.put(userName, userUUID);
                        } else {
                            Bukkit.getLogger().info("Cached UUID of " + userName + " caught as " + userUUID);
                        }
                        if (args.length == 6) {
                            serverName = args[5];
                        }
                        if (CmdPermissions.this.rankItemLibrary && serverName.equals(PermissionsConfig.SERVER_NAME) && CompilexPermissions.getHighestRankID(userUUID.toString()) > (Bukkit.getPluginManager().isPluginEnabled("CosmicSpaceChests") ? RankItems.getRankID(groupName) : RankItems.getRankID(groupName))) {
                            sender.sendMessage("Skipping permission update for " + userUUID.toString() + " to " + groupName + "(" + RankItems.getRankID(groupName) + ") on " + PermissionsConfig.SERVER_NAME + " due to existing rank ID " + CompilexPermissions.getHighestRankID(userUUID.toString()));
                            return;
                        }
                        final String uuidString = userUUID.toString();
                        final PermissionUser user = CmdPermissions.this.plugin.getPermissionManager().getCachedPermissionUsers().get(userUUID);
                        if (user != null && (cachedGroup = CmdPermissions.this.plugin.getPermissionManager().getCachedLoadedGroups().get(groupName.toLowerCase())) != null) {
                            CmdPermissions.this.plugin.getServer().getScheduler().runTask(CmdPermissions.this.plugin, new Runnable(){

                                @Override
                                public void run() {
                                    if (user.getGroupsEditable() != null && user.getGroupsEditable().size() > 0) {
                                        int rankId = RankItems.getRankID(cachedGroup.getName().toLowerCase());
                                        Bukkit.getLogger().info("RANK ID FOR " + cachedGroup.getName().toLowerCase() + " IS " + rankId);
                                        ArrayList<String> toRemove = Lists.newArrayList();
                                        for (PermissionGroup existing : Lists.newArrayList(user.getGroupsEditable())) {
                                            int existinRank = RankItems.getRankID(existing.getName().toLowerCase());
                                            Bukkit.getLogger().info("RANK ID FOR " + existing.getName() + " is " + existinRank);
                                            if (rankId < existinRank || RankItems.isAboveNormalRank(existing.getName().toLowerCase())) continue;
                                            user.getGroupsEditable().remove(existing);
                                            user.getGroupNames().remove(existing.getName().toLowerCase());
                                            user.getGroupNames().remove(existing.getName());
                                            toRemove.add(existing.getName());
                                            Bukkit.getLogger().info("Removing old lesser group: " + existing.getName() + " ID=" + existinRank + " due to new " + rankId);
                                        }
                                        if (toRemove.size() > 0) {
                                            List ls = CacheManager.getCachedPlayerGroups().computeIfAbsent(uuidString, e -> Lists.newArrayList());
                                            for (String removing : toRemove) {
                                                ls.remove(removing);
                                                ls.remove(removing.toLowerCase());
                                                Bukkit.getLogger().info("Removing " + removing + " group from " + uuidString);
                                            }
                                        }
                                    }
                                    user.getGroupsEditable().add(cachedGroup);
                                    user.getGroupNames().add(cachedGroup.getName().toLowerCase());
                                    for (String permission : cachedGroup.getPermissions()) {
                                        CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().put(permission, true);
                                    }
                                    CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
                                }
                            });
                        }
                        CmdPermissions.this.plugin.getDatabaseManager().addGroupToUserIfNotExists(userUUID, userName, groupName, serverName);

                        ArrayList<String> ls;
                        Bukkit.getLogger().info("[Permissions] Adding " + groupName + " to " + userUUID + "'s getCachedPlayerGroups() list.");
                        List<String> list = ls = CacheManager.getCachedPlayerGroups().containsKey(userUUID.toString()) ? (ArrayList<String>) CacheManager.getCachedPlayerGroups().get(userUUID.toString()) : new ArrayList<String>();
                        if (!ls.contains(groupName) && !ls.contains(groupName.toLowerCase())) {
                            ls.add(groupName);
                        }
                        CacheManager.getCachedPlayerGroups().put(userUUID.toString(), ls);
                        sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.AQUA + userName + " is now a member of the group " + groupName + " on server " + serverName + ".");
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return true;
        }
        if ((args.length == 5 || args.length == 6) && args[2].toLowerCase().startsWith("group") && (args[3].toLowerCase().startsWith("remove") || args[3].toLowerCase().startsWith("delete"))) {
            if (!sender.isOp()) {
                return true;
            }
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    try {
                        PermissionGroup cachedGroup;
                        PermissionUser user;
                        UUID userUUID;
                        String userName = args[1];
                        String groupName = args[4];
                        String serverName = PermissionsConfig.SERVER_NAME;
                        UUID uUID = userUUID = cached_uuid.containsKey(userName) ? cached_uuid.get(userName) : null;
                        if (userUUID == null) {
                            try {
                                userUUID = Bukkit.getPlayerExact(userName) != null ? Bukkit.getPlayerExact(userName).getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            catch (Exception e) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            if (userUUID == null) {
                                userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                            }
                            cached_uuid.put(userName, userUUID);
                        } else {
                            Bukkit.getLogger().info("Cached UUID of " + userName + " caught as " + userUUID);
                        }
                        if (args.length == 6) {
                            serverName = args[5];
                        }
                        if ((user = CmdPermissions.this.plugin.getPermissionManager().getCachedPermissionUsers().get(userUUID)) != null && (cachedGroup = CmdPermissions.this.plugin.getPermissionManager().getCachedLoadedGroups().get(groupName.toLowerCase())) != null) {
                            user.getGroupsEditable().remove(cachedGroup);
                            user.getGroupNames().remove(cachedGroup.getName().toLowerCase());
                            for (String permission : cachedGroup.getPermissions()) {
                                CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().remove(permission);
                            }
                            CmdPermissions.this.plugin.getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
                        }
                        CmdPermissions.this.plugin.getDatabaseManager().removeGroupFromUserIfExists(userUUID, groupName, serverName);

                        ArrayList ls;
                        Bukkit.getLogger().info("[Permissions] Removing " + groupName + " to " + userUUID + "'s getCachedPlayerGroups() list.");
                        List<Object> list = ls = CacheManager.getCachedPlayerGroups().containsKey(userUUID.toString()) ? (ArrayList) CacheManager.getCachedPlayerGroups().get(userUUID.toString()) : new ArrayList();
                        ls.remove(groupName);
                        CacheManager.getCachedPlayerGroups().put(userUUID.toString(), ls);
                        sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.AQUA + userName + " has been removed from the group " + groupName + " on server " + serverName + ".");
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return true;
        }
        if (args.length == 1 && args[0].toLowerCase().startsWith("reload")) {
            if (!sender.isOp()) {
                return true;
            }
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    CmdPermissions.this.plugin.getConfigurationManager().onEnable();
                    CmdPermissions.this.plugin.getPermissionManager().onEnable();
                    for (Player player : CmdPermissions.this.plugin.getServer().getOnlinePlayers()) {
                        PermissionsAPI.reloadPlayerPermissions(player);
                    }
                    sender.sendMessage(ChatColor.WHITE + "[Permissions] Permissions Reloaded.");
                }
            });
            return true;
        }
        if (args.length == 6 && args[2].toLowerCase().startsWith("timed") && args[3].toLowerCase().startsWith("add")) {
            int timeSeconds;
            if (!sender.isOp()) {
                return true;
            }
            String playerName = args[1];
            final String permissionName = args[4];
            try {
                timeSeconds = Integer.valueOf(args[5]);
            }
            catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.RED + args[5] + " is not a number.");
                return true;
            }
            final Player player = Bukkit.getPlayerExact(playerName);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.RED + "The player " + playerName + " is not online.");
                return true;
            }
            final PermissionUser user = this.plugin.getPermissionManager().getCachedPermissionUsers().get(player.getUniqueId());
            if (user != null) {
                user.getCurrentAttachment().setPermission(permissionName, true);
                this.plugin.getServer().getScheduler().runTaskLater(this.plugin, new Runnable(){

                    @Override
                    public void run() {
                        if (player != null && player.isOnline()) {
                            user.getCurrentAttachment().unsetPermission(permissionName);
                        }
                    }
                }, (long)timeSeconds * 20L);
            }
            sender.sendMessage(ChatColor.WHITE + "[Permissions] " + ChatColor.GREEN + "Timed permission " + permissionName + " added to " + playerName + " for " + timeSeconds + " seconds.");
            return true;
        }
        sender.sendMessage(ChatColor.WHITE + "[Permissions] Version " + ChatColor.RED + this.plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.WHITE + "[Permissions] Commands:");
        if (sender.isOp()) {
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions user playername group add groupname servername");
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions user playername group remove groupname servername");
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions user playername add permission servername");
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions user playername remove permission servername");
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions user playername timed add permission timeinseconds");
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions flush playername");
            sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions reload");
        }
        sender.sendMessage(ChatColor.WHITE + "[Permissions] /permissions user playername");
        return true;
    }

    public static void replaceGroup(final String userName, final String group_1, final String group_2) {
        CompilexPermissions.get().getServer().getScheduler().runTaskAsynchronously(CompilexPermissions.get(), new Runnable(){

            @Override
            public void run() {
                try {
                    UUID userUUID;
                    String serverName = PermissionsConfig.SERVER_NAME;
                    UUID uUID = userUUID = cached_uuid.containsKey(userName) ? cached_uuid.get(userName) : null;
                    if (userUUID == null) {
                        try {
                            userUUID = Bukkit.getPlayerExact(userName) != null ? Bukkit.getPlayerExact(userName).getUniqueId() : Bukkit.getOfflinePlayer(userName).getUniqueId();
                        }
                        catch (Exception e) {
                            userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                        }
                        if (userUUID == null) {
                            userUUID = Bukkit.getOfflinePlayer(userName).getUniqueId();
                        }
                        cached_uuid.put(userName, userUUID);
                    } else {
                        Bukkit.getLogger().info("Cached UUID of " + userName + " caught as " + userUUID);
                    }
                    final PermissionUser user = CompilexPermissions.get().getPermissionManager().getCachedPermissionUsers().get(userUUID);
                    if (user != null) {
                        final PermissionGroup cachedGroup_1 = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(group_1.toLowerCase());
                        final PermissionGroup cachedGroup_2 = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(group_2.toLowerCase());
                        if (cachedGroup_1 != null && cachedGroup_2 != null) {
                            CompilexPermissions.get().getServer().getScheduler().runTask(CompilexPermissions.get(), new Runnable(){

                                @Override
                                public void run() {
                                    user.getGroupsEditable().remove(cachedGroup_1);
                                    user.getGroupNames().remove(cachedGroup_1.getName().toLowerCase());
                                    user.getGroupsEditable().add(cachedGroup_2);
                                    user.getGroupNames().add(cachedGroup_2.getName().toLowerCase());
                                    for (String permission : cachedGroup_1.getPermissions()) {
                                        CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().remove(permission);
                                    }
                                    for (String permission : cachedGroup_2.getPermissions()) {
                                        CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).getChildren().put(permission, true);
                                    }
                                    CompilexPermissions.get().getServer().getPluginManager().getPermission(user.getPermissionName()).recalculatePermissibles();
                                }
                            });
                        }
                    }
                    CompilexPermissions.get().getDatabaseManager().addGroupToUserIfNotExists(userUUID, userName, group_2, serverName);
                    CompilexPermissions.get().getDatabaseManager().removeGroupFromUserIfExists(userUUID, group_1, serverName);
                    Bukkit.getLogger().info(ChatColor.WHITE + "[Permissions] " + ChatColor.AQUA + userName + " has been removed from the group " + group_1 + " and added to " + group_2 + " on server " + serverName + ".");
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

