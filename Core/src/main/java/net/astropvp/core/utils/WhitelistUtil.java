package net.astropvp.core.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WhitelistUtil {

    public static File configFile;
    public static YamlConfiguration config;

    // action passed needs to be "add" or "remove"
    public static void whitelist(String action) {

        configFile = new File(AstroCore.getInstance().getDataFolder(), "whitelist.yml");
        if (!configFile.exists()) {
            return;
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        for (String val : config.getDefaultSection().getKeys(false)) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist " + action + " " + val);
            Bukkit.broadcastMessage(CC.Gray + "(Whitelist) "+ CC.Yellow + "Automatically " + action + "ed " + val + ". ");
        }
    }
}
