/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.balancetop;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandBalanceTopAbstract
extends AbstractCommand {
    DecimalFormat df = new DecimalFormat("#,##0");

    public CommandBalanceTopAbstract(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command arg1, String arg2, final String[] args) {
        new BukkitRunnable(){

            public void run() {
                LinkedList<List<String>> pageCopy;
                sender.sendMessage((Object)ChatColor.YELLOW + "Loading the balances of " + CommandBalanceTopAbstract.this.insertCommas(BalanceManager.balanceMap.size()) + " players...");
                int index = 1;
                if (args.length == 1 && StringUtils.isNumeric((String)args[0])) {
                    index = Integer.parseInt(args[0]);
                }
                if ((pageCopy = BalanceManager.getPages()).size() + 1 < index) {
                    sender.sendMessage((Object)ChatColor.RED + "Please provide a valid page number 1 - " + (pageCopy.size() + 1));
                    return;
                }
                List<String> page = pageCopy.get(index - 1);
                sender.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "Top " + page.size() + " Balances - Page " + index + " / " + pageCopy.size());
                sender.sendMessage("");
                for (String s : page) {
                    sender.sendMessage(s);
                }
            }
        }.runTaskAsynchronously((Plugin)Arkkit.get());
        return false;
    }

    public String insertCommas(double d) {
        return this.df.format(d);
    }
}

