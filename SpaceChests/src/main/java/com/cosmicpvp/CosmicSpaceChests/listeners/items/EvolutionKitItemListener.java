package com.cosmicpvp.CosmicSpaceChests.listeners.items;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.RandomFireworks;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
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

public class EvolutionKitItemListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().getType() == Material.DIAMOND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            EvolutionKits vkit = EvolutionKitItems.getKitFromItemstack(e.getItem());
            if (vkit == null) {
                return;
            }
            if (e.getPlayer().getWorld().getName().equals("world_duels") || e.getPlayer().getWorld().getName().equals("world_duels2")) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot redeem this item while in a duel.");
                return;
            }
            if (!CosmicGlobalKits.kitGemsEnabled) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "Kit Gem redemption is currently disabled on this planet.");
                e.getPlayer().sendMessage(ChatColor.GRAY + "This is likely due to an economy exploit that admins are investigating.");
                return;
            }
            String kitPerm = "cosmicevolutionkits.kit." + vkit.getName().toLowerCase();
            Player p = e.getPlayer();
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getItem().getAmount() > 1) {
                p.sendMessage(ChatColor.RED + "You cannot redeem stacked Evolution Kit vouchers.");
                return;
            }
            if (p.hasPermission(kitPerm)) {
                p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You already have the " + vkit.getKit().getItemName() + ChatColor.RED + " Evolution Kit unlocked!");
                p.sendMessage(ChatColor.RED + "Access your Evolution Kit with " + ChatColor.RED + ChatColor.UNDERLINE + "/vkit!");
                return;
            }
            try {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add " + kitPerm + " " + CosmicSpaceChestsPlugin.serverName);
                p.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + vkit.getKit().getItemName() + ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + " /VKIT!");
                p.sendMessage(ChatColor.GRAY + "Redeem your new kit in the /vkit menu!");
                Bukkit.getLogger().info("[KitUnlock] " + p.getName() + " (" + p.getUniqueId() + ") redeemed a " + vkit.getKit().getItemName() + " kit item!");
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    ItemID.blacklistIID(ItemID.getItemID(p.getItemInHand()), "vkit item redeem");
                }
                p.setItemInHand(null);
                p.updateInventory();
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                RandomFireworks.getManager().launchRandomFirework(p.getLocation());
//              TODO: bowp
                //                TitleManager.sendTitle(p, vkit.getKit().getItemName(), TitleManager.TitleColor.AQUA);
//                TitleManager.sendSubTitle(p, ChatColor.GREEN + "Evolution Kit Unlocked", TitleManager.TitleColor.GREEN);
                Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(p, "VKIT_UNLOCK", new Object[]{vkit}));
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
    }
}

