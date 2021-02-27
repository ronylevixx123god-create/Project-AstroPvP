/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.coreprotect.CoreProtect
 *  net.coreprotect.CoreProtectAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.listeners;

import java.io.File;

import com.massivecraft.factions.P;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CreeperEggLimiter
implements Listener {
    private boolean coreProtectEnabled = new File("plugins/CoreProtect.jar").exists();

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerInteractCreeper(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.MONSTER_EGG && item.getDurability() == EntityType.CREEPER.getTypeId() && event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.useItemInHand() != Event.Result.DENY) {
            this.logPlacement(event.getClickedBlock(), event.getPlayer().getName(), (byte)EntityType.CREEPER.getTypeId());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled() && e.useItemInHand() != Event.Result.ALLOW) {
            return;
        }
        if (e.hasItem() && e.getItem().getType() == Material.MONSTER_EGG && e.getItem().getDurability() == 50 && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Player pl = e.getPlayer();
            if (!e.hasBlock()) {
                Bukkit.getLogger().info("[CosmicFactionUtils (AntiCreeperEggs)] No block found for interact event!");
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                return;
            }
            Material m1 = e.getClickedBlock().getType();
            Material m2 = e.getClickedBlock().getLocation().add(0.0, 1.0, 0.0).getBlock().getType();
            if (e.getClickedBlock().getLocation().distanceSquared(pl.getLocation()) > 25.0) {
                pl.sendMessage((Object)ChatColor.RED + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " aim so far away when placing a spawner egg.");
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                return;
            }
            if (m1 == Material.CHEST || m2 == Material.CHEST || m1 == Material.TRAPPED_CHEST || m2 == Material.TRAPPED_CHEST) {
                Block chestBlock = null;
                chestBlock = m1 == Material.CHEST || m1 == Material.TRAPPED_CHEST ? e.getClickedBlock() : e.getClickedBlock().getLocation().add(0.0, 1.0, 0.0).getBlock();
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                ItemStack creeperEggs = e.getItem();
                if (creeperEggs.getAmount() - 1 <= 0) {
                    pl.setItemInHand(null);
                } else {
                    creeperEggs.setAmount(creeperEggs.getAmount() - 1);
                    pl.setItemInHand(creeperEggs);
                }
                pl.updateInventory();
                Creeper c = (Creeper)chestBlock.getWorld().spawnEntity(chestBlock.getLocation(), EntityType.CREEPER);
                c.setMetadata("noSuffocation", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)true));
                Bukkit.getLogger().info("[CosmicFactionUtils (AntiCreeperEggs)] Spawned CREEPER inside of chest block at " + (Object)chestBlock.getLocation());
                this.logPlacement(chestBlock, pl.getName(), (byte)EntityType.CREEPER.getTypeId());
            }
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION && e.getEntity().hasMetadata("noSuffocation")) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    private void logPlacement(Block block, String user, byte data) {
        if (!this.coreProtectEnabled) {
            return;
        }
        try {
            CoreProtectAPI api = ((CoreProtect)Bukkit.getPluginManager().getPlugin("CoreProtect")).getAPI();
            if (api != null) {
                api.logPlacement(user, block.getLocation(), Material.MONSTER_EGG.getId(), data);
                Bukkit.getLogger().info("[CosmicFactionUtils] Tracking placement location for " + user + ": " + data);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

