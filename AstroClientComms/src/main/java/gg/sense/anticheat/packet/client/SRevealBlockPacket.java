package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SRevealBlockPacket
extends ClientPacket {
    @SerializedName(value="radius")
    private int radius;
    @SerializedName(value="millisecondsToShow")
    private long millisecondsToShow;

    public int b() {
        return this.radius;
    }

    public long a() {
        return this.millisecondsToShow;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SRevealBlockPacket)) {
            return false;
        }
        SRevealBlockPacket sRevealBlockPacket = (SRevealBlockPacket)object;
        if (!sRevealBlockPacket.a(this)) {
            return false;
        }
        if (this.b() != sRevealBlockPacket.b()) {
            return false;
        }
        return this.a() == sRevealBlockPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SRevealBlockPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.b();
        long l = this.a();
        n = n * 59 + (int)(l >>> 32 ^ l);
        return n;
    }

    @Override
    public String toString() {
        return "SRevealBlockPacket(radius=" + this.b() + ", millisecondsToShow=" + this.a() + ")";
    }
}