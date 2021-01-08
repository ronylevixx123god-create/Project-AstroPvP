/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockDamageEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.*;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.SpawnerLocation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.HashMap;

final class SpawnerFeeListener
        implements Listener {
    HashMap<Block, Long> blockPlaceEvent = new HashMap();

    SpawnerFeeListener() {
    }

    public static int getSpawnerFeeBase(EntityType et) {
        return Arkkit.get().getConfig().getInt("settings.spawner_fee." + et.toString().toLowerCase());
    }

    public static String convertBlockToString(Block b) {
        return b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ();
    }

    public static SpawnerLocation convertBlockToSpawnerLocation(Block b) {
        return new SpawnerLocation(b);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.MOB_SPAWNER) {
            SpawnerFee.playerSpawners.put(SpawnerFeeListener.convertBlockToString(e.getBlock()), SpawnerFeeListener.convertBlockToSpawnerLocation(e.getBlock()));
            this.blockPlaceEvent.put(e.getBlock(), System.currentTimeMillis());
            e.getPlayer().sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You will be fined $" + this.getMiningPrice(e.getPlayer(), e.getBlockPlaced()) + " for collecting this spawner in the future unless you break it within the next " + ChatColor.BOLD + "5 minutes.");
            FPlayer fp = FPlayers.i.get(e.getPlayer());
            Faction f = Board.getFactionAt(new FLocation(e.getBlockPlaced()));
            if (f.isNone()) {
                e.getPlayer().sendMessage(ChatColor.GRAY + "Mob Spawners placed in Wilderness spawn 60% less monsters than those placed in claimed land.");
            }
            if (f.isNormal() && fp.getRelationTo(f).isMember()) {
                e.getPlayer().sendMessage(ChatColor.GRAY + "Mob Spawners placed in your claims gradually add value to your faction's /f top over time.");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.MOB_SPAWNER && SpawnerFee.playerSpawners.containsKey(SpawnerFeeListener.convertBlockToString(e.getBlock()))) {
            if (this.blockPlaceEvent.containsKey(e.getBlock()) && System.currentTimeMillis() - this.blockPlaceEvent.get(e.getBlock()) <= 300000L) {
                SpawnerFee.playerSpawners.remove(SpawnerFeeListener.convertBlockToString(e.getBlock()));
                return;
            }
            Player p = e.getPlayer();
            int cost = this.getMiningPrice(p, e.getBlock());
            int baseMiningCost = this.getMiningPriceNoSpawnerCount(p, e.getBlock());
            CreatureSpawner ms = (CreatureSpawner) e.getBlock().getState();
            int spawnerCount = ms.getSpawnerCount();
            Arkkit.economy.withdrawPlayer(p, cost);
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- $" + cost + ChatColor.GRAY + "($" + baseMiningCost + "x" + spawnerCount + " spawners)");
            p.sendMessage(ChatColor.GRAY + "This 10% value fee is applied whenever you collect a spawner.");
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.7f);
            SpawnerFee.playerSpawners.remove(SpawnerFeeListener.convertBlockToString(e.getBlock()));
            this.blockPlaceEvent.remove(e.getBlock());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreakAttempt(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.MOB_SPAWNER && SpawnerFee.playerSpawners.containsKey(SpawnerFeeListener.convertBlockToString(e.getBlock()))) {
            if (this.blockPlaceEvent.containsKey(e.getBlock()) && System.currentTimeMillis() - this.blockPlaceEvent.get(e.getBlock()) <= 300000L) {
                return;
            }
            Player p = e.getPlayer();
            int cost = this.getMiningPrice(p, e.getBlock());
            int baseMiningCost = this.getMiningPriceNoSpawnerCount(p, e.getBlock());
            CreatureSpawner ms = (CreatureSpawner) e.getBlock().getState();
            int spawnerCount = ms.getSpawnerCount();
            if (!Arkkit.economy.has(p, cost)) {
                e.setCancelled(true);
                e.setExpToDrop(0);
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You do not have enough $ to collect this spawner.");
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "COST: " + ChatColor.GRAY + "$" + cost + " (" + ChatColor.RED + "$" + baseMiningCost + "x" + spawnerCount + " spawners" + ChatColor.GRAY + ")");
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplode(EntityExplodeEvent e) {
        final ArrayList<Location> blocksToCheck = new ArrayList<Location>();
        for (Block b : e.blockList()) {
            if (b.getType() != Material.MOB_SPAWNER) continue;
            blocksToCheck.add(b.getLocation());
        }
        Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

            @Override
            public void run() {
                for (Location l : blocksToCheck) {
                    if (l.getBlock().getType() == Material.MOB_SPAWNER) continue;
                    String s = SpawnerFeeListener.convertBlockToString(l.getBlock());
                    Bukkit.getLogger().info("[Arkkit (SpawnerFee)] Removing " + s + " from playerSpawners list due to explosion.");
                    SpawnerFee.playerSpawners.remove(s);
                }
            }
        }, 5L);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        if (e.getBlock().getType() == Material.MOB_SPAWNER && SpawnerFee.playerSpawners.containsKey(SpawnerFeeListener.convertBlockToString(e.getBlock()))) {
            if (this.blockPlaceEvent.containsKey(e.getBlock()) && System.currentTimeMillis() - this.blockPlaceEvent.get(e.getBlock()) <= 300000L) {
                return;
            }
            Player p = e.getPlayer();
            int cost = this.getMiningPrice(p, e.getBlock());
            int baseMiningCost = this.getMiningPriceNoSpawnerCount(p, e.getBlock());
            CreatureSpawner ms = (CreatureSpawner) e.getBlock().getState();
            int spawnerCount = ms.getSpawnerCount();
            e.getPlayer().sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You will be fined $" + this.getMiningPrice(e.getPlayer(), e.getBlock()) + " ($" + baseMiningCost + "x" + spawnerCount + " spawners) for collecting this spawner.");
            if (this.getMiningPriceModifier(e.getPlayer()) == 1.0) {
                e.getPlayer().sendMessage(ChatColor.GRAY + "Unlock or purchase a rank at " + ChatColor.UNDERLINE + "buy.cosmicpvp.com" + ChatColor.GRAY + " for smaller fees.");
            }
        }
    }

    private int getMiningPrice(Player p, Block b) {
        if (!SpawnerFee.spawnerFee) {
            return 0;
        }
        if (b.getType() == Material.MOB_SPAWNER) {
            CreatureSpawner ms = (CreatureSpawner) b.getState();
            EntityType spawned = ms.getSpawnedType();
            return (int) ((double) SpawnerFeeListener.getSpawnerFeeBase(spawned) * this.getMiningPriceModifier(p)) * ms.getSpawnerCount();
        }
        return 0;
    }

    private int getMiningPriceNoSpawnerCount(Player p, Block b) {
        if (!SpawnerFee.spawnerFee) {
            return 0;
        }
        if (b.getType() == Material.MOB_SPAWNER) {
            CreatureSpawner ms = (CreatureSpawner) b.getState();
            EntityType spawned = ms.getSpawnedType();
            return (int) ((double) SpawnerFeeListener.getSpawnerFeeBase(spawned) * this.getMiningPriceModifier(p));
        }
        return 0;
    }

    private double getMiningPriceModifier(Player p) {
        if (p.hasPermission("enchantments.max.20")) {
            return 0.6;
        }
        if (p.hasPermission("enchantments.max.12")) {
            return 0.7;
        }
        if (p.hasPermission("enchantments.max.9")) {
            return 0.75;
        }
        if (p.hasPermission("enchantments.max.6")) {
            return 0.8;
        }
        if (p.hasPermission("enchantments.max.3")) {
            return 0.9;
        }
        return 1.0;
    }
}

