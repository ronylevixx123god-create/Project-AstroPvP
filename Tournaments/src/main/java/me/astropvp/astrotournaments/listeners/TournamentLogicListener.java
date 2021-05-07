package me.astropvp.astrotournaments.listeners;

import me.astropvp.astrotournaments.data.ActiveTournamentData;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import me.astropvp.astrotournaments.struct.CommandReference;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.exoticpvp.exoticarmorsets.armor.enums.ArmorSetType;

import me.galacticpvp.galacticpvputils.nbt.NBTWrapper;
import me.galacticpvp.galacticpvputils.utils.CC;

public class TournamentLogicListener implements Listener {
	private static CommandReference whitelistedCommands;
	
	static {
		whitelistedCommands = new CommandReference().append("message").append("msg").append("r").append("tournament").append("gamemode").append("gm")
		.append("t").append("events").append("ft").append("filter");
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		user.remove();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(player.isOp()) {
			return;
		}
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		String text = event.getMessage();
		text = text.substring(1, text.length());
		if(!whitelistedCommands.isReferenced(text)) {
			player.sendMessage(CC.RedB + "(!) " + CC.Red + "That command is blocked while in a tournament!");
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChooseSetClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		if(!inventory.getTitle().equals(CC.Gray + "Tournament: Choose your set")) {
			return;
		}
		event.setCancelled(true);
		if(inventory != event.getClickedInventory()) {
			return;
		}
		ItemStack item = event.getCurrentItem();
		if(item == null || item.getType() == Material.AIR) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		NBTWrapper wrapper = new NBTWrapper(item);
		if(!wrapper.hasKey("t_choose_set")) {
			return;
		}
		player.closeInventory();
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 25F, 25F);
		ArmorSetType set = ArmorSetType.valueOf(wrapper.getString("t_choose_set"));
		player.sendMessage(CC.Green + "You have chosen the " + set.getColor() + set.getName() + " " + CC.Green + "Armor Set!");
		user.setArmorSet(set);
	}
}
