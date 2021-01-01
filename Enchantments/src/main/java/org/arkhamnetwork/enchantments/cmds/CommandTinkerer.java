package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.listeners.*;
import org.bukkit.*;

public class CommandTinkerer implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        p.openInventory(TinkererListener.buildTinkererWindow(p));
        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1.0f, 0.8f);
        p.sendMessage(ChatColor.YELLOW + "Trading with " + ChatColor.BOLD + "Astro Tinkerer" + ChatColor.YELLOW + "...");
        return true;
    }
}
