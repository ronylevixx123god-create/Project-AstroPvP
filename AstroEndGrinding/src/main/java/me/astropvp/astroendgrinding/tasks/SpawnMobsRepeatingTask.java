package me.astropvp.astroendgrinding.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.enums.MonsterType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.particle.ParticlePlugin;

public class SpawnMobsRepeatingTask extends BukkitRunnable {

	private int ticks;
	
	public SpawnMobsRepeatingTask() {
		this.ticks = 0;
	}
	
	@SneakyThrows
	@Override
	public void run() {
		ticks++;
		if(ticks % 30 == 0) {
			for(String value : AstroEndGrindingAPI.getBossLocations()) {
				Object[] values = AstroEndGrindingAPI.getValues(value);
				MonsterType type = (MonsterType) values[0];
				Location location = (Location) values[1];
				if (location.getChunk().getEntities().length == 0) {
					continue;
				}

				ParticleEffect.PORTAL.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1f, 1f, 1f, 0.05f, 60);



				int playersInChunk = 0;
				int mobsAmount = 0;
				for (Entity entity : location.getChunk().getEntities()) {
					if (entity.getType() == EntityType.PLAYER) {
						playersInChunk++;
					} else {
						mobsAmount++;
					}
				}
				if (playersInChunk == 0) {
					continue;
				}
				if (mobsAmount >= 6) {
					continue;
				}
				type.getInstance().spawn(location);
				Bukkit.getLogger().info("[EndGrinding] Spawning the Mob Type '" + type.getName() + "'.");
			}
		}
		
	}

}
