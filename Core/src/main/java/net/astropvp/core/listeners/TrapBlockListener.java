package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.items.TrapBlockerItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrapBlockListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer() != null) {
            if (TrapBlockerItem.isTrapped(e.getPlayer().getUniqueId())) {
                e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fBlock Place &c&l*"));
                e.setCancelled(true);
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onDestroy(BlockBreakEvent e) {
        if (e.getPlayer() != null) {
            if (TrapBlockerItem.isTrapped(e.getPlayer().getUniqueId())) {
                e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fBlock Break &c&l*"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onDoor(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block clicked = e.getClickedBlock();
        if ((action == Action.RIGHT_CLICK_BLOCK) || (action == Action.LEFT_CLICK_BLOCK)) {
            if (clicked.getType().name().contains("DOOR") || (clicked.getType() == Material.TRAP_DOOR) || (clicked.getType() == Material.FENCE_GATE)) {
                if (TrapBlockerItem.isTrapped(e.getPlayer().getUniqueId())) {
                    e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fDoor Open &c&l*"));
                    e.setCancelled(true);
                }
            }
        }
    }
}
