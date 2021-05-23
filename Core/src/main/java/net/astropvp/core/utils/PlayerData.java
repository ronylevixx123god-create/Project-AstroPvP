package net.astropvp.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Maps;



public class PlayerData {

	private UUID uuid;
	private String player;
	private Map<String, Object> map;
	private boolean saving;

	public PlayerData(Player player) {
		this.player = player.getName();
		this.uuid = player.getUniqueId();
		this.map = Maps.newHashMap();
		this.saving = false;
		player.setMetadata("player_cacher", new FixedMetadataValue(AstroCore.getInstance(), this));
	}

	public void addStoredValue(String key, Object value) {
		this.map.put(key, value);
	}
	
	public boolean isSaving() {
		return this.saving;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
	}

	public String getPlayerName() {
		return this.player;
	}

	public Object getStoredValue(String key) {
		return this.map.get(key);
	}

	public boolean hasStoredValue(String key) {
		return this.map.containsKey(key);
	}

	public boolean hasConfigValue(String key) {
		return this.getConfiguration().contains(key);
	}

	public boolean hasValue(String key) {
		boolean value = hasConfigValue(key) || hasStoredValue(key);
		if(!value) {
			setDefaultPath(key, null);
		}
		return value;
	}

	public YamlConfiguration getConfiguration() {
		return YamlConfiguration
				.loadConfiguration(new File(AstroCore.getInstance().getDataFolder().toString()
						+ "/player-data/" + uuid.toString() + ".yml"));
	}

	public void setDefaultPath(String path, Object value) {
		this.map.put(path, value);
	}

	public Map<String, Object> getStoredValues() {
		return this.map;
	}

	public void setStoredValues(Map<String, Object> map) {
		this.map = map;
	}

	public Object getValue(String key) {

		if (this.map.containsKey(key)) {
			return this.map.get(key);
		}

		Object value = getConfiguration().get(key);
		this.map.put(key, value);
		return value;
	}

	public void save() {
		if(this.isSaving()) {
			return;
		}
		this.saving = true;
		File file = new File(AstroCore.getInstance().getDataFolder().toString() + "/player-data/"
				+ uuid.toString() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		Bukkit.getLogger().info("[PlayerData] Saving " + this.getPlayerName() + "'s Data to the disk");
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			configuration.set(key, value);
		}

		try {
			configuration.save(file);
			this.saving = false;
		} catch (IOException e) {
			e.printStackTrace();
			this.saving = false;
		}

	}

	@Deprecated
	public void remove() {
		PlayerDataHelper.getPlayerData().remove(this);
		this.getPlayer().removeMetadata("player_cacher", AstroCore.getInstance());
	}

	@Deprecated
	public void removeStoredMetaData() {
		this.getPlayer().removeMetadata("player_cacher", AstroCore.getInstance());
	}

	@Deprecated
	public void removeStoredData() {
		PlayerDataHelper.getPlayerData().remove(this);
	}

	public void removeSafely() {
		this.save();
		this.remove();
	}

}
