/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.event.PermissionChangeEvent
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package com.cosmicpvp.titles.listeners;

import com.compilex.CompilexPermissions.event.PermissionChangeEvent;
import com.cosmicpvp.titles.sql.TitleDatabaseManager;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PermissionListener
implements Listener {
    private TitleDatabaseManager dbM;

    public PermissionListener(TitleDatabaseManager dm) {
        this.dbM = dm;
    }

    @EventHandler
    public void onPermissionChange(PermissionChangeEvent event) {
        if (event.getUser() == null) {
            return;
        }
        List<String> titles = event.getUser().getPermissionNodes();
        boolean title_perms_changed = false;
        ArrayList<String> newtitles = this.dbM.getUnlockedTitles().computeIfAbsent(event.getUser().getPlayerUUID(), e -> Lists.newArrayList());
        for (String s : titles) {
            if (!s.startsWith("titles.title.") || newtitles.contains(s.replace("titles.title.", ""))) continue;
            newtitles.add(s.replace("titles.title.", ""));
            title_perms_changed = true;
        }
        if (Bukkit.getPlayer((UUID)event.getUser().getPlayerUUID()) != null && title_perms_changed) {
            this.dbM.getUnlockedTitles().put(event.getUser().getPlayerUUID(), newtitles);
            Bukkit.getPlayer((UUID)event.getUser().getPlayerUUID()).sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + ">> " + (Object)ChatColor.GRAY + "Your available titles have updated. Use " + (Object)ChatColor.YELLOW + (Object)ChatColor.BOLD + "/title" + (Object)ChatColor.GRAY + " to view them.");
        }
    }
}

