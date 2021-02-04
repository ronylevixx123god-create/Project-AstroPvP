package net.astropvp.core.staff;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Freeze implements Listener {

    @Getter
    public static List<UUID> frozenPlayers = new ArrayList<>();

    public static void handle(Player target, Player staff) {
        if (frozenPlayers.contains(target.getUniqueId())) {
            unfreeze(target, staff);
        } else {
            freeze(target, staff);
        }
    }

    public static void freeze(Player target, Player staff) {
        target.sendMessage("");
        target.sendMessage(CC.RedB + "YOU HAVE BEEN FROZEN!");
        target.sendMessage(CC.Red + "Do not disconnect.");
        target.sendMessage("");
        StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Aqua + target.getName() + CC.Gray + " has been frozen by " + CC.White +  staff.getName());
        frozenPlayers.add(target.getUniqueId());
    }

    public static void unfreeze(Player target, Player staff) {
        target.sendMessage("");
        target.sendMessage(CC.Red + "You have been unfrozen by " + staff.getName() + ".");
        target.sendMessage("");
        StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Aqua + target.getName() + CC.Gray + " has been unfrozen by " + CC.White +  staff.getName());
        frozenPlayers.remove(target.getUniqueId());
    }

    public Boolean isFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (isFrozen((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (isFrozen((Player) event.getEntity())) {
                event.getDamager().sendMessage(CC.Red + event.getEntity().getName() + " is currently frozen.");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemPickUp(PlayerPickupItemEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (isFrozen((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer() != null) {
            if (isFrozen(event.getPlayer())) {
                StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Yellow + event.getPlayer().getName() + CC.RedB + " has logged out while frozen.");
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.getPlayer() != null) {
            if (isFrozen(event.getPlayer())) {
                StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Yellow + event.getPlayer().getName() + CC.RedB + " has been kicked while frozen.");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer() != null) {
            if (isFrozen(event.getPlayer())) {
                StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Yellow + event.getPlayer().getName() + CC.RedB + " has joined while frozen.");
            }
        }
    }
}
