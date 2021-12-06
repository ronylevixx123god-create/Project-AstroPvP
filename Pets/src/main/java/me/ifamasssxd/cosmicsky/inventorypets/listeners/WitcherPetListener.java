package me.ifamasssxd.cosmicsky.inventorypets.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

public class WitcherPetListener implements Listener {
    @EventHandler
    public void onHit(PlayerVelocityEvent e) {
        if(e.getPlayer().hasMetadata("noknockback")) {
            e.setCancelled(true);
        }
    }
}
