/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.FacMuteCommand;
import org.arkhamnetwork.Arkkit.patches.FactionMuteListener;
import org.arkhamnetwork.Arkkit.patches.IPMuteCommand;
import org.arkhamnetwork.Arkkit.patches.IPUnMuteCommand;
import org.arkhamnetwork.Arkkit.patches.MuteReason;
import org.arkhamnetwork.Arkkit.patches.UnFactionMuteCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class ExtraMute
extends ArkPlugin {
    private HashMap<String, Long> factionsMuted = new HashMap();
    private HashMap<String, MuteReason> ipsMuted = new HashMap();

    public ExtraMute(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new FactionMuteListener(this));
        this.registerCommand(new IPMuteCommand(this, "ipmute", "/ipmute <ip/player> <length> <reason>", "Mute ip", (List<String>)Lists.newArrayList(new String[]{"ipmutes"})));
        this.registerCommand(new IPUnMuteCommand(this, "ipunmute", "/ipunmute <ip/player>", "Unmute ip", (List<String>)Lists.newArrayList(new String[]{"unmuteip", "unipmute"})));
        this.registerCommand(new UnFactionMuteCommand(this, "unmutefac", "/<command> <name>", "Unmutes Faction", (List<String>)Lists.newArrayList(new String[]{"unmutefaction", "facunmute", "unfacmute"})));
        this.registerCommand(new FacMuteCommand(this, "facmute", "/<command> <fac> <length> <reason>", "Faction mute.", (List<String>)Lists.newArrayList(new String[]{"mutefaction"})));
    }

    @Override
    public void disable() {
        this.unregisterCommands();
        this.unregisterListeners();
    }

    public HashMap<String, Long> getFactionsMuted() {
        return this.factionsMuted;
    }

    public HashMap<String, MuteReason> getIpsMuted() {
        return this.ipsMuted;
    }
}

