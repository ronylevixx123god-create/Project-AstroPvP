package me.ily2bb.slotbot.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PotionEffectAddEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class WitchFix implements Listener {
    @EventHandler
    public void witchFix(PotionSplashEvent e) {
        if (e.getEntity() == null) return;
        if (e.getEntity().getType() == null) return;
        if (e.getEntityType() == EntityType.WITCH) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void witchFix2(PotionEffectAddEvent e) {
        if (e.getEntity() == null) return;
        if (e.getEntity().getType() == null) return;
        if (e.getEntityType() == EntityType.WITCH) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void witchFix3(EntityPotionEffectEvent e) {
        if (e.getEntity() == null) return;
        if (e.getEntity().getType() == null) return;
        if (e.getEntityType() == EntityType.WITCH) {
            e.setCancelled(true);
            return;
        }
    }
}
