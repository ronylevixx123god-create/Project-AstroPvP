package net.astropvp;

import lombok.Getter;
import lombok.Setter;
import net.astropvp.commands.CarnageCommand;
import net.astropvp.managers.CarnageManager;
import net.astropvp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class Carnage extends JavaPlugin {
    @Getter
    private static Carnage instance;
    @Getter
    private CarnageManager carnageManager;
    @Getter
    @Setter
    private boolean isCarnageEnabled;

    public void onEnable() {
        instance = this;
        getCommand("carnage").setExecutor(new CarnageCommand());
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            this.getConfig().addDefault("enabled", false);
            this.getConfig().addDefault("carnage-items", new ArrayList<>());
        }
        this.carnageManager = new CarnageManager();
        this.isCarnageEnabled = getConfig().getBoolean("enabled");
    }

    public void onDisable() {
        this.carnageManager.saveConfig();
        saveConfig();
        instance = null;
    }
}
