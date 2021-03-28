/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicKOTH.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.utils.RewardUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.sk89q.jchronic.utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class KothSanityListener implements Listener {

    private Cache<UUID, Long> recentKothDeaths = CacheBuilder.newBuilder().expireAfterWrite(8L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<UUID, Long>() {

        public Long load(UUID uuid) {
            return System.currentTimeMillis();
        }
    });

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (CosmicKOTHPlugin.get_().getKoth() != null) {
            Player player = e.getPlayer();
            CosmicKOTHPlugin.get_().getKoth().getClass();
            player.removeMetadata("KOTH_CAPPING_START", (Plugin)CosmicKOTHPlugin.get_());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().getType() == Material.WEB && e.getPlayer().getWorld().getName().equals("world_koth")) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.getPlayer().setItemInHand((ItemStack)null);
            e.getPlayer().sendMessage("" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "The KOTH arena absorbs your cobweb blocks!");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/warp koth")) {
            e.setMessage("/koth warp");
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (e.getBlock().getWorld().getName().equals("world_koth")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerAttemptSalvage(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.IRON_BLOCK && e.getClickedBlock().getWorld().getName().equals(CosmicKOTHPlugin.get_().getWorld().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPreviewInventoryClickLOWEST(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("KOTH Loot Preview")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPreviewInventoryClickMONITOR(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("KOTH Loot Preview")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerDamagedByKoth(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && (event.getDamager().hasMetadata("envoyMonster") || event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof LivingEntity && ((LivingEntity) ((Projectile)event.getDamager()).getShooter()).hasMetadata("envoyMonster"))) {
            //event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.5);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onEntityDamageByFall(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && CosmicKOTHPlugin.get_().getWorld() != null && e.getEntity().getWorld().equals((Object)CosmicKOTHPlugin.get_().getWorld())) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (CosmicKOTHPlugin.get_().getKoth() != null && e.getItem().hasMetadata("kothLootbag")) {
            CosmicKOTHPlugin.get_().getKoth().kothBroadcast(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + e.getPlayer().getName() + (Object)ChatColor.GREEN + " has picked up the CosmicPvP Koth Lootbag!");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        ItemStack toRemove = null;
        for (ItemStack i : e.getDrops()) {
            if (!RewardUtils.isKOTHLootbag(i)) continue;
            toRemove = i;
        }
        if (toRemove != null) {
            e.getDrops().remove((Object)toRemove);
            Item j = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), toRemove);
            j.setMetadata("kothLootbag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicKOTHPlugin.get_(), (Object)true));
        }
        if (e.getEntity().getWorld().getName().contains("_koth")) {
            this.recentKothDeaths.asMap().putIfAbsent(e.getEntity().getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Long lastDeath;
        if (event.getFrom() != null && event.getTo() != null && event.getTo().getWorld().getName().contains("_koth") && !event.getTo().getWorld().getName().equals(event.getFrom().getWorld().getName()) && (lastDeath = (Long)this.recentKothDeaths.asMap().get(event.getPlayer().getUniqueId())) != null) {
            int secondsLeft;
            long timeToLookFor = this.getTimeTillReturn(event.getPlayer());
            long timeSinceDeath = System.currentTimeMillis() - lastDeath;
            if (timeSinceDeath < timeToLookFor && (secondsLeft = (int)((timeToLookFor - timeSinceDeath) / 1000L)) > 0) {
                if (!event.getPlayer().isOp()) {
                    event.setCancelled(true);
                }
                event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot return to the KOTH world for another " + CC.RedU + TimeUtils.formatSeconds((long)secondsLeft) + CC.Red + " due to your recent death!");
            }
        }
    }

    private long getTimeTillReturn(Player player) {
        return player.hasPermission("cosmicpvp.heroic") ? TimeUnit.MINUTES.toMillis(5L) : TimeUnit.MINUTES.toMillis(8L);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerTelportMonitor(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getTo() != null && event.getFrom() != null && event.getTo().getWorld().getName().contains("_koth") && !event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) {
            long minutesToReturn = this.getTimeTillReturn(event.getPlayer()) / 1000L;
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "Upon Death you will be " + CC.RedU + "unable" + CC.Red + " to return to the KOTH world for " + CC.RedB + minutesToReturn / 60L + CC.Red + " minutes!");
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (RewardUtils.isKOTHLootbag(e.getItemDrop().getItemStack())) {
            e.getItemDrop().setMetadata("kothLootbag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicKOTHPlugin.get_(), (Object)true));
        }
    }
}

