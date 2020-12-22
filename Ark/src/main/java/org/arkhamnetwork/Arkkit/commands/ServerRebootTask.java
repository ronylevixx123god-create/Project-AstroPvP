/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.commands;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class ServerRebootTask
extends BukkitRunnable {
    volatile int iteration = 60;
    private String restartWarnMessage = ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&l *** LOCAL SERVER REBOOTING IN [time] SECONDS ***");

    public ServerRebootTask(boolean force) {
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "      *** A Compilex reboot has been queued. *** "));
        Bukkit.broadcastMessage((String)(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "  This process occurs when Compilex pulls a critical core update."));
        Bukkit.broadcastMessage((String)"");
        if (force) {
            this.iteration = 0;
        }
    }

    public synchronized void run() {
        if (this.iteration < -10) {
            return;
        }
        switch (this.iteration) {
            case 60: {
                this.doBroadcast();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.closeInventory();
                }
                break;
            }
            case 45: {
                this.doBroadcast();
                break;
            }
            case 30: {
                this.doBroadcast();
                break;
            }
            case 15: {
                this.doBroadcast();
                break;
            }
            case 10: {
                this.doBroadcast();
                Bukkit.getScheduler().runTask((Plugin)Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.savePlayers();
                        Bukkit.getLogger().info("(CommandArkReboot) Saving all worlds...");
                        for (World world : Bukkit.getWorlds()) {
                            world.save();
                        }
                    }
                });
                break;
            }
            case 5: {
                this.doBroadcast();
                break;
            }
            case 4: {
                this.doBroadcast();
                break;
            }
            case 3: {
                this.doBroadcast();
                break;
            }
            case 2: {
                this.doBroadcast();
                break;
            }
            case 1: {
                this.doBroadcast();
                break;
            }
            case -10: {
                Bukkit.getLogger().info("(CommandArkReboot) Stopping the server...");
                Bukkit.shutdown();
                this.cancel();
            }
        }
        --this.iteration;
    }

    private void doBroadcast() {
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)this.restartWarnMessage.replace("[time]", String.valueOf(this.iteration)));
        Bukkit.broadcastMessage((String)"");
    }
}

