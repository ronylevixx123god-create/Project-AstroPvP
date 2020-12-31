package net.astropvp.core;

import net.astropvp.core.commands.*;
import net.astropvp.core.listeners.*;
import net.astropvp.core.tasks.LastManStandingCapTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private final String consolePrefix = "[AstroCore]";
	private static Economy econ = null;

	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		setupEconomy();
		commands();
		events();
		new LastManStandingCapTask().runTaskTimer(this, 20L, 20L);

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


	public static Main getInstance() {
		return instance;
	}

	public static Economy getEcon() {
		return econ;
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

}
