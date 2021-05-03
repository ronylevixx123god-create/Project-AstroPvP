package me.astropvp.astroendgrinding.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;

public class NoFlyTask extends BukkitRunnable {

	@Override
	public void run() {
		for(Player player : AstroEndGrindingAPI.getWorld().getPlayers()) {
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
