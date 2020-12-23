/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarAnnouncer
        extends ArkPlugin {
    private static String currentAnnouncement = "Arkkit (BarAnnouncer)...";
    private static BA_SQLQueryThread sql_worker;

    public BarAnnouncer(Plugin p) {
        super(p);
    }

    protected static String getCurrentAnnouncement() {
        return currentAnnouncement;
    }

    protected static void setCurrentAnnouncement(String s) {
        currentAnnouncement = s;
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("BarAPI")) {
            Bukkit.getLogger().info("(Arkkit [BarAnnouncer]) BarAPI dependency not found, disabling patch.");
            this.kill();
            return;
        }
        List<String> alerts = new ArrayList();
        if (new File("plugins/Arkkit/BA_sqlconfig.yml").exists()) {
            Bukkit.getLogger().info("Loading SQL announcements!");
            BA_SQLManager.initializeSQL(Arkkit.get());
            sql_worker = new BA_SQLQueryThread();
            sql_worker.start();
            BA_SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS bar_messages(message_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, message TEXT)");
            ResultSet rs = BA_SQLConnection.getResultSet("SELECT * FROM bar_messages ORDER BY message_id ASC");
            try {
                while (rs.next()) {
                    alerts.add(rs.getString("message"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                alerts = Arkkit.get().getConfig().getStringList("settings.bar_announcer.announcements");
            }
        } else {
            alerts = Arkkit.get().getConfig().getStringList("settings.bar_announcer.announcements");
        }
        long delayTicks = Arkkit.get().getConfig().getLong("settings.bar_announcer.delayTicks");
        if (alerts.size() > 0) {
            this.registerListener(new BarAnnouncerPersistanceListener());
            this.registerTask(new BarAnnouncerTask(alerts).runTaskTimer(Arkkit.get(), delayTicks, delayTicks), BarAnnouncer.class);
        } else {
            Bukkit.getLogger().info("No alerts found for Arkkit's bar_announcer, sleeping.");
        }
    }

    @Override
    public void disable() {
        sql_worker.interrupt();
        sql_worker.doWork();
        sql_worker = null;
        StatusBarAPI.removeAllStatusBars();
    }
}

