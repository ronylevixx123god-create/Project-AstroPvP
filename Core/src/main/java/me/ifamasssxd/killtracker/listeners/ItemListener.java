/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.killtracker.listeners;

import me.ifamasssxd.killtracker.listeners.Tracker;
import me.ifamasssxd.killtracker.utils.ItemUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getCursor() != null && e.getCurrentItem().getType() != Material.AIR && ItemUtils.isTrackable(e.getCurrentItem()) && this.isKillTracker(e.getCursor())) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            Player p = (Player)e.getWhoClicked();
            ItemStack item = e.getCurrentItem();
            ItemStack cursor = e.getCursor();
            Tracker trying_to_add = this.getKillTrackerType(cursor);
            Tracker current = Tracker.getTrackerType(e.getCurrentItem());
            if (trying_to_add == null) {
                System.out.println("Null add item.");
                return;
            }
            if (current != null) {
                if (trying_to_add.equals((Object)current)) {
                    p.setItemOnCursor(null);
                    if (p.getInventory().firstEmpty() == -1) {
                        p.getWorld().dropItem(p.getLocation(), cursor);
                    } else {
                        p.getInventory().addItem(new ItemStack[]{cursor});
                    }
                    p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You " + (Object)ChatColor.RED + ChatColor.UNDERLINE.toString() + "cannot" + (Object)ChatColor.RED + " add the same type of tracker to this item.");
                    p.updateInventory();
                    p.closeInventory();
                    return;
                }
                if (trying_to_add.getRank() < current.getRank() || trying_to_add.trackMobKills() != current.trackMobKills()) {
                    p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot overwrite a higher tier tracker.");
                    return;
                }
                ItemUtils.removeTracker(item, current);
            }
            if (cursor.getAmount() == 1) {
                p.setItemOnCursor(null);
            } else {
                cursor.setAmount(cursor.getAmount() - 1);
                p.setItemOnCursor(cursor);
            }
            ItemUtils.setTrackedKills(item, trying_to_add, 0);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.3f);
            p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You have applied a " + trying_to_add.getName() + " Soul Tracker" + (Object)ChatColor.YELLOW + " to your " + (item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords((String)item.getType().name().toLowerCase().replace("_", " "))) + "!");
            p.updateInventory();
        }
    }

    public boolean isKillTracker(ItemStack is) {
        return is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore() && is.getItemMeta().getDisplayName().contains("Soul Tracker") && is.getItemMeta().getLore().size() > 0 && ((String)is.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "Apply to weapons to start tracking");
    }

    public Tracker getKillTrackerType(ItemStack is) {
        String name = is.getItemMeta().getDisplayName();
        for (Tracker t : Tracker.values()) {
            if (!name.startsWith(t.getName())) continue;
            return t;
        }
        return null;
    }
}

