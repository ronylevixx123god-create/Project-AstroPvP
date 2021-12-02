package me.venir.astrocollectors.listeners.collectorlisteners;

import me.venir.astrocollectors.collector.Collector;
import me.venir.astrocollectors.collector.CollectorManager;
import me.venir.astrocollectors.inventories.CollectorInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener  implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null ||e.getClickedBlock().getType() == Material.AIR) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getType() == Material.BEACON) {
            e.setCancelled(true);
            Location location = e.getClickedBlock().getLocation();
            if (!CollectorManager.DELEGATE.isCollectorContained(location)) return;
            Location collectorLocation = CollectorManager.DELEGATE.getCollector(location).getLocation();
            assert collectorLocation != null;
            if (!collectorLocation.equals(location)) return;
            Collector collector = CollectorManager.DELEGATE.getCollector(e.getClickedBlock().getLocation());
            CollectorInventory collectorInventory = new CollectorInventory(e.getPlayer(), collector);
            collectorInventory.open();
        }
    }
}
