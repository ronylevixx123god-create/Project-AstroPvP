/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.itemfilter.commands;

import me.ifamasssxd.itemfilter.Filter;
import me.ifamasssxd.itemfilter.FilterManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFilter
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        if (args.length == 1) {
            FilterManager manager = ItemFilter.get().getFilterManager();
            if (args[0].equalsIgnoreCase("toggle")) {
                Filter filter;
                Filter filter2 = filter = manager.getItemFilters().containsKey(player.getUniqueId()) ? manager.getItemFilters().get(player.getUniqueId()) : new Filter();
                if (!manager.getItemFilters().containsKey(player.getUniqueId())) {
                    manager.getItemFilters().put(player.getUniqueId(), filter);
                }
                filter.setEnabled(!filter.isEnabled());
                player.sendMessage((Object)(filter.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + "(!) Item Filter " + (filter.isEnabled() ? (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "ENABLED" : (Object)ChatColor.RED + ChatColor.BOLD.toString() + "DISABLED"));
                if (!filter.isEnabled()) {
                    ItemFilter.get().getItemListener().alerted.remove(player.getUniqueId());
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("edit")) {
                player.openInventory(ItemFilter.get().getFilterManager().getCategoryMenu(player));
                return true;
            }
        }
        player.sendMessage("");
        player.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Item Filter Commands");
        player.sendMessage((Object)ChatColor.WHITE + "/filter toggle (alias /ft)");
        player.sendMessage((Object)ChatColor.GRAY + "Toggle item pickup filter.");
        player.sendMessage("");
        player.sendMessage((Object)ChatColor.WHITE + "/filter edit (alias /fe)");
        player.sendMessage((Object)ChatColor.GRAY + "Edit items you pickup while the item filter is enabled.");
        player.sendMessage("");
        return true;
    }
}

