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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            ((Player) sender).getInventory().addItem(NewYears.getExplosiveCake());
        } else if (args[0].equals("firework")) {
            ((Player) sender).getInventory().addItem(NewYears.getCosmicCracker());
        } else if (args[0].equalsIgnoreCase("spacedrink")) {
            ((Player) sender).getInventory().addItem(Drinks.getDrink());
        } else if (args[0].equalsIgnoreCase("splashdrink")) {
            ((Player) sender).getInventory().addItem(Drinks.getSplashDrink());
        }
        return true;
    }
}

