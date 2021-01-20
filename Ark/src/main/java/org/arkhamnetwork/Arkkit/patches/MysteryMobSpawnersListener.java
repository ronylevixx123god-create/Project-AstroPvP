/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Sign
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.SignChangeEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

final class MysteryMobSpawnersListener
        implements Listener {
    MysteryMobSpawnersListener() {
    }

    protected static ItemStack getMobSpawnerFromMystery() {
        if (Math.random() < 0.1) {
            int x = new Random().nextInt(3);
            if (x == 0) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.GHAST);
            }
            if (x == 1) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.MAGMA_CUBE);
            }
            if (x == 2) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.MUSHROOM_COW);
            }
        } else {
            int x = new Random().nextInt(5);
            if (x == 0) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.ENDERMAN);
            }
            if (x == 1) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.PIG_ZOMBIE);
            }
            if (x == 2) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.CREEPER);
            }
            if (x == 3) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.IRON_GOLEM);
            }
            if (x == 4) {
                return MysteryMobSpawnersListener.getMobSpawner(EntityType.BLAZE);
            }
        }
        return MysteryMobSpawnersListener.getMobSpawner(EntityType.COW);
    }

    private static ItemStack getMobSpawner(EntityType entityType) {
        ItemStack i = ItemUtils.buildItem(Material.MOB_SPAWNER, entityType.getTypeId(), ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + MysteryMobSpawnersListener.getEntityName(entityType) + ChatColor.GOLD.toString() + " Spawner", ChatColor.GRAY + "Be careful where you place this!");
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            i = ItemID.setItemID(i, ItemID.generateNewItemID());
        }
        return i;
    }

    private static String getEntityName(EntityType et) {
        if (et == EntityType.IRON_GOLEM) {
            return "Iron Golem";
        }
        if (et == EntityType.MAGMA_CUBE) {
            return "Magmacube";
        }
        if (et == EntityType.MUSHROOM_COW) {
            return "Mushroom Cow";
        }
        if (et == EntityType.PIG_ZOMBIE) {
            return "Pig Zombie";
        }
        return et.getName();
    }

    @EventHandler
    public void onPlayerRedeemMysterySpawner(PlayerInteractEvent e) throws Exception {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && MysteryMobSpawners.isMysteryMobSpawner(e.getItem())) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getPlayer().getInventory().firstEmpty() == -1) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You have no free slots in your inventory!");
                return;
            }
            if (e.getItem().getAmount() > 1) {
                ItemStack mystery = e.getItem();
                mystery.setAmount(mystery.getAmount() - 1);
                e.getPlayer().setItemInHand(mystery);
            } else {
                e.getPlayer().setItemInHand(null);
            }
            ItemStack i = MysteryMobSpawnersListener.getMobSpawnerFromMystery();
            e.getPlayer().getInventory().addItem(i);
            e.getPlayer().updateInventory();
            //ParticleEffect.playEffect(ParticleEffect.SPELL, e.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0.6f, 50);
            de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayer(e.getPlayer(), e.getPlayer().getLocation(), 0, 1, 0, 0.6f, 50);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1.0f, 0.6f);
            if (i.getDurability() == 56) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l(!) &b&n" + e.getPlayer().getName() + "&b has just recieved a &d&lGhast Spawner&b from a Mystery Mob Spawner!"));
            } else if (i.getDurability() == 62) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l(!) &b&n" + e.getPlayer().getName() + "&b has just recieved a &d&lMagmacube Spawner&b from a Mystery Mob Spawner!"));
            } else if (i.getDurability() == 96) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l(!) &b&n" + e.getPlayer().getName() + "&b has just recieved a &d&lMooshroom Spawner&b from a Mystery Mob Spawner!"));
            }
            Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(e.getPlayer(), "MYSTERY_SPAWNER_OPEN", i));
        }
    }

    @EventHandler
    public void onPlayerInteractSign(PlayerInteractEvent e) {
        Sign s;
        if (e.hasBlock() && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST) && (s = (Sign) e.getClickedBlock().getState()).getLine(2).equals("MysterySpawner") && WorldGuardUtils.isPvPDisabled(e.getClickedBlock().getLocation())) {
            int price = Integer.parseInt(s.getLine(3).replace("$", "").replace(",", ""));
            Player p = e.getPlayer();
            if (!Arkkit.economy.has(p, price)) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "You do not have sufficient funds.");
                return;
            }
            if (p.getInventory().firstEmpty() == -1) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "You have no empty slots in your inventory!");
                return;
            }
            Arkkit.economy.withdrawPlayer(p, price);
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + " -$" + price);
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.2f, 0.3f);
            p.getInventory().addItem(MysteryMobSpawners.getMysteryMobSpawner());
            p.updateInventory();
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(2).equals("MysterySpawner") && !e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }
}

