package me.astropvp.astroendgrinding.listeners;

import java.util.Arrays;
import java.util.List;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astroendgrinding.AstroEndGrindingPlugin;
import me.astropvp.astroendgrinding.utils.EntityDamageEvent;
import me.astropvp.astroendgrinding.utils.PlayerUtils;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.data.EndPlayerData;


public class EndLogicListener implements Listener {
	
	private static List<String> allowedCommands = Lists.newArrayList();
	static {
		allowedCommands.addAll(Arrays.asList("msg", "message", "staffmode", "staff", "freeze", "lms",
		"koth", "ft", "filter", "r", "response", "end", "bless", "heal", "cf", "coinflip", "jackpot",
		"feed", "f", "fix"));
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWorldLeave(PlayerTeleportEvent event) {
		if(event.getTo() == null) {
			return;
		}
		if(!event.getFrom().getWorld().getName().equals(AstroEndGrindingAPI.getWorldName())) {
			if(event.getTo().getWorld().getName().equals(AstroEndGrindingAPI.getWorldName())) {
				new EndPlayerData(event.getPlayer());
			}
			return;
		}
		Player player = event.getPlayer();
		if(event.getCause() == TeleportCause.END_PORTAL) {
			event.setCancelled(true);
			if (CombatLog.inCombat(player)) {
				return;
			}
			player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			EndPlayerData.remove(player);
			return;
		}
		
		if(!event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) {
			EndPlayerData.remove(player);
			player.sendMessage("");
			player.sendMessage(CC.YellowB + "(!) " + CC.Yellow + "As a result of leaving the End World, "
					+ "you have lost your /end stats!");
			player.sendMessage("");
			player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 0.75F, 2F);
			EndPlayerData.remove(player);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void markLastHit(EntityDamageEvent event) {
		if(event.getDamager() == null || !event.getEntity().getWorld().getName()
		.equals(AstroEndGrindingAPI.getWorldName())) {
			return;
		}
		Entity damager = event.getRealDamager();
		LivingEntity entity = event.getEntity();
		if(!(damager instanceof Player) || !(entity instanceof Player)) {
			return;
		}
		Player pdamager = (Player) damager;
		Player pentity = (Player) entity;
		pentity.setMetadata("last_hit", new FixedMetadataValue(AstroEndGrindingPlugin.getInstance(),
		pdamager.getName()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onKill(EntityDamageEvent event) {
		if(!event.getEntity().getWorld().getName()
				.equals(AstroEndGrindingAPI.getWorldName()) || event.getDamageCause() != DamageCause.VOID) {
			return;
		}
		LivingEntity entity = event.getEntity();
		if(!(entity instanceof Player)) {
			return;
		}
		Player pentity = (Player) entity;
		pentity.sendMessage("");
		pentity.sendMessage(CC.YellowB + "(!) " + CC.Yellow + "You were knocked off of end!");
		pentity.sendMessage("");
		Player last_damager = pentity.hasMetadata("last_hit") ? Bukkit.getPlayer(pentity.getMetadata("last_hit").get(0).asString()) : null;
		if(last_damager == null || !last_damager.isOnline() || 
		!last_damager.getWorld().getName().equals(AstroEndGrindingAPI.getWorldName())) {
			PlayerUtils.killEntity(pentity);
			return;
		}
		pentity.teleport(last_damager);
		PlayerUtils.killEntity(pentity);
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
		if(!player.getWorld().getName().equals(AstroEndGrindingAPI.getWorldName())) {
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
		if(!player.getWorld().getName().equals(AstroEndGrindingAPI.getWorldName()) || player.isOp()) {
			return;
		}
		String cmd = event.getMessage().substring(1, event.getMessage().length());
		String[] cmdSplit = cmd.indexOf(" ") != -1 ? cmd.split(" " ) : new String[] {cmd};
		for(String command : allowedCommands) {
			String[] commandSplit = command.indexOf(" ") != -1 ? command.split(" ") : new String[] {command};
			if(commandSplit.length < cmdSplit.length) {
				String[] temporary = new String[commandSplit.length];
				for(int i = 0; i < temporary.length; i++) {
					temporary[i] = commandSplit[i];
				}
			}
			if(commandSplit.equals(cmdSplit)) {
				return;
			}
		}
		player.sendMessage(CC.RedB + "(!) " + CC.Red + "/" + cmd + " is denied in this world!");
		event.setCancelled(true);
	}
}
