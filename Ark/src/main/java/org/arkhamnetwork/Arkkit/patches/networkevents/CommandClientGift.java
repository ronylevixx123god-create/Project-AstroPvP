/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.BuycraftManager.BuycraftManagerAPI
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import com.compilex.BuycraftManager.BuycraftManagerAPI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLConnection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandClientGift
extends AbstractCommand {
    public CommandClientGift(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/clientgift <command>");
            sender.sendMessage("Use {p} to signify each unique player's name.");
            return true;
        }
        String ANY_SERVER = "";
        String gift = "";
        for (String s : args) {
            gift = gift + s + " ";
        }
        gift = gift.trim();
        try {
            PreparedStatement statement = NE_SQLConnection.getConnection().prepareStatement("SELECT player_name FROM 5zig_users WHERE protocol=2;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String player_name = rs.getString("player_name");
                String pgift = gift;
                pgift = pgift.replace("{p}", player_name);
                BuycraftManagerAPI.queueLoginCommand((String)player_name, (String)"CosmicPvP", (String)"CosmicPvP Client Mod Gift", (String)pgift, (boolean)false, (String)ANY_SERVER, (String)"buy.cosmicpvp.com");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}

