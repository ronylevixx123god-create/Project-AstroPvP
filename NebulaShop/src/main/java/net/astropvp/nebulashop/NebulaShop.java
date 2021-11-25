package net.astropvp.nebulashop;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import me.matthawkk.plugin.CustomPlugin;
import me.matthawkk.plugin.Plugins;
import net.astropvp.nebulashop.commands.NebulaCommand;
import net.astropvp.nebulashop.listeners.NebulaShopListener;
import net.astropvp.nebulashop.managers.NebulaShopManager;
import org.bukkit.ChatColor;

import java.io.File;

public class NebulaShop extends CustomPlugin {
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
