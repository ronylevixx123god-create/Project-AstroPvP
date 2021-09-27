// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.config;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFile
{
    private JavaPlugin plugin;
    private File file;
    private FileConfiguration config;
    private String fileAsName;
    private String folderPath;
    
    public ConfigFile(final JavaPlugin pluginName, final String fileName) {
        this.plugin = pluginName;
        this.file = new File(this.plugin.getDataFolder(), fileName + ".yml");
        this.fileAsName = fileName;
        this.folderPath = "default/" + fileName + ".yml";
        this.setupConfig();
        this.config = (FileConfiguration)new YamlConfiguration();
        this.reloadConfig();
    }
    
    public ConfigFile(final JavaPlugin pluginName, final String fileName, final String folderPathName) {
        this.plugin = pluginName;
        this.file = new File(this.plugin.getDataFolder() + "/" + folderPathName, fileName + ".yml");
        this.fileAsName = fileName;
        this.folderPath = folderPathName + "/" + fileName + ".yml";
        this.setupConfig();
        this.config = (FileConfiguration)new YamlConfiguration();
        this.reloadConfig();
    }
    
    public ConfigFile(final JavaPlugin pluginName, final String fileName, final String... folderPathName) {
        this.plugin = pluginName;
        String path = "default";
        for (final String s : folderPathName) {
            path = path + "/" + s;
        }
        this.file = new File(this.plugin.getDataFolder() + "/" + path, fileName + ".yml");
        this.fileAsName = fileName;
        this.folderPath = path + "/" + fileName + ".yml";
        this.setupConfig();
        this.config = (FileConfiguration)new YamlConfiguration();
        this.reloadConfig();
    }
    
    public FileConfiguration getConfig() {
        return this.config;
    }
    
    public File getConfigFile() {
        return this.file;
    }
    
    public String getFileAsName() {
        return this.fileAsName;
    }
    
    public String getFileName() {
        return this.file.getName();
    }
    
    public String getFolderPath() {
        return this.folderPath;
    }
    
    public void saveConfig() {
        try {
            this.config.save(this.file);
        }
        catch (IOException ex) {}
    }
    
    public void reloadConfig() {
        try {
            this.config.load(this.file);
        }
        catch (Exception ex) {}
    }
    
    public void copyDefaults(final InputStream inputFile, final File outputFile) {
        try {
            final OutputStream output = new FileOutputStream(outputFile);
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = inputFile.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.close();
            inputFile.close();
        }
        catch (Exception ex) {}
    }
    
    private void setupConfig() {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            this.copyDefaults(this.plugin.getResource(this.file.getName()), this.file);
        }
    }
}
