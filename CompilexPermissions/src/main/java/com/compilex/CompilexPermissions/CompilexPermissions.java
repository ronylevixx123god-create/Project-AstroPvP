/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.Connect
 *  net.minecraft.util.org.apache.commons.io.FileUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.compilex.CompilexPermissions;

import com.compilex.CompilexPermissions.cmd.CmdPermissions;
import com.compilex.CompilexPermissions.listener.PlayerListener;
import com.compilex.CompilexPermissions.logger.PermissionsLogger;
import com.compilex.CompilexPermissions.manager.CacheManager;
import com.compilex.CompilexPermissions.manager.ConfigurationManager;
import com.compilex.CompilexPermissions.manager.DatabaseManager;
import com.compilex.CompilexPermissions.manager.PermissionManager;
import com.compilex.CompilexPermissions.threads.PermissionChangeBroadcastThread;
import com.compilex.CompilexPermissions.threads.SQLQueryThread;
import com.compilex.CompilexPermissions.utils.RankItems;
import org.apache.commons.io.FileUtils;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class CompilexPermissions
extends JavaPlugin {
    private static CompilexPermissions me;
    private ConfigurationManager configurationManager;
    private PermissionManager permissionManager;
    private DatabaseManager databaseManager;
    private HashMap<UUID, String> globalRanks;
    private boolean API_MODE;
    private SQLQueryThread sqlWorker;
    private volatile CopyOnWriteArrayList<String> sqlQueryList;
    @Deprecated
    public PermissionChangeBroadcastThread permissionSyncThread;
    @Deprecated
    public static boolean syncPermissionChanges;
    public static boolean debug;

    public void onEnable() {
        me = this;
        this.globalRanks = new HashMap();
        this.sqlQueryList = new CopyOnWriteArrayList();
        new ArkhamPermissions();
        this.API_MODE = this.getConfig().getBoolean("API_MODE");
        this.loadConfig();
        this.loadPermissionFile();
        this.connectToSQL();
        if (!this.isAPI_MODE()) {
            this.cachePermissionData();
            this.registerCommands();
            this.registerListener();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                PermissionsAPI.reloadPlayerPermissions(player);
            }
        } else {
            Bukkit.getLogger().info("*******************************************************");
            Bukkit.getLogger().info("");
            Bukkit.getLogger().info("[CompilexPermissions] NOTICE: Running in API_MODE, all me.astropvp.cratekeys.net.astropvp.armorsets.listeners and permission checks are disabled.");
            Bukkit.getLogger().info("");
            Bukkit.getLogger().info("*******************************************************");
        }
        RankItems.findDonorRanks();
    }

    public void onDisable() {
        if (this.sqlWorker != null) {
            this.sqlWorker.interrupt();
            this.sqlWorker.doWork();
        }
        if (this.configurationManager != null) {
            this.configurationManager.onDisable();
        }
        if (this.databaseManager != null) {
            this.databaseManager.onDisable();
        }
        if (this.permissionManager != null) {
            this.permissionManager.onDisable();
        }
        if (this.permissionSyncThread != null && this.permissionSyncThread.isAlive()) {
            this.permissionSyncThread.interrupt();
        }
    }

    @Deprecated
    public static CompilexPermissions getInstance() {
        return me;
    }

    public static CompilexPermissions get() {
        return me;
    }

    public static Plugin getPluginObject() {
        return me;
    }

    private void registerCommands() {
        this.getCommand("permissions").setExecutor((CommandExecutor)new CmdPermissions());
    }

    private void registerListener() {
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }

    private void loadConfig() {
        this.convertOldConfigVersions();
        syncPermissionChanges = this.getConfig().getBoolean("SYNC_PERMISSION_CHANGES");
        if (syncPermissionChanges) {
            PermissionsLogger.logMessage("Running PermissionChangeBroadcastThread() due to SYNC_PERMISSION_CHANGES = true!");
            this.permissionSyncThread = new PermissionChangeBroadcastThread();
            this.permissionSyncThread.start();
        }
    }

    private void convertOldConfigVersions() {
        if (Bukkit.getPluginManager().isPluginEnabled("ArkhamPermissions")) {
            Bukkit.getLogger().info("[CompilexPermissions] Disabling ArkhamPermissions, renaming .jar to *.DISABLED and rebooting.");
            new File("plugins/ArkhamPermissions.jar").renameTo(new File("plugins/ArkhamPermissions.jar.DISABLED"));
            //Bukkit.shutdown();
            return;
        }
        File oldConfigs = new File("plugins/ArkhamPermissions");
        if (oldConfigs.exists()) {
            try {
                Bukkit.getLogger().info("[CompilexPermissions] Found old ArkhamPermission configs, converting...");
                FileUtils.deleteDirectory((File)new File("plugins/CopmilexPermissions"));
                FileUtils.copyDirectory((File)oldConfigs, (File)new File("plugins/CompilexPermissions"));
                this.reloadConfig();
                oldConfigs.delete();
                Bukkit.getLogger().info("[CompilexPermissions] ArkhamPermission config conversion successful!");
            }
            catch (IOException e) {
                e.printStackTrace();
                //Bukkit.getServer().shutdown();
            }
        }
        if (new File("donation_server").exists()) {
            this.getConfig().set("SYNC_PERMISSION_CHANGES", (Object)true);
            this.saveConfig();
            new File("donation_server").delete();
        }
    }

    private void loadPermissionFile() {
        boolean permission_file_unreachable;
        this.configurationManager = new ConfigurationManager();
        boolean bl = permission_file_unreachable = !this.configurationManager.onEnable();
        if (permission_file_unreachable) {
            Bukkit.getLogger().info("Could not download PermissionsConfig.yml file from remote host, searching for cache...");
            File f = new File("plugins/ArkhamPermissions/PermissionsConfig.yml");
            if (f.exists()) {
                Bukkit.getLogger().info("Located cached PermissionsConfig.yml file, loading!");
                this.configurationManager.setCachedWebConfiguration((FileConfiguration)YamlConfiguration.loadConfiguration((File)f));
            } else {
                Bukkit.getLogger().info("No cached permission file found, shutting down.");
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Bukkit.shutdown();
            }
        }
        this.permissionManager = new PermissionManager();
        this.permissionManager.onEnable();
    }

    private void cachePermissionData() {
        if (!CacheManager.cachePlayerGroups() || !CacheManager.cachePlayerPermissions()) {
            Bukkit.getLogger().info("Failed to cache player permissions and/or groups, shutting down.");
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Bukkit.shutdown();
            return;
        }
    }

    private void connectToSQL() {
        this.databaseManager = new DatabaseManager();
        if (!this.databaseManager.onEnable()) {
            Bukkit.getLogger().info("Failed to connect to SQL, shutting down.");
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Bukkit.shutdown();
            return;
        }
        this.sqlWorker = new SQLQueryThread();
        this.sqlWorker.start();
    }

    public static void removePlayerFromGroup(String userName, String groupName, String serverName) {
        PermissionsAPI.removePlayerFromGroup(userName, groupName, serverName);
    }

    @Deprecated
    public static void addPlayerToGroup(String userName, String groupName, String serverName) {
        PermissionsAPI.addPlayerToGroup(userName, groupName, serverName);
    }

    @Deprecated
    public static int getHighestRankID(String playerUUID) {
        return PermissionsAPI.getHighestRankID(playerUUID);
    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }

    public PermissionManager getPermissionManager() {
        return this.permissionManager;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public HashMap<UUID, String> getGlobalRanks() {
        return this.globalRanks;
    }

    public boolean isAPI_MODE() {
        return this.API_MODE;
    }

    public void setSqlWorker(SQLQueryThread sqlWorker) {
        this.sqlWorker = sqlWorker;
    }

    public SQLQueryThread getSqlWorker() {
        return this.sqlWorker;
    }

    public void setSqlQueryList(CopyOnWriteArrayList<String> sqlQueryList) {
        this.sqlQueryList = sqlQueryList;
    }

    public CopyOnWriteArrayList<String> getSqlQueryList() {
        return this.sqlQueryList;
    }

    @Deprecated
    public PermissionChangeBroadcastThread getPermissionSyncThread() {
        return this.permissionSyncThread;
    }

    static {
        syncPermissionChanges = false;
        debug = true;
    }
}

