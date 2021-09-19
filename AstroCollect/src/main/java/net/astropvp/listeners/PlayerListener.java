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
        CollectManager.getCollectList().remove(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getEntity().getKiller() == null)
            return;
        if(e.getKeepInventory())
            return;
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();
        CollectPlugin.getInstance().getCollectManager().getPlayerCollect(killer).add(new CollectCategory(CollectType.DEATH_INVENTORY, e.getDrops().toArray(new ItemStack[0]), CC.RedB + killed.getName() + "'s Death Inventory", killed.getName().toString()));
        e.getDrops().clear();


    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CollectPlugin.getInstance().getCollectManager().getPlayerCollect(e.getPlayer()).load(false, e.getPlayer());
    }


}
