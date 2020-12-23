/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.vexsoftware.votifier.model.Vote
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import com.vexsoftware.votifier.model.Vote;
import org.bukkit.scheduler.BukkitRunnable;

public class VoteProcessTask
        extends BukkitRunnable {
    public synchronized void run() {
        for (Vote vote : VotePatch.pending_votes) {
            VoteHandler.handleVote(vote, false, 0);
            VotePatch.pending_votes.remove(vote);
        }
    }
}

