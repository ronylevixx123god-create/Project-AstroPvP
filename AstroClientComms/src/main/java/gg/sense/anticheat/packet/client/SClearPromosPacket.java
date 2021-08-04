package gg.sense.anticheat.packet.client;

public class SClearPromosPacket extends ClientPacket {
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SClearPromosPacket)) {
            return false;
        }
        SClearPromosPacket sClearPromosPacket = (SClearPromosPacket)object;
        return sClearPromosPacket.a(this);
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SClearPromosPacket;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "SClearPromosPacket()";
    }
}
 