package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.menus.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.java.*;
import org.bukkit.*;

public class CommandEnchanter implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        new EnchanterGUI(p).openGUI(Enchantments.getInstance());
        p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.4f);
        return true;
    }
}
