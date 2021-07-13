package io.apice.clientcomms.struct;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AsyncPlayerProvider
implements Listener {
    private final ConcurrentMap<UUID, Player> idToPlayer = new MapMaker().concurrencyLevel(32).weakValues().makeMap();

    public Player getPlayer(UUID uuid) {
        return this.idToPlayer.get(uuid);
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerLogin(PlayerJoinEvent event) {
        this.idToPlayer.put(event.getPlayer().getUniqueId(), event.getPlayer());
    }

    @EventHandler(ignoreCancelled=true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.idToPlayer.remove(event.getPlayer().getUniqueId());
    }

    public ConcurrentMap<UUID, Player> getIdToPlayer() {
        return this.idToPlayer;
    }
}

