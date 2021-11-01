package me.ifamasssxd.itemfilter.commands;

import me.ifamasssxd.itemfilter.Filter;
import me.ifamasssxd.itemfilter.FilterManager;
import net.astropvp.core.AstroCore;
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
            FilterManager manager = AstroCore.getInstance().getFilterManager();
            if (args[0].equalsIgnoreCase("toggle")) {
                Filter filter;
                Filter filter2 = filter = manager.getItemFilters().containsKey(player.getUniqueId()) ? manager.getItemFilters().get(player.getUniqueId()) : new Filter();
                if (!manager.getItemFilters().containsKey(player.getUniqueId())) {
                    manager.getItemFilters().put(player.getUniqueId(), filter);
                }
                filter.setEnabled(!filter.isEnabled());
                player.sendMessage((filter.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + "(!) Item Filter " + (filter.isEnabled() ? ChatColor.GREEN + ChatColor.BOLD.toString() + "ENABLED" : ChatColor.RED + ChatColor.BOLD.toString() + "DISABLED"));
                if (!filter.isEnabled()) {
                    AstroCore.getInstance().getItemListener().alerted.remove(player.getUniqueId());
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("edit")) {
                player.openInventory(AstroCore.getInstance().getFilterManager().getCategoryMenu(player));
                return true;
            }
        }
        player.sendMessage("");
        player.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "Item Filter Commands");
        player.sendMessage(ChatColor.WHITE + "/filter toggle (alias /ft)");
        player.sendMessage(ChatColor.GRAY + "Toggle item pickup filter.");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "/filter edit (alias /fe)");
        player.sendMessage(ChatColor.GRAY + "Edit me.astropvp.cratekeys.items you pickup while the item filter is enabled.");
        player.sendMessage("");
        return true;
    }
}

