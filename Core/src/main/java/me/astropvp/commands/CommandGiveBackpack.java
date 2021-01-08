/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.objects.BackpackType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveBackpack
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        if (!player.isOp()) {
            return true;
        }
        if (args.length != 2) {
            player.sendMessage(CC.Red + "Correct Usage: /givebackpack <type> <player>");
            return true;
        }
        Player target = Bukkit.getPlayer((String)args[1]);
        BackpackType type = BackpackType.valueOf(args[0]);
        target.getInventory().addItem(type.getInstance().getItem());
        target.sendMessage(CC.Green + "Given " + type.getInstance().getItem().getItemMeta().getDisplayName() + " to " + target.getDisplayName());
        return false;
    }
}

