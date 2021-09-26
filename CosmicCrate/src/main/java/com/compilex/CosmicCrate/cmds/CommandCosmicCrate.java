// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.cmds;

import org.bukkit.plugin.Plugin;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.ChatColor;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.bukkit.Bukkit;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.menus.CrateStorageMenu;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandCosmicCrate implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (sender instanceof Player) ? sender : null;
        if (args.length == 0) {
            if (p != null) {
                new CrateStorageMenu(p).openGUI((JavaPlugin)CosmicCrate.get());
                p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.6f);
            }
            return true;
        }
        if (args.length == 2 && args[0].equals("give") && sender.isOp()) {
            final String version = args[1];
            p.getInventory().addItem(new ItemStack[] { CCAPI.getCosmicCrate(Version.valueOf(version.toUpperCase()), p.getName()) });
            return true;
        }
        if (args.length == 2 && args[0].equals("random") && sender.isOp()) {
            final String pName = args[1];
            final Player player = Bukkit.getPlayer(pName);
            if (player != null) {
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(new ItemStack[] { CCAPI.getMysteryCrate() });
                }
                else {
                    player.getWorld().dropItem(player.getLocation(), CCAPI.getMysteryCrate());
                }
            }
            else {
                Bukkit.getLogger().info("[CosmicCrate] /cc random: Player " + pName + " could not be found.");
            }
            return true;
        }
        if (args.length == 2 && args[0].equals("hrandom") && sender.isOp()) {
            final String pName = args[1];
            final Player player = Bukkit.getPlayer(pName);
            if (player != null) {
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(new ItemStack[] { CCAPI.getHeroicMysteryCrate() });
                }
                else {
                    player.getWorld().dropItem(player.getLocation(), CCAPI.getHeroicMysteryCrate());
                }
            }
            else {
                Bukkit.getLogger().info("[CosmicCrate] /cc hrandom: Player " + pName + " could not be found.");
            }
            return true;
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("godly") || args[0].equalsIgnoreCase("supreme") || args[0].equalsIgnoreCase("superior")) && sender.isOp()) {
            final Player player2 = Bukkit.getPlayer(args[1]);
            if (player2 != null) {
                PlayerUtil.giveOrDropProtectedItem(player2, CCAPI.getGodlyCosmicCrate(), -1);
                sender.sendMessage(CC.Red + "Godly Cosmic Crate added to " + player2.getName() + "'s Inventory!");
                return true;
            }
        }
        if (args.length == 2 && args[0].equals("unlock") && sender.isOp()) {
            final String playerName = args[1];
            if (Bukkit.getPlayer(playerName) != null) {
                final Player pGive = Bukkit.getPlayer(playerName);
                try {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "pex user " + pGive.getName() + " add " + Version.HOLIDAY_2017.getPermissionNode() + " " + CosmicSpaceChestsPlugin.serverName);
                    pGive.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + ChatColor.UNDERLINE + Version.HOLIDAY_2017.name() + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " Cosmic Crate!");
                    pGive.sendMessage(ChatColor.GRAY + "Redeem your new Cosmic Crate with /cc");
                    pGive.playSound(pGive.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                    Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            ArkhamPermissions.getInstance().getPermissionManager().reloadPlayer(pGive);
                        }
                    }, 15L);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
            else {
                Bukkit.getLogger().info("[CosmicCrate] Could not find user " + playerName + " to give Cosmic Crate to!");
            }
            return true;
        }
        if (args.length == 3 && args[0].equals("unlock") && sender.isOp()) {
            final String playerName = args[1];
            final String versionName = args[2];
            final Version v = Version.valueOf(versionName.toUpperCase());
            if (Bukkit.getPlayer(playerName) != null) {
                final Player pGive2 = Bukkit.getPlayer(playerName);
                try {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "pex user " + pGive2.getName() + " add " + v.getPermissionNode() + " " + CosmicSpaceChestsPlugin.serverName);
                    pGive2.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + ChatColor.UNDERLINE + v.name() + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " Cosmic Crate!");
                    pGive2.sendMessage(ChatColor.GRAY + "Redeem your new Cosmic Crate with /cc");
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
                Bukkit.getLogger().info("[CosmicCrate] Could not find user " + playerName + " to give " + v.name() + " Cosmic Crate to!");
            }
            return true;
        }
        if (args.length >= 2 && args[0].equals("clear") && sender.isOp()) {
            final String pName = args[1];
            final String month = (args.length > 2) ? args[3] : "all";
            final String uuid = (Bukkit.getPlayer(pName) != null) ? Bukkit.getPlayer(pName).getUniqueId().toString() : null;
            if (uuid != null) {
                CosmicCrate.claimedCosmicCrates.remove(uuid);
                CosmicCrate.sql_query.add("DELETE FROM ccrates WHERE username='" + pName + "'");
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
