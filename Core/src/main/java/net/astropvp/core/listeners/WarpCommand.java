package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.guis.WarpGUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WarpCommand implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void warpCMD(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if (cmd.equals("/warp") || cmd.equals("/ewarp") || cmd.equals("/warps") || cmd.equals("/ewarps")) {
            Player p = e.getPlayer();
            WarpGUI warpGUI = new WarpGUI(p);
            warpGUI.getLootGUI().open(p);
            p.playSound(p.getLocation(), Sound.CHEST_OPEN, 2, 2);
            p.sendMessage(CC.translate("&a&l(!)&a Opening the warp menu."));
        }
    }
}
