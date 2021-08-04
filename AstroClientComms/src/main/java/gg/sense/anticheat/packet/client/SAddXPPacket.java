package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SAddXPPacket
extends ClientPacket {
    @SerializedName(value="level")
    private int level;
    @SerializedName(value="xp")
    private long xp;
    @SerializedName(value="color")
    private String color;

    public int c() {
        return this.level;
    }

    public long b() {
        return this.xp;
    }

    public String a() {
        return this.color;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SAddXPPacket)) {
            return false;
        }
        SAddXPPacket sAddXPPacket = (SAddXPPacket)object;
        if (!sAddXPPacket.a(this)) {
            return false;
        }
        if (this.c() != sAddXPPacket.c()) {
            return false;
        }
        if (this.b() != sAddXPPacket.b()) {
            return false;
        }
        String string = this.a();
        String string2 = sAddXPPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SAddXPPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.c();
        long l = this.b();
        n = n * 59 + (int)(l >>> 32 ^ l);
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SAddXPPacket(level=" + this.c() + ", xp=" + this.b() + ", color=" + this.a() + ")";
    }
}