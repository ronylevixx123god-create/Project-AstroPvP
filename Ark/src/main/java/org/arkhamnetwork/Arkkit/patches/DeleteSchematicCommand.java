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

class DeleteSchematicCommand
extends AbstractCommand {
    private SchematicUpdater plugin;

    public DeleteSchematicCommand(SchematicUpdater updater, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.plugin = updater;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid usage: /deleteschematic <schematicName>");
            return true;
        }
        new BukkitRunnable(){

            public void run() {
                String schemName = args[0];
                if (!schemName.endsWith(".schematic")) {
                    schemName = schemName + ".schematic";
                }
                FTPClient client = new FTPClient();
                try {
                    ArrayList fileNames;
                    client.connect(((DeleteSchematicCommand)DeleteSchematicCommand.this).plugin.config.getString("ftp.host"), 1021);
                    if (!client.login(((DeleteSchematicCommand)DeleteSchematicCommand.this).plugin.config.getString("ftp.username"), ((DeleteSchematicCommand)DeleteSchematicCommand.this).plugin.config.getString("ftp.password"))) {
                        Bukkit.getLogger().severe("Unable to connect to FTP.");
                        return;
                    }
                    client.changeWorkingDirectory("../schematics");
                    if (client.getReplyCode() != 0) {
                        System.out.println(client.getReplyString());
                    }
                    if ((fileNames = Lists.newArrayList((Object[])client.listNames("."))).contains(schemName)) {
                        boolean deleted = client.deleteFile(schemName);
                        if (deleted) {
                            sender.sendMessage((Object)ChatColor.GREEN + "Deleted " + schemName + " from the cloud.");
                        } else {
                            sender.sendMessage((Object)ChatColor.RED + "Unable to delete file from the cloud!");
                        }
                        return;
                    }
                    sender.sendMessage((Object)ChatColor.RED + "No file found on remote server.");
                }
                catch (IOException e) {
                    e.printStackTrace();
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

