package me.kingsavaqe.astroenvoy.playerdata;

import java.util.UUID;

public class PlayerData {
    private UUID uuid;

    private int opened;

    private boolean notifications = true;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setOpened(int opened) {
        this.opened = opened;
    }

    public int getOpened() {
        return this.opened;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public boolean hasNotifications() {
        return this.notifications;
    }
}
