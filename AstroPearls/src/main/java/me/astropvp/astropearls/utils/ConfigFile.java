package me.astropvp.astropearls.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.astropvp.astropearls.AstroPearls;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
    private File file = new File(AstroPearls.getInstance().getDataFolder(), "config.yml");
    private YamlConfiguration configuration;

    public ConfigFile() {
        if (!this.file.exists()) {
            AstroPearls.getInstance().saveResource("config.yml", false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void load() {
        this.file = new File(AstroPearls.getInstance().getDataFolder(), "config.yml");
        if (!this.file.exists()) {
            AstroPearls.getInstance().saveResource("config.yml", false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public File getFile() {
        return this.file;
    }

    public double getDouble(String path) {
        return this.configuration.contains(path) ? this.configuration.getDouble(path) : 0.0D;
    }

    public int getInt(String path) {
        return this.configuration.contains(path) ? this.configuration.getInt(path) : 0;
    }

    public boolean getBoolean(String path) {
        return this.configuration.contains(path) && this.configuration.getBoolean(path);
    }

    public String getString(String path) {
        return this.configuration.contains(path) ? ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path)) : "String at path: " + path + " not found!";
    }

    public long getLong(String path) {
        return this.configuration.contains(path) ? this.configuration.getLong(path) : 0L;
    }

    public List<String> getStringList(String path) {
        if (!this.configuration.contains(path)) {
            return Arrays.asList("String List at path: " + path + " not found!");
        } else {
            ArrayList<String> strings = new ArrayList();
            Iterator var3 = this.configuration.getStringList(path).iterator();

            while(var3.hasNext()) {
                String string = (String)var3.next();
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }

            return strings;
        }
    }
}
