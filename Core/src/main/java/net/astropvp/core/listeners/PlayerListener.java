package net.astropvp.core.listeners;


import net.astropvp.core.data.InventoryData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player)event.getEntity();
        InventoryData.createInstance(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        InventoryData[] data = InventoryData.getData().containsKey(player.getUniqueId()) ? (InventoryData[])InventoryData.getData().get(player.getUniqueId()) : null;
        if (data == null)
            return;
        InventoryData.getData().remove(player.getUniqueId());
        InventoryData.saveToDisk(player, data);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (InventoryData.getData().containsKey(player.getUniqueId()))
            return;
        InventoryData.loadFromDisk(event.getPlayer());
    }
}
