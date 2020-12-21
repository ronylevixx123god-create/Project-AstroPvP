/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.trading;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.patches.trading.NewTrade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class TradeSyncTask
extends BukkitRunnable {
    Player p1;
    Player p2;
    long lastTick;

    public TradeSyncTask(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void run() {
        if (!this.p1.hasMetadata("compilexTradePartnerUUID") || !this.p2.hasMetadata("compilexTradePartnerUUID")) {
            return;
        }
        try {
            int slot;
            if (!((UUID)((MetadataValue)this.p1.getMetadata("compilexTradePartnerUUID").get(0)).value()).equals(this.p2.getUniqueId())) {
                return;
            }
            if (!((UUID)((MetadataValue)this.p2.getMetadata("compilexTradePartnerUUID").get(0)).value()).equals(this.p1.getUniqueId())) {
                return;
            }
            Inventory inv1 = this.p1.getOpenInventory().getTopInventory();
            Inventory inv2 = this.p2.getOpenInventory().getTopInventory();
            if (inv2.getItem(slot = 0) != null) {
                inv1.setItem(NewTrade.getLinkedSlot(slot).intValue(), inv2.getItem(slot));
            } else {
                inv1.setItem(NewTrade.getLinkedSlot(slot).intValue(), new ItemStack(Material.AIR));
            }
            if (inv1.getItem(slot) != null) {
                inv2.setItem(NewTrade.getLinkedSlot(slot).intValue(), inv1.getItem(slot));
            } else {
                inv2.setItem(NewTrade.getLinkedSlot(slot).intValue(), new ItemStack(Material.AIR));
            }
            if (!(inv1.getItem(0) != null && inv1.getItem(0).getDurability() != NewTrade.green_button.getDurability() || inv2.getItem(0) != null && inv2.getItem(0).getDurability() != NewTrade.green_button.getDurability())) {
                if (System.currentTimeMillis() - this.lastTick > 600L) {
                    this.lastTick = System.currentTimeMillis();
                    if (inv1.getItem(0) != null && inv1.getItem(0).getAmount() > 0 || inv2.getItem(0) != null && inv2.getItem(0).getAmount() > 0) {
                        if (inv1.getItem(0) != null) {
                            inv1.setItem(0, this.setAmount(inv1.getItem(0), inv1.getItem(0).getAmount() - 1));
                        }
                        if (inv1.getItem(8) == null) {
                            inv1.setItem(8, this.setAmount(NewTrade.green_button.clone(), 0));
                        }
                        if (inv1.getItem(8) != null && inv1.getItem(8).getDurability() == NewTrade.green_button.getDurability()) {
                            inv1.setItem(8, this.setAmount(inv1.getItem(8), inv1.getItem(8).getAmount() - 1));
                        }
                        if (inv2.getItem(0) != null) {
                            inv2.setItem(0, this.setAmount(inv2.getItem(0), inv2.getItem(0).getAmount() - 1));
                        }
                        if (inv2.getItem(8) == null) {
                            inv2.setItem(8, this.setAmount(NewTrade.green_button.clone(), 0));
                        }
                        if (inv2.getItem(8) != null && inv2.getItem(8).getDurability() == NewTrade.green_button.getDurability()) {
                            inv2.setItem(8, this.setAmount(inv2.getItem(8), inv2.getItem(8).getAmount() - 1));
                        }
                    }
                    if (!(inv1.getItem(0) != null && inv1.getItem(0).getAmount() > 0 || inv2.getItem(0) != null && inv2.getItem(0).getAmount() > 0)) {
                        if (!this.hasRoom(this.p1) || !this.hasRoom(this.p2)) {
                            inv1.setItem(0, NewTrade.gray_button);
                            inv2.setItem(0, NewTrade.gray_button);
                            this.p1.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You or your trading partner don't have enough inventory space to complete this trade.");
                            this.p2.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You or your trading partner don't have enough inventory space to complete this trade.");
                            return;
                        }
                        this.acceptTrade();
                    }
                }
            } else {
                ItemStack inv1Item = inv1.getItem(0);
                ItemStack inv2Item = inv2.getItem(0);
                if (inv1Item == null && inv2Item == null) {
                    Bukkit.getLogger().info("[NewTrade] Both panes null inside inventory: " + inv1.getTitle());
                    return;
                }
                if (inv1Item == null || inv2Item == null || inv1Item.getAmount() != NewTrade.green_button.getAmount() || inv2Item.getAmount() != NewTrade.green_button.getAmount()) {
                    inv1.setItem(0, NewTrade.gray_button);
                    inv2.setItem(0, NewTrade.gray_button);
                    Bukkit.getLogger().info("[NewTrade] Panes did not match green button amount trade partners: " + inv1.getTitle() + " inv1: " + (Object)inv1Item + " inv2: " + (Object)inv2Item + " p1: " + this.p1.getName() + ", resetting to unconfirmed..");
                }
            }
        }
        catch (Exception err) {
            Bukkit.getLogger().info("[Arkkit (TradeSyncTask)] Handled exception, force ending trade between " + this.p1.getName() + " (" + this.p1.getUniqueId() + ") and " + this.p2.getName() + " (" + this.p2.getUniqueId() + ")!");
            err.printStackTrace();
            this.p1.closeInventory();
            this.p2.closeInventory();
        }
    }

    private void acceptTrade() {
        Inventory inv1 = this.p1.getOpenInventory().getTopInventory();
        Inventory inv2 = this.p2.getOpenInventory().getTopInventory();
        ArrayList traded = Lists.newArrayList();
        ArrayList traded2 = Lists.newArrayList();
        for (int slot : NewTrade.slotConversionMap.values()) {
            if (inv1.getItem(slot) != null && inv1.getItem(slot).getType() != Material.STAINED_GLASS_PANE) {
                this.p1.getInventory().addItem(new ItemStack[]{inv1.getItem(slot)});
                traded.add(inv1.getItem(slot));
            }
            if (inv2.getItem(slot) == null || inv2.getItem(slot).getType() == Material.STAINED_GLASS_PANE) continue;
            this.p2.getInventory().addItem(new ItemStack[]{inv2.getItem(slot)});
            traded.add(inv2.getItem(slot));
        }
        this.p1.sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "Trade accepted.");
        this.p1.playSound(this.p1.getLocation(), Sound.BLAZE_HIT, 1.0f, 1.5f);
        this.p2.sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "Trade accepted.");
        this.p2.playSound(this.p2.getLocation(), Sound.BLAZE_HIT, 1.0f, 1.5f);
        this.p1.closeInventory();
        this.p2.closeInventory();
        Bukkit.getLogger().info("[NewTrade] Giving p1 " + this.p1.getName() + " Items: " + Arrays.toString((Object[])traded.toArray((T[])new ItemStack[traded.size()])) + " from " + this.p2.getName());
        Bukkit.getLogger().info("[NewTrade] Giving p2 " + this.p2.getName() + " Items: " + Arrays.toString((Object[])traded2.toArray((T[])new ItemStack[traded2.size()])) + " from " + this.p1.getName());
    }

    private int getTradeItemCount(Player p) {
        Inventory inv1 = p.getOpenInventory().getTopInventory();
        int c = 0;
        for (int slot : NewTrade.slotConversionMap.values()) {
            if (inv1.getItem(slot) == null || inv1.getItem(slot).getType() == Material.STAINED_GLASS_PANE) continue;
            ++c;
        }
        return c;
    }

    private ItemStack setAmount(ItemStack is, int x) {
        ItemStack i = is.clone();
        i.setAmount(x);
        return i;
    }

    private boolean hasRoom(Player p) {
        int openSlots = 0;
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType() != Material.AIR) continue;
            ++openSlots;
        }
        return openSlots >= this.getTradeItemCount(p);
    }
}

