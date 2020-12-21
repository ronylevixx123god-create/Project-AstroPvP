/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.session;

import com.zachsthings.libcomponents.config.ConfigurationBase;
import com.zachsthings.libcomponents.config.Setting;
import java.util.concurrent.TimeUnit;
import org.bukkit.command.CommandSender;

public abstract class PersistentSession
extends ConfigurationBase {
    public static final long THIRTY_MINUTES = TimeUnit.MINUTES.toMillis(30L);
    public static final long ONE_HOUR = TimeUnit.HOURS.toMillis(1L);
    private final long maxTime;
    @Setting(value="last-update")
    private long lastUpdate;
    private CommandSender sender;
    private String senderName;

    protected PersistentSession(long maxTime) {
        this.maxTime = maxTime;
    }

    public long getGoneTime() {
        return System.currentTimeMillis() - this.lastUpdate;
    }

    public boolean isRecent() {
        return this.maxTime == -1L || this.getGoneTime() < this.maxTime;
    }

    public CommandSender getOwner() {
        return this.sender;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void handleDisconnect() {
        this.lastUpdate = System.currentTimeMillis();
        this.sender = null;
    }

    public void handleReconnect(CommandSender sender) {
        if (sender == null) {
            throw new IllegalArgumentException("sender must not be null!");
        }
        this.sender = sender;
        this.senderName = sender.getName();
    }
}

