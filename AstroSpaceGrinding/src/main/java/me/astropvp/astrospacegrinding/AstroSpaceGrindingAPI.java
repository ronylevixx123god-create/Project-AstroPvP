package me.astropvp.astrospacegrinding;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import com.google.common.collect.Maps;
import me.astropvp.astrospacegrinding.utils.ConfigUtils;
import me.astropvp.astrospacegrinding.utils.LocationUtils;
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



	public static void setItems(MonsterType type, Map<ItemStack, Double> items) {
		String key = type.name().toLowerCase() + ".items";
		List<String> list = Lists.newArrayList();
		for (Map.Entry<ItemStack, Double> entry : items.entrySet()) {
			list.add(ConfigUtils.toBase64(new ItemStack[]{entry.getKey()}, 9) + ":" + entry.getValue());
		}
		AstroSpaceGrindingPlugin.getInstance().getConfig().set(key, list);
		AstroSpaceGrindingPlugin.getInstance().saveConfig();
		AstroSpaceGrindingPlugin.getInstance().reloadConfig();
	}

	public static Map<ItemStack, Double> getItems(MonsterType type) {
		String key = type.name().toLowerCase() + ".items";
		if (!AstroSpaceGrindingPlugin.getInstance().getConfig().contains(key))
			return Maps.newHashMap();
		Map<ItemStack, Double> map = Maps.newHashMap();
		List<String> items = AstroSpaceGrindingPlugin.getInstance().getConfig().getStringList(key);
		for (String item : items) {
			String[] split = item.split(":");
			map.put(Objects.requireNonNull(ConfigUtils.fromBase64(split[0])).getItem(0), Double.valueOf(split[1]));
		}
		return map;
	}

	public static List<ItemStack> getListItems(MonsterType type) {
		String key = type.name().toLowerCase() + ".items";
		if (!AstroSpaceGrindingPlugin.getInstance().getConfig().contains(key))
			return Lists.newArrayList();
		List<ItemStack> items = Lists.newArrayList();
		List<String> list = AstroSpaceGrindingPlugin.getInstance().getConfig().getStringList(key);
		for (String item : list) {
			String[] split = item.split(":");
			ItemStack it = Objects.requireNonNull(ConfigUtils.fromBase64(split[0])).getItem(0);
			int pos = 0;
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getType() == it.getType()) {
					pos = i + 1;
					break;
				}
			}
			if (pos > 0) {
				items.add(pos, it);
				continue;
			}
			items.add(it);
		}
		return items;
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
