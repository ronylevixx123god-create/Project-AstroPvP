/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.net.ftp.FTPClient
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AdminEventsListener;
import org.arkhamnetwork.Arkkit.patches.CommandWorldEvent;
import org.arkhamnetwork.Arkkit.patches.DownloadFinishCallback;
import org.arkhamnetwork.Arkkit.patches.UploadFinishCallback;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AdminEvents
extends ArkPlugin {
    protected static FileConfiguration ftpConfig;
    private File ftpConfigFile;
    private final String ftpConfigFilePath = "plugins/Arkkit/adminEventsCreds.yml";
    protected static boolean isAdminEventRunning;
    protected static String eventName;
    protected static String eventURL;
    protected static int maxClaims;
    private static List<String> fileList;

    public AdminEvents(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.loadConfig();
        this.registerListener(new AdminEventsListener());
        this.registerCommand(new CommandWorldEvent("worldevent", "/<command>", "Root command for admin world_event opperations.", Arrays.asList("worldevents")));
    }

    @Override
    public void disable() {
        Arkkit.get().getConfig().set("settings.adminEvents.isAdminEventRunning", (Object)isAdminEventRunning);
        Arkkit.get().getConfig().set("settings.adminEvents.eventName", (Object)eventName);
        Arkkit.get().getConfig().set("settings.adminEvents.eventURL", (Object)eventURL);
        Arkkit.get().getConfig().set("settings.adminEvents.canClaimInEventWorld", (Object)AdminEventsListener.canClaimInEventWorld);
        Arkkit.get().getConfig().set("settings.adminEvents.maxClaims", (Object)maxClaims);
    }

    private void loadConfig() {
        isAdminEventRunning = Arkkit.get().getConfig().getBoolean("settings.adminEvents.isAdminEventRunning", false);
        eventName = Arkkit.get().getConfig().getString("settings.adminEvents.eventName", null);
        eventURL = Arkkit.get().getConfig().getString("settings.adminEvents.eventURL", null);
        maxClaims = Arkkit.get().getConfig().getInt("settings.adminEvents.maxClaims", -1);
        AdminEventsListener.canClaimInEventWorld = Arkkit.get().getConfig().getBoolean("settings.adminEvents.canClaimInEventWorld", false);
        this.ftpConfigFile = new File("plugins/Arkkit/adminEventsCreds.yml");
        if (!this.ftpConfigFile.exists()) {
            try {
                this.ftpConfigFile.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ftpConfig = YamlConfiguration.loadConfiguration((File)this.ftpConfigFile);
    }

    protected static void uploadFile(final File file, final String name, final UploadFinishCallback callback) {
        new BukkitRunnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                FTPClient client = new FTPClient();
                try {
                    client.connect(ftpConfig.getString("ftp.host"), 1021);
                    if (!client.login(ftpConfig.getString("ftp.username"), ftpConfig.getString("ftp.password"))) {
                        Bukkit.getLogger().severe("Unable to connect to FTP.");
                        return;
                    }
                    if (client.getReplyCode() != 0) {
                        Bukkit.getLogger().info(client.getReplyString());
                    }
                    client.changeWorkingDirectory("../events");
                    client.setFileType(2);
                    FileInputStream str = new FileInputStream(file);
                    try {
                        boolean uploaded = client.storeFile(name, (InputStream)str);
                        Bukkit.getLogger().info("Uploading status: " + uploaded);
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

    protected static void downloadFile(final File outputFile, final String fileName, final boolean overwrite, final DownloadFinishCallback callback) {
        new BukkitRunnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                FTPClient client;
                block17: {
                    client = new FTPClient();
                    try {
                        ArrayList fileNames;
                        client.connect(ftpConfig.getString("ftp.host"), 1021);
                        if (!client.login(ftpConfig.getString("ftp.username"), ftpConfig.getString("ftp.password"))) {
                            Bukkit.getLogger().severe("Unable to connect to FTP.");
                            return;
                        }
                        client.changeWorkingDirectory("../events");
                        client.setFileType(2);
                        if (client.getReplyCode() != 0) {
                            Bukkit.getLogger().info(client.getReplyString());
                        }
                        if ((fileNames = Lists.newArrayList((Object[])client.listNames("."))).contains(fileName)) {
                            if (!outputFile.exists()) {
                                outputFile.createNewFile();
                            } else if (overwrite) {
                                outputFile.delete();
                            }
                            FileOutputStream stream = new FileOutputStream(outputFile);
                            try {
                                boolean success = client.retrieveFile(fileName, (OutputStream)stream);
                                if (!success) {
                                    outputFile.delete();
                                    Bukkit.getLogger().info((Object)ChatColor.RED + "Unable to download file.");
                                    return;
                                }
                                Bukkit.getLogger().info((Object)ChatColor.GRAY + "Downloaded file: " + fileName + " from remote server to: " + outputFile.getPath());
                                if (callback != null) {
                                    callback.onFinish();
                                }
                                break block17;
                            }
                            finally {
                                if (Collections.singletonList(stream).get(0) != null) {
                                    stream.close();
                                }
                            }
                        }
                        Bukkit.getLogger().info((Object)ChatColor.RED + "No file found on remote server.");
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
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void zipFile(String inputDirectory, String outputZipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(inputDirectory).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        fileList = new ArrayList<String>();
        try {
            fos = new FileOutputStream(outputZipFile);
            zos = new ZipOutputStream(fos);
            Bukkit.getLogger().info("Output to Zip : " + outputZipFile);
            FileInputStream in = null;
            for (String file : AdminEvents.generateFileList(new File(inputDirectory), inputDirectory)) {
                Bukkit.getLogger().info("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    int len;
                    in = new FileInputStream(inputDirectory + File.separator + file);
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                finally {
                    in.close();
                }
            }
            zos.closeEntry();
            Bukkit.getLogger().info("Folder successfully compressed");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                zos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> generateFileList(File node, String inputDirectory) {
        if (node.isFile()) {
            fileList.add(AdminEvents.generateZipEntry(node.toString(), inputDirectory));
        }
        if (node.isDirectory()) {
            String[] subNote;
            for (String filename : subNote = node.list()) {
                AdminEvents.generateFileList(new File(node, filename), inputDirectory);
            }
        }
        return fileList;
    }

    private static String generateZipEntry(String file, String inputDirectory) {
        return file.substring(inputDirectory.length() + 1, file.length());
    }

    protected static void unzip(String zipFile, String outputFolder) {
        byte[] buffer = new byte[1024];
        try {
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                int len;
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                Bukkit.getLogger().info("Unzipping: " + newFile.getAbsoluteFile());
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static FileConfiguration getFtpConfig() {
        return ftpConfig;
    }

    static {
        isAdminEventRunning = false;
        eventName = null;
        eventURL = null;
        maxClaims = -1;
    }
}

