package net.astropvp.core.staff;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Freeze implements Listener {

    public Freeze() {
        AstroCore.getInstance().getServer().getScheduler().runTaskTimer(AstroCore.getInstance(),
                new FrozenMessageTask(), 20L * 10, 20L);
    }

    @Getter
    public static HashMap<UUID, Location> frozenPlayers = new HashMap<>();

    public static void handle(Player target, Player staff) {
        if (frozenPlayers.containsKey(target.getUniqueId())) {
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
        frozenPlayers.put(target.getUniqueId(), target.getLocation());
    }

    public static void unfreeze(Player target, Player staff) {
        target.sendMessage("");
        target.sendMessage(CC.Red + "You have been unfrozen by " + staff.getName() + ".");
        target.sendMessage("");
        StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Aqua + target.getName() + CC.Gray + " has been unfrozen by " + CC.White +  staff.getName());
        frozenPlayers.remove(target.getUniqueId());
    }

    public Boolean isFrozen(Player player) {
        return frozenPlayers.containsKey(player.getUniqueId());
    }

    /*@EventHandler (ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isFrozen(player)) {
            if (event.getFrom().distanceSquared(event.getTo()) > 0.0) {
                player.teleport(new Location(player.getWorld(), 0,0,0));
                Bukkit.broadcastMessage("teleported to 0,0???");
            }
        }
    }*/

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().getName().equalsIgnoreCase("BOWP")) {
            return;
        }
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

    @RequiredArgsConstructor
    private static class FrozenMessageTask implements Runnable {

        private int count = 0;

        @Override
        public void run() {
            Set<UUID> remove = new HashSet<>();
            frozenPlayers.forEach((uuid, location) -> {
                Player target = Bukkit.getPlayer(uuid);
                if (target == null) {
                    remove.add(uuid);
                    return;
                }

                count++;

                if(count == 3) {
                    target.sendMessage("");
                    target.sendMessage(CC.RedB + "YOU HAVE BEEN FROZEN!");
                    target.sendMessage(CC.Red + "Do not disconnect.");
                    target.sendMessage("");
                    count = 0;
                }

                target.teleport(location);
            });

            remove.forEach(frozenPlayers::remove);
        }

    }
}
