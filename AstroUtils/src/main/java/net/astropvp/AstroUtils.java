package net.astropvp;

import org.bukkit.plugin.java.JavaPlugin;

public class AstroUtils extends JavaPlugin {
    private static AstroUtils instance;

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {
        instance = null;
    }

    public static AstroUtils get() {
        return instance;
    }
}
