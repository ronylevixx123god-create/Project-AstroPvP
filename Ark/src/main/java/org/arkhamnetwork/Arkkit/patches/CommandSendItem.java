/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

final class CommandSendItem
        extends AbstractCommand {
    public CommandSendItem(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!(p.hasPermission("itemlinking.senditem") || p.hasPermission("arkhamjsonitem.senditem") || p.hasPermission("cosmicpvp.senditem"))) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this feature!");
            p.sendMessage(ChatColor.GRAY + "Purchase a global rank with " + ChatColor.UNDERLINE + "/buy" + ChatColor.GRAY + " and try again!");
            return true;
        }
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "You must have an item to show someone!");
            return true;
        }
        if (p.hasMetadata("last_senditem") && System.currentTimeMillis() - p.getMetadata("last_senditem").get(0).asLong() <= 5000L) {
            p.sendMessage(ChatColor.RED + "You cannot send another /senditem for " + (5000L - (System.currentTimeMillis() - p.getMetadata("last_senditem").get(0).asLong())) + "ms!");
            return true;
        }
        p.setMetadata("last_senditem", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis()));
        if (args.length >= 1) {
            String p_name = args[0];
            Player to_show = Bukkit.getPlayer(p_name);
            if (to_show == null || !p.canSee(to_show)) {
                p.sendMessage(ChatColor.RED + "That is an invalid player: " + p_name);
                return true;
            }
            if (args.length == 1) {
                ItemStack is = p.getItemInHand();
                String text = ChatColor.GRAY + "(From " + p.getName() + ") " + ChatColor.RESET + "\u00bb" + (is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords(is.getType().name().toLowerCase().replace("_", " "))) + ChatColor.GRAY + (is.getMaxStackSize() >= 1 ? " (x" + is.getAmount() + ")" : "") + ChatColor.WHITE + " \u00ab";
                if (!p.hasMetadata("silentMute")) {
                    ItemLinking.sendItemViaJSON(ChatUtils.getFilteredMessage(text, to_show, true), p.getItemInHand(), to_show);
                }
                p.sendMessage(ChatColor.GREEN + "Sent!");
            } else {
                String text = "";
                boolean first = true;
                for (String arg : args) {
                    if (first) {
                        first = false;
                        continue;
                    }
                    text = text + arg + " ";
                }
                text = text.trim();
                text = ChatColor.translateAlternateColorCodes('&', text);
                ItemStack is = p.getItemInHand();
                text = ChatColor.GRAY + "(From " + p.getName() + ") " + ChatColor.RESET + "\u00bb" + (is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords(is.getType().name().toLowerCase().replace("_", " "))) + ChatColor.RESET + ChatColor.GRAY + (is.getMaxStackSize() >= 1 ? " (x" + is.getAmount() + ")" : "") + ChatColor.WHITE + " \u00ab - " + text;
                if (!p.hasMetadata("silentMute")) {
                    ItemLinking.sendItemViaJSON(ChatUtils.getFilteredMessage(text, to_show, true), p.getItemInHand(), to_show);
                }
            }
        } else {
            p.sendMessage(ChatColor.RED + "Invalid args: /senditem <playerName> [extraText]");
        }
        return true;
    }
}

