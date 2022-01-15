package net.astropvp.armorsets;

import lombok.Getter;
import net.astropvp.armorsets.listeners.ArmorSetApplyListener;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroArmorSets extends JavaPlugin {
    @Getter
    private static AstroArmorSets instance;



    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        System.out.println("[AstroArmorSets] Plugin has been enabled!");
    }


    public void onDisable() {
        System.out.println("[AstroArmorSets] Plugin has been disabled!");
        instance = null;
    }

    public void registerCommands() {

    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new ArmorSetApplyListener(this), this);
    }
}
