package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SEventPacket
extends ClientPacket {
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="time")
    private long time;

    public String b() {
        return this.name;
    }

    public long a() {
        return this.time;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SEventPacket)) {
            return false;
        }
        SEventPacket sEventPacket = (SEventPacket)object;
        if (!sEventPacket.a(this)) {
            return false;
        }
        String string = this.b();
        String string2 = sEventPacket.b();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        return this.a() == sEventPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SEventPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.b();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        long l = this.a();
        n = n * 59 + (int)(l >>> 32 ^ l);
        return n;
    }

    @Override
    public String toString() {
        return "SEventPacket(name=" + this.b() + ", time=" + this.a() + ")";
    }
}