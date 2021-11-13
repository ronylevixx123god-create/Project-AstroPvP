/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.CoreProtectUtils
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.decimatemc.decimatecollectionchests.DecimateCollectionChests
 *  com.decimatemc.decimatecollectionchests.item.CollectionChestItem
 *  com.google.common.collect.Sets
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.Role
 *  org.arkhamnetwork.Arkkit.patches.SpawnerFee
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.block.Chest
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.items.ChunkBusterItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.CoreProtectUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.decimatemc.decimatecollectionchests.DecimateCollectionChests;
import com.decimatemc.decimatecollectionchests.item.CollectionChestItem;
import com.google.common.collect.Sets;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Role;
import java.util.Set;
import org.arkhamnetwork.Arkkit.patches.SpawnerFee;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkBusterListener
implements Listener {
    private static Set<String> playersUsingChunkBusters = Sets.newHashSet();
    private static Set<Location> chunksBeingBusted = Sets.newHashSet();

    public static Set<Location> getChunksBeingBusted() {
        return chunksBeingBusted;
    }

    private void attemptChunkBust(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        Faction pFaction = FactionUtils.getFaction((Player)player);
        final Block block = event.getBlock();
        event.setCancelled(true);
        if (playersUsingChunkBusters.contains(player.getUniqueId().toString())) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou are already using a chunk buster, please wait until it is done before using another one."));
            return;
        }
        if (item.getAmount() != 1) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou cannot use stacked chunk busters."));
            return;
        }
        if (player.getWorld().getEnvironment() != World.Environment.NORMAL) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou can only use chunk busters in the overworld."));
            return;
        }
        if (pFaction == null || pFaction.isNone()) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou must be in a faction to use a chunk buster."));
            return;
        }
        if (!FactionUtils.getFactionAt((Location)block.getLocation()).getId().equals(pFaction.getId())) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou can only use chunk busters in your faction territory."));
            return;
        }
        if (!FactionUtils.getFPlayer((Player)player).getRole().isAtLeast(Role.MODERATOR)) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou must be at least a moderator to use a chunk buster."));
            return;
        }
        Chunk chunk = event.getBlock().getChunk();
        final int X = chunk.getX() * 16;
        final int Z = chunk.getZ() * 16;
        final Location chunkCenter = new Location(chunk.getWorld(), (double)(chunk.getX() << 4), (double)block.getY(), (double)(chunk.getZ() << 4));
        if (chunksBeingBusted.contains((Object)chunkCenter)) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cThis chunk is already being busted, please wait for it to finish."));
            return;
        }
        chunksBeingBusted.add(chunkCenter);
        playersUsingChunkBusters.add(player.getUniqueId().toString());
        for (int x = 0; x < 16; ++x) {
            int z = 0;
            while (z < 16) {
                final int finalZ = z++;
                final int finalX = x;
                new BukkitRunnable(){
                    int y;
                    {
                        this.y = block.getLocation().getBlockY() + 3;
                    }

                    public void run() {
                        --this.y;
                        if (this.y < 0) {
                            this.cancel();
                            chunksBeingBusted.remove((Object)chunkCenter);
                            playersUsingChunkBusters.remove(player.getUniqueId().toString());
                            return;
                        }
                        Block _block = event.getBlock().getWorld().getBlockAt(X + finalX, this.y, Z + finalZ);
                        if (_block.getType() != Material.BEDROCK && _block.getType() != Material.AIR) {
                            if (_block.getType() == Material.MOB_SPAWNER) {
                                CreatureSpawner ms = (CreatureSpawner)_block.getState();
                                ms.setStackCount(0);
                                SpawnerFee.playerSpawners.remove(ChunkBusterListener.convertBlockToString(_block));
                            }
                            if (_block.getType() == Material.CHEST) {
                                Chest chest = (Chest)_block.getState();
                                if (!chest.getBlockInventory().getTitle().equalsIgnoreCase(CC.translate((String)DecimateCollectionChests.getInstance().getChestConfiguration().getChestName()))) {
                                    return;
                                }
                                _block.getWorld().dropItemNaturally(_block.getLocation(), CollectionChestItem.getItem((int)1));
                                DecimateCollectionChests.getInstance().getChestMemory().removeLocation(_block.getLocation());
                                Bukkit.getScheduler().runTaskAsynchronously((Plugin)DecimateCollectionChests.getInstance(), () -> DecimateCollectionChests.getInstance().getChestSaver().removeChest(_block.getLocation()));
                            }
                            CoreProtectUtils.logBreak((String)player.getName(), (Location)_block.getLocation(), (int)_block.getType().getId(), (byte)_block.getData());
                            _block.setType(Material.AIR);
                        }
                    }
                }.runTaskTimer((Plugin)CosmicDungeonsPlugin.get(), 0L, 5L);
            }
        }
        if (ChunkBusterItem.getUses(item) == 1) {
            player.setItemInHand(null);
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou have used all of your chunk buster uses!"));
            player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1.0f, 1.0f);
            return;
        }
        ChunkBusterItem.setUses(item, ChunkBusterItem.getUses(item) - 1);
        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1.0f, 1.0f);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        if (ChunkBusterItem.isChunkBuster(item)) {
            if (ChunkBusterItem.getUses(item) > 0) {
                this.attemptChunkBust(event);
            } else {
                player.sendMessage(CC.translate((String)"&c&l(!) &cThis chunk buster has no remaining uses."));
            }
            event.setCancelled(true);
        }
    }

    public static String convertBlockToString(Block b) {
        return b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ();
    }
}

