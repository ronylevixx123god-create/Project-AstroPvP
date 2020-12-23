/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.vexsoftware.votifier.model.Vote
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import com.vexsoftware.votifier.model.Vote;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VoteHandler {
    private static final boolean store_vote_if_offline = false;

    public static void handleVote(Vote vote, boolean storedVote, int wouldHaveBeenVotes) {
        Player player;
        if (vote == null || vote.getUsername() == null || vote.getUsername().isEmpty() || vote.getUsername().length() <= 1) {
            return;
        }
        String uuid = Bukkit.getOfflinePlayer(vote.getUsername()).getUniqueId().toString();
        if (VotePatch.log_votes) {
            VoteAPI.setPlayerVotes(uuid, vote.getUsername(), VoteAPI.getPlayerVotes(uuid) + 1);
            VoteAPI.updateLastVotedTime(uuid);
        }
        if ((player = Bukkit.getPlayer(uuid)) == null || !player.isOnline()) {
            if (store_vote_if_offline) {
                Bukkit.getLogger().info("(Arkkit [Vote]) Skipped storing vote for " + vote.getUsername() + " cause it is depricated.");
                return;
            }
            if (VotePatch.reward_votes) {
                for (String command : VotePatch.vote_commands) {
                    VotePatch.pending_commands.add(command.replace("[PLAYER]", vote.getUsername()));
                }
            }
            Bukkit.getLogger().info("(Arkkit [Vote]) Processed vote for " + vote.getUsername() + "(" + uuid + ")");
            return;
        }
        int currentVotes = storedVote ? wouldHaveBeenVotes : VoteAPI.getPlayerVotes(vote.getUsername());
        if (VotePatch.reward_votes) {
            for (String command : VotePatch.vote_commands) {
                VotePatch.pending_commands.add(command.replace("[PLAYER]", vote.getUsername()));
            }
        }
        player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.YELLOW + " Monthly Vote Count: " + ChatColor.AQUA.toString() + ChatColor.UNDERLINE.toString() + currentVotes);
    }
}

