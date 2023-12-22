package me.astropvp.astrospacegrinding;

import java.util.List;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import me.astropvp.astrospacegrinding.utils.LocationUtils;
import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.struct.LootTable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.Lists;

import me.astropvp.astrospacegrinding.enums.MonsterType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AstroSpaceGrindingAPI {
	private static final String WORLD_NAME = "space";
	
	public static void addLocation(MonsterType type, Location location) {
		FileConfiguration file = AstroSpaceGrindingPlugin.getInstance().getConfig();
		List<String> locations = file.contains("boss-locations") ? file.getStringList("boss-locations") :
		Lists.newArrayList();
		locations.add(type.name() + ":" + LocationUtils.toStringLocation(location));
		file.set("boss-locations", locations);
		AstroSpaceGrindingPlugin.getInstance().saveConfig();
		AstroSpaceGrindingPlugin.getInstance().reloadConfig();
	}
	
	public static void removeLocations(MonsterType type) {
		FileConfiguration file = AstroSpaceGrindingPlugin.getInstance().getConfig();
		List<String> locations = file.contains("boss-locations") ? file.getStringList("boss-locations") :
		Lists.newArrayList();
		if(locations.isEmpty()) {
			return;
		}
		String name = type.name();
		locations.removeIf(location -> location.split(":")[0].equals(name));
		file.set("boss-locations", locations);
		AstroSpaceGrindingPlugin.getInstance().saveConfig();
		AstroSpaceGrindingPlugin.getInstance().reloadConfig();
	}
	
	public static Object[] getValues(String value) {
		String[] values = value.split(":");
		return new Object[] {MonsterType.valueOf(values[0]), LocationUtils.fromStringLocation(values[1])};
	}
	
	public static List<String> getBossLocations(){
		FileConfiguration file = AstroSpaceGrindingPlugin.getInstance().getConfig();
		return file.contains("boss-locations") ? file.getStringList("boss-locations") :
		Lists.newArrayList();
	}
	
	public static String getWorldName() {
		return WORLD_NAME;
	}
	
	public static World getWorld() {
		return Bukkit.getWorld(WORLD_NAME);
	}

	public static List<ItemStack> getListItems(MonsterType type) {
		return LootTableAPI.getAllLootFromLootTable(LootTable.RUINS);
	}


	public static Inventory getLootInventory(MonsterType type) {
		List<ItemStack> items = AstroSpaceGrindingAPI.getListItems(type);
		Inventory inventory = Bukkit.createInventory(null, InventoryUtils.fitSize(items.size()), CC.DarkGray + type.getName() + " Loot Items");
		int pos = 0;
		for (ItemStack item : items)
			inventory.setItem(pos++, item);

		return inventory;
	}


}
