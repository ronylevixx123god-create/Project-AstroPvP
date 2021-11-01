/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.itemfilter.listeners;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import me.ifamasssxd.itemfilter.Filter;
import me.ifamasssxd.itemfilter.FilterManager;
import me.ifamasssxd.itemfilter.ItemCategory;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ItemFilterListener
implements Listener {
    public Set<UUID> alerted = new HashSet<UUID>();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        if (event.getInventory().getName().startsWith("Item Filter:") && player.hasMetadata("filter_edit")) {
            player.removeMetadata("filter_edit", AstroCore.getInstance());
            Bukkit.getScheduler().scheduleSyncDelayedTask(AstroCore.getInstance(), () -> {
                player.openInventory(AstroCore.getInstance().getFilterManager().getCategoryMenu((Player)event.getPlayer()));
                player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 0.3f, 1.4f);
            }, 1L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().startsWith("Item Filter:")) {
            Material type;
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            Material material = type = item == null ? null : item.getType();
            if (item == null || type == Material.AIR) {
                return;
            }
            FilterManager manager = AstroCore.getInstance().getFilterManager();
            if (event.getRawSlot() + 1 > event.getInventory().getSize()) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            Filter filter = manager.getItemFilters().computeIfAbsent(player.getUniqueId(), e -> new Filter());
            if (filter.canPickupItem(item, type)) {
                filter.removeItemFromFilter(item);
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3f, 1.4f);
            } else {
                filter.addFilterItem(item);
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.3f, 1.4f);
            }
            event.getInventory().setItem(event.getRawSlot(), manager.getFilterMenuItem(item, filter));
            player.updateInventory();
        } else if (event.getInventory().getName().equals("Item Filter Categories")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            if (event.getRawSlot() + 1 > event.getInventory().getSize()) {
                return;
            }
            FilterManager manager = AstroCore.getInstance().getFilterManager();
            ItemCategory category = ItemCategory.getFromItemName(item.getItemMeta().getDisplayName());
            if (category == null) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            player.setMetadata("filter_edit", new FixedMetadataValue(AstroCore.getInstance(), ""));
            player.openInventory(manager.getFilterInventory(category, (Player)event.getWhoClicked()));
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 2.0f);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPLayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/filter")) {
            event.setMessage(event.getMessage().replaceFirst("/filter", "/filters"));
        }
        if (event.getMessage().equals("/ft")) {
            event.setMessage("/filters toggle");
        }
        if (event.getMessage().equals("/fe")) {
            event.setMessage("/filters edit");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.alerted.remove(event.getPlayer().getUniqueId());
    }

    public Set<UUID> getAlerted() {
        return this.alerted;
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Material type;
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();
        Material material = type = item == null ? null : item.getType();
        if (item == null || type == Material.AIR) {
            return;
        }
        FilterManager manager = AstroCore.getInstance().getFilterManager();
        Filter filter = manager.getItemFilters().get(player.getUniqueId());
        if (filter != null && filter.isEnabled() && !filter.canPickupItem(item, type)) {
            if (!this.alerted.contains(player.getUniqueId())) {
                player.sendMessage("");
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot pickup some me.astropvp.cratekeys.items because you have an Item /filter enabled!");
                player.sendMessage(ChatColor.GRAY + "Use" + ChatColor.WHITE + "/ft " + ChatColor.GRAY + "to disable your active /filter");
                player.sendMessage("");
                this.alerted.add(player.getUniqueId());
            }
            event.setCancelled(true);
        }
    }
}

