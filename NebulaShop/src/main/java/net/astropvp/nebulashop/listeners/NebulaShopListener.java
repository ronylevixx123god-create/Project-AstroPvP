package net.astropvp.nebulashop.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
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
            player.openInventory(NebulaShop.getInstance().getInventory().getInventory());
            player.sendMessage(CC.DarkPurpleB + "(Nebula) " + CC.LightPurple + "Opening Nebula Shop.");
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
        if(e.getRightClicked().getType().equals(EntityType.ENDER_CRYSTAL)) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            player.openInventory(NebulaShop.getInstance().getInventory().getInventory());
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
