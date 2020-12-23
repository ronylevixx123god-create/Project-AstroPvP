/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  net.minecraft.util.org.apache.commons.io.FileUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.apache.commons.io.FileUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ShopCommand
        extends ArkPlugin {
    public static Location shop_location = null;

    public ShopCommand(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.loadShopLocation();
        this.registerListener(new ShopCommandListener());
        this.registerCommand(new ShopCommandCommand("shoptp", "/<command>", "Teleports user to the Cosmic Exchange.", new ArrayList<String>(Arrays.asList(new File("plugins/CosmicShopGUI.jar").exists() ? "storetp" : "shop"))));
    }

    @Override
    public void disable() {
    }

    private void loadShopLocation() {
        String shopLocationString = Arkkit.get().getConfig().getString("settings.shop_command.shop-location");
        if (new File("plugins/ArkhamShops/config.yml").exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/ArkhamShops/config.yml"));
            shopLocationString = config.getString("shop-location");
            Arkkit.get().getConfig().set("settings.shop_command.shop-location", shopLocationString);
            try {
                FileUtils.deleteDirectory(new File("plugins/ArkhamShops"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (shopLocationString.equals("0,0,0")) {
            if (Bukkit.getIp().equalsIgnoreCase("10.0.0.27") || Bukkit.getIp().equalsIgnoreCase("10.0.0.33") || Bukkit.getIp().equalsIgnoreCase("10.0.0.47")) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 38.0, 111.0, 91.0, -136.0f, 2.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.28")) {
                shop_location = new Location(Bukkit.getWorlds().get(0), -191.0, 151.0, 59.0, 180.0f, 3.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.29") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 151.0, 152.0, 124.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.65") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 295.0, 194.0, 229.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.56") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), -376.0, 202.0, 452.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.45") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), -40.0, 235.0, -11.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.30") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 722.0, 160.0, 343.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.64") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 11.0, 163.0, -11.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.67") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 14.0, 165.0, 312.0, 0.0f, 0.0f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.43") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), 158.0, 164.0, 586.0, -102.2f, -14.7f);
            } else if (Bukkit.getIp().equalsIgnoreCase("10.0.0.80") && Bukkit.getPort() == 10001) {
                shop_location = new Location(Bukkit.getWorlds().get(0), -8.0, 170.0, 47.0, 27.0f, -1.0f);
            }
            shopLocationString = LocationUtils.convertLocationToString(shop_location);
            Arkkit.get().getConfig().set("settings.shop_command.shop-location", shopLocationString);
        } else {
            shop_location = LocationUtils.convertStringToLocation(shopLocationString);
        }
    }
}

