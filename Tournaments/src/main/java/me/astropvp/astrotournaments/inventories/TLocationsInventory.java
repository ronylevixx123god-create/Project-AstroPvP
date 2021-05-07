package me.astropvp.astrotournaments.inventories;

import java.util.Map;

import me.astropvp.astrotournaments.AstroTournamentsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.galacticpvp.galacticpvputils.nbt.NBTWrapper;
import me.galacticpvp.galacticpvputils.struct.InventoryWrapper;
import me.galacticpvp.galacticpvputils.utils.CC;
import me.galacticpvp.galacticpvputils.utils.ItemBuilder;

public class TLocationsInventory {
	public static Inventory getInventory() {
		Map<Integer, Location> locations = AstroTournamentsAPI.getConfigSpawnLocations();
		int size = (locations.size() / 9) * 9;
		if(size < locations.size()) {
			size += 9;
		}
		InventoryWrapper invWrapper = new InventoryWrapper(Bukkit.createInventory(null, size, CC.Gray + "Tournament Locations"));
		for(int i = 0; i < locations.size(); i++) {
			invWrapper.setContent(getLocationPane(i), i);
		}
		return invWrapper.get();
	}
	
	private static ItemStack getLocationPane(int pos) {
		Location location = AstroTournamentsAPI.getSpawnLocation(pos);
		ItemStack item = new ItemBuilder(Material.STAINED_GLASS_PANE, 1).damage((short)11).displayname(CC.RedB + "Location #" + pos)
		.lore(CC.Gray + "Click to teleport to the location", CC.Gray + location.getWorld().getName() + ": " + (int)location.getX() + "x, " + 
	    (int) location.getY() + "y, " + (int) location.getZ() + "z").build();
		NBTWrapper wrapper = new NBTWrapper(item);
		wrapper.setInteger("tlocation_pos", pos);
		return wrapper.build();
	}
}
