/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.teams;

import java.util.ArrayList;
import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.teams.CommandTeamChat;
import org.arkhamnetwork.Arkkit.patches.teams.CommandTeams;
import org.arkhamnetwork.Arkkit.patches.teams.TeamListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class Teams
extends ArkPlugin {
    public Teams(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new TeamListener());
        this.registerCommand(new CommandTeams("teams", "/command <args>", "Create and manage player parties!", new ArrayList<String>(Arrays.asList("gang", "clan", "parties", "group", "team", "party"))));
        this.registerCommand(new CommandTeamChat("teamchat", "/command <args>", "Speak to your /team in private!", new ArrayList<String>(Arrays.asList("tc", "partychat", "groupmsg"))));
    }

    @Override
    public void disable() {
    }
}

