package net.astropvp.christmas.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.matthawkk.chat.messages.PrefixedMessage;
import net.astropvp.christmas.Christmas;
import net.astropvp.christmas.inventories.ChristmasInventory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ChristmasListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER && e.getEntity().getWorld().getName().contains("Christmas")) {
            e.setCancelled(true);
            Player player = (Player) e.getDamager();
            new ChristmasInventory().open(player);
            player.sendMessage(new PrefixedMessage(Christmas.getInstance(), "Opening Christmas Shop.").get());
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER && e.getPlayer().getWorld().getName().contains("Christmas")) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            new ChristmasInventory().open(player);
            player.sendMessage(new PrefixedMessage(Christmas.getInstance(), "Opening Christmas Shop.").get());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("Christmas"))
            return;
        if (e.getPlayer().getLocation().getBlockY() < 135) {
            e.getPlayer().teleport(Christmas.getInstance().getChristmasManager().getShopLocation());
        }
    }


    @EventHandler
    public void teleportCancel(PlayerTeleportEvent e) {
        if (e.getPlayer() == null) return;
        if (!e.getTo().getWorld().getName().equals("Christmas")) return;
        if (!e.getPlayer().hasPermission("astrocore.Christmas")) {
            e.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot teleport to the Christmas lounge."));
            e.getPlayer().sendMessage(CC.translate("&cThis lounge requires the Christmas rank!"));
            e.setCancelled(true);
        }



    }
}
