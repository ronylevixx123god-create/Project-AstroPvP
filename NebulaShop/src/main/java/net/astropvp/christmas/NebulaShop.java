package net.astropvp.christmas;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import me.matthawkk.plugin.BasePlugin;
import me.matthawkk.plugin.Plugins;
import net.astropvp.christmas.commands.NebulaCommand;
import net.astropvp.christmas.listeners.NebulaShopListener;
import net.astropvp.christmas.managers.NebulaShopManager;
import org.bukkit.ChatColor;

import java.io.File;

public class NebulaShop extends BasePlugin {
    @Getter
    private static NebulaShop instance;

    @Getter
    private NebulaShopManager nebulaShopManager;


    public void load() {
        instance = this;
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        nebulaShopManager = new NebulaShopManager();
        getCommand("nebula").setExecutor(new NebulaCommand());
        Plugins.registerListeners(this, new NebulaShopListener());
        this.defaultColor = ChatColor.LIGHT_PURPLE;
        this.pluginPrefix = CC.DarkPurpleB + "<" + CC.LightPurpleB + "Nebula Shop" + CC.DarkPurpleB + ">";
    }

    public void unload() {
        getNebulaShopManager().saveShop();
        instance = null;
    }
}
