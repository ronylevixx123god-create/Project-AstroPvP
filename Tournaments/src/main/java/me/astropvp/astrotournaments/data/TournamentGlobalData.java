package me.astropvp.astrotournaments.data;

import java.util.Map;
import java.util.UUID;

import me.astropvp.astrotournaments.AstroTournamentsAPI;
import org.bukkit.Bukkit;

import com.google.common.collect.Maps;

import me.astropvp.astrotournaments.AstroTournamentsPlugin;
import me.astropvp.astrotournaments.struct.TournamentUser;

public class TournamentGlobalData {
	
	private static Map<UUID, TournamentUser> data = Maps.newHashMap();
	
	public static void loadDataFromDisk() {
		Bukkit.getScheduler().runTaskAsynchronously(AstroTournamentsPlugin.getInstance(), () -> data.putAll(AstroTournamentsAPI.receiveAllDiskData()));
	}
	
	public static void removeStoredData(UUID uuid) {
		if(data.containsKey(uuid)) {
			data.remove(uuid);
		}
	}
	public static void savaDataToDisk(TournamentUser user) {
		Bukkit.getScheduler().runTaskAsynchronously(AstroTournamentsPlugin.getInstance(), () -> AstroTournamentsAPI.saveDataToDisk(user));
	}
	
	public static void removeCachedData(TournamentUser user) {
		UUID uuid = user.getUUID();
		if(data.containsKey(uuid)) {
			data.remove(uuid);
		}
	}
	
	public static void addCachedData(TournamentUser user) {
		data.put(user.getUUID(), user);
	}
	
	public static void clearCachedData() {
		data.clear();
	}
	
	public static Map<UUID, TournamentUser> getAllUserData() {
		return data;
	}
}
