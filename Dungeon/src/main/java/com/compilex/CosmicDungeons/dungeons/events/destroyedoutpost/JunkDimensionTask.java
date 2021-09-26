/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.Chest
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class JunkDimensionTask
extends BukkitRunnable {
    private Map<Player, Integer> junkDimensionPlayerTimers = new MapMaker().concurrencyLevel(32).weakKeys().makeMap();
    private List<Location> junkChestLocations;
    private List<Location> chestsWithKeys = Lists.newArrayList();
    private Location junkDimensionLocation;
    private DestroyedOutpostDungeon dungeon;

    public JunkDimensionTask(DestroyedOutpostDungeon dungeon) {
        this.dungeon = dungeon;
        this.junkChestLocations = this.getLocations(this.dungeon, Lists.newArrayList());
        this.junkDimensionLocation = new Location(dungeon.getWorld(), -674.0, 60.0, -295.0);
    }

    public void run() {
        this.junkDimensionPlayerTimers.forEach((player, timeLeft) -> {
            if (!player.isValid() || player.isDead() || !player.getWorld().getName().equals(this.dungeon.getWorldName())) {
                this.junkDimensionPlayerTimers.remove(player);
            } else if (timeLeft <= 0) {
                this.dungeon.broadcastMessage(CC.White + player.getName() + "'s couldn't escape the Junk Dimension in time, they have been lost in the void!");
                player.setHealth(0.0);
                this.junkDimensionPlayerTimers.remove(player);
            } else {
                if (timeLeft % 15 == 0 || timeLeft <= 10) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You will must escape this dimension in " + timeLeft + "s or you will be lost forever!");
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1.0f, 1.4f);
                }
                Map<Player, Integer> junkDimensionPlayerTimers = this.junkDimensionPlayerTimers;
                timeLeft = timeLeft - 1;
                junkDimensionPlayerTimers.put((Player)player, (Integer)timeLeft);
            }
        });
    }

    public void startJunkTimer(Player player) {
        for (Location loc : this.junkChestLocations) {
            loc.getBlock().setTypeId(Material.CHEST.getId(), false);
        }
        this.junkDimensionPlayerTimers.put(player, 30);
        this.dungeon.teleportPlayer(this.junkDimensionLocation, player, true);
        player.sendMessage(CC.GoldB + "(DUNGEON) " + CC.White + "You have been transported to a mysterious dimension... Maybe there's a key somewhere?");
        this.fillRandomChest();
    }

    private void fillRandomChest() {
        Chest chestBlock;
        Location chest = this.junkChestLocations.get(ThreadLocalRandom.current().nextInt(this.junkChestLocations.size()));
        Block block = chest.getBlock();
        Material type = block.getType();
        if (type != Material.CHEST && type != Material.TRAPPED_CHEST) {
            block.setType(Material.CHEST);
        }
        if ((chestBlock = (Chest)block.getState()) != null) {
            chestBlock.getInventory().addItem(new ItemStack[]{this.dungeon.createJunkReturningItem()});
            chestBlock.update();
            this.chestsWithKeys.add(chest);
        }
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.hasItem() && this.dungeon.isJunkOfReturning(event.getItem())) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            player.getInventory().remove(event.getItem());
            player.updateInventory();
            if (!WorldGuardUtils.isInRegionsContainsFast((Location)player.getLocation(), (List)Lists.newArrayList(new String[]{"junk_realm"}))) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot use this item outside of the Junk Dimension!");
                player.playSound(player.getLocation(), Sound.GHAST_SCREAM, 1.0f, 0.9f);
                return;
            }
            this.junkDimensionPlayerTimers.remove((Object)player);
            player.sendMessage(CC.GoldB + "(DUNGEON) " + CC.White + "You managed to find your way back to the Portal Dimension!");
            if (this.dungeon.getDungeonEvents().reversingPortalOrder) {
                this.dungeon.getDungeonEvents().teleportPlayerToLastPortal(player);
            } else {
                this.dungeon.getDungeonEvents().teleportPlayerToFirstPortal(player);
            }
            player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, 4.0f, 1.1f);
            player.playSound(player.getLocation(), Sound.GHAST_MOAN, 1.0f, 1.1f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 0));
        } else {
            Block block = event.getClickedBlock();
            if (block == null) {
                return;
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && this.chestsWithKeys.remove((Object)block.getLocation())) {
                event.getPlayer().sendMessage(CC.GreenB + "(!) " + CC.Green + "You have located the Clock of Returning!");
                event.getPlayer().sendMessage(CC.Gray + "Right-Click it to return to the Portal Dimension!");
            }
        }
    }

    public void onPlayerTeleport(PlayerTeleportEvent event) {
    }

    public List<Location> getLocations(Dungeon d, List<Location> list) {
        list.add(new Location(d.getWorld(), -679.0, 60.0, -294.0));
        list.add(new Location(d.getWorld(), -685.0, 60.0, -293.0));
        list.add(new Location(d.getWorld(), -682.0, 60.0, -300.0));
        list.add(new Location(d.getWorld(), -681.0, 60.0, -301.0));
        list.add(new Location(d.getWorld(), -680.0, 60.0, -302.0));
        list.add(new Location(d.getWorld(), -679.0, 60.0, -303.0));
        list.add(new Location(d.getWorld(), -678.0, 60.0, -302.0));
        list.add(new Location(d.getWorld(), -677.0, 60.0, -300.0));
        list.add(new Location(d.getWorld(), -675.0, 60.0, -300.0));
        list.add(new Location(d.getWorld(), -673.0, 60.0, -300.0));
        list.add(new Location(d.getWorld(), -677.0, 60.0, -305.0));
        list.add(new Location(d.getWorld(), -675.0, 60.0, -305.0));
        list.add(new Location(d.getWorld(), -673.0, 60.0, -305.0));
        list.add(new Location(d.getWorld(), -672.0, 60.0, -302.0));
        list.add(new Location(d.getWorld(), -671.0, 60.0, -303.0));
        list.add(new Location(d.getWorld(), -670.0, 60.0, -302.0));
        list.add(new Location(d.getWorld(), -669.0, 60.0, -301.0));
        list.add(new Location(d.getWorld(), -668.0, 60.0, -300.0));
        list.add(new Location(d.getWorld(), -667.0, 60.0, -299.0));
        list.add(new Location(d.getWorld(), -668.0, 60.0, -298.0));
        list.add(new Location(d.getWorld(), -670.0, 60.0, -297.0));
        list.add(new Location(d.getWorld(), -665.0, 60.0, -297.0));
        list.add(new Location(d.getWorld(), -665.0, 60.0, -295.0));
        list.add(new Location(d.getWorld(), -665.0, 60.0, -293.0));
        list.add(new Location(d.getWorld(), -670.0, 60.0, -293.0));
        list.add(new Location(d.getWorld(), -668.0, 60.0, -292.0));
        list.add(new Location(d.getWorld(), -667.0, 60.0, -291.0));
        list.add(new Location(d.getWorld(), -668.0, 60.0, -290.0));
        list.add(new Location(d.getWorld(), -669.0, 60.0, -289.0));
        list.add(new Location(d.getWorld(), -670.0, 60.0, -288.0));
        list.add(new Location(d.getWorld(), -671.0, 60.0, -287.0));
        list.add(new Location(d.getWorld(), -672.0, 60.0, -288.0));
        list.add(new Location(d.getWorld(), -673.0, 60.0, -290.0));
        list.add(new Location(d.getWorld(), -675.0, 60.0, -290.0));
        list.add(new Location(d.getWorld(), -677.0, 60.0, -290.0));
        list.add(new Location(d.getWorld(), -673.0, 60.0, -285.0));
        list.add(new Location(d.getWorld(), -675.0, 60.0, -285.0));
        list.add(new Location(d.getWorld(), -677.0, 60.0, -285.0));
        list.add(new Location(d.getWorld(), -678.0, 60.0, -288.0));
        list.add(new Location(d.getWorld(), -679.0, 60.0, -287.0));
        list.add(new Location(d.getWorld(), -680.0, 60.0, -288.0));
        list.add(new Location(d.getWorld(), -681.0, 60.0, -289.0));
        list.add(new Location(d.getWorld(), -682.0, 60.0, -290.0));
        list.add(new Location(d.getWorld(), -683.0, 60.0, -291.0));
        list.add(new Location(d.getWorld(), -682.0, 60.0, -292.0));
        list.add(new Location(d.getWorld(), -685.0, 60.0, -295.0));
        list.add(new Location(d.getWorld(), -685.0, 60.0, -297.0));
        list.add(new Location(d.getWorld(), -682.0, 60.0, -298.0));
        list.add(new Location(d.getWorld(), -680.0, 60.0, -297.0));
        list.add(new Location(d.getWorld(), -680.0, 60.0, -293.0));
        list.add(new Location(d.getWorld(), -682.0, 60.0, -298.0));
        return list;
    }
}

