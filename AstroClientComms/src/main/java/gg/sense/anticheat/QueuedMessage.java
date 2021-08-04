package gg.sense.anticheat;

import gg.sense.anticheat.packet.client.ClientPacket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class QueuedMessage {
    private byte[] data;
    private ClientPacket message;

    public QueuedMessage(ClientPacket message) {
        this.message = message;
    }

    public QueuedMessage(byte[] data) {
        this.data = data;
    }
}
