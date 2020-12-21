/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import org.arkhamnetwork.Arkkit.patches.voting.VotePatch;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class VoteCommandExecTask
extends BukkitRunnable {
    public void run() {
        for (String cmd : VotePatch.pending_commands) {
            VotePatch.pending_commands.remove(cmd);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)cmd);
        }
    }
}

