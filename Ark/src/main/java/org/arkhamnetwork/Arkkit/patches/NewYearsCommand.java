/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class NewYearsCommand
extends AbstractCommand {
    public NewYearsCommand(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args[0].equals("cake")) {
            ((Player)sender).getInventory().addItem(new ItemStack[]{NewYears.getExplosiveCake()});
        } else if (args[0].equals("firework")) {
            ((Player)sender).getInventory().addItem(new ItemStack[]{NewYears.getCosmicCracker()});
        } else if (args[0].equalsIgnoreCase("spacedrink")) {
            ((Player)sender).getInventory().addItem(new ItemStack[]{Drinks.getDrink()});
        } else if (args[0].equalsIgnoreCase("splashdrink")) {
            ((Player)sender).getInventory().addItem(new ItemStack[]{Drinks.getSplashDrink()});
        }
        return true;
    }
}

