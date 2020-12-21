/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.session;

import com.sk89q.commandbook.session.PersistentSession;
import com.zachsthings.libcomponents.config.Setting;
import java.util.concurrent.TimeUnit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserSession
extends PersistentSession {
    public static final String CONSOLE_NAME = "#console";
    private static final long MAX_AGE = TimeUnit.DAYS.toMillis(10L);
    private static final long RECONNECT_GRACE = TimeUnit.MINUTES.toMillis(1L);
    @Setting(value="messaging.last-recipient")
    private String lastRecipient = null;
    @Setting(value="messaging.last-recipient-time")
    private long lastRecipientTime = 0L;
    private boolean hasThor = false;
    @Setting(value="idle-status")
    private String idleStatus = null;
    @Setting(value="confirm-command")
    private String commandToConfirm;

    protected UserSession() {
        super(MAX_AGE);
    }

    @Override
    public void handleReconnect(CommandSender player) {
        super.handleReconnect(player);
        if (this.getGoneTime() >= RECONNECT_GRACE) {
            this.lastRecipient = null;
        }
    }

    @Override
    public void handleDisconnect() {
        super.handleDisconnect();
        this.hasThor = false;
    }

    public String getLastRecipient() {
        return this.lastRecipient;
    }

    public void setLastRecipient(CommandSender target) {
        this.lastRecipient = target instanceof Player ? target.getName() : CONSOLE_NAME;
    }

    public void setNewLastRecipient(CommandSender target) {
        long now = System.currentTimeMillis();
        if (this.lastRecipient == null || now - this.lastRecipientTime > 1000L) {
            this.setLastRecipient(target);
            this.lastRecipientTime = now;
        }
    }

    public boolean hasThor() {
        return this.hasThor;
    }

    public void setHasThor(boolean hasThor) {
        this.hasThor = hasThor;
    }

    public String getIdleStatus() {
        return this.idleStatus;
    }

    public void setIdleStatus(String status) {
        this.idleStatus = status;
    }

    public boolean checkOrQueueConfirmed(String command) {
        if (this.commandToConfirm != null) {
            return true;
        }
        this.commandToConfirm = command;
        return false;
    }

    public String getCommandToConfirm(boolean clear) {
        if (clear) {
            String ret = this.commandToConfirm;
            this.commandToConfirm = null;
            return ret;
        }
        return this.commandToConfirm;
    }
}

