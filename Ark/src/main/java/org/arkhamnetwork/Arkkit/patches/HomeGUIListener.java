/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.User;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

class HomeGUIListener
implements Listener {
    private List<String> aliases = Lists.newArrayList("/home", "/ehome", "/homes", "/ehomes");
    private HomeGUIManager manager;

    public HomeGUIListener(HomeGUIManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (this.aliases.contains(event.getMessage().toLowerCase())) {
            event.setCancelled(true);
            event.setMessage("/null");
            Player player = event.getPlayer();
            player.openInventory(this.manager.getHomeInventory(player, 1));
        }
    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent event) {
        if (event.getPlayer().hasMetadata("custom_home")) {
            event.getPlayer().removeMetadata("custom_home", (Plugin)Arkkit.get());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().startsWith("Your Homes")) {
            String homeName;
            String pageStr;
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }
            if (event.getRawSlot() < 0 || event.getRawSlot() >= event.getInventory().getSize()) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            if (event.getInventory().getName().contains("#") && StringUtils.isNumeric((String)(pageStr = event.getInventory().getName().split("#")[1].split(" \\(")[0]))) {
                int pageOn = Integer.parseInt(pageStr);
                if (item.getType() == Material.ARROW) {
                    if (event.getRawSlot() == event.getInventory().getSize() - 1) {
                        player.openInventory(this.manager.getHomeInventory(player, pageOn + 1));
                        return;
                    }
                    if (event.getRawSlot() == event.getInventory().getSize() - 9) {
                        player.openInventory(this.manager.getHomeInventory(player, pageOn - 1));
                        return;
                    }
                }
            }
            if (!this.isValidHome(player, homeName = ChatColor.stripColor((String)item.getItemMeta().getDisplayName()).replace(" ", "_").toLowerCase())) {
                player.sendMessage((Object)ChatColor.RED + "That home is no longer valid!");
                player.closeInventory();
                return;
            }
            if (event.getClick() == ClickType.RIGHT) {
                player.openInventory(this.manager.getMaterialSelection());
                player.setMetadata("custom_home", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)homeName));
                player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0f, 1.0f);
            } else if (event.getClick() == ClickType.LEFT) {
                player.closeInventory();
                player.chat("/home " + homeName);
            } else if (event.getClick() == ClickType.MIDDLE) {
                player.closeInventory();
                player.chat("/delhome " + homeName);
            }
        } else if (event.getInventory().getName().equals("Select Home Icon")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            if (event.getRawSlot() < 0 || event.getRawSlot() >= event.getInventory().getSize()) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            if (player.hasMetadata("custom_home")) {
                String homeName = ((MetadataValue)player.getMetadata("custom_home").get(0)).asString();
                if (!this.isValidHome(player, homeName)) {
                    player.sendMessage((Object)ChatColor.RED + "That home is no longer valid!");
                    player.closeInventory();
                    return;
                }
                CustomHomes customHomes = this.manager.getCustomHomes().get(player.getUniqueId());
                if (customHomes == null) {
                    customHomes = new CustomHomes();
                    this.manager.getCustomHomes().put(player.getUniqueId(), customHomes);
                }
                customHomes.put(homeName, item.getType());
                player.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Saved " + homeName + " home icon to " + (Object)ChatColor.GREEN + ChatColor.UNDERLINE.toString() + StringUtils.capitaliseAllWords((String)item.getType().name().toLowerCase().replace("_", " ")));
                player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 0.8f);
                player.removeMetadata("custom_home", (Plugin)Arkkit.get());
                player.closeInventory();
                player.chat("/home");
            } else {
                player.closeInventory();
            }
        }
    }

    private boolean isValidHome(Player player, String name) {
        User user = EssentialsUtils.getEssentials().getUser(player);
        try {
            return user.getHome(name) != null;
        }
        catch (Exception e) {
            return false;
        }
    }
}

