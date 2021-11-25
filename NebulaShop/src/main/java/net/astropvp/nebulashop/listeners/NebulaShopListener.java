package net.astropvp.nebulashop.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.matthawkk.chat.PrefixedMessage;
import net.astropvp.nebulashop.NebulaShop;
import net.astropvp.nebulashop.inventories.NebulaShopInventory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class NebulaShopListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if(e.getEntityType().equals(EntityType.ENDER_CRYSTAL)) {
            e.setCancelled(true);
            Player player = (Player) e.getDamager();
            new NebulaShopInventory().open(player);
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Opening Nebula Shop.").get());
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
        if(e.getRightClicked().getType().equals(EntityType.ENDER_CRYSTAL)) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            new NebulaShopInventory().open(player);
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Opening Nebula Shop.").get());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(!e.getPlayer().getWorld().getName().equals("nebula"))
            return;
        if(e.getPlayer().getLocation().getBlockY() < 135) {
            e.getPlayer().teleport(NebulaShop.getInstance().getNebulaShopManager().getShopLocation());
        }
    }
}
