package net.astropvp.core.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

public class PotionFix implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntityType() == EntityType.SPLASH_POTION) {
            Projectile projectile = event.getEntity();
            if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting()) {
                double speed = 1.0;
                Vector velocity = projectile.getVelocity();
                velocity.setY(velocity.getY() - speed);
                projectile.setVelocity(velocity);
            }
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player) event.getEntity().getShooter();
            if (event.getIntensity(shooter) > 0.5D)
                event.setIntensity(shooter, 1.0D);

        }
    }
}
