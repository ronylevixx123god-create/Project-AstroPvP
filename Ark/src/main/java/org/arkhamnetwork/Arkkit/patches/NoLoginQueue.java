/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class NoLoginQueue
        extends ArkPlugin {
    protected static boolean no_new_connections;

    public NoLoginQueue(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        no_new_connections = Bukkit.getOnlinePlayers().size() <= 0 || System.currentTimeMillis() - Arkkit.get().enable_time <= 180000L;
        this.registerListener(new NoJoinQueueListener());
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "[Arkkit] " + ChatColor.WHITE + "Logins will be allowed in 5 second(s).");
        Bukkit.getScheduler().runTaskLater(this.getBukkitPlugin(), new Runnable() {

            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "[Arkkit] " + ChatColor.WHITE + "New connections are now allowed.");
                no_new_connections = false;
            }
        }, 20L);
    }

    @Override
    public void disable() {
    }
}

