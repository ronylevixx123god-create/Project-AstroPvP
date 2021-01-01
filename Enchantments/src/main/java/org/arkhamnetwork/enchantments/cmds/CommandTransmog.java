package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.arkhamnetwork.enchantments.*;

public class CommandTransmog implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length < 1) {
            return false;
        }
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        final String p_name = args[0];
        if (Bukkit.getPlayer(p_name) != null) {
            final Player pl = Bukkit.getPlayer(p_name);
            pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "+ 1x Transmog Scroll");
            if (pl.getInventory().firstEmpty() != -1) {
                pl.getInventory().addItem(new ItemStack[] { EnchantmentBook.getTransmogScroll() });
            }
            else {
                pl.getWorld().dropItem(pl.getLocation(), EnchantmentBook.getTransmogScroll());
            }
        }
        return true;
    }
}
