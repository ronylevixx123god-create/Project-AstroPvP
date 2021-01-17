/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  de.dustplanet.silkspawners.SilkSpawners
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.compilex.CosmicCustomCreepers.utils.ItemUtils;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import de.dustplanet.silkspawners.SilkSpawners;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Random;

import static org.arkhamnetwork.Arkkit.patches.NoWaterProtSpawners.su;

final class NoWaterProtSpawnersListener
        implements Listener {
    int explosionDropChance = 0;
    int eggDropChance = 0;
    SilkSpawners ssPlugin = (SilkSpawners) Bukkit.getPluginManager().getPlugin("SilkSpawners");

    NoWaterProtSpawnersListener() {
        this.explosionDropChance = this.ssPlugin.config.getInt("explosionDropChance", 30);
        this.eggDropChance = this.ssPlugin.config.getInt("eggDropChance", 100);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityExplode(EntityExplodeEvent e) {
        Location targetLoc;
        int z;
        int y;
        int x;
        if (e.isCancelled() && e.getEntityType() == EntityType.PRIMED_TNT) {
            return;
        }
        int dropChance = this.explosionDropChance;
        int radius = 3;
        boolean tntExplosion = false;
        if (e.getEntityType() == EntityType.CREEPER) {
            Creeper c = (Creeper) e.getEntity();
            if (c.isPowered()) {
                radius = 6;
            }
            if (e.getEntity().hasMetadata("customCreeperType")) {
                CreeperTypes egg_type = CreeperTypes.valueOf(c.getMetadata("customCreeperType").get(0).asString());
                if (egg_type == CreeperTypes.Lucky) {
                    dropChance = Math.max(dropChance, 60);
                } else if (egg_type == CreeperTypes.Raid) {
                    dropChance = Math.max(dropChance, 100);
                }
            }
        } else if (e.getEntityType() == EntityType.PRIMED_TNT) {
            TNTTypes tnt_type;
            TNTPrimed tnt = (TNTPrimed) e.getEntity();
            if (!tnt.hasMetadata("ObbyEntity")) {
                tntExplosion = true;
            }
            if (tnt.hasMetadata("custom_tnt") && (tnt_type = TNTTypes.valueOf(tnt.getMetadata("custom_tnt").get(0).asString())) == TNTTypes.Lucky) {
                dropChance = Math.max(dropChance, 60);
            }
        }
        Block detonator = e.getLocation().getBlock();
        Location detonatorLoc = e.getLocation();
        boolean mobSpawner = false;
        boolean container = false;
        for (x = -radius; x <= radius; ++x) {
            block1:
            for (y = radius; y >= -radius; --y) {
                for (z = -radius; z <= radius; ++z) {
                    targetLoc = new Location(detonator.getWorld(), detonatorLoc.getX() + (double) x, detonatorLoc.getY() + (double) y, detonatorLoc.getZ() + (double) z);
                    Block b = targetLoc.getBlock();
                    Material mat = b.getType();
                    if (b.getType() == Material.MOB_SPAWNER) {
                        mobSpawner = true;
                        continue block1;
                    }
                    if (!tntExplosion || mat != Material.CHEST && mat != Material.ENDER_CHEST && mat != Material.TRAPPED_CHEST)
                        continue;
                    container = true;
                    continue block1;
                }
            }
        }
        if (!mobSpawner && !container) {
            return;
        }
        for (x = -radius; x <= radius; ++x) {
            for (y = radius; y >= -radius; --y) {
                for (z = -radius; z <= radius; ++z) {
                    Inventory inventory;
                    targetLoc = new Location(detonator.getWorld(), detonatorLoc.getX() + (double) x, detonatorLoc.getY() + (double) y, detonatorLoc.getZ() + (double) z);
                    FLocation fl = new FLocation(targetLoc);
                    if (Board.getFactionAt(fl).isWarZone()) continue;
                    Block b = targetLoc.getBlock();
                    Material type = b.getType();
                    if (type == Material.MOB_SPAWNER) {
                        if (new Random().nextInt(100) <= dropChance) {
                            CreatureSpawner cs = (CreatureSpawner) b.getState();
                            int spawnCount = cs.getSpawnerCount();
                            if (spawnCount == 4 && !Board.getFactionAt(new FLocation(b.getLocation())).isNormal()) {
                                cs.setSpawnerCount((short) 1);
                                spawnCount = 1;
                            }
                            while (spawnCount-- > 0) {
                                String entityID = su.getSpawnerEntityID(b);
                                int id = Integer.parseInt(entityID);
                                b.getWorld().dropItemNaturally(b.getLocation(), ItemUtils.getMobSpawner(EntityType.fromId(id)));
                            }
                        }
                        if (ArkPlugins.SPAWNER_FEE.getArkPlugin().isRunning()) {
                            SpawnerFee.playerSpawners.remove(BlockValueListener.convertBlockToString(b));
                        }
                        b.setType(Material.AIR);
                        continue;
                    }
                    if (!tntExplosion || type != Material.CHEST && type != Material.ENDER_CHEST && type != Material.TRAPPED_CHEST)
                        continue;
                    BlockState state = b.getState();
                    Location loc = b.getLocation();
                    int viewers = 0;
                    if (state instanceof InventoryHolder && (viewers = (inventory = ((InventoryHolder) state).getInventory()).getViewers().size()) > 0) {
                        inventory.getViewers().forEach(HumanEntity::closeInventory);
                    }
                    b.setType(Material.AIR);
                    Bukkit.getLogger().info("Breaking chest with TNT at " + loc.getBlockX() + "x " + loc.getBlockY() + "y " + loc.getBlockZ() + "z that had " + viewers + " inventory viewers");
                }
            }
        }
    }
}

