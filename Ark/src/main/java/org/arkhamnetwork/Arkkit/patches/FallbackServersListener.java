/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.server.PluginDisableEvent
 *  org.bukkit.event.server.ServerCommandEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.FallbackServers;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

final class FallbackServersListener
implements Listener {
    boolean restarting = false;
    protected static boolean ignore_disable = false;

    FallbackServersListener() {
    }

    @EventHandler(ignoreCancelled=true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (this.restarting) {
            return;
        }
        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }
        if (ignore_disable) {
            Bukkit.getLogger().info("Arkkit's FallbackServers patch has ignored the last pluginDisable event...");
            ignore_disable = false;
            return;
        }
        if (!event.getPlugin().equals((Object)Arkkit.get())) {
            return;
        }
        this.restarting = true;
        Arkkit.get().shutting_down = true;
        FallbackServers.disconnectAllPlayers();
        try {
            Thread.sleep(5000L);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerSend(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().contains("reload Arkkit") || event.getMessage().contains("unload Arkkit") || event.getMessage().contains("plugman ")) {
            Bukkit.getLogger().info("[Arkkit (FallbakcServers)] Arkkit's FallbackServers patch will ignore the next plugin disable event...");
            ignore_disable = true;
        }
        if (event.getMessage().equalsIgnoreCase("stop")) {
            if (this.restarting) {
                return;
            }
            event.setCancelled(true);
            this.restarting = true;
            Arkkit.get().shutting_down = true;
            FallbackServers.disconnectAllPlayers();
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"stop");
                }
            }, 100L);
        }
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        if (event.getCommand().contains("reload Arkkit") || event.getCommand().contains("unload Arkkit")) {
            Bukkit.getLogger().info("Arkkit's FallbackServers patch will ignore the next plugin disable event...");
            ignore_disable = true;
        }
        if (event.getCommand().contains("stop")) {
            if (this.restarting) {
                return;
            }
            event.setCommand("broadcast " + FallbackServers.SERVER_RESTARTING_ALERT);
            this.restarting = true;
            FallbackServers.disconnectAllPlayers();
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"stop");
                }
            }, 100L);
        }
    }
}

