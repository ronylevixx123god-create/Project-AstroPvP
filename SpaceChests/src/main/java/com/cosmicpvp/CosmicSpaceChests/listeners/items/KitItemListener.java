/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.permissions.ArkhamPermissions
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.cosmicpvp.CosmicSpaceChests.listeners.items;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.concurrent.TimeUnit;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class KitItemListener
implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().getType() == Material.DIAMOND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            GlobalKits gkit = KitItems.getKitFromItemstack(e.getItem());
            if (gkit == null) {
                return;
            }
            if (e.getPlayer().getWorld().getName().equals("world_duels") || e.getPlayer().getWorld().getName().equals("world_duels2")) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot redeem this item while in a duel.");
                return;
            }
            if (!CosmicGlobalKits.kitGemsEnabled) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "Kit Gem redemption is currently disabled on this planet.");
                e.getPlayer().sendMessage((Object)ChatColor.GRAY + "This is likely due to an economy exploit that admins are investigating.");
                return;
            }
            String kitPerm = "cosmickits.kit." + gkit.getKitName();
            final Player p = e.getPlayer();
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                p.sendMessage((Object)ChatColor.RED + "You cannot redeem stacked Kit vouchers.");
                return;
            }
            if (p.hasPermission(kitPerm)) {
                if (CosmicGlobalKitsAPI.canLevelUpGkit((Player)p, (GlobalKits)gkit)) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("gkit levelup " + p.getName() + " " + gkit.name()));
                    Bukkit.getLogger().info("[KitUnlock] " + p.getName() + " (" + p.getUniqueId() + ") leveled up a " + gkit.getKitName() + " kit item!");
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        ItemID.blacklistIID((long)ItemID.getItemID((ItemStack)p.getItemInHand()), (String)"kit item redeem");
                    }
                    p.setItemInHand(null);
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                    return;
                }
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You already have the max tiered " + gkit.getItemName() + (Object)ChatColor.RED + " unlocked!");
                p.sendMessage((Object)ChatColor.GRAY + "Use /gkit to redeem it!");
                return;
            }
            try {
                String localKey = "gkit_unlocked_" + gkit.name();
                if (p.hasMetadata(localKey) && ((MetadataValue)p.getMetadata(localKey).get(0)).asLong() > System.currentTimeMillis()) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "Please wait for your previously applied /gkit to fully apply!");
                    Bukkit.getLogger().info("Cancelling GKIT claim for " + p.getName() + " due to not having permission for " + gkit.name() + " but claiming this session!");
                    return;
                }
                p.setMetadata(localKey, (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30L))));
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("pex user " + p.getName() + " add " + kitPerm + " " + CosmicSpaceChestsPlugin.serverName));
                p.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + gkit.getItemName() + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + " GKIT!");
                p.sendMessage((Object)ChatColor.GRAY + "Redeem your new kit in the /gkit menu!");
                Bukkit.getLogger().info("[KitUnlock] " + p.getName() + " (" + p.getUniqueId() + ") redeemed a " + gkit.getKitName() + " kit item!");
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    ItemID.blacklistIID((long)ItemID.getItemID((ItemStack)p.getItemInHand()), (String)"kit item redeem");
                }
                p.setItemInHand(null);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, new Runnable(){

                    @Override
                    public void run() {
                        ArkhamPermissions.getInstance().getPermissionManager().reloadPlayer(p);
                    }
                }, 15L);
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(p, "GKIT_LEVELUP", new Object[]{gkit, 0}));
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
    }
}

