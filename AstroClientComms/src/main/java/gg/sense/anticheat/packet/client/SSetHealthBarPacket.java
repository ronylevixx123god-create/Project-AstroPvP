package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SSetHealthBarPacket
extends ClientPacket {
    @SerializedName(value="currentHealth")
    private long currentHealth;
    @SerializedName(value="maxHealth")
    private long maxHealth;
    @SerializedName(value="name")
    private String name;

    public long b() {
        return this.currentHealth;
    }

    public long c() {
        return this.maxHealth;
    }

    public String a() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetHealthBarPacket)) {
            return false;
        }
        SSetHealthBarPacket sSetHealthBarPacket = (SSetHealthBarPacket)object;
        if (!sSetHealthBarPacket.a(this)) {
            return false;
        }
        if (this.b() != sSetHealthBarPacket.b()) {
            return false;
        }
        if (this.c() != sSetHealthBarPacket.c()) {
            return false;
        }
        String string = this.a();
        String string2 = sSetHealthBarPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetHealthBarPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        long l = this.b();
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = this.c();
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SSetHealthBarPacket(currentHealth=" + this.b() + ", maxHealth=" + this.c() + ", name=" + this.a() + ")";
    }
}