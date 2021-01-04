/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.help;

import me.ifamasssxd.help.commands.CommandHelp;
import me.ifamasssxd.help.commands.CommandHelpEnchants;
import me.ifamasssxd.help.commands.CommandSpawnMenu;
import me.ifamasssxd.help.commands.CommandWiki;
import me.ifamasssxd.help.listeners.CommandListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicHelp
extends JavaPlugin {
    private static CosmicHelp instance;
    private CommandHelp helpCommand;

    public void onEnable() {
        instance = this;
        this.helpCommand = new CommandHelp();
        if (this.helpCommand.isEnchantsEnabled()) {
            this.getCommand("helpenchants").setExecutor((CommandExecutor)new CommandHelpEnchants());
        }
        this.getCommand("wiki").setExecutor((CommandExecutor)new CommandWiki());
        this.getCommand("spawnmenu").setExecutor((CommandExecutor)new CommandSpawnMenu());
        this.registerListeners();
    }

    public static CosmicHelp get() {
        return instance;
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new CommandListener(), (Plugin)this);
    }

    public CommandHelp getHelpCommand() {
        return this.helpCommand;
    }
}

