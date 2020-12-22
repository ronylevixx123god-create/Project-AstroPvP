/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.google.common.collect.Lists
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class CommandSeasonalPass
extends AbstractCommand {
    public CommandSeasonalPass() {
        super("seasonalpass", "/<command>", "Seasonal Pass", Lists.newArrayList("seasonpass"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length > 1 && args[0].equalsIgnoreCase("give")) {
            Player pl = PlayerUtil.getPlayerFromString((String)args[1]);
            if (pl == null) {
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Unable to find player with name '" + args[1] + "'");
                return true;
            }
            sender.sendMessage(CC.Green + "Giving season pass to " + pl.getName());
            PlayerUtil.giveOrDropProtectedItem((Player)pl, (ItemStack)SeasonalPasses.createSeasonPass(), (int)-1, null, (String)(CC.Red + "Season pass placed on the floor due to full inventory!"));
            return true;
        }
        sender.sendMessage(CC.Red + "Usage: /seasonalpass give <player>");
        return false;
    }
}

