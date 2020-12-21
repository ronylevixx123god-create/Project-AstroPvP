/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.harry5574.utils.hologram.Hologram
 *  me.gtacraft.economy.EconomyAPI
 *  me.gtacraft.economy.GTAEconomy
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.player_shops;

import com.harry5574.utils.hologram.Hologram;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import me.gtacraft.economy.EconomyAPI;
import me.gtacraft.economy.GTAEconomy;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.player_shops.PlayerShops;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerShopListener
implements Listener {
    boolean gta_cash = new File("plugins/GTA-Economy.jar").exists();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player pl = e.getPlayer();
        if (PlayerShops.player_shops.containsKey((Object)pl)) {
            ItemStack is = this.getItem(PlayerShops.player_shops.get((Object)pl), 0);
            if (pl.getInventory().firstEmpty() == -1) {
                return;
            }
            PlayerShops.player_shops_inverse.remove((Object)PlayerShops.player_shops.get((Object)pl));
            PlayerShops.player_shops.remove((Object)pl);
            PlayerShops.player_shop_locations.remove((Object)pl);
            Hologram hg = PlayerShops.player_holograms.get((Object)pl);
            hg.destroy();
            PlayerShops.player_holograms.remove((Object)pl);
            if (is != null) {
                pl.getInventory().addItem(new ItemStack[]{is});
            }
        }
        if (PlayerShops.shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
            int taskid = PlayerShops.shop_teleport_task_ids.get(pl.getUniqueId());
            PlayerShops.shop_teleport_task_ids.remove(pl.getUniqueId());
            Bukkit.getScheduler().cancelTask(taskid);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player && PlayerShops.player_shops.containsKey((Object)e.getEntity())) {
            Player pl = (Player)e.getEntity();
            ItemStack is = this.getItem(PlayerShops.player_shops.get((Object)pl), 0);
            if (pl.getInventory().firstEmpty() == -1) {
                return;
            }
            PlayerShops.player_shops_inverse.remove((Object)PlayerShops.player_shops.get((Object)pl));
            PlayerShops.player_shops.remove((Object)pl);
            PlayerShops.player_shop_locations.remove((Object)pl);
            Hologram hg = PlayerShops.player_holograms.get((Object)pl);
            hg.destroy();
            PlayerShops.player_holograms.remove((Object)pl);
            if (is != null) {
                pl.getInventory().addItem(new ItemStack[]{is});
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player p_clicked;
        if (e.getRightClicked() instanceof Player && PlayerShops.player_shops.containsKey((Object)(p_clicked = (Player)e.getRightClicked()))) {
            Player pl = e.getPlayer();
            pl.openInventory(PlayerShops.player_shops.get((Object)p_clicked));
            pl.playSound(pl.getLocation(), Sound.VILLAGER_DEATH, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (ChatColor.stripColor((String)e.getInventory().getTitle()).contains("Price: ")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (!e.isLeftClick() || e.getRawSlot() != 0) {
                ((Player)e.getWhoClicked()).updateInventory();
                return;
            }
            ItemStack is = e.getInventory().getItem(0);
            if (is != null) {
                final Player pl = (Player)e.getWhoClicked();
                int price = this.getPrice(e.getInventory().getTitle());
                Player shop_owner = Bukkit.getPlayer((String)this.getShopOwner(e.getInventory()));
                if (pl.getInventory().firstEmpty() == -1) {
                    pl.sendMessage((Object)ChatColor.RED + "You don't have enough room in your inventory.");
                    Bukkit.getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                        @Override
                        public void run() {
                            pl.closeInventory();
                        }
                    }, 2L);
                    return;
                }
                if (PlayerShops.economy.has((OfflinePlayer)pl, (double)price) || this.gta_cash && (Double)GTAEconomy.player_balances.get(pl.getUniqueId()) >= (double)price) {
                    if (this.gta_cash) {
                        EconomyAPI.setUserBalance((UUID)pl.getUniqueId(), (double)(EconomyAPI.getUserBalance((UUID)pl.getUniqueId()) - (double)price));
                    } else {
                        PlayerShops.economy.withdrawPlayer((OfflinePlayer)pl, (double)price);
                    }
                    for (HumanEntity he : new ArrayList(e.getInventory().getViewers())) {
                        if (((Player)he).getName().equalsIgnoreCase(pl.getName())) {
                            final Player shopper = (Player)he;
                            Bukkit.getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                                @Override
                                public void run() {
                                    shopper.closeInventory();
                                }
                            }, 2L);
                            continue;
                        }
                        ((Player)he).closeInventory();
                        ((Player)he).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SHOP CLOSED!");
                    }
                    if (shop_owner != null) {
                        if (this.gta_cash) {
                            EconomyAPI.setUserBalance((UUID)shop_owner.getUniqueId(), (double)(EconomyAPI.getUserBalance((UUID)shop_owner.getUniqueId()) + (double)price));
                        } else {
                            PlayerShops.economy.depositPlayer((OfflinePlayer)shop_owner, (double)price);
                        }
                        PlayerShops.player_shops.remove((Object)shop_owner);
                        PlayerShops.player_shop_locations.remove((Object)shop_owner);
                        PlayerShops.player_shops_inverse.remove((Object)e.getInventory());
                        Hologram hg = PlayerShops.player_holograms.get((Object)shop_owner);
                        hg.destroy();
                        PlayerShops.player_holograms.remove((Object)shop_owner);
                        shop_owner.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "SOLD! " + (Object)ChatColor.GRAY + "+" + price + " " + PlayerShops.currency_name + "!");
                    }
                    pl.getInventory().addItem(new ItemStack[]{is});
                    pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "TRANSACTION SUCCESSFUL!" + (Object)ChatColor.GRAY + " -" + price + " " + PlayerShops.currency_name + "!");
                    pl.playSound(pl.getLocation(), Sound.ITEM_PICKUP, 1.0f, 1.0f);
                } else {
                    pl.sendMessage((Object)ChatColor.RED + "You don't have enough " + PlayerShops.currency_name + " to purchase this item.");
                    pl.sendMessage((Object)ChatColor.RED + "Price: " + price + " " + PlayerShops.currency_name);
                    Bukkit.getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                        @Override
                        public void run() {
                            pl.closeInventory();
                        }
                    }, 2L);
                }
            }
        }
    }

    public ItemStack getItem(Inventory inv, int index) {
        return inv != null ? inv.getItem(index) : null;
    }

    public String getShopOwner(Inventory inv) {
        String p_name = PlayerShops.player_shops_inverse.containsKey((Object)inv) ? PlayerShops.player_shops_inverse.get((Object)inv).getName() : null;
        return p_name;
    }

    public int getPrice(String title) {
        if (title.split(" ").length >= 2) {
            return Integer.parseInt(ChatColor.stripColor((String)title.split(" ")[1].replace("$", "")));
        }
        return 0;
    }
}

