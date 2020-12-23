/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ExtraMute
        extends ArkPlugin {
    private final HashMap<String, Long> factionsMuted = new HashMap();
    private final HashMap<String, MuteReason> ipsMuted = new HashMap();

    public ExtraMute(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new FactionMuteListener(this));
        this.registerCommand(new IPMuteCommand(this, "ipmute", "/ipmute <ip/player> <length> <reason>", "Mute ip", Lists.newArrayList("ipmutes")));
        this.registerCommand(new IPUnMuteCommand(this, "ipunmute", "/ipunmute <ip/player>", "Unmute ip", Lists.newArrayList("unmuteip", "unipmute")));
        this.registerCommand(new UnFactionMuteCommand(this, "unmutefac", "/<command> <name>", "Unmutes Faction", Lists.newArrayList("unmutefaction", "facunmute", "unfacmute")));
        this.registerCommand(new FacMuteCommand(this, "facmute", "/<command> <fac> <length> <reason>", "Faction mute.", Lists.newArrayList("mutefaction")));
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

