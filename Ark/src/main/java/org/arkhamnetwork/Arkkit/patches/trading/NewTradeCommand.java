/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  me.vaqxine.NetworkManager.NetworkManager
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.trading;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

final class NewTradeCommand
        extends AbstractCommand {
    public NewTradeCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player pl = (Player) sender;
        if (args.length != 1) {
            pl.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "Trade Commands");
            pl.sendMessage(ChatColor.GOLD.toString() + "/trade <player>");
            pl.sendMessage(ChatColor.GRAY + "Sends a trade request to the specified <player>.");
            return true;
        }
        if (Arkkit.get().shutting_down || CommandArkReboot.restart_task_exists || Bukkit.getPluginManager().getPlugin("NetworkManager") != null) {
            pl.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot use this command while the server is rebooting!");
            return true;
        }
        String tradie = args[0];
        if (tradie.equalsIgnoreCase(pl.getName())) {
            pl.sendMessage(ChatColor.RED + "'If a man loves the labour of his trade, apart from any question of success or fame, the gods have called him.'");
            return true;
        }
        Player p_tradie = Bukkit.getPlayer(tradie);
        if (p_tradie == null) {
            pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.RED + " " + ChatColor.RED + "You aren't close enough to " + tradie + " to send a /trade.");
            return true;
        }
        if (p_tradie.getName().equalsIgnoreCase(pl.getName())) {
            pl.sendMessage(ChatColor.RED + "'If a man loves the labour of his trade, apart from any question of success or fame, the gods have called him.'");
            return true;
        }
        if (!p_tradie.getWorld().getName().equalsIgnoreCase(pl.getWorld().getName())) {
            pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.RED + " " + ChatColor.RED + "You aren't close enough to " + tradie + " to send a /trade.");
            return true;
        }
        if (p_tradie.getLocation().distanceSquared(pl.getLocation()) > Math.pow(10.0, 2.0)) {
            pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.RED + " " + ChatColor.RED + "You aren't close enough to " + tradie + " to send a /trade.");
            pl.sendMessage(ChatColor.GRAY + "Move within 10 blocks of them and try again.");
            return true;
        }
        if (pl.hasMetadata("trade_request") || p_tradie.hasMetadata("trade_request")) {
            String stored_tradie;
            if (pl.hasMetadata("trade_request") && (stored_tradie = pl.getMetadata("trade_request").get(0).asString()).equalsIgnoreCase(tradie)) {
                if (!p_tradie.hasMetadata("trade_request") || !p_tradie.getMetadata("trade_request").get(0).asString().equalsIgnoreCase(pl.getName())) {
                    pl.sendMessage(ChatColor.RED + p_tradie.getName() + "'s trade request has timed out.");
                    pl.removeMetadata("trade_request", Arkkit.get());
                    return true;
                }
                pl.removeMetadata("trade_request", Arkkit.get());
                p_tradie.removeMetadata("trade_request", Arkkit.get());
                NewTrade.startTrade(pl, p_tradie);
                return true;
            }
            if (p_tradie.hasMetadata("trade_request")) {
                if (p_tradie.getMetadata("trade_request").get(0).asString().equalsIgnoreCase(pl.getName())) {
                    pl.removeMetadata("trade_request", Arkkit.get());
                    p_tradie.removeMetadata("trade_request", Arkkit.get());
                    NewTrade.startTrade(pl, p_tradie);
                }
                return true;
            }
        }
        pl.setMetadata("trade_request", new FixedMetadataValue(Arkkit.get(), p_tradie.getName()));
        pl.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.GOLD + " You have sent a trade request to " + p_tradie.getName() + "!");
        p_tradie.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "TRADE " + ChatColor.YELLOW + pl.getName() + ChatColor.GOLD + " wants to trade with you. " + ChatColor.YELLOW + "/trade " + pl.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> results = new ArrayList<String>();
        if (args.length != 1) {
            return null;
        }
        String s = args[0];
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("arkkit.staff.hidden") || !pl.getName().toLowerCase().startsWith(s.toLowerCase()))
                continue;
            results.add(pl.getName());
        }
        return results.size() > 0 ? results : null;
    }
}

