package me.astropvp.astrotournaments.data.player.helpers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import me.astropvp.astrotournaments.AstroTournamentsPlugin;
import me.astropvp.astrotournaments.data.player.PlayerData;
import me.astropvp.astrotournaments.data.player.helpers.listeners.PlayerDataListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;


public class PlayerDataHelper {

	private static List<PlayerData> data;

	public static void init() {
		AstroTournamentsPlugin.registerListener(new PlayerDataListener());
		data = Lists.newArrayList();
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data");
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static void onDisable() {
		for (int i = PlayerDataHelper.data.size() - 1; i >= 0; i--) {
			PlayerData data = PlayerDataHelper.data.get(i);
			data.removeSafely();
		}
	}

	public PlayerDataHelper(PlayerData data) {
		PlayerDataHelper.data.add(data);
	}

	public static void createConfiguration(Player player) {
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/"
				+ player.getUniqueId().toString() + ".yml");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setConfigValue(OfflinePlayer player, String key, Object value) {
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/"
				+ player.getUniqueId().toString() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set(key, value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static YamlConfiguration getConfiguration(OfflinePlayer player) {
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/"
				+ player.getUniqueId().toString() + ".yml");
		if (!file.exists()) {
			return null;
		}
		return YamlConfiguration.loadConfiguration(file);
	}

	public static List<File> getAllPlayerFiles() {
		List<File> files = Lists.newArrayList();
		for (File file : new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/")
				.listFiles()) {
			files.add(file);
		}
		return files;
	}

	public static int removeDataFromFiles(String path) {
		int count = 0;
		for (File file : new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/")
				.listFiles()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set(path, null);
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			count++;
		}
		return count;
	}

	public static File getFile(OfflinePlayer player) {
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/"
				+ player.getUniqueId().toString() + ".yml");
		if (!file.exists()) {
			return null;
		}
		return file;
	}
	
	public static File getFile(UUID uuid) {
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/"
				+ uuid.toString() + ".yml");
		if (!file.exists()) {
			return null;
		}
		return file;
	}

	public static boolean doesConfigurationExist(Player player) {
		File file = new File(AstroTournamentsPlugin.getInstance().getDataFolder().toString() + "/player-data/"
				+ player.getUniqueId().toString() + ".yml");
		return file.exists();
	}

	public static List<PlayerData> getPlayerData() {
		return data;
	}

	public static PlayerData findPlayerData(UUID uuid) {
		for (PlayerData data : PlayerDataHelper.data) {
			if (data.getUUID().equals(uuid)) {
				return data;
			}
		}
		Bukkit.getPlayer(uuid).kickPlayer("You have invalid Player Data! Report this to an Admin!");
	    return null;
	}

	public static PlayerData findPlayerData(Player player) {
		if (player.hasMetadata("etournament_player_cacher")) {
			return (PlayerData) player.getMetadata("etournament_player_cacher").get(0).value();
		}
		UUID uuid = player.getUniqueId();
		for (PlayerData data : PlayerDataHelper.data) {
			if (data.getUUID().equals(uuid)) {
				return data;
			}
		}
		return null;
	}
	
	public static UUID getUUIDFromFile(File file) {
		return UUID.fromString(file.getName().substring(0, file.getName().indexOf(".")));
	}

}
