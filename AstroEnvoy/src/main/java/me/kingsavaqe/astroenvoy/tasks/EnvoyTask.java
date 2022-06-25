package me.kingsavaqe.astroenvoy.tasks;


import java.io.File;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.menus.EnvoyMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;

public class EnvoyTask extends BukkitRunnable {
    private final AstroEnvoyPlugin instance;

    public EnvoyTask(AstroEnvoyPlugin instance) {
        this.instance = instance;
    }

    public void run() {
        FileConfiguration configLoad = this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        for (Player all : Bukkit.getOnlinePlayers()) {
            InventoryView inv = all.getOpenInventory();
            if (inv != null && inv.getTitle().equalsIgnoreCase(
                    ChatColor.translateAlternateColorCodes('&', configLoad.getString("Menus.Envoy.Title"))))
                EnvoyMenu.getInstance().open(all);
        }
    }
}
