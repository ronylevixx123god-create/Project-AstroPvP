package net.astropvp.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectManager;
import net.astropvp.struct.CollectType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        CollectPlugin.getInstance().getCollectManager().getPlayerCollect(e.getPlayer()).save(false);
        //CollectPlugin.getInstance().getCollectManager().remove(e.getPlayer());
    }

}
