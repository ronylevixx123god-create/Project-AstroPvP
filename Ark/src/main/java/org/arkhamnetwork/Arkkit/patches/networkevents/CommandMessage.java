/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEventsListener;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkPrivateMessagesListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandMessage
extends AbstractCommand {
    public CommandMessage(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 1) {
            sender.sendMessage((Object)ChatColor.YELLOW + "Usage " + (Object)ChatColor.WHITE + "/" + label + " [player] [message]");
            return true;
        }
        String target = args[0];
        String message = "";
        for (int i = 1; i < args.length; ++i) {
            message = message + (i == args.length - 1 ? args[i] : args[i] + " ");
        }
        if (Bukkit.getPlayer((String)target) != null) {
            if (!NetworkEvents.getServerSync().isPlayerHidden(Bukkit.getPlayer((String)target))) {
                String code = target + NetworkEvents.packet_split + sender.getName() + NetworkEvents.packet_split + message + NetworkEvents.packet_split + NetworkEvents.getUsername();
                String[] tofrommessage = code.split(NetworkEvents.packet_split);
                String to = tofrommessage[0];
                String from = tofrommessage[1];
                String raw_message = tofrommessage[2];
                String fromServer = tofrommessage[3];
                Player p = Bukkit.getPlayer((String)target);
                if (!sender.hasPermission("cosmicpvp.vip") && ChatUtils.checkMessageForWebPattern(raw_message)) {
                    sender.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot send URLs in private messages.");
                    return true;
                }
                if (!sender.hasPermission("bm.command.bminfo") && !sender.hasPermission("arkkit.ignore.bypass") && (IgnoreUtils.isIgnored(p, sender.getName()) || NetworkEventsListener.msgToggled.contains(p.getUniqueId()))) {
                    sender.sendMessage((Object)ChatColor.RED + target + " is not currently accepting messages.");
                    return true;
                }
                Player playerSending = (Player)sender;
                boolean silentMuted = playerSending.hasMetadata("silentMute");
                if (!silentMuted) {
                    NetworkEvents.lastMessaged.put(p.getName(), from);
                }
                String chat = ChatColor.translateAlternateColorCodes((char)'&', (String)Arkkit.get().getConfig().getString("language.network_private_messages.message-from").replace("{player}", from).replace("{server}", fromServer));
                if (sender.hasPermission("bm.command.bminfo")) {
                    chat = chat.replace(ChatColor.WHITE.toString(), ChatColor.RED.toString());
                }
                if (!silentMuted) {
                    p.sendMessage(chat.replace("{message}", ChatUtils.getFilteredMessage(raw_message, p, true)));
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)Arkkit.get().getConfig().getString("language.network_private_messages.message-sent").replace("{player}", to).replace("{message}", raw_message).replace("{server}", fromServer)));
                if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
                    for (FPlayer fplayer : FPlayers.i.getOnline()) {
                        if (!fplayer.isSpyingChat()) continue;
                        fplayer.sendMessage((Object)ChatColor.BLUE + "[NEspy] " + (Object)ChatColor.AQUA + sender.getName() + (Object)ChatColor.BLUE + "->" + (Object)ChatColor.DARK_AQUA + target + (Object)ChatColor.BLUE + ": " + (Object)ChatColor.WHITE + raw_message);
                    }
                }
                return true;
            }
            sender.sendMessage((Object)ChatColor.GRAY + "No online player found for '" + (Object)ChatColor.RED + target + (Object)ChatColor.GRAY + "' search query.");
            return true;
        }
        String target_server = NetworkEvents.getServerSync().lookupPlayer(target);
        if (target_server == null) {
            sender.sendMessage((Object)ChatColor.GRAY + "No online player found for '" + (Object)ChatColor.RED + target + (Object)ChatColor.GRAY + "' search query.");
            return true;
        }
        if (sender.hasPermission("bm.command.bminfo")) {
            message = message.replace(ChatColor.WHITE.toString(), ChatColor.RED.toString());
        }
        if (sender instanceof Player) {
            Player pSender = (Player)sender;
            Bukkit.getLogger().info("[Arkkit (NetworkEvents)] " + pSender.getName() + " /msg to " + target + "(@" + target_server + "): " + message);
            if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
                for (FPlayer fplayer : FPlayers.i.getOnline()) {
                    if (!fplayer.isSpyingChat()) continue;
                    fplayer.sendMessage((Object)ChatColor.BLUE + "[NEspy] " + (Object)ChatColor.AQUA + sender.getName() + (Object)ChatColor.BLUE + "->" + (Object)ChatColor.DARK_AQUA + target + "@" + target_server + (Object)ChatColor.BLUE + ": " + (Object)ChatColor.WHITE + message);
                }
            }
            if (pSender.hasMetadata("silentMute")) {
                NetworkPrivateMessagesListener.sendPlayerCallback(pSender, target, pSender.getName(), message, target_server);
                return true;
            }
        }
        NetworkEvents.request(target_server, "NetworkPrivateMessages.message", target + NetworkEvents.packet_split + sender.getName() + NetworkEvents.packet_split + message + NetworkEvents.packet_split + NetworkEvents.getUsername());
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
            if (!pl.getName().toLowerCase().startsWith(s.toLowerCase())) continue;
            results.add(pl.getName());
        }
        return results.size() > 0 ? results : null;
    }
}

