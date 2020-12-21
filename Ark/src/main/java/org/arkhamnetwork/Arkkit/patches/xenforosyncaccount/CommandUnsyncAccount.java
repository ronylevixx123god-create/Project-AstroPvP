/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.com.google.gson.JsonParser
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.xenforosyncaccount;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import net.minecraft.util.com.google.gson.JsonParser;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_SQLManager;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_Website_SQLManager;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XenforoSyncAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandUnsyncAccount
extends AbstractCommand {
    JsonParser parser = new JsonParser();

    public CommandUnsyncAccount(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("/unsyncaccount is a player-only command.");
            return true;
        }
        Player pl = (Player)sender;
        UUID uuid = pl.getUniqueId();
        try {
            this.setUserStateModerated(XenforoSyncAccount.getForumIDFromPlayerUUID(uuid));
            this.removeUserFromSyncDatabase(uuid);
            pl.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.AQUA + "All forum accounts associated with your username have been un-linked.");
            pl.sendMessage((Object)ChatColor.GRAY + "Use /syncaccount to link a new forum account.");
        }
        catch (Exception err) {
            pl.sendMessage((Object)ChatColor.RED + "An error occured while processing your request.");
            err.printStackTrace();
        }
        return true;
    }

    private void setUserStateModerated(String xf_user_id) {
        XF_Website_SQLManager.sql_query.add("UPDATE xf_user SET user_state='moderated' WHERE user_state='valid' && user_id=" + xf_user_id);
    }

    private void removeUserFromSyncDatabase(UUID uuid) throws SQLException {
        XF_SQLManager.sql_query.add("DELETE FROM mc_users WHERE uuid='" + uuid.toString() + "'");
    }
}

