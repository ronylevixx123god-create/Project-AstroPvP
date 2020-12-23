/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.vexsoftware.votifier.model.Vote
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import com.vexsoftware.votifier.model.Vote;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VotePatch
        extends ArkPlugin {
    protected static List<String> vote_messages;
    protected static boolean log_votes;
    protected static boolean reward_votes;
    protected static CopyOnWriteArrayList<Vote> pending_votes;
    protected static LinkedList<String> vote_commands;
    protected static CopyOnWriteArrayList<String> pending_commands;

    static {
        log_votes = false;
        reward_votes = true;
        pending_votes = new CopyOnWriteArrayList();
        pending_commands = new CopyOnWriteArrayList();
    }

    private VS_SQLQueryThread sql_worker;

    public VotePatch(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!new File("plugins/Votifier.jar").exists()) {
            Bukkit.getLogger().info("(Arkkit [Vote]) Votifier.jar not found, disabling Arkkit Vote patch.");
            this.kill();
            return;
        }
        vote_commands = new LinkedList();
        log_votes = this.getBukkitPlugin().getConfig().getBoolean("settings.voting.log_votes");
        reward_votes = this.getBukkitPlugin().getConfig().getBoolean("settings.voting.reward_votes");
        for (String s : Arkkit.get().getConfig().getStringList("settings.voting.vote_commands")) {
            vote_commands.add(s);
        }
        vote_messages = this.getBukkitPlugin().getConfig().getStringList("language.vote.voting_messages");
        if (new File("plugins/Arkkit/VS_sqlconfig.yml").exists()) {
            VS_SQLManager.initializeSQL(Arkkit.get());
            this.sql_worker = new VS_SQLQueryThread();
            this.sql_worker.start();
            VS_SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS player_votes(player_uuid VARCHAR(36) PRIMARY KEY NOT NULL, player_name VARCHAR(16), votes INT, last_vote LONG)");
        } else {
            Bukkit.getLogger().info("(Arkkit [Vote]) VS_sqlconfig.yml could not be found, SQL functionality will be disasbled.");
        }
        this.registerCommand(new CommandVote("vote", "/command", "View server voting methods and rewards!", new ArrayList<String>(Arrays.asList("votes", "voting"))));
        this.registerTask(new VoteProcessTask().runTaskTimerAsynchronously(Arkkit.get(), 20L, 20L), VotePatch.class);
        this.registerTask(new VoteCommandExecTask().runTaskTimer(Arkkit.get(), 20L, 20L), VotePatch.class);
        this.registerListener(new VoteListener());
    }

    @Override
    public void disable() {
        this.sql_worker.interrupt();
        this.sql_worker.doWork();
        this.sql_worker = null;
    }
}

