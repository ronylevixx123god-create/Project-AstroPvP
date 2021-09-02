package me.bryside.weaponrunes;

import me.bryside.weaponrunes.commands.CommandGiveWeaponRune;
import me.bryside.weaponrunes.commands.CommandWeaponBonus;
import me.bryside.weaponrunes.items.WeaponRuneExtractor;
import me.bryside.weaponrunes.listeners.WeaponBonusListener;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class WeaponRunesPlugin extends JavaPlugin {
    private static WeaponRunesPlugin plugin;

    public void onEnable() {
        registerCommands();
        registerListeners();
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new WeaponRuneExtractor());

    }

    public void onDisable() {
        plugin = null;
    }

    public void registerCommands() {
        getCommand("giveweaponbonus").setExecutor((CommandExecutor)new CommandGiveWeaponRune());
        getCommand("weaponbonus").setExecutor((CommandExecutor) new CommandWeaponBonus());
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents((Listener)new WeaponBonusListener(), (Plugin)this);
    }

    public static WeaponRunesPlugin getInstance() {
        return plugin;
    }
}
