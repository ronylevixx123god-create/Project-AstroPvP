package me.astropvp.astrotournaments;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.astropvp.astrotournaments.commands.CommandAdminTournament;
import me.astropvp.astrotournaments.commands.CommandTournament;
import me.astropvp.astrotournaments.data.player.helpers.listeners.PlayerDataListener;
import me.astropvp.astrotournaments.listeners.DamageEventsListener;
import me.astropvp.astrotournaments.listeners.TLocationListener;
import me.astropvp.astrotournaments.listeners.TournamentLogicListener;
import me.astropvp.astrotournaments.tasks.TournamentRepeatingTask;

public class AstroTournamentsPlugin extends JavaPlugin {
	
	private static AstroTournamentsPlugin GalacticTournamentsPlugin;
	
	public static AstroTournamentsPlugin getInstance() {
		return GalacticTournamentsPlugin;
	}
	
	public static void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, GalacticTournamentsPlugin);
	}
	
	@Override
	public void onEnable() {
		Bukkit.getLogger().info("[ExoticTournaments] Tournament Plugin Enabled");
		GalacticTournamentsPlugin = this;
		AstroTournamentsAPI.init();
		registerListeners();
		registerCommands();
		registerTasks();
	}
	
	@Override
	public void onDisable() {
		AstroTournamentsAPI.onDisable();
		Bukkit.getLogger().info("[ExoticTournaments] Tournament Plugin Disabled");
		GalacticTournamentsPlugin = null;
	}
	
	private void registerListeners() {
		registerListener(new PlayerDataListener());
		registerListener(new DamageEventsListener());
		registerListener(new TournamentLogicListener());
		registerListener(new TLocationListener());
	}
	
	private void registerCommands() {
		getCommand("admintournament").setExecutor(new CommandAdminTournament());
		getCommand("tournament").setExecutor(new CommandTournament());
	}
	
	private void registerTasks() {
		new TournamentRepeatingTask().runTaskTimer(this, 20, 20);
	}
}
