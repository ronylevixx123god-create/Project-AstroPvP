package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SSetXPPacket
extends ClientPacket {
    @SerializedName(value="level")
    private int level;
    @SerializedName(value="xp")
    private long xp;
    @SerializedName(value="total")
    private long total;
    @SerializedName(value="color")
    private String color;
    @SerializedName(value="animation")
    private boolean animation;

    public int b() {
        return this.level;
    }

    public long e() {
        return this.xp;
    }

    public long d() {
        return this.total;
    }

    public String c() {
        return this.color;
    }

    public boolean a() {
        return this.animation;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetXPPacket)) {
            return false;
        }
        SSetXPPacket sSetXPPacket = (SSetXPPacket)object;
        if (!sSetXPPacket.a(this)) {
            return false;
        }
        if (this.b() != sSetXPPacket.b()) {
            return false;
        }
        if (this.e() != sSetXPPacket.e()) {
            return false;
        }
        if (this.d() != sSetXPPacket.d()) {
            return false;
        }
        String string = this.c();
        String string2 = sSetXPPacket.c();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        return this.a() == sSetXPPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetXPPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.b();
        long l = this.e();
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = this.d();
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        String string = this.c();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + (this.a() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "SSetXPPacket(level=" + this.b() + ", xp=" + this.e() + ", total=" + this.d() + ", color=" + this.c() + ", animation=" + this.a() + ")";
    }
}