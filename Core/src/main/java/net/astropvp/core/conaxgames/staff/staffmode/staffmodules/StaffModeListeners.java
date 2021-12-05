package net.astropvp.core.conaxgames.staff.staffmode.staffmodules;

import net.astropvp.core.AstroCore;
import net.astropvp.core.conaxgames.staff.staffmode.StaffModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffModeListeners implements Listener {

    private final StaffModeManager staffModeManager;

    public StaffModeListeners(AstroCore plugin) {
        this.staffModeManager = plugin.getStaffModeManager();
    }

    @EventHandler
    public void onInventoryInteractInStaffMode(InventoryInteractEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        if (!staffModeManager.isStaffModeToggled(player)) return;
        if (event.getInventory().getType() != InventoryType.PLAYER) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLogOutWhileInStaffMode(PlayerQuitEvent event) {
        if (!staffModeManager.isStaffModeToggled(event.getPlayer())) return;
        this.staffModeManager.toggleStaffModeOff(event.getPlayer());
    }

    @EventHandler
    public void onPlayerPickUpItemInStaffMode(PlayerPickupItemEvent event) {
        if (!staffModeManager.isStaffModeToggled(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickUpArrowInStaffMode(PlayerPickupArrowEvent event) {
        if (!staffModeManager.isStaffModeToggled(event.getPlayer())) return;
        event.setCancelled(true);
    }
}
