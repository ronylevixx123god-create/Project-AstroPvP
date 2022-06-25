package me.kingsavaqe.astroenvoy.tasks;

import java.io.File;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.envoy.Envoy;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleTask extends BukkitRunnable {
    private final AstroEnvoyPlugin instance;

    public ParticleTask(AstroEnvoyPlugin instance) {
        this.instance = instance;
    }

    public void run() {
        Envoy envoy = this.instance.getEnvoyManager().getEnvoy();
        if (!envoy.hasSetup() && !envoy.hasStarted())
            return;
        if (!envoy.hasRandomCrateLocation())
            return;
        if (!this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration().getBoolean("Envoy.Crate.Particles.Enable"))
            return;
        for (Location crateLocation : envoy.getRandomCrateLocations()) {
            for (int i = 0; i < 22; i++) {
                crateLocation.getWorld().spigot().playEffect(crateLocation, Effect.WITCH_MAGIC);
                crateLocation.getWorld().spigot().playEffect(crateLocation, Effect.CLOUD, 0, 0, 0.0F, 0.0F, 0.0F, 0.2F, 1, 10);
            }
        }
    }
}
