package net.astropvp.antiphase;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiPhase extends JavaPlugin implements Listener {
    private AntiPhaseTask task;
    private Settings settings;

    public Settings getSettings() {
        return this.settings;
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.settings = new Settings(this);
        if (this.settings.isOutdated()) {
            this.settings.update();
            this.getLogger().info("Configuration file has been updated.");
        }

        Bukkit.getPluginManager().registerEvents(this, this);
        this.task = new AntiPhaseTask(this);
        this.task.runTaskTimer(this, 1L, 1L);
        this.getLogger().info(this.getName() + " has been enabled!");
    }

    public void onDisable() {
        this.task.cancel();
        this.getLogger().info(this.getName() + " has been disabled!");
    }

    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.HIGHEST
    )
    public void corretEnderpearlTeleportation(PlayerTeleportEvent event) {
        if (this.settings.correctEnderpearlTeleportation()) {
            if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
                Location to = event.getTo();
                Block block = to.getBlock();
                double excess = to.getY() - (double)((int)to.getY());
                if (this.settings.getPassable().contains(block.getType())) {
                    event.setTo(event.getTo().subtract(0.0D, excess, 0.0D));
                }

                if (!this.settings.getPassable().contains(block.getRelative(BlockFace.UP).getType())) {
                    event.setTo(to.subtract(0.0D, 1.0D, 0.0D));
                }

            }
        }
    }

    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.HIGH
    )
    public void preventLongMoves(PlayerMoveEvent event) {
        if (event.getFrom().distance(event.getTo()) > 8.0D) {
            event.setCancelled(true);
        }

    }

    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.MONITOR
    )
    public void setTeleported(PlayerTeleportEvent event) {
        if (event.getCause() != TeleportCause.ENDER_PEARL) {
            this.task.addTeleportedPlayer(event.getPlayer().getUniqueId());
        }

    }

    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.MONITOR
    )
    public void setTeleported(PlayerRespawnEvent event) {
        this.task.addTeleportedPlayer(event.getPlayer().getUniqueId());
    }
}
