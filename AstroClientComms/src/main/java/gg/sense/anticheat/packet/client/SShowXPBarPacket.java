package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SShowXPBarPacket
extends ClientPacket {
    @SerializedName(value="value")
    private boolean value;

    public boolean a() {
        return this.value;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SShowXPBarPacket)) {
            return false;
        }
        SShowXPBarPacket sShowXPBarPacket = (SShowXPBarPacket)object;
        if (!sShowXPBarPacket.a(this)) {
            return false;
        }
        return this.a() == sShowXPBarPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SShowXPBarPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + (this.a() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "SShowXPBarPacket(value=" + this.a() + ")";
    }
}