package net.astropvp.armorsets;

import lombok.Getter;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import net.astropvp.armorsets.commands.CommandDebugArmorSet;
import net.astropvp.armorsets.commands.CommandGiveArmorSet;
import net.astropvp.armorsets.commands.CommandGiveHeroicArmor;
import net.astropvp.armorsets.commands.CommandGiveHeroicUpgrade;
import net.astropvp.armorsets.items.ArmorCrystalItem;
import net.astropvp.armorsets.items.CrystalExtractionItem;
import net.astropvp.armorsets.listeners.ArmorSetApplyListener;
import net.astropvp.armorsets.listeners.HeroicArmorDamageListener;
import net.astropvp.armorsets.listeners.HeroicUpgradeApplyListener;
import net.astropvp.damagehandler.commands.CommandDebugDamage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroArmorSets extends JavaPlugin {
    @Getter
    private static AstroArmorSets instance;



    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        registerCustomItems();
        System.out.println("[AstroArmorSets] Plugin has been enabled!");
    }


    public void onDisable() {
        System.out.println("[AstroArmorSets] Plugin has been disabled!");
        instance = null;
    }

    public void registerCommands() {
        getCommand("heroicarmor").setExecutor(new CommandGiveHeroicArmor());
        getCommand("armorset").setExecutor(new CommandGiveArmorSet());
        getCommand("heroicupgrade").setExecutor(new CommandGiveHeroicUpgrade());
        getCommand("debugarmorsets").setExecutor(new CommandDebugArmorSet());



    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new ArmorSetApplyListener(this), this);
        getServer().getPluginManager().registerEvents(new HeroicUpgradeApplyListener(), this);
        getServer().getPluginManager().registerEvents(new HeroicArmorDamageListener(), this);


    }

    public void registerCustomItems() {
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new CrystalExtractionItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new ArmorCrystalItem());
        }
    }
}
