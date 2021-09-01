package net.astropvp.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        CollectPlugin.getInstance().getCollectManager().getPlayerCollect(e.getPlayer()).save(false);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(!(e.getEntity().getKiller() instanceof Player))
            return;
        if(e.getKeepInventory())
            return;
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();
        e.getDrops().clear();
        CollectPlugin.getInstance().getCollectManager().getPlayerCollect(killer).add(new CollectCategory(CollectType.DEATH_INVENTORY, e.getDrops(), CC.RedB + killed.getName() + "'s Death Inventory"));
    }
}
