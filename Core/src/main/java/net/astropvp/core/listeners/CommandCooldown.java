package net.astropvp.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;

public class CommandCooldown implements Listener {
    private static final HashMap<Player, Long> cooldowns = new HashMap<>();

    @EventHandler
    public static void onPreCommand(PlayerCommandPreprocessEvent event) {
        cooldowns.put(event.getPlayer(), System.currentTimeMillis());
    }
}
