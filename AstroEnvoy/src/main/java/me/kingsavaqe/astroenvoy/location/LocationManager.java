package me.kingsavaqe.astroenvoy.location;

import me.kingsavaqe.astroenvoy.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {
    public void setLocation(FileManager.Config config, String path, Location loc, boolean saveFile) {
        FileConfiguration configLoad = config.getFileConfiguration();
        configLoad.set(path + ".world", loc.getWorld().getName());
        configLoad.set(path + ".x", Double.valueOf(loc.getX()));
        configLoad.set(path + ".y", Double.valueOf(loc.getY()));
        configLoad.set(path + ".z", Double.valueOf(loc.getZ()));
        configLoad.set(path + ".yaw", Float.valueOf(loc.getYaw()));
        configLoad.set(path + ".pitch", Float.valueOf(loc.getPitch()));
        if (saveFile)
            config.saveFile();
    }

    public Location getLocation(FileManager.Config config, String path) {
        FileConfiguration configLoad = config.getFileConfiguration();
        if (configLoad.contains(path)) {
            String w = configLoad.getString(path + ".world");
            double x = configLoad.getDouble(path + ".x");
            double y = configLoad.getDouble(path + ".y");
            double z = configLoad.getDouble(path + ".z");
            double yaw = configLoad.getDouble(path + ".yaw");
            double pitch = configLoad.getDouble(path + ".pitch");
            Location location = new Location(Bukkit.getServer().getWorld(w), x, y, z);
            location.setYaw((float)yaw);
            location.setPitch((float)pitch);
            return location;
        }
        return null;
    }
}
