package me.astropvp.astrotournaments.tasks;

import org.bukkit.scheduler.BukkitRunnable;

public class TournamentRepeatingTask extends BukkitRunnable {
	
	private int ticks;

	@Override
	public void run() {
		ticks--;
		if(ticks <= 0) {
			
		}
	}
	
	public int getTicks() {
		return this.ticks;
	}

}
