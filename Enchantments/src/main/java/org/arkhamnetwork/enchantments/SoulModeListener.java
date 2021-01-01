package org.arkhamnetwork.enchantments;

import org.bukkit.event.block.*;
import me.ifamasssxd.killtracker.*;
import org.arkhamnetwork.enchantments.cmds.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

public class SoulModeListener implements Listener {
    @EventHandler
    public void onPlayerToggleSoulMode(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && KillTrackerAPI.isSoulGem(e.getItem()) && CommandEnchDust.soulEnabled) {
            Player p = e.getPlayer();
            if (KillTrackerAPI.hasSoulAmount(p, 1)) {
                if (!KillTrackerAPI.hasGodMode(p) && p.hasMetadata("soulTrap") && ((MetadataValue)p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis()) {
                    p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " enable soul mode while in a Soul Trap!");
                    return;
                }
                KillTrackerAPI.toggleGodMode(p);
                if (KillTrackerAPI.hasGodMode(p)) {
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "** SOUL MODE: " + ChatColor.UNDERLINE + "ON" + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " **");
                    p.sendMessage(ChatColor.GRAY + "Active soul enchantments will now drain soul gems.");
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.2F);
                } else {
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** SOUL MODE: " + ChatColor.UNDERLINE + "OFF" + ChatColor.RED.toString() + ChatColor.BOLD.toString() + " **");
                    p.sendMessage(ChatColor.GRAY + "Soul enchantments will no longer drain soul gems.");
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.2F);
                }
            } else {
                p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " enable soul mode without any soul gems!");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        e.getPlayer().setMetadata("lastItemHeldChange", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick())));
    }
}
