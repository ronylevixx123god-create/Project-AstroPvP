/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.DecimalFormatType
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.recolance.auction.controller;

import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.recolance.auction.Auction;
import me.recolance.auction.util.ItemUtils;
import me.recolance.auction.util.Util;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;

public class ConfirmMenu
        implements Listener {
    private static final DecimalFormat formatter = new DecimalFormat("#,###");
    private static final DecimalFormat format = new DecimalFormat("#.##");

    public static Inventory getConfirmMenu(ItemStack item, double price, double sellFee, double fee) {
        Inventory inv = Bukkit.createInventory(null, (int) 9, (String) "Confirm Auction");
        String feeString = CC.RedB + "LISTING FEE: " + CC.RedB + "$" + CC.Red + NumberUtils.format((double) sellFee, (DecimalFormatType) DecimalFormatType.MONEY) + (fee <= 0.0 ? CC.Red + " (Infernal Outpost)" : CC.Red + " (" + format.format(fee * 100.0) + "%)");
        ItemStack confirm = ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short) 5, (Object) ChatColor.GREEN + ChatColor.BOLD.toString() + "Confirm Auction Price", (Object) ChatColor.GRAY + "Click to " + (Object) ChatColor.GREEN + ChatColor.BOLD.toString() + "accept" + (Object) ChatColor.GRAY + " the auction price for: ", (Object) ChatColor.WHITE + Util.getItemName(item), " ", (Object) ChatColor.YELLOW + ChatColor.BOLD.toString() + "PRICE: $" + (Object) ChatColor.YELLOW + formatter.format(price), feeString);
        ItemStack decline = ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short) 14, (Object) ChatColor.RED + ChatColor.BOLD.toString() + "Decline Auction Price", (Object) ChatColor.GRAY + "Click to " + (Object) ChatColor.RED + ChatColor.BOLD.toString() + "decline" + (Object) ChatColor.GRAY + " the auction.", "", (Object) ChatColor.YELLOW + ChatColor.BOLD.toString() + "PRICE: $" + (Object) ChatColor.YELLOW + formatter.format(price), feeString);
        inv.setItem(0, confirm);
        inv.setItem(1, confirm);
        inv.setItem(2, confirm);
        inv.setItem(3, confirm);
        inv.setItem(4, item);
        inv.setItem(5, decline);
        inv.setItem(6, decline);
        inv.setItem(7, decline);
        inv.setItem(8, decline);
        return inv;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("Confirm Auction")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getClickedInventory() == null || e.getWhoClicked().getOpenInventory().getTopInventory() == null || !e.getClickedInventory().equals((Object) e.getWhoClicked().getOpenInventory().getTopInventory())) {
                return;
            }
            Player p = (Player) e.getWhoClicked();
            if (!p.hasMetadata("auctionConfirm")) {
                this.closePlayerInventoryOnClick(p);
                return;
            }
            int slot = e.getRawSlot();
            if (slot < 4) {
                ItemStack i = (ItemStack) ((MetadataValue) p.getMetadata("auctionConfirm").get(0)).value();
                int price = ((MetadataValue) p.getMetadata("auctionPrice").get(0)).asInt();
                p.removeMetadata("auctionConfirm", (Plugin) Auction.getPlugin());
                p.removeMetadata("auctionPrice", (Plugin) Auction.getPlugin());
                double fee = Controller.getSellFee(p, price);
                if (fee > 0.0 && !Auction.economy.has((OfflinePlayer) p, fee)) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "You can no longer afford the fee of " + CC.RedB + "$" + CC.Red + NumberUtils.format((double) fee, (DecimalFormatType) DecimalFormatType.MONEY));
                    this.closePlayerInventoryOnClick(p);
                    return;
                }
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && !ItemID.hasItemID((ItemStack) i)) {
                    boolean needsItemId = ItemID.needsItemID((ItemStack) i);
                    if (needsItemId && i.getAmount() == 1) {
                        ItemID.setItemID((ItemStack) i, (long) ItemID.generateNewItemID());
                    } else if (!needsItemId) {
                        ItemID.setItemID((ItemStack) i, (long) ItemID.generateNewItemID());
                    }
                }
                Controller.sell(p, i, price, fee, false);
                this.closePlayerInventoryOnClick(p);
            } else {
                if (slot > 4) {
                    ItemStack i = (ItemStack) ((MetadataValue) p.getMetadata("auctionConfirm").get(0)).value();
                    p.removeMetadata("auctionConfirm", (Plugin) Auction.getPlugin());
                    p.removeMetadata("auctionPrice", (Plugin) Auction.getPlugin());
                    if (p.getInventory().firstEmpty() != -1) {
                        p.getInventory().addItem(new ItemStack[]{i});
                    } else {
                        p.getWorld().dropItem(p.getLocation(), i);
                    }
                    p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object) ChatColor.RED + "You have cancelled your auction.");
                    p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0f, 1.2f);
                    this.closePlayerInventoryOnClick(p);
                    return;
                }
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (e.getPlayer().hasMetadata("auctionConfirm")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().hasMetadata("auctionConfirm")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().equals("Confirm Auction") && e.getPlayer().hasMetadata("auctionConfirm")) {
            final Player p = (Player) e.getPlayer();
            ItemStack i = (ItemStack) ((MetadataValue) p.getMetadata("auctionConfirm").get(0)).value();
            p.removeMetadata("auctionConfirm", (Plugin) Auction.getPlugin());
            p.removeMetadata("auctionPrice", (Plugin) Auction.getPlugin());
            if (!p.isDead() && p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(new ItemStack[]{i});
            } else {
                p.getWorld().dropItem(p.getLocation(), i);
            }
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object) ChatColor.RED + "You have cancelled your auction.");
            p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0f, 1.2f);
            Bukkit.getScheduler().runTaskLater((Plugin) Auction.getPlugin(), new Runnable() {

                @Override
                public void run() {
                    p.updateInventory();
                }
            }, 1L);
        }
    }

    private void closePlayerInventoryOnClick(final Player p) {
        Bukkit.getScheduler().runTaskLater((Plugin) Auction.getPlugin(), new Runnable() {

            @Override
            public void run() {
                p.closeInventory();
            }
        }, 1L);
    }
}

