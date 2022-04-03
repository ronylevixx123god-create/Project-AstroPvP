package me.astropvp.astrospacegrinding;

import lombok.Getter;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.astrospacegrinding.listeners.SpaceLogicListener;
import me.astropvp.astrospacegrinding.listeners.ViewItemsGUIListener;
import me.astropvp.astrospacegrinding.utils.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.astropvp.astrospacegrinding.commands.CommandAdminSpace;
import me.astropvp.astrospacegrinding.commands.CommandSpace;
import me.astropvp.astrospacegrinding.listeners.SpaceMobListener;
import me.astropvp.astrospacegrinding.tasks.NoFlyTask;
import me.astropvp.astrospacegrinding.tasks.SpawnMobsRepeatingTask;

import java.util.HashMap;
import java.util.Map;

public class AstroSpaceGrindingPlugin extends JavaPlugin {
	private static AstroSpaceGrindingPlugin astroSpaceGrindingPlugin;
	@Getter
	private ConfigManager configManager;
	public static HashMap<MonsterType, Map<ItemStack, Double>> cachedCrateLoot;


	@Override
	public void onEnable() {
		astroSpaceGrindingPlugin = this;
		registerListeners();
		registerCommands();
		startTasks();
		this.configManager = new ConfigManager(this);
		this.configManager.setupFiles();
		for (Entity entity : Bukkit.getWorld("space").getEntities()) {
			if (entity instanceof Player) continue;
			entity.remove();
		}
		cachedCrateLoot = new HashMap<>();
		for (MonsterType monsterType : MonsterType.values()) {
			cachedCrateLoot.put(monsterType, AstroSpaceGrindingAPI.getItems(monsterType));
		}
		Bukkit.getLogger().info("[AstroSpaceGrinding] - Cleared all entities in space world");
		Bukkit.getLogger().info("[AstroSpaceGrinding] - Enabling Astro Space Grinding");
	}
	
	@Override
	public void onDisable() {
		this.configManager = null;
		astroSpaceGrindingPlugin = null;
	}
	
	public void registerListeners() {
		registerListener(new SpaceLogicListener());
		registerListener(new SpaceMobListener());
		registerListener(new ViewItemsGUIListener());

	}
	
	public void registerCommands() {
		this.getCommand("space").setExecutor(new CommandSpace());
		this.getCommand("adminspace").setExecutor(new CommandAdminSpace());

	}
	
	public void startTasks() {
		new SpawnMobsRepeatingTask().runTaskTimer(this, 20, 20);
		new NoFlyTask().runTaskTimer(this, 20, 20);
	}
	
	public static void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, astroSpaceGrindingPlugin);
	}
	
	public static AstroSpaceGrindingPlugin getInstance() {
		return astroSpaceGrindingPlugin;
	}
}
