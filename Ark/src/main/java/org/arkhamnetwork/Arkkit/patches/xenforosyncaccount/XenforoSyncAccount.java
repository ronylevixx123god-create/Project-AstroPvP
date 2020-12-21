/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.xenforosyncaccount;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.CommandSyncAccount;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.CommandUnsyncAccount;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.SyncAccountListener;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_SQLConnection;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_SQLManager;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_SQLQueryThread;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_Website_SQLManager;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_Website_SQLQueryThread;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class XenforoSyncAccount
extends ArkPlugin {
    protected static String API_URL;
    protected static String API_KEY;
    private static XF_SQLQueryThread sql_worker;
    private static XF_Website_SQLQueryThread sql_worker_website;

    public XenforoSyncAccount(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!(new File("plugins/Arkkit/XF_sqlconfig.yml").exists() && XF_SQLManager.initializeSQL((Plugin)Arkkit.get()) && XF_Website_SQLManager.initializeSQL((Plugin)Arkkit.get()))) {
            Bukkit.getLogger().warning("XF_sqlconfig.yml or XF_website_sqlconfig.yml does not exist, disabling...");
            this.kill();
            return;
        }
        API_URL = Arkkit.get().getConfig().getString("language.xenforo_sync_account.api_url");
        API_KEY = Arkkit.get().getConfig().getString("language.xenforo_sync_account.api_key");
        sql_worker = new XF_SQLQueryThread();
        sql_worker.start();
        sql_worker_website = new XF_Website_SQLQueryThread();
        sql_worker_website.start();
        XF_SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS `mc_users` (`xf_user_id` int(11) NOT NULL,`xf_username` varchar(255) NOT NULL,`xf_email` varchar(255) NOT NULL,`uuid` varchar(255) NOT NULL,`username` varchar(255) NOT NULL,PRIMARY KEY (`xf_user_id`),UNIQUE KEY `uuid` (`uuid`),UNIQUE KEY `username` (`username`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        this.registerCommand(new CommandSyncAccount("syncaccount", "/<command>", "Sync your game and forum account!", new ArrayList<String>(Arrays.asList("sync", "link"))));
        this.registerCommand(new CommandUnsyncAccount("unsyncaccount", "/<command>", "Unsync your game and forum account!", new ArrayList<String>(Arrays.asList("unsync", "unlink"))));
        if (new File("plugins/CosmicLobby.jar").exists()) {
            this.registerListener(new SyncAccountListener());
        }
    }

    @Override
    public void disable() {
        sql_worker.interrupt();
        sql_worker.doWork();
        sql_worker = null;
    }

    protected static XF_SQLQueryThread getSQL() {
        return sql_worker;
    }

    protected static XF_Website_SQLQueryThread getWebsiteSQL() {
        return sql_worker_website;
    }

    public static String getForumUsernameFromPlayerName(String playerName) throws SQLException {
        ResultSet rs = XF_SQLConnection.getResultSet("SELECT xf_username FROM mc_users WHERE username='" + playerName + "'");
        if (rs.next()) {
            return rs.getString("xf_username");
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getForumUsernameFromPlayerUUID(UUID playerUUID) throws SQLException {
        try (Statement statement = null;){
            statement = XF_SQLConnection.getConnection().prepareStatement("SELECT xf_username FROM mc_users WHERE uuid='" + playerUUID.toString() + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String string = rs.getString("xf_username");
                return string;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getForumIDFromPlayerUUID(UUID playerUUID) throws SQLException {
        try (Statement statement = null;){
            statement = XF_SQLConnection.getConnection().prepareStatement("SELECT xf_user_id FROM mc_users WHERE uuid='" + playerUUID.toString() + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String string = rs.getString("xf_username");
                return string;
            }
        }
        return null;
    }
}

