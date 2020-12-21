/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import java.util.HashMap;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BarAnnouncer;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BarAnnouncerParser;
import org.arkhamnetwork.Arkkit.patches.barannouncer.StatusBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BarAnnouncerTask
extends BukkitRunnable {
    private HashMap<Integer, String> announcements = new HashMap();
    private int current = -1;

    public BarAnnouncerTask(List<String> announcementNames) {
        int currentCount = -1;
        for (String announcement : announcementNames) {
            this.announcements.put(++currentCount, announcement);
        }
        BarAnnouncer.setCurrentAnnouncement(this.getNextAnnouncement());
    }

    public void run() {
        BarAnnouncer.setCurrentAnnouncement(this.getNextAnnouncement());
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                StatusBarAPI.setStatusBar(player, BarAnnouncerParser.parseAnnouncement(BarAnnouncer.getCurrentAnnouncement(), player), 100.0f);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getNextAnnouncement() {
        this.current = this.current >= this.announcements.size() - 1 ? 0 : ++this.current;
        return this.announcements.get(this.current);
    }
}

