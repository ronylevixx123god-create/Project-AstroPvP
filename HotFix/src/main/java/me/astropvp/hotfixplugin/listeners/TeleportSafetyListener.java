package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import java.util.EnumSet;
import java.util.Set;
import me.ifamasssxd.cosmicduel.DuelAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class TeleportSafetyListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (event.getTo().getY() < 1.0D)
                event.setCancelled(true);
            if (event.getTo().getBlock().getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid() && event.getTo().getBlock().getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType().isSolid())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleportPearl(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL &&
                event.getTo().getY() < 1.0D)
            event.setCancelled(true);
    }

    @EventHandler
    public void playerDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world") && !e.getEntity().getWorld().getName().equalsIgnoreCase("flat")) return;
            if (e.getCause() != EntityDamageEvent.DamageCause.VOID) return;
            Player p = (Player) e.getEntity();
            if (p.getLocation().getY() < -1.0) {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                p.sendMessage(CC.translate("&cPrevented you from dying to void."));
            }
        }
    }
}


