package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.items.TrapBlockerItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrapBlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer() != null) {
            if (TrapBlockerItem.isTrapped(e.getPlayer().getUniqueId())) {
                e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fBlock Place &c&l*"));
                e.setCancelled(true);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDestroy(BlockBreakEvent e) {
        if (e.getPlayer() != null) {
            if (TrapBlockerItem.isTrapped(e.getPlayer().getUniqueId())) {
                e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fBlock Break &c&l*"));
                e.setCancelled(true);
            }
        }
    }
}
