/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class CommandItemID
extends AbstractCommand {
    public CommandItemID(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        long iid;
        Player p;
        ItemStack i;
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "IID HELP");
            sender.sendMessage((Object)ChatColor.GOLD + "/iid blacklist <add/remove/list/check/empty> (iid)" + (Object)ChatColor.WHITE + " - Controls the IID blacklist.");
            sender.sendMessage((Object)ChatColor.GOLD + "/iid get" + (Object)ChatColor.WHITE + " - Get the IID of item in your hand.");
            sender.sendMessage((Object)ChatColor.GOLD + "/iid next" + (Object)ChatColor.WHITE + " - Get the next_iid system variable.");
            sender.sendMessage((Object)ChatColor.GOLD + "/iid reset" + (Object)ChatColor.WHITE + " - Resets the IID of the item in your hand.");
            sender.sendMessage((Object)ChatColor.GOLD + "/iid clear" + (Object)ChatColor.WHITE + " - Removes IID of the item in your hand.");
            sender.sendMessage((Object)ChatColor.GOLD + "/iid setnext (iid)" + (Object)ChatColor.WHITE + " Sets the next_iid system variable.");
            return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (sender instanceof Player) {
                i = ((Player)sender).getItemInHand();
                ItemID.setItemID(i, ItemID.generateNewItemID());
                sender.sendMessage((Object)ChatColor.YELLOW + "You have reset the IID of the item in your hand to: " + ItemID.getItemID(i));
            } else {
                sender.sendMessage("/iid reset is a player-only command.");
            }
        }
        if (args[0].equalsIgnoreCase("clear")) {
            if (sender instanceof Player) {
                i = ((Player)sender).getItemInHand();
                ItemID.removeItemID(i);
                sender.sendMessage((Object)ChatColor.YELLOW + "You have removed the IID from the item in your hand.");
            } else {
                sender.sendMessage("/iid clear is a player-only command.");
            }
        }
        if (args[0].equalsIgnoreCase("setnext")) {
            long iid2 = Long.parseLong(args[1]);
            if (iid2 < ItemID.next_iid) {
                sender.sendMessage("The IID you specified, " + iid2 + ", is less than the current next_iid value of " + ItemID.next_iid);
                return true;
            }
            ItemID.next_iid = iid2;
            sender.sendMessage("Set next_iid to " + iid2 + "!");
        }
        if (args[0].equalsIgnoreCase("forceset")) {
            long iid3;
            ItemID.next_iid = iid3 = Long.parseLong(args[1]);
            sender.sendMessage("Force set next_iid to " + iid3 + "!");
            return true;
        }
        if (args[0].equalsIgnoreCase("next")) {
            sender.sendMessage((Object)ChatColor.GOLD + "next_iid: " + (Object)ChatColor.WHITE + ItemID.next_iid);
        }
        if (args[0].equalsIgnoreCase("get") && sender instanceof Player && (p = (Player)sender).getItemInHand() != null) {
            iid = ItemID.getItemID(p.getItemInHand());
            if (iid == -1L) {
                sender.sendMessage((Object)ChatColor.RED + "The item in your hand has no IID assigned.");
            } else {
                sender.sendMessage((Object)ChatColor.GOLD + "The IID of the item in your hand is: " + (Object)ChatColor.WHITE + iid);
            }
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("blacklist")) {
            String opp = args[1];
            if (opp.equalsIgnoreCase("add")) {
                iid = Long.parseLong(args[2]);
                ItemID.blacklistIID(iid, "manualAdd");
                sender.sendMessage((Object)ChatColor.RED + "Added iid " + iid + " to ItemID blacklist. Any items found with this IID will be automatically deleted.");
                if (iid > ItemID.next_iid) {
                    sender.sendMessage((Object)ChatColor.RED + "** IID " + iid + " is < next_iid value of " + ItemID.next_iid + ", meaning this item may not yet exist!");
                }
            }
            if (opp.equalsIgnoreCase("remove")) {
                iid = Long.parseLong(args[2]);
                if (ItemID.blacklist_iid.contains(iid)) {
                    ItemID.blacklist_iid.remove(iid);
                    sender.sendMessage((Object)ChatColor.RED + "Removed iid " + iid + " to ItemID blacklist. Items found with this IID will no longer be deleted.");
                } else {
                    sender.sendMessage((Object)ChatColor.YELLOW + "IID " + iid + " is not currently on the ItemID blaclist.");
                }
            }
            if (opp.equalsIgnoreCase("list")) {
                sender.sendMessage("");
                sender.sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "IID Blacklist");
                for (long iid4 : ItemID.blacklist_iid) {
                    sender.sendMessage((Object)ChatColor.GRAY + "" + iid4);
                }
                sender.sendMessage("");
            }
            if (opp.equalsIgnoreCase("check")) {
                long iid5 = Long.parseLong(args[2]);
                if (ItemID.blacklist_iid.contains(iid5)) {
                    sender.sendMessage((Object)ChatColor.RED + "IID " + iid5 + " is " + (Object)ChatColor.BOLD + "BLACKLISTED!");
                } else {
                    sender.sendMessage((Object)ChatColor.GREEN + "IID " + iid5 + " is " + (Object)ChatColor.BOLD + "NOT" + (Object)ChatColor.GREEN + " blacklisted.");
                }
            }
            if (opp.equalsIgnoreCase("empty")) {
                ItemID.blacklist_iid.clear();
                ArrayList llist = new ArrayList();
                Arkkit.get().getConfig().set("settings.itemID.blacklist_iid", llist);
                Arkkit.get().saveConfig();
                sender.sendMessage((Object)ChatColor.GREEN + "The IID blacklist has been completely cleared!");
            }
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            sender.sendMessage((Object)ChatColor.RED + "Missing (iid) parameter! Type /iid for help.");
        }
        return true;
    }
}

