/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicmasks.MaskType
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Chest
 *  org.bukkit.block.Dispenser
 *  org.bukkit.block.Dropper
 *  org.bukkit.block.Furnace
 *  org.bukkit.block.Hopper
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import me.ifamasssxd.cosmicmasks.MaskType;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class HotbarArmorSwapListener
        implements Listener {
    HotbarArmorSwapListener() {
    }

    public boolean isArmor(Material m) {
        String n = m.toString().toLowerCase();
        if (m == null || m == Material.AIR) {
            return false;
        }
        return n.contains("helmet") || n.contains("chestplate") || n.contains("leggings") || n.contains("boots");
    }

    public void equipArmor(Player player, ItemStack is) {
        if (is == null || is.getType() == Material.AIR) {
            return;
        }
        String n = is.getType().toString().toLowerCase();
        PlayerInventory inv = player.getInventory();
        if (n.contains("helmet")) {
            ItemStack old = inv.getHelmet();
            inv.setHelmet(is);
            inv.removeItem(is);
            if (old != null && old.getType() != Material.AIR) {
                inv.setItemInHand(old);
            }
        } else if (n.contains("chestplate")) {
            ItemStack old = inv.getChestplate();
            inv.setChestplate(is);
            inv.removeItem(is);
            if (old != null && old.getType() != Material.AIR) {
                inv.setItemInHand(old);
            }
        } else if (n.contains("leggings")) {
            ItemStack old = inv.getLeggings();
            inv.setLeggings(is);
            inv.removeItem(is);
            if (old != null && old.getType() != Material.AIR) {
                inv.setItemInHand(old);
            }
        } else if (n.contains("boots")) {
            ItemStack old = inv.getBoots();
            inv.setBoots(is);
            inv.removeItem(is);
            if (old != null && old.getType() != Material.AIR) {
                inv.setItemInHand(old);
            }
        }
        player.updateInventory();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand() == null || !this.isArmor(e.getPlayer().getItemInHand().getType())) {
            return;
        }
        if (e.hasBlock() && (e.getClickedBlock().getState() instanceof Chest || e.getClickedBlock().getState() instanceof Dispenser || e.getClickedBlock().getState() instanceof Dropper || e.getClickedBlock().getState() instanceof Furnace || e.getClickedBlock().getState() instanceof Hopper || e.getClickedBlock().getType() == Material.GOLD_BLOCK || e.getClickedBlock().getType() == Material.IRON_BLOCK)) {
            return;
        }
        String worldName = e.getPlayer().getWorld().getName();
        if (e.isCancelled() && (worldName.equals("world_duels") || worldName.equals("world_duels2") || worldName.equals("cosmic-pvparenas"))) {
            return;
        }
        Player p = e.getPlayer();
        if (p.hasMetadata("lastArmorSwap") && (long) MinecraftServer.currentTick - p.getMetadata("lastArmorSwap").get(0).asLong() <= 12L) {
            return;
        }
        if (e.isCancelled() && p.hasMetadata("noArmorSwap")) {
            return;
        }
        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);
        if (AntiHack.hasTeleportedCrossWorld(p)) {
            return;
        }
        p.setMetadata("lastArmorSwap", new FixedMetadataValue(Arkkit.get(), MinecraftServer.currentTick));
        this.equipArmor(p, e.getItem());
        if (HotbarArmorSwap.masksEnabled && e.hasItem() && e.getItem().getType().toString().toLowerCase().contains("helmet")) {
            MaskType.rescanMask(p, true);
        }
        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 10.0f, 0.95f);
    }
}

