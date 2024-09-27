package net.astropvp.space.tasks

import net.astropvp.space.hostile.MobManager
import net.astropvp.space.hostile.MobType
import net.astropvp.space.struct.LocationManager
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.particle.ParticleEffect

class MobSpawnTask : BukkitRunnable() {

    override fun run() {
        for (location in LocationManager.locations) {
            if(MobManager.shouldSpawn(location)) {
                try {
                    ParticleEffect.SPELL_WITCH.display(location)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                MobType.SPACE_ZOMBIE.spawn(location)
            }
        }
    }

}