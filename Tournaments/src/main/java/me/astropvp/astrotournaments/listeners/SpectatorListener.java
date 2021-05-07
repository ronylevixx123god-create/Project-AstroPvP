package me.astropvp.astrotournaments.listeners;

import me.astropvp.astrotournaments.data.ActiveTournamentData;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.galacticpvp.galacticpvputils.utils.CC;

public class SpectatorListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryChange(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		if(user.isSpectating()) {
			event.setCancelled(true);
		}
	}
}
