package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import me.astropvp.astrospawners.utils.LocationUtils;
import net.astropvp.core.utils.PlayerData;
import net.astropvp.core.utils.PlayerDataHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;

import net.astropvp.core.AstroCore;

public class PlayerDataListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().runTaskAsynchronously(AstroCore.getInstance(),
				(Runnable) new BukkitRunnable() {
					@Override
					public void run() {
						if (!PlayerDataHelper.doesConfigurationExist(player)) {
							PlayerDataHelper.createConfiguration(player);
							Bukkit.getLogger().info("[PlayerData] Creating Player Data for " + player.getName());
						}
						PlayerData data = new PlayerData(player);
						new PlayerDataHelper(data);
						data.setDefaultPath("login-time", System.currentTimeMillis());
						Bukkit.getScheduler().scheduleSyncDelayedTask(AstroCore.getInstance(), (Runnable)
						new BukkitRunnable() {
							@Override
							public void run() {
								Object value = data.getValue("last-logout-location");
								Location location = value != null ? LocationUtils.fromStringLocation((String)value) : null;
								if(location != null) {
									value = data.getValue("last-logout-time");
									long time = value != null ? System.currentTimeMillis() : -1;
									if(time <= -1) {
										return;
									}
									if(time - System.currentTimeMillis() <= -60 * 1000 * 15) {
										Faction faction = FactionUtils.getFactionAt(location);
										FPlayer fp = FactionUtils.getFPlayer(player);
										if(faction.getRelationTo(fp) == Relation.ALLY || 
										faction.getRelationTo(fp) == Relation.MEMBER ||
										faction.isWarZone() || faction.isSafeZone()) {
											return;
										}
										player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
										player.sendMessage(CC.Red + "You logged out in truce, neutral, or enemy territory "
										+ "more than 15 minutes ago, and you were teleported to /spawn.");
									}
								}
							}
						}, 1);
					}
				});
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlayerData data = PlayerDataHelper.findPlayerData(player);
		
		if(data == null) {
			return;
		}
		
		data.setDefaultPath("last-logout-location", LocationUtils.toStringLocation(player.getLocation()));
		data.setDefaultPath("last-logout-time", System.currentTimeMillis());
		data.removeStoredMetaData();
		Bukkit.getScheduler().runTaskAsynchronously(AstroCore.getInstance(),
				(Runnable) new BukkitRunnable() {
					@Override
					public void run() {
						if (data != null) {
							data.removeStoredData();
							data.save();
						}
					}
				});
	}
}
