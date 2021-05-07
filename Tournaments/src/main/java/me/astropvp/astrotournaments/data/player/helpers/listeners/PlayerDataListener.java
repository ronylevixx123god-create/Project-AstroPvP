package me.astropvp.astrotournaments.data.player.helpers.listeners;

import me.astropvp.astrospawners.utils.LocationUtils;
import me.astropvp.astrotournaments.AstroTournamentsPlugin;
import me.astropvp.astrotournaments.data.player.PlayerData;
import me.astropvp.astrotournaments.data.player.helpers.PlayerDataHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;



public class PlayerDataListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().runTaskAsynchronously(AstroTournamentsPlugin.getInstance(),
				(Runnable) new BukkitRunnable() {
					@Override
					public void run() {
						if (!PlayerDataHelper.doesConfigurationExist(player)) {
							PlayerDataHelper.createConfiguration(player);
						}
						PlayerData data = new PlayerData(player);
						new PlayerDataHelper(data);
						data.setDefaultPath("login-time", System.currentTimeMillis());
						Bukkit.getScheduler().scheduleSyncDelayedTask(AstroTournamentsPlugin.getInstance(), (Runnable)
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
		Bukkit.getScheduler().runTaskAsynchronously(AstroTournamentsPlugin.getInstance(),
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
