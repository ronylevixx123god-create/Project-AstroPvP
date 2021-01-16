/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.struct;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.inventory.CustomInventory;
import me.ifamasssxd.cosmicduel.inventory.CustomInventoryItem;
import me.ifamasssxd.cosmicduel.struct.ranked.RankedTopManager;
import me.ifamasssxd.cosmicduel.utils.ItemBuilder;
import me.ifamasssxd.cosmicduel.utils.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class DuelMenu
extends CustomInventory {
    public DuelMenu(Player player) {
        super("Duel Type", 1);
        DuelPlayer pl = DuelPlayer.getDuelPlayer(player);
        RankedTopManager manager = CosmicDuel.get().getTopManager();
        String ranking = manager.getEasyAccessMap().containsKey(player.getUniqueId()) ? "#" + manager.getEasyAccessMap().get(player.getUniqueId()).getPosition() : "N/A";
        String[] arrstring = new String[]{(Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "ELO: " + (Object)ChatColor.GREEN + (int)(pl != null ? pl.getElo() : 0.0), (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Rank: " + (Object)ChatColor.GREEN + ranking, (Object)ChatColor.GRAY + "Click to enter Ranked Duel Mode.", (Object)ChatColor.GRAY + "Fight using your custom " + (Object)ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/duel godset" + (Object)ChatColor.GRAY + "!"};
        this.insertItem(3, new CustomInventoryItem(ItemBuilder.buildItem(Material.DIAMOND_SWORD, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Ranked " + (Object)ChatColor.GRAY + "(" + (Object)ChatColor.GREEN + ChatColor.UNDERLINE.toString() + CosmicDuel.get().getQueueManager().getRankedQueue().getSearching().size() + (Object)ChatColor.GRAY + " in queue)", arrstring), (inventory, customItem, event) -> {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            if (!DuelMenu.checkCanQueue(pl, player)) {
                return;
            }
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "You have been placed in the " + (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "RANKED" + (Object)ChatColor.GREEN + " duel queue!");
            player.sendMessage(ChatColor.GREEN + "Use " + (Object)ChatColor.GREEN + ChatColor.UNDERLINE.toString() + "/duel leave" + (Object)ChatColor.GREEN + " to leave the queue.");
            player.sendMessage((Object)ChatColor.GRAY + "Searching for an opponent...");
            CosmicDuel.get().getQueueManager().getRankedQueue().addPlayerToQueue(player);
            player.closeInventory();
        }));
        this.insertItem(5, new CustomInventoryItem(ItemBuilder.buildItem(Material.DIAMOND_CHESTPLATE, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Unranked", (Object)ChatColor.GRAY + "Click to enter the Unranked Duel Queue."), (inventory, customItem, event) -> {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            player.closeInventory();
            player.performCommand("duel queue");
        }));
        this.updateInventoryItems();
    }

    public static boolean checkCanQueue(DuelPlayer pl, Player player) {
        if (pl.getCustomGodSet() == null) {
            player.sendMessage(ChatColor.RED + "You cannot enter a Ranked Duel Match until you create a custom God Set!");
            player.sendMessage(ChatColor.GRAY + "Use " + (Object)ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/duel godset" + (Object)ChatColor.GRAY + " to create your God Set.");
            return false;
        }
        if (DuelAPI.hasDuel(player)) {
            player.sendMessage((Object)ChatColor.RED + "You cannot do this while in a duel!");
            return false;
        }
        if (CosmicDuel.get().getQueueManager().isQueued(player) || CosmicDuel.get().getQueueManager().getRankedQueue().isInRankedQueue(player)) {
            player.sendMessage((Object)ChatColor.RED + "You are already in queue!");
            return false;
        }
        if (!player.hasMetadata("rank_queue_dodge")) {
            return true;
        }
        if (player.getMetadata("rank_queue_dodge").size() <= 0) {
            return true;
        }
        long time = ((MetadataValue)player.getMetadata("rank_queue_dodge").get(0)).asLong();
        if (time < System.currentTimeMillis()) {
            player.removeMetadata("rank_queue_dodge", (Plugin)CosmicDuel.get());
            return true;
        }
        player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You have recently dodged a ranked duel queue, you have been locked out of ranked matches for another " + TimeUtils.formatTime(time / 1000L) + ".");
        player.closeInventory();
        return false;
    }
}

