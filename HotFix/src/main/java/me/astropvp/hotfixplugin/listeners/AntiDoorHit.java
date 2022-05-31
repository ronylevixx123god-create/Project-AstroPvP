package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiDoorHit implements Listener {
    private final Map<UUID, Long> doorHits;

    public AntiDoorHit() {
        this.doorHits = new HashMap<>();
    }

    @EventHandler
    public void openDoor(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType().name().endsWith("_DOOR")) {
            doorHits.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (!canHit((Player) e.getDamager())) {
                e.setCancelled(true);
            }
        }
    }

    public boolean canHit(Player player) {
        if (doorHits.containsKey(player.getUniqueId())) {
            long lastOpened = doorHits.get(player.getUniqueId());
            int timeBetweenDoorHit = 500;
            return System.currentTimeMillis() >= lastOpened + timeBetweenDoorHit;
        }
        return true;
    }
}
