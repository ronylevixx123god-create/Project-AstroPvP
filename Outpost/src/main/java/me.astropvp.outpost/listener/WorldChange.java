package me.astropvp.outpost.listener;

import me.astropvp.outpost.Outpost;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WorldChange implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void clearEffects(PlayerTeleportEvent e) {
        Bukkit.getScheduler().runTaskLater(Outpost.getInstance(), () -> {
            if (e.getFrom().getWorld() != e.getTo().getWorld() && e.getTo().getWorld().getName().equals("world_outpost_pluto")) {

                for (PotionEffect potion : e.getPlayer().getActivePotionEffects()) {
                    if (potion.getType() == PotionEffectType.SPEED) {
                        if (potion.getAmplifier() >= 2) {
                            e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                            //TODO: Random speed 4 apply on world change
                            //TODO: Also deny masks
                        }
                    }
                }
                e.getPlayer().removePotionEffect(PotionEffectType.HEALTH_BOOST);
                e.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);

            }

        }, 10L);
    }
}
