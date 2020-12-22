/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.drtshock.playervaults.vaultmanagement.UUIDVaultManager
 *  com.earth2me.essentials.User
 *  com.gmail.nossr50.mcMMO
 *  nl.rutgerkok.betterenderchest.BetterEnderChest
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.User;
import com.gmail.nossr50.mcMMO;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

final class CommandResetPlayer
extends AbstractCommand {
    public CommandResetPlayer(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("/resetplayer <playerName> <pv/echest/mcmmo/inventory/essentials/all>");
            return true;
        }
        final String pName = args[0];
        if (Bukkit.getPlayer((String)pName) != null) {
            sender.sendMessage((Object)ChatColor.RED + "Player " + pName + " is currently online. Kick them from the server before attempting to reset data.");
            return true;
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                String opp;
                OfflinePlayer op = Bukkit.getOfflinePlayer((String)pName);
                if (op == null) {
                    sender.sendMessage((Object)ChatColor.RED + "Player " + pName + " has no playerData stored, could not resolve UUID. (they have never logged into this planet?)");
                    return;
                }
                UUID playerUUID = op.getUniqueId();
                if (CommandResetPlayer.this.resetPlayer(pName, playerUUID, opp = args[1])) {
                    sender.sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "<*> " + (Object)ChatColor.GREEN + pName + "'s (" + playerUUID.toString() + ") '" + opp + "' has been reset.");
                } else {
                    sender.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "<*> " + (Object)ChatColor.RED + " Failed to reset " + pName + "'s (" + playerUUID.toString() + ") '" + opp + "'... do they exist?");
                }
            }
        });
        return true;
    }

    private boolean resetPlayer(String playerName, UUID playerUUID, String opp) {
        if (opp.equalsIgnoreCase("inventory")) {
            new File("world/playerdata/" + playerUUID.toString() + ".dat").delete();
            return true;
        }
        if (opp.equalsIgnoreCase("essentials")) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("eco set " + playerName + " 0"));
            User u = EssentialsUtils.getEssentials().getUser(playerUUID);
            if (u == null) {
                Bukkit.getLogger().info("[Arkkit (Ypperin)] No essentials playerData for " + playerName + " (" + playerUUID + ")!");
                return true;
            }
            for (String home : u.getHomes()) {
                try {
                    u.delHome(home);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            u.reset();
            return true;
        }
        if (opp.equalsIgnoreCase("pv")) {
            UUIDVaultManager.getInstance().getCachedVaults().clearVaultCache(playerUUID);
            UUIDVaultManager.getInstance().getCachedVaults().flushVaultCacheToFile(playerUUID);
            new File("plugins/PlayerVaults/uuidvaults/" + playerUUID + ".yml").delete();
            return true;
        }
        if (opp.equalsIgnoreCase("mcmmo")) {
            mcMMO.getDatabaseManager().removeUser(playerName);
            mcMMO.getDatabaseManager().loadPlayerProfile(playerUUID);
            return true;
        }
        if (opp.equalsIgnoreCase("all")) {
            return this.resetPlayer(playerName, playerUUID, "inventory") && this.resetPlayer(playerName, playerUUID, "echest") && this.resetPlayer(playerName, playerUUID, "pv") && this.resetPlayer(playerName, playerUUID, "essentials") && this.resetPlayer(playerName, playerUUID, "mcmmo");
        }
        return false;
    }
}

