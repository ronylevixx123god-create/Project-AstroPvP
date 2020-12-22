/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class CommandSetChannel
extends AbstractCommand {
    public CommandSetChannel(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("setchannel.youtube") && !sender.hasPermission("setchannel.twitch")) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Only officially ranked Cosmic Content Creators can use /setchannel.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(CC.Yellow + "/setchannel <channelName> <channelURL>");
            sender.sendMessage((Object)ChatColor.YELLOW + "<channelName>:" + CC.Gray + " Any generic 1-word name for your channel, & color codes supported.");
            sender.sendMessage((Object)ChatColor.YELLOW + "<channelURL>:" + CC.Gray + " The URL link to your channel that will be clickable by players.");
            return true;
        }
        String channelName = ChatColor.translateAlternateColorCodes((char)'&', (String)args[0]);
        String channelURL = args[1];
        boolean youtube = channelURL.contains("youtube.com") || channelURL.contains("youtu.be");
        boolean twitch = channelURL.contains("twitch.tv");
        if (!youtube && !twitch) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Your <URL> must link to youtube.com or twitch.tv!");
            return true;
        }
        if (youtube && !sender.hasPermission("setchannel.youtube")) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have permission to set a youtube.com channel link.");
            return true;
        }
        if (twitch && !sender.hasPermission("setchannel.twitch")) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have permission to set a twitch.tv channel link.");
            return true;
        }
        if (sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.setMetadata("contentCreatorName", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)channelName));
            pSender.setMetadata("contentCreatorURL", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)channelURL));
            Arkkit.get().getConfig().set("settings.contentCreator." + pSender.getUniqueId() + ".channelName", (Object)channelName);
            Arkkit.get().getConfig().set("settings.contentCreator." + pSender.getUniqueId() + ".channelURL", (Object)channelURL);
            ContentCreatorListener.requestAll(ContentCreator.contentCreator_lilypadChannel, pSender.getUniqueId() + "," + channelName + "," + channelURL);
            sender.sendMessage(CC.YellowB + "(!) " + CC.Yellow + "Congratulations! Your " + channelName + CC.Yellow + " channel has been configured with the URL: " + channelURL);
            sender.sendMessage(CC.Gray + "Players will now be able to navigate to your channel by clicking your <YouTube> or <Twitch> rank prefix!");
            return true;
        }
        return true;
    }
}

