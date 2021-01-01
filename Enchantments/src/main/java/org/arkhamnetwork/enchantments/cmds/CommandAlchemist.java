package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.*;

public class CommandAlchemist implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        p.openInventory(CosmicAlchemist.getExchangeInventory());
        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1.0f, 0.8f);
        return true;
    }
}
