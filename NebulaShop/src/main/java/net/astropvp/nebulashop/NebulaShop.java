package net.astropvp.nebulashop;

import lombok.Getter;
import lombok.Setter;
import net.astropvp.nebulashop.commands.NebulaCommand;
import net.astropvp.nebulashop.inventories.NebulaShopInventory;
import net.astropvp.nebulashop.listeners.NebulaShopInventoryListener;
import net.astropvp.nebulashop.listeners.NebulaShopListener;
import net.astropvp.nebulashop.managers.NebulaShopManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NebulaShop extends JavaPlugin {
    @Getter
    private static NebulaShop instance;

    @Getter
    private NebulaShopManager nebulaShopManager;

    @Getter
    @Setter
    private NebulaShopInventory inventory;

    public void onEnable() {
        instance = this;
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        nebulaShopManager = new NebulaShopManager();
        getCommand("nebula").setExecutor(new NebulaCommand());
        getServer().getPluginManager().registerEvents(new NebulaShopInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new NebulaShopListener(), this);
        this.inventory = new NebulaShopInventory();

    }


    public void onDisable() {
        getNebulaShopManager().saveShop();
        instance = null;
    }
}
