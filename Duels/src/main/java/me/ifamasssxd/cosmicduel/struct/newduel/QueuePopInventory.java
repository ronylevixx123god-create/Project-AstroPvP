/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryCloseEvent
 */
package me.ifamasssxd.cosmicduel.struct.newduel;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.inventory.CustomInventory;
import me.ifamasssxd.cosmicduel.inventory.CustomInventoryCallback;
import me.ifamasssxd.cosmicduel.inventory.CustomInventoryItem;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class QueuePopInventory
extends CustomInventory {
    public NewDuel duel;
    boolean p1Ready = false;
    boolean p2Ready = false;

    public QueuePopInventory(final NewDuel duel) {
        super("Accept Duel Queue", 5);
        this.duel = duel;
        this.setCallback(new CustomInventoryCallback(){

            @Override
            public void inventoryCloseEvent(CustomInventory inventory, InventoryCloseEvent event) {
                if (duel != null && duel.getCreateState() != DuelCreateState.DONE && !duel.isCancelled()) {
                    duel.sendMessage(duel.getColoredMessage("&e&l(!) &e%s closed the &e&nQUEUE&e confirm menu.", event.getPlayer().getName()), false);
                    duel.sendMessage((Object)ChatColor.GRAY + "The duel has been cancelled.", false);
                    DuelAPI.cancelDuel(duel, CancelReason.CLOSE_QUEUE_POP);
                }
            }
        });
    }

    public boolean wasAccepted() {
        return this.p1Ready && this.p2Ready;
    }

    public void prepareQueue() {
        int index = 0;
        for (Player pl : this.duel.getPlayers()) {
            this.insertItem(index, this.getReadyItem(pl, index), true);
            index += 8;
        }
        for (int i = 0; i < this.getInventorySize(); ++i) {
            if (this.getItem(i) != null && this.getItem(i).getType() != Material.AIR) continue;
            this.insertItem(i, new CustomInventoryItem(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " ")), true);
        }
    }

    public void alertOfQueue() {
        for (Player p : this.duel.players) {
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5.0f, 1.4f);
            this.openInventory(p);
        }
    }

    public CustomInventoryItem getReadyItem(Player p, int index) {
        CustomInventoryItem item = new CustomInventoryItem(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)4, (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + p.getName(), (Object)ChatColor.GRAY + "Click to accept the queue."));
        item.setCallback((CustomInventory inventory, CustomInventoryItem customItem, InventoryClickEvent event) -> {
            event.setCancelled(true);
            Player clicked = (Player)event.getWhoClicked();
            if (this.duel == null || this.duel.getDuelState() != DuelState.IN_QUEUE) {
                this.duel = null;
                CosmicDuel.logMessage("DUEL WASNT IN QUEUE OR WAS NULL!");
                return;
            }
            if (clicked.equals((Object)p)) {
                if (index == 0) {
                    this.p1Ready = true;
                } else {
                    this.p2Ready = true;
                }
                customItem.setDurability((short)5);
                if (this.wasAccepted()) {
                    this.duel.onConfirmedQueue();
                    this.duel = null;
                }
                this.updateInventoryItems();
                return;
            }
            clicked.sendMessage((Object)ChatColor.RED + "Wrong Button.");
        });
        return item;
    }
}

