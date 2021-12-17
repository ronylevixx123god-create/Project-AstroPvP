package net.astropvp.christmas;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import me.matthawkk.plugin.BasePlugin;
import me.matthawkk.plugin.Plugins;
import net.astropvp.christmas.commands.ChristmasCommand;
import net.astropvp.christmas.managers.ChristmasManager;
import org.bukkit.ChatColor;

import java.io.File;

public class Christmas extends BasePlugin {
    @Getter
    private static Christmas instance;

    @Getter
    private ChristmasManager christmasManager;


    public void load() {
        instance = this;
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        christmasManager = new ChristmasManager();
        getCommand("christmas").setExecutor(new ChristmasCommand());
        this.defaultColor = ChatColor.LIGHT_PURPLE;
        this.pluginPrefix = CC.DarkRedB + "<" + CC.DarkGreenB + "Christmas" + CC.DarkRedB + ">";
    }

    public void unload() {
        getChristmasManager().saveShop();
        instance = null;
    }
}
