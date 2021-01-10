/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.compilex.CompilexPermissions.manager;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.configuration.PermissionsConfig;
import com.compilex.CompilexPermissions.database.DatabaseInformation;
import com.compilex.CompilexPermissions.logger.PermissionsLogger;
import com.compilex.CompilexPermissions.structure.PermissionsManager;
import com.compilex.CompilexPermissions.utils.DownloadUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationManager
implements PermissionsManager {
    private static final CompilexPermissions plugin = CompilexPermissions.getInstance();
    private FileConfiguration cachedWebConfiguration;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean onEnable() {
        plugin.saveDefaultConfig();
        FileConfiguration plugin_Config = plugin.getConfig();
        PermissionsConfig.SERVER_NAME = plugin_Config.getString("SERVER_NAME");
        PermissionsConfig.SHARED_PERMISSIONS = plugin_Config.getStringList("SHARED_PERMISSIONS");
        try {
            PermissionsConfig.WEB_GROUP_CONFIG_URL = new URL(plugin_Config.getString("WEB_GROUP_CONFIG_URL"));
        }
        catch (MalformedURLException ex) {
            return false;
        }
        new DatabaseInformation(plugin_Config.getString("DATABASE.HOST"), plugin_Config.getInt("DATABASE.PORT"), plugin_Config.getString("DATABASE.USER"), plugin_Config.getString("DATABASE.PASS"), plugin_Config.getString("DATABASE.DATABASENAME"));
        PermissionsLogger.logMessage("Attempting to download the web configuration from " + PermissionsConfig.WEB_GROUP_CONFIG_URL);
        InputStream webStream = null;
        try {
            webStream = DownloadUtils.downloadInputStream(PermissionsConfig.WEB_GROUP_CONFIG_URL);
            if (webStream.markSupported()) {
                webStream.mark(Integer.MAX_VALUE);
                if (webStream != null) {
                    int len;
                    File f = new File("plugins/CompilexPermissions/PermissionsConfig.yml");
                    if (f.exists()) {
                        f.delete();
                        f.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream("plugins/CompilexPermissions/PermissionsConfig.yml");
                    byte[] buffer = new byte[1024];
                    while ((len = webStream.read(buffer)) != -1) {
                        ((OutputStream)out).write(buffer, 0, len);
                    }
                    out.flush();
                    ((OutputStream)out).close();
                }
                webStream.reset();
            } else {
                Bukkit.getLogger().warning("InputStream mark/read not supported, skipping cache attempt.");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        try {
            this.cachedWebConfiguration = YamlConfiguration.loadConfiguration((InputStream)webStream);
        }
        finally {
            if (webStream != null) {
                try {
                    webStream.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        PermissionsLogger.logMessage("WebConfiguration downloaded successfuly.");
        return true;
    }

    @Override
    public boolean onDisable() {
        return true;
    }

    public FileConfiguration getCachedWebConfiguration() {
        return this.cachedWebConfiguration;
    }

    public void setCachedWebConfiguration(FileConfiguration cachedWebConfiguration) {
        this.cachedWebConfiguration = cachedWebConfiguration;
    }
}

