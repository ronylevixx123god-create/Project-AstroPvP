/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.net.ftp.FTPClient
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.DeleteSchematicCommand;
import org.arkhamnetwork.Arkkit.patches.DownloadForeignFileCommand;
import org.arkhamnetwork.Arkkit.patches.DownloadSchematicCommand;
import org.arkhamnetwork.Arkkit.patches.ListSchematicCommand;
import org.arkhamnetwork.Arkkit.patches.UploadFinishCallback;
import org.arkhamnetwork.Arkkit.patches.UploadSchematicCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SchematicUpdater
extends ArkPlugin {
    private File configFile;
    public FileConfiguration config;

    public SchematicUpdater(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
            Bukkit.getLogger().severe("No WorldEdit detected, disabling Schematicupdate..");
            this.kill();
            return;
        }
        this.configFile = new File("plugins/Arkkit/schematicCreds.yml");
        if (!this.configFile.exists()) {
            try {
                this.configFile.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration((File)this.configFile);
        this.registerCommand(new DownloadSchematicCommand(this, "downloadschematic", "/downloadschematic <name>", "Download schematic command.", (List<String>)Lists.newArrayList((Object[])new String[]{"downloadschem"})));
        this.registerCommand(new UploadSchematicCommand(this, "uploadschematic", "/uploadschematic <name>", "Upload schematic command.", (List<String>)Lists.newArrayList((Object[])new String[]{"uploadschem"})));
        this.registerCommand(new ListSchematicCommand(this, "listschematics", "/listschematics", "List all schematics stored on the cloud.", (List<String>)Lists.newArrayList((Object[])new String[]{"listschems"})));
        this.registerCommand(new DownloadForeignFileCommand(this, "downloadforeignfile", "/downloadforeignfile <url> <fileName>", "Downloads a foreign file and uploads to schematic cloud.", (List<String>)Lists.newArrayList((Object[])new String[]{"dlffile", "downloadforeignfile", "downloadff"})));
        this.registerCommand(new DeleteSchematicCommand(this, "deleteschematic", "/deleteschematic <name>", "Delete a given schematic file from the server.", (List<String>)Lists.newArrayList((Object[])new String[]{"deleteschem", "delschem"})));
    }

    @Override
    public void disable() {
        this.unregisterCommands();
    }

    public void uploadFile(final File file, final String name, final UploadFinishCallback callback) {
        new BukkitRunnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                FTPClient client = new FTPClient();
                try {
                    client.connect(SchematicUpdater.this.config.getString("ftp.host"), 1021);
                    if (!client.login(SchematicUpdater.this.config.getString("ftp.username"), SchematicUpdater.this.config.getString("ftp.password"))) {
                        Bukkit.getLogger().severe("Unable to connect to FTP.");
                        return;
                    }
                    if (client.getReplyCode() != 0) {
                        System.out.println(client.getReplyString());
                    }
                    client.changeWorkingDirectory("../schematics");
                    client.setFileType(2);
                    FileInputStream str = new FileInputStream(file);
                    try {
                        boolean uploaded = client.storeFile(name, (InputStream)str);
                        System.out.println("Uploading status: " + uploaded);
                        if (callback != null) {
                            callback.onFinish();
                        }
                    }
                    finally {
                        if (Collections.singletonList(str).get(0) != null) {
                            str.close();
                        }
                    }
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
    }
}

