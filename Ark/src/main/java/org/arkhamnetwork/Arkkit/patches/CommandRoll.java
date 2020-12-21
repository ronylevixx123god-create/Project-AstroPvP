/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.Gamble;
import org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

final class CommandRoll
extends AbstractCommand {
    private HashMap<String, Long> roll_delay = new HashMap();

    public CommandRoll(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            int roll;
            Player p = (Player)sender;
            if (args.length != 1) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Incorrect Syntax. " + (Object)ChatColor.GRAY + "/roll <1-10000/toggle>");
                p.sendMessage((Object)ChatColor.GRAY + "Rolls a random number between 0 and X and outputs the result to local chat.");
                return true;
            }
            if (args[0].length() > 5) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Incorrect Syntax. " + (Object)ChatColor.GRAY + "/roll <1-10000/toggle>");
                return true;
            }
            String number = args[0];
            ThreadLocalRandom dice = ThreadLocalRandom.current();
            if (number.equalsIgnoreCase("stfu") || number.equalsIgnoreCase("toggle")) {
                if (Gamble.disabledRollUUIDs.contains(p.getUniqueId().toString())) {
                    Gamble.disabledRollUUIDs.remove(p.getUniqueId().toString());
                    p.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GREEN + "You have enabled displaying /roll numbers!");
                } else {
                    Gamble.disabledRollUUIDs.add(p.getUniqueId().toString());
                    p.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You have disabled displaying /roll numbers!");
                }
                return true;
            }
            if (Gamble.disabledRollUUIDs.contains(p.getUniqueId().toString())) {
                p.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot use /roll while having it silenced. Use " + (Object)ChatColor.UNDERLINE + "/roll toggle" + (Object)ChatColor.RED + " to re-enable.");
                return true;
            }
            if (number.contains("-")) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Non-Positive Max Number. " + (Object)ChatColor.RED + "/roll <1-10000>");
                return true;
            }
            if (number.startsWith("0")) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Incorrect Syntax. " + (Object)ChatColor.GRAY + "/roll <1-10000>");
                return true;
            }
            Long time = this.roll_delay.get(p.getName());
            if (time != null && System.currentTimeMillis() - time <= 4000L) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You need to wait longer between uses of /roll!");
                return true;
            }
            this.roll_delay.put(p.getName(), System.currentTimeMillis());
            try {
                roll = ((Random)dice).nextInt(Integer.parseInt(number) + 1);
            }
            catch (NumberFormatException nfe) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Non-Numeric Max Number. " + (Object)ChatColor.RED + "/roll <1-10000>");
                return true;
            }
            for (Entity ent : p.getNearbyEntities(20.0, 20.0, 20.0)) {
                Player player;
                if (!(ent instanceof Player) || IgnoreUtils.isIgnored(player = (Player)ent, p.getName()) || Gamble.disabledRollUUIDs.contains(player.getUniqueId().toString())) continue;
                player.sendMessage((Object)ChatColor.GRAY + "** " + p.getName() + (Object)ChatColor.GRAY + " has rolled a " + (Object)ChatColor.UNDERLINE + (Object)ChatColor.BOLD + roll + (Object)ChatColor.GRAY + " out of " + (Object)ChatColor.UNDERLINE + (Object)ChatColor.BOLD + args[0] + ".");
            }
            p.sendMessage((Object)ChatColor.GRAY + "** " + p.getName() + (Object)ChatColor.GRAY + " has rolled a " + (Object)ChatColor.UNDERLINE + (Object)ChatColor.BOLD + roll + (Object)ChatColor.GRAY + " out of " + (Object)ChatColor.UNDERLINE + (Object)ChatColor.BOLD + args[0] + ".");
            return true;
        }
        return true;
    }
}

