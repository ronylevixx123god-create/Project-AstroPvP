package me.astropvp.astrotournaments;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.astropvp.astrotournaments.data.ActiveTournamentData;
import me.astropvp.astrotournaments.data.player.helpers.PlayerDataHelper;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import me.astropvp.astrotournaments.struct.TournamentUser;
import me.astropvp.astrotournaments.tasks.TournamentStartTask;

public class AstroTournamentsAPI {
	
	private static Location lobbyLocation;
	private static Map<Integer, Location> positions;
	private static TournamentStartTask taskInstance;
	
	public static void init() {
		lobbyLocation = getConfigLobbyLocation();
		positions = getConfigSpawnLocations();
	}
	
	public static void onDisable() {
		if(taskInstance != null) {
			taskInstance.cancel();
			taskInstance = null;
		}
		if(lobbyLocation != null) {
			AstroTournamentsPlugin.getInstance().getConfig().set("lobby-location", LocationUtils.toStringLocation(lobbyLocation));
		}
		AstroTournamentsPlugin.getInstance().getConfig().set("spawn-locations", deserializeSpawnLocations());
		AstroTournamentsPlugin.getInstance().saveConfig();
		AstroTournamentsPlugin.getInstance().reloadConfig();
		AstroTournamentsAPI.endTournament();
	}
	
	public static void saveDataToDisk(TournamentUser user) {
		UUID uuid = user.getUUID();
		File file = PlayerDataHelper.getFile(uuid);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("wins", user.getWins());
		config.set("losses", user.getLosses());
		config.set("rewards", user.getRewardsSerialized());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFromDisk(UUID uuid) {
		File file = new File("player-data/" + uuid.toString());
		if(!file.exists()) {
			return;
		}
		file.delete();
	}
	
	public static void deleteFromDisk(TournamentUser user) {
		deleteFromDisk(user.getUUID());
	}
	
	public static void setLobbyLocation(Location location) {
		AstroTournamentsAPI.lobbyLocation = location;
	}
	
	public static void addSpawnLocation(int pos, Location location) {
		positions.put(pos, location);
	}
	
	public static TournamentUser receiveDataFromDisk(UUID uuid) {
		TournamentUser user = new TournamentUser(uuid);
		File file = PlayerDataHelper.getFile(uuid);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		user.setWins(config.contains("wins") ? config.getInt("wins") : 0);
		user.setLosses(config.contains("losses") ? config.getInt("losses") : 0);
		user.setRewardsSerialized(config.contains("rewards") ? config.getStringList("rewards") : Lists.newArrayList());
		return user;
	}
	
	public static Map<UUID, TournamentUser> receiveAllDiskData() {
		Map<UUID, TournamentUser> users = Maps.newHashMap();
		for(File file : PlayerDataHelper.getAllPlayerFiles()) {
			String name = file.getName();
			name = name.substring(0, name.indexOf(".yml"));
			UUID uuid = UUID.fromString(name);
			users.put(uuid, receiveDataFromDisk(uuid));
		}
		return users;
	}
	
	public static void resetAllStats() {
		new File("/player-data").delete();
	}
	
	public static void startTournament() {
		taskInstance = new TournamentStartTask();
	}
	
	public static void endTournament() {
		List<ActiveTournamentUser> users = ActiveTournamentData.getUsers();
		for(int i = users.size() - 1; i >= 0; i--) {
			ActiveTournamentUser user = users.get(i);
			user.remove();
			Player player = user.getPlayer();
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			player.sendMessage(CC.DarkPurple + "The Tournament has ended!");
		}
		ActiveTournamentData.getOpponents().clear();
		ActiveTournamentData.setActive(false);
	}
	
	public static boolean isActive() {
		return ActiveTournamentData.isActive();
	}
	
	public static int getTournamentSize() {
		return 32;
	}
	
	public static Location getSpawnLocation(int pos) {
		return positions.get(pos);
	}
	
	public static Location getConfigLobbyLocation() {
		String serialized = AstroTournamentsPlugin.getInstance().getConfig().contains("lobby-location") ?
				AstroTournamentsPlugin.getInstance().getConfig().getString("lobby-location") : null;
		if(serialized == null) {
			return null;
		}
		return LocationUtils.fromStringLocation(serialized);
	}
	
	public static Map<Integer, Location> getConfigSpawnLocations(){
		Map<Integer, Location> locations = Maps.newHashMap();
		List<String> values = AstroTournamentsPlugin.getInstance().getConfig().contains("spawn-locations") ?
		AstroTournamentsPlugin.getInstance().getConfig().getStringList("spawn-locations") : Lists.newArrayList();
		for(String value : values) {
			String[] split = value.split(":");
			locations.put(Integer.valueOf(split[0]), LocationUtils.fromStringLocation(split[1]));
		}
		return locations;
	}
	
	public static Location getLobbyLocation() {
		return lobbyLocation;
	}
	
	private static List<String> deserializeSpawnLocations() {
		List<String> deserialized = Lists.newArrayList();
		for(Entry<Integer, Location> entry : positions.entrySet()) {
			deserialized.add(entry.getKey() + ":" + LocationUtils.toStringLocation(entry.getValue()));
		}
		return deserialized;
	}
}
