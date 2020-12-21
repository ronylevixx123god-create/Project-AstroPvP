/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Dispenser
 *  org.bukkit.block.Dropper
 *  org.bukkit.block.Hopper
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.minecart.HopperMinecart
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockFadeEvent
 *  org.bukkit.event.block.BlockFromToEvent
 *  org.bukkit.event.block.BlockSpreadEvent
 *  org.bukkit.event.block.LeavesDecayEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.inventory.InventoryMoveItemEvent
 *  org.bukkit.event.inventory.InventoryPickupItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.InventoryHolder
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashMap;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.TickRatelimit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

final class TickRatelimitListener
implements Listener {
    protected static HashMap<String, Integer> flow_counts = new HashMap();

    TickRatelimitListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onBlockFromToEvent_LOW(BlockFromToEvent e) {
        if (!TickRatelimit.limitFlow && !TickRatelimit.monitorFlow) {
            return;
        }
        if (TickRatelimit.requirePlayerNearby && TickRatelimit.limitFlow && !TickRatelimit.isLoadedChunk(e.getBlock().getChunk())) {
            if (TickRatelimit.refreshing_chunks && TickRatelimit.allowRefreshChunks) {
                TickRatelimit.trackChunk(e.getBlock().getChunk());
            } else {
                e.setCancelled(true);
                return;
            }
        }
        int maxFlowPerTick = Arkkit.get().getConfig().getInt("settings.limit_water_flow.block-flow-per-tick");
        int flow_count = this.getChunkFlowingCount(e.getBlock());
        if (flow_count >= maxFlowPerTick) {
            Bukkit.getLogger().info("[Arkkit (TickRateLimit)] Ratelimiting flow at " + (Object)e.getBlock().getLocation() + " due to flow_count (" + flow_count + ") >= " + maxFlowPerTick);
            if (TickRatelimit.limitFlow) {
                e.setCancelled(true);
            }
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockSpreadEvent(BlockSpreadEvent e) {
        if (!TickRatelimit.isLoadedChunk(e.getBlock().getLocation().getChunk())) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockFade(BlockFadeEvent e) {
        if (!TickRatelimit.isLoadedChunk(e.getBlock().getLocation().getChunk())) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onLeavesDecay(LeavesDecayEvent e) {
        if (!TickRatelimit.isLoadedChunk(e.getBlock().getLocation().getChunk())) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        if (!TickRatelimit.limitHopperOut) {
            return;
        }
        Location l = null;
        InventoryHolder holder = e.getSource().getHolder();
        if (holder instanceof Hopper) {
            l = ((Hopper)holder).getLocation();
        }
        if (holder instanceof HopperMinecart) {
            l = ((HopperMinecart)holder).getLocation();
        }
        if (holder instanceof Dispenser) {
            l = ((Dispenser)holder).getLocation();
        }
        if (holder instanceof Dropper) {
            l = ((Dropper)holder).getLocation();
        }
        if (l != null && !TickRatelimit.isLoadedChunk(l.getChunk())) {
            e.setCancelled(true);
            ++TickRatelimit.blockedHopperOUT;
            return;
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onInventoryPickupEvent(InventoryPickupItemEvent e) {
        if (!TickRatelimit.limitHopperIn) {
            return;
        }
        Location l = null;
        if (e.getInventory().getHolder() instanceof Hopper) {
            l = ((Hopper)e.getInventory().getHolder()).getLocation();
        }
        if (e.getInventory().getHolder() instanceof HopperMinecart) {
            l = ((HopperMinecart)e.getInventory().getHolder()).getLocation();
        }
        if (l != null && !TickRatelimit.isLoadedChunk(l.getChunk())) {
            e.setCancelled(true);
            ++TickRatelimit.blockedHopperIN;
            return;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (!TickRatelimit.limitCreatureSpawn) {
            return;
        }
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM && e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER && !TickRatelimit.isLoadedChunk(e.getLocation().getChunk())) {
            e.setCancelled(true);
            ++TickRatelimit.blockedCreatureSpawn;
            return;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockFromToEvent_MONITOR(BlockFromToEvent e) {
        if (!TickRatelimit.limitFlow && !TickRatelimit.monitorFlow) {
            return;
        }
        int c = flow_counts.containsKey(e.getBlock().getChunk().getX() + "," + e.getBlock().getChunk().getZ()) ? flow_counts.get(e.getBlock().getChunk().getX() + "," + e.getBlock().getChunk().getZ()) : 0;
        flow_counts.put(e.getBlock().getChunk().getX() + "," + e.getBlock().getChunk().getZ(), ++c);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity() != null && e.getEntityType() == EntityType.PRIMED_TNT) {
            int cx = e.getEntity().getLocation().getChunk().getX();
            int cz = e.getEntity().getLocation().getChunk().getZ();
            for (int x = -1; x < 1; ++x) {
                for (int z = -1; z < 1; ++z) {
                    TickRatelimit.trackChunk(cx + x, cz + z);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!TickRatelimit.limitFlow) {
            return;
        }
        if (e.hasItem() && (e.getItem().getType() == Material.BUCKET || e.getItem().getType().name().toLowerCase().contains("water") || e.getItem().getType().name().toLowerCase().contains("lava")) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Player pl = e.getPlayer();
            int cx = pl.getLocation().getChunk().getX();
            int cz = pl.getLocation().getChunk().getZ();
            for (int x = -1; x < 1; ++x) {
                for (int z = -1; z < 1; ++z) {
                    TickRatelimit.trackChunk(cx + x, cz + z);
                }
            }
        }
    }

    private int getChunkFlowingCount(Block b) {
        return b == null || b.getChunk() == null ? 0 : (flow_counts.containsKey(b.getChunk().getX() + "," + b.getChunk().getZ()) ? flow_counts.get(b.getChunk().getX() + "," + b.getChunk().getZ()) : 0);
    }
}

