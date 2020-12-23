/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandVote
        extends AbstractCommand {
    public CommandVote(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1 && args[0].equals("convert")) {
                this.convertVoteData();
                return true;
            }
            Bukkit.getLogger().info("That is not a console command.");
            return true;
        }
        final Player player = (Player) sender;
        Bukkit.getScheduler().runTaskAsynchronously(Arkkit.get(), new Runnable() {

            @Override
            public void run() {
                final int votes = VoteAPI.getPlayerVotes(player.getUniqueId().toString());
                Bukkit.getScheduler().runTask(Arkkit.get(), new Runnable() {

                    @Override
                    public void run() {
                        for (String voteMessage : VotePatch.vote_messages) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', voteMessage.replace("[VOTES]", String.valueOf(votes))));
                        }
                    }
                });
            }
        });
        return true;
    }

    private void convertVoteData() {
        ArrayList<VoterObject> old_vote_data = new ArrayList<VoterObject>();
        ResultSet old_data = VS_SQLConnection.getResultSet("SELECT * FROM votes.vote_player");
        try {
            while (old_data.next()) {
                String p = old_data.getString("player_name");
                String u = Bukkit.getOfflinePlayer(p).getUniqueId().toString();
                int v = old_data.getInt("votes");
                Bukkit.getLogger().info("Caching " + p + "(" + u + ") with " + v + " votes!");
                old_vote_data.add(new VoterObject(p, u, v));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("********");
        Bukkit.getLogger().info("*** CONVERTING " + old_vote_data.size() + " ROWS ***");
        Bukkit.getLogger().info("********");
        Bukkit.getLogger().info(" ");
        for (VoterObject vo : old_vote_data) {
            Bukkit.getLogger().info("Setting " + vo.getPlayerName() + "'s new vote count to " + vo.getVoteCount());
            VoteAPI.setPlayerVotes(vo.getUUID(), vo.getPlayerName(), vo.getVoteCount());
        }
    }
}

