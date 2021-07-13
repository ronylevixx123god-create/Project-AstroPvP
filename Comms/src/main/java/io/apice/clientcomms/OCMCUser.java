/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package io.apice.clientcomms;

import org.bukkit.entity.Player;

public class OCMCUser {
    private Player player;
    private String channel;
    private String version;
    private String system;

    public OCMCUser(Player player, String version) {
        this.player = player;
        this.channel = "local";
        this.version = version;
        this.system = null;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}

