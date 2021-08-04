package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SSetEntitySizePacket
extends ClientPacket {
    @SerializedName(value="entityID")
    private int entityID;
    @SerializedName(value="size")
    private double size;

    public int a() {
        return this.entityID;
    }

    public double b() {
        return this.size;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetEntitySizePacket)) {
            return false;
        }
        SSetEntitySizePacket sSetEntitySizePacket = (SSetEntitySizePacket)object;
        if (!sSetEntitySizePacket.a(this)) {
            return false;
        }
        if (this.a() != sSetEntitySizePacket.a()) {
            return false;
        }
        return Double.compare(this.b(), sSetEntitySizePacket.b()) == 0;
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetEntitySizePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.a();
        long l = Double.doubleToLongBits(this.b());
        n = n * 59 + (int)(l >>> 32 ^ l);
        return n;
    }

    @Override
    public String toString() {
        return "SSetEntitySizePacket(entityID=" + this.a() + ", size=" + this.b() + ")";
    }
}
 