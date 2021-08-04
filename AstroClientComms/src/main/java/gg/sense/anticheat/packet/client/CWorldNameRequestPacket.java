package gg.sense.anticheat.packet.client;

public class CWorldNameRequestPacket
extends ClientPacket {
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CWorldNameRequestPacket)) {
            return false;
        }
        CWorldNameRequestPacket cWorldNameRequestPacket = (CWorldNameRequestPacket)object;
        return cWorldNameRequestPacket.a(this);
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CWorldNameRequestPacket;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CWorldNameRequestPacket()";
    }
}