/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.net.ftp.FTPClient
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.SchematicUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

class ListSchematicCommand
extends AbstractCommand {
    SchematicUpdater plugin;

    public ListSchematicCommand(SchematicUpdater updater, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.plugin = updater;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        new BukkitRunnable(){

            public void run() {
                FTPClient client = new FTPClient();
                try {
                    client.connect(ListSchematicCommand.this.plugin.config.getString("ftp.host"), 1021);
                    if (!client.login(ListSchematicCommand.this.plugin.config.getString("ftp.username"), ListSchematicCommand.this.plugin.config.getString("ftp.password"))) {
                        Bukkit.getLogger().severe("Unable to connect to FTP.");
                        return;
                    }
                    if (client.getReplyCode() != 0) {
                        System.out.println(client.getReplyString());
                    }
                    client.changeWorkingDirectory("../schematics");
                    client.setFileType(0);
                    ArrayList files = Lists.newArrayList((Object[])client.listNames("."));
                    sender.sendMessage((Object)ChatColor.RED + "Found " + files.size() + " files:");
                    for (String fileName : files) {
                        sender.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.RED + fileName);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    sender.sendMessage((Object)ChatColor.RED + "There was a problem grabbing the files..");
                }
                try {
                    client.logout();
                    client.disconnect();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously((Plugin)Arkkit.get());
        return false;
    }
}

