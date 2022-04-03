package me.astropvp.astrospacegrinding.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;

public class NoFlyTask extends BukkitRunnable {

	@Override
	public void run() {
		for(Player player : AstroSpaceGrindingAPI.getWorld().getPlayers()) {
			if(player.isOp()) {
				continue;
			}
			if(player.isFlying()) {
				player.setFlying(false);
				player.setAllowFlight(false);
			}
		}
		
	}

}
