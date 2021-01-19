package me.ifamasssxd.cosmicbosses.listeners;

import java.util.concurrent.*;
import org.bukkit.entity.*;
import me.ifamasssxd.cosmicbosses.utils.*;
import me.ifamasssxd.cosmicbosses.tasks.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.metadata.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.plugin.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;

public class PlayerListener implements Listener
{
    public static ConcurrentHashMap<UUID, Player> aSyncPlayerMap;
    
    @EventHandler
    public void onPlayerLogon(final PlayerJoinEvent e) {
        PlayerListener.aSyncPlayerMap.put(e.getPlayer().getUniqueId(), e.getPlayer());
        ScoreboardUtils.clearBossScoreboard(e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerLeave(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        PlayerListener.aSyncPlayerMap.remove(p.getUniqueId());
        if (BleedTask.isBleeding(p)) {
            BleedTask.stopBleed(p);
        }
        ScoreboardUtils.clearBossScoreboard(p);
    }
    
    @EventHandler
    public void onPLayerDeath(final PlayerDeathEvent e) {
        if (BleedTask.isBleeding(e.getEntity())) {
            BleedTask.stopBleed(e.getEntity());
        }
        ScoreboardUtils.clearBossScoreboard(e.getEntity());
    }
    
    @EventHandler
    public void onPlayerREspawn(final PlayerRespawnEvent e) {
        e.getPlayer().setWalkSpeed(0.2f);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickup(final PlayerPickupItemEvent e) {
        if (e.getItem().hasMetadata("whitelisted_item") && e.getItem().getMetadata("whitelisted_item").size() > 0) {
            final String name = e.getItem().getMetadata("whitelisted_item").get(0).asString();
            if (!e.getPlayer().getName().equals(name)) {
                e.setCancelled(true);
            }
        }
        else if (e.getItem().hasMetadata("removeDurability") && !e.isCancelled()) {
            final ItemStack item = e.getItem().getItemStack();
            final ItemMeta im = item.getItemMeta();
            e.getItem().removeMetadata("removeDurability", (Plugin)CosmicBosses.getInstance());
            final List<String> lore = (List<String>)im.getLore();
            if (lore == null || lore.size() <= 0) {
                return;
            }
            lore.remove(lore.size() - 1);
            im.setLore((List)lore);
            item.setItemMeta(im);
            e.getItem().setItemStack(item);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickup(final InventoryPickupItemEvent e) {
        if (e.getItem().hasMetadata("whitelisted_item") && e.getItem().getMetadata("whitelisted_item").size() > 0) {
            e.setCancelled(true);
        }
    }
    
    static {
        PlayerListener.aSyncPlayerMap = new ConcurrentHashMap<UUID, Player>();
    }
}
