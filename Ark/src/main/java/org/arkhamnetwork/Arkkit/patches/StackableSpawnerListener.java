/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

final class StackableSpawnerListener
implements Listener {
    StackableSpawnerListener() {
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerStackSpawner(PlayerInteractEvent e) {
        if (e.hasItem() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem().getType() == Material.MOB_SPAWNER && e.getClickedBlock().getType() == Material.MOB_SPAWNER) {
            EntityType placedSpawnerType;
            Block placedSpawner = e.getClickedBlock();
            CreatureSpawner cs = (CreatureSpawner)((CraftBlock)placedSpawner).getState();
            EntityType spawnerInHandType = EntityType.fromId((int)e.getItem().getDurability());
            if (spawnerInHandType == (placedSpawnerType = cs.getSpawnedType()) && !e.getPlayer().isSneaking()) {
                Player p = e.getPlayer();
                int spawnersInHand = e.getItem().getAmount();
                int spawnersPlaced = cs.getSpawnerCount();
                String entityName = placedSpawnerType.name().replace("_", " ").toLowerCase();
                if (spawnersPlaced >= StackableSpawners.maxSpawnersPerBlock) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "You may only stack up to " + StackableSpawners.maxSpawnersPerBlock + "x spawners per block!");
                    e.setUseItemInHand(Event.Result.DENY);
                    e.setCancelled(true);
                    return;
                }
                if (!Board.getFactionAt((FLocation)new FLocation(placedSpawner)).isNormal() && !p.isOp()) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot stack mob spawners together in unclaimed chunks! Attempting to place normally...");
                    return;
                }
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                if (spawnersInHand + spawnersPlaced > StackableSpawners.maxSpawnersPerBlock) {
                    int spawnerSlotsLeft = StackableSpawners.maxSpawnersPerBlock - spawnersPlaced;
                    ItemStack inHand = p.getItemInHand();
                    inHand.setAmount(inHand.getAmount() - spawnerSlotsLeft);
                    p.setItemInHand(inHand);
                    cs.setSpawnerCount(StackableSpawners.maxSpawnersPerBlock);
                    p.sendMessage(CC.GreenB + " + " + CC.Green + spawnerSlotsLeft + "x (" + entityName + ") spawners" + CC.Gray + " (total: " + CC.Underline + cs.getSpawnerCount() + CC.Gray + ")");
                } else {
                    p.setItemInHand(null);
                    cs.setSpawnerCount((short)(cs.getSpawnerCount() + spawnersInHand));
                    p.sendMessage(CC.GreenB + " + " + CC.Green + spawnersInHand + "x (" + entityName + ") spawners" + CC.Gray + " (total: " + CC.Underline + cs.getSpawnerCount() + CC.Gray + ")");
                }
                SpawnerFee.updateMobSpawnerData(e.getClickedBlock());
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)placedSpawner.getLocation().add(0.5, 1.0, 0.5), (float)0.75f, (int)30);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerExamineSpawner(PlayerInteractEvent e) {
        if (e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.MOB_SPAWNER) {
            if (e.hasItem() && e.getItem().getType() == Material.MOB_SPAWNER || e.getPlayer().isSneaking()) {
                return;
            }
            Block placedSpawner = e.getClickedBlock();
            CreatureSpawner cs = (CreatureSpawner)((CraftBlock)placedSpawner).getState();
            String entityName = cs.getSpawnedType().name().replace("_", " ").toLowerCase();
            e.getPlayer().sendMessage(CC.YellowB + "(!) " + CC.Yellow + "This block has " + CC.Underline + cs.getSpawnerCount() + "x" + CC.Yellow + " (" + entityName + ") spawners in it.");
            if (cs.getSpawnerCount() == 1) {
                e.getPlayer().sendMessage(CC.Gray + "To add more spawners to this block, simply right-click it with spawner(s) of the same type in your hand!");
            }
        }
    }
}

