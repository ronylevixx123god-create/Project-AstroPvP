/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.drtshock.playervaults.vaultmanagement.VaultOperations
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit;

import com.drtshock.playervaults.vaultmanagement.VaultOperations;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

class CommandVaultName
extends AbstractCommand {
    private PlayerVaultGUIManager guiManager;

    public CommandVaultName(PlayerVaultGUIManager manager) {
        super("namevault", "/<command> <vaultNumber> <name>", "Rename your vaults", Lists.newArrayList("vaultname", "renamevault"));
        this.guiManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid Usage: /namevault <vaultNumber> <name>");
            return true;
        }
        if (StringUtils.isNumeric((String)args[0])) {
            int vaultId = Integer.parseInt(args[0]);
            if (vaultId < 1 || vaultId > 100) {
                sender.sendMessage((Object)ChatColor.RED + "Invalid Vault ID.");
                return true;
            }
            Player player = (Player)sender;
            if (!VaultOperations.checkPerms(sender, (int)vaultId)) {
                sender.sendMessage((Object)ChatColor.RED + "You do not have access to that Vault number!");
                return true;
            }
            CustomPlayerVaults vaults = (CustomPlayerVaults) this.guiManager.getCustomPvs().computeIfAbsent(player.getUniqueId(), e -> new CustomPlayerVaults());
            CustomPV pv = vaults.computeIfAbsent(vaultId, newPV -> new CustomPV());
            StringBuilder newNameBuilder = new StringBuilder();
            for (int i = 1; i < args.length; ++i) {
                newNameBuilder.append(args[i]);
            }
            String done = newNameBuilder.toString().trim();
            if (done.length() >= 32) {
                player.sendMessage((Object)ChatColor.RED + "Vault names cannot be longer than 32 characters.");
                return true;
            }
            pv.setCustomName(done);
            player.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Vault #" + vaultId + " renamed to " + done);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Arkkit.get(), () -> player.chat("/pv"));
            return true;
        }
        sender.sendMessage((Object)ChatColor.RED + "Invalid Usage: /namevault <vaultNumber> <name>");
        return false;
    }
}

