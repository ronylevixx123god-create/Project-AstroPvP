/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Bukkit
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
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

class CommandSendBrag
        extends AbstractCommand {
    private final ItemLinkingChatListener itemLinkingListener;

    public CommandSendBrag(String command, String usage, String description, List<String> aliases, ItemLinkingChatListener linking) {
        super(command, usage, description, aliases);
        this.itemLinkingListener = linking;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (!(p.hasPermission("itemlinking.senditem") || p.hasPermission("arkhamjsonitem.senditem") || p.hasPermission("cosmicpvp.senditem"))) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this feature!");
            p.sendMessage(ChatColor.GRAY + "Purchase a global rank with " + ChatColor.UNDERLINE + "/buy" + ChatColor.GRAY + " and try again!");
            return true;
        }
        if (p.hasMetadata("last_senditem") && System.currentTimeMillis() - p.getMetadata("last_senditem").get(0).asLong() <= 5000L) {
            p.sendMessage(ChatColor.RED + "You cannot send another /sendbrag for " + (5000L - (System.currentTimeMillis() - p.getMetadata("last_senditem").get(0).asLong())) + "ms!");
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
                String text = CC.Gray + "(From " + p.getName() + ") " + CC.Reset + "\u00bb [BRAG] " + CC.Reset + "\u00ab";
                if (!p.hasMetadata("silentMute")) {
                    this.itemLinkingListener.createNewBragInventory(p);
                    ItemLinking.sendItemViaJSON(ChatUtils.getFilteredMessage(text, to_show, true), p.getItemInHand(), to_show, p);
                }
                p.sendMessage(ChatColor.GREEN + "Brag Sent!");
                return true;
            }
            StringBuilder builder = new StringBuilder();
            for (int index = 1; index < args.length; ++index) {
                builder.append(args[index]).append(" ");
            }
            String text = ChatColor.GRAY + "(From " + p.getName() + ") " + ChatColor.WHITE + "\u00bb [BRAG] " + CC.White + "\u00ab - " + ChatColor.translateAlternateColorCodes('&', builder.toString().trim());
            if (!p.hasMetadata("silentMute")) {
                this.itemLinkingListener.createNewBragInventory(p);
                ItemLinking.sendItemViaJSON(ChatUtils.getFilteredMessage(text, to_show, true), p.getItemInHand(), to_show, p);
            }
            p.sendMessage(ChatColor.GREEN + "Brag Sent!");
            return true;
        }
        p.sendMessage(ChatColor.RED + "Invalid args: /sendBrag <playerName> [extraText]");
        return false;
    }
}

