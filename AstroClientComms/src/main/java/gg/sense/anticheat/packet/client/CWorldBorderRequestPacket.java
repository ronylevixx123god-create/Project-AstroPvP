package gg.sense.anticheat.packet.client;

public class CWorldBorderRequestPacket extends ClientPacket {
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CWorldBorderRequestPacket)) {
            return false;
        }
        CWorldBorderRequestPacket cWorldBorderRequestPacket = (CWorldBorderRequestPacket)object;
        return cWorldBorderRequestPacket.a(this);
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CWorldBorderRequestPacket;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CWorldBorderRequestPacket()";
    }
}