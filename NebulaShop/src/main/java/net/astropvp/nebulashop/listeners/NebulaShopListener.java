package net.astropvp.nebulashop.listeners;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.matthawkk.chat.messages.PrefixedMessage;
import net.astropvp.nebulashop.NebulaShop;
import net.astropvp.nebulashop.inventories.NebulaShopInventory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NebulaShopListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER && e.getEntity().getWorld().getName().contains("nebula")) {
            e.setCancelled(true);
            Player player = (Player) e.getDamager();
            new NebulaShopInventory().open(player);
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Opening Nebula Shop.").get());
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER && e.getPlayer().getWorld().getName().contains("nebula")) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            new NebulaShopInventory().open(player);
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Opening Nebula Shop.").get());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("nebula"))
            return;
        if (e.getPlayer().getLocation().getBlockY() < 135) {
            e.getPlayer().teleport(NebulaShop.getInstance().getNebulaShopManager().getShopLocation());
        }
    }


    @EventHandler
    public void teleportCancel(PlayerTeleportEvent e) {
        if (e.getPlayer() == null) return;
        if (!e.getTo().getWorld().getName().equals("nebula")) return;
        if (!e.getPlayer().hasPermission("astrocore.nebula")) {
            e.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot teleport to the nebula lounge."));
            e.getPlayer().sendMessage(CC.translate("&cThis lounge requires the nebula rank!"));
            e.setCancelled(true);
        }



    }
}
