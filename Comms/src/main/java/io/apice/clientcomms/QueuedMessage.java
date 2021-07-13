/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package io.apice.clientcomms;

import io.apice.clientcomms.OCMCMessage;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.entity.Player;

public class QueuedMessage {
    private String channel;
    private String username;
    private UUID uuid;
    private byte[] data;
    private OCMCMessage ocmcMessage;

    public QueuedMessage(String channel, Player player, byte[] data) {
        this.channel = channel;
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.data = data;
    }

    public QueuedMessage(String channel, Player player, OCMCMessage ocmcMessage) {
        this.channel = channel;
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.ocmcMessage = ocmcMessage;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getUsername() {
        return this.username;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public byte[] getData() {
        return this.data;
    }

    public OCMCMessage getOcmcMessage() {
        return this.ocmcMessage;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setOcmcMessage(OCMCMessage ocmcMessage) {
        this.ocmcMessage = ocmcMessage;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QueuedMessage)) {
            return false;
        }
        QueuedMessage other = (QueuedMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$channel = this.getChannel();
        String other$channel = other.getChannel();
        if (!Objects.equals(this$channel, other$channel)) {
            return false;
        }
        String this$username = this.getUsername();
        String other$username = other.getUsername();
        if (!Objects.equals(this$username, other$username)) {
            return false;
        }
        UUID this$uuid = this.getUuid();
        UUID other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !((Object)this$uuid).equals(other$uuid)) {
            return false;
        }
        if (!Arrays.equals(this.getData(), other.getData())) {
            return false;
        }
        OCMCMessage this$ocmcMessage = this.getOcmcMessage();
        OCMCMessage other$ocmcMessage = other.getOcmcMessage();
        return Objects.equals(this$ocmcMessage, other$ocmcMessage);
    }

    protected boolean canEqual(Object other) {
        return other instanceof QueuedMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $channel = this.getChannel();
        result = result * 59 + ($channel == null ? 43 : $channel.hashCode());
        String $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        UUID $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : ((Object)$uuid).hashCode());
        result = result * 59 + Arrays.hashCode(this.getData());
        OCMCMessage $ocmcMessage = this.getOcmcMessage();
        result = result * 59 + ($ocmcMessage == null ? 43 : $ocmcMessage.hashCode());
        return result;
    }

    public String toString() {
        return "QueuedMessage(channel=" + this.getChannel() + ", username=" + this.getUsername() + ", uuid=" + this.getUuid() + ", data=" + Arrays.toString(this.getData()) + ", ocmcMessage=" + this.getOcmcMessage() + ")";
    }
}

