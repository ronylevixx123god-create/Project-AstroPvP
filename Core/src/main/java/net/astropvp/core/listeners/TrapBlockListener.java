package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class TrapBlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer() instanceof Player) {
            if (e.getPlayer().hasMetadata("trap_blocked")) {
                e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fBlock Place &c&l*"));
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onDestroy(BlockBreakEvent e) {
        if (e.getPlayer() instanceof Player) {
            if (e.getPlayer().hasMetadata("trap_blocked")) {
                e.getPlayer().sendMessage(CC.translate("&c&l* TRAP BLOCK &7Cancelled &fBlock Break &c&l*"));
                e.setCancelled(true);
            }
        }
    }
}
