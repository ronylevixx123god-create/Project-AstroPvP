/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.EULA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

final class EULAListener
implements Listener {
    Inventory eulaInventory;

    EULAListener() {
    }

    @EventHandler
    public void onPlayerRightClickEULA(PlayerInteractEntityEvent e) {
        Player pEntity;
        if (e.getRightClicked().hasMetadata("NPC") && e.getRightClicked() instanceof Player && (pEntity = (Player)e.getRightClicked()).getName().equals(EULA.npcName)) {
            Player p = e.getPlayer();
            p.openInventory(this.buildEULAInventory());
            p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1.0f, 0.8f);
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "We were forced to add this NPC by Mojang to meet their EULA requirments.");
            p.sendMessage(ChatColor.GRAY.toString() + "Honestly, we recommend you purchase rank items off of /ah or obtain them from envoy events, as they're much cheaper and more readily available through those methods.");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(EULA.npcName)) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (!e.getInventory().equals((Object)e.getWhoClicked().getOpenInventory().getTopInventory())) {
                return;
            }
            if (e.getRawSlot() >= 2 && e.getRawSlot() <= 6) {
                long price;
                Player p = (Player)e.getWhoClicked();
                if (!Arkkit.economy.has((OfflinePlayer)p, (double)(price = (long)(e.getRawSlot() - 1) * 1000000000L))) {
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You do not have enough $ to purchase this rank.");
                    p.sendMessage((Object)ChatColor.GRAY + "Think these prices are outrageous? So do we, but the implementation of this NPC was required of us by Mojang.");
                } else {
                    Arkkit.economy.withdrawPlayer((OfflinePlayer)p, (double)price);
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "- $" + price);
                    p.getInventory().addItem(new ItemStack[]{this.removePrice(e.getCurrentItem().clone())});
                }
            }
        }
    }

    private Inventory buildEULAInventory() {
        if (this.eulaInventory != null) {
            return this.eulaInventory;
        }
        Inventory i = Bukkit.createInventory(null, (int)9, (String)EULA.npcName);
        i.setItem(2, this.setPrice(ItemUtils.getRankItem((ItemRarity)ItemRarity.SIMPLE), 1000000000L));
        i.setItem(3, this.setPrice(ItemUtils.getRankItem((ItemRarity)ItemRarity.UNIQUE), 2000000000L));
        i.setItem(4, this.setPrice(ItemUtils.getRankItem((ItemRarity)ItemRarity.ELITE), 3000000000L));
        i.setItem(5, this.setPrice(ItemUtils.getRankItem((ItemRarity)ItemRarity.ULTIMATE), 4000000000L));
        i.setItem(6, this.setPrice(ItemUtils.getRankItem((ItemRarity)ItemRarity.LEGENDARY), 5000000000L));
        this.eulaInventory = i;
        return this.eulaInventory;
    }

    private ItemStack setPrice(ItemStack i, long price) {
        ArrayList old_lore = new ArrayList();
        ItemMeta im = i.getItemMeta();
        List<String> lore = i.hasItemMeta() ? i.getItemMeta().getLore() : new ArrayList();
        lore.add("");
        lore.add(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Price: " + ChatColor.WHITE.toString() + "$" + price);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    private ItemStack removePrice(ItemStack i) {
        ItemMeta im = i.getItemMeta();
        im.setLore(im.getLore().subList(0, im.getLore().size() - 2));
        i.setItemMeta(im);
        return i;
    }
}

