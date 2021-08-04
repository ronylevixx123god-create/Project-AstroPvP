package gg.sense.anticheat.packet.client;

public class CTimeRequestPacket extends ClientPacket {
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CTimeRequestPacket)) {
            return false;
        }
        CTimeRequestPacket cTimeRequestPacket = (CTimeRequestPacket)object;
        return cTimeRequestPacket.a(this);
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CTimeRequestPacket;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CTimeRequestPacket()";
    }
}