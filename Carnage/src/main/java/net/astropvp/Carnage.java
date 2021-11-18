package net.astropvp;

import lombok.Getter;
import net.astropvp.managers.CarnageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Carnage extends JavaPlugin {
    @Getter
    private static Carnage instance;
    @Getter
    private CarnageManager carnageManager;

    public void onEnable() {
        instance = this;
        this.carnageManager = new CarnageManager();
    }

    public void onDisable() {
        this.carnageManager.saveConfig();
        instance = null;
    }
}
