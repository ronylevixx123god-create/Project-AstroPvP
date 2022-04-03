package me.astropvp.astrospacegrinding.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingPlugin;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.bukkit.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.data.SpacePlayerData;


public class SpaceLogicListener implements Listener {

	
	private static List<String> allowedCommands = Lists.newArrayList();
	static {
		allowedCommands.addAll(Arrays.asList("msg", "message", "staffmode", "staff", "freeze", "lms",
		"koth", "ft", "filter", "r", "bless","blessme", "response", "space", "bless", "heal", "cf", "coinflip", "jackpot",
		"feed", "f", "fix", "filter edit", "filter toggle", "ft", "bless", "ah", "r", "reply", "spawn", "warp spawn"));
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWorldLeave(PlayerTeleportEvent event) {
		if(event.getTo() == null) {
			return;
		}
		if(!event.getFrom().getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
			if(event.getTo().getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
				new SpacePlayerData(event.getPlayer());
			}
			return;
		}
		Player player = event.getPlayer();

		if(!event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) {
			if (CombatLog.inCombat(player)) {
				return;
			}
			SpacePlayerData.remove(player);
			player.sendMessage("");
			player.sendMessage(CC.RedB + "(!) " + CC.Red + "As a result of leaving the Space, "
					+ "you have lost your /space stats!");
			player.sendMessage("");
			player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 0.75F, 2F);
			SpacePlayerData.remove(player);
		}
	}
	


	@EventHandler(priority = EventPriority.LOW)
	public void enchantProc(CustomEnchantProcOnEntityEvent e) {
		if (e.getEnchant().heroicEnchantment) e.setCancelled(true);
	}

	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onRightClickEgg(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if(event.getClickedBlock().getType() == Material.DRAGON_EGG) {
			event.setCancelled(true);
		}
	}

	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.isOp()) {
			return;
		}
		if(!player.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
			return;
		}
		if(event.isFlying()) {
			player.setAllowFlight(false);
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(!player.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
			return;
		}

		if (player.isOp()) {
			return;
		}

		String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();
		assert rank != null;
		if (rank.equalsIgnoreCase("srmod")) {
			return;
		}
		if (allowedCommands.contains(event.getMessage().toLowerCase().replace("/", ""))) {
			return;
		}
		player.sendMessage(CC.RedB + "(!) " + event.getMessage() + " is denied in this world!");
		event.setMessage("/null");
		event.setCancelled(true);
	}



}
