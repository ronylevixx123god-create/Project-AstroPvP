package me.astropvp.outpost.listener;

import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlutoListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEnchantProc(CustomEnchantProcOnEntityEvent e) {
        if (e.getPlayer().getWorld().getName().equals("world_outpost_pluto")) {
            e.setCancelled(true);
        }
    }
}
