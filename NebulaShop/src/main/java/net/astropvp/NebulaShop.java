package net.astropvp;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.astropvp.commands.NebulaCommand;
import net.astropvp.listeners.NebulaShopInventoryListener;
import net.astropvp.managers.NebulaShopManager;
import net.astropvp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class NebulaShop extends JavaPlugin {
    @Getter
    private static NebulaShop instance;

    @Getter
    private NebulaShopManager nebulaShopManager;

    public void onEnable() {
        instance = this;
        nebulaShopManager = new NebulaShopManager();
        getCommand("nebula").setExecutor(new NebulaCommand());
        getServer().getPluginManager().registerEvents(new NebulaShopInventoryListener(), this);
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            this.getConfig().addDefault("current-item", ConfigUtils.toBase64(new ItemStack[] { new ItemStack(Material.EMERALD)}, 9));
            this.getConfig().addDefault("current-price", 1);
            this.getConfig().addDefault("shop-location", ConfigUtils.locationToString(new Location(Bukkit.getWorld("world"), 0, 100, 0)));
            this.getConfig().addDefault("already-bought", new ArrayList<>());
        }
    }


    public void onDisable() {
        getNebulaShopManager().saveShop();
        instance = null;
    }
}
