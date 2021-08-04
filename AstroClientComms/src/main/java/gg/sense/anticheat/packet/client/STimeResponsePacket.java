package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class STimeResponsePacket
extends ClientPacket {
    @SerializedName(value="epoch")
    private long epoch;

    public long a() {
        return this.epoch;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof STimeResponsePacket)) {
            return false;
        }
        STimeResponsePacket sTimeResponsePacket = (STimeResponsePacket)object;
        if (!sTimeResponsePacket.a(this)) {
            return false;
        }
        return this.a() == sTimeResponsePacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof STimeResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        long l = this.a();
        n = n * 59 + (int)(l >>> 32 ^ l);
        return n;
    }

    @Override
    public String toString() {
        return "STimeResponsePacket(epoch=" + this.a() + ")";
    }
}