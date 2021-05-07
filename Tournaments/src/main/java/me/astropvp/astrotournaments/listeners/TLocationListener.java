package me.astropvp.astrotournaments.listeners;

import me.astropvp.astrotournaments.AstroTournamentsAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.galacticpvp.galacticpvputils.nbt.NBTWrapper;
import me.galacticpvp.galacticpvputils.utils.CC;

public class TLocationListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		if(!inventory.getTitle().equals(CC.Gray + "Tournament Locations")) {
			return;
		}
		event.setCancelled(true);
		if(event.getInventory() != event.getClickedInventory()) {
			return;
		}
		ItemStack item = event.getCurrentItem();
		if(item == null || item.getType() == Material.AIR) {
			return;
		}
		NBTWrapper wrapper = new NBTWrapper(item);
		if(!wrapper.hasKey("tlocation_pos")) {
			return;
		}
		int pos = wrapper.getInt("tlocation_pos");
		Location location = AstroTournamentsAPI.getSpawnLocation(pos);
		Player player = (Player) event.getWhoClicked();
		player.teleport(location);
		player.sendMessage(CC.Yellow + "Teleporting you to position #" + pos);
	}
}
