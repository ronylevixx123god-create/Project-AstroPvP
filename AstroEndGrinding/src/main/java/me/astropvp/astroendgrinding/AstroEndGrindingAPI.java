package me.astropvp.astroendgrinding;

import java.util.List;

import me.astropvp.astroendgrinding.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.Lists;

import me.astropvp.astroendgrinding.enums.MonsterType;

public class AstroEndGrindingAPI {
	private static String WORLD_NAME = "world_the_end";
	
	public static void addLocation(MonsterType type, Location location) {
		FileConfiguration file = AstroEndGrindingPlugin.getInstance().getConfig();
		List<String> locations = file.contains("boss-locations") ? file.getStringList("boss-locations") :
		Lists.newArrayList();
		locations.add(type.name() + ":" + LocationUtils.toStringLocation(location));
		file.set("boss-locations", locations);
		AstroEndGrindingPlugin.getInstance().saveConfig();
		AstroEndGrindingPlugin.getInstance().reloadConfig();
	}
	
	public static void removeLocations(MonsterType type) {
		FileConfiguration file = AstroEndGrindingPlugin.getInstance().getConfig();
		List<String> locations = file.contains("boss-locations") ? file.getStringList("boss-locations") :
		Lists.newArrayList();
		if(locations.isEmpty()) {
			return;
		}
		String name = type.name();
		locations.removeIf(location -> location.split(":")[0].equals(name));
		file.set("boss-locations", locations);
		AstroEndGrindingPlugin.getInstance().saveConfig();
		AstroEndGrindingPlugin.getInstance().reloadConfig();
	}
	
	public static Object[] getValues(String value) {
		String[] values = value.split(":");
		return new Object[] {MonsterType.valueOf(values[0]), LocationUtils.fromStringLocation(values[1])};
	}
	
	public static List<String> getBossLocations(){
		FileConfiguration file = AstroEndGrindingPlugin.getInstance().getConfig();
		return file.contains("boss-locations") ? file.getStringList("boss-locations") :
		Lists.newArrayList();
	}
	
	public static String getWorldName() {
		return WORLD_NAME;
	}
	
	public static World getWorld() {
		return Bukkit.getWorld(WORLD_NAME);
	}
}
