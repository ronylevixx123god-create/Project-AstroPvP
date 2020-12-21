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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
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

class DownloadSchematicCommand
extends AbstractCommand {
    SchematicUpdater plugin;
    private String DL_URL = "schems.cosmicpvp.com/schematics/index.php?schematic=";

    public DownloadSchematicCommand(SchematicUpdater plugin, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.plugin = plugin;
        this.DL_URL = plugin.config.getString("URL", "schems.cosmicpvp.com");
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        String schemName;
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1) {
            schemName = args[0];
            if (!schemName.endsWith(".schematic")) {
                schemName = schemName + ".schematic";
            }
        } else {
            sender.sendMessage((Object)ChatColor.RED + "Invalid usage: /downloadschem <schematicName>");
            return true;
        }
        final String finalSchemName = schemName;
        new BukkitRunnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                FTPClient client;
                block14: {
                    File file = new File("plugins/WorldEdit/schematics/" + finalSchemName);
                    client = new FTPClient();
                    try {
                        ArrayList fileNames;
                        client.connect(DownloadSchematicCommand.this.plugin.config.getString("ftp.host"), 1021);
                        if (!client.login(DownloadSchematicCommand.this.plugin.config.getString("ftp.username"), DownloadSchematicCommand.this.plugin.config.getString("ftp.password"))) {
                            Bukkit.getLogger().severe("Unable to connect to FTP.");
                            return;
                        }
                        client.changeWorkingDirectory("../schematics");
                        client.setFileType(2);
                        if (client.getReplyCode() != 0) {
                            System.out.println(client.getReplyString());
                        }
                        if ((fileNames = Lists.newArrayList((Object[])client.listNames("."))).contains(finalSchemName)) {
                            sender.sendMessage((Object)ChatColor.GREEN + "You can download the schematic file at: ");
                            sender.sendMessage((Object)ChatColor.AQUA + DownloadSchematicCommand.this.DL_URL + finalSchemName);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            FileOutputStream stream = new FileOutputStream(file);
                            try {
                                boolean success = client.retrieveFile(finalSchemName, (OutputStream)stream);
                                if (!success) {
                                    file.delete();
                                    sender.sendMessage((Object)ChatColor.RED + "Unable to download file.");
                                    return;
                                }
                                sender.sendMessage((Object)ChatColor.GRAY + "Downloaded file from remote server into WorldEdit schematics folder.");
                                break block14;
                            }
                            finally {
                                if (Collections.singletonList(stream).get(0) != null) {
                                    stream.close();
                                }
                            }
                        }
                        sender.sendMessage((Object)ChatColor.RED + "No file found on remote server.");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
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

