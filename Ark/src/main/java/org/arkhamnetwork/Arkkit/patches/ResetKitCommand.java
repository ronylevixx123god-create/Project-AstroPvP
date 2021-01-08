/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.arkhamnetwork.Arkkit.libs.ArkPluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class ResetKitCommand
        extends ArkPluginCommand<ResetKit> {
    public ResetKitCommand(ResetKit patch, String command) {
        super(patch, command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1) {
            Player pl = Bukkit.getPlayer(args[0]);
            if (pl == null) {
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Unable to find player with that name!");
                return true;
            }
            PlayerUtil.giveOrDropProtectedItem(pl, this.patch.createResetKit(true), -1, null, CC.RedB + "INVENTORY FULL " + CC.Red + "Reset Kit dropped on ground");
            sender.sendMessage(CC.Red + "Added reset kit to " + pl.getName() + "'s Inventory!");
            return true;
        }
        sender.sendMessage(CC.Red + "Invalid Usage: /resetkit <player>");
        return false;
    }
}

