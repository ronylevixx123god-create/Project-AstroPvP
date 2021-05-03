package me.astropvp.astroendgrinding;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.astropvp.astroendgrinding.commands.CommandAdminEnd;
import me.astropvp.astroendgrinding.commands.CommandEnd;
import me.astropvp.astroendgrinding.listeners.EndLogicListener;
import me.astropvp.astroendgrinding.listeners.EndMobListener;
import me.astropvp.astroendgrinding.tasks.NoFlyTask;
import me.astropvp.astroendgrinding.tasks.SpawnMobsRepeatingTask;

public class AstroEndGrindingPlugin extends JavaPlugin {
	private static AstroEndGrindingPlugin AstroEndGrindingPlugin;
	
	@Override
	public void onEnable() {
		AstroEndGrindingPlugin = this;
		registerListeners();
		registerCommands();
		startTasks();
		Bukkit.getLogger().info("[AstroEndGrinding] - Enabling Astro End Grinding");
	}
	
	@Override
	public void onDisable() {
		AstroEndGrindingPlugin = null;
	}
	
	public void registerListeners() {
		registerListener(new EndLogicListener());
		registerListener(new EndMobListener());
	}
	
	public void registerCommands() {
		this.getCommand("astroend").setExecutor(new CommandEnd());
		this.getCommand("adminend").setExecutor(new CommandAdminEnd());
	}
	
	public void startTasks() {
		new SpawnMobsRepeatingTask().runTaskTimer(this, 20, 20);
		new NoFlyTask().runTaskTimer(this, 20, 20);
	}
	
	public static void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, AstroEndGrindingPlugin);
	}
	
	public static AstroEndGrindingPlugin getInstance() {
		return AstroEndGrindingPlugin;
	}
}
