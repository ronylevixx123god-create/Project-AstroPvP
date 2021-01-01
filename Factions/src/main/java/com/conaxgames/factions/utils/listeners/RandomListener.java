/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  com.sk89q.worldguard.protection.flags.Flag
 *  org.arkhamnetwork.Arkkit.patches.ItemNametag
 *  org.arkhamnetwork.Arkkit.utils.WorldGuardUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.EntityBlockFormEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.entity.SlimeSplitEvent
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.world.StructureGrowEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.conaxgames.factions.utils.listeners;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import java.util.Random;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

public class RandomListener
implements Listener {
    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        if (WorldGuardUtils.isInvincible((Location)e.getEntity().getLocation())) {
            if (e.getEntity() instanceof Player && ((Player)e.getEntity()).isOp()) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerInteractWithPufferfish(PlayerInteractEvent e) {
        ItemStack i;
        if (!(!e.hasItem() || e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK || (i = e.getItem()).getType() != Material.RAW_FISH && i.getType() != Material.COOKED_FISH || i.getDurability() != 3)) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "That fish is not safe to eat!");
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onTreeGrow(StructureGrowEvent e) {
        Faction baseFaction = Board.getFactionAt((FLocation)new FLocation(e.getLocation()));
        for (BlockState bs : e.getBlocks()) {
            Faction bsFaction = Board.getFactionAt((FLocation)new FLocation(bs.getLocation()));
            if (baseFaction.getTag().equals(bsFaction.getTag())) continue;
            e.setCancelled(true);
            break;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerStackSnow(PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().getType() == Material.SNOW && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getPlayer().isOp()) {
                return;
            }
            if (!WorldGuardUtils.getFlagState((Location)e.getClickedBlock().getLocation(), (Flag)DefaultFlag.BUILD)) {
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onMonsterDeathMultiplyXP(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            return;
        }
        if (e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            Player p;
            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)e.getEntity().getLastDamageCause();
            Entity damager = edbee.getDamager();
            if (damager instanceof Projectile && ((Projectile)damager).getShooter() instanceof Entity) {
                damager = (Entity)((Projectile)damager).getShooter();
            }
            if (damager instanceof Player && (p = (Player)damager).hasPermission("cosmicpvp.heroic")) {
                e.setDroppedExp((int)Math.round((double)e.getDroppedExp() * 1.3));
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerConsumePufferfish(PlayerItemConsumeEvent e) {
        if ((e.getItem().getType() == Material.RAW_FISH || e.getItem().getType() == Material.COOKED_FISH) && e.getItem().getDurability() == 3) {
            e.setCancelled(true);
            e.setItem(null);
            e.getPlayer().updateInventory();
            e.getPlayer().sendMessage((Object)ChatColor.YELLOW + "That does not look safe to eat!");
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        String msg = e.getMessage().toLowerCase();
        String msgCheck = msg.replaceAll(" ", "").replaceAll("_", "");
        if ((msgCheck.contains("raid") || msgCheck.contains("mc") || msgCheck.contains("revenge") || msgCheck.contains("custom")) && (msgCheck.contains("pvp") || msgCheck.contains("faction"))) {
            e.setCancelled(true);
            e.setMessage("");
            e.getPlayer().sendMessage((Object)ChatColor.RED + "The message you sent contains invalid word combinations.");
        }
        if (msgCheck.contains("pvping") && msgCheck.contains("net")) {
            e.setCancelled(true);
            e.setMessage("");
            e.getPlayer().sendMessage((Object)ChatColor.RED + "The message you sent contains invalid word combinations.");
        }
        if (msgCheck.contains("zenpvp")) {
            e.setCancelled(true);
            e.setMessage("");
            e.getPlayer().sendMessage((Object)ChatColor.RED + "The message you sent contains invalid word combinations.");
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        Location l = e.getEntity().getLocation();
        FLocation fl = new FLocation(l);
        Faction l_faction = Board.getFactionAt((FLocation)fl);
        if (!l_faction.isWarZone()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.SLIME && e.getDrops().size() == 0) {
            e.getDrops().add(new ItemStack(Material.SLIME_BALL, new Random().nextInt(2) + 1));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("Enchant") && e.getAction() == InventoryAction.HOTBAR_SWAP && e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BOOK && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equals((Object)ChatColor.DARK_GREEN + "Placeholder")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (e.isCancelled() && e.getPlayer().getItemInHand().getType() == Material.NAME_TAG) {
            e.setCancelled(false);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerUseNametag(PlayerInteractEvent e) {
        if (e.isCancelled() && e.hasItem() && e.getAction() == Action.RIGHT_CLICK_AIR && e.getItem().getType() == Material.NAME_TAG) {
            if (ItemNametag.isNametagItem((ItemStack)e.getItem())) {
                return;
            }
            e.setCancelled(false);
            e.setUseItemInHand(Event.Result.ALLOW);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerHurtByFall(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player) {
            short boot_dura;
            Player pl = (Player)e.getEntity();
            if (!e.isCancelled() && e.getDamage() > 0.0 && pl.getEquipment().getBoots() != null && (boot_dura = pl.getEquipment().getBoots().getDurability()) > 0 && boot_dura < pl.getEquipment().getBoots().getType().getMaxDurability()) {
                pl.getEquipment().getBoots().setDurability((short)(boot_dura - 1));
                pl.updateInventory();
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerTeleportEvent(PlayerTeleportEvent e) {
        if (!e.getPlayer().isOp() && WorldGuardUtils.getRegionName((Location)e.getTo()).startsWith("vip_")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage((Object)ChatColor.RED + "There is an anti-teleportation field active at your target location!");
            return;
        }
    }

    @EventHandler
    public void onEntityBlockFormEvent(EntityBlockFormEvent e) {
        FLocation ploc;
        if (e.getBlock().getType().equals((Object)Material.ANVIL) && Board.getFactionAt((FLocation)(ploc = new FLocation(e.getEntity().getLocation()))).isWarZone()) {
            Item item = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.ANVIL, 1));
            item.setVelocity(e.getEntity().getVelocity());
            e.getEntity().remove();
        }
    }
}

