/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.FallbackServers;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandArkUpdate
implements CommandExecutor {
    private Arkkit plugin = null;

    public CommandArkUpdate(Plugin p) {
        this.plugin = (Arkkit)p;
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: /arkupdate <default / url> (save)");
            sender.sendMessage("Default will grab latest stable branch from repository, URL will grab from specified location.");
            return true;
        }
        Bukkit.getServer().getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                boolean save;
                String update_url = args[0];
                boolean bl = save = args.length == 2 ? Boolean.valueOf(args[1]) : false;
                if (update_url.equalsIgnoreCase("default")) {
                    update_url = "http://10.0.0.2/tmp/Arkkit.jar";
                }
                Bukkit.getLogger().info("Deleting plugins/Arkkit.jar...");
                new File("plugins/Arkkit.jar").delete();
                Bukkit.getLogger().info("Downloading " + update_url + "...");
                if (!this.downloadUrl(update_url, "plugins/Arkkit.jar")) {
                    sender.sendMessage((Object)ChatColor.RED + "Failed to acquire Arkkit build from: " + update_url + ". The current build has still been purged, however will stay loaded in memory.");
                    return;
                }
                if (ArkPlugins.FALLBACK_SERVERS.getArkPlugin().isRunning()) {
                    ((FallbackServers)ArkPlugins.FALLBACK_SERVERS.getArkPlugin()).setIgnoreDisable(true);
                }
                if (save) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"save-all");
                }
                Bukkit.getServer().getScheduler().runTask((Plugin)Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"plm reload Arkkit");
                    }
                });
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean downloadUrl(String url, String dest_path) {
                FileOutputStream fos = null;
                try {
                    URL website = new URL(url);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    fos = new FileOutputStream(dest_path);
                    fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
                }
                catch (Exception err) {
                    err.printStackTrace();
                    boolean bl = false;
                    return bl;
                }
                finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            }
        });
        return true;
    }
}

