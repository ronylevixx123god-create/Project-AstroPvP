/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.permissions.ArkhamPermissions
 */
package com.cosmicpvp.cosmicutils.gui.listeners;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.TitleItems;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TitleItemListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().getType() == Material.PAPER && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            String title = TitleItems.getTitleFromItemstack(e.getItem());
            if (title == null) {
                return;
            }
            if (e.getPlayer().getWorld().getName().equals("world_duels") || e.getPlayer().getWorld().getName().equals("world_duels2")) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)((Object)ChatColor.RED) + "You cannot redeem this item while in a duel.");
                return;
            }
            String titlePerm = "titles.title." + title;
            Player p = e.getPlayer();
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                p.sendMessage((Object)((Object)ChatColor.RED) + "You cannot redeem stacked title vouchers.");
                return;
            }
            if (p.hasPermission(titlePerm)) {
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)((Object)ChatColor.RED) + "You already have the " + title + " title unlocked!");
                p.sendMessage((Object)((Object)ChatColor.GRAY) + "Use /title to equip it!");
                return;
            }
            try {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add " + titlePerm);
                p.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + title + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + " title!");
                p.sendMessage((Object)((Object)ChatColor.GRAY) + "Equip your new title in the /title menu!");
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    ItemID.blacklistIID((long)ItemID.getItemID((ItemStack)p.getItemInHand()), (String)"title item redeem");
                }
                p.setItemInHand(null);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                Bukkit.getScheduler().runTaskLater(CosmicUtils.get(), () -> ArkhamPermissions.getInstance().getPermissionManager().reloadPlayer(p), 15L);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
    }
}

