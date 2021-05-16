package me.astropvp.astroendgrinding.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astroendgrinding.AstroEndGrindingPlugin;
import me.astropvp.astroendgrinding.utils.PlayerUtils;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.data.EndPlayerData;


public class EndLogicListener implements Listener {
	
	private static List<String> allowedCommands = Lists.newArrayList();
	static {
		allowedCommands.addAll(Arrays.asList("msg", "message", "staffmode", "staff", "freeze", "lms",
		"koth", "ft", "filter", "r", "bless","blessme", "response", "end", "bless", "heal", "cf", "coinflip", "jackpot",
		"feed", "f", "fix", "filter edit", "filter toggle", "ft", "bless"));
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
		String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();
		if (rank.equalsIgnoreCase("banteam") || rank.equalsIgnoreCase("mod") || rank.equalsIgnoreCase("srmod")) {
			return;
		}
		if (allowedCommands.contains(event.getMessage()) || allowedCommands.contains("/" + event.getMessage())) {
			return;
		}
		player.sendMessage(CC.RedB + "(!) " + event.getMessage() + " is denied in this world!");
		event.setCancelled(true);
	}

	@EventHandler
	public void onPortalEnter(EntityPortalEnterEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (player.getWorld().getName().equals("world_the_end")) {
				World world = Bukkit.getWorld("world");
				Location location = world.getSpawnLocation();
				Bukkit.getScheduler().runTaskLater(AstroEndGrindingPlugin.getInstance(), () -> player.teleport(location, TeleportCause.UNKNOWN), 3L);
			}
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) || event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
			event.setCancelled(true);
		}
	}

	public HashMap<Player, Long> lastDamagerTime = new HashMap<>();
	public HashMap<Player, Player> lastDamager = new HashMap<>();

	@EventHandler
	public void playerDamageByOtherPlayer(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end")) return;
			lastDamagerTime.put((Player) e.getEntity(), System.currentTimeMillis());
			lastDamager.put((Player) e.getEntity(),(Player)  e.getDamager());
		}
	}




	@EventHandler
	public void playerDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end")) return;
			if (e.getCause() != EntityDamageEvent.DamageCause.VOID) return;
			Player p = (Player) e.getEntity();
			if (lastDamagerTime.containsKey(p)) {
				if (lastDamagerTime.get(p) + 20000 > System.currentTimeMillis()) {
					Player killer = lastDamager.get(p);
					if (p.getInventory().getContents() != null) {
						for (ItemStack itemStack : p.getInventory().getContents()) {
							if (itemStack != null) {
								killer.getWorld().dropItem(killer.getLocation(), itemStack);
							}
						}
					}
					if (p.getInventory().getHelmet() != null) {
						killer.getWorld().dropItem(killer.getLocation(), p.getInventory().getHelmet());
					}
					if (p.getInventory().getChestplate() != null) {
						killer.getWorld().dropItem(killer.getLocation(), p.getInventory().getChestplate());
					}
					if (p.getInventory().getLeggings() != null) {
						killer.getWorld().dropItem(killer.getLocation(), p.getInventory().getLeggings());
					}
					if (p.getInventory().getBoots() != null) {
						killer.getWorld().dropItem(killer.getLocation(), p.getInventory().getBoots());
					}
					p.setHealth(0D);
					e.setCancelled(true);
				}
			}

		}
	}
}
