// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.cmds;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.menus.CrateStorageMenu;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandCosmicCrate implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (sender instanceof Player) ? (Player) sender : null;
        if (args.length == 0) {
            if (p != null) {
                new CrateStorageMenu(p).openGUI((JavaPlugin)CosmicCrate.get());
                p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.6f);
            }
            return true;
        }
        if (args.length == 2 && args[0].equals("give") && sender.isOp()) {
            String version = args[1];
            p.getInventory().addItem(CCAPI.getCosmicCrate(version));
            return true;
        }
        if (args.length == 3 && args[0].equals("unlock") && sender.isOp()) {
            final String playerName = args[1];
            final String versionName = args[2];
            CosmicCrateData data = CosmicCrate.get().getCosmicCrateManager().getCosmicCrate(versionName);
            if (Bukkit.getPlayer(playerName) != null) {
                final Player pGive2 = Bukkit.getPlayer(playerName);
                try {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "pex user " + pGive2.getName() + " add " + CosmicCrate.get().getCosmicCrateManager().getPermissionNode(versionName) + " " + CosmicSpaceChestsPlugin.serverName);
                    pGive2.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + ChatColor.UNDERLINE + data.getDisplayName() + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " Astro Crate!");
                    pGive2.sendMessage(ChatColor.GRAY + "Redeem your new Astro Crate with /cc");
                    pGive2.playSound(pGive2.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                    Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            ArkhamPermissions.getInstance().getPermissionManager().reloadPlayer(pGive2);
                        }
                    }, 15L);
                }
                catch (Exception err2) {
                    err2.printStackTrace();
                }
            }
            else {
                Bukkit.getLogger().info("[CosmicCrate] Could not find user " + playerName + " to give " + data.getDisplayName() + " Astro Crate to!");
            }
            return true;
        }
        if (args.length >= 2 && args[0].equals("clear") && sender.isOp()) {
            final String pName = args[1];
            final String month = (args.length > 2) ? args[3] : "all";
            final String uuid = (Bukkit.getPlayer(pName) != null) ? Bukkit.getPlayer(pName).getUniqueId().toString() : null;
            if (uuid != null) {
                sender.sendMessage("Reset all claimedCosmicCrates data for " + pName + " (" + uuid + ")");
            }
            else {
                sender.sendMessage("Cannot /cc clear offline players.");
            }
            return true;
        }
        return true;
    }
}
