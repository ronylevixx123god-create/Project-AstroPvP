/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.marriage.CommandMarriage;
import org.arkhamnetwork.Arkkit.patches.marriage.MA_SQLManager;
import org.arkhamnetwork.Arkkit.patches.marriage.MA_SQLQueryThread;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageDataManager;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageHeartsTask;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageListener;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriagePlayerList;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class Marriage
extends ArkPlugin {
    protected static volatile CopyOnWriteArrayList<String> sql_query = new CopyOnWriteArrayList();
    protected static HashMap<UUID, UUID> pending_requests = new HashMap();
    protected static ArrayList<UUID> marriage_chatters = new ArrayList();
    protected static MA_SQLQueryThread sql_worker;
    protected static String prefix;

    public Marriage(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!new File("plugins/Arkkit/MA_sqlconfig.yml").exists()) {
            Bukkit.getLogger().info("(Arkkit [Marriage]) Cannot locate MA_sqlconfig.yml SQL settings, disabling marriage patch.");
            this.kill();
            return;
        }
        prefix = ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&l[&b&lMarriage&e&l]&r ");
        MA_SQLManager.initializeSQL((Plugin)Arkkit.get());
        sql_worker = new MA_SQLQueryThread();
        sql_worker.start();
        sql_query.add("CREATE TABLE IF NOT EXISTS `marriage_couples` (`player_uuid` VARCHAR(36), `partner_uuid` VARCHAR(36))");
        sql_query.add("CREATE TABLE IF NOT EXISTS `marriage_settings` (`player_uuid` VARCHAR(36) PRIMARY KEY, `marriageRequestsEnabled` BOOLEAN)");
        this.registerListener(new MarriageListener());
        this.registerListener(new MarriageDataManager());
        this.registerListener(new MarriagePlayerList());
        this.registerCommand(new CommandMarriage("marry", "/<command> [args]", "Sends marriage request to player.", new ArrayList<String>(Arrays.asList("marriage"))));
        this.registerTask(new MarriageHeartsTask().runTaskTimerAsynchronously((Plugin)Arkkit.get(), 0L, 5L), Marriage.class);
        this.registerTask(new MarriagePlayerList().runTaskTimerAsynchronously((Plugin)Arkkit.get(), 0L, 20L), Marriage.class);
    }

    @Override
    public void disable() {
        sql_worker.interrupt();
        sql_worker.doWork();
        sql_worker = null;
    }

    static {
        prefix = null;
    }
}

