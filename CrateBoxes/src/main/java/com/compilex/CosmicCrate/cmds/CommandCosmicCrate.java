/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  org.arkhamnetwork.permissions.ArkhamPermissions
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicCrate.cmds;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.menus.CrateStorageMenu;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CommandCosmicCrate
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String playerName;
        Player player;
        Player p;
        Player player2 = p = sender instanceof Player ? (Player)sender : null;
        if (args.length == 0) {
            if (p != null) {
                new CrateStorageMenu(p).openGUI(CosmicCrate.get());
                p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.6f);
            }
            return true;
        }
        if (args.length == 2 && args[0].equals("give") && sender.isOp()) {
            String version = args[1];
            p.getInventory().addItem(new ItemStack[]{CCAPI.getCosmicCrate(Version.valueOf(version.toUpperCase()), p.getName())});
            return true;
        }
        if (args.length == 2 && args[0].equals("random") && sender.isOp()) {
            String pName = args[1];
            Player player3 = Bukkit.getPlayer((String)pName);
            if (player3 != null) {
                if (player3.getInventory().firstEmpty() != -1) {
                    player3.getInventory().addItem(new ItemStack[]{CCAPI.getMysteryCrate()});
                } else {
                    player3.getWorld().dropItem(player3.getLocation(), CCAPI.getMysteryCrate());
                }
            } else {
                Bukkit.getLogger().info("[CosmicCrate] /cc random: Player " + pName + " could not be found.");
            }
            return true;
        }
        if (args.length == 2 && args[0].equals("hrandom") && sender.isOp()) {
            String pName = args[1];
            Player player4 = Bukkit.getPlayer((String)pName);
            if (player4 != null) {
                if (player4.getInventory().firstEmpty() != -1) {
                    player4.getInventory().addItem(new ItemStack[]{CCAPI.getHeroicMysteryCrate()});
                } else {
                    player4.getWorld().dropItem(player4.getLocation(), CCAPI.getHeroicMysteryCrate());
                }
            } else {
                Bukkit.getLogger().info("[CosmicCrate] /cc hrandom: Player " + pName + " could not be found.");
            }
            return true;
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("godly") || args[0].equalsIgnoreCase("supreme") || args[0].equalsIgnoreCase("superior")) && sender.isOp() && (player = Bukkit.getPlayer((String)args[1])) != null) {
            PlayerUtil.giveOrDropProtectedItem((Player)player, (ItemStack)CCAPI.getGodlyCosmicCrate(), (int)-1);
            sender.sendMessage(CC.Red + "Godly Cosmic Crate added to " + player.getName() + "'s Inventory!");
            return true;
        }
        if (args.length == 2 && args[0].equals("unlock") && sender.isOp()) {
            playerName = args[1];
            if (Bukkit.getPlayer((String)playerName) != null) {
                final Player pGive = Bukkit.getPlayer((String)playerName);
                try {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("pex user " + pGive.getName() + " add " + Version.HOLIDAY_2017.getPermissionNode() + " " + CosmicSpaceChestsPlugin.serverName));
                    pGive.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + (Object)ChatColor.UNDERLINE + Version.HOLIDAY_2017.name() + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " Cosmic Crate!");
                    pGive.sendMessage((Object)ChatColor.GRAY + "Redeem your new Cosmic Crate with /cc");
                    pGive.playSound(pGive.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                    Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, new Runnable(){

                        @Override
                        public void run() {
                            ArkhamPermissions.getInstance().getPermissionManager().reloadPlayer(pGive);
                        }
                    }, 15L);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                Bukkit.getLogger().info("[CosmicCrate] Could not find user " + playerName + " to give Cosmic Crate to!");
            }
            return true;
        }
        if (args.length == 3 && args[0].equals("unlock") && sender.isOp()) {
            playerName = args[1];
            String versionName = args[2];
            Version v = Version.valueOf(versionName.toUpperCase());
            if (Bukkit.getPlayer((String)playerName) != null) {
                final Player pGive = Bukkit.getPlayer((String)playerName);
                try {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("pex user " + pGive.getName() + " add " + v.getPermissionNode() + " " + CosmicSpaceChestsPlugin.serverName));
                    pGive.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) You've UNLOCKED the " + (Object)ChatColor.UNDERLINE + v.name() + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " Cosmic Crate!");
                    pGive.sendMessage((Object)ChatColor.GRAY + "Redeem your new Cosmic Crate with /cc");
                    pGive.playSound(pGive.getLocation(), Sound.LEVEL_UP, 1.0f, 0.72f);
                    Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, new Runnable(){

                        @Override
                        public void run() {
                            ArkhamPermissions.getInstance().getPermissionManager().reloadPlayer(pGive);
                        }
                    }, 15L);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                Bukkit.getLogger().info("[CosmicCrate] Could not find user " + playerName + " to give " + v.name() + " Cosmic Crate to!");
            }
            return true;
        }
        if (args.length >= 2 && args[0].equals("clear") && sender.isOp()) {
            String uuid;
            String pName = args[1];
            String month = args.length > 2 ? args[3] : "all";
            String string = uuid = Bukkit.getPlayer((String)pName) != null ? Bukkit.getPlayer((String)pName).getUniqueId().toString() : null;
            if (uuid != null) {
                CosmicCrate.claimedCosmicCrates.remove(uuid);
                CosmicCrate.sql_query.add("DELETE FROM ccrates WHERE username='" + pName + "'");
                sender.sendMessage("Reset all claimedCosmicCrates data for " + pName + " (" + uuid + ")");
            } else {
                sender.sendMessage("Cannot /cc clear offline players.");
            }
            return true;
        }
        return true;
    }
}

