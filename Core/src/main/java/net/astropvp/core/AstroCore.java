package net.astropvp.core;

import me.ifamasssxd.help.commands.CommandHelp;
import me.ifamasssxd.help.commands.CommandHelpEnchants;
import me.ifamasssxd.help.commands.CommandSpawnMenu;
import me.ifamasssxd.help.commands.CommandWiki;
import me.ifamasssxd.help.listeners.CommandListener;
import me.ifamasssxd.killtracker.commands.CommandGiveSoulGem;
import me.ifamasssxd.killtracker.commands.CommandGiveTracker;
import me.ifamasssxd.killtracker.commands.CommandWithdrawSouls;
import me.ifamasssxd.killtracker.listeners.ItemListener;
import me.ifamasssxd.killtracker.listeners.PlayerListener;
import me.ifamasssxd.killtracker.listeners.SoulGemListener;
import net.astropvp.core.commands.*;
import net.astropvp.core.listeners.*;
import net.astropvp.core.tasks.LastManStandingCapTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroCore extends JavaPlugin {

    private static AstroCore instance;
    private static Economy econ = null;
    private final String consolePrefix = "[AstroCore]";

    public static AstroCore getInstance() {
        return instance;
    }

    public static Economy getEcon() {
        return econ;
    }

    private CommandHelp commandHelp;
    public static String LORE_SUFFIX = ":  ";

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setupEconomy();
        commands();
        events();
        new LastManStandingCapTask().runTaskTimer(this, 20L, 20L);

        this.commandHelp = new CommandHelp();
        if (commandHelp.isEnchantsEnabled()) {
            this.getCommand("helpenchants").setExecutor(new CommandHelpEnchants());
        }
        this.getCommand("wiki").setExecutor(new CommandWiki());
        this.getCommand("spawnmenu").setExecutor(new CommandSpawnMenu());
        this.getCommand("withdrawsouls").setExecutor(new CommandWithdrawSouls());
        this.getCommand("givetracker").setExecutor(new CommandGiveTracker());
        this.getCommand("givesoulgem").setExecutor(new CommandGiveSoulGem());

        this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new SoulGemListener(), this);
    }

    public void commands() {
        getCommand("bless").setExecutor(new Bless());
        getCommand("dupe").setExecutor(new CommandDupe());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("pots").setExecutor(new PotionsCommand(this));
        getCommand("broadcast").setExecutor(new Broadcast(this));
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("givechunkbuster").setExecutor(new ChunkBuster(this));
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("lastmanstanding").setExecutor(new LastManStandingCommand());
    }

    public void events() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new DungeonInventoryEvent(), this);
        pm.registerEvents(new AutoOP(), this);
        pm.registerEvents(new ServerFixes(), this);
        pm.registerEvents(new MOTD(), this);
        pm.registerEvents(new HeroicArmorFix(), this);
        pm.registerEvents(new MobRewards(), this);
        pm.registerEvents(new PotionFix(), this);
        pm.registerEvents(new ChunkBusterListener(), this);
        pm.registerEvents(new OmniGemFix(), this);
        pm.registerEvents(new CommandAliases(), this);
        pm.registerEvents(new FirstJoin(), this);
        pm.registerEvents(new CombatLogBlock(), this);
        pm.registerEvents(new DamageFixes(), this);
        pm.registerEvents(new LastManStandingListener(), this);
        pm.registerEvents(new MobSpawnerFix(), this);
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return (econ != null);
    }

    public CommandHelp getHelpCommand() {
        return this.commandHelp;
    }

}
