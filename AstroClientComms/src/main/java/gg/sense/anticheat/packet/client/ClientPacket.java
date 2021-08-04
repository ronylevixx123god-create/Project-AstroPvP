package gg.sense.anticheat.packet.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@EqualsAndHashCode
public abstract class ClientPacket implements Serializable {
    public transient short protocolVersion;
    protected boolean a(Object object) {
        return object instanceof ClientPacket;
    }
}