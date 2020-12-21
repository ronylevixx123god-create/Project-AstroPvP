/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.blacklist.events.BlockBreakBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemUseBlacklistEvent;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class WorldGuardHangingListener
implements Listener {
    private WorldGuardPlugin plugin;

    public WorldGuardHangingListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onHangingingBreak(HangingBreakEvent event) {
        Hanging hanging = event.getEntity();
        World world = hanging.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (event instanceof HangingBreakByEntityEvent) {
            HangingBreakByEntityEvent entityEvent = (HangingBreakByEntityEvent)event;
            Entity removerEntity = entityEvent.getRemover();
            if (removerEntity instanceof Projectile) {
                Projectile projectile = (Projectile)removerEntity;
                Entity entity = removerEntity = projectile.getShooter() != null ? (LivingEntity)projectile.getShooter() : removerEntity;
            }
            if (removerEntity instanceof Player) {
                Player player = (Player)removerEntity;
                if (wcfg.getBlacklist() != null) {
                    if (hanging instanceof Painting && !wcfg.getBlacklist().check(new BlockBreakBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), 321), false, false)) {
                        event.setCancelled(true);
                        return;
                    }
                    if (hanging instanceof ItemFrame && !wcfg.getBlacklist().check(new BlockBreakBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), 389), false, false)) {
                        event.setCancelled(true);
                        return;
                    }
                }
                if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().canBuild(player, hanging.getLocation())) {
                    player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
                    event.setCancelled(true);
                    return;
                }
            } else {
                if (removerEntity instanceof Creeper) {
                    if (wcfg.blockCreeperBlockDamage || wcfg.blockCreeperExplosions) {
                        event.setCancelled(true);
                        return;
                    }
                    if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.CREEPER_EXPLOSION, hanging.getLocation())) {
                        event.setCancelled(true);
                        return;
                    }
                }
                if (hanging instanceof Painting && (wcfg.blockEntityPaintingDestroy || wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENTITY_PAINTING_DESTROY, hanging.getLocation()))) {
                    event.setCancelled(true);
                } else if (hanging instanceof ItemFrame && (wcfg.blockEntityItemFrameDestroy || wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, hanging.getLocation()))) {
                    event.setCancelled(true);
                }
            }
        } else if (hanging instanceof Painting && wcfg.blockEntityPaintingDestroy && event.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
            event.setCancelled(true);
        } else if (hanging instanceof ItemFrame && wcfg.blockEntityItemFrameDestroy && event.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onHangingPlace(HangingPlaceEvent event) {
        Block placedOn = event.getBlock();
        Player player = event.getPlayer();
        World world = placedOn.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.getBlacklist() != null) {
            if (event.getEntity() instanceof Painting && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), 321), false, false)) {
                event.setCancelled(true);
                return;
            }
            if (event.getEntity() instanceof ItemFrame && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), 389), false, false)) {
                event.setCancelled(true);
                return;
            }
        }
        if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().canBuild(player, placedOn.getRelative(event.getBlockFace()))) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(entity.getWorld());
        if (wcfg.useRegions && (entity instanceof ItemFrame || entity instanceof Painting) && !this.plugin.getGlobalRegionManager().canBuild(player, entity.getLocation())) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
            event.setCancelled(true);
            return;
        }
    }
}

