/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.DecimalFormatType
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class EnderpearlCooldownListener
        implements Listener {
    private final EnderpearlCooldown instance;

    public EnderpearlCooldownListener(EnderpearlCooldown cd) {
        this.instance = cd;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onEnderPearlThrow(PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.hasItem() && e.getItem().getType() == Material.ENDER_PEARL) {
            boolean teleblock;
            Player pl = e.getPlayer();
            if (pl.hasMetadata("lastEnderpearlThrow")) {
                long lastEnderpearlThrow = pl.getMetadata("lastEnderpearlThrow").get(0).asLong();
                long cd = 400L;
                long timeSinceThrow = System.currentTimeMillis() - lastEnderpearlThrow;
                if (timeSinceThrow < cd) {
                    double timeToWait = Math.max(0.1, (double) (cd - timeSinceThrow) / 1000.0);
                    e.setCancelled(true);
                    e.setUseItemInHand(Event.Result.DENY);
                    pl.sendMessage(CC.RedB + "(!) " + CC.Red + "You must wait " + CC.RedB + NumberUtils.format(timeToWait, DecimalFormatType.SECONDS) + CC.Red + "s before throwing another Enderpearl!");
                    pl.updateInventory();
                    return;
                }
            }
            boolean bl = teleblock = pl.hasMetadata("teleblock") && pl.getMetadata("teleblock").get(0).asLong() > System.currentTimeMillis();
            if (!EnderpearlCooldown.isEnderpearlCooldownOver(pl) || teleblock) {
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                if (teleblock) {
                    long delay = pl.getMetadata("teleblock").get(0).asLong() - System.currentTimeMillis();
                    pl.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "** Teleblock: " + ChatColor.LIGHT_PURPLE + "You cannot use an enderpearl for " + delay + "ms!");
                } else {
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot use an enderpearl for " + EnderpearlCooldown.getEnderpearlCooldownLeft(pl) + "ms!");
                }
                pl.updateInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerThrow(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof EnderPearl && event.getEntity().getShooter() instanceof Player) {
            ((Player) event.getEntity().getShooter()).setMetadata("lastEnderpearlThrow", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis()));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnderpearlTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            boolean teleblock;
            Player pl = e.getPlayer();
            boolean bl = teleblock = pl.hasMetadata("teleblock") && pl.getMetadata("teleblock").get(0).asLong() > System.currentTimeMillis();
            if (!EnderpearlCooldown.isEnderpearlCooldownOver(e.getPlayer()) || teleblock) {
                e.setCancelled(true);
                if (teleblock) {
                    long delay = pl.getMetadata("teleblock").get(0).asLong() - System.currentTimeMillis();
                    pl.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "** Teleblock: " + ChatColor.LIGHT_PURPLE + "You cannot use an enderpearl for " + delay + "ms!");
                } else {
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot use an enderpearl for " + EnderpearlCooldown.getEnderpearlCooldownLeft(e.getPlayer()) + "ms!");
                }
                return;
            }
            e.getPlayer().setMetadata("last_enderpearl_throw", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis()));
        }
    }
}

