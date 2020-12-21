/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.vexsoftware.votifier.model.VotifierEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.arkhamnetwork.Arkkit.patches.voting.VoteAPI;
import org.arkhamnetwork.Arkkit.patches.voting.VotePatch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class VoteListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onVote(VotifierEvent evt) {
        VotePatch.pending_votes.add(evt.getVote());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        VoteAPI.clearPlayerVoteCache(e.getPlayer().getUniqueId().toString());
    }
}

